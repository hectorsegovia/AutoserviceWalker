/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelos.DAO.ajustesDAO;
import Modelos.DAOIMPL.ajustesDAOIMPL;
import Modelos.DTO.ajustesDTO;
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
public class AjusteCTRL extends HttpServlet {

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
       String json = "";
        PrintWriter out = response.getWriter();
        out = response.getWriter();
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        if (br.ready()) {
            json = br.readLine();
        }

        System.out.println("Json c/ tres datos " + json);
        Gson gson = new Gson();
        ajustesDTO dto = gson.fromJson(json, ajustesDTO.class);

        ajustesDAO dao = new ajustesDAOIMPL();
        switch (dto.getBandera()) {
            case 1:
                dao.generarAjustes(dto);
                break;
            case 2:
                System.out.println("Llegamos al caso 2");
                response.setContentType("application/json, charset=UTF-8");
                String sucur = gson.toJson(dao.getListConsultarSucursal());
                System.out.println(sucur);
                if (sucur != null) {
                    out.println(sucur);
                    out.close();
                }
                break;
            case 3:
                System.out.println("Llegamos al caso 3");
                response.setContentType("application/json, charset=UTF-8");
                String motivo = gson.toJson(dao.getListConsultarMotivo());
                System.out.println(motivo);
                if (motivo != null) {
                    out.println(motivo);
                    out.close();
                }
                break;
            case 4:
                System.out.println("Llegamos al caso 4");
                response.setContentType("application/json, charset=UTF-8");
                String merca = gson.toJson(dao.getListConsultarMercaderia());
                System.out.println(merca);
                if (merca != null) {
                    out.println(merca);
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
