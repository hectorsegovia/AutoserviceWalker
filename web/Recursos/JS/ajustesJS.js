var RegAjustesJSON;
var jsonCabDetP;
CargarTablaMercaderia();
CargarTablaSucursal();
CargarTablaMotivos();

function verificar() {
//    var camposucursal = $('#reg_compra').val();
    var campoobservacion = $('#observacion').val();
    if (campoobservacion === "") {
        alert('¡¡favor completar todos los campos!!');
    } else {
        prepararJsonRegAjustes(accion);
        alert('Registro Guardado');
        limpiarRegAjustes();
    }
}

function limpiarRegAjustes() {
    document.getElementById('id_ajuste').value = "";
    document.getElementById('observacion').value = "";
    document.getElementById('fecha_ajuste').value = "";
    document.getElementById('id_usuario').value = "";
    document.getElementById('id_sucursal').value = "";
    window.location.reload();
}

function Limpiarmercaderia(){
    document.getElementById('id_mercaderia').value = "";
    document.getElementById('descrip_mercaderia').value = "";
    document.getElementById('cantidad').value = "";
    document.getElementById('id_motivo').value = "";
    document.getElementById('nom_motivo').value = "";
}




function generarRegAjustes() {
    document.getElementById('observacion').focus;
    accion = 1;

    document.getElementById('id_mercaderia').disabled=false;
    
}
//
//function anularRegAjustes() {
//    accion = 2;
////    InputIdLectura();
//}
//function recuperarRegAjustes() {
//    accion = 3;
//}

function grabarRegAjustes() {
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
        prepararJsonRegAjustes(accion);
        alert('Registro Anulado');
        CargarTablaRegAjustes();
        limpiarRegAjustes();
        
    } else {
        limpiarRegAjustes();
    }
}

function Preguntar() {
    confirmar = confirm("Estas seguro que querés Modificar el registro???");
    if (confirmar) {
        prepararJsonRegAjustes(accion);
        alert('Registro Modificado');
        CargarTablaRegAjustes();
        limpiarRegAjustes();
    } else {
        limpiarRegAjustes();
// si pulsamos en cancelar
        ///alert('Registro eliminado');
    }
}

function agregarFilaAjustes() {
    var acu;
    var tindex;
    var filaspedidos;

    var idmer = document.getElementById('id_mercaderia').value;
    var mer = document.getElementById('descrip_mercaderia').value;
    var cant = document.getElementById('cantidad').value;
    var idmoti = document.getElementById('id_motivo').value;    
    var nommoti = document.getElementById('nom_motivo').value;
//    var precio = document.getElementById('precio_articulo').value;
//    var calculo = cant * precio;

//    acu += calculo;
//    document.getElementById('montototalpedido').value = acu;
    tindex++;

    $('#tabladetalleRegAjustes').append("<tr id=\'prod" + tindex + "\'>\n\
            <td style=' width: 5%;'>" + idmer + "</td>\n\
            <td style=' width: 60%;'>" + mer + "</td>\n\
            <td style=' width: 5%;'>" + cant + "</td>\n\
            <td style=' width: 5%;'>" + idmoti + "</td>\n\
            <td style=' width: 5%;'>" + nommoti + "</td>\n\
            <td style=' width: 5%;'><img onclick=\"$(\'#prod" + tindex + "\')updateMonto(" + tindex + ")\" src='../Recursos/img/update.png'/></td>\n\
            <td style=' width: 5%;'><img onclick=\"$(\'#prod" + tindex + "\').remove();updateMonto(" + tindex + ")\" src='../Recursos/img/delete.png'/></td>\n\
      </tr>");
    Limpiarmercaderia();
    document.getElementById('id_mercaderia').focus();
}



function prepararJsonRegAjustes(ban) {

//var jsonCabDet;
    var listamercaderia = [];

    $("#tabladetalleRegAjustes  tr").each(function () {
        //push => Agrega un nuevo elemento al Array [listaProductos]
        listamercaderia.push({
            id_mercaderia: $(this).find("td").eq(0).html(),
            cantidad: $(this).find("td").eq(2).html(),
            id_motivo: $(this).find("td").eq(3).html()
//            monto: $(this).find("td").eq(3).html()
        });
    });



    RegAjustesJSON = {
        "bandera": ban,
        "id_ajuste": $('#id_ajuste').val().length <= 0 ? "0" : $('#id_ajuste').val(),
        "observacion": $('#observacion').val().length <= 0 ? "0" : $('#observacion').val(),
        "fecha_ajuste": $('#fecha_ajuste').val().length <= 0 ? "0" : $('#fecha_ajuste').val(),
        "id_usuario": $('#id_usuario').val().length <= 0 ? "0" : $('#id_usuario').val(),
        "id_sucursal": $('#id_sucursal').val().length <= 0 ? "0" : $('#id_sucursal').val(),
        "lista_mercaderias": listamercaderia.length <= 0 ? "0" : listamercaderia
    };
    envioo();
}
function envioo() {
    var xmlhttp = new XMLHttpRequest();   // new HttpRequest instance 
    xmlhttp.open("POST", "/AutoserviceWalker/AjusteCTRL");
    xmlhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xmlhttp.send(JSON.stringify(RegAjustesJSON));
}


