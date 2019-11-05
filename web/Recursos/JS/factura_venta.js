var PedidoJSON;
var jsonCabDetP;
var total;
CargarTablaNumeroFactura();
CargarTablasucursal();
CargarTablaUltimocodigo();
CargarTablaMercaderias();
CargarTablaCliente();
CargarTablaCondicionn();
CargarTablatipocobro();
CargarTablaFactura();
//

function prepararJsonpagos(ban) {
    var listapagos = [];

    $("#tabladetallecobro  tr").each(function () {
        //push => Agrega un nuevo elemento al Array [listaProductos]
        listapagos.push({
            id_ticobro: $(this).find("td").eq(0).html(),
            monto: $(this).find("td").eq(2).html()
        });
    });



    PagosJSON = {
        "bandera": 4,
        "id_venta": $('#id_ventas').val().length <= 0 ? "0" : $('#id_ventas').val(),
        "fecha": $('#fecha').val().length <= 0 ? "0" : $('#fecha').val(),
        "id_sucursal": $('#id_sucursal').val().length <= 0 ? "0" : $('#id_sucursal').val(),
        "id_caja": $('#id_cajas').val().length <= 0 ? "0" : $('#id_cajas').val(),
        "id_usuario": $('#id_usuario').val().length <= 0 ? "0" : $('#id_usuario').val(),
        "lista_tipocobros": listapagos.length <= 0 ? "0" : listapagos
    };
    enviopagos();
}




function verificarpagos() {
    var total = $('#total').val();
    var pagar = $('#pagar').val();
    if (total === "" | pagar === "") {
        alert('¡¡favor completar todos los campos!!');
    } else {
        if(total === pagar){
            prepararJsonpagos();
            alert('Registro Guardado');
            limpiarpagos();
            limpiarorden();
        }else{
            alert('¡¡Favor revisar el pago. El valor ingresado esta mal!!');
        }
    }
}




function verificar() {
    var camposucursal = $('#id_pedido').val();
    var campoobservacion = $('#fecha').val();
    if (camposucursal === "" | campoobservacion === "") {
        alert('¡¡favor completar todos los campos!!');
    } else {
        prepararJsonorden(accion);
//        alert('Registro Guardado');
        
    }
}

function cerrarbuscadorr() {
    document.getElementById('buscardormercaderia').style.display = "none";
}
function cerrarbuscadorrr() {
    document.getElementById('id011').style.display = "none";
}
function cerrarbuscadorrrr() {
    document.getElementById('id0111').style.display = "none";
}

function limpiarpagos() {
    document.getElementById('total').value = "";
    document.getElementById('cambio').value = "";
    document.getElementById('pagos').value = "";
    window.location.reload();
}


function limpiarorden() {
    document.getElementById('id_orden').value = "";
    document.getElementById('id_pedido').value = "";
    document.getElementById('id_usuario').value = "";
    document.getElementById('sucursal_pedido').value = "";
    document.getElementById('id_proveedor').value = "";
    document.getElementById('descrip_proveedor').value = "";
    document.getElementById('id_estadolistado').value = "";
    window.location.reload();
}

function cerrarbuscadororden() {
    document.getElementById('buscardor_orden').style.display = "none";
}

function verificarcobros() {
    var camposucursal = $('#id_pedido').val();
    var campoobservacion = $('#fecha').val();
    if (camposucursal === "" | campoobservacion === "") {
        alert('¡¡favor completar todos los campos!!');
    } else {
        prepararJsonorden(accion);

        alert('Registro Guardado');
        limpiarorden();
    }
}

function generarfactura() {
    accion = 1;
    document.getElementById('id_usuario').focus;
    // document.getElementById('proveedor').focus();
}

function anularfactura() {
    accion = 3;
//    InputIdLectura();
}



function grabarfactura() {
    switch (accion) {
        case 1:
           // debugger;
            verificar();
            break;
        case 2:
            Preguntar();
            break;
        case 3:
            confirmar();
            break;
        case 4:
            verificarpagos();
            break;
    }
}



