/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imaginea.botbot.server.converter;

import com.imaginea.botbot.server.jpa.RecordSession;
import java.net.URI;
import java.util.Collection;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlAttribute;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

/**
 *
 * @author rohit
 */
@XmlRootElement(name = "recordSessions")
public class RecordSessionsConverter {
    private Collection<RecordSession> entities;
    private Collection<RecordSessionConverter> items;
    private URI uri;
    private int expandLevel;

    /** Creates a new instance of RecordSessionsConverter */
    public RecordSessionsConverter() {
    }

    /**
     * Creates a new instance of RecordSessionsConverter.
     *
     * @param entities associated entities
     * @param uri associated uri
     * @param expandLevel indicates the number of levels the entity graph should be expanded
     */
    public RecordSessionsConverter(Collection<RecordSession> entities, URI uri, int expandLevel) {
        this.entities = entities;
        this.uri = uri;
        this.expandLevel = expandLevel;
        getRecordSession();
    }

    /**
     * Returns a collection of RecordSessionConverter.
     *
     * @return a collection of RecordSessionConverter
     */
    @XmlElement
    public Collection<RecordSessionConverter> getRecordSession() {
        if (items == null) {
            items = new ArrayList<RecordSessionConverter>();
        }
        if (entities != null) {
            items.clear();
            for (RecordSession entity : entities) {
                items.add(new RecordSessionConverter(entity, uri, expandLevel, true));
            }
        }
        return items;
    }

    /**
     * Sets a collection of RecordSessionConverter.
     *
     * @param a collection of RecordSessionConverter to set
     */
    public void setRecordSession(Collection<RecordSessionConverter> items) {
        this.items = items;
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
     * Returns a collection RecordSession entities.
     *
     * @return a collection of RecordSession entities
     */
    @XmlTransient
    public Collection<RecordSession> getEntities() {
        entities = new ArrayList<RecordSession>();
        if (items != null) {
            for (RecordSessionConverter item : items) {
                entities.add(item.getEntity());
            }
        }
        return entities;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof RecordSessionsConverter)) {
            return false;
        }
        RecordSessionsConverter other = (RecordSessionsConverter) object;
        if ((this.uri == null && other.uri != null) || (this.uri != null && !this.uri.equals(other.uri))) {
            return false;
        }
        if (this.expandLevel != other.expandLevel) {
            return false;
        }
        if (this.items.size() != other.items.size()) {
            return false;
        }
        Set<RecordSessionConverter> itemSet = new HashSet<RecordSessionConverter>(this.items);
        if (!itemSet.containsAll(other.items)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = uri == null ? 0 : uri.hashCode();
        hash = 37 * hash + expandLevel;
        for (RecordSessionConverter item : this.items) {
            hash = 37 * hash + item.hashCode();
        }
        return hash;
    }
}
