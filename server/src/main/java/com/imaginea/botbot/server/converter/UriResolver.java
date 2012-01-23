/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imaginea.botbot.server.converter;

import javax.ws.rs.WebApplicationException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import javax.xml.bind.JAXBContext;

/**
 * Utility class for resolving an uri into an entity.
 *
 * @author rohit
 */
public class UriResolver {
    
    private static ThreadLocal<UriResolver> instance = new ThreadLocal<UriResolver>() {

        protected UriResolver initialValue() {
            return new UriResolver();
        }
    };
    private boolean inProgress = false;
    
    private UriResolver() {
    }

    /**
     * Returns an instance of UriResolver.
     *
     * @return an instance of UriResolver.
     */
    public static UriResolver getInstance() {
        return instance.get();
    }
    
    private static void removeInstance() {
        instance.remove();
    }

    /**
     * Returns the entity associated with the given uri.
     *
     * @param type the converter class used to unmarshal the entity from XML
     * @param uri the uri identifying the entity
     * @return the entity associated with the given uri
     */
    public <T> T resolve(Class<T> type, URI uri) {
        if (inProgress) {
            return null;
        }
        
        inProgress = true;
        
        try {
            if (uri == null) {
                throw new RuntimeException("No uri specified in a reference.");
            }
            
            URL url = uri.toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            
            if (conn.getResponseCode() == 200) {
                JAXBContext context = JAXBContext.newInstance(type);
                
                return (T) context.createUnmarshaller().unmarshal(conn.getInputStream());
            } else {
                throw new WebApplicationException(new Throwable("Resource for " + uri + " does not exist."), 404);
            }
        } catch (WebApplicationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new WebApplicationException(ex);
        } finally {
            removeInstance();
        }
    }
}
