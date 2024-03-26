package com.example.SpeedDriveBackend.controllers;

import com.example.SpeedDriveBackend.dtos.request.MessageRequest;
import com.example.SpeedDriveBackend.dtos.response.ChatRoomResponse;
import com.example.SpeedDriveBackend.dtos.response.MessageResponse;
import com.example.SpeedDriveBackend.dtos.response.RentResponse;
import com.example.SpeedDriveBackend.entities.ChatRoom;
import com.example.SpeedDriveBackend.entities.Message;
import com.example.SpeedDriveBackend.services.MessageService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/messages")
    @PreAuthorize("hasAnyAuthority('AGENCY','CLIENT')")
    public ResponseEntity<MessageResponse> sendMessage(@RequestBody MessageRequest messageRequest) {

        MessageResponse sentMessage = messageService.sendMessage(messageRequest);

        return ResponseEntity.ok(sentMessage);
    }
@GetMapping("/chatrooms/{id}")
@PreAuthorize("hasAnyAuthority('AGENCY','CLIENT')")
    public List<ChatRoomResponse> getChatRoomsByAgency(@PathVariable UUID id) {
        return messageService.getChatRoomsById(id);
    }


    @GetMapping("/messages/{chatRoomId}")
    @PreAuthorize("hasAnyAuthority('AGENCY','CLIENT')")
    public ResponseEntity<List<MessageResponse>> getMessagesByChatRoom(@PathVariable UUID chatRoomId) {
        List<MessageResponse> messages = messageService.getMessagesByChatRoom(chatRoomId);
        return ResponseEntity.ok(messages);
    }


}
