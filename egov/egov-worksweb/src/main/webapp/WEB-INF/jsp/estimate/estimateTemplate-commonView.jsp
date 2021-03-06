<!-- -------------------------------------------------------------------------------
# eGov suite of products aim to improve the internal efficiency,transparency,
#    accountability and the service delivery of the government  organizations.
# 
#     Copyright (C) <2015>  eGovernments Foundation
# 
#     The updated version of eGov suite of products as by eGovernments Foundation
#     is available at http://www.egovernments.org
# 
#     This program is free software: you can redistribute it and/or modify
#     it under the terms of the GNU General Public License as published by
#     the Free Software Foundation, either version 3 of the License, or
#     any later version.
# 
#     This program is distributed in the hope that it will be useful,
#     but WITHOUT ANY WARRANTY; without even the implied warranty of
#     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#     GNU General Public License for more details.
# 
#     You should have received a copy of the GNU General Public License
#     along with this program. If not, see http://www.gnu.org/licenses/ or
#     http://www.gnu.org/licenses/gpl.html .
# 
#     In addition to the terms of the GPL license to be adhered to in using this
#     program, the following additional terms are to be complied with:
# 
# 	1) All versions of this program, verbatim or modified must carry this
# 	   Legal Notice.
# 
# 	2) Any misrepresentation of the origin of the material is prohibited. It
# 	   is required that all modified versions of this material be marked in
# 	   reasonable ways as different from the original version.
# 
# 	3) This license does not grant any rights to any user of the program
# 	   with regards to rights under trademark law for use of the trade names
# 	   or trademarks of eGovernments Foundation.
# 
#   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
#------------------------------------------------------------------------------- -->
<div class="row">
	<div class="col-md-12">
		<div class="panel panel-primary" data-collapsed="0">
			<div class="panel-heading"></div>
			<div class="panel-body">
				<div class="row add-border">
					<div class="col-xs-3 add-margin">
						<s:text name="estimate.template.code" />
					</div>
					<div class="col-xs-3 add-margin view-content">
						<s:property value="%{code}" />
					</div>
					<div class="col-xs-3 add-margin">
						<s:text name="estimate.template.name" />
					</div>
					<div class="col-xs-3 add-margin view-content">
						<s:property value="%{name}" />
					</div>
					<div class="col-xs-3 add-margin">
						<s:text name="estimate.template.description" />
					</div>
					<div class="col-xs-3 add-margin view-content">
						<s:property value="%{description}" />
					</div>
					<div class="col-xs-3 add-margin">
						<s:text name="estimate.template.type" />
					</div>
					<div class="col-xs-3 add-margin view-content">
						<s:property value="%{workType.code}" />
					</div>
					<div class="panel panel-primary" data-collapsed="0">
						<div class="panel-heading">
							<div class="panel-title">
								<s:text name="page.title.estimate.SOR" />
							</div>
						</div>
						<div class="panel-body">
							<table class="table table-bordered">
								<thead>
									<tr>
										<th><s:text name="column.title.SLNo" /></th>
										<th><s:text name="estimate.template.code" /></th>
										<th><s:text name="estimate.template.description" /></th>
										<th><s:text name="column.title.UOM" /></th>
									</tr>
								</thead>
								<tbody>
									<s:iterator id="soriterator" value="SORActivities" status="row_status">
										<tr>
											<td><s:property value="#row_status.count" /></td>
											<td><s:property value="%{schedule.code}" /></td>
											<td><s:property value="%{schedule.descriptionJS}" /></td>
											<td><s:property value="%{schedule.uom.uom}" /></td>
										</tr>
									</s:iterator>
								</tbody>

							</table>

						</div>
						<div class="panel-heading">
							<div class="panel-title">
								<s:text name="page.title.estimate.NonSOR" />
							</div>
						</div>
						<div class="panel-body">
							<table class="table table-bordered">
								<thead>
									<tr>
										<th><s:text name="column.title.SLNo" /></th>
										<th><s:text name="estimate.template.description" /></th>
										<th><s:text name="column.title.UOM" /></th>
										<th><s:text name="column.title.unitrate" /></th>
									</tr>
								</thead>
								<tbody>
									<s:iterator id="nonsoriterator" value="NonSORActivities" status="row_status">
										<tr>
											<td><s:property value="#row_status.count" /></td>
											<td><s:property value="%{nonSor.descriptionJS}" /></td>
											<td><s:property value="%{nonSor.uom.id}" /></td>
											<td><s:property value="%{rate}" /></td>
										</tr>
									</s:iterator>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>