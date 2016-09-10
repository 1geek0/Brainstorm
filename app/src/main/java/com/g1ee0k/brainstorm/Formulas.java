package com.g1ee0k.brainstorm;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;

/**
 * Created by geek on 5/9/16.
 */
@IgnoreExtraProperties
public class Formulas {
    public ArrayList<String> formulas;
    public Formulas(){

    }
    public Formulas(ArrayList<String> formulas){
        this.formulas = formulas;
    }
}
