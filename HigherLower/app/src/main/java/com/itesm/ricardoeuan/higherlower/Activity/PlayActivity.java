package com.itesm.ricardoeuan.higherlower.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.itesm.ricardoeuan.higherlower.R;

import java.util.Random;

public class PlayActivity extends AppCompatActivity {

    private Handler mHandler = new Handler();

    private Intent intent;
    private int range;
    private int randomValue;
    private int userValue;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        intent = getIntent();
        range = intent.getIntExtra(MainActivity.EXTRA_DIFFICULTY, 10);
        randomValue = generateRandomNumber();

        TextView numberTextView = (TextView) findViewById(R.id.numberTextView);
        numberTextView.setText(numberTextView.getText().toString().concat(" " +  String.valueOf(range)));

        final EditText value = (EditText) findViewById(R.id.editNumber);
        final TextView checkText = (TextView) findViewById(R.id.checkTextView);
        final Button checkButton = (Button) findViewById(R.id.checkButton);
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!value.getText().toString().isEmpty()) {
                    userValue = Integer.parseInt(value.getText().toString());
                    count++;
                    if (userValue == randomValue) {
                        checkText.setText("You guessed it after " + String.valueOf(count) + " time(s) !");
                        randomValue = generateRandomNumber();
                        count = 0;
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "There's a new number to guess", Toast.LENGTH_LONG).show();
                                checkText.setText("");
                            }
                        }, 3000);
                    } else if(userValue < randomValue)
                        Toast.makeText(getApplicationContext(), "Whoops you gave a lower value, try again", Toast.LENGTH_LONG).show();
                    else if(userValue > randomValue)
                        Toast.makeText(getApplicationContext(), "Whoops you gave a higher value, try again", Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(getApplicationContext(), "You need to set your guess value", Toast.LENGTH_LONG).show();
            }
        });
    }

    private int generateRandomNumber() {
        return new Random().nextInt(range) + 1;
    }
}
