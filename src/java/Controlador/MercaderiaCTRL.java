/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelos.DAO.MercaderiaDAO;
import Modelos.DAOIMPL.MercaderiasDAOIMPL;
import Modelos.DTO.MercaderiaDTO;
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
public class MercaderiaCTRL extends HttpServlet {

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
        MercaderiaDTO dto = jsonAObjeto.fromJson(json,  MercaderiaDTO.class);
        
        
        ///////////////////////////////////////////////////////////////
        //PerfilDTO dto =new PerfilDTO();
       // dto.setId(3);
        //dto.setDescrip("Prueba DTO MODIFICADO");
        MercaderiaDAO dao=new MercaderiasDAOIMPL();
  
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
                String dato= jsonAObjeto.toJson(dao.consultarSegunId(dto.getId_mercaderia()));
                System.out.println(dato);
                if (dato != null) {
                    out.println(dato);
                    out.close();
                }
            break;
             case 6:
                System.out.println("Llegamos al caso 4");
                response.setContentType("application/json, charset=UTF-8");
                String datoss= jsonAObjeto.toJson(dao.getListMarcas());
                System.out.println(datoss);
                if (datoss != null) {
                    out.println(datoss);
                    out.close();
                }
            break;
             case 7:
                System.out.println("Llegamos al caso 4");
                response.setContentType("application/json, charset=UTF-8");
                String datosss= jsonAObjeto.toJson(dao.getListSabor());
                System.out.println(datosss);
                if (datosss != null) {
                    out.println(datosss);
                    out.close();
                }
            break;
             case 8:
                System.out.println("Llegamos al caso 4");
                response.setContentType("application/json, charset=UTF-8");
                String datossss= jsonAObjeto.toJson(dao.getListCategoria());
                System.out.println(datossss);
                if (datossss != null) {
                    out.println(datossss);
                    out.close();
                }
            break;
             case 9:
                System.out.println("Llegamos al caso 4");
                response.setContentType("application/json, charset=UTF-8");
                String datosssss= jsonAObjeto.toJson(dao.getListFamilias());
                System.out.println(datosssss);
                if (datosssss != null) {
                    out.println(datosssss);
                    out.close();
                }
            break;
             case 10:
                System.out.println("Llegamos al caso 4");
                response.setContentType("application/json, charset=UTF-8");
                String datsosssss= jsonAObjeto.toJson(dao.getListSubcategorias());
                System.out.println(datsosssss);
                if (datsosssss != null) {
                    out.println(datsosssss);
                    out.close();
                }
            break;
             case 11:
                System.out.println("Llegamos al caso 4");
                response.setContentType("application/json, charset=UTF-8");
                String datososssss= jsonAObjeto.toJson(dao.getListTipofamilia());
                System.out.println(datososssss);
                if (datososssss != null) {
                    out.println(datososssss);
                    out.close();
                }
            break;
             case 12:
                System.out.println("Llegamos al caso 4");
                response.setContentType("application/json, charset=UTF-8");
                String datsossssss= jsonAObjeto.toJson(dao.getListUnidadmedida());
                System.out.println(datsossssss);
                if (datsossssss != null) {
                    out.println(datsossssss);
                    out.close();
                }
            break;
             case 13:
                System.out.println("Llegamos al caso 4");
                response.setContentType("application/json, charset=UTF-8");
                String datsosssssss= jsonAObjeto.toJson(dao.getListImpuestos());
                System.out.println(datsosssssss);
                if (datsosssssss != null) {
                    out.println(datsosssssss);
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
