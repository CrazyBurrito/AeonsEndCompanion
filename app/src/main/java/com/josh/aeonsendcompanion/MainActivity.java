package com.josh.aeonsendcompanion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    public static String EXTRA_MESSAGE = "com.josh.AeonsEndCompanion.Message";
    public static String numPlayers = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner playersSpinner = findViewById(R.id.players_spinner);
        playersSpinner.setOnItemSelectedListener(this);

        List<String> numPlayers = new ArrayList<>();
        numPlayers.add("1");
        numPlayers.add("2");
        numPlayers.add("3");
        numPlayers.add("4");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, numPlayers);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        playersSpinner.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        numPlayers = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent){
        //error
    }

    public void generateResult(View view){
        Intent intent = new Intent(this, DeckActivity.class);

        intent.putExtra(EXTRA_MESSAGE, "");
        startActivity(intent);
    }
}
