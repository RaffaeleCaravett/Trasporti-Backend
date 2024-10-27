package com.example.TrasportiBackend.Socket;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.example.TrasportiBackend.User.*;
import com.example.TrasportiBackend.payloads.entities.MessaggioDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class SocketIOController {

    @Autowired
    private SocketIOServer socketServer;
    @Autowired
            private UserService userService;
    SocketIOController(SocketIOServer socketServer){
        this.socketServer=socketServer;

        this.socketServer.addConnectListener(onUserConnectWithSocket);
        this.socketServer.addDisconnectListener(onUserDisconnectWithSocket);

        /**
         * Here we create only one event listener
         * but we can create any number of listener
         * messageSendToUser is socket end point after socket connection user have to send message payload on messageSendToUser event
         */
        this.socketServer.addEventListener("send_message", MessaggioDTO.class, onSendMessage);

    }


    public ConnectListener onUserConnectWithSocket = new ConnectListener() {
        @Override
        public void onConnect(SocketIOClient client) {
            log.info("Perform operation on user connect in controller");
        }
    };


    public DisconnectListener onUserDisconnectWithSocket = new DisconnectListener() {
        @Override
        public void onDisconnect(SocketIOClient client) {
            log.info("Perform operation on user disconnect in controller");
        }
    };

    public DataListener<MessaggioDTO> onSendMessage = new DataListener<MessaggioDTO>() {
        @Override
        public void onData(SocketIOClient client, MessaggioDTO message, AckRequest acknowledge) throws Exception {

            Trasportatore trasportatore= new Trasportatore();
            Azienda azienda = new Azienda();
            if(message.receiverType().equals("Trasportatore")){
                trasportatore = userService.getTrasportatoreById(message.receiver_id());
            }else{
                azienda = userService.getAziendaById(message.receiver_id());
            }

            socketServer.getBroadcastOperations().sendEvent(message.receiverType().equals("Trasportatore")?trasportatore.getEmail():azienda.getEmail(),client, message);
            
            acknowledge.sendAckData("Message send to target user successfully");
        }
    };

}