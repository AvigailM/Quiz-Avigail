package com.avigail.android.quizexpert;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.avigail.android.quizexpert.model.QuizQuestion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import es.dmoral.toasty.Toasty;


/**
 * Created by Avigail on 05/10/2018.
 */

public class GameActivity extends AppCompatActivity  implements View.OnClickListener {


    private static final String TAG_RESULTS = "results";

    private static final String TAG_CATEGORY= "category";

    private static final String TAG_TYPE = "type";

    private static final String TAG_DIFFICULTY = "difficulty";

    private static final String TAG_QUESTION = "question";

    private static final String TAG_CORRECT_ANSWER = "correct_answer";

    private static final String TAG_INCORRECT_ANSWERS = "incorrect_answers";

    String url = "https://opentdb.com/api.php?amount=10";

    private List<QuizQuestion> quizQuestionsList;

    private TextView questionTextView;

    private TextView numOfQuestionTextView;

    private TextView scoreText;

    // if the Options for the Answer are 2 instead of 4
    private static boolean setTwoAnswerOptions = false;


    private int numOfQuestion = 0;
    private int numOfScore = 0;

    View loadingIndicator;

    // set buttons:
    private Button buttonAnswer1;
    private Button buttonAnswer2;
    private Button buttonAnswer3;
    private Button buttonAnswer4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_quiz);

        String urlBuildBySettings = getIntent().getExtras().getString("url");

        if (!urlBuildBySettings.isEmpty()){
            url = urlBuildBySettings;
        }

        questionTextView = (TextView)findViewById(R.id.question);

        numOfQuestionTextView = (TextView)findViewById(R.id.numOfQuestion);

        scoreText = (TextView)findViewById(R.id.scoreText);

        loadingIndicator = findViewById(R.id.loading_indicator);

        buttonAnswer1 = (Button)findViewById(R.id.buttonAnswer1);
        buttonAnswer2 = (Button)findViewById(R.id.buttonAnswer2);
        buttonAnswer3 = (Button)findViewById(R.id.buttonAnswer3);
        buttonAnswer4 = (Button)findViewById(R.id.buttonAnswer4);

        buttonAnswer1.setOnClickListener(this);
        buttonAnswer2.setOnClickListener(this);
        buttonAnswer3.setOnClickListener(this);
        buttonAnswer4.setOnClickListener(this);

        //initializing the list of questions for the quiz:
        quizQuestionsList = new ArrayList<>();

        jsonParse();

    }

    //----------------------------------------------------------------------------

    // get the data from the url
    private void jsonParse() {

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Log.e("data",response);

                try {
                    //getting the whole json object from the response
                    JSONObject obj = new JSONObject(response);
                    int success = obj.getInt("response_code");

                    if (success == 0) {

                        JSONArray jsonArray;
                        jsonArray = obj.getJSONArray(TAG_RESULTS);

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject c = null;
                            c = jsonArray.getJSONObject(i);

                            String category = c.getString(TAG_CATEGORY);
                            String type = c.getString(TAG_TYPE);
                            String difficulty = c.getString(TAG_DIFFICULTY);
                            String question = c.getString(TAG_QUESTION);
                            // decode HTML :
                            question = Html.fromHtml(question).toString();
                            String correctAnswer = c.getString(TAG_CORRECT_ANSWER);
                            // decode HTML :
                            correctAnswer = Html.fromHtml(correctAnswer).toString();

                            JSONArray incorrectAnswers = c.getJSONArray(TAG_INCORRECT_ANSWERS);
                            ArrayList<String> incorrectAnswersList = new ArrayList<>();
                            for (int j = 0; j < incorrectAnswers.length(); j++) {
                                // decode HTML:
                                incorrectAnswersList.add(Html.fromHtml(incorrectAnswers.getString(j)).toString());
                            }

                            quizQuestionsList.add(new QuizQuestion(category, type, difficulty, question, correctAnswer, incorrectAnswersList));

                        }

                        loadingIndicator.setVisibility(View.GONE);

                        LoaderView();

                        // set the first answers and the question on screen :
                        setVeiw();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error while readfing google", Toast.LENGTH_SHORT).show();
            }
        });

        SingletonClass.getInstance(this).addToRequestQueue(stringRequest);

    }

    //----------------------------------------------------------------------------

    // setting the buttons and Text views with answers, score and question on the screen :
    private void setVeiw(){

        ArrayList<String>  answersList = new ArrayList<String>();

        answersList = quizQuestionsList.get(numOfQuestion).getIncorrectAnswers();

        answersList.add(quizQuestionsList.get(numOfQuestion).getmCorrect_answer());

        Collections.shuffle(answersList, new Random());

        questionTextView.setText(quizQuestionsList.get(numOfQuestion).getmQuestion());

        if(!quizQuestionsList.get(numOfQuestion).getmType().equals("boolean")){

            if(setTwoAnswerOptions){
                buttonAnswer4.setVisibility(View.VISIBLE);
                buttonAnswer3.setVisibility(View.VISIBLE);
                setTwoAnswerOptions = false;
            }

            buttonAnswer3.setText(answersList.get(2));
            buttonAnswer4.setText(answersList.get(3));

        }else{
            // if the you have Two Answer Options instead of 4:
            setTwoAnswerOptions = true;
            buttonAnswer4.setVisibility(View.INVISIBLE);
            buttonAnswer3.setVisibility(View.INVISIBLE);

        }

        buttonAnswer1.setText(answersList.get(0));
        buttonAnswer2.setText(answersList.get(1));

    }


    //----------------------------------------------------------------------------

    // when buttons are clicked:
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.buttonAnswer1:
                checkAnswerClicked(buttonAnswer1);
                break;
            case R.id.buttonAnswer2:
                checkAnswerClicked(buttonAnswer2);
                break;
            case R.id.buttonAnswer3:
                checkAnswerClicked(buttonAnswer3);
                break;
            case R.id.buttonAnswer4:
                checkAnswerClicked(buttonAnswer4);
                break;
        }

        numOfQuestion++;

        if( numOfQuestion < quizQuestionsList.size()){
            numOfQuestionTextView.setText(numOfQuestion+1 + "/" + quizQuestionsList.size());
            setVeiw();
        } else {
            // Finished the Questions of the quiz - Go to the Results Screen
            Intent i = new Intent(GameActivity.this,ResultActivity.class);
            i.putExtra("score", numOfScore);
            i.putExtra("url", url);
            startActivity(i);
        }

    }

    //----------------------------------------------------------------------------

    // check if the answer is correct or wrong and
    // send a Toast message using Toasty:
    private void checkAnswerClicked(final Button buttonClicked){

        String clickedAnswer = buttonClicked.getText().toString();
        String textToPrintOnScreen = "";

        String correctAnswer = quizQuestionsList.get(numOfQuestion).getmCorrect_answer().toString();

        if ( numOfQuestion < quizQuestionsList.size() && clickedAnswer.equals(correctAnswer)){

           textToPrintOnScreen = "Correct!";
           numOfScore += 10;
           scoreText.setText("score " + numOfScore);
           Toasty.success(this, textToPrintOnScreen, Toast.LENGTH_SHORT, true).show();

        } else{

            textToPrintOnScreen = "wrong the correct answer is \n" + correctAnswer;
            Toasty.error(this, textToPrintOnScreen, Toast.LENGTH_SHORT, true).show();

        }

    }

    //----------------------------------------------------------------------------

    // if the data is ok- set all the screen to VISIBLE:
    // and set score and number Of Question on the screen
    private void LoaderView(){

        buttonAnswer1.setVisibility(View.VISIBLE);
        buttonAnswer2.setVisibility(View.VISIBLE);
        buttonAnswer3.setVisibility(View.VISIBLE);
        buttonAnswer4.setVisibility(View.VISIBLE);
        scoreText.setVisibility(View.VISIBLE);
        numOfQuestionTextView.setVisibility(View.VISIBLE);
        questionTextView.setVisibility(View.VISIBLE);

        // set the score and the number Of Question :
        numOfQuestionTextView.setText(numOfQuestion + "/" + quizQuestionsList.size());
        scoreText.setText("score " + numOfScore);
    }

    //----------------------------------------------------------------------------

}
