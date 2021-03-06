<!--
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
  -->
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td class="greybox">&nbsp;</td>
		<td class="greybox" width="20%"><strong><s:text
					name="subCodeFor" /><span class="mandatory"></span></strong></td>
		<td class="greybox"><s:select list="dropdownData.userDefCodeList"
				id="accEntity.accountdetailtype.id" listKey="id" listValue="name"
				name="accEntity.accountdetailtype.id" headerKey=""
				headerValue="---- Choose ----"
				value="accEntity.accountdetailtype.id"></s:select></td>
		<td class="greybox" colspan="2">&nbsp;</td>
	</tr>
	<tr>
		<td class="bluebox">&nbsp;</td>
		<td class="bluebox" width="20%"><strong><s:text
					name="userDefCode.code" /><span class="mandatory"></span></strong></td>
		<td class="bluebox"><s:textfield id="code" name="code"
				value="%{code}" /></td>
		<td class="bluebox" width="20%"><strong><s:text
					name="userDefCode.name" /><span class="mandatory"></span></strong></td>
		<td class="bluebox"><s:textfield id="name" name="name"
				value="%{name}" /></td>
	</tr>
	<tr>
		<td class="greybox">&nbsp;</td>
		<td class="greybox" width="20%"><strong><s:text
					name="userDefCode.Desc" /></strong></td>
		<td class="greybox"><s:textarea name="narration" id="narration"
				rows="3" cols="60" /></td>
		<td class="greybox" width="20%"><strong><s:text
					name="userDefCode.active" /></strong></td>
		<td class="greybox"><s:checkbox name="isactive" id="isactive" />
		</td>
	</tr>
</table>
