
CargarTablaCaja();
CargarTablaSucursal();
CargarTablaApertura();
CargarTablaResponsables();
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
    var fecha = $('#fecha').val();
    var montototal = $('#efectivo').val();
    var caja = $('#id_caja').val();
    
    fecha = fecha.toUpperCase();
    montototal = montototal.toUpperCase();
    caja = caja.toUpperCase();
    $("#crpTbApertura  tr").each(function () {
        if (fecha === $(this).find("td").eq(1).html() & montototal === $(this).find("td").eq(2).html() & caja === $(this).find("td").eq(3).html()) {
            alert("Ya Existe el Registro");
            estado = true;
        }
    });
    return estado;

}

function verificarApertura() {
    fecha = $('#fecha').val();
    efectivo  = $('#efectivo').val();
    fecha = fecha.toUpperCase();
    efectivo = efectivo.toUpperCase();
    
    if (fecha === "" && efectivo === "") {
        alert('¡¡favor completar todos los campos!!');
        limpieeezas();
    } else {
        if (comparar() === false) {
            construirJSON(accion);
            alert('Registro Guardados');
            limpieeezas();
        }
        limpieeezas();
    }
}

function Preguntar() {
    confirmar = confirm("Estas seguro que querés Modificar el registro???");
    if (confirmar) {
        verificarApertura();
    } else {
        limpieeezas();
    }
}

function limpieeezas() {
    document.getElementById('id_apertura').value = "";
    document.getElementById('efectivo').value = "";
    document.getElementById('tarjeta').value = "";
    document.getElementById('cheque').value = "";
    location.reload();
    CargarTablaApertura();
}

function confirmar() {
    confirmar = confirm("Estas seguro que querés eliminar el registro???");
    if (confirmar) {
        construirJSON(accion);
        CargarTablaApertura();
        alert('Registro eliminado');
        limpieeezas();
    } else {
        limpieeezas();
// si pulsamos en cancelar
        ///alert('Registro eliminado');
    }
}
function filtroaperturafocuss() {
    document.getElementById('filtrosapertura').focus();
}


function construirJSON(ban) {
    alert('llegueee');
    // alert(ban);
    datos = {
        bandera: ban,
        id_apertura: (document.getElementById('id_apertura').value === "") ? "0" : document.getElementById('id_apertura').value,
        fecha: fecha,
        id_usuario: document.getElementById('id_usuario').value,
        montototal: document.getElementById('efectivo').value,
        id_caja: document.getElementById('id_caja').value,
        fecha_cierre: document.getElementById('fecha_cierre').value,
        id_responsable: document.getElementById('id_responsable').value,
        estado: document.getElementById('id_estado').value

    };
    envio();
    CargarTablaApertura();

}



function InputIdLectura() {
    document.getElementById('id_apertura').disabled = false;
    document.getElementById('id_apertura').value = "";
    document.getElementById('id_apertura').readOnly = false;
    document.getElementById('id_apertura').placeholder = "Ingrese ID";
    document.getElementById('filtrosapertura').focus();
}
function InputIdLecturaAgregar() {
    document.getElementById('id_apertura').disabled = true;
//    document.getElementById('id_cargo').value= "1231";
}


function agregarApertura() {
    accion = 1;
    InputIdLecturaAgregar();
    document.getElementById('efectivo').focus();
}


function modificarApertura() {
    accion = 2;

    //construirJSON(2);
}

function eliminarApertura() {
    accion = 3;

    // construirJSON(3);
}

function grabarApertura() {
    switch (accion) {
        case 1:
            verificarApertura();
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
    xmlhttp.open("POST", "/AutoserviceWalker/AperturacajaCTRL");
    xmlhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xmlhttp.send(JSON.stringify(datos));
    if (xmlhttp.status === 200) {

        alert('respuesta exitosa');
    } else {
        // alert('Error durante la respuesta ');
    }
    CargarTablaApertura();
}

function recuperarAperturaUnico() {
    alert("Llegueeeeee");
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
                if (json[0].id_apertura === 0) {
                    alert('El Codigo Que Ingreso ¡¡No Existe!!, o ha ¡¡Sido Cambiado!!');
                    limpieeezas();
                    return;
                }
                var i;
                for (i = 0; i < json.length; i++) {
                    document.getElementById('id_apertura').value = json[i].id_apertura;
                    document.getElementById('id_caja').value = json[i].id_caja;
                    document.getElementById('id_usuario').value = json[i].id_usuario;
                    document.getElementById('fecha').value = json[i].fecha;
                    document.getElementById('efectivo').value = json[i].montoefectivo;
                    document.getElementById('id_responsable').value = json[i].id_responsable;
                    document.getElementById('fecha_cierre').value = json[i].fecha_cierre;
                    document.getElementById('id_estado').value = json[i].estado;
                }
            }
        }
    };
    // 2. Handle Response from Server - End
    // 3. Specify your action, location and Send to the server - Start   
    xhr.open('POST', '/AutoserviceWalker/AperturacajaCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 5, id_apertura: document.getElementById('id_apertura').value}));
    // 3. Specify your action, location and Send to the server - End
}

function CargarTablaApertura() {
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
                    filas += "<tr onclick=getTDapertura(this);>";
                    filas += "<td id=td1_" + i + ">" + json[i].id_apertura + "</td>";
                    filas += "<td id=td2_" + i + ">" + json[i].nombre_caja + "</td>";
                    filas += "<td id=td3_" + i + ">" + json[i].nombre_usuario + "</td>";
                    filas += "<td id=td4_" + i + ">" + json[i].fecha + "</td>";
                    filas += "</tr>";
                }
                document.getElementById("crpTbApertura").innerHTML = filas;

            }
        }
    };
    // 2. Handle Response from Server - End

    // 3. Specify your action, location and Send to the server - Start   
    xhr.open('POST', '/AutoserviceWalker/AperturacajaCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 4}));
    // 3. Specify your action, location and Send to the server - End
}

function FiltroApertura() {
    var input, filter, table, tr, td, i;
    input = document.getElementById("filtrosapertura");
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


function getTDapertura(obj) {
    var varTR = obj.getElementsByTagName('td');
    document.getElementById('id_apertura').value = varTR[0].innerHTML;
    recuperarAperturaUnico();
    document.getElementById('filtrosapertura').value = "";
    CargarTablaApertura();
    document.getElementById('id01').style.display = "none";
}

function cerrarbuscador() {
    document.getElementById('id01').style.display = "none";
}


function CargarTablaCaja() {
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

                    filas += "<option value= " + json[i].id_caja + ">" + json[i].descripcion + "</option>";
                }
                document.getElementById("id_caja").innerHTML = filas;

            }
        }
    };
    xhr.open('POST', '/AutoserviceWalker/AperturacajaCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 6}));
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
                for (i = 0; i < json.length; i++) {

                    filas += "<option value= " + json[i].id_sucursal + ">" + json[i].descripcion + "</option>";
                }
                document.getElementById("id_sucursal").innerHTML = filas;

            }
        }
    };
    xhr.open('POST', '/AutoserviceWalker/AperturacajaCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 7}));
}



function CargarTablaResponsables() {
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

                    filas += "<option value= " + json[i].id_usuario + ">" + json[i].nombre + "</option>";
                }
                document.getElementById("id_responsable").innerHTML = filas;

            }
        }
    };
    xhr.open('POST', '/AutoserviceWalker/AperturacajaCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 8}));
}