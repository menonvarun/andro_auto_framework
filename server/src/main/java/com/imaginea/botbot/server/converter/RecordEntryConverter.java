/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imaginea.botbot.server.converter;

import com.imaginea.botbot.server.jpa.RecordEntry;
import java.net.URI;
import java.util.Calendar;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlAttribute;
import javax.ws.rs.core.UriBuilder;
import javax.persistence.EntityManager;
import com.imaginea.botbot.server.jpa.RecordSession;

/**
 *
 * @author rohit
 */
@XmlRootElement(name = "recordentry")
public class RecordEntryConverter {
    private RecordEntry entity;
    private URI uri;
    private int expandLevel;

    /** Creates a new instance of RecordEntryConverter */
    public RecordEntryConverter() {
        entity = new RecordEntry();
    }

    /**
     * Creates a new instance of RecordEntryConverter.
     *
     * @param entity associated entity
     * @param uri associated uri
     * @param expandLevel indicates the number of levels the entity graph should be expanded@param isUriExtendable indicates whether the uri can be extended
     */
    public RecordEntryConverter(RecordEntry entity, URI uri, int expandLevel, boolean isUriExtendable) {
        this.entity = entity;
        this.uri = (isUriExtendable) ? UriBuilder.fromUri(uri).path(entity.getId() + "/").build() : uri;
        this.expandLevel = expandLevel;
        getRecordSession();
    }

    /**
     * Creates a new instance of RecordEntryConverter.
     *
     * @param entity associated entity
     * @param uri associated uri
     * @param expandLevel indicates the number of levels the entity graph should be expanded
     */
    public RecordEntryConverter(RecordEntry entity, URI uri, int expandLevel) {
        this(entity, uri, expandLevel, false);
    }

    /**
     * Getter for id.
     *
     * @return value for id
     */
    @XmlElement
    public Long getId() {
        return (expandLevel > 0) ? entity.getId() : null;
    }

    /**
     * Setter for id.
     *
     * @param value the value to set
     */
    public void setId(Long value) {
        entity.setId(value);
    }

    /**
     * Getter for entryNo.
     *
     * @return value for entryNo
     */
    @XmlElement
    public Long getEntryNo() {
        return (expandLevel > 0) ? entity.getEntryNo() : null;
    }

    /**
     * Setter for entryNo.
     *
     * @param value the value to set
     */
    public void setEntryNo(Long value) {
        entity.setEntryNo(value);
    }

    /**
     * Getter for timestamp.
     *
     * @return value for timestamp
     */
    @XmlElement
    public Calendar getTimestamp() {
        return (expandLevel > 0) ? entity.getEntryTime() : null;
    }

    /**
     * Setter for timestamp.
     *
     * @param value the value to set
     */
    public void setTimestamp(Calendar value) {
        entity.setEntryTime(value);
    }

    /**
     * Getter for payload.
     *
     * @return value for payload
     */
    @XmlElement
    public String getPayload() {
        return (expandLevel > 0) ? entity.getPayload() : null;
    }

    /**
     * Setter for payload.
     *
     * @param value the value to set
     */
    public void setPayload(String value) {
        entity.setPayload(value);
    }

    /**
     * Getter for recordSession.
     *
     * @return value for recordSession
     */
    @XmlElement
    public RecordSessionConverter getRecordSession() {
        if (expandLevel > 0) {
            if (entity.getRecordSession() != null) {
                return new RecordSessionConverter(entity.getRecordSession(), uri.resolve("recordSession/"), expandLevel - 1, false);
            }
        }
        return null;
    }

    /**
     * Setter for recordSession.
     *
     * @param value the value to set
     */
    public void setRecordSession(RecordSessionConverter value) {
        entity.setRecordSession((value != null) ? value.getEntity() : null);
    }

    /**
     * Returns the URI associated with this converter.
     *
     * @return the uri
     */
    @XmlAttribute
    public URI getUri() {
        return uri;
    }

    /**
     * Sets the URI for this reference converter.
     *
     */
    public void setUri(URI uri) {
        this.uri = uri;
    }

    /**
     * Returns the RecordEntry entity.
     *
     * @return an entity
     */
    @XmlTransient
    public RecordEntry getEntity() {
        if (entity.getId() == null) {
            RecordEntryConverter converter = UriResolver.getInstance().resolve(RecordEntryConverter.class, uri);
            if (converter != null) {
                entity = converter.getEntity();
            }
        }
        return entity;
    }

    /**
     * Returns the resolved RecordEntry entity.
     *
     * @return an resolved entity
     */
    public RecordEntry resolveEntity(EntityManager em) {
        RecordSession recordSession = entity.getRecordSession();
        if (recordSession != null) {
            entity.setRecordSession(em.getReference(RecordSession.class, recordSession.getId()));
        }
        return entity;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof RecordEntryConverter)) {
            return false;
        }
        RecordEntryConverter other = (RecordEntryConverter) object;
        if ((this.uri == null && other.uri != null) || (this.uri != null && !this.uri.equals(other.uri))) {
            return false;
        }
        if (this.expandLevel != other.expandLevel) {
            return false;
        }
        if (expandLevel <= 0) {
            return true;
        }
        if ((this.entity == null && other.entity != null) || (this.entity != null && !this.entity.equals(other.entity))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = uri == null ? 0 : uri.hashCode();
        if (expandLevel <= 0) {
            return hash + 37 * expandLevel;
        }
        return hash + 37 * (expandLevel + 37 * (entity == null ? 0 : entity.hashCode()));
    }
}
