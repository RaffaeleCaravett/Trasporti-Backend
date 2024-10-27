package com.example.TrasportiBackend.Socket;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.example.TrasportiBackend.messaggi.MessaggiService;
import com.example.TrasportiBackend.payloads.entities.MessaggioDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
/*
@Slf4j

@Component
*/
public class SocketModule {
/*

    private final SocketIOServer server;

    private final SocketService socketService;
    @Autowired
    private MessaggiService messaggiService;

    public SocketModule(SocketIOServer server, SocketService socketService) {
        this.server = server;
        this.socketService = socketService;
        server.addConnectListener(this.onConnected());
        server.addDisconnectListener(this.onDisconnected());
        server.addEventListener("send_message", MessaggioDTO.class, this.onChatReceived());
    }

    private DataListener<MessaggioDTO> onChatReceived() {
        return (senderClient,data, ackSender)-> {
            socketService.saveMessage(senderClient, data);
        }
        ;
    }

    private ConnectListener onConnected() {
        return (client) -> {
            var params = client.getHandshakeData().getUrlParams();
            List<String> room=params.get("room");
            List<String> username=params.get("username");
            if(username!=null) {
                username.stream().collect(Collectors.joining());
            }
            if(room!=null) {
                room.stream().collect(Collectors.joining());
                client.joinRoom(room.toString());
            }
        }
        ;

    }

    private DisconnectListener onDisconnected() {
        return client -> {
            var params = client.getHandshakeData().getUrlParams();
            List<String> room=params.get("room");
            List<String> username=params.get("username");
            if(room!=null) {
                room.stream().collect(Collectors.joining());
            }
            if(username!=null) {
                username.stream().collect(Collectors.joining());
            }
            }
        ;
    }
 */
}
