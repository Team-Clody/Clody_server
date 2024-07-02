package com.donkeys_today.server.domain.alarm;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "alarm")
public class Alarm {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "time")
    private LocalDateTime time;

    @Builder
    public Alarm(LocalDateTime time) {
        this.time = time;
    }
}
