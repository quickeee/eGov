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
package org.egov.wtms.masters.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.egov.infra.persistence.entity.AbstractPersistable;

@Entity
@Table(name = "egwtr_water_rates_details")
@SequenceGenerator(name = WaterRatesDetails.SEQ_WATERRATESDETAILS, sequenceName = WaterRatesDetails.SEQ_WATERRATESDETAILS, allocationSize = 1)
public class WaterRatesDetails extends AbstractPersistable<Long> {

    private static final long serialVersionUID = -8237417567777811811L;
    public static final String SEQ_WATERRATESDETAILS = "SEQ_EGWTR_WATER_RATES_DETAILS";

    @Id
    @GeneratedValue(generator = SEQ_WATERRATESDETAILS, strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "waterratesheader", nullable = false)
    private WaterRatesHeader waterRatesHeader;

    private Long startingUnits;

    private Long endingUnits;

    private Double unitRate;

    private Double minimumRate;

    private Double monthlyRate;

    @NotNull
    @Temporal(value = TemporalType.DATE)
    private Date fromDate;

    @Temporal(value = TemporalType.DATE)
    private Date toDate;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(final Long id) {
        this.id = id;
    }

    public WaterRatesHeader getWaterRatesHeader() {
        return waterRatesHeader;
    }

    public void setWaterRatesHeader(final WaterRatesHeader waterRatesHeader) {
        this.waterRatesHeader = waterRatesHeader;
    }

    public Long getStartingUnits() {
        return startingUnits;
    }

    public void setStartingUnits(final Long startingUnits) {
        this.startingUnits = startingUnits;
    }

    public Long getEndingUnits() {
        return endingUnits;
    }

    public void setEndingUnits(final Long endingUnits) {
        this.endingUnits = endingUnits;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(final Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(final Date toDate) {
        this.toDate = toDate;
    }

    public Double getUnitRate() {
        return unitRate;
    }

    public void setUnitRate(final Double unitRate) {
        this.unitRate = unitRate;
    }

    public Double getMinimumRate() {
        return minimumRate;
    }

    public void setMinimumRate(final Double minimumRate) {
        this.minimumRate = minimumRate;
    }

    public Double getMonthlyRate() {
        return monthlyRate;
    }

    public void setMonthlyRate(final Double monthlyRate) {
        this.monthlyRate = monthlyRate;
    }

}