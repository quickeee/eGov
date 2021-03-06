<!---------------------------------------------------------------------------------
	eGov suite of products aim to improve the internal efficiency,transparency, 
    accountability and the service delivery of the government  organizations.
 
    Copyright (C) <2015>  eGovernments Foundation
 
    The updated version of eGov suite of products as by eGovernments Foundation 
    is available at http://www.egovernments.org
 
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    any later version.
 
    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.
 
    You should have received a copy of the GNU General Public License
    along with this program. If not, see http://www.gnu.org/licenses/ or 
    http://www.gnu.org/licenses/gpl.html .
 
    In addition to the terms of the GPL license to be adhered to in using this
    program, the following additional terms are to be complied with:
 
 	1) All versions of this program, verbatim or modified must carry this 
 	   Legal Notice.
 
 	2) Any misrepresentation of the origin of the material is prohibited. It 
 	   is required that all modified versions of this material be marked in 
 	   reasonable ways as different from the original version.
 
 	3) This license does not grant any rights to any user of the program 
 	   with regards to rights under trademark law for use of the trade names 
 	   or trademarks of eGovernments Foundation.
 
   	In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
------------------------------------------------------------------------------- -->
<%@ include file="/includes/taglibs.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<html>
<head>
	<title><s:text name="tax.dues"/></title>
</head>
<body>
	<div class="formmainbox" align="center">
		<div class="headingbg">
			<s:text name="arrdndpen" />
		</div>
		<table border="0" cellspacing="0" cellpadding="0" width="50%">
			<tr>
				<td class="bluebox"></td>
				<td class="bluebox"><s:text name="CurrentTax" /> :</td>
				<td class="bluebox">
					<span class="bold"><s:text name="rs"/> <s:property default="N/A" value="%{currentPropertyTax}" /></span>
				</td>
			</tr>
			<tr>
				<td class="greybox"></td>
				<td class="greybox"><s:text name="CurrentTaxDue" /> :</td>
				<td class="greybox">
					<span class="bold"><s:text name="rs"/> <s:property default="N/A" value="%{currentPropertyTaxDue}" /></span>
				</td>
			</tr>
			<tr>
				<td class="greybox"></td>
				<td class="greybox"><s:text name="waterTaxDue"/> :</td>
				<td class="greybox">
					<span class="bold"><s:text name="rs"/> <s:property default="N/A" value="%{currentWaterTaxDue}" /></span>
				</td>
			</tr>
			<tr>
				<td class="bluebox"></td>
				<td class="bluebox"><s:text name="ArrearsDue" /> :</td>
				<td class="bluebox">
					<span class="bold"><s:text name="rs"/> <s:property default="N/A" value="%{arrearPropertyTaxDue}" /></span>
				</td>
			</tr>
			<tr>
				<td colspan="3" align="center" style="padding:30">
					<input type="button" value="Close" class="button" align="center" onclick="window.close()" />
				</td>
			</tr>
		</table>
		<div align="center" class="mandatory">
			<s:property value="%{taxDueErrorMsg}"/>
		</div>
	</div>
</body>
</html>