package com.josh.aeonsendcompanion;

import android.widget.TextView;

/**
 * Created by Josh on 12/10/2017.
 */

public class Card {

    private Role role;
    private TextView cardView;
    private Boolean taggedForShuffle;

    Card( TextView cardView){
        this.cardView = cardView;
        cardView.setTag(this);
        this.taggedForShuffle = false;
        cardView.setBackgroundResource(R.drawable.border_yellow);
    }

    void setRole(Role role){
        this.role = role;
        cardView.setText(role.toString());
    }

    void setTaggedForShuffle(Boolean taggedForShuffle){
        this.taggedForShuffle = taggedForShuffle;
    }

    public void setBackground(int background){
        cardView.setBackgroundResource(background);
    }

    Role getRole(){
        return role;
    }

    TextView getCardView() {
        return cardView;
    }

    Boolean isTaggedForShuffle() {
        return taggedForShuffle;
    }

}
