package com.jacinthsolution.quizscoring.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;


import java.util.List;

@Entity
@Data
@Table(name = "quiz questions")
public class QuizQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native")
    @Column(name = "quiz_id")
    private long id;

    private String question;

    @Enumerated(EnumType.STRING)
    private QuestionType questionType;

    @OneToMany( cascade = CascadeType.ALL)
    private List<QuizAnswer> answers;

    @Enumerated(EnumType.STRING)
    private DifficultyLevel difficultyLevel;

}
