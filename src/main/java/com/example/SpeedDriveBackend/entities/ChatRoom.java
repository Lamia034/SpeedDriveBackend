package com.example.SpeedDriveBackend.entities;

import com.example.SpeedDriveBackend.embeddable.ChatRoomId;
import com.example.SpeedDriveBackend.enumerations.Sender;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
public class ChatRoom {

//    @EmbeddedId
//    private ChatRoomId chatRoomId;
    @Id
private UUID chatRoomId;
    private UUID sender;


    private UUID receiver;

    @ManyToOne
    @JoinColumn(name = "agencyId")
    private Agency agency;

    @ManyToOne
    @JoinColumn(name = "clientId")
    private Client client;

//@JsonIgnore
    @OneToMany(mappedBy = "chatRoom", fetch = FetchType.EAGER , cascade = CascadeType.ALL)
    private List<Message> messages;
}
