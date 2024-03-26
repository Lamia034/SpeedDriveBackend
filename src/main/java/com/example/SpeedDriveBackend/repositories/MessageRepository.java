package com.example.SpeedDriveBackend.repositories;

import com.example.SpeedDriveBackend.entities.ChatRoom;
import com.example.SpeedDriveBackend.entities.Message;
import com.example.SpeedDriveBackend.enumerations.Sender;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID> {
    List<Message> findByChatRoom_ChatRoomId(UUID chatRoomId);

//    List<Message> findByChatRoom_ChatRoomIdOrderBySentDesc(UUID chatRoomId);
}
