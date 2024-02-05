package com.jacinthsolution.quizscoring.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Entity
@Data
@Table(name = "quiz questions")
@ToString
public class QuizQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native")
    @Column(name = "quiz_id")
    private long id;

    private String question;

    @Enumerated(EnumType.STRING)
    private QuestionType questionType;

    @ElementCollection
    private List<String> possibleAnswers;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "correct_answer_id")
    private QuizAnswer correctAnswer;

    @Enumerated(EnumType.STRING)
    private DifficultyLevel difficultyLevel;

    @ManyToOne
    @JoinColumn(name = "user_quiz_score_id")
    private UserQuizScore userQuizScore;


}
