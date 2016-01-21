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
package org.egov.tl.service;

import org.egov.commons.Installment;
import org.egov.demand.model.EgDemandReasonMaster;
import org.egov.eis.entity.Assignment;
import org.egov.infra.admin.master.entity.Module;
import org.egov.infra.admin.master.entity.User;
import org.egov.infstr.services.PersistenceService;
import org.egov.infstr.workflow.WorkFlowMatrix;
import org.egov.pims.commons.Position;
import org.egov.tl.entity.License;
import org.egov.tl.entity.LicenseAppType;
import org.egov.tl.entity.LicenseStatusValues;
import org.egov.tl.entity.MotorMaster;
import org.egov.tl.entity.NatureOfBusiness;
import org.egov.tl.entity.TradeLicense;
import org.egov.tl.entity.WorkflowBean;
import org.egov.tl.entity.transfer.LicenseTransfer;
import org.egov.tl.utils.Constants;
import org.egov.tl.utils.LicenseUtils;
import org.joda.time.DateTime;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static org.egov.tl.utils.Constants.BUTTONAPPROVE;
import static org.egov.tl.utils.Constants.BUTTONREJECT;

public class TradeLicenseService extends AbstractLicenseService<TradeLicense> {

    public TradeLicenseService(final PersistenceService<TradeLicense, Long> licensePersitenceService) {
        super(licensePersitenceService);
    }

    @Override
    protected NatureOfBusiness getNatureOfBusiness() {
        final NatureOfBusiness natureOfBusiness = (NatureOfBusiness) persistenceService
                .find("from org.egov.tl.entity.NatureOfBusiness where   name='Permanent'");
        return natureOfBusiness;
    }

    @Override
    protected Module getModuleName() {
        final Module module = (Module) persistenceService.find(
                "from org.egov.infra.admin.master.entity.Module where parentModule is null and name=?", "Trade License");
        return module;
    }

    @Override
    public License additionalOperations(final TradeLicense license, final Set<EgDemandReasonMaster> egDemandReasonMasters, final Installment installment) {
        final TradeLicense tl = (TradeLicense) license;
        final List<MotorMaster> motorMasterList = persistenceService.findAllBy("from org.egov.tl.entity.MotorMaster");
        tl.setMotorMasterList(motorMasterList);
        tl.additionalDemandDetails(egDemandReasonMasters, installment);
        return tl;
    }

    @Transactional
    public void transferLicense(final TradeLicense tl, final LicenseTransfer licenseTransfer) {
        final String runningApplicationNumber = applicationNumberGenerator.generate();
        final String currentApplno = tl.getApplicationNumber();
        final String generatedApplicationNumber = tl.generateApplicationNumber(runningApplicationNumber);
        tl.setApplicationNumber(currentApplno);
        licenseTransfer.setLicense(tl);
        licenseTransfer.setType("TradeLicense");
        tl.setLicenseTransfer(licenseTransfer);
        licenseTransfer.setOldApplicationNumber(generatedApplicationNumber);
    }

    @Transactional
    public void initiateWorkFlowForTransfer(final TradeLicense license, final WorkflowBean workflowBean) {
        /*final Position position = eisCommonsManager.getPositionByUserId(Integer.valueOf(EGOVThreadLocals.getUserId()));
        try {
            tradeLicenseWorkflowService.start(license, position, workflowBean.getComments());
        } catch (final ApplicationRuntimeException e) {
            if (license.getState().getValue().equalsIgnoreCase("END")) {
                license.setState(null);
                persistenceService.persist(license);
                tradeLicenseWorkflowService.start(license, position, workflowBean.getComments());
            } else
                throw e;
        }
        license.getState().setText2(license.getWorkflowIdentityForTransfer());
        final LicenseStatus underWorkflowStatus = (LicenseStatus) persistenceService
                .find("from org.egov.tl.entity.LicenseStatus where code='UWF'");
        license.setStatus(underWorkflowStatus);*/
        processWorkFlowForTransfer(license, workflowBean);
        return;
    }

