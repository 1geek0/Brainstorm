package com.g1ee0k.brainstorm;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LandingActivity extends AppCompatActivity {

    @BindView(R.id.snackbarPosition)
    CoordinatorLayout rootView;

    @BindView(R.id.questionFormula)
    AppCompatTextView questionFormula;

    @BindView(R.id.mathInputText)
    AppCompatEditText mathInputText;

    @BindView(R.id.inputTabLayout)
    TabLayout inputTabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @BindView(R.id.del)
    AppCompatImageView backspaceButton;
    @BindView(R.id.submit)
    AppCompatImageView submitButton;

    @BindView(R.id.addFab)
    FloatingActionButton addFabButton;
    @BindView(R.id.addAnswer)
    AppCompatImageView addAnswerButton;
    @BindView(R.id.addQuestionEditText)
    TextInputEditText addQuestionEditText;
    @BindView(R.id.addQuestion)
    AppCompatImageView addQuestion;

    DirectExecutor executor = new DirectExecutor();

    FirebaseAuth auth;
    FirebaseAnalytics mFirebaseAnalytics;

    AlertDialog.Builder confirmDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        ButterKnife.bind(this);
        setUpViewPager(viewPager);
        inputTabLayout.setupWithViewPager(viewPager);
        confirmDialog = new AlertDialog.Builder(LandingActivity.this);
        executor.execute(getFormulas);
    }

    private void setUpViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new mathNumbers(), "Numbers");
        adapter.addFragment(new mathFunctions(), "Functions");
        adapter.addFragment(new CharacterKeyboard(), "Characters");
        viewPager.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
