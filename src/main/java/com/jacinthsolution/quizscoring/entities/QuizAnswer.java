package com.jacinthsolution.quizscoring.entities;

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

    private String answer;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private QuizQuestion quizQuestion;

}