function confirmar() {
    confirmar = confirm("Estas seguro que querés anular el registro???");
    if (confirmar) {
        prepararJsonorden(accion);
        alert('Registro Anulado');
        CargarTablaOrden();
        limpiarorden();

    } else {
        limpiarorden();
    }
}

function Preguntar() {
    confirmar = confirm("Estas seguro que querés Modificar el registro???");
    if (confirmar) {
        prepararJsonorden(accion);
        alert('Registro Modificado');
        CargarTablaOrden();
        limpiarorden();
    } else {
        limpiarorden();
// si pulsamos en cancelar
        ///alert('Registro eliminado');
    }
}
function monto_pagar() {
    var totaliza = document.getElementById('total').value;
    var monto = document.getElementById('monto').value;
    var totalizador = totaliza + monto;
    document.getElementById('total').value = totalizador;
}

function agregarFilapagos() {
    var acu;
    var tindex;
    var cod = document.getElementById('id_tipocobro').value;
    var des = document.getElementById('descrip_cobro').value;
    var monto = document.getElementById('monto').value;
    tindex++;
    acu += parseInt(monto);
    
    $('#tabladetallecobro').append("<tr onclick=gettdagregar(this);\'>\n\
            <td style=' width: 5%;'>" + cod + "</td>\n\
            <td style=' width: 60%;'>" + des + "</td>\n\
            <td style=' width: 60%;'>" + monto + "</td>\n\
            <td style=' width: 5%;'><img onclick=\"$(\'#prod" + tindex + "\')updateMonto(" + tindex + ")\" src='../Recursos/img/update.png'/></td>\n\
            <td style=' width: 5%;'><img onclick=\"$(\'#prod" + tindex + "\').remove();updateMonto(" + tindex + ")\" src='../Recursos/img/delete.png'/></td>\n\
      </tr>");
    monto_pagar();
    Limpiarmercaderia();
    document.getElementById('id_tipocobro').focus();
}





function agregarFilafactura() {
    var acu;
    var total = 0;
    var tindex;
    var filaspedidos;
    var cod = document.getElementById('id_mercaderia').value;
    var des = document.getElementById('descrip_mercaderia').value;
    var precio = document.getElementById('precio').value;
    var cant = document.getElementById('cantidad').value;
    var totalizador = precio * cant;
    tindex++;
    total = document.getElementById('precio_total').value;
    total += totalizador;
    $('#tabladetallefactura').append("<tr onclick=gettdagregar(this);\'>\n\
            <td style=' width: 5%;'>" + cod + "</td>\n\
            <td style=' width: 60%;'>" + des + "</td>\n\
            <td style=' width: 60%;'>" + precio + "</td>\n\
            <td style=' width: 5%;'>" + cant + "</td>\n\
            <td style=' width: 5%;'>" + totalizador + "</td>\n\
            <td style=' width: 5%;'><img onclick=\"$(\'#prod" + tindex + "\')updateMonto(" + tindex + ")\" src='../Recursos/img/update.png'/></td>\n\
            <td style=' width: 5%;'><img onclick=\"$(\'#prod" + tindex + "\').remove();updateMonto(" + tindex + ")\" src='../Recursos/img/delete.png'/></td>\n\
      </tr>");
    Limpiarpagos();
    document.getElementById('id_mercaderia').focus();
}

function Limpiarmercaderia() {
    document.getElementById('id_tipocobro').value = "";
    document.getElementById('descrip_cobro').value = "";
    document.getElementById('monto').value = "";
}


function gettdagregar(obj) {
    var varTR = obj.getElementsByTagName('td');
    document.getElementById('id_mercaderia').value = varTR[0].innerHTML;
    document.getElementById('descrip_mercaderia').value = varTR[1].innerHTML;
    document.getElementById('cantdad').style.display = "none";
}



