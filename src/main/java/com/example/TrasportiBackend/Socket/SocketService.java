package com.example.TrasportiBackend.Socket;

import com.corundumstudio.socketio.SocketIOClient;
import com.example.TrasportiBackend.enums.StatoMessaggio;
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

    public void saveMessage(SocketIOClient senderClient, Messaggi message) {

        Messaggi storedMessage = messageService.save(
        new MessaggioDTO(message.getChat().getId(),
                message.getAziendaAsSender()==null?
                message.getTrasportatoreAsSender().getId():
                message.getAziendaAsSender().getId(),
                message.getAziendaAsReceiver()==null?
                        message.getTrasportatoreAsReceiver().getId():
                        message.getAziendaAsReceiver().getId(),
                message.getAziendaAsReceiver()==null?
                        "Trasportatore":
                        "Azienda",
                message.getAziendaAsSender()==null?
                        "Trasportatore":
                        "Azienda",
                message.getTesto(),
                message.getRoom()
                )
        );

        sendSocketmessage(senderClient, storedMessage, message.getRoom());

    }

    public void saveInfoMessage(SocketIOClient senderClient, Messaggi message, String room) {
        Messaggi storedMessage = messageService.save(
                new MessaggioDTO(message.getChat().getId(),
                        message.getAziendaAsSender()==null?
                                message.getTrasportatoreAsSender().getId():
                                message.getAziendaAsSender().getId(),
                        message.getAziendaAsReceiver()==null?
                                message.getTrasportatoreAsReceiver().getId():
                                message.getAziendaAsReceiver().getId(),
                        message.getAziendaAsReceiver()==null?
                                "Trasportatore":
                                "Azienda",
                        message.getAziendaAsSender()==null?
                                "Trasportatore":
                                "Azienda",
                        message.getTesto(),
                        message.getRoom()
                )
        );

        sendSocketmessage(senderClient, storedMessage, room);
    }
}