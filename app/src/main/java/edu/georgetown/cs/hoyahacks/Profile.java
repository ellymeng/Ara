package edu.georgetown.cs.hoyahacks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Profile extends AppCompatActivity {
    Button profileButton;
    Button suggestionsButton;
    Button chatButton;
    TextView profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileButton = (Button) findViewById(R.id.profileButton);
        suggestionsButton = (Button) findViewById(R.id.suggestionsButton);
        chatButton = (Button) findViewById(R.id.chatButton);
        profileButton.setOnClickListener(menuListener);
        suggestionsButton.setOnClickListener(menuListener);
        chatButton.setOnClickListener(menuListener);

        profile = (TextView) findViewById(R.id.profile);
    }

    public View.OnClickListener menuListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (v == profileButton) {
                startActivity(new Intent(Profile.this, Profile.class));
            }
            else if (v == suggestionsButton) {
                startActivity(new Intent(Profile.this, Suggestions.class));
            }
            else if (v == chatButton) {
                startActivity(new Intent(Profile.this, OpenChats.class));
            }
        }
    };


}
