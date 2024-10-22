package com.example.TrasportiBackend.Socket;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.AckRequest;
import com.example.TrasportiBackend.messaggi.Messaggi;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import okhttp3.internal.ws.RealWebSocket;

@Component
@Log4j2
public class SocketIOController {

    @Autowired
    private SocketIOServer socketServer;

    SocketIOController(SocketIOServer socketServer){
        this.socketServer=socketServer;

        this.socketServer.addConnectListener(onUserConnectWithSocket);
        this.socketServer.addDisconnectListener(onUserDisconnectWithSocket);

        /**
         * Here we create only one event listener
         * but we can create any number of listener
         * messageSendToUser is socket end point after socket connection user have to send message payload on messageSendToUser event
         */
        this.socketServer.addEventListener("messageSendToUser", Messaggi.class, onSendMessage);

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

    public DataListener<Messaggi> onSendMessage = new DataListener<Messaggi>() {
        @Override
        public void onData(SocketIOClient client, Messaggi message, AckRequest acknowledge) throws Exception {

            /**
             * Sending message to target user
             * target user should subscribe the socket event with his/her name.
             * Send the same payload to user
             */
            String sender = message.getAziendaAsSender()==null? message.getTrasportatoreAsSender().getNome(): message.getAziendaAsSender().getNomeAzienda();
            String receiver = message.getAziendaAsReceiver()==null? message.getTrasportatoreAsReceiver().getNome(): message.getAziendaAsReceiver().getNomeAzienda();

            log.info(sender +" send message to "+receiver+" and message is "+message.getTesto());
            socketServer.getBroadcastOperations().sendEvent(receiver,client, message);


            /**
             * After sending message to target user we can send acknowledge to sender
             */
            acknowledge.sendAckData("Message send to target user successfully");
        }
    };

}
