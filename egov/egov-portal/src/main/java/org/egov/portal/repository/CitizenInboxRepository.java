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
package org.egov.portal.repository;

import java.util.List;

import org.egov.portal.entity.CitizenInbox;
import org.egov.portal.entity.enums.MessageType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CitizenInboxRepository extends JpaRepository<CitizenInbox, Long> {

    @Query("select count(*) from CitizenInbox ci where ci.assignedToCitizen.id=:citizenUserId and ci.read is false")
    Integer findUnreadMessagesCount(@Param("citizenUserId") Long citizenUserId);

    @Query("select ci from CitizenInbox ci where ci.assignedToCitizen.id=:citizenUserId order by ci.createdDate desc")
    List<CitizenInbox> findAllInboxMessage(@Param("citizenUserId") Long citizenUserId);

    @Query("select ci from CitizenInbox ci where ci.messageType=:messageType and ci.assignedToCitizen.id=:citizenUserId order by ci.createdDate desc")
    List<CitizenInbox> findAllInboxMessageByType(@Param("messageType") MessageType messageType,
            @Param("citizenUserId") Long citizenUserId);

    @Query("select ci from CitizenInbox ci where ci.messageType=:messageType and ci.createdBy.id=:citizenUserId and ci.id in (select max(ci1.id) from CitizenInbox ci1 where ci1.messageType=:messageType and ci1.createdBy.id=:citizenUserId group by ci1.identifier) order by ci.createdDate desc")
    List<CitizenInbox> findMyAccountMessages(@Param("messageType") MessageType messageType,
            @Param("citizenUserId") Long citizenUserId);
}
