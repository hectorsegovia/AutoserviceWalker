

var datos;
var accion;
var MayusCargo;
var nombre;
var apellido;
var direccion;
var cedula;
var celular;

CargarTablaArqueo();
desabilitarcampos();

function comparar() {
    var estado = Boolean(false);
    var fecha = $('#fecha').val();
    var montototal = $('#total').val();
    var caja = $('#id_caja').val();

    fecha = fecha.toUpperCase();
    montototal = montototal.toUpperCase();
    caja = caja.toUpperCase();
    $("#crpTbArqueo  tr").each(function () {
        if (fecha === $(this).find("td").eq(1).html() & montototal === $(this).find("td").eq(2).html() & caja === $(this).find("td").eq(3).html()) {
            alert("Ya Existe el Registro");
            estado = true;
        }
    });
    return estado;

}

function verificarArqueo() {
    total = $('#fecha').val();

    total = total.toUpperCase();

    if (total === "") {
        alert('¡¡favor completar todos los campos!!');
        limpiararqueo();
    } else {
        if (comparar() === false) {
            construirJSON(accion);
            alert('Registro Guardados');
            limpiararqueo();
        }
        limpiararqueo();
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
    datos = {
        bandera: ban,
        id_arqueo: (document.getElementById('id_arqueo').value === "") ? "0" : document.getElementById('id_arqueo').value,
        monto_arqueo: document.getElementById('total').value,
        id_sucursal: document.getElementById('id_sucursal').value,
        id_usuario: document.getElementById('id_usuario').value,
        fecha: document.getElementById('fecha').value,
        id_caja: document.getElementById('id_caja').value,
        id_apertura: document.getElementById('id_apertura').value
    };
    envio();
    CargarTablaArqueo();

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


function agregarArqueo() {
    accion = 1;
}



function grabarArqueo() {
    switch (accion) {
        case 1:
            verificarArqueo();
            break;
        case 2:
            Preguntar();
            break;
        case 3:
            confirmar();
            break;
    }

}

function limpiararqueo(){
    document.getElementById('id_arqueo').value = "";
    document.getElementById('id_apertura').value = "";
    document.getElementById('fecha').value = "";
    document.getElementById('id_usuario').value = "";
    document.getElementById('nombre').value = "";
    document.getElementById('id_sucursal').value = "";
    document.getElementById('nombre_sucursal').value = "";
    document.getElementById('id_caja').value = "";
    document.getElementById('nombre_caja').value = "";
    document.getElementById('total').value = "";
}


function desabilitarcampos(){
    document.getElementById('id_arqueo').disabled = true;
    document.getElementById('id_apertura').disabled = true;
    document.getElementById('fecha').disabled = true;
    document.getElementById('id_usuario').disabled = true;
    document.getElementById('nombre').disabled = true;
    document.getElementById('id_sucursal').disabled = true;
    document.getElementById('nombre_sucursal').disabled = true;
    document.getElementById('id_caja').disabled = true;
    document.getElementById('nombre_caja').disabled = true;
    document.getElementById('total').disabled = true;
}





function envio() {
    var xmlhttp = new XMLHttpRequest();   // new HttpRequest instance 
    xmlhttp.open("POST", "/AutoserviceWalker/ArqueoCTRL");
    xmlhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xmlhttp.send(JSON.stringify(datos));
    if (xmlhttp.status === 200) {

        alert('respuesta exitosa');
    } else {
        // alert('Error durante la respuesta ');
    }
    CargarTablaApertura();
}

function recuperarArqueoUnico() {
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
                    document.getElementById('total').value = json[i].monto_arqueo;

                }
                desabilitarcampos();

            }

        }
    };
    // 2. Handle Response from Server - End

    // 3. Specify your action, location and Send to the server - Start   
    xhr.open('POST', '/AutoserviceWalker/ArqueoCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 6, id_usuario: document.getElementById('id_usuario').value,
        id_sucursal: document.getElementById('id_sucursal').value,
        id_caja: document.getElementById('id_caja').value,
        fecha: document.getElementById('fecha').value}));
    // 3. Specify your action, location and Send to the server - End
}

function CargarTablaArqueo() {
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
                    filas += "<tr onclick=getTDapertur(this);>";
                    filas += "<td id=td1_" + i + ">" + json[i].id_apertura + "</td>";
                    filas += "<td id=td2_" + i + ">" + json[i].id_usuario + "</td>";
                    filas += "<td id=td3_" + i + ">" + json[i].nombre_usuario + "</td>";
                    filas += "<td id=td2_" + i + ">" + json[i].id_caja + "</td>";
                    filas += "<td id=td3_" + i + ">" + json[i].nombre_caja + "</td>";
                    filas += "<td id=td2_" + i + ">" + json[i].id_sucursal + "</td>";
                    filas += "<td id=td3_" + i + ">" + json[i].nombre_sucursal + "</td>";
                    filas += "<td id=td3_" + i + ">" + json[i].fecha + "</td>";
                    filas += "</tr>";
                }
                document.getElementById("crpTbAperturas").innerHTML = filas;

            }
        }
    };
    // 2. Handle Response from Server - End

    // 3. Specify your action, location and Send to the server - Start   
    xhr.open('POST', '/AutoserviceWalker/ArqueoCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 7}));
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


function getTDapertur(obj) {
    var varTR = obj.getElementsByTagName('td');
    document.getElementById('id_apertura').value = varTR[0].innerHTML;
    document.getElementById('id_usuario').value = varTR[1].innerHTML;
    document.getElementById('nombre').value = varTR[2].innerHTML;
    document.getElementById('id_caja').value = varTR[3].innerHTML;
    document.getElementById('nombre_caja').value = varTR[4].innerHTML;
    document.getElementById('id_sucursal').value = varTR[5].innerHTML;
    document.getElementById('nombre_sucursal').value = varTR[6].innerHTML;
    document.getElementById('fecha').value = varTR[7].innerHTML;
    recuperarArqueoUnico();
    document.getElementById('id01').style.display = "none";
}

function cerrarbuscador() {
    document.getElementById('id01').style.display = "none";
}
