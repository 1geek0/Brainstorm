package com.g1ee0k.brainstorm;


import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class mathFunctions extends Fragment {

    @BindView(R.id.square)
    AppCompatButton squareButton;
    @BindView(R.id.cube)
    AppCompatButton cubeButton;
    @BindView(R.id.power4)
    AppCompatButton power4Button;

    @BindView(R.id.sin)
    AppCompatButton sinButton;
    @BindView(R.id.cos)
    AppCompatButton cosButton;
    @BindView(R.id.tan)
    AppCompatButton tanButton;
    @BindView(R.id.cosec)
    AppCompatButton cosecButton;
    @BindView(R.id.sec)
    AppCompatButton secButton;
    @BindView(R.id.cot)
    AppCompatButton cotButton;

    @BindView(R.id.paranthesesO)
    AppCompatButton paranthesesOButton;
    @BindView(R.id.paranthesesC)
    AppCompatButton paranthesesCButton;
    @BindView(R.id.plus)
    AppCompatButton plusButton;
    @BindView(R.id.minus)
    AppCompatButton minusButton;
    @BindView(R.id.multiply)
    AppCompatButton multiplyButton;
    @BindView(R.id.divide)
    AppCompatButton divideButton;

    TextInputEditText addQuestion;
    AppCompatEditText mathInputText;
    AppCompatTextView questionFormula;

    public mathFunctions(){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_math_functions, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        addQuestion = (TextInputEditText) ((LandingActivity) getActivity()).findViewById(R.id.addQuestionEditText);
        mathInputText = (AppCompatEditText) ((LandingActivity) getActivity()).findViewById(R.id.mathInputText);
        questionFormula = (AppCompatTextView) ((LandingActivity) getActivity()).findViewById(R.id.questionFormula);

        appendSymbol(squareButton, helper.squareSymbol, helper.squareRootSymbol); //Html.fromHtml("<sup><small>2</small></sup>"));
        appendSymbol(cubeButton, helper.cubeSymbol, helper.cubeRootSymbol);
        appendSymbol(power4Button, helper.power4Symbol);
        appendSymbol(sinButton, getString(R.string.sin), getString(R.string.sin)+helper.superScriptOne);
        appendSymbol(cosButton, getString(R.string.cos), getString(R.string.cos)+helper.superScriptOne);
        appendSymbol(tanButton, getString(R.string.tan), getString(R.string.tan)+helper.superScriptOne);
        appendSymbol(cosecButton, getString(R.string.cosec), getString(R.string.cosec)+helper.superScriptOne);
        appendSymbol(secButton, getString(R.string.sec), getString(R.string.sec)+helper.superScriptOne);
        appendSymbol(cotButton, getString(R.string.cot), getString(R.string.cot)+helper.superScriptOne);
        appendSymbol(paranthesesOButton, getString(R.string.paranthesesO));
        appendSymbol(paranthesesCButton, getString(R.string.paranthesesC));
        appendSymbol(plusButton, getString(R.string.plus));
        appendSymbol(minusButton, getString(R.string.minus));
        appendSymbol(multiplyButton, getString(R.string.multiply));
        appendSymbol(divideButton, "/");
    }

    public void appendSymbol(final AppCompatButton button, final CharSequence mathSymbol) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (helper.inFocusEditText == 0) {
                    mathInputText.setText(mathInputText.getText().toString() + mathSymbol);
                    mathInputText.setSelection(mathInputText.length());
                } else if (helper.inFocusEditText == 1) {
                    addQuestion.setText(addQuestion.getText().toString() + mathSymbol);
                    addQuestion.setSelection(addQuestion.length());
                }
            }
        });
    }
    public void appendSymbol(final AppCompatButton button, final CharSequence mathSymbol, final CharSequence longSymbol) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (helper.inFocusEditText == 0) {
                    mathInputText.setText(mathInputText.getText().toString() + mathSymbol);
                    mathInputText.setSelection(mathInputText.length());
                } else if (helper.inFocusEditText == 1) {
                    addQuestion.setText(addQuestion.getText().toString() + mathSymbol);
                    addQuestion.setSelection(addQuestion.length());
                }
            }
        });
        button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (helper.inFocusEditText == 0) {
                    mathInputText.setText(mathInputText.getText().toString() + longSymbol);
                    mathInputText.setSelection(mathInputText.length());
                } else if (helper.inFocusEditText == 1) {
                    addQuestion.setText(addQuestion.getText().toString() + longSymbol);
                    addQuestion.setSelection(addQuestion.length());
                }
                return true;
            }
        });
    }
}
