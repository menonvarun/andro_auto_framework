/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imaginea.botbot.server.jpa;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author rohit
 */
@Entity
public class RecordSession implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String status;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "recordSession")
    private List<RecordEntry> recordEntries;

    public List<RecordEntry> getRecordEntries() {
        return recordEntries;
    }

    public void setRecordEntries(List<RecordEntry> recordEntries) {
        this.recordEntries = recordEntries;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getStatus(){
        return this.status;    
    }
    
    public void setStatus(String status){
        this.status=status;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RecordSession)) {
            return false;
        }
        RecordSession other = (RecordSession) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.imaginea.botbot.server.RecordSession[ id=" + id + " ]";
    }
}