function prepararJsonorden(ban) {

//var jsonCabDet;
    var listamercaderia = [];

    $("#tabladetallefactura  tr").each(function () {
        //push => Agrega un nuevo elemento al Array [listaProductos]
        listamercaderia.push({
            id_mercaderia: $(this).find("td").eq(0).html(),
            precio: $(this).find("td").eq(2).html(),
            cantidad: $(this).find("td").eq(3).html(),
            subtotal: $(this).find("td").eq(4).html()
//            monto: $(this).find("td").eq(3).html()
        });
    });



    PedidoJSON = {
        "bandera": ban,
        "id_venta": $('#id_venta').val().length <= 0 ? "0" : $('#id_venta').val(),
        "id_timbrado": $('#id_timbrado').val().length <= 0 ? "0" : $('#id_timbrado').val(),
        "fecha": $('#fecha').val().length <= 0 ? "0" : $('#fecha').val(),
        "id_usuario": $('#id_usuario').val().length <= 0 ? "0" : $('#id_usuario').val(),
        "ven_factura": $('#ven_factura').val().length <= 0 ? "0" : $('#ven_factura').val(),
        "id_condicion": $('#id_condicion').val().length <= 0 ? "0" : $('#id_condicion').val(),
        "num_factura": $('#num_factura').val().length <= 0 ? "0" : $('#num_factura').val(),
        "id_cliente": $('#id_clientes').val().length <= 0 ? "0" : $('#id_clientes').val(),
        "id_caja": $('#id_caja').val().length <= 0 ? "0" : $('#id_caja').val(),
        "lista_mercaderias": listamercaderia.length <= 0 ? "0" : listamercaderia
    };
    envioo();
}
function envioo() {
    var xmlhttp = new XMLHttpRequest();   // new HttpRequest instance 
    xmlhttp.open("POST", "/AutoserviceWalker/Factura_VentaCTRL");
    xmlhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xmlhttp.send(JSON.stringify(PedidoJSON));
}

function enviopagos() {
    var xmlhttp = new XMLHttpRequest();   // new HttpRequest instance 
    xmlhttp.open("POST", "/AutoserviceWalker/CobrosCTRL");
    xmlhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xmlhttp.send(JSON.stringify(PagosJSON));
}



