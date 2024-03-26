package com.example.SpeedDriveBackend.repositories;

import com.example.SpeedDriveBackend.entities.ChatRoom;
import com.example.SpeedDriveBackend.entities.Message;
import com.example.SpeedDriveBackend.enumerations.Sender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ChatRoomRepository extends JpaRepository<ChatRoom , UUID> {
//    ChatRoom findBySenderAndReceiver(Sender sender, Sender receiver);
@Query("SELECT c FROM ChatRoom c WHERE c.chatRoomId = :chatRoomId")
ChatRoom findChatRoomById(UUID chatRoomId);

    ChatRoom findBySenderAndReceiver(UUID sender, UUID receiver);

    List<ChatRoom> findBySender(UUID id);

    List<ChatRoom> findByReceiver(UUID id);
}
