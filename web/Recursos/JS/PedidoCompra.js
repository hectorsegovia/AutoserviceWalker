var PedidoJSON;
var jsonCabDetP;
CargarTablaMercaderia();
CargarTablaSucursal();
CargarTablaPedido();
CargarTablaCodigo();



function verificar() {
    var camposucursal = $('#id_cucursal').val();
    var campoobservacion = $('#observacion').val();



    if (camposucursal === "" | campoobservacion === "") {
        alert('¡¡favor completar todos los campos!!');
    } else {
        prepararJsonPedidos(accion);
        alert('Registro Guardado');
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
function limpiarpedido() {
    document.getElementById('id_pedido').value = "";
    document.getElementById('id_sucursal').value = "";
    document.getElementById('descrip_sucursal').value = "";
    document.getElementById('observacion').value = "";
    document.getElementById('id_usuario').value = "";
    document.getElementById('descrip_usuario').value = "";
    window.location.reload();
}


function InputIdLectura() {
    document.getElementById('id_pedido').disabled = false;
    document.getElementById('id_pedido').value = "";
    document.getElementById('id_pedido').readOnly = false;
    document.getElementById('id_pedido').placeholder = "Ingrese ID";
    document.getElementById('id_pedido').focus();
}


function InputIdLecturaagregar() {
    document.getElementById('id_pedido').disabled = true;
    document.getElementById('observacion').focus();
}

function generarpedido() {
    accion = 1;
    InputIdLecturaagregar();
    // document.getElementById('proveedor').focus();
}

function anularpedido() {
    accion = 3;
    document.getElementById('id0111').style.display = 'block';
//    InputIdLectura();
}
function recuperarPedidos() {
    accion = 3;
    InputIdLectura();
}

function aprobarpedido() {
    accion = 7;
    aprobar();
}





function grabarPedido() {
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
        prepararJsonPedidos(accion);
        alert('Registro Anulado');
        limpiarpedido();
        CargarTablapedido();
    } else {
        limpiarpedido();
    }
}


function aprobar() {
    confirmar = confirm("Estas seguro que querés aprobar el registro???");
    if (confirmar) {
        prepararJsonPedidos(accion);
        alert('Registro Aprobado');
        limpiarpedido();
        CargarTablapedido();
    } else {
        limpiarpedido();
    }
}

function Preguntar() {
    confirmar = confirm("Estas seguro que querés Modificar el registro???");
    if (confirmar) {
        prepararJsonPedidos(accion);
        alert('Registro Modificado');
        CargarTablapedido();
        limpiarpedido();
    } else {
        limpiarpedido();
// si pulsamos en cancelar
        ///alert('Registro eliminado');
    }
}

function agregarFilaPedidos() {
    var acu;
    var tindex;
    var filaspedidos;

    var cod = document.getElementById('id_mercaderia').value;
    var des = document.getElementById('descrip_mercaderia').value;
    var cant = document.getElementById('cantidad').value;
//    var precio = document.getElementById('precio_articulo').value;
//    var calculo = cant * precio;

//    acu += calculo;
//    document.getElementById('montototalpedido').value = acu;
    tindex++;

    $('#tabladetallepedido').append("<tr id=\'prod" + tindex + "\'>\n\
            <td style=' width: 5%;'>" + cod + "</td>\n\
            <td style=' width: 60%;'>" + des + "</td>\n\
            <td style=' width: 5%;'>" + cant + "</td>\n\
            <td style=' width: 5%;'><img onclick=\"$(\'#prod" + tindex + "\')updateMonto(" + tindex + ")\" src='../Recursos/img/update.png'/></td>\n\
            <td style=' width: 5%;'><img onclick=\"$(\'#prod" + tindex + "\').remove();updateMonto(" + tindex + ")\" src='../Recursos/img/delete.png'/></td>\n\
      </tr>");
    Limpiarmercaderia();
    document.getElementById('id_mercaderia').focus();
}


function Limpiarmercaderia() {
    document.getElementById('id_mercaderia').value = "";
    document.getElementById('descrip_mercaderia').value = "";
    document.getElementById('cantidad').value = "";
}


function prepararJsonPedidos(ban) {
//var jsonCabDet;
    var listamercaderia = [];

    $("#tabladetallepedido  tr").each(function () {
        //push => Agrega un nuevo elemento al Array [listaProductos]
        listamercaderia.push({
            id_mercaderia: $(this).find("td").eq(0).html(),
            cantidad: $(this).find("td").eq(2).html()
//            monto: $(this).find("td").eq(3).html()
        });
    });



    PedidoJSON = {
        "bandera": ban,
        "id_pedido": $('#id_pedido').val().length <= 0 ? "0" : $('#id_pedido').val(),
        "fecha": $('#fecha').val().length <= 0 ? "0" : $('#fecha').val(),
        "estado": $('#id_estado').val().length <= 0 ? "0" : $('#id_estado').val(),
        "id_usuario": $('#id_usuario').val().length <= 0 ? "0" : $('#id_usuario').val(),
        "id_sucursal": $('#id_sucursal').val().length <= 0 ? "0" : $('#id_sucursal').val(),
        "observacion": $('#observacion').val().length <= 0 ? "0" : $('#observacion').val(),
        "lista_mercaderias": listamercaderia.length <= 0 ? "0" : listamercaderia
    };
    alert('Registro Guardado');
    envioo();
}
function envioo() {
    var xmlhttp = new XMLHttpRequest();   // new HttpRequest instance 
    xmlhttp.open("POST", "/AutoserviceWalker/PedidoCompraCTRL");
    xmlhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xmlhttp.send(JSON.stringify(PedidoJSON));
    CargarTablapedido();
}


function RecuperarPedido() {
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
                if (json[0].id_pedido === 0) {
                    alert('Este codigo no existe');
                    return;
                }
                for (x in json) {

                    document.getElementById('fecha').value = json[x].fecha;
                    document.getElementById('id_usuario').value = json[x].id_usuario;
                    document.getElementById('descrip_usuario').value = json[x].nombre_usuario;
                    document.getElementById('id_sucursal').value = json[x].id_sucursal;
                    document.getElementById('descrip_sucursal').value = json[x].nombre_sucursal;
                    document.getElementById('observacion').value = json[x].observacion;
                    document.getElementById('id_estado').value = json[x].estado;


                    for (d in json[x].lista_mercaderias) {
                        $('#tabladetallepedido').append("<tr id=\'prod" + tindex + "\'>\n\
                                <td style=' width: 5%;'>" + json[x].lista_mercaderias[d].id_mercaderia + "</td>\n\
                                <td style=' width: 60%;'>" + json[x].lista_mercaderias[d].descripcion + "</td>\n\
                                <td style=' width: 5%;'>" + json[x].lista_mercaderias[d].cantidad + "</td>\n\
                                <td style=' width: 5%;'><img onclick=\"$(\'#prod" + tindex + "\').remove();updateMonto(" + 444 + "," + tindex + ")\" src='../Recursos/img/update.png'/></td>\n\
                                <td style=' width: 5%;'><img onclick=\"$(\'#prod" + tindex + "\').remove();updateMonto(" + 555 + "," + tindex + ")\" src='../Recursos/img/delete.png'/></td>\n\
                          </tr>");

                    }
                }
            }
        }
    };
    xhr.open('POST', '/AutoserviceWalker/PedidoCompraCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 2, id_pedido: $('#id_pedido').val()}));
    // 3. Specify your action, location and Send to the server - End
}


