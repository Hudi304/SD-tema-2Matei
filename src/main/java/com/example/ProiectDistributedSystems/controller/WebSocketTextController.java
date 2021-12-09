package com.example.ProiectDistributedSystems.controller;

import com.example.ProiectDistributedSystems.DTO.Socket.MessageDTO;
import com.example.ProiectDistributedSystems.DTO.Socket.ResponseMesage;
import com.example.ProiectDistributedSystems.DTO.TextMessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;


//@Controller
////@CrossOrigin(origins = "*", allowedHeaders = "*")
//public class WebSocketTextController {
//
//    @MessageMapping("/message")
//    @SendTo("/topic/messages")
//    public ResponseMesage getMessage(final MessageDTO message){
//        return new ResponseMesage(HtmlUtils.htmlEscape(message.getMessageContent()));
//    }
//
//
//}


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class WebSocketTextController {

    @Autowired
    private SimpMessagingTemplate template;

    @PostMapping("/send")
    public ResponseEntity<Void> sendMessage(@RequestBody TextMessageDTO textMessageDTO) {
        template.convertAndSend("/topic/message", textMessageDTO);
        System.out.println("sendMessage");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @MessageMapping("/sendMessage")
    public void receiveMessage(@Payload TextMessageDTO textMessageDTO) {
        System.out.println("receiveMessage");

        // receive message from client
    }


    @SendTo("/topic/message")
    public TextMessageDTO broadcastMessage(@Payload TextMessageDTO textMessageDTO) {
        System.out.println("broadcastMessage");

        return textMessageDTO;
    }
}