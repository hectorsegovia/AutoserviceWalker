

CargarTablaCiudad();
CargarTablaTipocliente();
CargarTablaCliente();
var datos;
var accion;
var MayusCargo;
var nombre;
var apellido;
var direccion;
var cedula;
var celular;


function comparar() {
    var estado = Boolean(false);
    var nombre = $('#nombre').val();
    var ruc = $('#ruc').val();
    var ciudad = $('#ciudad').val();
    nombre = nombre.toUpperCase();
    ruc = ruc.toUpperCase();
    ciudad = ciudad.toUpperCase();
    $("#crpTbCliente  tr").each(function () {
        if (nombre === $(this).find("td").eq(1).html() & ruc === $(this).find("td").eq(2).html() & ciudad === $(this).find("td").eq(3).html()) {
            alert("Ya Existe el Registro");
            estado = true;
        }
    });
    return estado;

}

function verificarCliente() {
    nombre = $('#nombre').val();
    ruc = $('#ruc').val();
    ciudad = $('#ciudad').val();
    tipocliente = $('#tipocliente').val();
    nombre = nombre.toUpperCase();
    ruc = ruc.toUpperCase();
    ciudad = ciudad.toUpperCase();
    tipocliente = tipocliente.toUpperCase();

    if (nombre === "" && ruc === "" && ciudad === "" && tipocliente === "") {
        alert('¡¡favor completar todos los campos!!');
        limpieee();
    } else {
        if (comparar() === false) {
            construirJSON(accion);
            alert('Registro Guardados');
            limpieee();
        }
        limpieee();
    }
}

function Preguntar() {
    confirmar = confirm("Estas seguro que querés Modificar el registro???");
    if (confirmar) {
        verificarCliente();
    } else {
        limpieee();
    }
}

function limpieee() {
    document.getElementById('id_cliente').value = "";
    document.getElementById('nombre').value = "";
    document.getElementById('ruc').value = "";
    document.getElementById('direccion').value = "";
    document.getElementById('telefono').value = "";
    document.getElementById('tipociudad').value = "";
    CargarTablaCliente();
        location.reload();
}

function confirmar() {
    confirmar = confirm("Estas seguro que querés eliminar el registro???");
    if (confirmar) {
        construirJSON(accion);
        CargarTablaCliente();
        alert('Registro eliminado');
        limpieee();
    } else {
        limpieee();
// si pulsamos en cancelar
        ///alert('Registro eliminado');
    }
}



function construirJSON(ban) {
    // alert(ban);
    datos = {
        bandera: ban,
        id_cliente: (document.getElementById('id_cliente').value === "") ? "0" : document.getElementById('id_cliente').value,
        nombre: nombre,
        ruc: ruc,
        id_ciudad: ciudad,
        telefono: document.getElementById('telefono').value,
        direccion: document.getElementById('direccion').value,
        ticliente: document.getElementById('tipocliente').value
    };
    envio();
    CargarTablaCliente();

}

function cargarCliente() {
    construirJSON(4);
}

function InputIdLectura() {
    document.getElementById('id_cliente').disabled = false;
    document.getElementById('id_cliente').value = "";
    document.getElementById('id_cliente').readOnly = false;
    document.getElementById('id_cliente').placeholder = "Ingrese ID";
    document.getElementById('filtroscliente').focus();
}
function InputIdLecturaAgregar() {
    document.getElementById('id_cliente').disabled = true;
//    document.getElementById('id_cargo').value= "1231";
}

function validarInputsRegistroCliente() {
    //Grupo de validaciones
    //
    //document.getElementById("opcionGrabarPerfil").disabled = true;
    document.getElementById("opcionGrabarCliente").focus();

}

function agregarCliente() {
    accion = 1;
    InputIdLecturaAgregar();
    document.getElementById('nombre').focus();
}


function modificarCliente() {
    accion = 2;
    InputIdLectura();
    //construirJSON(2);
}

function eliminarCliente() {
    accion = 3;
    InputIdLectura();
    // construirJSON(3);
}

function grabarCliente() {
    switch (accion) {
        case 1:
            verificarCliente();
            break;
        case 2:
            Preguntar();
            break;
        case 3:
            confirmar();
            break;
    }

}

