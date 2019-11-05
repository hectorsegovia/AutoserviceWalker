var datos;
var accion;
var MayusCargo;
var campoUni;
CargarTablaTipocheque();
//initComponentes();

function comparar() {
    var estado = Boolean(false);
    var Mitexto = $('#descripcion').val();
    Mitexto = Mitexto.toUpperCase();
    $("#crpTbTipocheque  tr").each(function () {
        if (Mitexto === $(this).find("td").eq(1).html()) {
            alert("Ya Existe el Registro");
            estado = true;
        }
    });
    return estado;

}

function verificarTipocheque() {
     campoUni = $('#descripcion').val();
    campoUni = campoUni.toUpperCase();
    if (campoUni === "") {
        alert('¡¡favor completar todos los campos!!');
        limpii();
    } 
    else {
        if (comparar() === false) {
            construirJSON(accion);
            alert('Registro Guardados');
            limpii();
        }
        limpii();
    }
}

function Preguntar() {
    confirmar = confirm("Estas seguro que querés Modificar el registro???");
    if (confirmar) {
        verificarTipocheque();
    } else {
        limpii();
    }
}

function limpii() {
    document.getElementById('id_tipocheque').value = "";
    document.getElementById('descripcion').value = "";
    location.reload();
    CargarTablaTipocheque();
}

function confirmar() {
    confirmar = confirm("Estas seguro que querés eliminar el registro???");
    if (confirmar) {
        construirJSON(accion);
        CargarTablaTipocheque();
        alert('Registro eliminado');
        limpii();
    } else {
        limpii();
// si pulsamos en cancelar
        ///alert('Registro eliminado');
    }
}
function filtrotipochequefocus(){
    document.getElementById('filtrostipocheque').focus();
}


function construirJSON(ban) {
    // alert(ban);
    datos = {
        bandera: ban,
        id_ticheque: (document.getElementById('id_tipocheque').value === "") ? "0" : document.getElementById('id_tipocheque').value,
        descripcion: campoUni
    };
    envio();
    CargarTablaTipocheque();


}

function cargarTipocheque() {
    construirJSON(4);
}

function InputIdLectura() {
    document.getElementById('id_tipocheque').disabled = false;
    document.getElementById('id_tipocheque').value = "";
    document.getElementById('id_tipocheque').readOnly = false;
    document.getElementById('id_tipocheque').placeholder = "Ingrese ID";
    document.getElementById('id_tipocheque').focus();
}
function InputIdLecturaAgregar() {
    document.getElementById('id_tipocheque').disabled = true;
//    document.getElementById('id_cargo').value= "1231";
}

function validarInputsRegistroTipocheque() {
    //Grupo de validaciones
    //
    //document.getElementById("opcionGrabarPerfil").disabled = true;
    document.getElementById("opcionGrabarTipocheque").focus();

}

function agregarTipocheque() {
    accion = 1;
    InputIdLecturaAgregar();
    document.getElementById('descripcion').focus();
}


function modificarTipocheque() {
    accion = 2;
    InputIdLectura();
    //construirJSON(2);
}

function eliminarTipocheque() {
    accion = 3;
    InputIdLectura();
    // construirJSON(3);
}

function grabarTipocheque() {
        switch (accion) {
        case 1:
            verificarTipocheque();
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
    xmlhttp.open("POST", "/AutoserviceWalker/Tipo_chequeCTRL");
    xmlhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xmlhttp.send(JSON.stringify(datos));
    if (xmlhttp.status === 200) {

        alert('respuesta exitosa');
    } else {
        // alert('Error durante la respuesta ');
    }
    CargarTablaTipocheque();
}

function recuperarTipochequeUnico() {
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
                if (json[0].id_tipocheque === 0) {
                    alert('El Codigo Que Ingreso ¡¡No Existe!!, o ha ¡¡Sido Cambiado!!');
                    limpii();
                    return;
                }
                var i;
                for (i = 0; i < json.length; i++) {
                    document.getElementById('id_tipocheque').value = json[i].id_tipocheque;
                    document.getElementById('descripcion').value = json[i].descripcion;
                    document.getElementById('descripcion').focus();

                }

            }

        }
    };
    // 2. Handle Response from Server - End

    // 3. Specify your action, location and Send to the server - Start   
    xhr.open('POST', '/AutoserviceWalker/Tipo_chequeCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 5, id_tipocheque: document.getElementById('id_tipocheque').value}));
    // 3. Specify your action, location and Send to the server - End
}

function CargarTablaTipocheque() {
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
                    filas += "<tr onclick=getTDtipocheque(this);>";
                    filas += "<td id=td1_"+i+">" + json[i].id_ticheque + "</td>";
                    filas += "<td id=td2_"+i+">" + json[i].descripcion + "</td>";
                    filas += "</tr>";
                }
                document.getElementById("crpTbTipocheque").innerHTML = filas;

            }
        }
    };
    // 2. Handle Response from Server - End

    // 3. Specify your action, location and Send to the server - Start   
    xhr.open('POST', '/AutoserviceWalker/Tipo_chequeCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 4}));
    // 3. Specify your action, location and Send to the server - End
}

 function FiltroTipocheque() {
  var input, filter, table, tr, td, i;
  input = document.getElementById("filtrostipocheque");
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


function getTDtipocheque(obj){
    var varTR=obj.getElementsByTagName('td');
     document.getElementById('id_tipocheque').value = varTR[0].innerHTML;
     document.getElementById('descripcion').value = varTR[1].innerHTML;
     document.getElementById('filtrostipocheque').value = "";
     CargarTablaTipocheque();
     document.getElementById('id01').style.display="none";
}

function cerrarbuscador(){
    document.getElementById('id01').style.display="none";
}


