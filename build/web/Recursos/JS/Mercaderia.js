
/* global codigo_barra, descripcion */

CargarTablaMarcas();
CargarTablaSabor();
CargarTablaCategoria();
CargarTablaFamilias();
CargarTablaSubcategoria();
CargarTablaImpuestos();
CargarTablaMedidas();
CargarTablaTipoFamilia();


CargarTablaMercaderias();
var datos;
var accion;
var descripcion;
var codigo_barra;
var precio;
var unidad;


function comparar() {
    var estado = Boolean(false);
    var codigo_barras = $('#codigo_barra').val();
    codigo_barras = codigo_barras.toUpperCase();
    $("#crpTbMercaderias  tr").each(function () {
        if (codigo_barras === $(this).find("td").eq(1).html()) {
            alert("Ya Existe el Registro");
            estado = true;
        }
    });
    return estado;

}

function verificarMercaderias() {
    descripcion = $('#descripcion').val();
    precio = $('#precio').val();
    unidad = $('#unidad').val();
    codigo_barra = $('#codigo_barra').val();
    
    descripcion = descripcion.toUpperCase();
    precio = precio.toUpperCase();
    unidad = unidad.toUpperCase();
    codigo_barra = codigo_barra.toUpperCase();

    if (descripcion === "" && precio === "" && unidad === "" && codigo_barra === "") {
        alert('¡¡favor completar todos los campos!!');
        limpieezae();
    } else {
        if (comparar() === false) {
            construirJSON(accion);
            alert('Registro Guardados');
            limpieezae();
        }
        limpieezae();
    }
}

function Preguntar() {
    confirmar = confirm("Estas seguro que querés Modificar el registro???");
    if (confirmar) {
        verificarMercaderias();
    } else {
        limpieezae();
    }
}

function limpieezae() {
    document.getElementById('id_mercaderia').value = "";
    document.getElementById('codigo_barra').value = "";
    document.getElementById('descripcion').value = "";
    document.getElementById('precio').value = "";
    document.getElementById('unidad').value = "";
    location.reload();
    CargarTablaMercaderias();
}

function confirmar() {
    confirmar = confirm("Estas seguro que querés eliminar el registro???");
    if (confirmar) {
        construirJSON(accion);
        CargarTablaMercaderias();
        alert('Registro eliminado');
        limpieezae();
    } else {
        limpieezae();
// si pulsamos en cancelar
        ///alert('Registro eliminado');
    }
}



function construirJSON(ban) {
    // alert(ban);
    datos = {
        bandera: ban,
        id_mercaderia: (document.getElementById('id_mercaderia').value === "") ? "0" : document.getElementById('id_mercaderia').value,
        codigo_barra: codigo_barra,
        descripcion: document.getElementById('descripcion').value,
        precio: document.getElementById('precio').value,
        id_marca: document.getElementById('id_marca').value,
        id_medida: document.getElementById('id_medida').value,
        id_sabor: document.getElementById('id_sabor').value,
        id_impuesto: document.getElementById('id_impuesto').value,
        id_categoria: document.getElementById('id_categoria').value,
        unidad: document.getElementById('unidad').value,
        id_familia: document.getElementById('id_familia').value,
        id_subcategoria: document.getElementById('id_subcategoria').value,
        id_tifamilia: document.getElementById('id_tifamilia').value
    };
    envio();
    CargarTablaMercaderias();

}

function cargarMercaderias() {
    construirJSON(4);
}

function InputIdLectura() {
    document.getElementById('id_mercaderia').disabled = false;
    document.getElementById('id_mercaderia').value = "";
    document.getElementById('id_mercaderia').readOnly = false;
    document.getElementById('id_mercaderia').placeholder = "Ingrese ID";
    document.getElementById('filtrosmercaderias').focus();
}
function InputIdLecturaAgregar() {
    document.getElementById('id_mercaderia').disabled = true;
//    document.getElementById('id_cargo').value= "1231";
}



function agregarMercaderias() {
    accion = 1;
    InputIdLecturaAgregar();
    document.getElementById('codigo_barra').disabled = false;
    document.getElementById('codigo_barra').focus();
}

function desabilitarcampos(){
    document.getElementById('precio').disabled = false;
    document.getElementById('precio').focus();
}

function modificarMercaderias() {
    accion = 2;
    InputIdLectura();
    //construirJSON(2);
}

function eliminarMercaderias() {
    accion = 3;
    InputIdLectura();
    // construirJSON(3);
}

function grabarMercaderias() {
    switch (accion) {
        case 1:
            verificarMercaderias();
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
    xmlhttp.open("POST", "/AutoserviceWalker/MercaderiaCTRL");
    xmlhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xmlhttp.send(JSON.stringify(datos));
    if (xmlhttp.status === 200) {

        alert('respuesta exitosa');
    } else {
        // alert('Error durante la respuesta ');
    }
    CargarTablaMercaderias();
}

function recuperarMercaderiasUnico() {
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
                if (json[0].id_empleados === 0) {
                    alert('El Codigo Que Ingreso ¡¡No Existe!!, o ha ¡¡Sido Cambiado!!');
                    limpieezae();
                    return;
                }
                var i;
                for (i = 0; i < json.length; i++) {
                    document.getElementById('id_mercaderia').value = json[i].id_mercaderia;
                    document.getElementById('codigo_barra').value = json[i].codigo_barra;
                    document.getElementById('descripcion').value = json[i].descripcion;
                    document.getElementById('precio').value = json[i].precio;
                    document.getElementById('unidad').value = json[i].unidad;
                    document.getElementById('id_marca').value = json[i].id_marca;
                    document.getElementById('id_medida').value = json[i].id_medida;
                    document.getElementById('id_sabor').value = json[i].id_sabor;
                    document.getElementById('id_impuesto').value = json[i].id_impuesto;
                    document.getElementById('id_categoria').value = json[i].id_categoria;
                    document.getElementById('id_familia').value = json[i].id_familia;
                    document.getElementById('id_subcategoria').value = json[i].id_subcategoria;
                    document.getElementById('id_tifamilia').value = json[i].id_tifamilia;
                    document.getElementById('descripcion').focus();

                }

            }

        }
    };
    // 2. Handle Response from Server - End

    // 3. Specify your action, location and Send to the server - Start   
    xhr.open('POST', '/AutoserviceWalker/MercaderiaCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 5, id_mercaderia: document.getElementById('id_mercaderia').value}));
    // 3. Specify your action, location and Send to the server - End
}

