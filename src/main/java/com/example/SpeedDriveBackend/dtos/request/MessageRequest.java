package com.example.SpeedDriveBackend.dtos.request;

import com.example.SpeedDriveBackend.entities.ChatRoom;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;
@Data
public class MessageRequest {
    private UUID messageId;
    @NotBlank
    private String text;

    private UUID sender;


    private UUID receiver;
//private UUID clientId;
//    private UUID agencyId;
@NotNull
    private LocalDateTime sent;

    private UUID chatRoomId;
}
