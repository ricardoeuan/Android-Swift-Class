package com.itesm.ricardoeuan.higherlower.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.itesm.ricardoeuan.higherlower.R;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String EXTRA_DIFFICULTY = "com.itesm.ricardoeuan.higherlower.DIFFICULTY";
    private int difficultyRange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner levelsSpinner = (Spinner) findViewById(R.id.levelsSpinner);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.levels_array, android.R.layout.simple_spinner_dropdown_item);
        levelsSpinner.setAdapter(spinnerAdapter);

        levelsSpinner.setOnItemSelectedListener(this);

        Button startButton = (Button) findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent playIntent = new Intent(getApplicationContext(), PlayActivity.class);
                playIntent.putExtra(EXTRA_DIFFICULTY, difficultyRange);
                startActivity(playIntent);
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0 :
                this.difficultyRange = 10;
                showCustomMessage("It seems you're a beginner. Let's practice a little");
                break;
            case 1:
                this.difficultyRange = 20;
                showCustomMessage("Nice to have an average player here");
                break;
            case 2:
                this.difficultyRange = 30;
                showCustomMessage("Clearly this is not the first time you play");
                break;
            case 3:
                this.difficultyRange = 40;
                showCustomMessage("So you're an experienced player, let's se what you've got");
                break;
            case 4:
                this.difficultyRange = 50;
                showCustomMessage("Only few players have left here with success");
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
            this.difficultyRange = 10;
    }

    private void showCustomMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
