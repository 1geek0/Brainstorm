package com.g1ee0k.brainstorm;

import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;

/**
 * Created by geek on 4/9/16.
 */
public class logSubmission implements Runnable {
    String question;
    ArrayList<String> rightAnswers;
    String givenAnswer;
    boolean correctAnswer;
    FirebaseAnalytics firebaseAnalytics;

    logSubmission(String question, ArrayList<String> rightAnswers, String givenAnswer, boolean correctAnswer, FirebaseAnalytics firebaseAnalytics){
        this.question = question;
        this.rightAnswers = rightAnswers;
        this.givenAnswer = givenAnswer;
        this.correctAnswer = correctAnswer;
        this.firebaseAnalytics = firebaseAnalytics;
    }

    @Override
    public void run() {
        Bundle submission = new Bundle();
        submission.putString("question", question);
        submission.putStringArrayList("rightAnswers", rightAnswers);
        submission.putString("givenAnswer", givenAnswer);
        submission.putBoolean("correctAnswer", correctAnswer);
        firebaseAnalytics.logEvent("answerSubmit", submission);
    }
}