function CargarTablaMercaderias() {
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
                    filas += "<tr onclick=getTDmercaderias(this);>";
                    filas += "<td id=td1_" + i + ">" + json[i].id_mercaderia + "</td>";
                    filas += "<td id=td2_" + i + ">" + json[i].codigo_barra + "</td>";
                    filas += "<td id=td3_" + i + ">" + json[i].descripcion + "</td>";
                    filas += "<td id=td4_" + i + ">" + json[i].precio + "</td>";
                    filas += "<td id=td4_" + i + ">" + json[i].id_marca + "</td>";
                    filas += "</tr>";
                }
                document.getElementById("crpTbMercaderias").innerHTML = filas;

            }
        }
    };
    // 2. Handle Response from Server - End

    // 3. Specify your action, location and Send to the server - Start   
    xhr.open('POST', '/AutoserviceWalker/MercaderiaCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 4}));
    // 3. Specify your action, location and Send to the server - End
}

function FiltroMercaderias() {
    var input, filter, table, tr, td, i;
    input = document.getElementById("filtrosmercaderias");
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
function FiltroMercaderiass() {
    var input, filter, table, tr, td, i;
    input = document.getElementById("filtrosmercaderiass");
    filter = input.value.toUpperCase();
    table = document.getElementById("id01");
    tr = table.getElementsByTagName("tr");
    for (i = 0; i < tr.length; i++) {
        td = tr[i].getElementsByTagName("td")[2];
        if (td) {
            if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {
                tr[i].style.display = "";
            } else {
                tr[i].style.display = "none";
            }
        }
    }
}


function getTDmercaderias(obj) {
    var varTR = obj.getElementsByTagName('td');
    document.getElementById('id_mercaderia').value = varTR[0].innerHTML;
    recuperarMercaderiasUnico();
    document.getElementById('filtrosmercaderias').value = "";
    CargarTablaMercaderias();
    document.getElementById('id01').style.display = "none";
}

function cerrarbuscador() {
    document.getElementById('id01').style.display = "none";
}


function CargarTablaSabor() {
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

                    filas += "<option value= " + json[i].id_sabor + ">" + json[i].descripcion + "</option>";
                }
                document.getElementById("id_sabor").innerHTML = filas;

            }
        }
    };
    xhr.open('POST', '/AutoserviceWalker/MercaderiaCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 7}));
}


function CargarTablaCategoria() {
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

                    filas += "<option value= " + json[i].id_categoria + ">" + json[i].descripcion + "</option>";
                }
                document.getElementById("id_categoria").innerHTML = filas;

            }
        }
    };
    xhr.open('POST', '/AutoserviceWalker/MercaderiaCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 8}));
}

function CargarTablaMarcas() {
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

                    filas += "<option value= " + json[i].id_marca + ">" + json[i].descripcion + "</option>";
                }
                document.getElementById("id_marca").innerHTML = filas;

            }
        }
    };
    xhr.open('POST', '/AutoserviceWalker/MercaderiaCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 6}));

}

    function CargarTablaFamilias() {
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

                    filas += "<option value= " + json[i].id_familia + ">" + json[i].descripcion + "</option>";
                }
                document.getElementById("id_familia").innerHTML = filas;

            }
        }
    };
    xhr.open('POST', '/AutoserviceWalker/MercaderiaCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 9}));
}
  
    function CargarTablaSubcategoria() {
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

                    filas += "<option value= " + json[i].id_subcategoria + ">" + json[i].descripcion + "</option>";
                }
                document.getElementById("id_subcategoria").innerHTML = filas;

            }
        }
    };
    xhr.open('POST', '/AutoserviceWalker/MercaderiaCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 10}));
}
    function CargarTablaImpuestos() {
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

                    filas += "<option value= " + json[i].id_impuesto + ">" + json[i].descripcion + "</option>";
                }
                document.getElementById("id_impuesto").innerHTML = filas;

            }
        }
    };
    xhr.open('POST', '/AutoserviceWalker/MercaderiaCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 13}));
}


    function CargarTablaMedidas() {
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

                    filas += "<option value= " + json[i].id_medida + ">" + json[i].descripcion + "</option>";
                }
                document.getElementById("id_medida").innerHTML = filas;

            }
        }
    };
    xhr.open('POST', '/AutoserviceWalker/MercaderiaCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 12}));
}

    

    function CargarTablaTipoFamilia() {
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

                    filas += "<option value= " + json[i].id_tifamilia + ">" + json[i].descripcion + "</option>";
                }
                document.getElementById("id_tifamilia").innerHTML = filas;

            }
        }
    };
    xhr.open('POST', '/AutoserviceWalker/MercaderiaCTRL');
    xhr.send(JSON.stringify(datos = {bandera: 11}));
}

    