function Recuperarfactura() {
    // alert("Llegamos...");
    // 1. Instantiate XHR - Start 
    var xhr;
    if (window.XMLHttpRequest)//verifica que el navegador tenga soporte
        xhr = new XMLHttpRequest();
    else if (window.ActiveXObject)
        xhr = new ActiveXObject("Msxml2.XMLHTTP");
    else
        throw new Error("Ajax is not supported by your browser");
    xhr.onreadystatechange = function () {
        // alert(xhr.responseText);
        if (xhr.readyState === 4) {
            if (xhr.status === 200 && xhr.status < 300)
            {
                var json = JSON.parse(xhr.responseText); //reponseText returns the entire JSON file and we assign it to a javascript variable "json".
                var x;
                var d;
                var tindex;
                if (json[0].id_orden === 0) {
                    alert('Este codigo no existe');
                    return;
                }
                for (x in json) {

                    document.getElementById('fecha').value = json[x].fecha;
                    document.getElementById('id_timbrado').value = json[x].id_timbrado;
                    document.getElementById('num_factura').value = json[x].num_factura;
                    document.getElementById('ven_factura').value = json[x].ven_factura;
                    document.getElementById('id_clientes').value = json[x].id_cliente;
                    document.getElementById('nombre_clientes').value = json[x].nombre_cliente;
                    document.getElementById('id_condicion').value = json[x].id_condicion;
                    for (d in json[x].lista_mercaderias) {
                        $('#tabladetallefactura').append("<tr id=\'prod" + tindex + "\'>\n\
                                <td style=' width: 5%;'>" + json[x].lista_mercaderias[d].id_mercaderia + "</td>\n\
                                <td style=' width: 60%;'>" + json[x].lista_mercaderias[d].descripcion + "</td>\n\
                                <td style=' width: 5%;'>" + json[x].lista_mercaderias[d].cantidad + "</td>\n\
                                <td style=' width: 5%;'>" + json[x].lista_mercaderias[d].precio + "</td>\n\
                                <td style=' width: 5%;'>" + json[x].lista_mercaderias[d].subtotal + "</td>\n\
                                <td style=' width: 5%;'><img onclick=\"$(\'#prod" + tindex + "\').remove();updateMonto(" + 444 + "," + tindex + ")\" src='../Recursos/img/update.png'/></td>\n\
                                <td style=' width: 5%;'><img onclick=\"$(\'#prod" + tindex + "\').remove();updateMonto(" + 555 + "," + tindex + ")\" src='../Recursos/img/delete.png'/></td>\n\
                          </tr>");

                    }
                }
            }
        }
    };
    xhr.open('POST', '/AutoserviceWalker/Factura_VentaCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 2, id_venta: $('#id_venta').val()}));
    // 3. Specify your action, location and Send to the server - End
}



function getTDfacturas(obj) {
    var varTR = obj.getElementsByTagName('td');
    document.getElementById('id_venta').value = varTR[0].innerHTML;
    Recuperarfactura();
    document.getElementById('buscardor_orden').style.display = "none";

}
function getTDmercaderia(obj) {
    var varTR = obj.getElementsByTagName('td');
    document.getElementById('id_mercaderia').value = varTR[0].innerHTML;
    document.getElementById('descrip_mercaderia').value = varTR[1].innerHTML;
    document.getElementById('precio').value = varTR[2].innerHTML;
    document.getElementById('buscardormercaderia').style.display = "none";

}


function cerrarbuscadorcliente() {
    document.getElementById('buscador_cliente').style.display = "none";
}
function getTDclientes(obj) {
    var varTR = obj.getElementsByTagName('td');
    document.getElementById('id_clientes').value = varTR[0].innerHTML;
    document.getElementById('nombre_clientes').value = varTR[1].innerHTML;
    document.getElementById('buscador_cliente').style.display = "none";

}

function CargarTablaCondicion() {
    // 1. Instantiate XHR - Start 
    var xhr;
    if (window.XMLHttpRequest)//verifica que el navegador tenga soporte
        xhr = new XMLHttpRequest();
    else if (window.ActiveXObject)
        xhr = new ActiveXObject("Msxml2.XMLHTTP");
    else
        throw new Error("Ajax is not supported by your browser");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200 && xhr.status < 300)
            {
                var json = JSON.parse(xhr.responseText); //reponseText returns the entire JSON file and we assign it to a javascript variable "json".
                var i;
                var filas = "";
                for (i = 0; i < json.length; i++) {

                    filas += "<option value= " + json[i].id_condicion + ">" + json[i].descripcion + "</option>";
                }
                document.getElementById("id_condicion").innerHTML = filas;

            }
        }
    };
    xhr.open('POST', '/AutoserviceWalker/Factura_VentaCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 5}));
}



function CargarTablaFactura() {
    // 1. Instantiate XHR - Start 
    var xhr;
    if (window.XMLHttpRequest)//verifica que el navegador tenga soporte
        xhr = new XMLHttpRequest();
    else if (window.ActiveXObject)
        xhr = new ActiveXObject("Msxml2.XMLHTTP");
    else
        throw new Error("Ajax is not supported by your browser");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200 && xhr.status < 300)
            {
                var json = JSON.parse(xhr.responseText); //reponseText returns the entire JSON file and we assign it to a javascript variable "json".
                var i;
                var filas = "";
                var idTD = "td_";
                for (i = 0; i < json.length; i++) {
                    idTD += i;
                    filas += "<tr onclick=getTDfacturas(this);>";
                    filas += "<td id=td1_" + i + ">" + json[i].id_venta + "</td>";
                    filas += "<td id=td2_" + i + ">" + json[i].fecha + "</td>";
                    filas += "<td id=td1_" + i + ">" + json[i].num_factura + "</td>";
                    filas += "<td id=td3_" + i + " >" + json[i].nombre_cliente + "</td>";
                    filas += "</tr>";
                }
                document.getElementById("id_facturalistado").innerHTML = filas;
            }
        }
    };
    // 2. Handle Response from Server - End

    // 3. Specify your action, location and Send to the server - Start   
    xhr.open('POST', '/AutoserviceWalker/Factura_VentaCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 4}));
    // 3. Specify your action, location and Send to the server - End
}