    @Transactional
    public void processWorkFlowForTransfer(final TradeLicense license, final WorkflowBean workflowBean) {
        /*if (workflowBean.getActionName().equalsIgnoreCase(Constants.BUTTONSAVE)) {
            final Position position = eisCommonsManager.getPositionByUserId(Integer.valueOf(EGOVThreadLocals.getUserId()));
            license.changeState(Constants.WORKFLOW_STATE_TYPE_TRANSFERLICENSE + "NEW", position, workflowBean.getComments());
            license.getState().setText2(license.getWorkflowIdentityForTransfer());
        } else if (workflowBean.getActionName().equalsIgnoreCase(Constants.BUTTONAPPROVE)) {
            Position position = eisCommonsManager.getPositionByUserId(Integer.valueOf(EGOVThreadLocals.getUserId()));
            license.changeState(Constants.WORKFLOW_STATE_TYPE_TRANSFERLICENSE + Constants.WORKFLOW_STATE_APPROVED, position,
                    workflowBean.getComments());
            license.getState().setText2(license.getWorkflowIdentityForTransfer());
            license.acceptTransfer();
            position = eisCommonsManager.getPositionByUserId(license.getCreatedBy().getId());
            license.changeState(Constants.WORKFLOW_STATE_TYPE_TRANSFERLICENSE + Constants.WORKFLOW_STATE_GENERATECERTIFICATE,
                    position, workflowBean.getComments());
            license.getState().setText2(license.getWorkflowIdentityForTransfer());
            license.getLicenseTransfer().setApproved(true);
        } else if (workflowBean.getActionName().equalsIgnoreCase(Constants.BUTTONFORWARD)) {
            final Position nextPosition = eisCommonsManager.getPositionByUserId(workflowBean.getApproverUserId());
            license.changeState(Constants.WORKFLOW_STATE_TYPE_TRANSFERLICENSE + Constants.WORKFLOW_STATE_FORWARDED, nextPosition,
                    workflowBean.getComments());
            license.getState().setText2(license.getWorkflowIdentityForTransfer());
        } else if (workflowBean.getActionName().equalsIgnoreCase(Constants.BUTTONREJECT)) {
            if (license.getState().getValue().contains(Constants.WORKFLOW_STATE_REJECTED)) {
                final Position position = eisCommonsManager.getPositionByUserId(Integer.valueOf(EGOVThreadLocals.getUserId()));
                workflowService().end(license, position);
                license.getLicenseTransfer().setApproved(false);
                license.getState().setText2(license.getWorkflowIdentityForTransfer());
            } else {
                final Position position = eisCommonsManager.getPositionByUserId(license.getCreatedBy().getId());
                license.changeState(Constants.WORKFLOW_STATE_TYPE_TRANSFERLICENSE + Constants.WORKFLOW_STATE_REJECTED, position,
                        workflowBean.getComments());
                license.getState().setText2(license.getWorkflowIdentityForTransfer());
            }
        } else if (workflowBean.getActionName().equalsIgnoreCase(Constants.BUTTONGENERATEDCERTIFICATE)) {
            final Position position = eisCommonsManager.getPositionByUserId(Integer.valueOf(EGOVThreadLocals.getUserId()));
            workflowService().end(license, position);
            final LicenseStatus activeStatus = (LicenseStatus) persistenceService
                    .find("from org.egov.tl.entity.LicenseStatus where code='ACT'");
            license.setStatus(activeStatus);
        }*/
        final DateTime currentDate = new DateTime();
        final User user = securityUtils.getCurrentUser();
        final Assignment userAssignment = assignmentService.getPrimaryAssignmentForUser(user.getId());
        Position pos = null;
        Assignment wfInitiator = null;

        if (null != license.getId())
            wfInitiator = getWorkflowInitiator(license);

        if (BUTTONREJECT.equalsIgnoreCase(workflowBean.getWorkFlowAction())) {
            if (wfInitiator.equals(userAssignment)) {
                license.transition(true).end().withSenderName(user.getName()).withComments(workflowBean.getApproverComments())
                        .withDateInfo(currentDate.toDate());
            } else {
                final String stateValue = license.getCurrentState().getValue();
                license.transition(true).withSenderName(user.getName()).withComments(workflowBean.getApproverComments())
                        .withStateValue(stateValue).withDateInfo(currentDate.toDate())
                        .withOwner(wfInitiator.getPosition()).withNextAction("Assistant Health Officer approval pending");
            }

        } else {
            if (null != workflowBean.getApproverPositionId() && workflowBean.getApproverPositionId() != -1)
                pos = (Position) persistenceService.find("from Position where id=?", workflowBean.getApproverPositionId());
            else if (BUTTONAPPROVE.equalsIgnoreCase(workflowBean.getWorkFlowAction()))
                pos = wfInitiator.getPosition();
            if (null == license.getState()) {
                final WorkFlowMatrix wfmatrix = transferWorkflowService.getWfMatrix(license.getStateType(), null,
                        null, null, workflowBean.getCurrentState(), null);
                license.transition().start().withSenderName(user.getName()).withComments(workflowBean.getApproverComments())
                        .withStateValue(wfmatrix.getNextState()).withDateInfo(currentDate.toDate()).withOwner(pos)
                        .withNextAction(wfmatrix.getNextAction());
            } else if (license.getCurrentState().getNextAction().equalsIgnoreCase("END"))
                license.transition(true).end().withSenderName(user.getName()).withComments(workflowBean.getApproverComments())
                        .withDateInfo(currentDate.toDate());
            else {
                final WorkFlowMatrix wfmatrix = transferWorkflowService.getWfMatrix(license.getStateType(), null,
                        null, null, license.getCurrentState().getValue(), null);
                license.transition(true).withSenderName(user.getName()).withComments(workflowBean.getApproverComments())
                        .withStateValue(wfmatrix.getNextState()).withDateInfo(currentDate.toDate()).withOwner(pos)
                        .withNextAction(wfmatrix.getNextAction());
            }
        }
    }

