/*
 * eGov suite of products aim to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) <2015>  eGovernments Foundation
 *
 *     The updated version of eGov suite of products as by eGovernments Foundation
 *     is available at http://www.egovernments.org
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program. If not, see http://www.gnu.org/licenses/ or
 *     http://www.gnu.org/licenses/gpl.html .
 *
 *     In addition to the terms of the GPL license to be adhered to in using this
 *     program, the following additional terms are to be complied with:
 *
 *         1) All versions of this program, verbatim or modified must carry this
 *            Legal Notice.
 *
 *         2) Any misrepresentation of the origin of the material is prohibited. It
 *            is required that all modified versions of this material be marked in
 *            reasonable ways as different from the original version.
 *
 *         3) This license does not grant any rights to any user of the program
 *            with regards to rights under trademark law for use of the trade names
 *            or trademarks of eGovernments Foundation.
 *
 *   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */

package org.egov.infra.config.security.oauth2.provider.token.store.redis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.common.ExpiringOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * This from spring oauth2 master branch, has to be replaced with original when released.
 **/
public class RedisTokenStore implements TokenStore {

    private static final String ACCESS = "access:";
    private static final String AUTH_TO_ACCESS = "auth_to_access:";
    private static final String AUTH = "auth:";
    private static final String REFRESH_AUTH = "refresh_auth:";
    private static final String ACCESS_TO_REFRESH = "access_to_refresh:";
    private static final String REFRESH = "refresh:";
    private static final String REFRESH_TO_ACCESS = "refresh_to_access:";
    private static final String CLIENT_ID_TO_ACCESS = "client_id_to_access:";
    private static final String UNAME_TO_ACCESS = "uname_to_access:";

    private final RedisConnectionFactory connectionFactory;
    private AuthenticationKeyGenerator authenticationKeyGenerator = new DefaultAuthenticationKeyGenerator();
    private RedisTokenStoreSerializationStrategy serializationStrategy = new JdkSerializationStrategy();

    public RedisTokenStore(RedisConnectionFactory connectionFactory) {
            this.connectionFactory = connectionFactory;
    }

    public void setAuthenticationKeyGenerator(AuthenticationKeyGenerator authenticationKeyGenerator) {
            this.authenticationKeyGenerator = authenticationKeyGenerator;
    }

    public void setSerializationStrategy(RedisTokenStoreSerializationStrategy serializationStrategy) {
            this.serializationStrategy = serializationStrategy;
    }

    private RedisConnection getConnection() {
            return connectionFactory.getConnection();
    }

    private byte[] serialize(Object object) {
            return serializationStrategy.serialize(object);
    }

    private OAuth2AccessToken deserializeAccessToken(byte[] bytes) {
            return serializationStrategy.deserialize(bytes, OAuth2AccessToken.class);
    }

    private OAuth2Authentication deserializeAuthentication(byte[] bytes) {
            return serializationStrategy.deserialize(bytes, OAuth2Authentication.class);
    }

    private OAuth2RefreshToken deserializeRefreshToken(byte[] bytes) {
            return serializationStrategy.deserialize(bytes, OAuth2RefreshToken.class);
    }

    private byte[] serialize(String string) {
            return serializationStrategy.serialize(string);
    }

    private String deserializeString(byte[] bytes) {
            return serializationStrategy.deserializeString(bytes);
    }

