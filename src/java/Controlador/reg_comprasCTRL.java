/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelos.DAO.reg_comprasDAO;
import Modelos.DAOIMPL.reg_comprasDAOIMPL;
import Modelos.DTO.reg_comprasDTO;
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
public class reg_comprasCTRL extends HttpServlet {

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
        reg_comprasDTO dto = gson.fromJson(json, reg_comprasDTO.class);

        reg_comprasDAO dao = new reg_comprasDAOIMPL();
        switch (dto.getBandera()) {
            case 1:
                dao.reg_compras(dto);
                break;
            case 2:
                dao.anular_reg_compras(dto);
                break;
            case 3:
                response.setContentType("application/json, charset=UTF-8");
                String jsonCabDet;
                jsonCabDet = gson.toJson(dao.recuperar_reg_compras(dto));
                System.out.println("json Cab Obtenido " + jsonCabDet);
                out.print(jsonCabDet);
                out.close();
                break;
            case 4:
                response.setContentType("application/json, charset=UTF-8");
                String jsonrecorden;
                jsonrecorden = gson.toJson(dao.recuperar_OrdenCompras(dto));
                System.out.println("json Cab Obtenido " + jsonrecorden);
                out.print(jsonrecorden);
                out.close();
                break;
            case 5:
                System.out.println("Llegamos al caso 5");
                response.setContentType("application/json, charset=UTF-8");
                String datoss = gson.toJson(dao.getListConsultarTodo());
                System.out.println(datoss);
                if (datoss != null) {
                    out.println(datoss);
                    out.close();
                }
                break;
            case 6:
                System.out.println("Llegamos al caso 6");
                response.setContentType("application/json, charset=UTF-8");
                String datossss = gson.toJson(dao.getListOrdenCompras());
                System.out.println(datossss);
                if (datossss != null) {
                    out.println(datossss);
                    out.close();
                }
                break;
            case 7:
                System.out.println("Llegamos al caso 7");
                response.setContentType("application/json, charset=UTF-8");
                String OrdEstado = gson.toJson(dao.getListEstado());
                System.out.println(OrdEstado);
                if (OrdEstado != null) {
                    out.println(OrdEstado);
                    out.close();
                }
                break;
                case 8:
                response.setContentType("application/json, charset=UTF-8");
                String jsonCabDett;
                jsonCabDett = gson.toJson(dao.recuperar_OrdenCompras(dto));
                System.out.println("json Cab Obtenido " + jsonCabDett);
                out.print(jsonCabDett);
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
