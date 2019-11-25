var datos;
var accion;
var MayusCargo;
var campoUni;
CargarTablaCondicion();
//initComponentes();

function comparar() {
    var estado = Boolean(false);
    var Mitexto = $('#descripcion').val();
    Mitexto = Mitexto.toUpperCase();
    $("#crpTbCondicion  tr").each(function () {
        if (Mitexto === $(this).find("td").eq(1).html()) {
            alert("Ya Existe el Registro");
            estado = true;
        }
    });
    return estado;

}

function verificarCondicion() {
     campoUni = $('#descripcion').val();
    campoUni = campoUni.toUpperCase();
    if (campoUni === "") {
        alert('¡¡favor completar todos los campos!!');
        limpiarr();
    } 
    else {
        if (comparar() === false) {
            construirJSON(accion);
            alert('Registro Guardados');
        }
        limpiarr();
    }
}

function Preguntar() {
    confirmar = confirm("Estas seguro que querés Modificar el registro???");
    if (confirmar) {
        verificarCondicion();
    } else {
        limpiarr();
    }
}

function limpiarr() {
    document.getElementById('id_condicion').value = "";
    document.getElementById('descripcion').value = "";
    location.reload();
    CargarTablaCondicion();
}

function confirmar() {
    confirmar = confirm("Estas seguro que querés eliminar el registro???");
    if (confirmar) {
        construirJSON(accion);
        CargarTablaCondicion();
        alert('Registro eliminado');
        limpiarr();
    } else {
        limpiarr();
// si pulsamos en cancelar
        ///alert('Registro eliminado');
    }
}
function filtrocondicionfocus(){
    document.getElementById('filtroscondicion').focus();
}


function construirJSON(ban) {
    // alert(ban);
    datos = {
        bandera: ban,
        id_condicion: (document.getElementById('id_condicion').value === "") ? "0" : document.getElementById('id_condicion').value,
        descripcion: campoUni
    };
    envio();
    CargarTablaCondicion();


}

function cargarCondicion() {
    construirJSON(4);
}

function InputIdLectura() {
    document.getElementById('id_condicion').disabled = false;
    document.getElementById('id_condicion').value = "";
    document.getElementById('id_condicion').readOnly = false;
    document.getElementById('id_condicion').placeholder = "Ingrese ID";
    document.getElementById('id_condicion').focus();
}
function InputIdLecturaAgregar() {
    document.getElementById('id_condicion').disabled = true;
//    document.getElementById('id_cargo').value= "1231";
}

function validarInputsRegistroCondicion() {
    //Grupo de validaciones
    //
    //document.getElementById("opcionGrabarPerfil").disabled = true;
    document.getElementById("opcionGrabarCondicion").focus();

}

function agregarCondicion() {
    //construirJSON(1);
    accion = 1;
    InputIdLecturaAgregar();
    document.getElementById('descripcion').focus();
}


function modificarCondicion() {
    accion = 2;
    InputIdLectura();
    //construirJSON(2);
}

function eliminarCondicion() {
    accion = 3;
    InputIdLectura();
    // construirJSON(3);
}

function grabarCondicion() {
         switch (accion) {
        case 1:
            verificarCondicion();
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
    xmlhttp.open("POST", "/AutoserviceWalker/CondicionCTRL");
    xmlhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xmlhttp.send(JSON.stringify(datos));
    if (xmlhttp.status === 200) {

        alert('respuesta exitosa');
    } else {
        // alert('Error durante la respuesta ');
    }
    CargarTablaCondicion();
}

function recuperarCondicionUnico() {
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
                if (json[0].id_condicion === 0) {
                    alert('El Codigo Que Ingreso ¡¡No Existe!!, o ha ¡¡Sido Cambiado!!');
                    limpiarr();
                    return;
                }
                var i;
                for (i = 0; i < json.length; i++) {
                    document.getElementById('id_condicion').value = json[i].id_condicion;
                    document.getElementById('descripcion').value = json[i].descripcion;
                    document.getElementById('descripcion').focus();

                }

            }

        }
    };
    // 2. Handle Response from Server - End

    // 3. Specify your action, location and Send to the server - Start   
    xhr.open('POST', '/AutoserviceWalker/CondicionCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 5, id_condicion: document.getElementById('id_condicion').value}));
    // 3. Specify your action, location and Send to the server - End
}

function CargarTablaCondicion() {
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
                    filas += "<tr onclick=getTDcondicion(this);>";
                    filas += "<td id=td1_"+i+">" + json[i].id_condicion + "</td>";
                    filas += "<td id=td2_"+i+">" + json[i].descripcion + "</td>";
                    filas += "</tr>";
                }
                document.getElementById("crpTbCondicion").innerHTML = filas;

            }
        }
    };
    // 2. Handle Response from Server - End

    // 3. Specify your action, location and Send to the server - Start   
    xhr.open('POST', '/AutoserviceWalker/CondicionCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 4}));
    // 3. Specify your action, location and Send to the server - End
}

 function FiltroCondicion() {
  var input, filter, table, tr, td, i;
  input = document.getElementById("filtroscondicion");
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


function getTDcondicion(obj){
    var varTR=obj.getElementsByTagName('td');
     document.getElementById('id_condicion').value = varTR[0].innerHTML;
     document.getElementById('descripcion').value = varTR[1].innerHTML;
     document.getElementById('filtroscondicion').value = "";
     CargarTablaCondicion();
     document.getElementById('id01').style.display="none";
}

function cerrarbuscador(){
    document.getElementById('id01').style.display="none";
}