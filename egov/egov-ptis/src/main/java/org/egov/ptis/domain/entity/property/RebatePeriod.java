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
package org.egov.ptis.domain.entity.property;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.egov.commons.Installment;
import org.egov.infra.persistence.entity.AbstractAuditable;
import org.egov.search.domain.Searchable;

@Entity
@Table(name = "EGPT_REBATE_PERIOD")
@Searchable
@SequenceGenerator(name = RebatePeriod.SEQ_REBATE_PERIOD, sequenceName = RebatePeriod.SEQ_REBATE_PERIOD, allocationSize = 1)
public class RebatePeriod extends AbstractAuditable {

    private static final long serialVersionUID = 859229842367886338L;
    public static final String SEQ_REBATE_PERIOD = "SEQ_EGPT_REBATE_PERIOD";

    @Id
    @GeneratedValue(generator = SEQ_REBATE_PERIOD, strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "installment")
    private Installment installment;

    @NotNull
    private Date rebateDate;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(final Long id) {
        this.id = id;
    }

    public Installment getInstallment() {
        return installment;
    }

    public void setInstallment(final Installment installment) {
        this.installment = installment;
    }

    public Date getRebateDate() {
        return rebateDate;
    }

    public void setRebateDate(final Date rebateDate) {
        this.rebateDate = rebateDate;
    }

}
