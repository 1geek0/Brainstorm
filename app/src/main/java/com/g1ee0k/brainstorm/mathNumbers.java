package com.g1ee0k.brainstorm;


import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class mathNumbers extends Fragment {

    @BindView(R.id.one)
    AppCompatButton oneButton;
    @BindView(R.id.two)
    AppCompatButton twoButton;
    @BindView(R.id.three)
    AppCompatButton threeButton;
    @BindView(R.id.four)
    AppCompatButton fourButton;
    @BindView(R.id.five)
    AppCompatButton fiveButton;
    @BindView(R.id.six)
    AppCompatButton sixButton;
    @BindView(R.id.seven)
    AppCompatButton sevenButton;
    @BindView(R.id.eight)
    AppCompatButton eightButton;
    @BindView(R.id.nine)
    AppCompatButton nineButton;
    @BindView(R.id.zero)
    AppCompatButton zeroButton;

    @BindView(R.id.paranthesesOn)
    AppCompatButton paranthesesOButton;
    @BindView(R.id.paranthesesCn)
    AppCompatButton paranthesesCButton;
    @BindView(R.id.plusn)
    AppCompatButton plusButton;
    @BindView(R.id.minusn)
    AppCompatButton minusButton;
    @BindView(R.id.multiplyn)
    AppCompatButton multiplyButton;
    @BindView(R.id.dividen)
    AppCompatButton divideButton;

    TextInputEditText addQuestion;
    AppCompatEditText mathInputText;

    public mathNumbers() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_math_numbers, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        addQuestion = (TextInputEditText) ((LandingActivity) getActivity()).findViewById(R.id.addQuestionEditText);
        mathInputText = (AppCompatEditText) ((LandingActivity) getActivity()).findViewById(R.id.mathInputText);

        appendSymbol(oneButton, "1"); //Html.fromHtml("<sup><small>2</small></sup>"));
        appendSymbol(twoButton, "2");
        appendSymbol(threeButton, "3");
        appendSymbol(fourButton, "4");
        appendSymbol(fiveButton, "5");
        appendSymbol(sixButton, "6");
        appendSymbol(sevenButton, "7");
        appendSymbol(eightButton, "8");
        appendSymbol(nineButton, "9");
        appendSymbol(zeroButton, "0");
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
