var datos;
var accion;
var MayusCargo;
var campoUni;
CargarTablaTipofamilia();
//initComponentes();

function comparar() {
    var estado = Boolean(false);
    var Mitexto = $('#descripcion').val();
    Mitexto = Mitexto.toUpperCase();
    $("#crpTbTipofamilia  tr").each(function () {
        if (Mitexto === $(this).find("td").eq(1).html()) {
            alert("Ya Existe el Registro");
            estado = true;
        }
    });
    return estado;

}

function verificarTipofamilia() {
     campoUni = $('#descripcion').val();
    campoUni = campoUni.toUpperCase();
    if (campoUni === "") {
        alert('¡¡favor completar todos los campos!!');
        limmmmmm();
    } 
    else {
        if (comparar() === false) {
            construirJSON(accion);
            alert('Registro Guardados');
        }
        limmmmmm();
    }
}

function Preguntar() {
    confirmar = confirm("Estas seguro que querés Modificar el registro???");
    if (confirmar) {
        verificarTipofamilia();
    } else {
        limmmmmm();
    }
}

function limmmmmm() {
    document.getElementById('id_tipofamilia').value = "";
    document.getElementById('descripcion').value = "";
    location.reload();
    CargarTablaTipofamilia();
}

function confirmar() {
    confirmar = confirm("Estas seguro que querés eliminar el registro???");
    if (confirmar) {
        construirJSON(accion);
        CargarTablaTipofamilia();
        alert('Registro eliminado');
        limmmmmm();
    } else {
        limmmmmm();
// si pulsamos en cancelar
        ///alert('Registro eliminado');
    }
}
function filtrotipofamiliasfocus(){
    document.getElementById('filtrostipofamilia').focus();
}


function construirJSON(ban) {
    // alert(ban);
    datos = {
        bandera: ban,
        id_tifamilia: (document.getElementById('id_tipofamilia').value === "") ? "0" : document.getElementById('id_tipofamilia').value,
        descripcion: campoUni
    };
    envio();
    CargarTablaTipofamilia();


}

function cargarTipofamilia() {
    construirJSON(4);
}

function InputIdLectura() {
    document.getElementById('id_tipofamilia').disabled = false;
    document.getElementById('id_tipofamilia').value = "";
    document.getElementById('id_tipofamilia').readOnly = false;
    document.getElementById('id_tipofamilia').placeholder = "Ingrese ID";
    document.getElementById('id_tipofamilia').focus();
}
function InputIdLecturaAgregar() {
    document.getElementById('id_tipofamilia').disabled = true;
//    document.getElementById('id_cargo').value= "1231";
}

function validarInputsRegistroTipofamilia() {
    //Grupo de validaciones
    //
    //document.getElementById("opcionGrabarPerfil").disabled = true;
    document.getElementById("opcionGrabarTipofamilia").focus();

}

function agregarTipofamilia() {
    //construirJSON(1);
    accion = 1;
    InputIdLecturaAgregar();
    document.getElementById('descripcion').focus();
}


function modificarTipofamilia() {
    accion = 2;
    InputIdLectura();
    //construirJSON(2);
}

function eliminarTipofamilia() {
    accion = 3;
    InputIdLectura();
    // construirJSON(3);
}

function grabarTipofamilia() {
         switch (accion) {
        case 1:
            verificarTipofamilia();
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
    xmlhttp.open("POST", "/AutoserviceWalker/tipo_familiaCTRL");
    xmlhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xmlhttp.send(JSON.stringify(datos));
    if (xmlhttp.status === 200) {

        alert('respuesta exitosa');
    } else {
        // alert('Error durante la respuesta ');
    }
    CargarTablaTipofamilia();
}

function recuperarTipofamiliaUnico() {
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
                if (json[0].id_tifamilia === 0) {
                    alert('El Codigo Que Ingreso ¡¡No Existe!!, o ha ¡¡Sido Cambiado!!');
                    limmmmmm();
                    return;
                }
                var i;
                for (i = 0; i < json.length; i++) {
                    document.getElementById('id_tipofamilia').value = json[i].id_tifamilia;
                    document.getElementById('descripcion').value = json[i].descripcion;
                    document.getElementById('descripcion').focus();

                }

            }

        }
    };
    // 2. Handle Response from Server - End

    // 3. Specify your action, location and Send to the server - Start   
    xhr.open('POST', '/AutoserviceWalker/tipo_familiaCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 5, id_tifamilia: document.getElementById('id_tipofamilia').value}));
    // 3. Specify your action, location and Send to the server - End
}

function CargarTablaTipofamilia() {
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
                    filas += "<tr onclick=getTDtipofamilia(this);>";
                    filas += "<td id=td1_"+i+">" + json[i].id_tifamilia + "</td>";
                    filas += "<td id=td2_"+i+">" + json[i].descripcion + "</td>";
                    filas += "</tr>";
                }
                document.getElementById("crpTbTipofamilia").innerHTML = filas;

            }
        }
    };
    // 2. Handle Response from Server - End

    // 3. Specify your action, location and Send to the server - Start   
    xhr.open('POST', '/AutoserviceWalker/tipo_familiaCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 4}));
    // 3. Specify your action, location and Send to the server - End
}

 function FiltroTipofamilia() {
  var input, filter, table, tr, td, i;
  input = document.getElementById("filtrostipofamilia");
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


function getTDtipofamilia(obj){
    var varTR=obj.getElementsByTagName('td');
     document.getElementById('id_tipofamilia').value = varTR[0].innerHTML;
     document.getElementById('descripcion').value = varTR[1].innerHTML;
     document.getElementById('filtrostipofamilia').value = "";
     CargarTablaTipofamilia();
     document.getElementById('id01').style.display="none";
}

function cerrarbuscador(){
    document.getElementById('id01').style.display="none";
}


