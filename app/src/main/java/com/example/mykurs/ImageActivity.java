package com.example.mykurs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class ImageActivity extends AppCompatActivity {
    ImageView image;
    TextView scoreText;
    TextView hintText;
    EditText textField;
    TextView mylevele;
    ProgressBar progressBar;
    Button helpButton;
    String[] words = {"fox", "castle", "car", "elephant", "house", "giraffe", "planet", "boat", "eagle",
            "africa", "radio", "alarm", "camera", "pizza", "money", "sheep", "rain", "penguin", "television", "tree", "fire"};
    int imageIndex = 0;
    int score = 0;
    int wordIndex = 0;
    int soloIndex = 0;
    int num = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        image = (ImageView) findViewById(R.id.myImage);
        scoreText = (TextView) findViewById(R.id.scoreText);
        mylevele = (TextView) findViewById(R.id.mylevel);
        textField = (EditText) findViewById(R.id.textField);
        textField.setImeOptions(EditorInfo.IME_ACTION_DONE);
        textField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    checkImage(findViewById(android.R.id.content));
                    return true;
                }
                return false;
            }
        });

        helpButton = (Button) findViewById(R.id.help);
        helpButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                AlertDialog.Builder b = new AlertDialog.Builder(ImageActivity.this);
                View v = getLayoutInflater().inflate(R.layout.activity_help, null);
                //assigning functions of buttons and textFields
                ImageButton exitButton = v.findViewById(R.id.exitButton);
                exitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onBackPressed();
                    }
                });
                Button wordButton = v.findViewById(R.id.getWordButton);
                wordButton.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint({"SetTextI18n", "DefaultLocale"})
                    @Override
                    public void onClick(View view) {
                        if (score > 25) {
                            score -= 25;
                            hintText.setText("try using the word: " + words[wordIndex]);
                            scoreText.setText("Score: " + String.format("%02d", score));
                        } else {
                            Toast.makeText(getApplicationContext(), "Not enought points!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                Button letterButton = v.findViewById(R.id.getLetterButton);
                letterButton.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint({"SetTextI18n", "DefaultLocale"})
                    @Override
                    public void onClick(View view) {
                        if (score > 10) {
                            score -= 10;
                            hintText.setText("this word starts with: " + words[imageIndex].charAt(0));
                            scoreText.setText("Score: " + String.format("%02d", score));
                        } else {
                            Toast.makeText(getApplicationContext(), "Not enought points!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                hintText = v.findViewById(R.id.hintText);
                TextView startsText = v.findViewById(R.id.starsText);
                startsText.setText("You have " + score + " points");
                b.setView(v);
                AlertDialog a = b.create();
                a.show();
            }
        });

        textField.setImeOptions(EditorInfo.IME_ACTION_DONE);
        textField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    checkImage(findViewById(android.R.id.content));
                    return true;
                }
                return false;
            }
        });
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        setImage();
    }

    public void setImage() {
        int i = this.getResources().getIdentifier(words[imageIndex], "mipmap", this.getPackageName());
        image.setImageResource(i);
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    public void checkImage(View v) {
        num = Integer.parseInt(mylevele.getText().toString());
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

        String msg;
        if (textField.getText().toString().equals(words[imageIndex])) {
            num += 1;
            mylevele.setText(Integer.toString(num));
            msg = "Correct!";
            score += 15;
            float progress = (float) imageIndex / words.length;
            progressBar.setProgress((int) (progress * 100));
            scoreText.setText("Score: " + String.format("%02d", score));
            imageIndex++;
            setImage();
        } else{
            num = 0;
            mylevele.setText(Integer.toString(num));
            msg = "try again...";
        }
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

        textField.setText("");
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class).putExtra("score", score);
        i = pushItems(i, score, wordIndex, imageIndex, soloIndex);
        startActivity(i);
        super.onBackPressed();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    protected void onResume() {
        super.onResume();
        if (getIntent().getExtras() != null) {
            loadItems();
        } else System.out.println("no");

        scoreText.setText("Score: " + String.format("%02d", score));
    }

    protected Intent pushItems(Intent i, int score, int wordIndex, int imageIndex, int soloIndex) {
        i.putExtra("score", score);
        i.putExtra("wordIndex", wordIndex);
        i.putExtra("imageIndex", imageIndex);
        i.putExtra("soloIndex", soloIndex);
        return i;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    protected void loadItems() {
        score = Objects.requireNonNull(getIntent().getExtras()).getInt("score");
        wordIndex = getIntent().getExtras().getInt("wordIndex");
        imageIndex = getIntent().getExtras().getInt("imageIndex");
        soloIndex = getIntent().getExtras().getInt("soloIndex");
        System.out.println(score + " " + wordIndex + " " + imageIndex);
        setImage();
    }
}
