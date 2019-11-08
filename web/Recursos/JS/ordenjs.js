var PedidoJSON;
var jsonCabDetP;
var total;
CargarTablaProveedor();
CargarTablaEstado();
CargarTablaPedido();
CargarTablaOrden();


function verificar() {
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

function cerrarbuscadorr() {
    document.getElementById('buscardormercaderia').style.display = "none";
}
function cerrarbuscadorrr() {
    document.getElementById('id011').style.display = "none";
}
function cerrarbuscadorrrr() {
    document.getElementById('id0111').style.display = "none";
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



function generarorden() {
    accion = 1;
    document.getElementById('id_usuario').focus;
    // document.getElementById('proveedor').focus();
}

function anularorden() {
    accion = 3;
//    InputIdLectura();
}
function recuperarorden() {
    accion = 3;
}

function grabarorden() {
    switch (accion) {
        case 1:
            verificar();
            break;
        case 2:
            Preguntar();
            break;
        case 3:
            confirmar();
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

function agregarFilaorden() {
    var acu;
    var tindex;
    var filaspedidos;
    total = eval(precio_total.value);
    var cod = document.getElementById('id_mercaderia').value;
    var des = document.getElementById('descrip_mercaderia').value;
    var cant = document.getElementById('cantidad').value;
    var totalizador = cant * cod;
    
    document.getElementById('precio_total').value = total + totalizador  ;
//    var precio = document.getElementById('precio_articulo').value;
//    var calculo = cant * precio;

//    acu += calculo;
//    document.getElementById('montototalpedido').value = acu;
    tindex++;

    $('#tabladetallepedido').append("<tr onclick=gettdagregar(this);\'>\n\
            <td style=' width: 5%;'>" + cod + "</td>\n\
            <td style=' width: 60%;'>" + des + "</td>\n\
            <td style=' width: 5%;'>" + cant + "</td>\n\
            <td style=' width: 5%;'><img onclick=\"$(\'#prod" + cant + "\')updateMonto(" + cant + ")\" src='../Recursos/img/delete.png'/></td>\n\
            <td style=' width: 5%;'><img onclick=\"$(\'#prod" + tindex + "\').remove();updateMonto(" + tindex + ")\" src='../Recursos/img/delete.png'/></td>\n\
      </tr>");
    Limpiarmercaderia();
    document.getElementById('id_mercaderia').focus();
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

    $("#tabladetalleorden  tr").each(function () {
        //push => Agrega un nuevo elemento al Array [listaProductos]
        listamercaderia.push({
            id_mercaderia: $(this).find("td").eq(0).html(),
            cantidad: $(this).find("td").eq(2).html(),
            precio: $(this).find("td").eq(3).html(),
            total: $(this).find("td").eq(4).html()
//            monto: $(this).find("td").eq(3).html()
        });
    });



    PedidoJSON = {
        "bandera": ban,
        "id_orden": $('#id_orden').val().length <= 0 ? "0" : $('#id_orden').val(),
        "fecha": $('#fecha').val().length <= 0 ? "0" : $('#fecha').val(),
        "id_usuario": $('#id_usuario').val().length <= 0 ? "0" : $('#id_usuario').val(),
        "id_pedido": $('#id_pedido').val().length <= 0 ? "0" : $('#id_pedido').val(),
        "id_estado": $('#id_estadolistado').val().length <= 0 ? "0" : $('#id_estadolistado').val(),
        "id_proveedor": $('#id_proveedor').val().length <= 0 ? "0" : $('#id_proveedor').val(),
        "lista_mercaderias": listamercaderia.length <= 0 ? "0" : listamercaderia
    };
    envioo();
}
function envioo() {
    var xmlhttp = new XMLHttpRequest();   // new HttpRequest instance 
    xmlhttp.open("POST", "/AutoserviceWalker/OrdenCTRL");
    xmlhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xmlhttp.send(JSON.stringify(PedidoJSON));
}


function Recuperarorden() {
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
                    document.getElementById('id_proveedor').value = json[x].id_proveedor;
                    document.getElementById('descrip_proveedor').value = json[x].nombre_proveedor;
                    document.getElementById('id_pedido').value = json[x].id_pedido;
                    for (d in json[x].lista_mercaderias) {
                        $('#tabladetalleorden').append("<tr id=\'prod" + tindex + "\'>\n\
                                <td style=' width: 5%;'>" + json[x].lista_mercaderias[d].id_mercaderia + "</td>\n\
                                <td style=' width: 60%;'>" + json[x].lista_mercaderias[d].descripcion + "</td>\n\
                                <td style=' width: 5%;'>" + json[x].lista_mercaderias[d].cantidad + "</td>\n\
                                <td style=' width: 5%;'>" + json[x].lista_mercaderias[d].precio + "</td>\n\
                                <td style=' width: 5%;'>" + json[x].lista_mercaderias[d].total + "</td>\n\
                                <td style=' width: 5%;'><img onclick=\"$(\'#prod" + tindex + "\').remove();updateMonto(" + 444 + "," + tindex + ")\" src='../Recursos/img/update.png'/></td>\n\
                                <td style=' width: 5%;'><img onclick=\"$(\'#prod" + tindex + "\').remove();updateMonto(" + 555 + "," + tindex + ")\" src='../Recursos/img/delete.png'/></td>\n\
                          </tr>");

                    }
                }
            }
        }
    };
    xhr.open('POST', '/AutoserviceWalker/OrdenCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 2, id_orden: $('#id_orden').val()}));
    // 3. Specify your action, location and Send to the server - End
}



function getTDorden(obj) {
    var varTR = obj.getElementsByTagName('td');
    document.getElementById('id_orden').value = varTR[0].innerHTML;
    Recuperarorden();

    document.getElementById('buscardor_orden').style.display = "none";

}
function getTDproveedor(obj) {
    var varTR = obj.getElementsByTagName('td');
    document.getElementById('id_proveedor').value = varTR[0].innerHTML;
    document.getElementById('descrip_proveedor').value = varTR[1].innerHTML;
    document.getElementById('buscador_proveedor').style.display = "none";

}


function cerrarbuscadorproveedor() {
    document.getElementById('buscador_proveedor').style.display = "none";
}
function getTDpedido(obj) {
    var varTR = obj.getElementsByTagName('td');
    document.getElementById('id_pedido').value = varTR[0].innerHTML;
    document.getElementById('sucursal_pedido').value = varTR[2].innerHTML;
    RecuperarPedidos();
    document.getElementById('buscador_pedidos').style.display = "none";

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
    xhr.open('POST', '/AutoserviceWalker/OrdenCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 5}));
}


function CargarTablaProveedor() {
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
                    filas += "<tr onclick=getTDproveedor(this);>";
                    filas += "<td id=td1_" + i + ">" + json[i].id_proveedor + "</td>";
                    filas += "<td id=td2_" + i + " >" + json[i].nombre + "</td>";
                    filas += "</tr>";
                }
                document.getElementById("crpTbProveedor").innerHTML = filas;
            }
        }
    };
    // 2. Handle Response from Server - End

    // 3. Specify your action, location and Send to the server - Start   
    xhr.open('POST', '/AutoserviceWalker/OrdenCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 6}));
    // 3. Specify your action, location and Send to the server - End
}

