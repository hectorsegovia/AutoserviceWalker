/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Genericos.Reporte;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author AspireES15
 */
public class ReportesCTRL extends HttpServlet {
    HttpServletResponse responses;
    HashMap<String, Object> parametros;
    Reporte reporte;
    String nombreJasper,urlPdf;
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
       this.responses = response;
        
        Integer bandera = Integer.parseInt(request.getParameter("bandera")) ;
        Integer valorId=Integer.parseInt(request.getParameter("valor")) ;
        System.out.println(valorId);
        Integer desde=Integer.parseInt(request.getParameter("desde")) ;
        Integer hasta=Integer.parseInt(request.getParameter("hasta"));
                System.out.println("desde "+ desde);
        System.out.println("hasta " + hasta);

        System.out.println("Bandera " + bandera);
        reporte = new Reporte();
        switch (bandera) {
            case 1:
                nombreJasper="Cargo";
                urlPdf = reporte.verReporte(nombreJasper);
            break;
            case 2:
                nombreJasper="Perfil_Usuario";
                urlPdf = reporte.verReporte(nombreJasper);
            break;
            case 3:
                nombreJasper="Stock";
                urlPdf = reporte.verReporte(nombreJasper);
            break;
            case 4:
                nombreJasper="TipoEquipoInformatico";
                urlPdf = reporte.verReporte(nombreJasper);
            break;
            case 5:
                nombreJasper="TipoTrabajo";
                urlPdf = reporte.verReporte(nombreJasper);
            break;
            case 6:
                nombreJasper="Repuestos";
                urlPdf = reporte.verReporte(nombreJasper);
            break;
            case 7:
                nombreJasper="Marca";
                urlPdf = reporte.verReporte(nombreJasper);
            break;
            case 8:
                nombreJasper="Modelo";
                urlPdf = reporte.verReporte(nombreJasper);
            break;
            case 9:
                nombreJasper="Servicios";
                urlPdf = reporte.verReporte(nombreJasper);
            break;
            case 10:
                nombreJasper="TipoCliente";
                urlPdf = reporte.verReporte(nombreJasper);
            break;
            case 11:
                nombreJasper="Tecnico";
                urlPdf = reporte.verReporte(nombreJasper);
            break;
            case 12:
                nombreJasper="Unidad_Perteciente";
                urlPdf = reporte.verReporte(nombreJasper);
            break;
            case 13:
                nombreJasper="Direccion_Perteneciente";
                urlPdf = reporte.verReporte(nombreJasper);
            break;
            case 14:
                nombreJasper="Departamento";
                urlPdf = reporte.verReporte(nombreJasper);
            break;
            case 15:
                nombreJasper="Cargo";
                urlPdf = reporte.verReporte(nombreJasper);
            break;
            case 16:
                parametros = new HashMap<>();
                parametros.put("desde", desde);
                parametros.put("hasta", hasta);
                urlPdf = reporte.verReporteParametros("Mvmto_OrdenTrabajo", parametros);
            break;
            case 17:
                    parametros = new HashMap<>();
                    parametros.put("codigo",desde);
                    urlPdf = reporte.verReporteParametros("Mvmto_Pedido", parametros);
            break;
            case 18:
               parametros = new HashMap<>();
                    parametros.put("desde",desde);
                    parametros.put("hasta",hasta);
                    urlPdf = reporte.verReporteParametros("Cliente", parametros);
            break;
            case 19:
               parametros = new HashMap<>();
                    parametros.put("desde",desde);
                    parametros.put("hasta",hasta);
                    urlPdf = reporte.verReporteParametros("CaractEquipos", parametros);
            break;
            case 20:
               parametros = new HashMap<>();
                    parametros.put("desde",desde);
                    parametros.put("hasta",hasta);
                    urlPdf = reporte.verReporteParametros("Recepciones", parametros);
            break;
        }
        convertirPDF(urlPdf);
    }

    private void convertirPDF(String urlPdf) {

        java.io.FileInputStream ficheroInput = null;
        try {
            ficheroInput = new java.io.FileInputStream(urlPdf);
            int tamanoInput = ficheroInput.available();
            byte[] datosPDF = new byte[tamanoInput];
            ficheroInput.read(datosPDF, 0, tamanoInput);

            responses.setContentType("application/pdf");
            responses.setContentLength(tamanoInput);
            responses.getOutputStream().write(datosPDF);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReportesCTRL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReportesCTRL.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ficheroInput.close();
            } catch (IOException ex) {
                Logger.getLogger(ReportesCTRL.class.getName()).log(Level.SEVERE, null, ex);
            }
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
