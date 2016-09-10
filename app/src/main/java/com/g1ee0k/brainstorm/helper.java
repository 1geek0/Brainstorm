package com.g1ee0k.brainstorm;

import android.support.v7.widget.AppCompatTextView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by geek on 3/9/16.
 */
public class helper {

    // TODO: 6/9/16 add support for 'strict characters' like +,-,*,/

    static ArrayList<String> currentAnswerSet = new ArrayList<>();
    static String currentQuestion = "";

    static String squareSymbol = "\u00B2";
    static String squareRootSymbol = "\u221A";

    static String cubeSymbol = "\u00B3";
    static String cubeRootSymbol = "\u221B";

    static String power4Symbol = "\u2074";

    static String alphaSymbol = "\u03B1";

    static String superScriptOne = "\u207B\u00B9";

    static int inFocusEditText = 0; //0 means answer and 1 means addQuestionEditText

    static String addedQuestion = "";
    static ArrayList<String> addedAnswersList = new ArrayList<>();

    static final String[] functionsList = new String[]{
            "sin", "cos", "tan", "cosec", "sec", "cot",
            "sin⁻¹", "cos⁻¹", "tan⁻¹", "cosec⁻¹", "sec⁻¹", "cot⁻¹"
    };

    static HashMap<String, ArrayList<String>> FormulasMap = new HashMap<>();

    static void getNewQuestion(AppCompatTextView questionFormula) {
        Random random = new Random();
        int range = FormulasMap.size();
        int randomInt = random.nextInt(range);
        String question = (String) FormulasMap.keySet().toArray()[randomInt];
        helper.currentAnswerSet = (ArrayList<String>) FormulasMap.values().toArray()[randomInt];
        helper.currentQuestion = question;
        questionFormula.setText(question);
    }

    static boolean checkAnswer(String givenAnswer, String possibleAnswer) {
        givenAnswer = givenAnswer.replaceAll("[()]", "");
        possibleAnswer = possibleAnswer.replaceAll("[()]", "");
        int errors = 0;
        char[] givenAnswerArray = givenAnswer.toCharArray();
        char[] possibleAnswerArray = possibleAnswer.toCharArray();
        char[] greaterArray, smallerArray;
        if (givenAnswerArray.length >= possibleAnswerArray.length) {
            greaterArray = givenAnswerArray;
            smallerArray = possibleAnswerArray;
        } else {
            greaterArray = possibleAnswerArray;
            smallerArray = givenAnswerArray;
        }
        for (int i = 0; i < smallerArray.length; i++) {
            if (!(smallerArray[i] == greaterArray[i])) {
                errors += 1;
            }
        }
        errors += (greaterArray.length - smallerArray.length);
        return errors < 3;
    }

    private static FirebaseDatabase mDatabase;

    public static FirebaseDatabase getDatabase() {
        if (mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance();
            mDatabase.setPersistenceEnabled(true);
        }
        return mDatabase;
    }
}