//        helper.getNewQuestion(questionFormula);

        executor.execute(listenerSetter);
    }

    View.OnClickListener submitListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String givenAnswer = mathInputText.getText().toString();
            for (String possibleAnswer : helper.currentAnswerSet) {
                if (helper.checkAnswer(givenAnswer, possibleAnswer)) {
                    Snackbar.make(rootView, "Good Bacche :)", Snackbar.LENGTH_SHORT).show();
                    mathInputText.setText("");
                    mathInputText.setText("");
                    helper.getNewQuestion(questionFormula);
                    executor.execute(new logSubmission(helper.currentQuestion, helper.currentAnswerSet, givenAnswer, true, mFirebaseAnalytics));
                    break;
                } else {
                    executor.execute(new logSubmission(helper.currentQuestion, helper.currentAnswerSet, givenAnswer, false, mFirebaseAnalytics));
                    Snackbar.make(rootView, "Nalayak Kahika!", Snackbar.LENGTH_SHORT).show();
                }
            }
        }
    };

    View.OnClickListener backspaceListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (helper.inFocusEditText == 0) {
                String inputString = mathInputText.getText().toString();
                if (inputString.length() > 0) {
                    for (String aFunctionsList : helper.functionsList) {
                        if (inputString.endsWith(aFunctionsList)) {
                            mathInputText.setText(inputString.substring(0, inputString.lastIndexOf(aFunctionsList)));
                            mathInputText.setSelection(mathInputText.length());
                            break;
                        } else {
                            mathInputText.setText(inputString.substring(0, inputString.length() - 1));
                            mathInputText.setSelection(mathInputText.length());
                        }
                    }
                }
            } else if (helper.inFocusEditText == 1) {
                String inputString = addQuestionEditText.getText().toString();
                if (inputString.length() > 0) {
                    for (String aFunctionsList : helper.functionsList) {
                        if (inputString.endsWith(aFunctionsList)) {
                            addQuestionEditText.setText(inputString.substring(0, inputString.lastIndexOf(aFunctionsList)));
                            addQuestionEditText.setSelection(addQuestionEditText.length());
                            break;
                        } else {
                            addQuestionEditText.setText(inputString.substring(0, inputString.length() - 1));
                            addQuestionEditText.setSelection(addQuestionEditText.length());
                        }
                    }
                }
            }
        }
    };

    View.OnLongClickListener backspaceLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            if (helper.inFocusEditText == 0) {
                mathInputText.setText("");
            } else if (helper.inFocusEditText == 1) {
                addQuestionEditText.setText("");
            }
            return false;
        }
    };

    View.OnClickListener addFabListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (addQuestionEditText.getVisibility() == View.GONE) {
                questionFormula.setVisibility(View.GONE);
                submitButton.setVisibility(View.GONE);
                addFabButton.setImageDrawable(ContextCompat.getDrawable(LandingActivity.this, R.drawable.ic_check_white_48dp));
                addQuestionEditText.setVisibility(View.VISIBLE);
                addAnswerButton.setVisibility(View.VISIBLE);
                addQuestion.setVisibility(View.VISIBLE);
            } else {
                switchToNormal();
            }
        }
    };

    View.OnClickListener addAnswerListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mathInputText.length() > 0) {
                helper.addedAnswersList.add(mathInputText.getText().toString());
                mathInputText.setText("");
            }
        }
    };

    View.OnTouchListener addQuestionTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            helper.inFocusEditText = 1;
            return false;
        }
    };

    View.OnTouchListener answerTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            helper.inFocusEditText = 0;

            return false;
        }
    };

    private void switchToNormal() {
        confirmDialog.setTitle("Do You Want To Save This Formula?");
        confirmDialog.setCancelable(true);
        confirmDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addFormulaRunnable formulaRunnable = new addFormulaRunnable(addQuestionEditText.getText().toString(), helper.addedAnswersList);
                executor.execute(formulaRunnable);

                questionFormula.setVisibility(View.VISIBLE);
                addFabButton.setImageDrawable(ContextCompat.getDrawable(LandingActivity.this, R.drawable.ic_add_white_48dp));
                addQuestionEditText.setVisibility(View.GONE);
                addAnswerButton.setVisibility(View.GONE);
                submitButton.setVisibility(View.VISIBLE);
                addQuestion.setVisibility(View.GONE);

                addQuestionEditText.setText("");
                mathInputText.setText("");

                helper.addedAnswersList.clear();
            }
        });
        confirmDialog.setNegativeButton("Let Me Check", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        boolean answerListIsEmpty = helper.addedAnswersList.isEmpty();
        int answerEditTextLength = mathInputText.getText().length();
        int questionEditTextLength = addQuestionEditText.getText().length();
        if (answerListIsEmpty && (answerEditTextLength==0 | questionEditTextLength==0)) {
            questionFormula.setVisibility(View.VISIBLE);
            addFabButton.setImageDrawable(ContextCompat.getDrawable(LandingActivity.this, R.drawable.ic_add_white_48dp));
            addQuestionEditText.setVisibility(View.GONE);
            addAnswerButton.setVisibility(View.GONE);
            submitButton.setVisibility(View.VISIBLE);
            addQuestion.setVisibility(View.GONE);
        } else if(answerListIsEmpty && (answerEditTextLength>0 && questionEditTextLength>0)){
            helper.addedAnswersList.add(mathInputText.getText().toString());
            confirmDialog.show();
        } else if (!answerListIsEmpty && (answerEditTextLength>0 && questionEditTextLength>0)){
            helper.addedAnswersList.add(mathInputText.getText().toString());
            confirmDialog.show();
        } else if (!answerListIsEmpty && answerEditTextLength==0 && questionEditTextLength>0){
            confirmDialog.show();
        }
    }

    View.OnClickListener addQuestionListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            confirmDialog.setTitle("Do You Want To Save This Formula?");
            confirmDialog.setCancelable(true);
            confirmDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    addFormulaRunnable formulaRunnable = new addFormulaRunnable(addQuestionEditText.getText().toString(), helper.addedAnswersList);
                    executor.execute(formulaRunnable);

                    addQuestionEditText.setText("");
                    mathInputText.setText("");

                    helper.addedAnswersList.clear();
                }
            });
            confirmDialog.setNegativeButton("Let Me Check", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            if (!helper.addedAnswersList.isEmpty() && addQuestionEditText.getText().length()>0){
                Log.d("addQuestion", "reached here");
                confirmDialog.show();
            } else if (mathInputText.getText().length()>0 && addQuestionEditText.getText().length()>0){
                helper.addedAnswersList.add(mathInputText.getText().toString());
                Log.d("addQuestion", "reached here");
                confirmDialog.show();
            }
        }
    };

    /*private void getFormulas(){
        DatabaseReference formulasRef = FirebaseDatabase.getInstance().getReference("formulas");
        formulasRef.orderByKey().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("theDB", dataSnapshot.getKey() + ": " + dataSnapshot.getValue().toString());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }*/

    Runnable listenerSetter = new Runnable() {
        @Override
        public void run() {
            submitButton.setOnClickListener(submitListener);

            auth = FirebaseAuth.getInstance();
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(LandingActivity.this);
            mFirebaseAnalytics.setUserId(auth.getCurrentUser().getEmail());

            backspaceButton.setOnClickListener(backspaceListener);
            backspaceButton.setOnLongClickListener(backspaceLongClickListener);

            addFabButton.setOnClickListener(addFabListener);

            mathInputText.setOnTouchListener(answerTouchListener);
            addQuestionEditText.setOnTouchListener(addQuestionTouchListener);
            addAnswerButton.setOnClickListener(addAnswerListener);
            addQuestion.setOnClickListener(addQuestionListener);
        }
    };

    Runnable getFormulas = new Runnable() {
        @Override
        public void run() {
            DatabaseReference formulaRef = helper.getDatabase().getReference("formulas");
            formulaRef.orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    HashMap formulas = (HashMap) dataSnapshot.getValue();
                    FirebaseToLocal firebaseToLocal = new FirebaseToLocal(formulas);
                    executor.execute(firebaseToLocal);
                    helper.getNewQuestion(questionFormula);
//                    helper.FormulasMap.put(dataSnapshot.getKey(), (HashMap) dataSnapshot.getValue());
                    Log.d("theDB", String.valueOf(formulas.get("tan2α")));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    };
}
