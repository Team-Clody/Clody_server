package com.clody.domain.reply;

import static jakarta.persistence.GenerationType.IDENTITY;

import com.clody.domain.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "replies", indexes = @Index(name = "idx_diary_created_date", columnList = "diary_created_date"))
public class Reply {
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

    public boolean isNotRead(){
        return !this.is_read;
    }

    public boolean isRead(){
        return this.is_read;
    }

    @Builder
    public Reply(String content, Boolean is_read, LocalDate diaryCreatedDate, User user) {
        this.content = content;
        this.is_read = is_read;
        this.diaryCreatedDate = diaryCreatedDate;
        this.user = user;
    }

    public static Reply createStaticReply(User user, String content, LocalDate createdDate) {
        return new Reply(content, false, createdDate, user);
    }
}
