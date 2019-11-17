var PedidoJSON;
var jsonCabDetP;
CargarTablaEstado();
CargarTablaRegCompra();
CargarOrdenComprass();
CargarTablaCondicion();
CargarTablaCondicioncompra();

function verificar() {
    var campoobservacion = $('#id_ordenn').val();
    if (campoobservacion === "") {
        alert('¡¡favor completar todos los campos!!');
    } else {
        prepararJsonRegCompra(accion);
        alert('Registro Guardado');
    }
}

function limpiarRegCompra() {
    document.getElementById('nro_factura').value = "";
    document.getElementById('fecha_fact').value = "";
    document.getElementById('num_timbrado').value = "";
    document.getElementById('fecha_timbrado').value = "";
    document.getElementById('id_ordenn').value = "";
    document.getElementById('id_proveedor').value = "";
    document.getElementById('nombre_proveedor').value = "";
    document.getElementById('tabladetalleRegCompra').value = "";
    window.location.reload();
}




function generarRegCompra() {
    accion = 1;
    document.getElementById('id_user').focus;
}

function anularRegCompra() {
    accion = 2;
//    InputIdLectura();
}
function recuperarRegCompra() {
    accion = 3;
}

function aprobarorden() {
    accion = 11;
    aprobar();
}

function aprobar() {
    confirmar = confirm("Estas seguro que querés aprobar el registro???");
    if (confirmar) {
        prepararJsonRegCompra(accion);
        alert('Registro Aprobado');
        limpiarRegCompra();
        CargarTablaRegCompra();
    } else {
        limpiarRegCompra();
    }
}


function grabarRegCompras() {
    switch (accion) {
        case 1:
            verificar();
            break;
        case 3:
            Preguntar();
            break;
        case 2:
            confirmars();
            break;
    }
}



function confirmars() {
    confirmar = confirm("Estas seguro que querés anular el registro???");
    if (confirmar) {
        prepararJsonRegCompra(accion);
        alert('Registro Anulado');
        CargarTablaRegCompra();
        limpiarRegCompra();

    } else {
        limpiarRegCompra();
    }
}

function Preguntar() {
    confirmar = confirm("Estas seguro que querés Modificar el registro???");
    if (confirmar) {
        prepararJsonRegCompra(accion);
        alert('Registro Modificado');
        CargarTablaRegCompra();
        limpiarRegCompra();
    } else {
        limpiarRegCompra();
// si pulsamos en cancelar
        ///alert('Registro eliminado');
    }
}

//function agregarFilaRegCompra() {
//    var acu;
//    var tindex;
//    var filasorden;
//
//    var cod = document.getElementById('id_mercaderia').value;
//    var des = document.getElementById('descrip_mercaderia').value;
//    var cant = document.getElementById('cantidad').value;
////    var precio = document.getElementById('precio_articulo').value;
////    var calculo = cant * precio;
//
////    acu += calculo;
////    document.getElementById('montototalpedido').value = acu;
//    tindex++;
//
//    $('#tabladetallepedido').append("<tr onclick=gettdagregar(this);\'>\n\
//            <td style=' width: 5%;'>" + cod + "</td>\n\
//            <td style=' width: 60%;'>" + des + "</td>\n\
//            <td style=' width: 5%;'>" + cant + "</td>\n\
//            <td style=' width: 5%;'><img onclick=\"$(\'#prod" + cant + "\').remove();updateMonto(" + cant + ")\" src='../Recursos/img/delete.png'/></td>\n\
//            <td style=' width: 5%;'><img onclick=\"$(\'#prod" + tindex + "\').remove();updateMonto(" + tindex + ")\" src='../Recursos/img/delete.png'/></td>\n\
//      </tr>");
//    Limpiarmercaderia();
//    document.getElementById('id_mercaderia').focus();
//}


function gettdagregar(obj) {
    var varTR = obj.getElementsByTagName('td');
    document.getElementById('id_mercaderia').value = varTR[0].innerHTML;
    document.getElementById('descrip_mercaderia').value = varTR[1].innerHTML;
    document.getElementById('cantdad').style.display = "none";
}



