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
package org.egov.commons.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.egov.commons.CVoucherHeader;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class VoucherHeaderHibernateDAO  implements VoucherHeaderDAO {
    @Transactional
    public CVoucherHeader update(final CVoucherHeader entity) {
        getCurrentSession().update(entity);
        return entity;
    }

    @Transactional
    public CVoucherHeader create(final CVoucherHeader entity) {
        getCurrentSession().persist(entity);
        return entity;
    }

    @Transactional
    public void delete(CVoucherHeader entity) {
        getCurrentSession().delete(entity);
    }
@Override
    public CVoucherHeader findById(Number id, boolean lock) {
        return (CVoucherHeader) getCurrentSession().load(CVoucherHeader.class, id);
    }

    public List<CVoucherHeader> findAll() {
        return (List<CVoucherHeader>) getCurrentSession().createCriteria(CVoucherHeader.class).list();
    }

    @PersistenceContext
    private EntityManager entityManager;

    
    public Session getCurrentSession() {
        return entityManager.unwrap(Session.class);
    }


    @Override
    public List<CVoucherHeader> getVoucherHeadersByStatus(final Integer status) throws Exception {
        final Query qry = getCurrentSession().createQuery("from CVoucherHeader vh where vh.status=:status");
        qry.setInteger("status", status);
        return qry.list();
    }

    @Override
    public List<CVoucherHeader> getVoucherHeadersByStatusAndType(final Integer status, final String type)
            throws Exception {
        final Query qry = getCurrentSession().createQuery(
                "from CVoucherHeader vh where vh.status=:status and vh.type=:type");
        qry.setInteger("status", status);
        qry.setString("type", type);
        return qry.list();
    }

    @Override
    public CVoucherHeader getVoucherHeadersByCGN(final String cgn) {
        final Query qry = getCurrentSession().createQuery(" from CVoucherHeader where cgn =:cgn");
        qry.setString("cgn", cgn);
        return (CVoucherHeader) qry.uniqueResult();
    }
}