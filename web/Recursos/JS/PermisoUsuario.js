var datos;
function validarUsuario() {
    var xhr = new XMLHttpRequest(), //
            method = "POST",
            url = "/AutoserviceWalker/permisoUsuarioCTRL";
    xhr.open(method, url, true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            //alert(xhr.responseText);
//            alert('ssss');
            if (xhr.responseText === "") {
                alert('¡¡No existe el Usuario, Ó Erro en la Contraseña!!');
                window.location = 'AccesoSistema.html';
            }
            document.getElementById("bar_menu_principall").innerHTML = xhr.responseText;
        }
        
    };
    xhr.send(JSON.stringify(datos = {bandera: 1, nombre: document.getElementById('datos_usuario').value,
        clave: document.getElementById('datos_clave').value}));
    document.getElementById('panelAcceso').style.display = "none";
    document.getElementById('BotonAcceso').style.display = "none";



}
