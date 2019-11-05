/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelos.DAO.OrdenDAO;
import Modelos.DAOIMPL.OrdenDAOIMPLE;
import Modelos.DTO.OrdenDTO;
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
public class OrdenCTRL extends HttpServlet {

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
        OrdenDTO dto = gson.fromJson(json, OrdenDTO.class);

        OrdenDAO dao = new OrdenDAOIMPLE();
        switch (dto.getBandera()) {
            case 1:
                dao.generarorden(dto);
                break;
            case 2:
                response.setContentType("application/json, charset=UTF-8");
                String jsonCabDet;
                jsonCabDet = gson.toJson(dao.recuperarOrden(dto));
                System.out.println("json Cab Obtenido " + jsonCabDet);
                out.print(jsonCabDet);
                out.close();
                break;
            case 3:
                dao.anularorden(dto);
                break;
            case 4:
                System.out.println("Llegamos al caso 4");
                response.setContentType("application/json, charset=UTF-8");
                String datoss = gson.toJson(dao.getListConsultarTodo());
                System.out.println(datoss);
                if (datoss != null) {
                    out.println(datoss);
                    out.close();
                }
                break;
            case 5:
                System.out.println("Llegamos al caso 4");
                response.setContentType("application/json, charset=UTF-8");
                String datosss = gson.toJson(dao.getListEstado());
                System.out.println(datosss);
                if (datosss != null) {
                    out.println(datosss);
                    out.close();
                }
                break;
            case 6:
                System.out.println("Llegamos al caso 4");
                response.setContentType("application/json, charset=UTF-8");
                String datossss = gson.toJson(dao.getListOProveedor());
                System.out.println(datossss);
                if (datossss != null) {
                    out.println(datossss);
                    out.close();
                }
                break;
                case 7:
                System.out.println("Llegamos al caso 4");
                response.setContentType("application/json, charset=UTF-8");
                String datosssss = gson.toJson(dao.getListPedido());
                System.out.println(datosssss);
                if (datosssss != null) {
                    out.println(datosssss);
                    out.close();
                }
                break;
                case 8:
                response.setContentType("application/json, charset=UTF-8");
                String jsonCabDeta;
                jsonCabDeta = gson.toJson(dao.recuperarPedido(dto));
                System.out.println("json Cab Obtenido " + jsonCabDeta);
                out.print(jsonCabDeta);
                out.close();
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
