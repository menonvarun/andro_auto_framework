/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imaginea.botbot.server.converter;

import com.imaginea.botbot.server.jpa.RecordEntry;
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
@XmlRootElement(name = "recordentries")
public class RecordEntriesConverter {
    private Collection<RecordEntry> entities;
    private Collection<RecordEntryConverter> items;
    private URI uri;
    private int expandLevel;

    /** Creates a new instance of RecordEntriesConverter */
    public RecordEntriesConverter() {
    }

    /**
     * Creates a new instance of RecordEntriesConverter.
     *
     * @param entities associated entities
     * @param uri associated uri
     * @param expandLevel indicates the number of levels the entity graph should be expanded
     */
    public RecordEntriesConverter(Collection<RecordEntry> entities, URI uri, int expandLevel) {
        this.entities = entities;
        this.uri = uri;
        this.expandLevel = expandLevel;
        getRecord();
    }

    /**
     * Returns a collection of RecordEntryConverter.
     *
     * @return a collection of RecordEntryConverter
     */
    @XmlElement
    public Collection<RecordEntryConverter> getRecord() {
        if (items == null) {
            items = new ArrayList<RecordEntryConverter>();
        }
        if (entities != null) {
            items.clear();
            for (RecordEntry entity : entities) {
                items.add(new RecordEntryConverter(entity, uri, expandLevel, true));
            }
        }
        return items;
    }

    /**
     * Sets a collection of RecordEntryConverter.
     *
     * @param a collection of RecordEntryConverter to set
     */
    public void setRecord(Collection<RecordEntryConverter> items) {
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
     * Returns a collection RecordEntry entities.
     *
     * @return a collection of RecordEntry entities
     */
    @XmlTransient
    public Collection<RecordEntry> getEntities() {
        entities = new ArrayList<RecordEntry>();
        if (items != null) {
            for (RecordEntryConverter item : items) {
                entities.add(item.getEntity());
            }
        }
        return entities;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof RecordEntriesConverter)) {
            return false;
        }
        RecordEntriesConverter other = (RecordEntriesConverter) object;
        if ((this.uri == null && other.uri != null) || (this.uri != null && !this.uri.equals(other.uri))) {
            return false;
        }
        if (this.expandLevel != other.expandLevel) {
            return false;
        }
        if (this.items.size() != other.items.size()) {
            return false;
        }
        Set<RecordEntryConverter> itemSet = new HashSet<RecordEntryConverter>(this.items);
        if (!itemSet.containsAll(other.items)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = uri == null ? 0 : uri.hashCode();
        hash = 37 * hash + expandLevel;
        for (RecordEntryConverter item : this.items) {
            hash = 37 * hash + item.hashCode();
        }
        return hash;
    }
}