function prepararJsonRegCompra(ban) {

//var jsonCabDet;
    var listamercaderia = [];

    $("#tabladetalleRegCompra  tr").each(function () {
        //push => Agrega un nuevo elemento al Array [listaProductos]
        listamercaderia.push({
            id_mercaderia: $(this).find("td").eq(0).html(),
            cantidad: $(this).find("td").eq(2).html(),
            precio: $(this).find("td").eq(3).html(),
            total: $(this).find("td").eq(4).html()
//            monto: $(this).find("td").eq(3).html()
        });
    });



    RegCompraJSON = {
        "bandera": ban,
        "num_factura": $('#nro_factura').val().length <= 0 ? "0" : $('#nro_factura').val(),
        "num_timbrado": $('#num_timbrado').val().length <= 0 ? "0" : $('#num_timbrado').val(),
        "fecha_factura": $('#fecha_factura').val().length <= 0 ? "0" : $('#fecha_factura').val(),
        "fecha_timbrado": $('#fecha_timbrado').val().length <= 0 ? "0" : $('#fecha_timbrado').val(),
        "id_ordencompra": $('#id_ordenn').val().length <= 0 ? "0" : $('#id_ordenn').val(),
        "id_condicion": $('#id_condicionlistado').val().length <= 0 ? "0" : $('#id_condicionlistado').val(),
        "condicion_compra": $('#id_condicioncompra').val().length <= 0 ? "0" : $('#id_condicioncompra').val(),
        "id_estado": $('#id_estado').val().length <= 0 ? "0" : $('#id_estado').val(),
        "id_usuario": $('#id_user').val().length <= 0 ? "0" : $('#id_user').val(),
        
        "lista_mercaderias": listamercaderia.length <= 0 ? "0" : listamercaderia
    };
    envioo();
}
function envioo() {
    var xmlhttp = new XMLHttpRequest();   // new HttpRequest instance 
    xmlhttp.open("POST", "/AutoserviceWalker/reg_comprasCTRL");
    xmlhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xmlhttp.send(JSON.stringify(RegCompraJSON));
}


function RecuperarRegCompra() {
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
                var iva5 = 0;
                var iva10 = 0;
                var total = 0;
                var tindex;
                if (json[0].id_orden === 0) {
                    alert('Este codigo no existe');
                    return;
                }
                for (x in json) {
                    document.getElementById('nro_factura').value = json[x].num_factura;
                    document.getElementById('fecha_factura').value = json[x].fecha_factura;
                    document.getElementById('id_user').value = json[x].id_usuario;
                    document.getElementById('nro_factura').value = json[x].num_factura;
                    document.getElementById('fecha_fact').value = json[x].fecha_factura;
                    document.getElementById('num_timbrado').value = json[x].num_timbrado;
                    document.getElementById('fecha_timbrado').value = json[x].fecha_timbrado;
                    document.getElementById('id_ordenn').value = json[x].id_ordencompra;
                    document.getElementById('id_proveedor').value = json[x].id_proveedor;
                    document.getElementById('nombre_proveedor').value = json[x].nombre_proveedor;
                    document.getElementById('id_condicionlistado').value = json[x].id_condicion;
                    document.getElementById('id_condicioncompra').value = json[x].condicion_compra;
                    document.getElementById('id_estado').value = json[x].id_estado;
                    for (d in json[x].lista_mercaderias) {
                        $('#tabladetalleRegCompra').append("<tr id=\'prod" + tindex + "\'>\n\
                                <td style=' width: 5%;'>" + json[x].lista_mercaderias[d].id_mercaderia + "</td>\n\
                                <td style=' width: 60%;'>" + json[x].lista_mercaderias[d].descripcion + "</td>\n\
                                <td style=' width: 5%;'>" + json[x].lista_mercaderias[d].precio + "</td>\n\
                                <td style=' width: 5%;'>" + json[x].lista_mercaderias[d].cantidad + "</td>\n\
                                <td style=' width: 5%;'>" + json[x].lista_mercaderias[d].exenta + "</td>\n\
                                <td style=' width: 5%;'>" + json[x].lista_mercaderias[d].iva5 + "</td>\n\
                                <td style=' width: 5%;'>" + json[x].lista_mercaderias[d].iva10 + "</td>\n\
                                <td style=' width: 5%;'>" + json[x].lista_mercaderias[d].total + "</td>\n\
                                <td style=' width: 5%;'><img onclick=\"$(\'#prod" + tindex + "\').remove();updateMonto(" + 444 + "," + tindex + ")\" src='../Recursos/img/update.png'/></td>\n\
                                <td style=' width: 5%;'><img onclick=\"$(\'#prod" + tindex + "\').remove();updateMonto(" + 555 + "," + tindex + ")\" src='../Recursos/img/delete.png'/></td>\n\
                          </tr>");
                        total += json[x].lista_mercaderias[d].total;
                        iva5 += json[x].lista_mercaderias[d].iva5;
                        iva10 += json[x].lista_mercaderias[d].iva10;
                    }
                    document.getElementById('precio_total').value = total;
                    var total_iva5 = Math.round(iva5 / 21); 
                    document.getElementById('total_iva5').value = total_iva5;
                    var total_iva10 = Math.round(iva10 / 11);
                    document.getElementById('total_iva10').value = total_iva10;
                    var total_iva = total_iva5 + total_iva10;
                    document.getElementById('total_iva').value = total_iva;
                }
            }
        }
    };
    xhr.open('POST', '/AutoserviceWalker/reg_comprasCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 3, num_factura: $('#nro_factura').val()}));
    // 3. Specify your action, location and Send to the server - End
}



