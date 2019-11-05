/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos.DTO;

/**
 *
 * @author AspireES15
 */
public class UsuarioDTO {
    private Integer bandera;
    private Integer id_bandera;
    private Integer id_usuario;
    private String nombre;
    private String clave;
    private Integer id_perfil;
    private String nombre_perfil;
    private Integer id_empleados;
    private String nombre_empleados;

    public Integer getBandera() {
        return bandera;
    }

    public void setBandera(Integer bandera) {
        this.bandera = bandera;
    }

    public Integer getId_bandera() {
        return id_bandera;
    }

    public void setId_bandera(Integer id_bandera) {
        this.id_bandera = id_bandera;
    }

    public Integer getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Integer getId_perfil() {
        return id_perfil;
    }

    public void setId_perfil(Integer id_perfil) {
        this.id_perfil = id_perfil;
    }

    public String getNombre_perfil() {
        return nombre_perfil;
    }

    public void setNombre_perfil(String nombre_perfil) {
        this.nombre_perfil = nombre_perfil;
    }

    public Integer getId_empleados() {
        return id_empleados;
    }

    public void setId_empleados(Integer id_empleados) {
        this.id_empleados = id_empleados;
    }

    public String getNombre_empleados() {
        return nombre_empleados;
    }

    public void setNombre_empleados(String nombre_empleados) {
        this.nombre_empleados = nombre_empleados;
    }
    
}
