/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos.DTO;

import java.util.List;

/**
 *
 * @author GuruW10P
 */
public class reg_comprasDTO {
  private Integer bandera;
  private Integer id_regcompra;
  private Integer num_factura;
  private String fecha_factura;
  private Integer id_condicion;
  private String descrip_condicion;
  private Integer id_estado;
  private Integer id_usuario;
  private String nombre_usu;
  private Integer id_ordencompra;
  private String caracter_1;
  private String nombre_provee;

  private List<MercaderiaDTO> lista_mercaderias;

    public Integer getBandera() {
        return bandera;
    }

    public void setBandera(Integer bandera) {
        this.bandera = bandera;
    }

  
    public String getNombre_provee() {
        return nombre_provee;
    }

    public void setNombre_provee(String nombre_provee) {
        this.nombre_provee = nombre_provee;
    }

  
    public Integer getId_regcompra() {
        return id_regcompra;
    }

    public void setId_regcompra(Integer id_regcompra) {
        this.id_regcompra = id_regcompra;
    }

    public Integer getNum_factura() {
        return num_factura;
    }

    public void setNum_factura(Integer num_factura) {
        this.num_factura = num_factura;
    }

    public String getFecha_factura() {
        return fecha_factura;
    }

    public void setFecha_factura(String fecha_factura) {
        this.fecha_factura = fecha_factura;
    }

    public Integer getId_condicion() {
        return id_condicion;
    }

    public void setId_condicion(Integer id_condicion) {
        this.id_condicion = id_condicion;
    }

    public String getDescrip_condicion() {
        return descrip_condicion;
    }

    public void setDescrip_condicion(String descrip_condicion) {
        this.descrip_condicion = descrip_condicion;
    }

    public Integer getId_estado() {
        return id_estado;
    }

    public void setId_estado(Integer id_estado) {
        this.id_estado = id_estado;
    }

    public Integer getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombre_usu() {
        return nombre_usu;
    }

    public void setNombre_usu(String nombre_usu) {
        this.nombre_usu = nombre_usu;
    }

    public Integer getId_ordencompra() {
        return id_ordencompra;
    }

    public void setId_ordencompra(Integer id_ordencompra) {
        this.id_ordencompra = id_ordencompra;
    }

    public String getCaracter_1() {
        return caracter_1;
    }

    public void setCaracter_1(String caracter_1) {
        this.caracter_1 = caracter_1;
    }

    public List<MercaderiaDTO> getLista_mercaderias() {
        return lista_mercaderias;
    }

    public void setLista_mercaderias(List<MercaderiaDTO> lista_mercaderias) {
        this.lista_mercaderias = lista_mercaderias;
    }

}