function Recuperarordencompra() {
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
                var iva5 = 0;
                var iva10 = 0;
                var total = 0;
                var tindex;
                for (x in json) {
                    document.getElementById('id_ordenn').value = json[x].id_orden;
                    document.getElementById('id_proveedor').value = json[x].id_proveedor;
                    document.getElementById('nombre_proveedor').value = json[x].nombre_proveedor;
                    for (d in json[x].lista_mercaderias) {
                        $('#tabladetalleRegCompra').append("<tr id=\'prod" + tindex + "\'>\n\
                                <td style=' width: 5%;'>" + json[x].lista_mercaderias[d].id_mercaderia + "</td>\n\
                                <td style=' width: 60%;'>" + json[x].lista_mercaderias[d].descripcion + "</td>\n\
                                <td style=' width: 5%;'>" + json[x].lista_mercaderias[d].precio + "</td>\n\
                                \n\<td style=' width: 5%;'>" + json[x].lista_mercaderias[d].cantidad + "</td>\n\
                                \n\<td style=' width: 5%;'>" + json[x].lista_mercaderias[d].exenta + "</td>\n\
                                \n\<td style=' width: 5%;'>" + json[x].lista_mercaderias[d].iva5 + "</td>\n\
                                \n\<td style=' width: 5%;'>" + json[x].lista_mercaderias[d].iva10 + "</td>\n\
                                \n\<td style=' width: 5%;'>" + json[x].lista_mercaderias[d].total + "</td>\n\
                                <td style=' width: 5%;'><img onclick=\"$(\'#prod" + tindex + "\').remove();updateMonto(" + 444 + "," + tindex + ")\" src='../Recursos/img/update.png'/></td>\n\
                                <td style=' width: 5%;'><img onclick=\"$(\'#prod" + tindex + "\').remove();updateMonto(" + 555 + "," + tindex + ")\" src='../Recursos/img/delete.png'/></td>\n\
                          </tr>");

                    }
                    total += json[x].lista_mercaderias[d].total;
                    iva5 += json[x].lista_mercaderias[d].iva5;
                    iva10 += json[x].lista_mercaderias[d].iva10;
                }
                document.getElementById('precio_total').value = total;
                var total_iva5 = Math.round(iva5 / 21);
                document.getElementById('total_iva5').value = total_iva5;
                var total_iva10 = Math.round(iva10 / 11);
                document.getElementById('total_iva10').value = total_iva10;
                var total_iva = total_iva5 + total_iva10;
                document.getElementById('total_iva').value = total_iva;
            }
        }
    };
    xhr.open('POST', '/AutoserviceWalker/reg_comprasCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 8, id_ordencompra: $('#id_ordenn').val()}));
    // 3. Specify your action, location and Send to the server - End
}

