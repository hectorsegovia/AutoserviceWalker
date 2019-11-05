var PedidoJSON;
var jsonCabDetP;
CargarTablaEstado();
CargarTablaRegCompra();
CargarOrdenCompras();

function verificar() {
//    var camposucursal = $('#reg_compra').val();
    var campoobservacion = $('#id_ordenn').val();
    if (campoobservacion === "") {
        alert('¡¡favor completar todos los campos!!');
    } else {
        prepararJsonRegCompra(accion);
        alert('Registro Guardado');
        limpiarRegCompra();
    }
}

function limpiarRegCompra() {
    document.getElementById('reg_compra').value = "";
    document.getElementById('nro_factura').value = "";
    document.getElementById('fecha_fact').value = "";
    document.getElementById('id_estadolistado').value = "";
    document.getElementById('id_user').value = "";
    document.getElementById('id_ordenn').value = "";
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

function grabarRegCompras() {
    switch (accion) {
        case 1:
            verificar();
            break;
        case 3:
            Preguntar();
            break;
        case 2:
            confirmar();
            break;
    }
}



function confirmar() {
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
        "id_regcompra": $('#reg_compra').val().length <= 0 ? "0" : $('#reg_compra').val(),
        "num_factura": $('#nro_factura').val().length <= 0 ? "0" : $('#nro_factura').val(),
        "fecha_factura": $('#fecha_fact').val().length <= 0 ? "0" : $('#fecha_fact').val(),
        "id_estado": $('#id_estadolistado').val().length <= 0 ? "0" : $('#id_estadolistado').val(),
        "id_usuario": $('#id_user').val().length <= 0 ? "0" : $('#id_user').val(),
        "id_ordencompra": $('#id_ordenn').val().length <= 0 ? "0" : $('#id_ordenn').val(),
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
                var tindex;
                if (json[0].id_orden === 0) {
                    alert('Este codigo no existe');
                    return;
                }
                for (x in json) {
                    document.getElementById('nro_factura').value = json[x].num_factura;
                    document.getElementById('fecha_fact').value = json[x].fecha_factura;
                    document.getElementById('id_estadolistado').value = json[x].id_estado;
                    document.getElementById('id_ordenn').value = json[x].id_ordencompra;
                    for (d in json[x].lista_mercaderias) {
                        $('#tabladetalleRegCompra').append("<tr id=\'prod" + tindex + "\'>\n\
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
    xhr.open('POST', '/AutoserviceWalker/reg_comprasCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 3, id_regcompra: $('#reg_compra').val()}));
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
                var tindex;
                for (x in json) {
                    document.getElementById('id_ordenn').value = json[x].id_orden;
                    for (d in json[x].lista_mercaderias) {
                        $('#tabladetalleRegCompra').append("<tr id=\'prod" + tindex + "\'>\n\
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
                    filas += "<td id=td1_" + i + ">" + json[i].id_regcompra + "</td>";
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
    document.getElementById('reg_compra').value = varTR[0].innerHTML;
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

function CargarOrdenCompras() {
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




