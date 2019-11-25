function Fecha_Factura_Compra() {
    var someDateString = moment().format("YYYY-MM-DD");
//    var someDate = moment(someDateString, "YYYY/MM/DD");
    moment().format("YYYY/MM/DD");
//    (document.getElementById('fecha_solicitud').value === '' ? 0 : document.getElementById('fecha_solicitud').value)
    document.getElementById('factura_compra_fecha').value = someDateString;
}

function checkId(cod) {
    let ids = document.querySelectorAll('#detalleTablaMercaderia_Factura_Compra td[for="cod"]');
    return [].filter.call(ids, td => td.textContent === cod).length === 1;
}

function agregarFila_TablaMercaderiaFacturaCompra() {
    var cod = document.getElementById('codigo_mercaderia').value;
    var des = document.getElementById('mercaderia').value;
    var pre = document.getElementById('precio_unitario_mercaderia').value;
    var cant = document.getElementById('cantidad_mercaderia').value;
    var exento = document.getElementById('excento').value;
    var iva5 = document.getElementById('iva5').value;
    var iva10 = document.getElementById('iva10').value;
//    var total = 0;
    var tindex;
    cant = cant.replace(".", "");
    pre = pre.replace(".", "");
    var subtotal = cant * pre;
//    var exento = (cant * pre)*0/100;
//    var iva5 = (cant * pre)*5/100;
//    var iva10 = (cant * pre)*10/100;
//    total += subtotal;
//    document.getElementById('precio_total').value = total;
    tindex++;

    if (checkId(cod)) {
        var des = document.getElementById('mercaderia').value;
        return alert(des + ' ya fue cargado a la tabla detalle');
    }

    $('#detalleTablaMercaderia_Factura_Compra').append("<tr id=\'prod" + tindex + "\'>\n\
    <td for='cod' id='cod' style=' width: 10%;'>" + cod + "</td>\n\
    <td id='des' style=' width: 20%;'>" + des + "</td>\n\
    <td id='cant' style=' width: 8%;'>" + cant + "</td>\n\
    <td id='pre' style=' width: 13%;'>" + format(pre) + "</td>\n\
    <td id='ex_td' style=' width: 13%;'>" + format(exento) + "</td>\n\
    <td id='5_td' style=' width: 13%;'>" + format(iva5) + "</td>\n\
    <td id='10_td' style=' width: 13%;'>" + format(iva10) + "</td>\n\
    <td id='pre' style=' width: 15%;'>" + format(subtotal) + "</td>\n\
    <td style=' width: 5%;'><img class='update' onclick=\"$(\'#prod" + tindex + "\');calcularTotalCompra()\" src='../Recursos/Img/update.png'/></td>\n\
    <td style=' width: 5%;'><img class='delete' onclick=\"$(\'#prod" + tindex + "\');calcularTotalCompra()\" src='../Recursos/Img/delete.png'/></td>\n\
    </tr>");

    calcularTotalCompra();
}

function verificarDetalleFacturaCompra(evt) {
    var cod = document.getElementById('codigo_mercaderia').value;
    var des = document.getElementById('mercaderia').value;
    var pre = document.getElementById('precio_unitario_mercaderia').value;
    var can = document.getElementById('cantidad_mercaderia').value;

    if (cod.length === 0 || des.length === 0 || pre.length === 0) {
        if (confirm('Debe Ingresar una Mercaderia')) {
            evt.preventDefault();
            document.getElementById('cantidad_mercaderia').focus();
        } else {
            limpiarInputMercaderia_Factura_Compra();
            evt.preventDefault();
        }
    }
    if (can.length === 0) {
        if (confirm('Debe ingresar la Cantidad')) {
            evt.preventDefault();
            document.getElementById('cantidad_mercaderia').focus();
        } else {
            limpiarInputMercaderia_Factura_Compra();
            evt.preventDefault();
        }
    }
}

function limpiarInputMercaderia_Factura_Compra() {
    document.getElementById('codigo_mercaderia').value = '';
    document.getElementById('mercaderia').value = '';
    document.getElementById('precio_unitario_mercaderia').value = '';
    document.getElementById('cantidad_mercaderia').value = '';
    document.getElementById('excento').value = '';
    document.getElementById('iva5').value = '';
    document.getElementById('iva10').value = '';
    document.getElementById('cantidad_mercaderia').focus();
}

