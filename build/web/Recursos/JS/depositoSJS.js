var datos;
var accion;
var MayusCargo;
var campoUni;
CargarTablaDeposito();
CargarTablaSucursal();
//initComponentes();

function comparar() {
    var estado = Boolean(false);
    var Mitexto = $('#descripcion').val();
    Mitexto = Mitexto.toUpperCase();
    $("#crpTbDeposito1  tr").each(function () {
        if (Mitexto === $(this).find("td").eq(1).html()) {
            alert("Ya Existe el Registro");
            estado = true;
        }
    });
    return estado;

}

function verificarDeposito() {
     campoUni = $('#descripcion').val();
    campoUni = campoUni.toUpperCase();
    if (campoUni === "") {
        alert('¡¡favor completar todos los campos!!');
        limpiar();
    } 
    else {
        if (comparar() === false) {
            construirJSON(accion);
            alert('Registro Guardados');
        }
        limpiar();
    }
}

function Preguntar() {
    confirmar = confirm("Estas seguro que querés Modificar el registro???");
    if (confirmar) {
        verificarDeposito();
    } else {
        limpiar();
    }
}

function limpiarDeposito() {
    document.getElementById('id_deposito').value = "";
    document.getElementById('descripcion').value = "";
    location.reload();
    CargarTablaDeposito();
}

function confirmar() {
    confirmar = confirm("Estas seguro que querés eliminar el registro???");
    if (confirmar) {
        construirJSON(accion);
        CargarTablaDeposito();
        alert('Registro eliminado');
        limpiarDeposito();
    } else {
        limpiarDeposito();
// si pulsamos en cancelar
        ///alert('Registro eliminado');
    }
}
function filtroDepositofocus(){
    document.getElementById('filtrosdeposito').focus();
}


function construirJSON(ban) {
    // alert(ban);
    datos = {
        bandera: ban,
        id_deposito: (document.getElementById('id_deposito').value === "") ? "0" : document.getElementById('id_deposito').value,
        descripcion: campoUni,
        id_sucursal: document.getElementById('id_sucursal').value
    };
    envio();
    CargarTablaDeposito();


}

function cargarDeposito() {
    construirJSON(4);
}

function InputIdLectura() {
    document.getElementById('id_deposito').disabled = false;
    document.getElementById('id_deposito').value = "";
    document.getElementById('id_deposito').readOnly = false;
    document.getElementById('id_deposito').placeholder = "Ingrese ID";
    document.getElementById('id_deposito').focus();
}
function InputIdLecturaAgregar() {
    document.getElementById('id_deposito').disabled = true;
//    document.getElementById('id_cargo').value= "1231";
}

function validarInputsRegistroDeposito() {
    //Grupo de validaciones
    //
    //document.getElementById("opcionGrabarPerfil").disabled = true;
    document.getElementById("opcionGrabarDeposito").focus();

}

function agregarDeposito() {
    //construirJSON(1);
    accion = 1;
    InputIdLecturaAgregar();
    document.getElementById('descripcion').focus();
}


function modificarDeposito() {
    accion = 2;
    InputIdLectura();
    //construirJSON(2);
}

function eliminarDeposito() {
    accion = 3;
    InputIdLectura();
    // construirJSON(3);
}

function grabarDeposito() {
         switch (accion) {
        case 1:
            verificarDeposito();
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
    xmlhttp.open("POST", "/AutoserviceWalker/depositoCTRL");
    xmlhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xmlhttp.send(JSON.stringify(datos));
    if (xmlhttp.status === 200) {

        alert('respuesta exitosa');
    } else {
        // alert('Error durante la respuesta ');
    }
    CargarTablaDeposito();
}

function recuperarDepositoUnico() {
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
                if (json[0].id_deposito === 0) {
                    alert('El Codigo Que Ingreso ¡¡No Existe!!, o ha ¡¡Sido Cambiado!!');
                    limpiar();
                    return;
                }
                var i;
                for (i = 0; i < json.length; i++) {
                    document.getElementById('id_deposito').value = json[i].id_deposito;
                    document.getElementById('descripcion').value = json[i].descripcion;
                    document.getElementById('id_sucursal').value = json[i].id_sucursal;
                    document.getElementById('descripcion').focus();

                }

            }

        }
    };
    // 2. Handle Response from Server - End

    // 3. Specify your action, location and Send to the server - Start   
    xhr.open('POST', '/AutoserviceWalker/depositoCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 5, id_deposito: document.getElementById('id_deposito').value}));
    // 3. Specify your action, location and Send to the server - End
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
                for (i = 0; i < json.length; i++) {
                    filas += "<tr onclick=getTDDeposito(this);>";
                    filas += "<td id=td1_"+i+">" + json[i].id_deposito + "</td>";
                    filas += "<td id=td2_"+i+">" + json[i].descripcion + "</td>";
                    filas += "<td id=td3_"+i+">" + json[i].nom_sucu + "</td>";
                    filas += "</tr>";
                }
                document.getElementById("crpTbDeposito1").innerHTML = filas;

            }
        }
    };
    // 2. Handle Response from Server - End

    // 3. Specify your action, location and Send to the server - Start   
    xhr.open('POST', '/AutoserviceWalker/depositoCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 4}));
    // 3. Specify your action, location and Send to the server - End
}

 function FiltroDeposito1() {
  var input, filter, table, tr, td, i;
  input = document.getElementById("filtrosdeposito");
  filter = input.value.toUpperCase();
  table = document.getElementById("id01Deposito");
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


function getTDDeposito(obj){
    var varTR=obj.getElementsByTagName('td');
     document.getElementById('id_deposito').value = varTR[0].innerHTML;
     document.getElementById('descripcion').value = varTR[1].innerHTML;
     document.getElementById('id_sucursal').value = varTR[1].innerHTML;
     document.getElementById('filtrosdeposito').value = "";
     CargarTablaDeposito();
     document.getElementById('id01Deposito').style.display="none";
     document.getElementById('descripcion').focus;
}

function cerrarbuscador(){
    document.getElementById('id01Deposito').style.display="none";
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
    xhr.open('POST', '/AutoserviceWalker/depositoCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 6}));
}


