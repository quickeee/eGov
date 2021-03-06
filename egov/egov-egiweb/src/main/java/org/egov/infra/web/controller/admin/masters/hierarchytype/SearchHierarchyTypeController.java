/*
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
 *         1) All versions of this program, verbatim or modified must carry this
 *            Legal Notice.
 *
 *         2) Any misrepresentation of the origin of the material is prohibited. It
 *            is required that all modified versions of this material be marked in
 *            reasonable ways as different from the original version.
 *
 *         3) This license does not grant any rights to any user of the program
 *            with regards to rights under trademark law for use of the trade names
 *            or trademarks of eGovernments Foundation.
 *
 *   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */
package org.egov.infra.web.controller.admin.masters.hierarchytype;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.egov.infra.admin.master.entity.HierarchyType;
import org.egov.infra.admin.master.service.HierarchyTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/hierarchytype")
public class SearchHierarchyTypeController {

    private static final String REQUEST_MAP_VIEW = "/view";
    private static final String REQUEST_MAP_UPDATE = "/update";

    private final HierarchyTypeService hierarchyTypeService;

    @Autowired
    public SearchHierarchyTypeController(final HierarchyTypeService hierarchyTypeService) {
        this.hierarchyTypeService = hierarchyTypeService;
    }

    @ModelAttribute
    public HierarchyType hierarchyTypeModel() {
        return new HierarchyType();
    }

    @ModelAttribute(value = "hierarchyTypes")
    public List<HierarchyType> listHierarchyTypes() {
        return hierarchyTypeService.getAllHierarchyTypes();
    }

    @RequestMapping(value = { REQUEST_MAP_VIEW, REQUEST_MAP_UPDATE }, method = RequestMethod.GET)
    public String showHierarchyTypes(final Model model) {
        return "hierarchyType-list";
    }

    @RequestMapping(value = { REQUEST_MAP_VIEW, REQUEST_MAP_UPDATE }, method = RequestMethod.POST)
    public String search(@ModelAttribute final HierarchyType hierarchyType, final BindingResult errors,
            final RedirectAttributes redirectAttrs, final HttpServletRequest request) {
        if (errors.hasErrors())
            return "boundaryType-form";
        final String requestURI = request.getRequestURI();
        String redirectURI = "";
        if (requestURI.contains("view"))
            redirectURI = "redirect:/hierarchytype/view/" + hierarchyType.getName();
        else if (requestURI.contains("update"))
            redirectURI = "redirect:/hierarchytype/update/" + hierarchyType.getName();
        return redirectURI;
    }
}