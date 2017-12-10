package com.josh.aeonsendcompanion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

        Spinner playersSpinner = (Spinner)findViewById(R.id.players_spinner);
        playersSpinner.setOnItemSelectedListener(this);

        List<String> nums = new ArrayList<String>();
        nums.add("1");
        nums.add("2");
        nums.add("3");
        nums.add("4");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, nums);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        playersSpinner.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        numPlayers = item;
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
