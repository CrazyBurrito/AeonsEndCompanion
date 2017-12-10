package com.josh.aeonsendcompanion;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class DeckActivity extends AppCompatActivity{

    public ArrayList<Card> deck = new ArrayList<>();
    public Animation a;
    private ArrayList<Card> discard = new ArrayList<>();
    public int players = Integer.parseInt(MainActivity.numPlayers);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck);
        a = AnimationUtils.loadAnimation(this, R.anim.alpha);
        a.reset();

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

        card1.setRole("Nemesis");
        card2.setRole("Nemesis");
        card3.setRole("Player 1");

        switch (players){
            case 1:
                card4.setRole("Player 1");
                card5.setRole("Player 1");
                break;
            case 2:
                card4.setRole("Player 1");
                card5.setRole("Player 2");
                card6.setRole("Player 2");
                break;
            case 3:
                card4.setRole("Player 2");
                card5.setRole("Player 3");
                card6.setRole("Wild");
                break;
            case 4:
                card4.setRole("Player 2");
                card5.setRole("Player 3");
                card6.setRole("Player 4");
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

        ImageView turnImage = (ImageView) findViewById(R.id.turnImage);

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
        }

        Card next = deck.remove(0);
        next.setBackground(R.drawable.border_red);
        discard.add(next);

        switch(next.getRole()){
            case "Nemesis":
                turnImage.setImageResource(R.drawable.turn_order_nemesis);
                break;
            case "Player 1":
                turnImage.setImageResource(R.drawable.turn_order_p1);
                break;
            case "Player 2":
                turnImage.setImageResource(R.drawable.turn_order_p2);
                break;
            case "Player 3":
                turnImage.setImageResource(R.drawable.turn_order_p3);
                break;
            case "Player 4":
                turnImage.setImageResource(R.drawable.turn_order_p4);
                break;
            case "Wild":
                turnImage.setImageResource(R.drawable.turn_order_wild);
                break;
        }
        turnImage.startAnimation(a);

    }

    public void selectForShuffle(View view){

        for(Card card : discard){
            card.getCardView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.setBackgroundResource(R.drawable.border_green);
                    Card card = (Card)v.getTag();
                    card.setShuffle(true);
                }
            });
        }

        ImageButton nextButton = (ImageButton) findViewById(R.id.nextButton);
        nextButton.setClickable(false);

        view.setVisibility(View.GONE);
        final Button confirmShuffle = new Button(this);
        final Button cancelShuffle = new Button(this);
        confirmShuffle.setText(R.string.confirm);
        cancelShuffle.setText(R.string.cancel);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, DrawerLayout.LayoutParams.MATCH_PARENT, 0.5f);
        confirmShuffle.setLayoutParams(params);
        cancelShuffle.setLayoutParams(params);
        ViewGroup layout = (ViewGroup) findViewById(R.id.shuffleLayout);
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
                ImageButton nextButton = (ImageButton) findViewById(R.id.nextButton);
                nextButton.setClickable(true);
                cancelShuffle.setVisibility(View.GONE);
                confirmShuffle.setVisibility(View.GONE);
                Button shuffleButton = (Button) findViewById(R.id.shuffleButton);
                shuffleButton.setVisibility(View.VISIBLE);
            }
        });

        cancelShuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelShuffle.setVisibility(View.GONE);
                confirmShuffle.setVisibility(View.GONE);
                Button shuffleButton = (Button) findViewById(R.id.shuffleButton);
                shuffleButton.setVisibility(View.VISIBLE);
                ImageButton nextButton = (ImageButton) findViewById(R.id.nextButton);
                nextButton.setClickable(true);

                for(Card card : discard){
                    if(card.isTaggedForShuffle()){
                        card.setBackground(R.drawable.border_red);
                        card.setShuffle(false);
                    }
                }
            }
        });
    }



}
