
CargarTablaSucursal();
CargarTablaCiudad();
CargarTablaCargo();
CargarTablaEmpleados();
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
    var apellido = $('#apellido').val();
    var cedula = $('#cedula').val();
    nombre = nombre.toUpperCase();
    apellido = apellido.toUpperCase();
    cedula = cedula.toUpperCase();
    $("#crpTbEmpledos  tr").each(function () {
        if (nombre === $(this).find("td").eq(1).html() & apellido === $(this).find("td").eq(2).html() & cedula === $(this).find("td").eq(5).html()) {
            alert("Ya Existe el Registro");
            estado = true;
        }
    });
    return estado;

}

function verificarEmpleados() {
    nombre = $('#nombre').val();
    apellido = $('#apellido').val();
    direccion = $('#direccion').val();
    celular = $('#celular').val();
    cedula = $('#cedula').val();
    fechanaci = $('#fechanaci').val();
    nombre = nombre.toUpperCase();
    apellido = apellido.toUpperCase();
    direccion = direccion.toUpperCase();
    celular = celular.toUpperCase();
    cedula = cedula.toUpperCase();
    fechanaci = fechanaci.toUpperCase();

    if (nombre === "" && apellido === "" && direccion === "" && celular === "" && cedula === "" && fechanaci === "") {
        alert('¡¡favor completar todos los campos!!');
        limpie();
    } else {
        if (comparar() === false) {
            construirJSON(accion);
            alert('Registro Guardados');
            limpie();
        }
        limpie();
    }
}

function Preguntar() {
    confirmar = confirm("Estas seguro que querés Modificar el registro???");
    if (confirmar) {
        verificarEmpleados();
    } else {
        limpie();
    }
}

function limpie() {
    document.getElementById('id_empleados').value = "";
    document.getElementById('nombre').value = "";
    document.getElementById('apellido').value = "";
    document.getElementById('direccion').value = "";
    document.getElementById('celular').value = "";
    document.getElementById('cedula').value = "";
    document.getElementById('fechanaci').value = "";
    location.reload();
    CargarTablaEmpleados();
}

function confirmar() {
    confirmar = confirm("Estas seguro que querés eliminar el registro???");
    if (confirmar) {
        construirJSON(accion);
        CargarTablaEmpleados();
        alert('Registro eliminado');
        limpie();
    } else {
        limpie();
// si pulsamos en cancelar
        ///alert('Registro eliminado');
    }
}
function filtroempleadosfocus() {
    document.getElementById('filtrosempleados').focus();
}


function construirJSON(ban) {
    // alert(ban);
    datos = {
        bandera: ban,
        id_empleados: (document.getElementById('id_empleados').value === "") ? "0" : document.getElementById('id_empleados').value,
        nombre: nombre,
        apellido: apellido,
        direccion: direccion,
        celular: celular,
        cedula: cedula,
        id_ciudad: document.getElementById('id_ciudad').value,
        id_cargo: document.getElementById('id_cargo').value,
        fechanaci: fechanaci,
        id_sucursal: document.getElementById('id_sucursal').value
    };
    envio();
    CargarTablaEmpleados();

}

function cargarEmpleados() {
    construirJSON(4);
}

function InputIdLectura() {
    document.getElementById('id_empleados').disabled = false;
    document.getElementById('id_empleados').value = "";
    document.getElementById('id_empleados').readOnly = false;
    document.getElementById('id_empleados').placeholder = "Ingrese ID";
    document.getElementById('filtrosempleados').focus();
}
function InputIdLecturaAgregar() {
    document.getElementById('id_empleados').disabled = true;
//    document.getElementById('id_cargo').value= "1231";
}

function validarInputsRegistroCiudad() {
    //Grupo de validaciones
    //
    //document.getElementById("opcionGrabarPerfil").disabled = true;
    document.getElementById("opcionGrabarEmpleados").focus();

}

function agregarEmpleados() {
    accion = 1;
//    InputIdLecturaAgregar();
    document.getElementById('nombre').focus();
}


function modificarEmpleados() {
    accion = 2;
    InputIdLectura();
    //construirJSON(2);
}

function eliminarEmpleados() {
    accion = 3;
    InputIdLectura();
    // construirJSON(3);
}

