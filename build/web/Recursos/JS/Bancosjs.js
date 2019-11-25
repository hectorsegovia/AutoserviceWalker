var datos;
var accion;
var MayusBancos;
var campoUni;
CargarTablaBancos();
//initComponentes();

function comparar() {
    var estado = Boolean(false);
    var Mitexto = $('#nombre').val();
    var direccion = $('#direccion').val();
    Mitexto = Mitexto.toUpperCase();
    direccion = direccion.toUpperCase();
    
    $("#crpTbBancos  tr").each(function () {
        if (Mitexto === $(this).find("td").eq(1).html() & direccion === $(this).find("td").eq(2).html()) {
            alert("Ya Existe el Registro");
            estado = true;
        }
    });
    return estado;

}

function verificarBancos() {
     campoUni = $('#nombre').val();
     MayusBancos = $('#direccion').val();
     
    campoUni = campoUni.toUpperCase();
    MayusBancos = MayusBancos.toUpperCase();
    if (campoUni === "" && MayusBancos === "") {
        alert('¡¡favor completar todos los campos!!');
        limpr();
    } 
    else {
        if (comparar() === false) {
            construirJSON(accion);
            alert('Registro Guardados');
        }
        limpr();
    }
}

function Preguntar() {
    confirmar = confirm("Estas seguro que querés Modificar el registro???");
    if (confirmar) {
        verificarBancos();
    } else {
        limpiar();
    }
}

function limpr() {
    document.getElementById('id_bancos').value = "";
    document.getElementById('nombre').value = "";
    document.getElementById('direccion').value = "";
    document.getElementById('telefono').value = "";
    location.reload();
    CargarTablaBancos();
}

function confirmar() {
    confirmar = confirm("Estas seguro que querés eliminar el registro???");
    if (confirmar) {
        construirJSON(accion);
        CargarTablaBancos();
        alert('Registro eliminado');
        limpr();
    } else {
        limpr();
// si pulsamos en cancelar
        ///alert('Registro eliminado');
    }
}
function filtrobancosfocus(){
    document.getElementById('filtrosbancos').focus();
}


function construirJSON(ban) {
    // alert(ban);
    datos = {
        bandera: ban,
        id_banco: (document.getElementById('id_bancos').value === "") ? "0" : document.getElementById('id_bancos').value,
        nombre: campoUni,
        direccion: MayusBancos,
        telefono: document.getElementById('telefono').value
    };
    envio();
    CargarTablaBancos();


}

function cargarBancos() {
    construirJSON(4);
}

function InputIdLectura() {
    document.getElementById('id_bancos').disabled = false;
    document.getElementById('id_bancos').value = "";
    document.getElementById('id_bancos').readOnly = false;
    document.getElementById('id_bancos').placeholder = "Ingrese ID";
    document.getElementById('id_bancos').focus();
}
function InputIdLecturaAgregar() {
    document.getElementById('id_bancos').disabled = true;
//    document.getElementById('id_cargo').value= "1231";
}

function validarInputsRegistroCargo() {
    //Grupo de validaciones
    //
    //document.getElementById("opcionGrabarPerfil").disabled = true;
    document.getElementById("opcionGrabarBancos").focus();

}

function agregarBancos() {
    //construirJSON(1);
    accion = 1;
    InputIdLecturaAgregar();
    document.getElementById('nombre').focus();
}


function modificarBancos() {
    accion = 2;
    InputIdLectura();
    //construirJSON(2);
}

function eliminarBancos() {
    accion = 3;
    InputIdLectura();
    // construirJSON(3);
}

function grabarBancos() {
         switch (accion) {
        case 1:
            verificarBancos();
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
    xmlhttp.open("POST", "/AutoserviceWalker/BancosCTRL");
    xmlhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xmlhttp.send(JSON.stringify(datos));
    if (xmlhttp.status === 200) {

        alert('respuesta exitosa');
    } else {
        // alert('Error durante la respuesta ');
    }
    CargarTablaBancos();
}

function recuperarBancosUnico() {
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
                if (json[0].id_banco === 0) {
                    alert('El Codigo Que Ingreso ¡¡No Existe!!, o ha ¡¡Sido Cambiado!!');
                    limpr();
                    return;
                }
                var i;
                for (i = 0; i < json.length; i++) {
                    document.getElementById('id_cargo').value = json[i].id_banco;
                    document.getElementById('nombre').value = json[i].nombre;
                    document.getElementById('direccion').value = json[i].direccion;
                    document.getElementById('telefono').value = json[i].telefono;
                    document.getElementById('nombre').focus();

                }

            }

        }
    };
    // 2. Handle Response from Server - End

    // 3. Specify your action, location and Send to the server - Start   
    xhr.open('POST', '/AutoserviceWalker/BancosCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 5, id_banco: document.getElementById('id_bancos').value}));
    // 3. Specify your action, location and Send to the server - End
}

function CargarTablaBancos() {
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
                    filas += "<tr onclick=getTDbancos(this);>";
                    filas += "<td id=td1_"+i+">" + json[i].id_banco + "</td>";
                    filas += "<td id=td2_"+i+">" + json[i].nombre + "</td>";
                    filas += "<td id=td2_"+i+">" + json[i].direccion + "</td>";
                    filas += "<td id=td2_"+i+">" + json[i].telefono + "</td>";
                    filas += "</tr>";
                }
                document.getElementById("crpTbBancos").innerHTML = filas;

            }
        }
    };
    // 2. Handle Response from Server - End

    // 3. Specify your action, location and Send to the server - Start   
    xhr.open('POST', '/AutoserviceWalker/BancosCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 4}));
    // 3. Specify your action, location and Send to the server - End
}

 function FiltroBancos() {
  var input, filter, table, tr, td, i;
  input = document.getElementById("filtrosbancos");
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


function getTDbancos(obj){
    var varTR=obj.getElementsByTagName('td');
     document.getElementById('id_bancos').value = varTR[0].innerHTML;
     document.getElementById('nombre').value = varTR[1].innerHTML;
     document.getElementById('direccion').value = varTR[2].innerHTML;
     document.getElementById('telefono').value = varTR[3].innerHTML;
     document.getElementById('filtrosbancos').value = "";
     CargarTablaBancos();
     document.getElementById('id01').style.display="none";
}

function cerrarbuscador(){
    document.getElementById('id01').style.display="none";
}