function CargarTablaOrden() {
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
                    filas += "<td id=td1_" + i + ">" + json[i].id_pedido + "</td>";
                    filas += "<td id=td3_" + i + " >" + json[i].nombre_proveedor + "</td>";
                    filas += "</tr>";
                }
                document.getElementById("id_ordenlistado").innerHTML = filas;
            }
        }
    };
    // 2. Handle Response from Server - End

    // 3. Specify your action, location and Send to the server - Start   
    xhr.open('POST', '/AutoserviceWalker/OrdenCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 4}));
    // 3. Specify your action, location and Send to the server - End
}



function CargarTablaPedido() {
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
                    filas += "<tr onclick=getTDpedido(this);>";
                    filas += "<td id=td1_" + i + ">" + json[i].id_pedido + "</td>";
                    filas += "<td id=td2_" + i + ">" + json[i].fecha + "</td>";
                    filas += "<td id=td3_" + i + " >" + json[i].nombre_sucursal + "</td>";
                    filas += "</tr>";
                }
                document.getElementById("id_pedidolistado").innerHTML = filas;
            }
        }
    };
    // 2. Handle Response from Server - End

    // 3. Specify your action, location and Send to the server - Start   
    xhr.open('POST', '/AutoserviceWalker/OrdenCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 7}));
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

function FiltroPedidos() {
    var input, filter, table, tr, td, i;
    input = document.getElementById("filtrospedidos");
    filter = input.value.toUpperCase();
    table = document.getElementById("buscador_pedidos");
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



function FiltroOrden() {
    var input, filter, table, tr, td, i;
    input = document.getElementById("filtrosorden");
    filter = input.value.toUpperCase();
    table = document.getElementById("id09");
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




function RecuperarPedidos() {
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
                var total = 0;
                var d;
                var tindex;
                if (json[0].id_pedido === 0) {
                    alert('Este codigo no existe');
                    return;
                }
                for (x in json) {

                    document.getElementById('id_pedido').value = json[x].id_pedido;
                    for (d in json[x].lista_mercaderias) {
                        $('#tabladetalleorden').append("<tr id=\'prod" + tindex + "\'>\n\
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
                        total += json[x].lista_mercaderias[d].total;
                                
                    }
//                    debugger;
                    document.getElementById('precio_total').value = total;
                }
            }
        }
    };
    xhr.open('POST', '/AutoserviceWalker/OrdenCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 8, id_pedido: $('#id_pedido').val()}));
    // 3. Specify your action, location and Send to the server - End
}