var datos;
var accion;
var MayusCargo;
var campoUni;
CargarTablaPerfil();
//initComponentes();

function comparar() {
    var estado = Boolean(false);
    var Mitexto = $('#descripcion').val();
    Mitexto = Mitexto.toUpperCase();
    $("#crpTbPerfil  tr").each(function () {
        if (Mitexto === $(this).find("td").eq(1).html()) {
            alert("Ya Existe el Registro");
            estado = true;
        }
    });
    return estado;

}

function verificarPerfil() {
     campoUni = $('#descripcion').val();
    campoUni = campoUni.toUpperCase();
    if (campoUni === "") {
        alert('¡¡favor completar todos los campos!!');
        limpieza();
    } 
    else {
        if (comparar() === false) {
            construirJSON(accion);
            alert('Registro Guardados');
            limpieza();
        }
        limpieza();
    }
}

function Preguntar() {
    confirmar = confirm("Estas seguro que querés Modificar el registro???");
    if (confirmar) {
        verificarPerfil();
    } else {
        limpieza();
    }
}

function limpieza() {
    document.getElementById('id_perfil').value = "";
    document.getElementById('descripcion').value = "";
    location.reload();
    CargarTablaPerfil();
}

function confirmar() {
    confirmar = confirm("Estas seguro que querés eliminar el registro???");
    if (confirmar) {
        construirJSON(accion);
        CargarTablaPerfil();
        alert('Registro eliminado');
        limpieza();
    } else {
        limpieza();
// si pulsamos en cancelar
        ///alert('Registro eliminado');
    }
}
function filtroperfilfocus(){
    document.getElementById('filtrosperfil').focus();
}


function construirJSON(ban) {
    // alert(ban);
    datos = {
        bandera: ban,
        id_perfil: (document.getElementById('id_perfil').value === "") ? "0" : document.getElementById('id_perfil').value,
        descripcion: campoUni
    };
    envio();
    CargarTablaPerfil();


}

function cargarPerfil() {
    construirJSON(4);
}

function InputIdLectura() {
    document.getElementById('id_perfil').disabled = false;
    document.getElementById('id_perfil').value = "";
    document.getElementById('id_perfil').readOnly = false;
    document.getElementById('id_perfil').placeholder = "Ingrese ID";
    document.getElementById('id_perfil').focus();
}
function InputIdLecturaAgregar() {
    document.getElementById('id_perfil').disabled = true;
//    document.getElementById('id_cargo').value= "1231";
}

function validarInputsRegistroCargo() {
    //Grupo de validaciones
    //
    //document.getElementById("opcionGrabarPerfil").disabled = true;
    document.getElementById("opcionGrabarPerfil").focus();

}

function agregarPerfiles() {
    accion = 1;
    InputIdLecturaAgregar();
    document.getElementById('descripcion').focus();
}


function modificarPerfiles() {
    accion = 2;
    InputIdLectura();
    //construirJSON(2);
}

function eliminarPerfiles() {
    accion = 3;
    InputIdLectura();
    // construirJSON(3);
}

function grabarPerfiles() {
        switch (accion) {
        case 1:
            verificarPerfil();
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
    xmlhttp.open("POST", "/AutoserviceWalker/PerfilesCTRL");
    xmlhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xmlhttp.send(JSON.stringify(datos));
    if (xmlhttp.status === 200) {

        alert('respuesta exitosa');
    } else {
        // alert('Error durante la respuesta ');
    }
    CargarTablaPerfil();
}

function recuperarPerfilUnico() {
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
                if (json[0].id_perfil === 0) {
                    alert('El Codigo Que Ingreso ¡¡No Existe!!, o ha ¡¡Sido Cambiado!!');
                    limpieza();
                    return;
                }
                var i;
                for (i = 0; i < json.length; i++) {
                    document.getElementById('id_perfil').value = json[i].id_perfil;
                    document.getElementById('descripcion').value = json[i].descripcion;
                    document.getElementById('descripcion').focus();

                }

            }

        }
    };
    // 2. Handle Response from Server - End

    // 3. Specify your action, location and Send to the server - Start   
    xhr.open('POST', '/AutoserviceWalker/PerfilesCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 5, id_perfil: document.getElementById('id_perfil').value}));
    // 3. Specify your action, location and Send to the server - End
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
                    filas += "<tr onclick=getTDperfil(this);>";
                    filas += "<td id=td1_"+i+">" + json[i].id_perfil + "</td>";
                    filas += "<td id=td2_"+i+">" + json[i].descripcion + "</td>";
                    filas += "</tr>";
                }
                document.getElementById("crpTbPerfil").innerHTML = filas;

            }
        }
    };
    // 2. Handle Response from Server - End

    // 3. Specify your action, location and Send to the server - Start   
    xhr.open('POST', '/AutoserviceWalker/PerfilesCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 4}));
    // 3. Specify your action, location and Send to the server - End
}

 function FiltroPerfil() {
  var input, filter, table, tr, td, i;
  input = document.getElementById("filtrosperfil");
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


function getTDperfil(obj){
    var varTR=obj.getElementsByTagName('td');
     document.getElementById('id_perfil').value = varTR[0].innerHTML;
     document.getElementById('descripcion').value = varTR[1].innerHTML;
     document.getElementById('filtrosperfil').value = "";
     CargarTablaPerfil();
     document.getElementById('id01').style.display="none";
}

function cerrarbuscador(){
    document.getElementById('id01').style.display="none";
}