function procesarJSON(bandera) {

    var listaMercaderiaFacturaCompra = [];
    $("#detalleTablaMercaderia_Factura_Compra  tr").each(function () {

        listaMercaderiaFacturaCompra.push({
            cod_mercaderia: $(this).find("td").eq(0).html(),
            cantidad: $(this).find("td").eq(2).html(),
            precio_unitario: $(this).find("td").eq(3).html(),
            exento: $(this).find("td").eq(4).html(),
            iva5: $(this).find("td").eq(5).html(),
            iva10: $(this).find("td").eq(6).html()
        });
    });

    valores = {
        bandera: bandera,
        cod_factura: (document.getElementById('factura_compra_nro').value === '' ? 0 : document.getElementById('factura_compra_nro').value),
        fecha_creacion: document.getElementById('factura_compra_fecha').value,
        nro_factura: document.getElementById('factura_compra').value,
        fecha_compra: document.getElementById('factura_fecha').value,
        nro_orden: document.getElementById('orden_compra').value,
        nro_proveedor: document.getElementById('codigo_proveedor').value,
        fac_timbrado: document.getElementById('nro_timbrado').value,
        fac_vtotimbrado: document.getElementById('fecha_venc_timbrado').value,
        condicion_compra: document.getElementById('condicion').value,
        cod_empleado: document.getElementById('codigo_empleado').value,
        cod_deposito: document.getElementById('codigo_deposito').value,

        lista_mercaderia: listaMercaderiaFacturaCompra
    };
    envioCabDetFacturaCompra();
}

function mostrar_Orden_Compra() {
    var xhr = new XMLHttpRequest(),
            method = "POST",
            url = "/JavaWeb_Compras/Factura_CompraCTR";
    xhr.open(method, url, true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            var json = JSON.parse(xhr.responseText);
            var i;
            var tindex;
            var filas = "";

            tindex++;

            for (i in json) {
                //aqui cargamos los datos a la tabla
                filas += "<tr>";
                filas += "<td>" + json[i].nro_orden + "</td>";
                filas += "<td>" + json[i].fecha + "</td>";
                filas += "<td>" + json[i].proveedor_descripcion + "</td>";
                filas += "<td>" + json[i].condicion_compra + "</td>";
                filas += "<td>" + json[i].total + "</td>";
                filas += "<td> <img onclick=\"RecuperarDeBuscadorOrdenCompra(\n\
                    " + json[i].nro_orden + " , " + json[i].nro_proveedor + ", \n\
                    '" + json[i].proveedor_descripcion + "' , '" + json[i].condicion_compra + "',\n\
                    'orden_compra', 'codigo_proveedor', 'proveedor', 'condicion'); \n\
                    \" src=\"../Recursos/Img/select.png\" alt=\"Sel\"/></td>";
                filas += "</tr>";
            }
            document.getElementById("Tabla_Orden_Compra").innerHTML = filas;
            document.getElementById('datos_Abuscar_Orden_Compra').style.display = 'block';
            document.getElementById("filtro_buscador_Orden_Compra").focus();
            document.getElementById("imprimir").disabled = false;
        }
    };
    xhr.send(JSON.stringify(datos = {bandera: 4}));
}

function RecuperarDeBuscadorOrdenCompra(nro_orden, nro_proveedor, proveedor_descripcion, condicion_compra,
        orden_compra, codigo_proveedor, proveedor, condicion) {

    document.getElementById(orden_compra).value = nro_orden;
    document.getElementById(codigo_proveedor).value = nro_proveedor;
    document.getElementById(proveedor).value = proveedor_descripcion;
    document.getElementById(condicion).value = condicion_compra;
    RecuperarOrdenCompra();

    document.getElementById('datos_Abuscar_Orden_Compra').style.display = 'none';
}

