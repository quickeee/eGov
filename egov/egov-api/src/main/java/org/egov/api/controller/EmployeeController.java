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
package org.egov.api.controller;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.egov.infra.workflow.inbox.InboxRenderService.RENDER_Y;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.egov.api.adapter.UserAdapter;
import org.egov.api.controller.core.ApiController;
import org.egov.api.controller.core.ApiResponse;
import org.egov.api.controller.core.ApiUrl;
import org.egov.api.model.InboxItem;
import org.egov.eis.entity.Employee;
import org.egov.eis.service.EmployeeService;
import org.egov.eis.service.PositionMasterService;
import org.egov.infra.security.utils.SecurityUtils;
import org.egov.infra.workflow.entity.State;
import org.egov.infra.workflow.entity.State.StateStatus;
import org.egov.infra.workflow.entity.StateAware;
import org.egov.infra.workflow.entity.WorkflowTypes;
import org.egov.infra.workflow.inbox.InboxRenderServiceDeligate;
import org.egov.infstr.services.PersistenceService;
import org.egov.pgr.entity.Complaint;
import org.egov.pgr.service.ComplaintService;
import org.hibernate.FetchMode;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/v1.0")
public class EmployeeController extends ApiController {

    private static final Logger LOGGER = Logger.getLogger(EmployeeController.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormat.forPattern("dd/MM/yyyy hh:mm a");

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private PersistenceService<State, Long> statePersistenceService;

    @Autowired
    private PersistenceService<WorkflowTypes, Long> workflowTypePersistenceService;

    @Autowired
    private PositionMasterService posMasterService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private SecurityUtils securityUtils;

    @Autowired
    InboxRenderServiceDeligate<StateAware> inboxRenderServiceDelegate;
    @Autowired
    private ComplaintService complaintService;

    @RequestMapping(value = ApiUrl.EMPLOYEE_INBOX_LIST_WFT_COUNT, method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> getWorkFlowTypesWithItemsCount(final HttpServletRequest request) {
        final ApiResponse res = ApiResponse.newInstance();
        try {
            final List<Long> ownerpostitions = new ArrayList<Long>();
            ownerpostitions.add(posMasterService.getPositionByUserId(securityUtils.getCurrentUser().getId()).getId());

            return res.setDataAdapter(new UserAdapter())
                    .success(getWorkflowTypesWithCount(securityUtils.getCurrentUser().getId(), ownerpostitions));
        } catch (final Exception ex) {
            LOGGER.error("EGOV-API ERROR ", ex);
            return res.error(getMessage("server.error"));
        }
    }

    @RequestMapping(value = ApiUrl.EMPLOYEE_INBOX_LIST_FILTER_BY_WFT, method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> getInboxListByWorkFlowType(@PathVariable final String workFlowType,
            @PathVariable final int resultsFrom, @PathVariable final int resultsTo) {
        final ApiResponse res = ApiResponse.newInstance();
        try {
            final List<Long> ownerpostitions = new ArrayList<Long>();
            ownerpostitions.add(posMasterService.getPositionByUserId(securityUtils.getCurrentUser().getId()).getId());
            return res.setDataAdapter(new UserAdapter())
                    .success(createInboxData(getWorkflowItemsByUserAndWFType(securityUtils.getCurrentUser().getId(),
                            ownerpostitions, workFlowType, resultsFrom, resultsTo)));
        } catch (final Exception ex) {
            LOGGER.error("EGOV-API ERROR ", ex);
            return res.error(getMessage("server.error"));
        }
    }

    // --------------------------------------------------------------------------------//
    /**
     * Clear the session
     *
     * @param request
     * @return
     */
    @RequestMapping(value = ApiUrl.EMPLOYEE_LOGOUT, method = RequestMethod.POST)
    public ResponseEntity<String> logout(final HttpServletRequest request, final OAuth2Authentication authentication) {
        try {
            final OAuth2AccessToken token = tokenStore.getAccessToken(authentication);
            if (token == null)
                return ApiResponse.newInstance().error(getMessage("msg.logout.unknown"));

            tokenStore.removeAccessToken(token);
            return ApiResponse.newInstance().success("", getMessage("msg.logout.success"));
        } catch (final Exception ex) {
            LOGGER.error("EGOV-API ERROR ", ex);
            return ApiResponse.newInstance().error(getMessage("server.error"));
        }
    }

    /* DATA RELATED FUCNTIONS */

    private List<InboxItem> createInboxData(final List<StateAware> inboxStates) {
        final List<InboxItem> inboxItems = new LinkedList<>();
        inboxStates.sort(byCreatedDate());
        for (final StateAware stateAware : inboxStates) {
            final State state = stateAware.getCurrentState();
            final WorkflowTypes workflowTypes = getWorkflowType(stateAware.getStateType());
            final InboxItem inboxItem = new InboxItem();

            final Complaint complaint = complaintService.getComplaintById(stateAware.getId());
            if (complaint != null) {
                inboxItem.setRefNum(complaint.getCrn());
                inboxItem.setTask(isBlank(state.getNatureOfTask()) ? workflowTypes.getDisplayName() : state.getNatureOfTask());
                inboxItem.setRefDate(DATE_FORMATTER.print(new DateTime(complaint.getCreatedDate())));
                inboxItem.setCitizenName(complaint.getComplainant() != null ? complaint.getComplainant().getName() : "");
                inboxItem.setSender(state.getSenderName());
                inboxItem.setResolutionDate(DATE_FORMATTER.print(complaint.getEscalationDate()));
                inboxItem.setCitizenPhoneno(complaint.getComplainant() != null ? complaint.getComplainant().getMobile() : "");
                inboxItem.setCitizenAddress(complaint.getComplainant() != null ? complaint.getComplainant().getAddress() : "");
                if (complaint.getChildLocation() == null)
                    inboxItem.setLocation(complaint.getLocation() != null ? complaint.getLocation().getName() : "");
                else
                    inboxItem.setLocation(
                            complaint.getLocation().getName() + "-" + complaint.getChildLocation().getName());
                final Employee employee = employeeService.getEmployeeForPositionAndDate(state.getOwnerPosition().getId(),
                        new Date());
                if (employee != null)
                    inboxItem.setSenderPhoneno(employee.getMobileNumber() != null ? employee.getMobileNumber() : "");
                final String nextAction = inboxRenderServiceDelegate.getNextAction(state);
                inboxItem.setStatus(state.getValue() + (isBlank(nextAction) ? EMPTY : " - " + nextAction));
                inboxItem.setItemDetails(isBlank(stateAware.getStateDetails()) ? EMPTY : stateAware.getStateDetails());
                inboxItem.setLink(workflowTypes.getLink().replace(":ID", stateAware.myLinkId()));
                inboxItems.add(inboxItem);
            }
        }
        return inboxItems;
    }

    private Comparator<? super StateAware> byCreatedDate() {
        return (stateAware_1, stateAware_2) -> {
            int returnVal = 1;
            if (stateAware_1 == null)
                returnVal = stateAware_2 == null ? 0 : -1;
            else if (stateAware_2 == null)
                returnVal = 1;
            else {
                final Date first_date = stateAware_1.getState().getCreatedDate();
                final Date second_date = stateAware_2.getState().getCreatedDate();
                if (first_date.after(second_date))
                    returnVal = -1;
                else if (first_date.equals(second_date))
                    returnVal = 0;
            }
            return returnVal;
        };
    }

    @SuppressWarnings("unchecked")
    public List<StateAware> getWorkflowItemsByUserAndWFType(final Long userId, final List<Long> owners, final String workFlowType,
            final int resultsFrom, final int resultsTo) throws HibernateException, ClassNotFoundException {
        return statePersistenceService.getSession().createCriteria(Class.forName(getWorkflowType(workFlowType).getTypeFQN()))
                .setFirstResult(resultsFrom)
                .setMaxResults(resultsTo)
                .setFetchMode("state", FetchMode.JOIN).createAlias("state", "state")
                .setFlushMode(FlushMode.MANUAL).setReadOnly(true).setCacheable(true)
                .add(Restrictions.eq("state.type", workFlowType))
                .add(Restrictions.in("state.ownerPosition.id", owners))
                .add(Restrictions.ne("state.status", StateStatus.ENDED))
                .add(Restrictions.not(Restrictions.conjunction().add(Restrictions.eq("state.status", StateStatus.STARTED))
                        .add(Restrictions.eq("createdBy.id", userId))))
                .addOrder(Order.desc("state.createdDate"))
                .list();

    }

    @SuppressWarnings("unchecked")
    public Number getWorkflowItemsCountByWFType(final Long userId, final List<Long> owners, final String workFlowType)
            throws HibernateException, ClassNotFoundException {
        return (Number) statePersistenceService.getSession()
                .createCriteria(Class.forName(getWorkflowType(workFlowType).getTypeFQN()))
                .setFetchMode("state", FetchMode.JOIN).createAlias("state", "state")
                .setFlushMode(FlushMode.MANUAL).setReadOnly(true).setCacheable(true)
                .setProjection(Projections.rowCount())
                .add(Restrictions.eq("state.type", workFlowType))
                .add(Restrictions.in("state.ownerPosition.id", owners))
                .add(Restrictions.ne("state.status", StateStatus.ENDED))
                .add(Restrictions.not(Restrictions.conjunction().add(Restrictions.eq("state.status", StateStatus.STARTED))
                        .add(Restrictions.eq("createdBy.id", userId))))
                .uniqueResult();
    }

    public List<HashMap<String, Object>> getWorkflowTypesWithCount(final Long userId, final List<Long> ownerPostitions)
            throws HibernateException, ClassNotFoundException {

        final List<HashMap<String, Object>> workFlowTypesWithItemsCount = new ArrayList<HashMap<String, Object>>();
        final Query query = workflowTypePersistenceService.getSession().createQuery(
                "select type, count(type) from State  where ownerPosition.id in (:ownerPositions) and status != :statusEnded and status != :statusStarted and createdBy.id != :userId group by type");
        query.setParameterList("ownerPositions", ownerPostitions);
        query.setParameter("statusEnded", StateStatus.ENDED);
        query.setParameter("statusStarted", StateStatus.STARTED);
        query.setParameter("userId", userId);

        final List<Object[]> result = query.list();
        for (final Object[] rowObj : result) {
            final Long wftitemscount = (Long) getWorkflowItemsCountByWFType(userId, ownerPostitions, String.valueOf(rowObj[0]));
            if (wftitemscount > 0) {
                final HashMap<String, Object> workFlowType = new HashMap<String, Object>();
                workFlowType.put("workflowtype", rowObj[0]);
                workFlowType.put("inboxlistcount", wftitemscount);
                workFlowType.put("workflowtypename", getWorkflowType(String.valueOf(rowObj[0])).getDisplayName());
                workFlowTypesWithItemsCount.add(workFlowType);
            }
        }
        return workFlowTypesWithItemsCount;
    }

    @Transactional(readOnly = true)
    public WorkflowTypes getWorkflowType(final String wfType) {
        final WorkflowTypes workflowType = (WorkflowTypes) workflowTypePersistenceService.getSession()
                .createCriteria(WorkflowTypes.class)
                .add(Restrictions.eq("renderYN", RENDER_Y)).add(Restrictions.eq("type", wfType))
                .setReadOnly(true).uniqueResult();
        return workflowType;
    }

}
