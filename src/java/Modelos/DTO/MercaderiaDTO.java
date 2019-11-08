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
public class MercaderiaDTO {
    private Integer bandera;
    private String id_mercaderia;
    private String codigo_barra;
    private String descripcion;
    private Integer precio;
    private Integer id_marca;
    private Integer id_medida;
    private Integer id_sabor;
    private Integer id_impuesto;
    private Integer id_categoria;
    private Integer unidad;
    private Integer id_familia;
    private Integer id_subcategoria;
    private Integer id_tifamilia;
    private Integer total;
    private Integer saldo;
    private Integer id_motivo;
    private Integer id_deposito;
    private String nombre_deposito;
    private Integer subtotal;
    private Integer ven_iva;
    private Integer descuentos;
    private Integer cantidadd;
    private String nombre_impuesto;
    private Integer iva10;
    private Integer iva5;
    private Integer exenta;

    public Integer getIva10() {
        return iva10;
    }

    public void setIva10(Integer iva10) {
        this.iva10 = iva10;
    }

    public Integer getIva5() {
        return iva5;
    }

    public void setIva5(Integer iva5) {
        this.iva5 = iva5;
    }

    public Integer getExenta() {
        return exenta;
    }

    public void setExenta(Integer exenta) {
        this.exenta = exenta;
    }

    public String getNombre_impuesto() {
        return nombre_impuesto;
    }

    public void setNombre_impuesto(String nombre_impuesto) {
        this.nombre_impuesto = nombre_impuesto;
    }

    
    
    public Integer getCantidadd() {
        return cantidadd;
    }

    public void setCantidadd(Integer cantidadd) {
        this.cantidadd = cantidadd;
    }
    
    

    public Integer getVen_iva() {
        return ven_iva;
    }

    public void setVen_iva(Integer ven_iva) {
        this.ven_iva = ven_iva;
    }

    public Integer getDescuentos() {
        return descuentos;
    }

    public void setDescuentos(Integer descuentos) {
        this.descuentos = descuentos;
    }

    
    
    public Integer getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Integer subtotal) {
        this.subtotal = subtotal;
    }
    
    
    
    //este de abajo se usa para generar pedido
    private Integer cantidad;
    private Integer monto;

    public Integer getId_deposito() {
        return id_deposito;
    }

    public void setId_deposito(Integer id_deposito) {
        this.id_deposito = id_deposito;
    }

    public String getNombre_deposito() {
        return nombre_deposito;
    }

    public void setNombre_deposito(String nombre_deposito) {
        this.nombre_deposito = nombre_deposito;
    }

    public Integer getId_motivo() {
        return id_motivo;
    }

    public void setId_motivo(Integer id_motivo) {
        this.id_motivo = id_motivo;
    }
    

    public Integer getSaldo() {
        return saldo;
    }

    public void setSaldo(Integer saldo) {
        this.saldo = saldo;
    }

    
    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getMonto() {
        return monto;
    }

    public void setMonto(Integer monto) {
        this.monto = monto;
    }
    
    
    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
    
    
    public Integer getBandera() {
        return bandera;
    }

    public void setBandera(Integer bandera) {
        this.bandera = bandera;
    }

    public String getId_mercaderia() {
        return id_mercaderia;
    }

    public void setId_mercaderia(String id_mercaderia) {
        this.id_mercaderia = id_mercaderia;
    }

    public String getCodigo_barra() {
        return codigo_barra;
    }

    public void setCodigo_barra(String codigo_barra) {
        this.codigo_barra = codigo_barra;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    public Integer getId_marca() {
        return id_marca;
    }

    public void setId_marca(Integer id_marca) {
        this.id_marca = id_marca;
    }

    public Integer getId_medida() {
        return id_medida;
    }

    public void setId_medida(Integer id_medida) {
        this.id_medida = id_medida;
    }

    public Integer getId_sabor() {
        return id_sabor;
    }

    public void setId_sabor(Integer id_sabor) {
        this.id_sabor = id_sabor;
    }

    public Integer getId_impuesto() {
        return id_impuesto;
    }

    public void setId_impuesto(Integer id_impuesto) {
        this.id_impuesto = id_impuesto;
    }

    public Integer getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(Integer id_categoria) {
        this.id_categoria = id_categoria;
    }

    public Integer getUnidad() {
        return unidad;
    }

    public void setUnidad(Integer unidad) {
        this.unidad = unidad;
    }

    public Integer getId_familia() {
        return id_familia;
    }

    public void setId_familia(Integer id_familia) {
        this.id_familia = id_familia;
    }

    public Integer getId_subcategoria() {
        return id_subcategoria;
    }

    public void setId_subcategoria(Integer id_subcategoria) {
        this.id_subcategoria = id_subcategoria;
    }

    public Integer getId_tifamilia() {
        return id_tifamilia;
    }

    public void setId_tifamilia(Integer id_tifamilia) {
        this.id_tifamilia = id_tifamilia;
    }
    
}