function envio() {
    var xmlhttp = new XMLHttpRequest();   // new HttpRequest instance 
    xmlhttp.open("POST", "/AutoserviceWalker/ClienteCTRL");
    xmlhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xmlhttp.send(JSON.stringify(datos));
    if (xmlhttp.status === 200) {

        alert('respuesta exitosa');
    } else {
        // alert('Error durante la respuesta ');
    }
    CargarTablaCliente();
}

function recuperarClienteUnico() {
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
                if (json[0].id_cliente === 0) {
                    alert('El Codigo Que Ingreso ¡¡No Existe!!, o ha ¡¡Sido Cambiado!!');
                    limpieee();
                    return;
                }
                var i;
                for (i = 0; i < json.length; i++) {
                    document.getElementById('id_cliente').value = json[i].id_cliente;
                    document.getElementById('nombre').value = json[i].nombre;
                    document.getElementById('ruc').value = json[i].ruc;
                    document.getElementById('ciudad').value = json[i].id_ciudad;
                    document.getElementById('telefono').value = json[i].telefono;
                    document.getElementById('direccion').value = json[i].direccion;
                    document.getElementById('tipocliente').value = json[i].ticliente;
                    document.getElementById('nombre').focus();

                }

            }

        }
    };
    // 2. Handle Response from Server - End

    // 3. Specify your action, location and Send to the server - Start   
    xhr.open('POST', '/AutoserviceWalker/ClienteCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 5, id_cliente: document.getElementById('id_cliente').value}));
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
                for (i = 0; i < json.length; i++) {
                    filas += "<tr onclick=getTDcliente(this);>";
                    filas += "<td id=td1_" + i + ">" + json[i].id_cliente + "</td>";
                    filas += "<td id=td2_" + i + ">" + json[i].nombre + "</td>";
                    filas += "<td id=td3_" + i + ">" + json[i].ruc + "</td>";
                    filas += "<td id=td4_" + i + ">" + json[i].nombre_ciudad + "</td>";
                    filas += "<td id=td5_" + i + ">" + json[i].telefono + "</td>";
                    filas += "<td id=td7_" + i + ">" + json[i].nombre_ticliente + "</td>";
                    filas += "</tr>";
                }
                document.getElementById("crpTbCliente").innerHTML = filas;

            }
        }
    };
    // 2. Handle Response from Server - End

    // 3. Specify your action, location and Send to the server - Start   
    xhr.open('POST', '/AutoserviceWalker/ClienteCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 4}));
    // 3. Specify your action, location and Send to the server - End
}

function FiltroCliente() {
    var input, filter, table, tr, td, i;
    input = document.getElementById("filtroscliente");
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


function getTDcliente(obj) {
//    recuperarClienteUnico();
    var varTR = obj.getElementsByTagName('td');
    document.getElementById('id_cliente').value = varTR[0].innerHTML;
    recuperarClienteUnico();
    document.getElementById('id01').style.display = "none";
//    document.getElementById('nombre').value = varTR[1].innerHTML;
//    document.getElementById('ruc').value = varTR[2].innerHTML;
//    document.getElementById('telefono').value = varTR[5].innerHTML;
//    document.getElementById('ciudad').value = varTR[4].innerHTML;
//    document.getElementById('direccion').value = varTR[3].innerHTML;
//    document.getElementById('tipo_cliente').value = varTR[8].innerHTML;
//    document.getElementById('filtroscliente').value = "";
//    CargarTablaEmpleados();
    document.getElementById('id01').style.display = "none";
}

function cerrarbuscador() {
    document.getElementById('id01').style.display = "none";
}


function CargarTablaCiudad() {
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

                    filas += "<option value= " + json[i].id_ciudad + ">" + json[i].descripcion + "</option>";
                }
                document.getElementById("ciudad").innerHTML = filas;

            }
        }
    };
    xhr.open('POST', '/AutoserviceWalker/ClienteCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 7}));
}


function CargarTablaTipocliente() {
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

                    filas += "<option value= " + json[i].id_ticliente + ">" + json[i].descripcion + "</option>";
                }
                document.getElementById("tipocliente").innerHTML = filas;

            }
        }
    };
    xhr.open('POST', '/AutoserviceWalker/ClienteCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 6}));
}


