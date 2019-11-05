
CargarTablaPerfil();
CargarTablaEmpleados();
CargarTablaUsuario();
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
    var clave = $('#clave').val();
    nombre = nombre.toUpperCase();
    clave = clave.toUpperCase();
    $("#crpTbUsuario  tr").each(function () {
        if (nombre === $(this).find("td").eq(1).html() & clave === $(this).find("td").eq(2).html()) {
            alert("Ya Existe el Registro");
            estado = true;
        }
    });
    return estado;

}

function verificarUsuario() {
    nombre = $('#nombre').val();
    clave = $('#clave').val();
    nombre = nombre.toUpperCase();
    clave = clave.toUpperCase();

    if (nombre === "" && clave === "") {
        alert('¡¡favor completar todos los campos!!');
        limpiee();
    } else {
        if (comparar() === false) {
            construirJSON(accion);
            alert('Registro Guardados');
            limpiee();
        }
        limpiee();
    }
}

function Preguntar() {
    confirmar = confirm("Estas seguro que querés Modificar el registro???");
    if (confirmar) {
        verificarUsuario();
    } else {
        limpiee();
    }
}

function limpiee() {
    document.getElementById('id_usuario').value = "";
    document.getElementById('nombre').value = "";
    document.getElementById('clave').value = "";
    location.reload();
    CargarTablaUsuario();
}

function confirmar() {
    confirmar = confirm("Estas seguro que querés eliminar el registro???");
    if (confirmar) {
        construirJSON(accion);
        CargarTablaUsuario();
        alert('Registro eliminado');
        limpiee();
    } else {
        limpiee();
// si pulsamos en cancelar
        ///alert('Registro eliminado');
    }
}
function filtrousuariofocuss() {
    document.getElementById('filtrosusuario').focus();
}


function construirJSON(ban) {
    // alert(ban);
    datos = {
        bandera: ban,
        id_usuario: (document.getElementById('id_usuario').value === "") ? "0" : document.getElementById('id_usuario').value,
        nombre: nombre,
        clave: clave,
        id_perfil: document.getElementById('id_perfil').value,
        id_empleados: document.getElementById('id_empleados').value
    };
    envio();
    CargarTablaUsuario();

}

function cargarusuario() {
    construirJSON(4);
}

function InputIdLectura() {
    document.getElementById('id_usuario').disabled = false;
    document.getElementById('id_usuario').value = "";
    document.getElementById('id_usuario').readOnly = false;
    document.getElementById('id_usuario').placeholder = "Ingrese ID";
    document.getElementById('filtrosusuario').focus();
}
function InputIdLecturaAgregar() {
    document.getElementById('id_usuario').disabled = true;
//    document.getElementById('id_cargo').value= "1231";
}

function validarInputsRegistroCiudad() {
    //Grupo de validaciones
    //
    //document.getElementById("opcionGrabarPerfil").disabled = true;
    document.getElementById("opcionGrabarUsuario").focus();

}

function agregarUsuario() {
    accion = 1;
    InputIdLecturaAgregar();
    document.getElementById('nombre').focus();
}


function modificarUsuario() {
    accion = 2;
    InputIdLectura();
    //construirJSON(2);
}

function eliminarUsuario() {
    accion = 3;
    InputIdLectura();
    // construirJSON(3);
}

function grabarUsuario() {
    switch (accion) {
        case 1:
            verificarUsuario();
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
    xmlhttp.open("POST", "/AutoserviceWalker/UsuarioCTRL");
    xmlhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xmlhttp.send(JSON.stringify(datos));
    if (xmlhttp.status === 200) {

        alert('respuesta exitosa');
    } else {
        // alert('Error durante la respuesta ');
    }
    CargarTablaUsuario();
}

function recuperarUsuarioUnico() {
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
                if (json[0].id_usuario === 0) {
                    alert('El Codigo Que Ingreso ¡¡No Existe!!, o ha ¡¡Sido Cambiado!!');
                    limpiee();
                    return;
                }
                var i;
                for (i = 0; i < json.length; i++) {
                    document.getElementById('id_usuario').value = json[i].id_usuario;
                    document.getElementById('nombre').value = json[i].nombre;
                    document.getElementById('clave').value = json[i].clave;
                    document.getElementById('id_perfil').value = json[i].id_perfil;
//                    document.getElementById('nombre_perfil').value = json[i].nombre_perfil;
                    document.getElementById('id_empleados').value = json[i].id_empleados;
                    document.getElementById('nombre_empleados').value = json[i].nombre_empleados;
                    document.getElementById('nombre').focus();
                }

            }

        }
    };
    // 2. Handle Response from Server - End

    // 3. Specify your action, location and Send to the server - Start   
    xhr.open('POST', '/AutoserviceWalker/UsuarioCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 5, id_usuario: document.getElementById('id_usuario').value}));
    // 3. Specify your action, location and Send to the server - End
}

function CargarTablaUsuario() {
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
                    filas += "<tr onclick=getTDusuario(this);>";
                    filas += "<td id=td1_" + i + ">" + json[i].id_usuario + "</td>";
                    filas += "<td id=td2_" + i + ">" + json[i].nombre + "</td>";
                    filas += "<td id=td3_" + i + ">" + json[i].clave + "</td>";
                    filas += "<td id=td4_" + i + ">" + json[i].id_perfil + "</td>";
                    filas += "<td id=td5_" + i + ">" + json[i].id_empleados + "</td>";
                    filas += "</tr>";
                }
                document.getElementById("crpTbUsuario").innerHTML = filas;

            }
        }
    };
    // 2. Handle Response from Server - End

    // 3. Specify your action, location and Send to the server - Start   
    xhr.open('POST', '/AutoserviceWalker/UsuarioCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 4}));
    // 3. Specify your action, location and Send to the server - End
}

function FiltroUsuario() {
    var input, filter, table, tr, td, i;
    input = document.getElementById("filtrosusuario");
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


function getTDusuario(obj) {
    var varTR = obj.getElementsByTagName('td');
    document.getElementById('id_usuario').value = varTR[0].innerHTML;
    document.getElementById('nombre').value = varTR[1].innerHTML;
    document.getElementById('clave').value = varTR[2].innerHTML;
    document.getElementById('id_perfil').value = varTR[3].innerHTML;
    document.getElementById('id_empleados').value = varTR[4].innerHTML;
    document.getElementById('id01').style.display = "none";
    CargarTablaUsuario();
}

function cerrarbuscador() {
    document.getElementById('id01').style.display = "none";
}


function CargarTablaPerfil() {
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

                    filas += "<option value= " + json[i].id_perfil + ">" + json[i].descripcion + "</option>";
                }
                document.getElementById("id_perfil").innerHTML = filas;

            }
        }
    };
    xhr.open('POST', '/AutoserviceWalker/UsuarioCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 6}));
}


function CargarTablaEmpleados() {
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

                    filas += "<option value= " + json[i].id_empleados + ">" + json[i].nombre + "</option>";
                }
                document.getElementById("id_empleados").innerHTML = filas;

            }
        }
    };
    xhr.open('POST', '/AutoserviceWalker/UsuarioCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 7}));
}

