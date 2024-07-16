package com.donkeys_today.server.domain.reply;

import static jakarta.persistence.GenerationType.IDENTITY;

import com.donkeys_today.server.domain.user.User;
import com.donkeys_today.server.support.auditing.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "replies")
public class Reply extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "content")
    private String content;

    @Column(name = "is_read")
    private Boolean is_read;

    @Column(name = "diary_created_date")
    private LocalDate diaryCreatedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action= OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id")
    private User user;

    public void readReply() {
        this.is_read = true;
    }
}
