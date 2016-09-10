package com.g1ee0k.brainstorm;

import java.util.ArrayList;

/**
 * Created by geek on 4/9/16.
 */
public class addFormulaRunnable implements Runnable {
    String formulaQuestion;
//    String formulaType;
    ArrayList<String> formulaAnswers;

    public addFormulaRunnable(String formulaString, ArrayList<String> formulaAnswers){
        this.formulaQuestion = formulaString;
        this.formulaAnswers = formulaAnswers;
    }

    @Override
    public void run() {
        if (helper.FormulasMap.get(formulaQuestion) != null) {
            if (helper.FormulasMap.get(formulaQuestion) != formulaAnswers) {
                formulaAnswers.addAll(helper.FormulasMap.get(formulaQuestion));
                helper.getDatabase().getReference().child("formulas").child(formulaQuestion).setValue(formulaAnswers);
            }
        } else {
            helper.getDatabase().getReference().child("formulas").child(formulaQuestion).setValue(formulaAnswers);
        }
    }
}
