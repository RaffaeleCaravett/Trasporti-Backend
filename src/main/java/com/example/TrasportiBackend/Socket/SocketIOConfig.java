package com.example.TrasportiBackend.Socket;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.Transport;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import jakarta.annotation.PreDestroy;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import com.corundumstudio.socketio.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@Component
@Log4j2
public class SocketIOConfig {

    @Value("${socket.host}")
    private String SOCKETHOST;
    @Value("${socket.port}")
    private int SOCKETPORT;
    private SocketIOServer server;


    @Bean
    public SocketIOServer socketIOServer() {
        Configuration config = new Configuration();
        config.setHostname(SOCKETHOST);
        config.setPort(SOCKETPORT);
        config.setAllowCustomRequests(true);
        config.setOrigin("https://trasporti.netlify.app");
        config.setAllowHeaders("*");
        config.setTransports(Transport.POLLING,Transport.WEBSOCKET);

        server = new SocketIOServer(config);
        CustomSocketIOChannelInitializer customSocketIOChannelInitializer = new CustomSocketIOChannelInitializer(config);
        server.setPipelineFactory(customSocketIOChannelInitializer);
        server.start();

        server.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient client) {
                log.info("connected");
            }
        });

        server.addDisconnectListener(new DisconnectListener() {
            @Override
            public void onDisconnect(SocketIOClient client) {
                client.getNamespace().getAllClients().stream().forEach(data-> {
                    log.info("user disconnected "+data.getSessionId().toString());});
            }
        });

        return server;
    }

    @PreDestroy
    public void stopSocketIOServer() {
        this.server.stop();
    }

}
