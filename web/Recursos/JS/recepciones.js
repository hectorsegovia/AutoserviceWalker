var PedidoJSON;
var jsonCabDetP;
CargarTablaRegCompra();
CargarOrdenComprass();
CargarTablaCodigosrecepcion();


function verificar() {
    var campoobservacion = $('#id_ordenn').val();
    if (campoobservacion === "") {
        alert('¡¡favor completar todos los campos!!');
    } else {
        prepararJsonRegCompra(accion);
        alert('Registro Guardado');
    }
}

function limpiarrecepcion() {
    document.getElementById('id_recepcion').value = "";
    document.getElementById('id_ordenn').value = "";
    document.getElementById('id_proveedor').value = "";
    document.getElementById('observacion').value = "";
    document.getElementById('nombre_proveedor').value = "";
    document.getElementById('tabladetalleRegCompra').value = "";
    window.location.reload();
}




function generarrecepcion() {
    accion = 1;
    document.getElementById('id_user').focus;
}

function anularRecepcion() {
    accion = 2;
//    InputIdLectura();
}
function recuperarrecepcion() {
    accion = 3;
}

function aprobarrecepcion() {
    accion = 7;
    aprobar();
}

function aprobar() {
    confirmar = confirm("Estas seguro que querés aprobar el registro???");
    if (confirmar) {
        prepararJsonRegCompra(accion);
        alert('Registro Aprobado');
        limpiarrecepcion();
        CargarTablaRegCompra();
    } else {
        limpiarrecepcion();
    }
}


function grabarRecepcion() {
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
        limpiarrecepcion();

    } else {
        limpiarrecepcion();
    }
}

function Preguntar() {
    confirmar = confirm("Estas seguro que querés Modificar el registro???");
    if (confirmar) {
        prepararJsonRegCompra(accion);
        alert('Registro Modificado');
        CargarTablaRegCompra();
        limpiarrecepcion();
    } else {
        limpiarrecepcion();
// si pulsamos en cancelar
        ///alert('Registro eliminado');
    }
}



function gettdagregar(obj) {
    var varTR = obj.getElementsByTagName('td');
    document.getElementById('id_mercaderia').value = varTR[0].innerHTML;
    document.getElementById('descrip_mercaderia').value = varTR[1].innerHTML;
    document.getElementById('cantdad').style.display = "none";
}



function Parcial() {
    confirmar = confirm("Recibir de Forma Parcial");
    if (confirmar) {
        prepararJsonRegCompra(accion);
        alert('Registro Guardado');
        CargarTablaRegCompra();
        limpiarrecepcion();
    } else {
        limpiarrecepcion();
    }
}




function verificarw(obj) {
  var varTR = obj.getElementsByTagName('td');
  var cantidad = varTR[2].innerHTML;
  var cantidad_recibida = varTR[3].innerHTML;
  if (cantidad_recibida < cantidad){
      Parcial();
  }else{
      grabarRecepcion();
  }
    

}

function prepararJsonRegCompra(ban) {

//var jsonCabDet;
    var listamercaderia = [];

    $("#tabladetalleRecepciones  tr").each(function () {
        //push => Agrega un nuevo elemento al Array [listaProductos]
        listamercaderia.push({
            id_mercaderia: $(this).find("td").eq(0).html(),
            cantidad: $(this).find("td").eq(2).html(),
            cantidad_recibida: parseInt($(this).find("td").eq(3).html())

        });
    });



    RegCompraJSON = {
        "bandera": ban,
        "id_recepcion": $('#id_recepcion').val().length <= 0 ? "0" : $('#id_recepcion').val(),
        "fecha": $('#fecha_recepcion').val().length <= 0 ? "0" : $('#fecha_recepcion').val(),
        "id_ordencompra": $('#id_ordenn').val().length <= 0 ? "0" : $('#id_ordenn').val(),
        "estado": $('#id_estado').val().length <= 0 ? "0" : $('#id_estado').val(),
        "id_usuario": $('#id_user').val().length <= 0 ? "0" : $('#id_user').val(),
        "observacion": $('#observacion').val().length <= 0 ? "0" : $('#observacion').val(),
        
        "lista_mercaderias": listamercaderia.length <= 0 ? "0" : listamercaderia
    };
    envioo();
}
function envioo() {
    var xmlhttp = new XMLHttpRequest();   // new HttpRequest instance 
    xmlhttp.open("POST", "/AutoserviceWalker/RecepcionesCTRL");
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
                        $('#tabladetalleRecepciones').append("<tr id=\'prod" + tindex + "\'>\n\
                                <td style=' width: 5%;'>" + json[x].lista_mercaderias[d].id_mercaderia + "</td>\n\
                                <td style=' width: 60%;'>" + json[x].lista_mercaderias[d].descripcion + "</td>\n\
                                \n\<td contenteditable='true' style=' width: 5%;'>" + json[x].lista_mercaderias[d].cantidad + "</td>\n\
                                \n\<td contenteditable='true' style=' width: 5%;'>" + 0 + "</td>\n\
                                <td style=' width: 5%;'><img onclick=\"$(\'#prod" + tindex + "\').remove();updateMonto(" + 444 + "," + tindex + ")\" src='../Recursos/img/update.png'/></td>\n\
                                <td style=' width: 5%;'><img onclick=\"$(\'#prod" + tindex + "\').remove();updateMonto(" + 555 + "," + tindex + ")\" src='../Recursos/img/delete.png'/></td>\n\
                          </tr>");

                    }
                  
                }
            }
        }
    };
    xhr.open('POST', '/AutoserviceWalker/RecepcionesCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 4, id_recepcion: $('#id_recepcion').val()}));
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
                    filas += "<td id=td1_" + i + ">" + json[i].id_recepcion + "</td>";
                    filas += "<td id=td1_" + i + ">" + json[i].id_ordencompra + "</td>";
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
    xhr.open('POST', '/AutoserviceWalker/RecepcionesCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 6}));
    // 3. Specify your action, location and Send to the server - End
}

function getTDorden(obj) {
    var varTR = obj.getElementsByTagName('td');
    document.getElementById('id_recepcion').value = varTR[0].innerHTML;
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



function CargarTablaCodigosrecepcion() {
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
                var idTD = "td_";
                for (i = 0; i < json.length; i++) {
                document.getElementById("id_recepcion").value = json[i].id_recepcion;
            }
            }
        }
    };
    // 2. Handle Response from Server - End

    // 3. Specify your action, location and Send to the server - Start   
    xhr.open('POST', '/AutoserviceWalker/RecepcionesCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 8}));
    // 3. Specify your action, location and Send to the server - End
}
