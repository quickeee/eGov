/**
 * eGov suite of products aim to improve the internal efficiency,transparency, accountability and the service delivery of the
 * government organizations.
 * 
 * Copyright (C) <2015> eGovernments Foundation
 * 
 * The updated version of eGov suite of products as by eGovernments Foundation is available at http://www.egovernments.org
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the License, or any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If not, see
 * http://www.gnu.org/licenses/ or http://www.gnu.org/licenses/gpl.html .
 * 
 * In addition to the terms of the GPL license to be adhered to in using this program, the following additional terms are to be
 * complied with:
 * 
 * 1) All versions of this program, verbatim or modified must carry this Legal Notice.
 * 
 * 2) Any misrepresentation of the origin of the material is prohibited. It is required that all modified versions of this
 * material be marked in reasonable ways as different from the original version.
 * 
 * 3) This license does not grant any rights to any user of the program with regards to rights under trademark law for use of the
 * trade names or trademarks of eGovernments Foundation.
 * 
 * In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */

package org.egov.commons;

// Generated Jul 19, 2007 2:41:17 PM by Hibernate Tools 3.2.0.b9

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.egov.infra.admin.master.entity.User;
import org.egov.infra.persistence.entity.AbstractPersistable;
import org.egov.infra.persistence.validator.annotation.Required;
import org.egov.infra.persistence.validator.annotation.Unique;
import org.hibernate.search.annotations.Field;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "fund")
@SequenceGenerator(name = Fund.SEQ, sequenceName = Fund.SEQ, allocationSize = 1)
@Unique(id = "id", tableName = "Fund", fields = { "code" }, columnName = { "code" }, enableDfltMsg = true)
public class Fund extends AbstractPersistable<Integer> {

	private static final long serialVersionUID =7977534010758407945L;

	public static final String SEQ = "SEQ_Fund";

    @Id
    @GeneratedValue(generator = Fund.SEQ, strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Field
    @Length(max = 50, min = 2)
    @Required
    private String name;

    @Length(max = 50, min = 2)
    @Required
    private String code;

    private Character identifier;
    @Required
    private BigDecimal llevel = BigDecimal.ONE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentid")
    private Fund parentId;

    private Boolean isnotleaf;

    private Boolean isactive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "createdby")
    private User createdby;
    private Date createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lastModifiedBy")
    private User lastModifiedBy;

    private Date lastModifiedDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Character getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Character identifier) {
        this.identifier = identifier;
    }

    public BigDecimal getLlevel() {
        return llevel;
    }

    public void setLlevel(BigDecimal llevel) {
        this.llevel = llevel;
    }

    public Fund getParentId() {
        return parentId;
    }

    public void setParentId(Fund parentId) {
        this.parentId = parentId;
    }

    public Boolean getIsnotleaf() {
        return isnotleaf;
    }

    public void setIsnotleaf(Boolean isnotleaf) {
        this.isnotleaf = isnotleaf;
    }

    public Boolean getIsactive() {
        return isactive;
    }

    public void setIsactive(Boolean isactive) {
        this.isactive = isactive;
    }

    public User getCreatedby() {
        return createdby;
    }

    public void setCreatedby(User createdby) {
        this.createdby = createdby;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public User getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(User lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

}
