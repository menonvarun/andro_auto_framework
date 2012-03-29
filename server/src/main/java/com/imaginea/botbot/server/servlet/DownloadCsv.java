/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imaginea.botbot.server.servlet;

import com.imaginea.botbot.server.jpa.RecordEntry;
import com.imaginea.botbot.server.jpa.RecordSession;
import com.imaginea.botbot.server.service.PersistenceService;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.WebApplicationException;
import org.json.simple.JSONObject;

import org.json.simple.parser.JSONParser;

/**
 *
 * @author moiz
 * 
 * This servlet provides csv file for download
 * provided  paramater id which points to session id
 */
public class DownloadCsv extends HttpServlet {

    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException,
            IOException {

        ServletOutputStream out = null;
        ByteArrayInputStream byteArrayInputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        try {
            
            
            //TODO if id is invalid it needs to be handles 
            int id = Integer.parseInt(request.getParameter("id"));
            RecordSession session = getEntity(id);
            List<RecordEntry> sessionList = session.getRecordEntries();
            Iterator<RecordEntry> iterator = sessionList.iterator();
            response.setContentType("text/csv");
            String disposition = "attachment; fileName=testCase.csv";
            response.setHeader("Content-Disposition", disposition);

            PrintWriter writer = response.getWriter();
            writer.append("\"Command\",\"Rid\",\"Argument 1\",\"Argument 2\"\n");

            while (iterator.hasNext()) {
                RecordEntry tempEntry = iterator.next();

                JSONObject jsonObject = (JSONObject) new JSONParser().parse(tempEntry.getPayload());

                if (!jsonObject.containsKey("args[1]")) {
                    writer.append("\"" + jsonObject.get("command") + "\"" + "," + "\"" + jsonObject.get("rid") + "\"" + "," + "\"" + jsonObject.get("args[0]") + "\"" + ",\"\"\n");
                } else {
                    writer.append("\"" + jsonObject.get("command") + "\"" + "," + "\"" + jsonObject.get("rid") + "\"" + "," + "\"" + jsonObject.get("args[0]") + "\"" + "," + "\"" + jsonObject.get("args[1]") + "\"" + "\n");
                }
            }

            out.flush();
            out.close();

        } catch (Exception e) {
            System.err.println(e);


        } finally {
            if (out != null) {
                out.close();
            }
            if (byteArrayInputStream != null) {
                byteArrayInputStream.close();
            }
            if (bufferedOutputStream != null) {
                bufferedOutputStream.close();
            }
        }

    }
    
    //Getting a entity object
    protected RecordSession getEntity(long id) {
        EntityManager em = PersistenceService.getInstance().getEntityManager();
        try {
            return (RecordSession) em.createQuery("SELECT e FROM RecordSession e where e.id = :id").setParameter("id", id).getSingleResult();
        } catch (NoResultException ex) {
            throw new WebApplicationException(new Throwable("Resource does not exist."), 404);
        }
    }
}