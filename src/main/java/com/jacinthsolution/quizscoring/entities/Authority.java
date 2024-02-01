package com.jacinthsolution.quizscoring.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "authorities")
@Data
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native")
    private long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