function RecuperarOrdenCompra() {
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
                var x;
                var d;
                var tindex;

                for (x in json) {
                    document.getElementById('orden_compra').value = json[x].nro_orden;
                    document.getElementById('codigo_proveedor').value = json[x].nro_proveedor;
                    document.getElementById('proveedor').value = json[x].proveedor_descripcion;
                    document.getElementById('condicion').value = json[x].condicion_compra;

                    $('#detalleTablaMercaderia_Factura_Compra').append("<tr id=\'prod" + tindex + "\'>\n\
                                <td for='cod' id='cod' style=' width: 6%;'>" + json[x].cod_mercaderia + "</td>\n\
                                <td id='med' style=' width: 20%;'>" + json[x].articulo + "</td>\n\
                                <td id='cant_td' style=' width: 8%;'>" + json[x].cantidad + "</td>\n\
                                <td id='pre_td' style=' width: 13%;'>" + json[x].precio_unitario + "</td>\n\
                                <td id='ex_td' style=' width: 10%;'>" + json[x].exento + "</td>\n\
                                <td id='5_td' style=' width: 10%;'>" + json[x].iva5 + "</td>\n\
                                <td id='10_td' style=' width: 10%;'>" + json[x].iva10 + "</td>\n\
                                <td id='pre_td' style=' width: 13%;'>" + json[x].subtotal + "</td>\n\
                                <td style=' width: 3%;'><img class='update' onclick=\"$(\'#prod" + tindex + "\');calcularTotalCompra()\" src='../Recursos/Img/update.png'/></td>\n\
                                <td style=' width: 3%;'><img class='delete' onclick=\"$(\'#prod" + tindex + "\');calcularTotalCompra()\" src='../Recursos/Img/delete.png'/></td>\n\
                                </tr>");
                }
            }
            calcularTotalCompra();
        }
    }
    ;
    xhr.open('POST', '/JavaWeb_Compras/Orden_Compra');
    xhr.send(JSON.stringify(datos = {bandera: 11, nro_orden: $('#orden_compra').val()}));
    $('#orden_compra').disabled;
}

function mostrarEmpleadoFacturaCompra() {
    var xhr = new XMLHttpRequest(), //
            method = "POST",
            url = "/JavaWeb_Compras/Factura_CompraCTR";
    xhr.open(method, url, true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
//            alert(xhr.responseText);
            var json = JSON.parse(xhr.responseText); //reponseText returns the entire JSON file and we assign it to a javascript variable "json".
            var i;
            var filas = "";
            for (i = 0; i < json.length; i++) {
                //aqui cargamos los datos a la tabla
                filas += "<tr>";
                filas += "<td>" + json[i].cod_empleado + "</td>";
                filas += "<td>" + json[i].emp_ci + "</td>";
                filas += "<td>" + json[i].nombre + " " + json[i].apellido + "</td>";
                filas += "<td> <img onclick=\"recuperarEmpleado_Compra(" + json[i].cod_empleado + " ,\n\
'" + json[i].nombre + ' ' + json[i].apellido + "', \n\
'codigo_empleado' , \n\
'empleado') \n\
\" src=\"../Recursos/Img/select.png\" alt=\"Sel\"/></td>";
                filas += "</tr>";
            }
            document.getElementById("Tabla_Empleado").innerHTML = filas;
            document.getElementById('datos_Abuscar_Empleado').style.display = 'block';
            document.getElementById("filtro_buscador_Empleado").focus();
        }
    };
    xhr.send(JSON.stringify(datos = {bandera: 7}));
}

function recuperarEmpleado_Compra(cod_empleado, empleado_descripcion,
        codigo_empleado, empleado) {
    document.getElementById(codigo_empleado).value = cod_empleado;
    document.getElementById(empleado).value = empleado_descripcion;
    document.getElementById('empleado').focus();
    document.getElementById('datos_Abuscar_Empleado').style.display = 'none';
}

