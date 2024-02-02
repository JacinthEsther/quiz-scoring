# Quiz Scoring Application

This is a quiz scoring application designed to receive quiz submissions, store quiz questions and answers, evaluate user-submitted answers, 
calculate and store users' scores for each quiz attempt, and provide a way to retrieve and display quiz scores to users.

## Table of Contents
- [Features](#features)
- [Prerequisites](#prerequisites)
- [Usage](#usage)
  - [Quiz Submission](#quiz-submission)
  - [Retrieve Quiz Scores](#retrieve-quiz-scores)
- [Technologies Used](#technologies-used)


## Features

- Receive quiz submissions from the front-end.
- Store quiz questions and answers in a database.
- Evaluate user-submitted answers against correct answers.
- Calculate and store users' scores for each quiz attempt.
- Provide a way to retrieve and display quiz scores to users.

## Prerequisites

- Java (version 17)
- Spring Boot (version 3.0)
- Database (PostgreSQL)

## Usage
- Quiz Submission
    Submit a quiz using the /submit endpoint. For example:

    curl -X POST -H "Content-Type: application/json" -d '{"userId": 1, "quizId": 1, "answers": ["optionA", "optionB", "optionC"]}' http://localhost:8080/submit

- Retrieve Quiz Scores
    Retrieve quiz scores for a specific user using the /scores/{userId} endpoint. For example:

    curl http://localhost:8080/scores/1

## Technologies Used
Java
Spring Boot
Database (PostgreSQL)
