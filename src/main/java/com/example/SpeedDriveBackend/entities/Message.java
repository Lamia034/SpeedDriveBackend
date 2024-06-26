package com.example.SpeedDriveBackend.entities;

import com.example.SpeedDriveBackend.entities.abstracts.Person;
import com.example.SpeedDriveBackend.enumerations.Sender;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
public class Message {
    @Id
    private UUID messageId;
    private String text;

    private UUID sender;
    private UUID receiver;

    private LocalDateTime sent;

    @ManyToOne
    @JoinColumn(name = "chatRoomId")
    private ChatRoom chatRoom;
}