function CargarTablaRegCompra() {
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
                    filas += "<tr onclick=getTDregCompras(this);>";
                    filas += "<td id=td2_" + i + ">" + json[i].num_factura + "</td>";
                    filas += "<td id=td3_" + i + ">" + json[i].fecha_factura + "</td>";
                    filas += "<td id=td4_" + i + " >" + json[i].nombre_provee + "</td>";
                    filas += "</tr>";
                }
                document.getElementById("id_Consultar_Todos").innerHTML = filas;
            }
        }
    };
    // 2. Handle Response from Server - End

    // 3. Specify your action, location and Send to the server - Start   
    xhr.open('POST', '/AutoserviceWalker/reg_comprasCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 5}));
    // 3. Specify your action, location and Send to the server - End
}

function getTDregCompras(obj) {
    var varTR = obj.getElementsByTagName('td');
    document.getElementById('nro_factura').value = varTR[0].innerHTML;
    RecuperarRegCompra();
    document.getElementById('buscardor_regcompras').style.display = "none";

}

function FiltroRegCompra() {
    var input, filter, table, tr, td, i;
    input = document.getElementById("filtrosRegCompra");
    filter = input.value.toUpperCase();
    table = document.getElementById("buscardor_regcompras");
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


function cerrarbuscadorRegCompra() {
    document.getElementById('buscardor_regcompras').style.display = "none";
}

function CargarOrdenComprass() {
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
                    filas += "<tr onclick=getTDorden(this);>";
                    filas += "<td id=td1_" + i + ">" + json[i].id_orden + "</td>";
                    filas += "<td id=td2_" + i + ">" + json[i].fecha + "</td>";
                    filas += "<td id=td3_" + i + " >" + json[i].nombre_proveedor + "</td>";
                    filas += "</tr>";
                }
                document.getElementById("id_ordencompras").innerHTML = filas;
            }
        }
    };
    // 2. Handle Response from Server - End

    // 3. Specify your action, location and Send to the server - Start   
    xhr.open('POST', '/AutoserviceWalker/reg_comprasCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 6}));
    // 3. Specify your action, location and Send to the server - End
}

function getTDorden(obj) {
    var varTR = obj.getElementsByTagName('td');
    document.getElementById('id_ordenn').value = varTR[0].innerHTML;
    Recuperarordencompra();
    document.getElementById('buscador_orden2').style.display = "none";

}

function cerrarbuscadorOrdenCompraa() {
    document.getElementById('buscador_orden2').style.display = "none";
}

function FiltroOrdenCompras() {
    var input, filter, table, tr, td, i;
    input = document.getElementById("filtrosordencompras");
    filter = input.value.toUpperCase();
    table = document.getElementById("buscador_orden2");
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

function CargarTablaEstado() {
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

                    filas += "<option value= " + json[i].id_estado + ">" + json[i].descripcion + "</option>";
                }
                document.getElementById("id_estadolistado").innerHTML = filas;

            }
        }
    };
    xhr.open('POST', '/AutoserviceWalker/reg_comprasCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 7}));
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
                document.getElementById("id_condicionlistado").innerHTML = filas;

            }
        }
    };
    xhr.open('POST', '/AutoserviceWalker/reg_comprasCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 9}));
}

function CargarTablaCondicioncompra() {
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

                    filas += "<option value= " + json[i].id_condicioncompra + ">" + json[i].descripcion + "</option>";
                }
                document.getElementById("id_condicioncompra").innerHTML = filas;

            }
        }
    };
    xhr.open('POST', '/AutoserviceWalker/reg_comprasCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 10}));
}
