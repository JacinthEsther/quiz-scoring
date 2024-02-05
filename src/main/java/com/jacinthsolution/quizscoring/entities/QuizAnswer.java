package com.jacinthsolution.quizscoring.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
@Table(name = "quiz answers")
public class QuizAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native")
    private long id;

    private String correctAnswer;

    @OneToOne(mappedBy = "correctAnswer")
    @JsonIgnore
    private QuizQuestion quizQuestion;

}
