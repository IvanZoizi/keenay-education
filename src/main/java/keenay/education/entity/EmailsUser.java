package keenay.education.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "emails_user")
@Data
@NoArgsConstructor
public class EmailsUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id", name = "user_id")
    private Users user;

    @Column(name = "text")
    private String text;

    @Column(name = "subject")
    private String subject;

    @Column(name = "attempt")
    private Integer attempt;

    @Column(name = "is_sended")
    private Boolean isSended = false;

    @Column(name = "exception")
    private String exceptionMessage;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