    @Override
    public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
            String key = authenticationKeyGenerator.extractKey(authentication);
            byte[] serializedKey = serialize(AUTH_TO_ACCESS + key);
            byte[] bytes = null;
            RedisConnection conn = getConnection();
            try {
                    bytes = conn.get(serializedKey);
            } finally {
                    conn.close();
            }
            OAuth2AccessToken accessToken = deserializeAccessToken(bytes);
            if (accessToken != null
                            && !key.equals(authenticationKeyGenerator.extractKey(readAuthentication(accessToken.getValue())))) {
                    // Keep the stores consistent (maybe the same user is
                    // represented by this authentication but the details have
                    // changed)
                    storeAccessToken(accessToken, authentication);
            }
            return accessToken;
    }

    @Override
    public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
            return readAuthentication(token.getValue());
    }

    @Override
    public OAuth2Authentication readAuthentication(String token) {
            byte[] bytes = null;
            RedisConnection conn = getConnection();
            try {
                    bytes = conn.get(serialize(AUTH + token));
            } finally {
                    conn.close();
            }
            OAuth2Authentication auth = deserializeAuthentication(bytes);
            return auth;
    }

    @Override
    public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {
            return readAuthenticationForRefreshToken(token.getValue());
    }

    public OAuth2Authentication readAuthenticationForRefreshToken(String token) {
            RedisConnection conn = getConnection();
            try {
                    byte[] bytes = conn.get(serialize(REFRESH_AUTH + token));
                    OAuth2Authentication auth = deserializeAuthentication(bytes);
                    return auth;
            } finally {
                    conn.close();
            }
    }

    @Override
    public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
            byte[] serializedAccessToken = serialize(token);
            byte[] serializedAuth = serialize(authentication);
            byte[] accessKey = serialize(ACCESS + token.getValue());
            byte[] authKey = serialize(AUTH + token.getValue());
            byte[] authToAccessKey = serialize(AUTH_TO_ACCESS + authenticationKeyGenerator.extractKey(authentication));
            byte[] approvalKey = serialize(UNAME_TO_ACCESS + getApprovalKey(authentication));
            byte[] clientId = serialize(CLIENT_ID_TO_ACCESS + authentication.getOAuth2Request().getClientId());

            RedisConnection conn = getConnection();
            try {
                    conn.openPipeline();
                    conn.set(accessKey, serializedAccessToken);
                    conn.set(authKey, serializedAuth);
                    conn.set(authToAccessKey, serializedAccessToken);
                    if (!authentication.isClientOnly()) {
                            conn.rPush(approvalKey, serializedAccessToken);
                    }
                    conn.rPush(clientId, serializedAccessToken);
                    if (token.getExpiration() != null) {
                            int seconds = token.getExpiresIn();
                            conn.expire(accessKey, seconds);
                            conn.expire(authKey, seconds);
                            conn.expire(authToAccessKey, seconds);
                            conn.expire(clientId, seconds);
                            conn.expire(approvalKey, seconds);
                    }
                    OAuth2RefreshToken refreshToken = token.getRefreshToken();
                    if (refreshToken != null && refreshToken.getValue() != null) {
                            byte[] refresh = serialize(token.getRefreshToken().getValue());
                            byte[] auth = serialize(token.getValue());
                            byte[] refreshToAccessKey = serialize(REFRESH_TO_ACCESS + token.getRefreshToken().getValue());
                            conn.set(refreshToAccessKey, auth);
                            byte[] accessToRefreshKey = serialize(ACCESS_TO_REFRESH + token.getValue());
                            conn.set(accessToRefreshKey, refresh);
                            if (refreshToken instanceof ExpiringOAuth2RefreshToken) {
                                    ExpiringOAuth2RefreshToken expiringRefreshToken = (ExpiringOAuth2RefreshToken) refreshToken;
                                    Date expiration = expiringRefreshToken.getExpiration();
                                    if (expiration != null) {
                                            int seconds = (int) (expiration.getTime() / 1000);
                                            conn.expireAt(refreshToAccessKey, seconds);
                                            conn.expireAt(accessToRefreshKey, seconds);
                                    }
                            }
                    }
                    conn.closePipeline();
            } finally {
                    conn.close();
            }
    }

    private static String getApprovalKey(OAuth2Authentication authentication) {
            String userName = authentication.getUserAuthentication() == null ? "" : authentication.getUserAuthentication()
                            .getName();
            return getApprovalKey(authentication.getOAuth2Request().getClientId(), userName);
    }

    private static String getApprovalKey(String clientId, String userName) {
            return clientId + (userName == null ? "" : ":" + userName);
    }

    @Override
    public void removeAccessToken(OAuth2AccessToken accessToken) {
            removeAccessToken(accessToken.getValue());
    }

    @Override
    public OAuth2AccessToken readAccessToken(String tokenValue) {
            byte[] key = serialize(ACCESS + tokenValue);
            byte[] bytes = null;
            RedisConnection conn = getConnection();
            try {
                    bytes = conn.get(key);
            } finally {
                    conn.close();
            }
            OAuth2AccessToken accessToken = deserializeAccessToken(bytes);
            return accessToken;
    }

    public void removeAccessToken(String tokenValue) {
            byte[] accessKey = serialize(ACCESS + tokenValue);
            byte[] authKey = serialize(AUTH + tokenValue);
            byte[] accessToRefreshKey = serialize(ACCESS_TO_REFRESH + tokenValue);
            RedisConnection conn = getConnection();
            try {
                    conn.openPipeline();
                    conn.get(accessKey);
                    conn.get(authKey);
                    conn.del(accessKey);
                    conn.del(accessToRefreshKey);
                    // Don't remove the refresh token - it's up to the caller to do that
                    conn.del(authKey);
                    List<Object> results = conn.closePipeline();
                    byte[] access = (byte[]) results.get(0);
                    byte[] auth = (byte[]) results.get(1);

                    OAuth2Authentication authentication = deserializeAuthentication(auth);
                    if (authentication != null) {
                            String key = authenticationKeyGenerator.extractKey(authentication);
                            byte[] authToAccessKey = serialize(AUTH_TO_ACCESS + key);
                            byte[] unameKey = serialize(UNAME_TO_ACCESS + getApprovalKey(authentication));
                            byte[] clientId = serialize(CLIENT_ID_TO_ACCESS + authentication.getOAuth2Request().getClientId());
                            conn.openPipeline();
                            conn.del(authToAccessKey);
                            conn.lRem(unameKey, 1, access);
                            conn.lRem(clientId, 1, access);
                            conn.del(serialize(ACCESS + key));
                            conn.closePipeline();
                    }
            } finally {
                    conn.close();
            }
    }

    @Override
    public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
            byte[] refreshKey = serialize(REFRESH + refreshToken.getValue());
            byte[] refreshAuthKey = serialize(REFRESH_AUTH + refreshToken.getValue());
            byte[] serializedRefreshToken = serialize(refreshToken);
            RedisConnection conn = getConnection();
            try {
                    conn.openPipeline();
                    conn.set(refreshKey, serializedRefreshToken);
                    conn.set(refreshAuthKey, serialize(authentication));
                    if (refreshToken instanceof ExpiringOAuth2RefreshToken) {
                            ExpiringOAuth2RefreshToken expiringRefreshToken = (ExpiringOAuth2RefreshToken) refreshToken;
                            Date expiration = expiringRefreshToken.getExpiration();
                            if (expiration != null) {
                                    int seconds = (int) (expiration.getTime() / 1000);
                                    conn.expireAt(refreshKey, seconds);
                                    conn.expireAt(refreshAuthKey, seconds);
                            }
                    }
                    conn.closePipeline();
            } finally {
                    conn.close();
            }
    }

    @Override
    public OAuth2RefreshToken readRefreshToken(String tokenValue) {
            byte[] key = serialize(REFRESH + tokenValue);
            byte[] bytes = null;
            RedisConnection conn = getConnection();
            try {
                    bytes = conn.get(key);
            } finally {
                    conn.close();
            }
            OAuth2RefreshToken refreshToken = deserializeRefreshToken(bytes);
            return refreshToken;
    }

    @Override
    public void removeRefreshToken(OAuth2RefreshToken refreshToken) {
            removeRefreshToken(refreshToken.getValue());
    }

    public void removeRefreshToken(String tokenValue) {
            byte[] refreshKey = serialize(REFRESH + tokenValue);
            byte[] refresh2AccessKey = serialize(REFRESH_TO_ACCESS + tokenValue);
            byte[] access2RefreshKey = serialize(ACCESS_TO_REFRESH + tokenValue);
            RedisConnection conn = getConnection();
            try {
                    conn.openPipeline();
                    conn.del(refreshKey);
                    conn.del(refresh2AccessKey);
                    conn.del(access2RefreshKey);
                    conn.closePipeline();
            } finally {
                    conn.close();
            }
    }

    @Override
    public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {
            removeAccessTokenUsingRefreshToken(refreshToken.getValue());
    }

    private void removeAccessTokenUsingRefreshToken(String refreshToken) {
            byte[] key = serialize(REFRESH_TO_ACCESS + refreshToken);
            List<Object> results = null;
            RedisConnection conn = getConnection();
            try {
                    conn.openPipeline();
                    conn.get(key);
                    conn.del(key);
                    results = conn.closePipeline();
            } finally {
                    conn.close();
            }
            if (results == null) {
                    return;
            }
            byte[] bytes = (byte[]) results.get(0);
            String accessToken = deserializeString(bytes);
            if (accessToken != null) {
                    removeAccessToken(accessToken);
            }
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientId, String userName) {
            byte[] approvalKey = serialize(UNAME_TO_ACCESS + getApprovalKey(clientId, userName));
            List<byte[]> byteList = null;
            RedisConnection conn = getConnection();
            try {
                    byteList = conn.lRange(approvalKey, 0, -1);
            } finally {
                    conn.close();
            }
            if (byteList == null || byteList.size() == 0) {
                    return Collections.<OAuth2AccessToken> emptySet();
            }
            List<OAuth2AccessToken> accessTokens = new ArrayList<OAuth2AccessToken>(byteList.size());
            for (byte[] bytes : byteList) {
                    OAuth2AccessToken accessToken = deserializeAccessToken(bytes);
                    accessTokens.add(accessToken);
            }
            return Collections.<OAuth2AccessToken> unmodifiableCollection(accessTokens);
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
            byte[] key = serialize(CLIENT_ID_TO_ACCESS + clientId);
            List<byte[]> byteList = null;
            RedisConnection conn = getConnection();
            try {
                    byteList = conn.lRange(key, 0, -1);
            } finally {
                    conn.close();
            }
            if (byteList == null || byteList.size() == 0) {
                    return Collections.<OAuth2AccessToken> emptySet();
            }
            List<OAuth2AccessToken> accessTokens = new ArrayList<OAuth2AccessToken>(byteList.size());
            for (byte[] bytes : byteList) {
                    OAuth2AccessToken accessToken = deserializeAccessToken(bytes);
                    accessTokens.add(accessToken);
            }
            return Collections.<OAuth2AccessToken> unmodifiableCollection(accessTokens);
    }
}
