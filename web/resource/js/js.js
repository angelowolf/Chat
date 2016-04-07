var wsocket;
var serviceLocation = "wss://localhost:8443/Chat/chat";
var $nick;
var $clave;
var $lista_usuario;
var $mensaje;
var $destino;
var $response;
var token;

function mensajeRecibido(evt) {
    var mensaje = JSON.parse(evt.data);
    var tipo = mensaje.tipo + "";
    switch (tipo) {
        case 'LOGIN':
            {
                alert(mensaje.mensaje);
            }
            break;
        case 'OK':
            {
                $('#ventana-login').hide();
                $('#ventana_chat').show();
                $('#chat-defalut').hide();
                token = mensaje.token;
            }
            break;
        case 'MENSAJE':
            {
                //recibi un mensaje....
                //var msg = eval('(' + evt.data + ')');
                var msg = JSON.parse(evt.data); // native API
                cargarVentanaChatParticular(msg.envia, false);
                $elchat = $('#chat-' + msg.envia);
                $lista_usuario.find("#" + msg.envia + ' > .badge').text("nuevo");
                var $messageLine = $('<br> <div class="message badge pull-left">' + msg.mensaje + '</div>');
                $elchat.find("div#response").append($messageLine);
            }
            break;
        case 'CONTACTOS':
            {
                //actualizacion de la lista de contactos...
                var usuarios = mensaje.usuarios;
                $lista_usuario.empty();
                for (i = 0; i < usuarios.length; i++) {
                    var $li = $('<li id="' + usuarios[i] + '" onclick="cargarVentanaChatParticular(this.id,true)">' + usuarios[i] + '<span class="badge"></badge></li>');
                    $lista_usuario.append($li);
                }
            }
            break;
        case 'ERROR':
            alert(mensaje.mensaje);
            break;

    }
}
function enviarMensaje() {
    var $messageLine = $(' <br> <div class="message badge pull-right">' + $mensaje.val()
            + '</div>');
    $response.append($messageLine);
    var msg = '{"mensaje":"' + $mensaje.val() + '", "envia":"'
            + $nick.val() + '", "recibe":"' + $destino.val() + '","tipo":"MENSAJE","token":"' + token + '"}';
    wsocket.send(msg);
    $mensaje.val('').focus();
}


function conectarWebSocket() {
    wsocket = new WebSocket(serviceLocation);
    wsocket.onmessage = mensajeRecibido;
    wsocket.onopen = function (e) {
        var msg = '{"nick":"' + $nick.val() + '","clave":"' + $clave.val() + '","tipo":"LOGIN"}';
        wsocket.send(msg);
    };
}

function cargarVentanaChatParticular(id, mostrar) {
    $elchat = $('#chat-' + id);
    if ($elchat.length) {
        if (mostrar) {
            $lista_usuario.find("#" + id + ' > .badge').text("");
            esconderChats();
            $destino = $elchat.find("input#destino");
            $mensaje = $elchat.find("input#mensaje");
            $response = $elchat.find("div#response");
            $elchat.show();
        }
    } else {
        $elchat = $('#chat-defalut').clone();
        $elchat.attr("id", 'chat-' + id);
        $('#ventana_chat').append($elchat);
        $elchat.find("label#nombre").text(id);
        $elchat.find("input#destino").val(id);
        if (mostrar) {
            $mensaje = $elchat.find("input#mensaje");
            $destino = $elchat.find("input#destino");
            $response = $elchat.find("div#response");
            esconderChats();
            $elchat.show();
        }
        $btn = $elchat.find("input#chat-boton");
        $btn.click(function (e) {
            e.preventDefault();
            enviarMensaje();
        });
    }
}
function esconderChats() {
    $('#ventana_chat form').each(function (index, item) {
        $(item).hide();
    });
}

$(document).ready(function () {
    $('#ventana_chat').hide();

    $nick = $('#nick');
    $clave = $('#clave');
    $lista_usuario = $('#lista-usuarios');
    $mensaje = $('#mensaje');
    $nick.focus();
    $('#iniciarsesion').click(function (evt) {
        evt.preventDefault();
        conectarWebSocket();
    });
});