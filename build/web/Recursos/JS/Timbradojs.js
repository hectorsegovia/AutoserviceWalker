
CargarTablaTimbrado();


function comparar() {
    var estado = Boolean(false);
    var fechainicio = $('#fechainicio').val();
    var fechafin = $('#fechafin').val();
    var timbrado_nro = $('#timbrado_nro').val();
    fechainicio = fechainicio.toUpperCase();
    fechafin = fechafin.toUpperCase();
    timbrado_nro = timbrado_nro.toUpperCase();
    $("#crpTbUsuario  tr").each(function () {
        if (fechainicio === $(this).find("td").eq(2).html() & fechafin === $(this).find("td").eq(3).html() & timbrado_nro === $(this).find("td").eq(1).html()) {
            alert("Ya Existe el Registro");
            estado = true;
        }
    });
    return estado;

}

function verificarTimbrado() {
    fechainicio = $('#fechainicio').val();
    fechafin = $('#fechafin').val();
    timbrado_nro = $('#timbrado_nro').val();
    fechainicio = fechainicio.toUpperCase();
    fechafin = fechafin.toUpperCase();
    timbrado_nro = timbrado_nro.toUpperCase();

    if (timbrado_nro === "" && fechainicio === "" && fechafin === "") {
        alert('¡¡favor completar todos los campos!!');
        limpieezaa();
    } else {
        validar();

    }
}

function Preguntar() {
    confirmar = confirm("Estas seguro que querés Modificar el registro???");
    if (confirmar) {
        verificarTimbrado();
    } else {
        limpieezaa();
    }
}

function validar() {
    var inicio = document.getElementById('fechainicio').value;
    var finalq = document.getElementById('fechafin').value;
    inicio = new Date(inicio);
    finalq = new Date(finalq);
    if (inicio > finalq)
        alert('La fecha de inicio debe ser Mayor que la fecha fin');
    else {
        if (comparar() === false) {
            construirJSON(accion);
            alert('Registro Guardados');
            limpieezaa();
        }
        limpieezaa();
    }
        

}

function limpieezaa() {
    document.getElementById('id_timbrado').value = "";
    document.getElementById('fechainicio').value = "";
    document.getElementById('fechafin').value = "";
    document.getElementById('timbrado_nro').value = "";
    location.reload();
    CargarTablaTimbrado();
}

function confirmar() {
    confirmar = confirm("Estas seguro que querés eliminar el registro???");
    if (confirmar) {
        construirJSON(accion);
        CargarTablaTimbrado();
        alert('Registro eliminado');
        limpieezaa();
    } else {
        limpieezaa();
// si pulsamos en cancelar
        ///alert('Registro eliminado');
    }
}
function filtrotimbradofocuss() {
    document.getElementById('filtrostimbrado').focus();
}


function construirJSON(ban) {
    // alert(ban);
    datos = {
        bandera: ban,
        id_timbrado: (document.getElementById('id_timbrado').value === "") ? "0" : document.getElementById('id_timbrado').value,
        timbrado_nro: timbrado_nro,
        fechainicio: fechainicio,
        fechafin: fechafin
    };
    envio();
    CargarTablaTimbrado();

}


function InputIdLectura() {
    document.getElementById('id_timbrado').disabled = false;
    document.getElementById('id_timbrado').value = "";
    document.getElementById('id_timbrado').readOnly = false;
    document.getElementById('id_timbrado').placeholder = "Ingrese ID";
    document.getElementById('filtrostimbrado').focus();
}
function InputIdLecturaAgregar() {
    document.getElementById('id_timbrado').disabled = true;
//    document.getElementById('id_cargo').value= "1231";
}
 
function agregarTimbrado() {
    accion = 1;
    InputIdLecturaAgregar();
    document.getElementById('timbrado_nro').focus();
}


function modificarTimbrado() {
    accion = 2;
    InputIdLectura();
    //construirJSON(2);
}

function eliminarTimbrado() {
    accion = 3;
    InputIdLectura();
    // construirJSON(3);
}

function grabarTimbrado() {
    switch (accion) {
        case 1:
            verificarTimbrado();
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
    xmlhttp.open("POST", "/AutoserviceWalker/TimbradoCTRL");
    xmlhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xmlhttp.send(JSON.stringify(datos));
    if (xmlhttp.status === 200) {

        alert('respuesta exitosa');
    } else {
        // alert('Error durante la respuesta ');
    }
    CargarTablaTimbrado();
}

function recuperarTimbradoUnico() {
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
                if (json[0].id_timbrado === 0) {
                    alert('El Codigo Que Ingreso ¡¡No Existe!!, o ha ¡¡Sido Cambiado!!');
                    limpieezaa();
                    return;
                }
                var i;
                for (i = 0; i < json.length; i++) {
                    document.getElementById('id_timbrado').value = json[i].id_timbrado;
                    document.getElementById('timbrado_nro').value = json[i].timbrado;
                    document.getElementById('fechainicio').value = json[i].fechainicio;
                    document.getElementById('fechafin').value = json[i].fechafin;
                    document.getElementById('timbrado_nro').focus();
                }

            }

        }
    };
    // 2. Handle Response from Server - End

    // 3. Specify your action, location and Send to the server - Start   
    xhr.open('POST', '/AutoserviceWalker/TimbradoCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 5, id_timbrado: document.getElementById('id_timbrado').value}));
    // 3. Specify your action, location and Send to the server - End
}

function CargarTablaTimbrado() {
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
                    filas += "<tr onclick=getTDtimbrado(this);>";
                    filas += "<td id=td1_" + i + ">" + json[i].id_timbrado + "</td>";
                    filas += "<td id=td2_" + i + ">" + json[i].timbrado_nro + "</td>";
                    filas += "<td id=td3_" + i + ">" + json[i].fechainicio + "</td>";
                    filas += "<td id=td4_" + i + ">" + json[i].fechafin + "</td>";
                    filas += "</tr>";
                }
                document.getElementById("crpTbTimbrado").innerHTML = filas;

            }
        }
    };
    // 2. Handle Response from Server - End

    // 3. Specify your action, location and Send to the server - Start   
    xhr.open('POST', '/AutoserviceWalker/TimbradoCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 4}));
    // 3. Specify your action, location and Send to the server - End
}

function FiltroTimbrado() {
    var input, filter, table, tr, td, i;
    input = document.getElementById("filtrostimbrado");
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


function getTDtimbrado(obj) {
    var varTR = obj.getElementsByTagName('td');
    document.getElementById('id_timbrado').value = varTR[0].innerHTML;
    document.getElementById('timbrado_nro').value = varTR[1].innerHTML;
    document.getElementById('fechainicio').value = varTR[2].innerHTML;
    document.getElementById('fechafin').value = varTR[3].innerHTML;
    document.getElementById('filtrostimbrado').value = "";
    CargarTablaTimbrado();
    document.getElementById('id01').style.display = "none";
}

function cerrarbuscador() {
    document.getElementById('id01').style.display = "none";
}