function CargarTablaSucursal() {
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
                    filas += "<tr onclick=getTDregSucursal(this);>";
                    filas += "<td id=td1_" + i + ">" + json[i].id_sucursal + "</td>";
                    filas += "<td id=td2_" + i + ">" + json[i].descripcion + "</td>";
                    filas += "</tr>";
                }
                document.getElementById("id_Consultar_Suc").innerHTML = filas;
            }
        }
    };
    // 2. Handle Response from Server - End

    // 3. Specify your action, location and Send to the server - Start   
    xhr.open('POST', '/AutoserviceWalker/AjusteCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 2}));
    // 3. Specify your action, location and Send to the server - End
}

function getTDregSucursal(obj) {
    var varTR = obj.getElementsByTagName('td');
    document.getElementById('id_sucursal').value = varTR[0].innerHTML;
    document.getElementById('nom_sucursal').value = varTR[1].innerHTML;
    document.getElementById('buscardor_Sucursal').style.display = "none";

}

function FiltroSucursal() {
    var input, filter, table, tr, td, i;
    input = document.getElementById("filtrosSucursal");
    filter = input.value.toUpperCase();
    table = document.getElementById("buscardor_Sucursal");
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


function cerrarbuscadorSucursal() {
    document.getElementById('buscardor_Sucursal').style.display = "none";
}

function CargarTablaMotivos() {
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
                    filas += "<tr onclick=getTDMotivo(this);>";
                    filas += "<td id=td1_" + i + ">" + json[i].id_motivo + "</td>";
                    filas += "<td id=td2_" + i + ">" + json[i].descripcion + "</td>";
                    filas += "</tr>";
                }
                document.getElementById("id_ConsultarTodosMotivo").innerHTML = filas;
            }
        }
    };
    // 2. Handle Response from Server - End

    // 3. Specify your action, location and Send to the server - Start   
    xhr.open('POST', '/AutoserviceWalker/AjusteCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 3}));
    // 3. Specify your action, location and Send to the server - End
}

function getTDMotivo(obj) {
    var varTR = obj.getElementsByTagName('td');
    document.getElementById('id_motivo').value = varTR[0].innerHTML;
    document.getElementById('nom_motivo').value = varTR[1].innerHTML;
    document.getElementById('buscador_Motivo').style.display = "none";
    agregarFilaAjustes();

}

function cerrarbuscadorMotivo() {
    document.getElementById('buscador_Motivo').style.display = "none";
}



function CargarTablaMercaderia() {
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
                    filas += "<tr onclick=getTDMerca(this);>";
                    filas += "<td id=td1_" + i + ">" + json[i].id_mercaderia + "</td>";
                    filas += "<td id=td2_" + i + " >" + json[i].descripcion + "</td>";
                    filas += "</tr>";
                }
                document.getElementById("crpTbmercaderias").innerHTML = filas;
            }
        }
    };
    // 2. Handle Response from Server - End

    // 3. Specify your action, location and Send to the server - Start   
    xhr.open('POST', '/AutoserviceWalker/AjusteCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 4}));
    // 3. Specify your action, location and Send to the server - End
}

function getTDMerca(obj) {
    var varTR = obj.getElementsByTagName('td');  
    document.getElementById('id_mercaderia').value = varTR[0].innerHTML;
    document.getElementById('descrip_mercaderia').value = varTR[1].innerHTML;
    document.getElementById('buscardormercaderias').style.display = "none";
    document.getElementById('cantidad').focus();

}

function FiltroMercaderiasAjus() {
    var input, filter, table, tr, td, i;
    input = document.getElementById("filtrosmercaderias");
    filter = input.value.toUpperCase();
    table = document.getElementById("buscardormercaderias");
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

function cerrarbuscadorMercaderia() {
    document.getElementById('buscardormercaderias').style.display = "none";
}


