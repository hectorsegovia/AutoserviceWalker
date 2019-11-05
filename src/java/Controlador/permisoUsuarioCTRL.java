/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelos.DAO.UsuarioDAO;
import Modelos.DAOIMPL.UsuarioDAOIMPL;
import Modelos.DTO.UsuarioDTO;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.HTML;

public class permisoUsuarioCTRL extends HttpServlet {

    private String json;
    private PrintWriter out;
    private Gson gson;
    private UsuarioDTO dto;
    private UsuarioDAO dao;
    private String bloqueReferencialesCompras, bloqueReferencialesVentas, bloqueAdministracion, bloqueCompras, bloqueVentas, bloqueDistribucion, bloqueAyuda, bloqueReportes, bloqueSalir, reporteCompras, reporteVentas, reporteDistribucion;

    public permisoUsuarioCTRL() {
        json = "";
        bloqueReferencialesCompras = "";
        bloqueReferencialesVentas = "";
        bloqueAdministracion = "";
        bloqueCompras = "";
        bloqueVentas = "";
        bloqueDistribucion = "";
        bloqueAyuda = "";
        bloqueReportes = "";
        bloqueSalir = "";
        reporteCompras = "";
        reporteVentas = "";
        reporteDistribucion = "";

    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        out = response.getWriter();
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        if (br.ready()) {
            json = br.readLine();
        }
        System.out.println("Json c/ tres datos " + json);
        gson = new Gson();
        dto = gson.fromJson(json, UsuarioDTO.class);
        System.out.println("Exitoso " + dto.getNombre());
        System.out.println("Exitoso " + dto.getClave());
        System.out.println("Exitoso " + dto.getId_perfil());
        getBloquesPermisos();
        dao = new UsuarioDAOIMPL();
        System.out.println("id Perfil " + dao.getPermisoUsuario(dto));
        switch (dao.getPermisoUsuario(dto)) {
            case 1:
                out.println(bloqueReferencialesCompras + bloqueReferencialesVentas + bloqueCompras + bloqueVentas + bloqueReportes + bloqueAyuda + bloqueSalir);
                out.close();
                break;
            case 2:
                out.println(bloqueReferencialesVentas + bloqueVentas + reporteVentas + bloqueAyuda + bloqueSalir);
                out.close();
                break;
            case 3:
                out.println(bloqueReferencialesCompras + bloqueCompras + reporteCompras + bloqueAyuda + bloqueSalir);
                out.close();
                break;
            case 4:
                out.println(bloqueDistribucion + reporteDistribucion + bloqueAyuda + bloqueSalir);
                out.close();
                break;
            case 5:
                out.println();
                out.close();
                break;
        }

    }