function mostrarDepositoFacturaCompra() {
    var xhr = new XMLHttpRequest(), //
            method = "POST",
            url = "/JavaWeb_Compras/Factura_CompraCTR";
    xhr.open(method, url, true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            var json = JSON.parse(xhr.responseText); //reponseText returns the entire JSON file and we assign it to a javascript variable "json".
            var i;
            var filas = "";
            for (i = 0; i < json.length; i++) {
                //aqui cargamos los datos a la tabla
                filas += "<tr>";
                filas += "<td>" + json[i].cod_deposito + "</td>";
                filas += "<td>" + json[i].descripcion + "</td>";
                filas += "<td>" + json[i].sucursal_descripcion + "</td>";
                filas += "<td> <img onclick=\"recuperarDepositoFacturaCompra(" + json[i].cod_deposito + " ,\n\
'" + json[i].descripcion + "', '" + json[i].sucursal_descripcion + "', \n\
'codigo_deposito' , 'deposito', 'sucursal') \n\
\" src=\"../Recursos/Img/select.png\" alt=\"Sel\"/></td>";
                filas += "</tr>";
            }
            document.getElementById("Tabla_DepositoFacturaCompra").innerHTML = filas;
            document.getElementById('datos_Abuscar_DepositoFacturaCompra').style.display = 'block';
            document.getElementById("filtro_buscador_DepositoFacturaCompra").focus();
        }
    };
    xhr.send(JSON.stringify(datos = {bandera: 5}));
}

function recuperarDepositoFacturaCompra(cod_deposito, descripcion_deposito, descripcion_sucursal,
        codigo_deposito, deposito, sucursal) {
    document.getElementById(codigo_deposito).value = cod_deposito;
    document.getElementById(deposito).value = descripcion_deposito;
    document.getElementById(sucursal).value = descripcion_sucursal;
    document.getElementById('deposito').focus();
    document.getElementById('datos_Abuscar_DepositoFacturaCompra').style.display = 'none';
}

function mostrarMercaderiaFacturaCompra() {
    var xhr = new XMLHttpRequest(), //
            method = "POST",
            url = "/JavaWeb_Compras/Factura_CompraCTR";
    xhr.open(method, url, true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
//            alert(xhr.responseText);
            var json = JSON.parse(xhr.responseText); //reponseText returns the entire JSON file and we assign it to a javascript variable "json".
            var i;
            var filas = "";

            for (i = 0; i < json.length; i++) {
                //aqui cargamos los datos a la tabla
                filas += "<tr>";
                filas += "<td>" + json[i].cod_mercaderia + "</td>";
                filas += "<td>" + json[i].descripcion_medida + "</td>";
                filas += "<td>" + json[i].articulo + "</td>";
                filas += "<td>" + json[i].descripcion_marca + "</td>";
                filas += "<td>" + json[i].precio_unitario + "</td>";
                filas += "<td> <img onclick=\"recuperarMercaderiaFacturaCompra(" + json[i].cod_mercaderia + ",\n\
'" + json[i].articulo + "', '" + json[i].precio_unitario + "',\n\
'codigo_mercaderia' , 'mercaderia','precio_unitario_mercaderia') \n\
\" src=\"../Recursos/Img/select.png\" alt=\"Sel\"/></td>";
                filas += "</tr>";
            }
            document.getElementById("Tabla_MercaderiaFacturaCompra").innerHTML = filas;
            document.getElementById('datos_Abuscar_MercaderiaFacturaCompra').style.display = 'block';
            document.getElementById("filtro_buscador_MercaderiaFacturaCompra").focus();
        }
    };
    xhr.send(JSON.stringify(datos = {bandera: 6}));
}

function recuperarMercaderiaFacturaCompra(cod_mercaderia, articulo, precio_unitario,
        codigo_mercaderia, mercaderia, precio_unitario_mercaderia) {
    document.getElementById(codigo_mercaderia).value = cod_mercaderia;
    document.getElementById(mercaderia).value = articulo;
    document.getElementById(precio_unitario_mercaderia).value = precio_unitario;
    document.getElementById('cantidad_mercaderia').focus();
    document.getElementById('datos_Abuscar_MercaderiaFacturaCompra').style.display = 'none';
}

function buscador(filtro, tabla) {
    var input, filter, table, tr, td, i;
    input = document.getElementById(filtro);
    filter = input.value.toUpperCase();
    table = document.getElementById(tabla);
    tr = table.getElementsByTagName("tr");
    for (i = 0; i < tr.length; i++) {
        visible = false;
        td = tr[i].getElementsByTagName("td");
        for (j = 0; j < td.length; j++) {
            if (td[j] && td[j].innerHTML.toUpperCase().indexOf(filter) > -1) {
                visible = true;
            }
        }
        if (visible === true) {
            tr[i].style.display = "";
        } else {
            tr[i].style.display = "none";
        }
    }
}

