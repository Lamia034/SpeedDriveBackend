package com.example.SpeedDriveBackend.dtos.response;

import com.example.SpeedDriveBackend.entities.Agency;
import com.example.SpeedDriveBackend.entities.ChatRoom;
import com.example.SpeedDriveBackend.entities.Client;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;
@Data
public class MessageResponse {
    private UUID messageId;
    private String text;
    private UUID sender;
    private UUID receiver;
//    private Client client;
//    private Agency agency;
    private LocalDateTime sent;

    private UUID chatRoomId;
}
