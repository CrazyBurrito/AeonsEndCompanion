package com.josh.aeonsendcompanion;

import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Josh on 12/10/2017.
 */

public class Card {

    private String role;
    private TextView cardView;
    private Boolean shuffle;

    public Card( TextView cardView){
        this.cardView = cardView;
        cardView.setTag(this);
        this.shuffle = false;
        cardView.setBackgroundResource(R.drawable.border_yellow);
    }

    public void setRole(String role){
        this.role = role;
        cardView.setText(role);
    }

    public void setShuffle(Boolean shuffle){
        this.shuffle= shuffle;
    }

    public void setBackground(int background){
        cardView.setBackgroundResource(background);
    }

    public String getRole(){
        return this.role;
    }

    public TextView getCardView() {
        return cardView;
    }

    public Boolean isTaggedForShuffle() {
        return shuffle;
    }

}
