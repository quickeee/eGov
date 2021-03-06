/*******************************************************************************
 * eGov suite of products aim to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) <2015>  eGovernments Foundation
 *
 *     The updated version of eGov suite of products as by eGovernments Foundation
 *     is available at http://www.egovernments.org
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program. If not, see http://www.gnu.org/licenses/ or
 *     http://www.gnu.org/licenses/gpl.html .
 *
 *     In addition to the terms of the GPL license to be adhered to in using this
 *     program, the following additional terms are to be complied with:
 *
 * 	1) All versions of this program, verbatim or modified must carry this
 * 	   Legal Notice.
 *
 * 	2) Any misrepresentation of the origin of the material is prohibited. It
 * 	   is required that all modified versions of this material be marked in
 * 	   reasonable ways as different from the original version.
 *
 * 	3) This license does not grant any rights to any user of the program
 * 	   with regards to rights under trademark law for use of the trade names
 * 	   or trademarks of eGovernments Foundation.
 *
 *   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 ******************************************************************************/
package org.egov.ptis.web.controller.reports;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.egov.commons.CFinancialYear;
import org.egov.commons.RegionalHeirarchy;
import org.egov.commons.RegionalHeirarchyType;
import org.egov.commons.dao.FinancialYearDAO;
import org.egov.commons.service.RegionalHeirarchyService;
import org.egov.infra.config.properties.ApplicationProperties;
import org.egov.infra.utils.DateUtils;
import org.egov.ptis.domain.entity.property.BillCollectorDailyCollectionReportResult;
import org.egov.ptis.domain.service.report.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.GsonBuilder;

/**
 * @author Pradeep
 */
@Controller
@RequestMapping(value = "/reports")
public class GeneralDialyCollectionReportController {

    private static final String BILL_COLLECTOR_COLL_REPORT_FORM = "bcDailyCollectionReport-form";
    private static final String ULBWISE_COLL_REPORT_FORM = "ulbWiseCollectionReport-form";
    private static final String ULBWISE_DCB_REPORT_FORM = "ulbWiseDcbReport-form";
    private String DISTRICT = "DISTRICT";
    private String CITY = "CITY";
   private  BillCollectorDailyCollectionReportResult bcDailyCollectionReportResult = new BillCollectorDailyCollectionReportResult();

    @Autowired
    private ApplicationProperties applicationProperties;

    @Autowired
    private RegionalHeirarchyService regionalHeirarchyService;

    @Autowired
    private ReportService reportService;

    protected @Autowired FinancialYearDAO financialYearDAO;

    @ModelAttribute("previousFinancialYear")
    public CFinancialYear previousFinancialYear() {
        return financialYearDAO.getPreviousFinancialYearByDate(new Date());
    }

    @ModelAttribute("currentFinancialYear")
    public CFinancialYear currentFinancialYear() {
        return financialYearDAO.getFinancialYearByDate(new Date());
    }

    @ModelAttribute("regions")  
    public List<RegionalHeirarchy> getRegions() {
        return regionalHeirarchyService.getActiveRegionalHeirarchyByRegion(RegionalHeirarchyType.REGION);
    }

    @RequestMapping(value = "/getRegionHeirarchyByType", method = GET, produces = APPLICATION_JSON_VALUE)
    public @ResponseBody List<RegionalHeirarchy> getRegionHeirarchyByType(@RequestParam final String regionName,
            @RequestParam final String type) {
     
        if (type != null && type.equalsIgnoreCase(DISTRICT))
            return regionalHeirarchyService.getActiveChildRegionHeirarchyByPassingParentNameAndType(
                    RegionalHeirarchyType.DISTRICT, regionName);
        else {
          
            if (type != null && type.equalsIgnoreCase(CITY))
                return regionalHeirarchyService.getActiveChildRegionHeirarchyByPassingParentNameAndType(
                        RegionalHeirarchyType.CITY, regionName);
        }
        return null;
    }

    
    @ModelAttribute("bcDailyCollectionReportResult")
    public BillCollectorDailyCollectionReportResult bcDailyCollectionReportResultModel() {
        return bcDailyCollectionReportResult;
    }

    @RequestMapping(value = "/billcollectorDailyCollectionReport-form", method = RequestMethod.GET)
    public String searchForm(final Model model) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        if (bcDailyCollectionReportResult != null)
            bcDailyCollectionReportResult.setGeneratedDate(dateFormat.format(calendar.getTime()));

        return BILL_COLLECTOR_COLL_REPORT_FORM;
    }
    @RequestMapping(value = "/ulbWiseDcbReport-form", method = RequestMethod.GET)
    public String searchUlbWiseDcbForm(final Model model, @RequestParam final String type) { 
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); 
        Calendar calendar = Calendar.getInstance();
        model.addAttribute("typeValue", type);
        calendar.add(Calendar.DATE, -1);
        if (bcDailyCollectionReportResult != null)
            bcDailyCollectionReportResult.setGeneratedDate(dateFormat.format(calendar.getTime()));

        return ULBWISE_DCB_REPORT_FORM;
    }

    
    @RequestMapping(value = "/ulbWiseCollectionReport-form", method = RequestMethod.GET)
    public String searchUlbWiseForm(final Model model) {
        return ULBWISE_COLL_REPORT_FORM;
    }
    
   
    @RequestMapping(value = "/ulbWiseDCBList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody void searchUlbWiseDcb(final HttpServletRequest request, final HttpServletResponse response,BillCollectorDailyCollectionReportResult bcDailyCollectionReportResult,
            final Model model) throws IOException {
        IOUtils.write(
                "{ \"data\":"
                        + new GsonBuilder().setDateFormat(applicationProperties.defaultDatePattern()).create()
                                .toJson(reportService.getUlbWiseDcbCollection(new Date(),bcDailyCollectionReportResult)) + "}",
                response.getWriter()); 
    }
  
    
    @RequestMapping(value = "/ulbWiseCollectionList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody void searchUlbWise(final HttpServletRequest request, final HttpServletResponse response,
            final Model model) throws IOException {
        IOUtils.write(
                "{ \"data\":"
                        + new GsonBuilder().setDateFormat(applicationProperties.defaultDatePattern()).create()
                                .toJson(reportService.getUlbWiseDailyCollection(new Date())) + "}",
                response.getWriter());
    }

    
    @RequestMapping(value = "/billcollectorDailyCollectionReportList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody void search(final HttpServletRequest request, final HttpServletResponse response,BillCollectorDailyCollectionReportResult bcDailyCollectionReportResult,
            final Model model) throws IOException {
        IOUtils.write(
                "{ \"data\":"
                        + new GsonBuilder().setDateFormat(applicationProperties.defaultDatePattern()).create()
                                .toJson(reportService.getBillCollectorWiseDailyCollection(new Date(),bcDailyCollectionReportResult)) + "}",
                response.getWriter());
    }

}
