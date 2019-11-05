/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos.DTO;

import java.util.List;

/**
 *
 * @author AspireES15
 */
public class Factura_ventaDTO {
    private Integer bandera;
    private Integer id_venta;
    private String fecha;
    private Integer num_factura;
    private String ven_factura;
    private Integer id_condicion;
    private String nombre_condicion;
    private Integer id_usuario;
    private Integer id_timbrado;
    private Integer id_cliente;
    private String nombre_cliente;
    private Integer id_ticobro;
    private Integer monto;
    private Integer id_codigoultimo;
    private Integer id_caja;
    
    private List<MercaderiaDTO> lista_mercaderias;

    public Integer getId_caja() {
        return id_caja;
    }

    public void setId_caja(Integer id_caja) {
        this.id_caja = id_caja;
    }

    public Integer getId_codigoultimo() {
        return id_codigoultimo;
    }

    public void setId_codigoultimo(Integer id_codigoultimo) {
        this.id_codigoultimo = id_codigoultimo;
    }

    public Integer getId_ticobro() {
        return id_ticobro;
    }

    public void setId_ticobro(Integer id_ticobro) {
        this.id_ticobro = id_ticobro;
    }



    public Integer getMonto() {
        return monto;
    }

    public void setMonto(Integer monto) {
        this.monto = monto;
    }

    
    
    public List<MercaderiaDTO> getLista_mercaderias() {
        return lista_mercaderias;
    }

    public void setLista_mercaderias(List<MercaderiaDTO> lista_mercaderias) {
        this.lista_mercaderias = lista_mercaderias;
    }

    
    
    public Integer getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(Integer id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getNombre_cliente() {
        return nombre_cliente;
    }

    public void setNombre_cliente(String nombre_cliente) {
        this.nombre_cliente = nombre_cliente;
    }
    

    public Integer getBandera() {
        return bandera;
    }

    public void setBandera(Integer bandera) {
        this.bandera = bandera;
    }

    public Integer getId_venta() {
        return id_venta;
    }

    public void setId_venta(Integer id_venta) {
        this.id_venta = id_venta;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Integer getNum_factura() {
        return num_factura;
    }

    public void setNum_factura(Integer num_factura) {
        this.num_factura = num_factura;
    }

    public String getVen_factura() {
        return ven_factura;
    }

    public void setVen_factura(String ven_factura) {
        this.ven_factura = ven_factura;
    }

    public Integer getId_condicion() {
        return id_condicion;
    }

    public void setId_condicion(Integer id_condicion) {
        this.id_condicion = id_condicion;
    }

    public String getNombre_condicion() {
        return nombre_condicion;
    }

    public void setNombre_condicion(String nombre_condicion) {
        this.nombre_condicion = nombre_condicion;
    }

    public Integer getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }

    public Integer getId_timbrado() {
        return id_timbrado;
    }

    public void setId_timbrado(Integer id_timbrado) {
        this.id_timbrado = id_timbrado;
    }
    
    
}
