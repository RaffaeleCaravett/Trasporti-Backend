package com.example.TrasportiBackend.Socket;

import com.corundumstudio.socketio.SocketIOClient;
import com.example.TrasportiBackend.messaggi.Messaggi;
import com.example.TrasportiBackend.messaggi.MessaggiService;
import com.example.TrasportiBackend.payloads.entities.MessaggioDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SocketService {

    private final MessaggiService messageService;

    public void sendSocketmessage(SocketIOClient senderClient, Messaggi message, String room) {
        for (
                SocketIOClient client: senderClient.getNamespace().getRoomOperations(room).getClients()
        ) {
            if (!client.getSessionId().equals(senderClient.getSessionId())) {
                client.sendEvent("read_message", message.getTesto());
            }
        }
    }

    public void saveMessage(SocketIOClient senderClient, MessaggioDTO message) {

        Messaggi storedMessage = messageService.save(
      message
        );

        sendSocketmessage(senderClient, storedMessage, message.room());

    }

    public void saveInfoMessage(SocketIOClient senderClient, MessaggioDTO message, String room) {
        Messaggi storedMessage = messageService.save(
               message
        );

        sendSocketmessage(senderClient, storedMessage, room);
    }
}