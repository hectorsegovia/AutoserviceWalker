/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelos.DAO.ArqueoDAO;
import Modelos.DAOIMPL.ArqueoDAOIMPL;
import Modelos.DTO.ArqueoDTO;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author AspireES15
 */
public class ArqueoCTRL extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();//para escribir la respuesta
        
              BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String json = "";
        if (br.ready()) {
            json = br.readLine();
        }
        System.out.println("Json Leido " + json);
        System.out.println("Json Leido soy el nuevo " + json);
        Gson jsonAObjeto = new Gson();
        ArqueoDTO dto = jsonAObjeto.fromJson(json, ArqueoDTO.class);
        
        
        ///////////////////////////////////////////////////////////////
        //PerfilDTO dto =new PerfilDTO();
       // dto.setId(3);
        //dto.setDescrip("Prueba DTO MODIFICADO");
        ArqueoDAO dao=new ArqueoDAOIMPL();
  
        switch (dto.getBandera()) {
            case 1:
                if (dao.agregar(dto)){
                }else{
                }
            break;
            case 7:
                response.setContentType("application/json, charset=UTF-8");
                String dato= jsonAObjeto.toJson(dao.getListAperturaarqueo());
                System.out.println(dato);
                if (dato != null) {
                    out.println(dato);
                    out.close();
                }
            break;
             case 6:
                response.setContentType("application/json, charset=UTF-8");
                String datoss= jsonAObjeto.toJson(dao.consultararqueo(dto.getId_usuario(), dto.getId_sucursal(), dto.getId_caja(), dto.getFecha()));
                System.out.println(datoss);
                if (datoss != null) {
                    out.println(datoss);
                    out.close();
                }
            break;
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