function CargarTablaCliente() {
    // 1. Instantiate XHR - Start 
    var xhr;
    if (window.XMLHttpRequest)//verifica que el navegador tenga soporte
        xhr = new XMLHttpRequest();
    else if (window.ActiveXObject)
        xhr = new ActiveXObject("Msxml2.XMLHTTP");
    else
        throw new Error("Ajax is not supported by your browser");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200 && xhr.status < 300)
            {
                var json = JSON.parse(xhr.responseText); //reponseText returns the entire JSON file and we assign it to a javascript variable "json".
                var i;
                var filas = "";
                var idTD = "td_";
                for (i = 0; i < json.length; i++) {
                    idTD += i;
                    filas += "<tr onclick=getTDclientes(this);>";
                    filas += "<td id=td1_" + i + ">" + json[i].id_cliente + "</td>";
                    filas += "<td id=td2_" + i + " >" + json[i].nombre + "</td>";
                    filas += "</tr>";
                }
                document.getElementById("id_clientess").innerHTML = filas;
            }
        }
    };
    // 2. Handle Response from Server - End

    // 3. Specify your action, location and Send to the server - Start   
    xhr.open('POST', '/AutoserviceWalker/Factura_VentaCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 6}));
    // 3. Specify your action, location and Send to the server - End
}



function FiltroMercaderias() {
    var input, filter, table, tr, td, i;
    input = document.getElementById("filtrosmercaderias");
    filter = input.value.toUpperCase();
    table = document.getElementById("id01");
    tr = table.getElementsByTagName("tr");
    for (i = 0; i < tr.length; i++) {
        td = tr[i].getElementsByTagName("td")[1];
        if (td) {
            if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {
                tr[i].style.display = "";
            } else {
                tr[i].style.display = "none";
            }
        }
    }
}

function cerrarbuscadorpedido() {
    document.getElementById('buscador_pedidos').style.display = "none";
}

function FiltroCliente() {
    var input, filter, table, tr, td, i;
    input = document.getElementById("filtroscliente");
    filter = input.value.toUpperCase();
    table = document.getElementById("buscador_cliente");
    tr = table.getElementsByTagName("tr");
    for (i = 0; i < tr.length; i++) {
        td = tr[i].getElementsByTagName("td")[1];
        if (td) {
            if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {
                tr[i].style.display = "";
            } else {
                tr[i].style.display = "none";
            }
        }
    }
}



function FiltroFactura() {
    var input, filter, table, tr, td, i;
    input = document.getElementById("filtrosfacturas");
    filter = input.value.toUpperCase();
    table = document.getElementById("buscardor_orden");
    tr = table.getElementsByTagName("tr");
    for (i = 0; i < tr.length; i++) {
        td = tr[i].getElementsByTagName("td")[2];
        if (td) {
            if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {
                tr[i].style.display = "";
            } else {
                tr[i].style.display = "none";
            }
        }
    }
}



function CargarTablaMercaderias() {
    // 1. Instantiate XHR - Start 
    var xhr;
    if (window.XMLHttpRequest)//verifica que el navegador tenga soporte
        xhr = new XMLHttpRequest();
    else if (window.ActiveXObject)
        xhr = new ActiveXObject("Msxml2.XMLHTTP");
    else
        throw new Error("Ajax is not supported by your browser");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200 && xhr.status < 300)
            {
                var json = JSON.parse(xhr.responseText); //reponseText returns the entire JSON file and we assign it to a javascript variable "json".
                var i;
                var filas = "";
                var idTD = "td_";
                for (i = 0; i < json.length; i++) {
                    idTD += i;
                    filas += "<tr onclick=getTDmercaderia(this);>";
                    filas += "<td id=td1_" + i + ">" + json[i].id_mercaderia + "</td>";
                    filas += "<td id=td2_" + i + " >" + json[i].descripcion + "</td>";
                    filas += "<td id=td2_" + i + " >" + json[i].precio + "</td>";
                    filas += "</tr>";
                }
                document.getElementById("crpTbmercaderiass").innerHTML = filas;
            }
        }
    };
    // 2. Handle Response from Server - End

    // 3. Specify your action, location and Send to the server - Start   
    xhr.open('POST', '/AutoserviceWalker/Factura_VentaCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 7}));
    // 3. Specify your action, location and Send to the server - End
}




