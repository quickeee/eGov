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
package org.egov.wtms.masters.service;

import java.util.List;

import org.egov.wtms.masters.entity.PipeSize;
import org.egov.wtms.masters.entity.UsageType;
import org.egov.wtms.masters.entity.WaterRatesHeader;
import org.egov.wtms.masters.entity.WaterSource;
import org.egov.wtms.masters.entity.enums.ConnectionType;
import org.egov.wtms.masters.repository.WaterRatesHeaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class WaterRatesHeaderService {

    private final WaterRatesHeaderRepository waterRatesHeaderRepository;

    @Autowired
    public WaterRatesHeaderService(final WaterRatesHeaderRepository waterRatesHeaderRepository) {
        this.waterRatesHeaderRepository = waterRatesHeaderRepository;
    }

    public WaterRatesHeader findBy(final Long waterRatesHeaderId) {
        return waterRatesHeaderRepository.findOne(waterRatesHeaderId);
    }

    @Transactional
    public void createWaterRatesHeader(final WaterRatesHeader waterRatesHeader) {
        waterRatesHeaderRepository.save(waterRatesHeader);
    }

    @Transactional
    public void updateWaterRatesHeader(final WaterRatesHeader waterRatesHeader) {
        waterRatesHeaderRepository.save(waterRatesHeader);
    }

    public List<WaterRatesHeader> findAll() {
        return waterRatesHeaderRepository.findAll(new Sort(Sort.Direction.DESC, "id"));
    }

    public List<WaterRatesHeader> findAllByConnectionType(final ConnectionType connectionType) {
        return waterRatesHeaderRepository.findAllByConnectionType(connectionType);
    }

    public List<WaterRatesHeader> findAllByUsageType(final UsageType usageType) {
        return waterRatesHeaderRepository.findAllByUsageType(usageType);
    }

    public List<WaterRatesHeader> findAllByWaterSource(final WaterSource waterSource) {
        return waterRatesHeaderRepository.findAllByWaterSource(waterSource);
    }

    public WaterRatesHeader load(final Long id) {
        return waterRatesHeaderRepository.getOne(id);
    }

    public WaterRatesHeader findByConnectionTypeAndUsageTypeAndWaterSourceAndPipeSize(
            final ConnectionType connectionType, final UsageType usageType, final WaterSource waterSource,
            final PipeSize pipeSize) {
        return waterRatesHeaderRepository.findByConnectionTypeAndUsageTypeAndWaterSourceAndPipeSize(connectionType,
                usageType, waterSource, pipeSize);
    }

}
