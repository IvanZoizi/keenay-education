package keenay.education.entity;

import jakarta.persistence.*;
import keenay.education.entity.status.TasksStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "tasks")
@NoArgsConstructor
public class Tasks {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id", name = "advertisement_id")
    private Advertisement advertisement;

    @Column(name = "photo", nullable = true)
    private String photoUrl;

    @Column(name = "status")
    private TasksStatus status;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;


    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
