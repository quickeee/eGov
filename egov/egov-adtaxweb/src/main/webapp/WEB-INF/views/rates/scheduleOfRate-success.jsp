<!-- #-------------------------------------------------------------------------------
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
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<div class="row">
	<div class="col-md-12">
		<form:form  id="agencysuccess" method ="post" class="form-horizontal form-groups-bordered" modelAttribute="rate" >
			<c:if test="${not empty message}">
				<div class="alert alert-success" role="alert">
					<spring:message code="${message}" />
				</div>
			</c:if>
			<div class="panel panel-primary" data-collapsed="0">
			<div class="panel-heading ">
				<div class="panel-title" >
					<strong><spring:message code="title.scheduleofrates" /></strong>
				</div>
			</div>
				<div class="form-group row add-border">
						<div class="col-md-3 col-xs-6 add-margin"> <spring:message
								code="lbl.category.name" /></div>
						<div class="col-md-3 col-xs-6 add-margin view-content">
							<input type=hidden id="mode" value="${mode}">
							<c:out value="${rate.category.name}"></c:out>
						</div>
						<div class="col-md-3 col-xs-6 add-margin"><spring:message
										code="lbl.subcategory.name" /></div>
						<div class="col-md-3 col-xs-6 add-margin view-content">
							<c:out value="${rate.subCategory.description}"></c:out>
						</div>
						<div class="col-md-3 col-xs-6 add-margin"> <spring:message
								code="lbl.unitofmeasure.name" /></div>
						<div class="col-md-3 col-xs-6 add-margin view-content">
							  <form:hidden path="id" id="id" value="${rate.id}"/>
							<c:out value="${rate.unitofmeasure.description}"></c:out>
						</div>
						<div class="col-md-3 col-xs-6 add-margin"><spring:message
								code="lbl.rateClass.name" /></div>
						<div class="col-md-3 col-xs-6 add-margin view-content">
									<c:out value="${rate.classtype.description}"></c:out>
						</div>
						<div class="col-md-3 col-xs-6 add-margin"><spring:message
								code="lbl.financial.year" /></div>
						<div class="col-md-3 col-xs-6 add-margin view-content">
									<c:out value="${rate.financialyear.finYearRange}"></c:out>
						</div>
						<div class="col-md-3 col-xs-6 add-margin"><spring:message
								code="lbl.per.unit" /></div>
						<div class="col-md-3 col-xs-6 add-margin view-content">
									<c:out value="${rate.unitrate}"></c:out>
						</div>
						</div>
				</div>
				<table id="schedleOfrateTable" table width="80%" border="0"
					cellpadding="0" cellspacing="0" class="table table-bordered">
					<thead>
						<tr>
							<th><spring:message code="lbl.scheduleorrate.unitfrom" /></th>
							<th><spring:message code="lbl.scheduleorrate.unitto" /></th>
							<th><spring:message code="lbl.scheduleorrate.rate" /></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="contact" items="${rate.advertisementRatesDetails}"
							varStatus="status">
							<tr>
								<td><input type="text" class="form-control is_valid_number"
									id="advertisementRatesDetailsUnitFrom${status.index}"
									value="${contact.unitFrom}" maxlength="10"
									name="advertisementRatesDetails[${status.index}].unitFrom"
									autocomplete="off" required="required" readonly="readonly">
								</td>
								<td><input type="text" class="form-control is_valid_number"
									id="advertisementRatesDetailsUnitTo${status.index}"
									value="${contact.unitTo}" maxlength="10"
									name="advertisementRatesDetails[${status.index}].unitTo"
									autocomplete="off" required="required" readonly="readonly">
								</td>
								<td><input type="text" class="form-control is_valid_number"
									id="advertisementRatesDetailsAmount${status.index}"
									value="${contact.amount}" maxlength="10"
									name="advertisementRatesDetails[${status.index}].amount"
									autocomplete="off" required="required" readonly="readonly">
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<div class="row">
					<div class="text-center">
						<button type="button" class="btn btn-default" data-dismiss="modal"
							onclick="self.close()">
							<spring:message code="lbl.close" />
						</button>
					</div>
				</div>
		</form:form>
	</div>
</div>