function getTD(obj) {
    var varTR = obj.getElementsByTagName('td');
    document.getElementById('id_mercaderia').value = varTR[0].innerHTML;
    document.getElementById('descrip_mercaderia').value = varTR[1].innerHTML;
    document.getElementById('buscardormercaderia').style.display = "none";
    document.getElementById('cantidad').focus();

}
function getTDsucursal(obj) {
    var varTR = obj.getElementsByTagName('td');
    document.getElementById('id_sucursal').value = varTR[0].innerHTML;
    document.getElementById('descrip_sucursal').value = varTR[1].innerHTML;
    document.getElementById('id011').style.display = "none";
    document.getElementById('observacion').focus();
}
function getTDpedido(obj) {
    var varTR = obj.getElementsByTagName('td');
    document.getElementById('id_pedido').value = varTR[0].innerHTML;
    document.getElementById('id_pedido').value = varTR[0].innerHTML;
    RecuperarPedido();
    document.getElementById('id0111').style.display = "none";

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
                    filas += "<tr onclick=getTD(this);>";
                    filas += "<td id=td1_" + i + ">" + json[i].id_mercaderia + "</td>";
                    filas += "<td id=td2_" + i + " >" + json[i].descripcion + "</td>";
                    filas += "<td id=td3_" + i + " >" + json[i].cantidadd + "</td>";
                    filas += "</tr>";
                }
                document.getElementById("crpTbmercaderias").innerHTML = filas;
            }
        }
    };
    // 2. Handle Response from Server - End

    // 3. Specify your action, location and Send to the server - Start   
    xhr.open('POST', '/AutoserviceWalker/PedidoCompraCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 4}));
    // 3. Specify your action, location and Send to the server - End
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
                    filas += "<tr onclick=getTDsucursal(this);>";
                    filas += "<td id=td1_" + i + ">" + json[i].id_sucursal + "</td>";
                    filas += "<td id=td2_" + i + " >" + json[i].descripcion + "</td>";
                    filas += "</tr>";
                }
                document.getElementById("crpTbSucursal").innerHTML = filas;
            }
        }
    };
    // 2. Handle Response from Server - End

    // 3. Specify your action, location and Send to the server - Start   
    xhr.open('POST', '/AutoserviceWalker/PedidoCompraCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 5}));
    // 3. Specify your action, location and Send to the server - End
}