function habilitaInputFactura_Compra(factura_compra, factura_fecha, orden_compra, codigo_proveedor, nro_timbrado,
        fecha_venc_timbrado, condicion, codigo_empleado, codigo_deposito, codigo_mercaderia, cantidad_mercaderia)
{
    var fact_compra = document.getElementById(factura_compra);
    var fact_fecha = document.getElementById(factura_fecha);
    var nro_orden = document.getElementById(orden_compra);
    var proveedor = document.getElementById(codigo_proveedor);
    var timbrado = document.getElementById(nro_timbrado);
    var timbrado_fecha = document.getElementById(fecha_venc_timbrado);
    var condicion_compra = document.getElementById(condicion);
    var empleado = document.getElementById(codigo_empleado);
    var deposito = document.getElementById(codigo_deposito);
    var mercaderia = document.getElementById(codigo_mercaderia);
    var cantidad = document.getElementById(cantidad_mercaderia);

    fact_compra.disabled = !fact_compra.disabled;
    fact_fecha.disabled = !fact_fecha.disabled;
    nro_orden.disabled = !nro_orden.disabled;
    proveedor.disabled = !proveedor.disabled;
    timbrado.disabled = !timbrado.disabled;
    timbrado_fecha.disabled = !timbrado_fecha.disabled;
    condicion_compra.disabled = !condicion_compra.disabled;
    empleado.disabled = !empleado.disabled;
    deposito.disabled = !deposito.disabled;
    mercaderia.disabled = !mercaderia.disabled;
    cantidad.disabled = !cantidad.disabled;

    document.getElementById(factura_compra).focus();
}

function calcularTotalCompra() {
    var total = 0;
    $('#detalleTablaMercaderia_Factura_Compra tr').each(function () {

        var item = $(this).find("td").eq(7).html();
        item = item.replace(".", "");
        var numero = Number(item);
        total = total + numero;

    });
    document.getElementById('precio_total_compra').value = format(total);
}

function format(num)
{
    var nuevonro = 0;
//    var num = num.replace(".", '');
    if (!isNaN(num)) {
        num = num.toString().split('').reverse().join('').replace(/(?=\d*\.?)(\d{3})/g, '$1.');
        num = num.split('').reverse().join('').replace(/^[\.]/, '');
        nuevonro = num;
//        input.value = num;
    } else {
        alert('Solo se permiten numeros');
//        input.value = input.value.replace(/[^\d\.]*/g, '');
    }
    return nuevonro;
}

$(document).on('click', '.delete', function (event) {
    if (confirm('Confirmar la eliminación de la Fila')) {
        event.preventDefault();
        $(this).closest('tr').remove();
        calcularTotalCompra();
    } else {
        limpiarInputMercaderia_Factura_Compra();
    }
});

$(document).ready(function () {
    $("#detalleTablaMercaderia_Factura_Compra").on('click', '.update', function (e) {
        if (confirm('Desea Modificar la Fila')) {
            e.preventDefault();
            var currentRow = $(this).closest("tr");
            var col1 = currentRow.find("td:eq(0)").text();
            var col2 = currentRow.find("td:eq(1)").text();
            var col3 = currentRow.find("td:eq(2)").text();
            var col4 = currentRow.find("td:eq(3)").text();
//            var col5 = currentRow.find("td:eq(4)").text();
//            var col6 = currentRow.find("td:eq(5)").text();
//            var col7 = currentRow.find("td:eq(6)").text();
//            var col8 = currentRow.find("td:eq(7)").text();

            $("#codigo_mercaderia").val(col1);
            $("#mercaderia").val(col2);
            $("#cantidad_mercaderia").val(col3);
            $("#precio_unitario_mercaderia").val(col4);
            $(this).closest('tr').remove();
            calcularTotalCompra();
        } else {
            return false;
        }
    });
});