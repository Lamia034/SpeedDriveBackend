package com.example.SpeedDriveBackend.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ChatRoomId implements Serializable {

    @Column(name = "clientId")
    private UUID clientId;
    @Column(name = "agencyId")
    private UUID agencyId;
}
