<%@page contentType="text/json" %>
<%@taglib prefix="s" uri="/WEB-INF/taglib/struts-tags.tld" %> 
{
"ResultSet": {
    "Result":[
    <s:iterator var="s" value="propUsageList" status="status">
    {"Text":"<s:property value="%{usageName}" />",
    "Value":"<s:property value="%{id}" />"
    }<s:if test="!#status.last">,</s:if>
    </s:iterator>       
    ]
  }
}