function CargarTablaCondicionn() {
    // 1. Instantiate XHR - Start 
    var xhr;
    if (window.XMLHttpRequest)//verifica que el navegador tenga soporte
        xhr = new XMLHttpRequest();
    else if (window.ActiveXObject)
        xhr = new ActiveXObject("Msxml2.XMLHTTP");
    else
        throw new Error("Ajax is not supported by your browser");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200 && xhr.status < 300)
            {
                var json = JSON.parse(xhr.responseText); //reponseText returns the entire JSON file and we assign it to a javascript variable "json".
                var i;
                var filas = "";
                for (i = 0; i < json.length; i++) {

                    filas += "<option value= " + json[i].id_condicion + ">" + json[i].descripcion + "</option>";
                }
                document.getElementById("id_condicion").innerHTML = filas;

            }
        }
    };
    xhr.open('POST', '/AutoserviceWalker/Factura_VentaCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 5}));
}


function CargarTablatipocobro() {
    // 1. Instantiate XHR - Start 
    var xhr;
    if (window.XMLHttpRequest)//verifica que el navegador tenga soporte
        xhr = new XMLHttpRequest();
    else if (window.ActiveXObject)
        xhr = new ActiveXObject("Msxml2.XMLHTTP");
    else
        throw new Error("Ajax is not supported by your browser");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200 && xhr.status < 300)
            {
                var json = JSON.parse(xhr.responseText); //reponseText returns the entire JSON file and we assign it to a javascript variable "json".
                var i;
                var filas = "";
                var idTD = "td_";
                for (i = 0; i < json.length; i++) {
                    idTD += i;
                    filas += "<tr onclick=getTDpagos(this);>";
                    filas += "<td id=td1_" + i + ">" + json[i].id_ticobro + "</td>";
                    filas += "<td id=td2_" + i + " >" + json[i].descripcion + "</td>";
                    filas += "</tr>";
                }
                document.getElementById("id_tipocobros").innerHTML = filas;
            }
        }
    };
    // 2. Handle Response from Server - End

    // 3. Specify your action, location and Send to the server - Start   
    xhr.open('POST', '/AutoserviceWalker/Factura_VentaCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 8}));
    // 3. Specify your action, location and Send to the server - End
}


function cerrarbuscadorpagos() {
    document.getElementById('buscador_tipocobro').style.display = "none";
}


function getTDpagos(obj) {
    var varTR = obj.getElementsByTagName('td');
    document.getElementById('id_tipocobro').value = varTR[0].innerHTML;
    document.getElementById('descrip_cobro').value = varTR[1].innerHTML;
    document.getElementById('buscador_tipocobro').style.display = "none";
    document.getElementById('monto').focus();

}

function valortotal() {
    accion = 4;
    var total = $('#precio_total').val();
    var cliente = $('#nombre_clientes').val();
    var codigoultimo = $('#id_venta').val();
    var caja = $('#id_caja').val();
    document.getElementById('pagar').value = total;
    document.getElementById('cliente').value = cliente;
    document.getElementById('id_ventas').value = codigoultimo;
    document.getElementById('id_cajas').value = caja;
}

function cerrarbuscadorpagos() {
    document.getElementById('buscador_pagos').style.display = "none";
}