    protected Assignment getWorkflowInitiator(final TradeLicense license) {
        Assignment wfInitiator = assignmentService.getPrimaryAssignmentForUser(license.getCreatedBy().getId());
        return wfInitiator;
    }

    @Override
    protected LicenseAppType getLicenseApplicationTypeForRenew() {
        final LicenseAppType appType = (LicenseAppType) persistenceService
                .find("from org.egov.tl.entity.LicenseAppType where   name='Renewal'");
        return appType;
    }

    @Override
    protected LicenseAppType getLicenseApplicationType() {
        final LicenseAppType appType = (LicenseAppType) persistenceService
                .find("from org.egov.tl.entity.LicenseAppType where   name='New'");
        return appType;
    }

    @Transactional
    public void revokeSuspendedLicense(final TradeLicense license, final LicenseUtils licenseUtils,
                                       final LicenseStatusValues licenseStatusValues) {
        license.setActive(false);
        license.setStatus(licenseUtils.getLicenseStatusbyCode("ACT"));
        licenseStatusValues.setLicense(license);
        licenseStatusValues.setLicenseStatus(licenseUtils.getLicenseStatusbyCode("ACT"));
        licenseStatusValues.setActive(true);
        licenseStatusValues.setReason(Integer.valueOf(Constants.REASON_REVOKESUSPENTION_NO_4));
        license.addLicenseStatusValuesSet(licenseStatusValues);
        licensePersitenceService.update(license);
        return;
    }

    public List getHotelCategoriesForTrade() {
        final List subCategory = persistenceService
                .findAllBy("select id from org.egov.tl.entity.LicenseSubCategory where upper(name) like '%HOTEL%' and licenseType.id= (select id from org.egov.tl.entity.LicenseType where name='TradeLicense')");
        return subCategory;
    }

}