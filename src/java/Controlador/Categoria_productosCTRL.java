/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelos.DAO.Categoria_productoDAO;
import Modelos.DAOIMPL.Categoria_productoDAOIMPL;
import Modelos.DTO.Categoria_productoDTO;
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
public class Categoria_productosCTRL extends HttpServlet {

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
        Categoria_productoDTO dto = jsonAObjeto.fromJson(json, Categoria_productoDTO.class);
        
        
        ///////////////////////////////////////////////////////////////
        //PerfilDTO dto =new PerfilDTO();
       // dto.setId(3);
        //dto.setDescrip("Prueba DTO MODIFICADO");
        Categoria_productoDAO dao=new Categoria_productoDAOIMPL();
  
        switch (dto.getBandera()) {
            case 1:
                if (dao.agregar(dto)){
                }else{
                }
            case 2:
                if (dao.modificar(dto)){
                }else{
                }
                break;
            case 3:
                if (dao.eliminar(dto)){
                }else{
                }
                break;
            case 4:
                System.out.println("Llegamos al caso 4");
                response.setContentType("application/json, charset=UTF-8");
                String datos= jsonAObjeto.toJson(dao.consultarTodos());
                System.out.println(datos);
                if (datos != null) {
                    out.println(datos);
                    out.close();
                }
            break;
            case 5:
                response.setContentType("application/json, charset=UTF-8");
                String dato= jsonAObjeto.toJson(dao.consultarSegunId(dto.getId_categoria()));
                System.out.println(dato);
                if (dato != null) {
                    out.println(dato);
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