function CargarTablaUltimocodigo() {
    // 1. Instantiate XHR - Start 
    var xhr;
    if (window.XMLHttpRequest)//verifica que el navegador tenga soporte
        xhr = new XMLHttpRequest();
    else if (window.ActiveXObject)
        xhr = new ActiveXObject("Msxml2.XMLHTTP");
    else
        throw new Error("Ajax is not supported by your browser");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200 && xhr.status < 300)
            {
                var json = JSON.parse(xhr.responseText); //reponseText returns the entire JSON file and we assign it to a javascript variable "json".
                var i;
                var filas = "";
                var idTD = "td_";
                for (i = 0; i < json.length; i++) {
                    idTD += i;
                    filas += "<tr onclick=getTDpagoss(this);>";
                   document.getElementById("id_venta").value = json[i].id_codigoultimo;
                }
            }
        }
    };
    // 2. Handle Response from Server - End

    // 3. Specify your action, location and Send to the server - Start   
    xhr.open('POST', '/AutoserviceWalker/Factura_VentaCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 9}));
    // 3. Specify your action, location and Send to the server - End
}




function CargarTablasucursal() {
    // 1. Instantiate XHR - Start 
    var xhr;
    if (window.XMLHttpRequest)//verifica que el navegador tenga soporte
        xhr = new XMLHttpRequest();
    else if (window.ActiveXObject)
        xhr = new ActiveXObject("Msxml2.XMLHTTP");
    else
        throw new Error("Ajax is not supported by your browser");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200 && xhr.status < 300)
            {
                var json = JSON.parse(xhr.responseText); //reponseText returns the entire JSON file and we assign it to a javascript variable "json".
                var i;
                var filas = "";
                var idTD = "td_";
                for (i = 0; i < json.length; i++) {
                    idTD += i;
                    filas += "<tr onclick=getTDsucursal(this);>";
                    filas += "<td id=td1_" + i + ">" + json[i].id_sucursal + "</td>";
                    filas += "<td id=td2_" + i + " >" + json[i].descripcion + "</td>";
                    filas += "</tr>";
                }
                document.getElementById("id_sucursall").innerHTML = filas;
            }
        }
    };
    // 2. Handle Response from Server - End

    // 3. Specify your action, location and Send to the server - Start   
    xhr.open('POST', '/AutoserviceWalker/CobrosCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 5}));
    // 3. Specify your action, location and Send to the server - End
}


function FiltroSucursal() {
    var input, filter, table, tr, td, i;
    input = document.getElementById("filtrossucursal");
    filter = input.value.toUpperCase();
    table = document.getElementById("buscardor_orden");
    tr = table.getElementsByTagName("tr");
    for (i = 0; i < tr.length; i++) {
        td = tr[i].getElementsByTagName("td")[2];
        if (td) {
            if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {
                tr[i].style.display = "";
            } else {
                tr[i].style.display = "none";
            }
        }
    }
}


function cerrarbuscadorsucursal() {
    document.getElementById('buscador_sucursal').style.display = "none";
}


function getTDsucursal(obj) {
    var varTR = obj.getElementsByTagName('td');
    document.getElementById('id_sucursal').value = varTR[0].innerHTML;
    document.getElementById('sucursal').value = varTR[1].innerHTML;
    document.getElementById('buscador_sucursal').style.display = "none";

}





function CargarTablaNumeroFactura() {
    // 1. Instantiate XHR - Start 
    var xhr;
    if (window.XMLHttpRequest)//verifica que el navegador tenga soporte
        xhr = new XMLHttpRequest();
    else if (window.ActiveXObject)
        xhr = new ActiveXObject("Msxml2.XMLHTTP");
    else
        throw new Error("Ajax is not supported by your browser");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200 && xhr.status < 300)
            {
                var json = JSON.parse(xhr.responseText); //reponseText returns the entire JSON file and we assign it to a javascript variable "json".
                var i;
                var filas = "";
                var idTD = "td_";
                for (i = 0; i < json.length; i++) {
                    idTD += i;
                    filas += "<tr onclick=getTDpagoss(this);>";
                   document.getElementById("num_factura").value = json[i].num_factura;
                }
            }
        }
    };
    // 2. Handle Response from Server - End

    // 3. Specify your action, location and Send to the server - Start   
    xhr.open('POST', '/AutoserviceWalker/Factura_VentaCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 10}));
    // 3. Specify your action, location and Send to the server - End
}