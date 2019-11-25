var datos;
var accion;
var MayusCargo;
var campoUni;
CargarTablaTipocobro();
//initComponentes();

function comparar() {
    var estado = Boolean(false);
    var Mitexto = $('#descripcion').val();
    Mitexto = Mitexto.toUpperCase();
    $("#crpTbTipocobro  tr").each(function () {
        if (Mitexto === $(this).find("td").eq(1).html()) {
            alert("Ya Existe el Registro");
            estado = true;
        }
    });
    return estado;

}

function verificarTipocobro() {
     campoUni = $('#descripcion').val();
    campoUni = campoUni.toUpperCase();
    if (campoUni === "") {
        alert('¡¡favor completar todos los campos!!');
        limmm();
    } 
    else {
        if (comparar() === false) {
            construirJSON(accion);
            alert('Registro Guardados');
        }
        limmm();
    }
}

function Preguntar() {
    confirmar = confirm("Estas seguro que querés Modificar el registro???");
    if (confirmar) {
        verificarTipocobro();
    } else {
        limmm();
    }
}

function limmm() {
    document.getElementById('id_tipocobro').value = "";
    document.getElementById('descripcion').value = "";
    location.reload();
    CargarTablaTipocobro();
}

function confirmar() {
    confirmar = confirm("Estas seguro que querés eliminar el registro???");
    if (confirmar) {
        construirJSON(accion);
        CargarTablaTipocobro();
        alert('Registro eliminado');
        limmm();
    } else {
        limmm();
// si pulsamos en cancelar
        ///alert('Registro eliminado');
    }
}
function filtrotipocobrofocus(){
    document.getElementById('filtrostipocobro').focus();
}


function construirJSON(ban) {
    // alert(ban);
    datos = {
        bandera: ban,
        id_ticobro: (document.getElementById('id_tipocobro').value === "") ? "0" : document.getElementById('id_tipocobro').value,
        descripcion: campoUni
    };
    envio();
    CargarTablaTipocobro();


}

function cargarTipocobro() {
    construirJSON(4);
}

function InputIdLectura() {
    document.getElementById('id_tipocobro').disabled = false;
    document.getElementById('id_tipocobro').value = "";
    document.getElementById('id_tipocobro').readOnly = false;
    document.getElementById('id_tipocobro').placeholder = "Ingrese ID";
    document.getElementById('id_tipocobro').focus();
}
function InputIdLecturaAgregar() {
    document.getElementById('id_tipocobro').disabled = true;
//    document.getElementById('id_cargo').value= "1231";
}

function validarInputsRegistroCajas() {
    //Grupo de validaciones
    //
    //document.getElementById("opcionGrabarPerfil").disabled = true;
    document.getElementById("opcionGrabarTipocobro").focus();

}

function agregarTipocobro() {
    //construirJSON(1);
    accion = 1;
    InputIdLecturaAgregar();
    document.getElementById('descripcion').focus();
}


function modificarTipocobro() {
    accion = 2;
    InputIdLectura();
    //construirJSON(2);
}

function eliminarTipocobro() {
    accion = 3;
    InputIdLectura();
    // construirJSON(3);
}

function grabarTipocobro() {
         switch (accion) {
        case 1:
            verificarTipocobro();
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
    xmlhttp.open("POST", "/AutoserviceWalker/Tipo_cobroCTRL");
    xmlhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xmlhttp.send(JSON.stringify(datos));
    if (xmlhttp.status === 200) {

        alert('respuesta exitosa');
    } else {
        // alert('Error durante la respuesta ');
    }
    CargarTablaTipocobro();
}

function recuperarTipocobroUnico() {
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
                if (json[0].id_ticobro === 0) {
                    alert('El Codigo Que Ingreso ¡¡No Existe!!, o ha ¡¡Sido Cambiado!!');
                    limmm();
                    return;
                }
                var i;
                for (i = 0; i < json.length; i++) {
                    document.getElementById('id_tipocobro').value = json[i].id_ticobro;
                    document.getElementById('descripcion').value = json[i].descripcion;
                    document.getElementById('descripcion').focus();

                }

            }

        }
    };
    // 2. Handle Response from Server - End

    // 3. Specify your action, location and Send to the server - Start   
    xhr.open('POST', '/AutoserviceWalker/Tipo_cobroCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 5, id_ticobro: document.getElementById('id_tipocobro').value}));
    // 3. Specify your action, location and Send to the server - End
}

function CargarTablaTipocobro() {
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
                    filas += "<tr onclick=getTDtipocobro(this);>";
                    filas += "<td id=td1_"+i+">" + json[i].id_ticobro + "</td>";
                    filas += "<td id=td2_"+i+">" + json[i].descripcion + "</td>";
                    filas += "</tr>";
                }
                document.getElementById("crpTbTipocobro").innerHTML = filas;

            }
        }
    };
    // 2. Handle Response from Server - End

    // 3. Specify your action, location and Send to the server - Start   
    xhr.open('POST', '/AutoserviceWalker/Tipo_cobroCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 4}));
    // 3. Specify your action, location and Send to the server - End
}

 function FiltroTipocobro() {
  var input, filter, table, tr, td, i;
  input = document.getElementById("filtrostipocobro");
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


function getTDtipocobro(obj){
    var varTR=obj.getElementsByTagName('td');
     document.getElementById('id_tipocobro').value = varTR[0].innerHTML;
     document.getElementById('descripcion').value = varTR[1].innerHTML;
     document.getElementById('filtrostipocobro').value = "";
     CargarTablaTipocobro();
     document.getElementById('id01').style.display="none";
}

function cerrarbuscador(){
    document.getElementById('id01').style.display="none";
}