function grabarEmpleadoss() {
    switch (accion) {
        case 1:
            verificarEmpleados();
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
    xmlhttp.open("POST", "/AutoserviceWalker/EmpleadosCTRL");
    xmlhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xmlhttp.send(JSON.stringify(datos));
    if (xmlhttp.status === 200) {

        alert('respuesta exitosa');
    } else {
        // alert('Error durante la respuesta ');
    }
    CargarTablaEmpleados();
}

function recuperarEmpleadosUnico() {
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
                if (json[0].id_empleados === 0) {
                    alert('El Codigo Que Ingreso ¡¡No Existe!!, o ha ¡¡Sido Cambiado!!');
                    limpieza();
                    return;
                }
                var i;
                for (i = 0; i < json.length; i++) {
                    document.getElementById('id_ciudad').value = json[i].id_empleados;
                    document.getElementById('nombre').value = json[i].nombre;
                    document.getElementById('apellido').value = json[i].apellido;
                    document.getElementById('direccion').value = json[i].direccion;
                    document.getElementById('celular').value = json[i].celular;
                    document.getElementById('cedula').value = json[i].cedula;
                    document.getElementById('id_ciudad').value = json[i].id_ciudad;
                    document.getElementById('id_cargo').value = json[i].id_cargo;
                    document.getElementById('fechanaci').value = json[i].fechanaci;
                    document.getElementById('id_sucursal').value = json[i].id_sucursal;
                    document.getElementById('nombre').focus();

                }

            }

        }
    };
    // 2. Handle Response from Server - End

    // 3. Specify your action, location and Send to the server - Start   
    xhr.open('POST', '/AutoserviceWalker/EmpleadosCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 5, id_empleados: document.getElementById('id_empleados').value}));
    // 3. Specify your action, location and Send to the server - End
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
                    filas += "<tr onclick=getTDempleados(this);>";
                    filas += "<td id=td1_" + i + ">" + json[i].id_empleados + "</td>";
                    filas += "<td id=td2_" + i + ">" + json[i].nombre + "</td>";
                    filas += "<td id=td3_" + i + ">" + json[i].apellido + "</td>";
                    filas += "<td id=td4_" + i + ">" + json[i].cedula + "</td>";
                    filas += "<td id=td5_" + i + ">" + json[i].celular + "</td>";
                    filas += "<td id=td6_" + i + ">" + json[i].direccion + "</td>";
                    filas += "<td id=td7_" + i + ">" + json[i].fechanaci + "</td>";
                    filas += "<td id=td8_" + i + ">" + json[i].nombre_cargo + "</td>";
                    filas += "<td id=td9_" + i + ">" + json[i].nombre_ciudad + "</td>";
                    filas += "<td id=td10_" + i + ">" + json[i].nombre_sucursal + "</td>";
                    filas += "</tr>";
                }
                document.getElementById("crpTbEmpleados").innerHTML = filas;

            }
        }
    };
    // 2. Handle Response from Server - End

    // 3. Specify your action, location and Send to the server - Start   
    xhr.open('POST', '/AutoserviceWalker/EmpleadosCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 4}));
    // 3. Specify your action, location and Send to the server - End
}

function FiltroEmpleados() {
    var input, filter, table, tr, td, i;
    input = document.getElementById("filtrosempleados");
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


function getTDempleados(obj) {
    var varTR = obj.getElementsByTagName('td');
    document.getElementById('id_empleados').value = varTR[0].innerHTML;
    recuperarEmpleadosUnico();
//    document.getElementById('nombre').value = varTR[1].innerHTML;
//    document.getElementById('apellido').value = varTR[2].innerHTML;
//    document.getElementById('direccion').value = varTR[5].innerHTML;
//    document.getElementById('celular').value = varTR[4].innerHTML;
//    document.getElementById('cedula').value = varTR[3].innerHTML;
//    document.getElementById('id_ciudad').value = varTR[8].innerHTML;
//    document.getElementById('id_cargo').value = varTR[7].innerHTML;
//    document.getElementById('fechanaci').value = varTR[6].innerHTML;
//    document.getElementById('id_sucursal').value = varTR[9].innerHTML;
    document.getElementById('filtrosempleados').value = "";
    CargarTablaEmpleados();
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
    xhr.open('POST', '/AutoserviceWalker/EmpleadosCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 7}));
}


function CargarTablaCargo() {
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

                    filas += "<option value= " + json[i].id_cargo + ">" + json[i].descripcion + "</option>";
                }
                document.getElementById("id_cargo").innerHTML = filas;

            }
        }
    };
    xhr.open('POST', '/AutoserviceWalker/EmpleadosCTRL');
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
    xhr.open('POST', '/AutoserviceWalker/EmpleadosCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 8}));
}
