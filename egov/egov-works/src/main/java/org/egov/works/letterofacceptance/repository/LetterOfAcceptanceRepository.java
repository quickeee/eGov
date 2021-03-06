/**
 * eGov suite of products aim to improve the internal efficiency,transparency,
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
 */
package org.egov.works.letterofacceptance.repository;

import java.util.List;

import org.egov.works.models.workorder.WorkOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LetterOfAcceptanceRepository extends JpaRepository<WorkOrder, Long> {

    WorkOrder findById(final Long id);

    List<WorkOrder> findByWorkOrderNumberContainingIgnoreCase(final String workOrderNumber);

    List<WorkOrder> findByEstimateNumberContainingIgnoreCase(final String name);

    List<WorkOrder> findByEstimateNumberAndEgwStatus_codeEquals(final String estimateNumber, final String statusCode);

    List<WorkOrder> findByWorkOrderNumberContainingIgnoreCaseAndEgwStatus_codeEquals(final String workOrderNumber,
            final String statusCode);

    List<WorkOrder> findByEstimateNumberContainingIgnoreCaseAndEgwStatus_codeEquals(final String estimateNumber,
            final String statusCode);

    @Query("select distinct(wo.contractor.name) from WorkOrder as wo where wo.contractor.name like :name or wo.contractor.code like :name")
    List<String> findDistinctContractorByContractor_codeAndNameContainingIgnoreCase(@Param("name") final String name);

    WorkOrder findByWorkOrderNumberAndEgwStatus_codeNotLike(final String workOrderNumber, final String statusCode);

    WorkOrder findByEstimateNumberAndEgwStatus_codeNotLike(final String estimateNumber, final String statusCode);

    WorkOrder findByWorkOrderNumberAndEgwStatus_codeEquals(final String workOrderNumber, final String statusCode);

    @Query("select distinct(wo.workOrderNumber) from WorkOrder as wo where upper(wo.workOrderNumber) like upper(:workOrderNumber) and wo.egwStatus.code = :workOrderStatus and not exists (select distinct(cbr.workOrder) from ContractorBillRegister as cbr where wo.id = cbr.workOrder.id and upper(cbr.billstatus) != :status and cbr.billtype = :billtype)")
    List<String> findWorkOrderNumberForContractorBill(@Param("workOrderNumber") String workOrderNumber,
            @Param("workOrderStatus") String workOrderStatus, @Param("status") String status, @Param("billtype") String billtype);

    @Query("select distinct(wo.estimateNumber) from WorkOrder as wo where upper(wo.estimateNumber) like upper(:estimateNumber) and wo.egwStatus.code = :workOrderStatus and not exists (select distinct(cbr.workOrder) from ContractorBillRegister as cbr where wo.id = cbr.workOrder.id and upper(cbr.billstatus) != :status and cbr.billtype = :billtype)")
    List<String> findEstimateNumberForContractorBill(@Param("estimateNumber") String estimateNumber,
            @Param("workOrderStatus") String workOrderStatus, @Param("status") String status, @Param("billtype") String billtype);

    @Query("select distinct(wo.contractor.name) from WorkOrder as wo where upper(wo.contractor.name) like upper(:contractorname) and wo.egwStatus.code = :workOrderStatus and not exists (select distinct(cbr.workOrder) from ContractorBillRegister as cbr where wo.id = cbr.workOrder.id and upper(cbr.billstatus) != :status and cbr.billtype = :billtype)")
    List<String> findContractorForContractorBill(@Param("contractorname") String contractorname,
            @Param("workOrderStatus") String workOrderStatus, @Param("status") String status, @Param("billtype") String billtype);

    @Query("select distinct(cbr.workOrder.workOrderNumber) from ContractorBillRegister as cbr where upper(cbr.billstatus) != :status and cbr.billtype = :billtype")
    List<String> getDistinctNonCancelledWorkOrderNumbersByBillType(@Param("status") String billstatus,
            @Param("billtype") String finalBill);

    @Query("select distinct(cbr.workOrder.workOrderNumber) from ContractorBillRegister as cbr where cbr.workOrder.id = :workOrderId and upper(cbr.billstatus) not in (:billstatus1,:billstatus2)")
    List<String> getContractorBillInWorkflowForWorkorder(@Param("workOrderId") Long workOrderId,
            @Param("billstatus1") String billstatus1, @Param("billstatus2") String billstatus2);
}
