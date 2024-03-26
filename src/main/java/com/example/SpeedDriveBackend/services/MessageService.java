package com.example.SpeedDriveBackend.services;

import com.example.SpeedDriveBackend.dtos.request.MessageRequest;
import com.example.SpeedDriveBackend.dtos.response.ChatRoomResponse;
import com.example.SpeedDriveBackend.dtos.response.MessageResponse;
import com.example.SpeedDriveBackend.entities.ChatRoom;
import com.example.SpeedDriveBackend.entities.Message;
import com.example.SpeedDriveBackend.enumerations.Sender;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

public interface MessageService {
    MessageResponse sendMessage(MessageRequest messageRequest);

    List<ChatRoomResponse> getChatRoomsById(UUID id);




    List<MessageResponse> getMessagesByChatRoom(UUID chatRoomId);
//    ChatRoom findOrCreateChatRoom(Sender sender, Sender receiver);
//Message sendMessage( Message message);
}
