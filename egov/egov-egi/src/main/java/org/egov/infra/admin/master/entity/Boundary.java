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
package org.egov.infra.admin.master.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.egov.infra.persistence.entity.AbstractAuditable;
import org.egov.infra.persistence.validator.annotation.DateFormat;
import org.egov.infra.persistence.validator.annotation.Unique;
import org.egov.search.domain.Searchable;
import org.elasticsearch.common.geo.GeoPoint;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;

@Entity
@Unique(id = "id", tableName = "eg_boundary", fields = { "name" }, columnName = { "name" }, enableDfltMsg = true)
@Table(name = "EG_BOUNDARY")
@Searchable
@NamedQuery(name = "Boundary.findBoundariesByBoundaryType", query = "select b from Boundary b where b.boundaryType.id = :boundaryTypeId")
@SequenceGenerator(name = Boundary.SEQ_BOUNDARY, sequenceName = Boundary.SEQ_BOUNDARY, allocationSize = 1)
public class Boundary extends AbstractAuditable {

    private static final long serialVersionUID = 3054956514161912026L;
    public static final String SEQ_BOUNDARY = "seq_eg_boundary";

    @Expose
    @DocumentId
    @Id
    @GeneratedValue(generator = SEQ_BOUNDARY, strategy = GenerationType.SEQUENCE)
    private Long id;

    @Length(max = 512)
    @Searchable(name = "name")
    @SafeHtml
    @NotBlank
    private String name;

    private Long boundaryNum;

    @ManyToOne
    @JoinColumn(name = "boundaryType", updatable = false)
    private BoundaryType boundaryType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent")
    @Fetch(value = FetchMode.SELECT)
    private Boundary parent;

    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "parent")
    @Fetch(value = FetchMode.SELECT)
    @JsonIgnore
    private Set<Boundary> children = new HashSet<Boundary>();

    @DateFormat
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date fromDate;

    private Date toDate;

    private boolean isHistory;

    private Long bndryId;

    @SafeHtml
    private String localName;
   
    private Float longitude;
 
    private Float latitude;

    @Length(max = 32)
    private String materializedPath;

    @Transient
    private Long parentBoundaryNum;

    @Transient
    private Long hierarchyTypeId;

    @Transient
    private Long boundaryTypeId;

    @Transient
    private City city = new City();
    
    @Transient
    @Searchable(name = "boundaryLocation", group = Searchable.Group.COMMON)
    private transient GeoPoint boundaryLocation;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(final String boundaryNameLocal) {
        localName = boundaryNameLocal;
    }

    public Boundary getParent() {
        return parent;
    }

    public void setParent(final Boundary parent) {
        this.parent = parent;
    }

    public Set<Boundary> getChildren() {
        return children;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {

        this.name = name;
    }

    public boolean isLeaf() {
        return getChildren().isEmpty();
    }

    public BoundaryType getBoundaryType() {
        return boundaryType;
    }

    public Long getBoundaryNum() {
        return boundaryNum;
    }

    public void addChild(final Boundary boundary) {
        boundary.setParent(this);
        children.add(boundary);
    }

    public void deleteChild(final Boundary boundary) {
        children.remove(boundary);
    }

    public void addChildren(final Set<Boundary> boundaries) {
        children.addAll(boundaries);

    }

    public void deleteChildren(final Set<Boundary> boundaries) {
        children.removeAll(boundaries);
    }

    public boolean isRoot() {
        return getParent() == null ? true : false;
    }

    public void setBoundaryType(final BoundaryType boundaryType) {
        this.boundaryType = boundaryType;
    }

    public void setBoundaryNum(final Long number) {

        boundaryNum = number;
    }

    public void setChildren(final Set<Boundary> boundaries) {
        children = boundaries;

    }

    public Long getParentBoundaryNum() {
        return parentBoundaryNum;
    }

    public void setParentBoundaryNum(final Long parentBoundaryNum) {
        this.parentBoundaryNum = parentBoundaryNum;
    }

    public Long getHierarchyTypeId() {
        return hierarchyTypeId;
    }

    public void setHierarchyTypeId(final Long hierarchyTypeId) {
        this.hierarchyTypeId = hierarchyTypeId;
    }

    public Long getBoundaryTypeId() {
        return boundaryTypeId;
    }

    public void setBoundaryTypeId(final Long boundaryTypeId) {
        this.boundaryTypeId = boundaryTypeId;
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

    public boolean isHistory() {
        return isHistory;
    }

    public void setHistory(final boolean isHistory) {
        this.isHistory = isHistory;
    }

    public Long getBndryId() {
        return bndryId;
    }

    public void setBndryId(final Long bndryId) {
        this.bndryId = bndryId;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(final Float longitude) {
        this.longitude = longitude;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(final Float latitude) {
        this.latitude = latitude;
    }

    public String getMaterializedPath() {
        return materializedPath;
    }

    public void setMaterializedPath(final String materializedPath) {
        this.materializedPath = materializedPath;
    }

    public City getCity() {
        return city;
    }

    public void setCity(final City city) {
        this.city = city;
    }
    
    public GeoPoint getBoundaryLocation() {
        if (this.getLatitude() != null && this.getLongitude() != null) {
            this.boundaryLocation =(new GeoPoint(this.getLatitude(), this.getLongitude()));
        }
        return boundaryLocation;
    }

    public void setBoundaryLocation(GeoPoint boundaryLocation) {
        this.boundaryLocation = boundaryLocation;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (boundaryNum == null ? 0 : boundaryNum.hashCode());
        result = prime * result + (id == null ? 0 : id.hashCode());
        result = prime * result + (localName == null ? 0 : localName.hashCode());
        result = prime * result + (name == null ? 0 : name.hashCode());
        result = prime * result + (parent == null ? 0 : parent.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Boundary other = (Boundary) obj;
        if (boundaryNum == null) {
            if (other.boundaryNum != null)
                return false;
        } else if (!boundaryNum.equals(other.boundaryNum))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (localName == null) {
            if (other.localName != null)
                return false;
        } else if (!localName.equals(other.localName))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (parent == null) {
            if (other.parent != null)
                return false;
        } else if (!parent.equals(other.parent))
            return false;
        return true;
    }
}
