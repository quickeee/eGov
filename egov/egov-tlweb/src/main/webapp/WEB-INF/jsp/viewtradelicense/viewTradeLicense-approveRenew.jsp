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
<%@ include file="/includes/taglibs.jsp"%>
<html>
	<head>
		<title><s:text name="page.title.approve.renew.trade" />
		</title>
		<script>
	  		function validateForm(obj) {
	    		if (validateApprover(obj) == false) {
	      			return false;
	    		} else {
	      			clearWaterMark();
	      			return true;
	    		}
	  		}
		</script>
	</head>
	<body>
		<table align="center" width="100%">
			<tbody>
				<tr>
					<td>
						<div align="center">
							<center>
								<div class="formmainbox">
									<div class="headingbg">
										<s:text name="page.title.approve.renew.trade" />
									</div>
									<table>
										<tr>
											<td align="left" style="color: #FF0000">
												<s:actionerror cssStyle="color: #FF0000" />
												<s:fielderror />
												<s:actionmessage />
											</td>
										</tr>
									</table>
									<s:form action="viewTradeLicense" theme="simple" name="viewForm" validate="true">
									<s:token/>
										<s:push value="model">
											<s:hidden name="docNumber" id="docNumber" />
											<c:set var="trclass" value="greybox" />
											<table border="0" cellpadding="0" cellspacing="0" width="100%">
												<%@ include file='../common/view.jsp'%>
												<s:if test="%{motorInstalled}">
													<tr>
														<td colspan="5">
															<%@ include file='../common/motordetailsview.jsp'%>
														</td>
													</tr>
												</s:if>
												<tr>
													<td colspan="5">
														<%@ include file='../common/feedetailsview.jsp'%>
													</td>
												</tr>												
												<tr>
													<td colspan="5">
														<%@ include file='../common/commonWorkflowMatrix.jsp'%>
														<%@ include file='../common/commonWorkflowMatrix-button.jsp'%>
													</td>
												</tr>
												<tr>
													<td colspan="5" align="center">
														<br/>
														<input type="button" class="button" value="Upload Document" id="docUploadButton" onclick="showDocumentManagerForDoc('docNumber');updateCurrentDocId('docNumber')" tabindex="1" />
														<br/>
														<br/>
													</td>
												</tr>												
											</table>
											<div>
												<table>
													<tr class="buttonbottom" id="buttondiv" style="align: middle">
													<s:if test="%{roleName.contains('TLAPPROVER')}">
														<td>
															<s:submit type="submit" cssClass="buttonsubmit" value="Approve" id="Approve" method="approveRenew" onclick="return validateForm(this);" />
														</td>
														</s:if>
														<td>
															<s:submit type="submit" cssClass="buttonsubmit" value="Forward" id="Forward" method="approveRenew" onclick="return validateForm(this);" />
														</td>
														<td>
															<s:submit type="submit" cssClass="buttonsubmit" value="Reject" id="Reject" method="approveRenew" onclick="return validateForm(this);" />
														</td>
														<td>
															<input type="button" value="Close" id="closeButton" onclick="javascript:window.close();" class="button" />
														</td>
													</tr>
												</table>
											</div>
										</s:push>
									</s:form>
								</div>
							</center>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
	</body>
</html>