function CargarTablaCodigo() {
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
                document.getElementById("id_pedido").value = json[i].id_pedido;
            }
            }
        }
    };
    // 2. Handle Response from Server - End

    // 3. Specify your action, location and Send to the server - Start   
    xhr.open('POST', '/AutoserviceWalker/PedidoCompraCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 8}));
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
                document.getElementById("crpTbPedidos").innerHTML = filas;
            }
        }
    };
    // 2. Handle Response from Server - End

    // 3. Specify your action, location and Send to the server - Start   
    xhr.open('POST', '/AutoserviceWalker/PedidoCompraCTRL');
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


function FiltroPedidos() {
    var input, filter, table, tr, td, i;
    input = document.getElementById("filtrospedidos");
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






function FiltroSucursal() {
    var input, filter, table, tr, td, i;
    input = document.getElementById("filtrossucursal");
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



function FiltroDeposito() {
    var input, filter, table, tr, td, i;
    input = document.getElementById("filtrosdeposito");
    filter = input.value.toUpperCase();
    table = document.getElementById("busacadordeposito");
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

function getTDdeposito(obj) {
    var varTR = obj.getElementsByTagName('td');
    document.getElementById('id_deposito').value = varTR[0].innerHTML;
    document.getElementById('descripcion').value = varTR[1].innerHTML;
    document.getElementById('busacadordeposito').style.display = "none";
}



function CargarTablaDeposito() {
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
                    filas += "<tr onclick=getTDdeposito(this);>";
                    filas += "<td id=td1_" + i + ">" + json[i].id_deposito + "</td>";
                    filas += "<td id=td2_" + i + " >" + json[i].descripcion + "</td>";
                    filas += "</tr>";
                }
                document.getElementById("crpTbDeposito").innerHTML = filas;
            }
        }
    };
    // 2. Handle Response from Server - End

    // 3. Specify your action, location and Send to the server - Start   
    xhr.open('POST', '/AutoserviceWalker/PedidoCompraCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 7}));
    // 3. Specify your action, location and Send to the server - End
}


function cerrarbuscadordeposito() {
    document.getElementById('busacadordeposito').style.display = "none";
}