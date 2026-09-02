package keenay.education.entity;

import jakarta.persistence.*;
import jdk.jfr.DataAmount;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "animals")
@NoArgsConstructor
public class Animals {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;
}