    private void getBloquesPermisos() {
        bloqueReferencialesCompras = " <div class=\"w3-dropdown-hover\">\n"
                + "                    <button class=\"w3-button\">Ref. Compras</button>\n"
                + "                    <div class=\"w3-dropdown-content w3-bar-block w3-card-2\">\n"
                + "                        <a href=\"Marcas.html\" target=\"contenedor_paginas\" class=\"w3-bar-item w3-button\">Marca</a>\n"
                + "                        <a href=\"Categoria_Productos.html\" class=\"w3-bar-item w3-button\" target=\"contenedor_paginas\">Categoria Productos</a>\n"
                + "                        <a href=\"Ciudades.html\" class=\"w3-bar-item w3-button\" target=\"contenedor_paginas\">Ciudades</a>\n"
                + "                        <a href=\"Mercaderias.html\" class=\"w3-bar-item w3-button\" target=\"contenedor_paginas\">Mercaderias</a>\n"
                + "                        <a href=\"Estado.html\" class=\"w3-bar-item w3-button\" target=\"contenedor_paginas\">Estados</a>\n"
                + "                        <a href=\"Familias.html\" class=\"w3-bar-item w3-button\" target=\"contenedor_paginas\">Familias</a>\n"
                + "                        <a href=\"Pais.html\" class=\"w3-bar-item w3-button\" target=\"contenedor_paginas\">Pais</a>\n"
                + "                        <a href=\"Sabores.html\" class=\"w3-bar-item w3-button\" target=\"contenedor_paginas\">Sabores</a>\n"
                + "                        <a href=\"Subcategorias.html\" class=\"w3-bar-item w3-button\" target=\"contenedor_paginas\">Sub Categorias</a>\n"
                + "                        <a href=\"Sucursales.html\" class=\"w3-bar-item w3-button\" target=\"contenedor_paginas\">Sucursal</a>\n"
                + "                        <a href=\"Tipo_familias.html\" class=\"w3-bar-item w3-button\" target=\"contenedor_paginas\">Tipo Familias</a>\n"
                + "                        <a href=\"Proveedor.html\" class=\"w3-bar-item w3-button\" target=\"contenedor_paginas\">Proveedor</a>\n"
                + "                    </div>\n"
                + "                </div> ";
        bloqueReferencialesVentas = " <div class=\"w3-dropdown-hover\">\n"
                + "                    <button class=\"w3-button\">Ref. Ventas</button>\n"
                + "                    <div class=\"w3-dropdown-content w3-bar-block w3-card-2\">\n"
                + "                        <a href=\"Bancos.html\" class=\"w3-bar-item w3-button\" target=\"contenedor_paginas\">Bancos/Financieras</a>\n"
                + "                        <a href=\"Cajas.html\" class=\"w3-bar-item w3-button\" target=\"contenedor_paginas\">Cajas</a>\n"
                + "                        <a href=\"Cliente.html\" class=\"w3-bar-item w3-button\" target=\"contenedor_paginas\">Cliente</a>\n"
                + "                        <a href=\"Condicion.html\" class=\"w3-bar-item w3-button\" target=\"contenedor_paginas\">Condicion</a>\n"
                + "                        <a href=\"Motivos.html\" class=\"w3-bar-item w3-button\" target=\"contenedor_paginas\">Motivos</a>\n"
                + "                        <a href=\"Tipo_Cheque.html\" class=\"w3-bar-item w3-button\" target=\"contenedor_paginas\">Tipo Cheque</a>\n"
                + "                        <a href=\"Tipo_Cliente.html\" class=\"w3-bar-item w3-button\" target=\"contenedor_paginas\">Tipo Cliente</a>\n"
                + "                        <a href=\"Tipo_cobros.html\" class=\"w3-bar-item w3-button\" target=\"contenedor_paginas\">Tipo Cobros</a>\n"
                + "                        <a href=\"Tipo_facturas.html\" class=\"w3-bar-item w3-button\" target=\"contenedor_paginas\">Tipo Facturas</a>\n"
                + "                        <a href=\"Tipo_tarjeta.html\" class=\"w3-bar-item w3-button\" target=\"contenedor_paginas\">Tipo Tarjetas</a>\n"
                + "                        <a href=\"Timbrado.html\" class=\"w3-bar-item w3-button\" target=\"contenedor_paginas\">Timbrado</a>\n"
                + "                        <a href=\"Bancos.html\" class=\"w3-bar-item w3-button\" target=\"contenedor_paginas\">Timbrado</a>\n"
                + "                        <a href=\"Tarjeta.html\" class=\"w3-bar-item w3-button\" target=\"contenedor_paginas\">Timbrado</a>\n"

                + "                    </div>\n"
                + "                </div> ";
          bloqueAdministracion = " <div class=\"w3-dropdown-hover\">\n"
                + "                    <button class=\"w3-button\">Administracion</button>\n"
                + "                    <div class=\"w3-dropdown-content w3-bar-block w3-card-2\">\n"
                + "                        <a href=\"Usuario.html\" target=\"contenedor_paginas\" class=\"w3-bar-item w3-button\">Usuarios</a>\n"
                + "                        <a href=\"Perfiles.html\" class=\"w3-bar-item w3-button\" target=\"contenedor_paginas\">Perfiles</a>\n"
                + "                        <a href=\"ajustes.html\" class=\"w3-bar-item w3-button\" target=\"contenedor_paginas\">Ajustes</a>\n"
                + "                        <a href=\"Depositos.html\" class=\"w3-bar-item w3-button\" target=\"contenedor_paginas\">Depositos</a>\n"
                + "                    </div>\n"
                + "                </div> ";
        
        bloqueCompras = " <div class=\"w3-dropdown-hover\">\n"
                + "                    <button class=\"w3-button\">Compras</button>\n"
                + "                    <div class=\"w3-dropdown-content w3-bar-block w3-card-2\">\n"
                + "                        <a href=\"PedidoCompra.html\" class=\"w3-bar-item w3-button\" target=\"contenedor_paginas\">Generar Pedidos</a>\n"
                + "                        <a href=\"OrdenCompra.html\" class=\"w3-bar-item w3-button\" target=\"contenedor_paginas\">Orden de Compras</a>\n"
                + "                        <a href=\"reg_compras.html\" class=\"w3-bar-item w3-button\" target=\"contenedor_paginas\">Registrar Compras</a>\n"
                + "                    </div>\n"
                + "                </div> ";
        bloqueVentas = " <div class=\"w3-dropdown-hover\">\n"
                + "                    <button class=\"w3-button\">Ventas</button>\n"
                + "                    <div class=\"w3-dropdown-content w3-bar-block w3-card-2\">\n"
                + "                        <a href=\"AperturaCaja.html\" class=\"w3-bar-item w3-button\" target=\"contenedor_paginas\">Apertura</a>\n"
                + "                        <a href=\"Arqueo.html\" class=\"w3-bar-item w3-button\" target=\"contenedor_paginas\">Arqueo Caja</a>\n"
                + "                        <a href=\"Factura_Venta.html\" class=\"w3-bar-item w3-button\" target=\"contenedor_paginas\">Factura Ventas</a>\n"
                + "                        <a href=\"CierreCaja.html\" class=\"w3-bar-item w3-button\" target=\"contenedor_paginas\">Cierre Caja</a>\n"
////                + "                        <a href=\"generarCompra.html\" class=\"w3-bar-item w3-button\" target=\"contenedor_paginas\">Compras</a>\n"
////                + "                        <a href=\"stock.html\" class=\"w3-bar-item w3-button\" target=\"contenedor_paginas\">Stock</a>\n"
                + "                    </div>\n"
                + "                </div> ";
        bloqueDistribucion = " <div class=\"w3-dropdown-hover\">\n"
                + "                    <button class=\"w3-button\">Distribución</button>\n"
                + "                    <div class=\"w3-dropdown-content w3-bar-block w3-card-2\">\n"
////                + "                        <a href=\"generarPedido.html\" class=\"w3-bar-item w3-button\" target=\"contenedor_paginas\">Pedidos</a>\n"
////                + "                        <a href=\"generarCompra.html\" class=\"w3-bar-item w3-button\" target=\"contenedor_paginas\">Compras</a>\n"
////                + "                        <a href=\"stock.html\" class=\"w3-bar-item w3-button\" target=\"contenedor_paginas\">Stock</a>\n"
                + "                    </div>\n"
                + "                </div> ";
        bloqueReportes = " <div class=\"w3-dropdown-hover\">\n"
                + "                    <button class=\"w3-button\">Reportes</button>\n"
                + "                    <div class=\"w3-dropdown-content w3-bar-block w3-card-2\">\n"
                + "                        <a href=\"Reportes.html\" class=\"w3-bar-item w3-button\" target=\"contenedor_paginas\">Reporte</a>\n"
                + "                    </div>\n"
                + "                </div> ";
        reporteCompras = " <div class=\"w3-dropdown-hover\">\n"
                + "                    <button class=\"w3-button\">Reportes</button>\n"
                + "                    <div class=\"w3-dropdown-content w3-bar-block w3-card-2\">\n"
                + "                        <a href=\"reporteCompras.html\" class=\"w3-bar-item w3-button\" target=\"contenedor_paginas\">Compras</a>\n"
                + "                    </div>\n"
                + "                </div> ";
        
        reporteVentas = " <div class=\"w3-dropdown-hover\">\n"
                + "                    <button class=\"w3-button\">Reportes</button>\n"
                + "                    <div class=\"w3-dropdown-content w3-bar-block w3-card-2\">\n"
                + "                        <a href=\"reporteVentas.html\" class=\"w3-bar-item w3-button\" target=\"contenedor_paginas\">Ventas</a>\n"
                + "                    </div>\n"
                + "                </div> ";
        
        reporteDistribucion = " <div class=\"w3-dropdown-hover\">\n"
                + "                    <button class=\"w3-button\">Reportes</button>\n"
                + "                    <div class=\"w3-dropdown-content w3-bar-block w3-card-2\">\n"
                + "                        <a href=\"reporteDistribucion.html\" class=\"w3-bar-item w3-button\" target=\"contenedor_paginas\">Distribución</a>\n"
                + "                    </div>\n"
                + "                </div> ";
        
        bloqueAyuda = " <div class=\"w3-dropdown-hover\">\n"
                + "                    <a target=\"_blank\" href=\"../Ayuda/\"><button   class=\"w3-button\">Ayuda</button>\n"
                + "                </div> ";
        bloqueSalir = " <div class=\"w3-dropdown-hover\">\n"
                + "                    <a href=\"AccesoSistema.html\" <button class=\"w3-button\">Salir</button>\n"
//                + "                    <div class=\"w3-dropdown-content w3-bar-block w3-card-2\">\n"
//                + "                        <a href=\"acceso.html\" class=\"w3-bar-item w3-button\" target=\"contenedor_paginas\">Salir</a>\n"
//                + "                    </div>\n"
                + "                </div> ";
        

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
