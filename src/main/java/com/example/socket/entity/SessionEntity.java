package com.example.socket.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "session")
public class SessionEntity {
    @Id
    String id;
    @Column
    Long creationTime;
    @Column
    private long lastAccessedTime;
    @Column
    private int maxInactiveInterval;
    @Column
    private String sessionStore;
    @Column
    private boolean isNew;

    public SessionEntity(
            String id,
            Long creationTime,
            long lastAccessedTime,
            int maxInactiveInterval,
            String sessionStore,
            boolean isNew
    ) {
        this.id = id;
        this.creationTime = creationTime;
        this.lastAccessedTime = lastAccessedTime;
        this.maxInactiveInterval = maxInactiveInterval;
        this.sessionStore = sessionStore;
        this.isNew = isNew;
    }
}
