<%--
  ~ eGov suite of products aim to improve the internal efficiency,transparency,
  ~    accountability and the service delivery of the government  organizations.
  ~
  ~     Copyright (C) <2015>  eGovernments Foundation
  ~
  ~     The updated version of eGov suite of products as by eGovernments Foundation
  ~     is available at http://www.egovernments.org
  ~
  ~     This program is free software: you can redistribute it and/or modify
  ~     it under the terms of the GNU General Public License as published by
  ~     the Free Software Foundation, either version 3 of the License, or
  ~     any later version.
  ~
  ~     This program is distributed in the hope that it will be useful,
  ~     but WITHOUT ANY WARRANTY; without even the implied warranty of
  ~     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  ~     GNU General Public License for more details.
  ~
  ~     You should have received a copy of the GNU General Public License
  ~     along with this program. If not, see http://www.gnu.org/licenses/ or
  ~     http://www.gnu.org/licenses/gpl.html .
  ~
  ~     In addition to the terms of the GPL license to be adhered to in using this
  ~     program, the following additional terms are to be complied with:
  ~
  ~         1) All versions of this program, verbatim or modified must carry this
  ~            Legal Notice.
  ~
  ~         2) Any misrepresentation of the origin of the material is prohibited. It
  ~            is required that all modified versions of this material be marked in
  ~            reasonable ways as different from the original version.
  ~
  ~         3) This license does not grant any rights to any user of the program
  ~            with regards to rights under trademark law for use of the trade names
  ~            or trademarks of eGovernments Foundation.
  ~
  ~   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
  --%>
<%@ taglib prefix="s" uri="/WEB-INF/taglib/struts-tags.tld"%>
<%@ taglib prefix="egov" tagdir="/WEB-INF/tags"%>
<br/>
<table width="100%">
	<tr>
		<td colspan="5" class="headingwk">
			<div class="arrowiconwk">
				<img src="${pageContext.request.contextPath}/images/arrow.gif" height="20" />
			</div>
			<div class="headplacer">
				<s:text name='license.title.approvaldetails' />
			</div>
		</td>
	</tr>
</table>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<c:choose>
			<c:when test="${trclass=='greybox'}">
				<c:set var="trclass" value="bluebox" />
			</c:when>
			<c:when test="${trclass=='bluebox'}">
				<c:set var="trclass" value="greybox" />
			</c:when>
		</c:choose>
		<tr>
			<td class="<c:out value="${trclass}"/>">
				<s:text name="license.workflow.approver.department" />
				<span class="mandatory1">*</span>
			</td>
			<td class="<c:out value="${trclass}"/>">
				<s:select name="workflowBean.departmentId" id="departmentId" list="workflowBean.departmentList" listKey="id" listValue="deptName" headerKey="-1" headerValue="----Choose----" value="%{workflowBean.departmentId}" onchange="populateDesignations()" />
			</td>
			<egov:ajaxdropdown id="designationId" fields="['Text','Value']" dropdownId="designationId" url="domain/commonAjax-ajaxPopulateDesignationsByDept.action" />
			<td class="<c:out value="${trclass}"/>">
				Approver Designation
				<span class="mandatory1">*</span>
			</td>
			<td class="<c:out value="${trclass}"/>">
				<s:select name="workflowBean.designationId" id="designationId" list="workflowBean.designationList" listKey="designationId" listValue="designationName" headerKey="-1" headerValue="----Choose----" onchange="populateUser()" />
			</td>
		</tr>
		<c:choose>
			<c:when test="${trclass=='greybox'}">
				<c:set var="trclass" value="bluebox" />
			</c:when>
			<c:when test="${trclass=='bluebox'}">
				<c:set var="trclass" value="greybox" />
			</c:when>
		</c:choose>
		<tr>
			<egov:ajaxdropdown id="approverUserId" fields="['Text','Value']" dropdownId="approverUserId" url="domain/commonAjax-ajaxPopulateUsersByDesignation.action" />
			<td class="<c:out value="${trclass}"/>" width="13%">
				<s:text name="license.workflow.approver" />
				<span class="mandatory1">*</span>
			</td>
			<td class="<c:out value="${trclass}"/>" width="33%">
				<s:select id="approverUserId" name="workflowBean.approverUserId" list="workflowBean.appoverUserList" headerKey="-1" headerValue="----Choose----" listKey="id" listValue="userName" />
			</td>
			<td class="<c:out value="${trclass}"/>"></td>
			<td class="<c:out value="${trclass}"/>"></td>
		</tr>
		<c:choose>
			<c:when test="${trclass=='greybox'}">
				<c:set var="trclass" value="bluebox" />
			</c:when>
			<c:when test="${trclass=='bluebox'}">
				<c:set var="trclass" value="greybox" />
			</c:when>
		</c:choose>
		<tr>
			<td class="<c:out value="${trclass}"/>" width="13%">
				<s:text name="workflow.approver.comment" />
			</td>
			<td class="<c:out value="${trclass}"/>" colspan="4" align="centre">
				<center>
					<s:textarea name="workflowBean.comments" rows="3" cols="80" />
				</center>
			</td>
		</tr>
		<s:hidden name="workflowBean.actionName" id="workflowBean.actionName" />
		<s:hidden name="model.id" id="model.id" />
	</table>

<script>
	designationIdFailureHandler=function(){
	}

	function populateDesignations(){
		populatedesignationId({departmentId:document.getElementById("departmentId").value})
	}

	function populateUser(){
   		populateapproverUserId({designationId:document.getElementById("designationId").value})
	}
 
	function validateApprover(obj){
		document.getElementById("workflowBean.actionName").value=obj.value;
		if(obj.value.toUpperCase()=="REJECT" || obj.value.toUpperCase()=="APPROVE" || obj.value.toUpperCase()=="SAVE") {
			return true;
		} else if(document.getElementById("approverUserId") && document.getElementById("approverUserId").value=="-1") {
			bootbox.alert("Please select appropriate Approver info.");
			return false;
		} else {
			return true;
		}
	}
</script>
