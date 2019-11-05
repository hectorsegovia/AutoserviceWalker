var datos;
var accion;
var MayusCargo;
var campoUni;
CargarTablaCiudad();
//initComponentes();

function comparar() {
    var estado = Boolean(false);
    var Mitexto = $('#descripcion').val();
    Mitexto = Mitexto.toUpperCase();
    $("#crpTbCiudad  tr").each(function () {
        if (Mitexto === $(this).find("td").eq(1).html()) {
            alert("Ya Existe el Registro");
            estado = true;
        }
    });
    return estado;

}

function verificarCiudad() {
     campoUni = $('#descripcion').val();
    campoUni = campoUni.toUpperCase();
    if (campoUni === "") {
        alert('¡¡favor completar todos los campos!!');
        limpiador();
    } 
    else {
        if (comparar() === false) {
            construirJSON(accion);
            alert('Registro Guardados');
            limpiador();
        }
        limpieza();
    }
}

function Preguntar() {
    confirmar = confirm("Estas seguro que querés Modificar el registro???");
    if (confirmar) {
        verificarCiudad();
    } else {
        limpiador();
    }
}

function limpiador() {
    document.getElementById('id_ciudad').value = "";
    document.getElementById('descripcion').value = "";
    location.reload();
    CargarTablaCiudad();
}

function confirmar() {
    confirmar = confirm("Estas seguro que querés eliminar el registro???");
    if (confirmar) {
        construirJSON(accion);
        CargarTablaCiudad();
        alert('Registro eliminado');
        limpiador();
    } else {
        limpiador();
// si pulsamos en cancelar
        ///alert('Registro eliminado');
    }
}
function filtrociudadfocus(){
    document.getElementById('filtrosciudad').focus();
}


function construirJSON(ban) {
    // alert(ban);
    datos = {
        bandera: ban,
        id_ciudad: (document.getElementById('id_ciudad').value === "") ? "0" : document.getElementById('id_ciudad').value,
        descripcion: campoUni
    };
    envio();
    CargarTablaCiudad();


}

function cargarCiudad() {
    construirJSON(4);
}

function InputIdLectura() {
    document.getElementById('id_ciudad').disabled = false;
    document.getElementById('id_ciudad').value = "";
    document.getElementById('id_ciudad').readOnly = false;
    document.getElementById('id_ciudad').placeholder = "Ingrese ID";
    document.getElementById('id_ciudad').focus();
}
function InputIdLecturaAgregar() {
    document.getElementById('id_ciudad').disabled = true;
//    document.getElementById('id_cargo').value= "1231";
}

function validarInputsRegistroCiudad() {
    //Grupo de validaciones
    //
    //document.getElementById("opcionGrabarPerfil").disabled = true;
    document.getElementById("opcionGrabarCiudad").focus();

}

function agregarCiudad() {
    accion = 1;
    InputIdLecturaAgregar();
    document.getElementById('descripcion').focus();
}


function modificarCiudad() {
    accion = 2;
    InputIdLectura();
    //construirJSON(2);
}

function eliminarCiudad() {
    accion = 3;
    InputIdLectura();
    // construirJSON(3);
}

function grabarCiudad() {
    if (accion === 2) {
        Preguntar();
    }
    if (accion === 3) {
        confirmar();
    } else {
        verificarCiudad();
    }

}

function envio() {
    var xmlhttp = new XMLHttpRequest();   // new HttpRequest instance 
    xmlhttp.open("POST", "/AutoserviceWalker/CiudadCTRL");
    xmlhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xmlhttp.send(JSON.stringify(datos));
    if (xmlhttp.status === 200) {

        alert('respuesta exitosa');
    } else {
        // alert('Error durante la respuesta ');
    }
    CargarTablaCiudad();
}

function recuperarCiudadUnico() {
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
                if (json[0].id_ciudad === 0) {
                    alert('El Codigo Que Ingreso ¡¡No Existe!!, o ha ¡¡Sido Cambiado!!');
                    limpieza();
                    return;
                }
                var i;
                for (i = 0; i < json.length; i++) {
                    document.getElementById('id_ciudad').value = json[i].id_ciudad;
                    document.getElementById('descripcion').value = json[i].descripcion;
                    document.getElementById('descripcion').focus();

                }

            }

        }
    };
    // 2. Handle Response from Server - End

    // 3. Specify your action, location and Send to the server - Start   
    xhr.open('POST', '/AutoserviceWalker/CiudadCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 5, id_ciudad: document.getElementById('id_ciudad').value}));
    // 3. Specify your action, location and Send to the server - End
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
                    filas += "<tr onclick=getTDciudad(this);>";
                    filas += "<td id=td1_"+i+">" + json[i].id_ciudad + "</td>";
                    filas += "<td id=td2_"+i+">" + json[i].descripcion + "</td>";
                    filas += "</tr>";
                }
                document.getElementById("crpTbCiudad").innerHTML = filas;

            }
        }
    };
    // 2. Handle Response from Server - End

    // 3. Specify your action, location and Send to the server - Start   
    xhr.open('POST', '/AutoserviceWalker/CiudadCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 4}));
    // 3. Specify your action, location and Send to the server - End
}

 function FiltroCiudad() {
  var input, filter, table, tr, td, i;
  input = document.getElementById("filtrosciudad");
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


function getTDciudad(obj){
    var varTR=obj.getElementsByTagName('td');
     document.getElementById('id_ciudad').value = varTR[0].innerHTML;
     document.getElementById('descripcion').value = varTR[1].innerHTML;
     document.getElementById('filtrosciudad').value = "";
     CargarTablaCiudad();
     document.getElementById('id01').style.display="none";
}

function cerrarbuscador(){
    document.getElementById('id01').style.display="none";
}

