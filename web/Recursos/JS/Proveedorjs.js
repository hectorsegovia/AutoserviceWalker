
CargarTablaCiudad();
CargarTablaPais();
CargarTablaProveedor();
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
    nombre = nombre.toUpperCase();
    ruc = ruc.toUpperCase();
    $("#crpTbProveedor  tr").each(function () {
        if (nombre === $(this).find("td").eq(1).html() & ruc === $(this).find("td").eq(2).html()) {
            alert("Ya Existe el Registro");
            estado = true;
        }
    });
    return estado;

}

function verificarProveedor() {
    nombre = $('#nombre').val();
    ruc = $('#ruc').val();
    direccion = $('#direccion').val();
    celular = $('#celular').val();
    cuenta1 = $('#cuenta1').val();
    nombre = nombre.toUpperCase();
    ruc = ruc.toUpperCase();
    direccion = direccion.toUpperCase();
    celular = celular.toUpperCase();
    cuenta1 = cuenta1.toUpperCase();
    

    if (nombre === "" && ruc === "" && direccion === "" && celular === "" && cuenta1 === "") {
        alert('¡¡favor completar todos los campos!!');
        limpieez();
    } else {
        if (comparar() === false) {
            construirJSON(accion);
            alert('Registro Guardados');
            limpieez();
        }
        limpieez();
    }
}

function Preguntar() {
    confirmar = confirm("Estas seguro que querés Modificar el registro???");
    if (confirmar) {
        verificarProveedor();
    } else {
        limpieez();
    }
}

function limpieez() {
    document.getElementById('id_proveedor').value = "";
    document.getElementById('nombre').value = "";
    document.getElementById('ruc').value = "";
    document.getElementById('celular').value = "";
    document.getElementById('direccion').value = "";
    document.getElementById('cuenta1').value = "";
    location.reload();
    CargarTablaProveedor();
}

function confirmar() {
    confirmar = confirm("Estas seguro que querés eliminar el registro???");
    if (confirmar) {
        construirJSON(accion);
        CargarTablaProveedor();
        alert('Registro eliminado');
        limpieez();
    } else {
        limpieez();
// si pulsamos en cancelar
        ///alert('Registro eliminado');
    }
}
function filtroproveedorfocuss() {
    document.getElementById('filtrosproveedor').focus();
}


function construirJSON(ban) {
    // alert(ban);
    datos = {
        bandera: ban,
        id_proveedor: (document.getElementById('id_proveedor').value === "") ? "0" : document.getElementById('id_proveedor').value,
        nombre: nombre,
        direccion: direccion,
        ruc: ruc,
        celular: celular,
        cuenta1: cuenta1,
        id_ciudad: document.getElementById('id_ciudad').value,
        id_pais: document.getElementById('id_pais').value
    };
    envio();
    CargarTablaProveedor();

}


function agregarProveedor() {
    accion = 1;
    document.getElementById('nombre').focus();
}


function modificarProveedor() {
    accion = 2;
    //construirJSON(2);
}

function eliminarProveeodor() {
    accion = 3;
    // construirJSON(3);
}

function grabarProveedor() {
    switch (accion) {
        case 1:
            verificarProveedor();
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
    xmlhttp.open("POST", "/AutoserviceWalker/ProveedorCTRL");
    xmlhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xmlhttp.send(JSON.stringify(datos));
    if (xmlhttp.status === 200) {

        alert('respuesta exitosa');
    } else {
        // alert('Error durante la respuesta ');
    }
    CargarTablaProveedor();
}

function recuperarProveedorUnico() {
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
                if (json[0].id_proveedor === 0) {
                    alert('El Codigo Que Ingreso ¡¡No Existe!!, o ha ¡¡Sido Cambiado!!');
                    limpieez();
                    return;
                }
                var i;
                for (i = 0; i < json.length; i++) {
                    document.getElementById('id_proveedor').value = json[i].id_proveedor;
                    document.getElementById('nombre').value = json[i].nombre;
                    document.getElementById('direccion').value = json[i].direccion;
                    document.getElementById('celular').value = json[i].celular;
                    document.getElementById('ruc').value = json[i].ruc;
                    document.getElementById('cuenta1').value = json[i].cuenta1;
                    document.getElementById('id_ciudad').value = json[i].id_ciudad;
                    document.getElementById('id_pais').value = json[i].id_pais;
                    document.getElementById('nombre').focus();
                }

            }

        }
    };
    // 2. Handle Response from Server - End

    // 3. Specify your action, location and Send to the server - Start   
    xhr.open('POST', '/AutoserviceWalker/ProveedorCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 5, id_proveedor: document.getElementById('id_proveedor').value}));
    // 3. Specify your action, location and Send to the server - End
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
                for (i = 0; i < json.length; i++) {
                    filas += "<tr onclick=getTDproveedor(this);>";
                    filas += "<td id=td1_" + i + ">" + json[i].id_proveedor + "</td>";
                    filas += "<td id=td2_" + i + ">" + json[i].nombre + "</td>";
                    filas += "<td id=td3_" + i + ">" + json[i].ruc + "</td>";
                    filas += "<td id=td5_" + i + ">" + json[i].celular + "</td>";
                    filas += "<td id=td5_" + i + ">" + json[i].nombre_ciudad + "</td>";
                    filas += "<td id=td5_" + i + ">" + json[i].nombre_pais + "</td>";
                    filas += "</tr>";
                }
                document.getElementById("crpTbProveedor").innerHTML = filas;

            }
        }
    };
    // 2. Handle Response from Server - End

    // 3. Specify your action, location and Send to the server - Start   
    xhr.open('POST', '/AutoserviceWalker/ProveedorCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 4}));
    // 3. Specify your action, location and Send to the server - End
}

function FiltroProveedor() {
    var input, filter, table, tr, td, i;
    input = document.getElementById("filtrosproveedor");
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


function getTDproveedor(obj) {
    var varTR = obj.getElementsByTagName('td');
    document.getElementById('id_proveedor').value = varTR[0].innerHTML;
    recuperarProveedorUnico();
//    document.getElementById('nombre').value = varTR[1].innerHTML;
//    document.getElementById('ruc').value = varTR[2].innerHTML;
//    document.getElementById('direccion').value = varTR[3].innerHTML;
//    document.getElementById('celular').value = varTR[4].innerHTML;
//    document.getElementById('cuenta1').value = varTR[4].innerHTML;
//    document.getElementById('id_ciudad').value = varTR[4].innerHTML;
//    document.getElementById('id_pais').value = varTR[4].innerHTML;
    document.getElementById('filtrosproveedor').value = "";
    CargarTablaProveedor();
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
                document.getElementById("id_ciudad").innerHTML = filas;

            }
        }
    };
    xhr.open('POST', '/AutoserviceWalker/ProveedorCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 6}));
}


function CargarTablaPais() {
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

                    filas += "<option value= " + json[i].id_pais + ">" + json[i].descripcion + "</option>";
                }
                document.getElementById("id_pais").innerHTML = filas;

            }
        }
    };
    xhr.open('POST', '/AutoserviceWalker/ProveedorCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 7}));
}


