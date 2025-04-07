package org.deha.be.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.deha.be.generic.BaseEntity;

import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;

@Entity
@Getter
@Setter
public class EmailVerification extends BaseEntity {
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(unique = true, length = 6)
    private int code;
    private Instant expireAt;

    @PrePersist
    public void prePersist() {
        this.code = ThreadLocalRandom.current().nextInt(100000, 1000000);
        this.expireAt = Instant.now().plusSeconds(10L * 60L);
    }
}
