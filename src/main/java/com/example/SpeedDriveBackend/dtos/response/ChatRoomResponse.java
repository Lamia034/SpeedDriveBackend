package com.example.SpeedDriveBackend.dtos.response;

import com.example.SpeedDriveBackend.embeddable.ChatRoomId;
import com.example.SpeedDriveBackend.entities.Message;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class ChatRoomResponse {
//    private ChatRoomId chatroomId;
private UUID chatRoomId;
    private UUID sender;
private String senderName;
private String receiverName;

    private UUID receiver;
    private List<MessageResponse> messages;
}
