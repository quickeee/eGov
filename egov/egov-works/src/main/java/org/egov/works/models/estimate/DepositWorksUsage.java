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
package org.egov.works.models.estimate;

import java.math.BigDecimal;
import java.util.Date;

import org.egov.commons.CChartOfAccounts;
import org.egov.commons.CFinancialYear;
import org.egov.infstr.models.BaseModel;
import org.egov.works.models.masters.DepositCode;

public class DepositWorksUsage extends BaseModel {

    private static final long serialVersionUID = 8244011179703999724L;
    private AbstractEstimate abstractEstimate;
    private BigDecimal totalDepositAmount;
    private BigDecimal consumedAmount;
    private BigDecimal releasedAmount;
    private String appropriationNumber;
    private Date appropriationDate;
    private CFinancialYear financialYear;
    private DepositCode depositCode;
    private CChartOfAccounts coa;

    public AbstractEstimate getAbstractEstimate() {
        return abstractEstimate;
    }

    public void setAbstractEstimate(final AbstractEstimate abstractEstimate) {
        this.abstractEstimate = abstractEstimate;
    }

    public BigDecimal getTotalDepositAmount() {
        return totalDepositAmount;
    }

    public void setTotalDepositAmount(final BigDecimal totalDepositAmount) {
        this.totalDepositAmount = totalDepositAmount;
    }

    public BigDecimal getConsumedAmount() {
        return consumedAmount;
    }

    public void setConsumedAmount(final BigDecimal consumedAmount) {
        this.consumedAmount = consumedAmount;
    }

    public BigDecimal getReleasedAmount() {
        return releasedAmount;
    }

    public void setReleasedAmount(final BigDecimal releasedAmount) {
        this.releasedAmount = releasedAmount;
    }

    public String getAppropriationNumber() {
        return appropriationNumber;
    }

    public void setAppropriationNumber(final String appropriationNumber) {
        this.appropriationNumber = appropriationNumber;
    }

    public Date getAppropriationDate() {
        return appropriationDate;
    }

    public void setAppropriationDate(final Date appropriationDate) {
        this.appropriationDate = appropriationDate;
    }

    public CFinancialYear getFinancialYear() {
        return financialYear;
    }

    public void setFinancialYear(final CFinancialYear financialYear) {
        this.financialYear = financialYear;
    }

    public DepositCode getDepositCode() {
        return depositCode;
    }

    public void setDepositCode(final DepositCode depositCode) {
        this.depositCode = depositCode;
    }

    public CChartOfAccounts getCoa() {
        return coa;
    }

    public void setCoa(final CChartOfAccounts coa) {
        this.coa = coa;
    }

}
