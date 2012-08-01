/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imaginea.botbot.server.converter;

import com.imaginea.botbot.server.jpa.RecordSession;
import java.net.URI;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlAttribute;
import javax.ws.rs.core.UriBuilder;
import javax.persistence.EntityManager;
import java.util.List;
import com.imaginea.botbot.server.jpa.RecordEntry;

/**
 *
 * @author rohit
 */
@XmlRootElement(name = "recordSession")
public class RecordSessionConverter {
    private RecordSession entity;
    private URI uri;
    private int expandLevel;

    /** Creates a new instance of RecordSessionConverter */
    public RecordSessionConverter() {
        entity = new RecordSession();
    }

    /**
     * Creates a new instance of RecordSessionConverter.
     *
     * @param entity associated entity
     * @param uri associated uri
     * @param expandLevel indicates the number of levels the entity graph should be expanded@param isUriExtendable indicates whether the uri can be extended
     */
    public RecordSessionConverter(RecordSession entity, URI uri, int expandLevel, boolean isUriExtendable) {
        this.entity = entity;
        this.uri = (isUriExtendable) ? UriBuilder.fromUri(uri).path(entity.getId() + "/").build() : uri;
        this.expandLevel = expandLevel;
        getRecordEntries();
    }

    /**
     * Creates a new instance of RecordSessionConverter.
     *
     * @param entity associated entity
     * @param uri associated uri
     * @param expandLevel indicates the number of levels the entity graph should be expanded
     */
    public RecordSessionConverter(RecordSession entity, URI uri, int expandLevel) {
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
     * Getter for name.
     *
     * @return value for name
     */
    @XmlElement
    public String getName() {
        return (expandLevel > 0) ? entity.getName() : null;
    }

    /**
     * Setter for name.
     *
     * @param value the value to set
     */
    public void setName(String value) {
        entity.setName(value);
    }
    
    /**
     * Getter for status.
     *
     * @return value for name
     */
    @XmlElement
    public String getStatus() {
        return (expandLevel > 0) ? entity.getStatus() : null;
    }

    /**
     * Setter for status.
     *
     * @param value the value to set
     */
    public void setStatus(String value) {
        entity.setStatus(value);
    }

    /**
     * Getter for records.
     *
     * @return value for records
     */
    @XmlElement
    public RecordEntriesConverter getRecordEntries() {
        if (expandLevel > 0) {
            if (entity.getRecordEntries() != null) {
                return new RecordEntriesConverter(entity.getRecordEntries(), uri.resolve("recordentries/"), expandLevel - 1);
            }
        }
        return null;
    }

    /**
     * Setter for records.
     *
     * @param value the value to set
     */
    public void setRecordEntries(RecordEntriesConverter value) {
        entity.setRecordEntries((value != null) ? new java.util.ArrayList<RecordEntry>(value.getEntities()) : null);
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
     * Returns the RecordSession entity.
     *
     * @return an entity
     */
    @XmlTransient
    public RecordSession getEntity() {
        if (entity.getId() == null) {
            RecordSessionConverter converter = UriResolver.getInstance().resolve(RecordSessionConverter.class, uri);
            if (converter != null) {
                entity = converter.getEntity();
            }
        }
        return entity;
    }

    /**
     * Returns the resolved RecordSession entity.
     *
     * @return an resolved entity
     */
    public RecordSession resolveEntity(EntityManager em) {
        List<RecordEntry> recordEntries = entity.getRecordEntries();
        List<RecordEntry> newrecordEntries = new java.util.ArrayList<RecordEntry>();
        if (recordEntries != null) {
            for (RecordEntry item : recordEntries) {
                newrecordEntries.add(em.getReference(RecordEntry.class, item.getId()));
            }
        }
        entity.setRecordEntries(newrecordEntries);
        return entity;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof RecordSessionConverter)) {
            return false;
        }
        RecordSessionConverter other = (RecordSessionConverter) object;
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
