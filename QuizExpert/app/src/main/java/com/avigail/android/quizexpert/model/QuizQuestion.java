package com.avigail.android.quizexpert.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Avigail on 17/09/2018.
 */


// the model :
public class QuizQuestion {

    private String mCategory;

    private String mType;

    private String mDifficulty;

    private String mQuestion;

    private String mCorrectAnswer;

    private ArrayList<String> incorrectAnswers;


    // constractor :
    public QuizQuestion(String category, String type, String difficulty, String question, String correctAnswer, ArrayList<String> incorrectAnswers) {
        this.mCategory = category;
        this.mType = type;
        this.mDifficulty = difficulty;
        this.mQuestion = question;
        this.mCorrectAnswer = correctAnswer;
        this.incorrectAnswers = incorrectAnswers;
    }


    public ArrayList<String> getIncorrectAnswers() {
        return incorrectAnswers;
    }

    public void setIncorrectAnswers(ArrayList<String> incorrectAnswers) {
        this.incorrectAnswers = incorrectAnswers;
    }


    public String getType() {
        return mType;
    }

    public String getDifficulty() {
        return mDifficulty;
    }

    public String getQuestion() {
        return mQuestion;
    }

    public String getCorrect_answer() {
        return mCorrectAnswer;
    }




}
