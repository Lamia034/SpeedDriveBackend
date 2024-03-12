package com.example.SpeedDriveBackend.entities;

import com.example.SpeedDriveBackend.entities.abstracts.Person;
import com.example.SpeedDriveBackend.enumerations.Sender;
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

    @Enumerated(EnumType.STRING)
    private Sender sender;

    @Enumerated(EnumType.STRING)
    private Sender receiver;

    private LocalDateTime sent;

    @ManyToOne
    @JoinColumn(name = "chatroomId")
    private ChatRoom chatRoom;
}
