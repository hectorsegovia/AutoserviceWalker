var datos;
var accion;
var MayusCargo;
var campoUni;
//initComponentes();


function ssaludo(){
    alert('holaa');
}
    var nombre = prompt("Dime tu nombre");
    document.write( nombre );
    
function comparar() {
    var estado = Boolean(false);
    var Mitexto = $('#descripcion').val();
    Mitexto = Mitexto.toUpperCase();
    $("#crpTbPromos  tr").each(function () {
        if (Mitexto === $(this).find("td").eq(1).html()) {
            alert("Ya Existe el Registro");
            estado = true;
        }
    });
    return estado;

}

function verificarPromos() {
     campoUni = $('#descripcion').val();
    campoUni = campoUni.toUpperCase();
    if (campoUni === "") {
        alert('¡¡favor completar todos los campos!!');
        lim();
    } 
    else {
        if (comparar() === false) {
            construirJSON(accion);
            alert('Registro Guardados');
        }
        lim();
    }
}

function Preguntar() {
    confirmar = confirm("Estas seguro que querés Modificar el registro???");
    if (confirmar) {
//        verificarCajas();
    } else {
        lim();
    }
}

function lim() {
    document.getElementById('id_promos').value = "";
    document.getElementById('descripcion').value = "";
    location.reload();
//    CargarTablaCajas();
}

function confirmar() {
    confirmar = confirm("Estas seguro que querés eliminar el registro???");
    if (confirmar) {
        construirJSON(accion);
//        CargarTablaCajas();
        alert('Registro eliminado');
        lim();
    } else {
        lim();
// si pulsamos en cancelar
        ///alert('Registro eliminado');
    }
}



function construirJSON(ban) {
    // alert(ban);
    datos = {
        bandera: ban,
        id_promo: (document.getElementById('id_promos').value),
        nombre: campoUni,
        imagen: (document.getElementById('span').value)
    };
    envio();
//    CargarTablaCajas();


}





function agregarPromos() {
    //construirJSON(1);
    accion = 1;
    document.getElementById('descripcion').focus();
}


function modificarPromos() {
    accion = 2;
    //construirJSON(2);
}

function eliminarPromos() {
    accion = 3;
    // construirJSON(3);
}

function grabarPromos() {
         switch (accion) {
        case 1:
            verificarPromos();
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
    xmlhttp.open("POST", "/AutoserviceWalker/PromoCTRL");
    xmlhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xmlhttp.send(JSON.stringify(datos));
    if (xmlhttp.status === 200) {

        alert('respuesta exitosa');
    } else {
        // alert('Error durante la respuesta ');
    }
//    CargarTablaCajas();
}

function recuperarPromosUnico() {
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
                if (json[0].id_promo === 0) {
                    alert('El Codigo Que Ingreso ¡¡No Existe!!, o ha ¡¡Sido Cambiado!!');
                    limpiar();
                    return;
                }
                var i;
                for (i = 0; i < json.length; i++) {
                    document.getElementById('id_promos').value = json[i].id_promo;
                    document.getElementById('descripcion').value = json[i].descripcion;
                    document.getElementById('descripcion').focus();

                }

            }

        }
    };
    // 2. Handle Response from Server - End

    // 3. Specify your action, location and Send to the server - Start   
    xhr.open('POST', '/AutoserviceWalker/PromoCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 5, id_promo: document.getElementById('id_promos').value}));
    // 3. Specify your action, location and Send to the server - End
}





