package edu.georgetown.cs.hoyahacks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Profile extends AppCompatActivity {
    Button profileButton;
    Button suggestionsButton;
    Button chatButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileButton = (Button) findViewById(R.id.profileButton);
        suggestionsButton = (Button) findViewById(R.id.suggestionsButton);
        chatButton = (Button) findViewById(R.id.chatButton);
        profileButton.setOnClickListener(menuListener);
    }

    public View.OnClickListener menuListener;


}
