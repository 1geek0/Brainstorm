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
public class CharacterKeyboard extends Fragment {

    @BindView(R.id.alpha)
    AppCompatButton alphaButton;
    @BindView(R.id.beta)
    AppCompatButton betaButton;
    @BindView(R.id.pi)
    AppCompatButton piButton;
    @BindView(R.id.degree)
    AppCompatButton degreeButton;
    @BindView(R.id.greaterThan)
    AppCompatButton greaterThanButton;
    @BindView(R.id.lessThan)
    AppCompatButton lessThanButton;
    @BindView(R.id.a)
    AppCompatButton aButton;
    @BindView(R.id.b)
    AppCompatButton bButton;
    @BindView(R.id.c)
    AppCompatButton cButton;

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

    public CharacterKeyboard() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_character_keyboard, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        addQuestion = (TextInputEditText) ((LandingActivity) getActivity()).findViewById(R.id.addQuestionEditText);
        mathInputText = (AppCompatEditText) ((LandingActivity) getActivity()).findViewById(R.id.mathInputText);
        questionFormula = (AppCompatTextView) ((LandingActivity) getActivity()).findViewById(R.id.questionFormula);

        appendSymbol(alphaButton, getString(R.string.alpha));
        appendSymbol(betaButton, getString(R.string.beta));
        appendSymbol(piButton, getString(R.string.pi));
        appendSymbol(degreeButton, getString(R.string.degree));
        appendSymbol(greaterThanButton, getString(R.string.greaterThan));
        appendSymbol(lessThanButton, getString(R.string.lessThan));
        appendSymbol(aButton, getString(R.string.a));
        appendSymbol(bButton, getString(R.string.b));
        appendSymbol(cButton, getString(R.string.c));

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
}
