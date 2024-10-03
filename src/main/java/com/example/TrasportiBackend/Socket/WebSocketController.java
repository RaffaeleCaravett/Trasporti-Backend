package com.example.TrasportiBackend.Socket;

import com.example.TrasportiBackend.messaggi.Messaggi;
import com.example.TrasportiBackend.payloads.entities.MessaggioDTO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {


    @MessageMapping("/update")
    @SendTo("/topic/update")
    public Messaggi updateMessage(Messaggi messaggi) throws InterruptedException {
        Thread.sleep(1000);
        return messaggi;
    }
}
