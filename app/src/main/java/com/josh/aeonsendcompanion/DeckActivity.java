package com.josh.aeonsendcompanion;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class DeckActivity extends AppCompatActivity implements  AdapterView.OnItemSelectedListener{

    public ArrayList<Card> deck = new ArrayList<>();
    public Animation animation;
    private ArrayList<Card> discard = new ArrayList<>();
    public int players = Integer.parseInt(MainActivity.numPlayers);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck);
        animation = AnimationUtils.loadAnimation(this, R.anim.alpha);
        animation.reset();

        Card card1 = new Card((TextView) findViewById(R.id.card1));
        Card card2 = new Card((TextView) findViewById(R.id.card2));
        Card card3 = new Card((TextView) findViewById(R.id.card3));
        Card card4 = new Card((TextView) findViewById(R.id.card4));
        Card card5 = new Card((TextView) findViewById(R.id.card5));
        Card card6 = new Card((TextView) findViewById(R.id.card6));

        deck.add(card1);
        deck.add(card2);
        deck.add(card3);
        deck.add(card4);
        deck.add(card5);

        if(players == 1) {
            findViewById(R.id.card6).setVisibility(View.GONE);
        } else{
            deck.add(card6);
        }

        card1.setRole(Role.NEMESIS);
        card2.setRole(Role.NEMESIS);
        card3.setRole(Role.PLAYER_1);

        switch (players){
            case 1:
                card4.setRole(Role.PLAYER_1);
                card5.setRole(Role.PLAYER_1);
                break;
            case 2:
                card4.setRole(Role.PLAYER_1);
                card5.setRole(Role.PLAYER_2);
                card6.setRole(Role.PLAYER_2);
                break;
            case 3:
                card4.setRole(Role.PLAYER_2);
                card5.setRole(Role.PLAYER_3);
                card6.setRole(Role.WILD);
                break;
            case 4:
                card4.setRole(Role.PLAYER_2);
                card5.setRole(Role.PLAYER_3);
                card6.setRole(Role.PLAYER_4);
                break;
            default:
                //error
                break;
        }

        Collections.shuffle(deck);
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    public void nextPlayer(View view){

        ImageView turnImage = findViewById(R.id.turnImage);

        if(deck.isEmpty()){
            for(Iterator<Card> iterator = discard.iterator(); iterator.hasNext();){
                Card card = iterator.next();
                deck.add(card);
                iterator.remove();
            }
            Collections.shuffle(deck);
            Context context = getApplicationContext();
            CharSequence message = "Deck Shuffled";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, message, duration);
            toast.show();

            for(Card card : deck){
                card.setBackground(R.drawable.border_yellow);
            }

            Button scryButton = findViewById(R.id.scryButton);
            scryButton.setEnabled(true);
        }

        Card next = deck.remove(0);
        next.setBackground(R.drawable.border_red);
        discard.add(next);

        switch(next.getRole()){
            case NEMESIS:
                turnImage.setImageResource(R.drawable.turn_order_nemesis);
                break;
            case PLAYER_1:
                turnImage.setImageResource(R.drawable.turn_order_p1);
                break;
            case PLAYER_2:
                turnImage.setImageResource(R.drawable.turn_order_p2);
                break;
            case PLAYER_3:
                turnImage.setImageResource(R.drawable.turn_order_p3);
                break;
            case PLAYER_4:
                turnImage.setImageResource(R.drawable.turn_order_p4);
                break;
            case WILD:
                turnImage.setImageResource(R.drawable.turn_order_wild);
                break;
        }
        turnImage.startAnimation(animation);

        if (deck.size() < 2) {
            Button scryButton = findViewById(R.id.scryButton);
            scryButton.setEnabled(false);
        }

    }

    public void selectForShuffle(View view){

        for(Card card : discard){
            card.getCardView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.setBackgroundResource(R.drawable.border_green);
                    Card card = (Card)v.getTag();
                    card.setTaggedForShuffle(true);
                }
            });
        }

        ImageButton nextButton = findViewById(R.id.nextButton);
        nextButton.setClickable(false);

        view.setVisibility(View.GONE);
        final View scryButton = findViewById(R.id.scryButton);
        scryButton.setVisibility(View.GONE);
        final Button confirmShuffle = new Button(this);
        final Button cancelShuffle = new Button(this);
        confirmShuffle.setText(R.string.confirm);
        cancelShuffle.setText(R.string.cancel);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT, 0.5f);
        confirmShuffle.setLayoutParams(params);
        cancelShuffle.setLayoutParams(params);
        ViewGroup layout = findViewById(R.id.shuffleLayout);
        layout.addView(confirmShuffle);
        layout.addView(cancelShuffle);

        confirmShuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(Iterator<Card> iterator = discard.iterator(); iterator.hasNext();){
                    Card card = iterator.next();
                    if(card.isTaggedForShuffle()) {
                        deck.add(card);
                        card.setBackground(R.drawable.border_yellow);
                        iterator.remove();
                    }
                }
                Collections.shuffle(deck);
                ImageButton nextButton = findViewById(R.id.nextButton);
                nextButton.setClickable(true);
                cancelShuffle.setVisibility(View.GONE);
                confirmShuffle.setVisibility(View.GONE);
                Button shuffleButton = findViewById(R.id.shuffleButton);
                shuffleButton.setVisibility(View.VISIBLE);
                scryButton.setVisibility(View.VISIBLE);

                if (deck.size() > 1) {
                    scryButton.setEnabled(true);
                }
            }
        });

        cancelShuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelShuffle.setVisibility(View.GONE);
                confirmShuffle.setVisibility(View.GONE);
                Button shuffleButton =  findViewById(R.id.shuffleButton);
                shuffleButton.setVisibility(View.VISIBLE);
                scryButton.setVisibility(View.VISIBLE);

                ImageButton nextButton = findViewById(R.id.nextButton);
                nextButton.setClickable(true);

                for(Card card : discard){
                    if(card.isTaggedForShuffle()){
                        card.setBackground(R.drawable.border_red);
                        card.setTaggedForShuffle(false);
                    }
                }
            }
        });
    }

    public void scry(View view) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = inflater.inflate(R.layout.scry_popup, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);

        SeekBar scryDepthSeekBar = popupView.findViewById(R.id.scryDepthSeekBar);
        scryDepthSeekBar.setMax(deck.size());
        scryDepthSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                TextView scryDepthProgress = popupView.findViewById(R.id.scryDepthProgress);
                scryDepthProgress.setText(String.valueOf(progress + 1));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        popupWindow.setWidth(ConstraintLayout.LayoutParams.WRAP_CONTENT);

        popupWindow.showAtLocation(findViewById(R.id.deckGrid), Gravity.CENTER, 0, 0);

        //TODO scry functionality
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent){
        //error
    }
}
