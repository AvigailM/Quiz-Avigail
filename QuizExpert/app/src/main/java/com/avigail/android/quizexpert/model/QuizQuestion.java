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
    public QuizQuestion(String mCategory, String mType, String mDifficulty, String mQuestion, String mCorrectAnswer, ArrayList<String> incorrectAnswers) {
        this.mCategory = mCategory;
        this.mType = mType;
        this.mDifficulty = mDifficulty;
        this.mQuestion = mQuestion;
        this.mCorrectAnswer = mCorrectAnswer;
        this.incorrectAnswers = incorrectAnswers;
    }


    public ArrayList<String> getIncorrectAnswers() {
        return incorrectAnswers;
    }

    public void setIncorrectAnswers(ArrayList<String> incorrectAnswers) {
        this.incorrectAnswers = incorrectAnswers;
    }



    public String getmType() {
        return mType;
    }

    public String getmDifficulty() {
        return mDifficulty;
    }

    public String getmQuestion() {
        return mQuestion;
    }

    public String getmCorrect_answer() {
        return mCorrectAnswer;
    }




}
