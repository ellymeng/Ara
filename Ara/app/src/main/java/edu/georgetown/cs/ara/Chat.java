package edu.georgetown.cs.ara;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;
import java.util.Map;


public class Chat extends AppCompatActivity {
    LinearLayout layout;
    RelativeLayout layout_2;
    ImageView sendButton;
    EditText messageArea;
    ScrollView scrollView;
    Firebase reference1, reference2;
    Button profileButton;
    Button suggestionsButton;
    Button chatButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        layout = (LinearLayout) findViewById(R.id.layout1);
        layout_2 = (RelativeLayout)findViewById(R.id.layout2);
        sendButton = (ImageView)findViewById(R.id.sendButton);
        messageArea = (EditText)findViewById(R.id.messageArea);
        scrollView = (ScrollView)findViewById(R.id.scrollView);

        Firebase.setAndroidContext(this);
        reference1 = new Firebase("https://hoyahacks-96238.firebaseio.com/messages/" + UserDetails.username + "_" + UserDetails.chatWith);
        reference2 = new Firebase("https://hoyahacks-96238.firebaseio.com/messages/" + UserDetails.chatWith + "_" + UserDetails.username);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        sendButton.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER))
                {
                    sendMessage();
                }
                return false;
            }
        });

        reference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map map = dataSnapshot.getValue(Map.class);
                String message = map.get("message").toString();
                String userName = map.get("user").toString();

                if(userName.equals(UserDetails.username)){
                    addMessage("You:\n" + message, 1);
                }
                else{
                    addMessage(UserDetails.chatWith + ":\n" + message, 2);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        profileButton = (Button) findViewById(R.id.profileButton);
        suggestionsButton = (Button) findViewById(R.id.suggestionsButton);
        chatButton = (Button) findViewById(R.id.chatButton);
        profileButton.setOnClickListener(menuListener);
        suggestionsButton.setOnClickListener(menuListener);
        chatButton.setOnClickListener(menuListener);
    }

    public void sendMessage() {
        String messageText = messageArea.getText().toString();

        if (!messageText.equals("")) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("message", messageText);
            map.put("user", UserDetails.username);
            reference1.push().setValue(map);
            reference2.push().setValue(map);
            messageArea.setText("");

            Firebase openChatsRef = new Firebase("https://hoyahacks-96238.firebaseio.com/open_chats");
            Map<String, Object> openChatsUpdate = new HashMap<>();
            openChatsUpdate.put(UserDetails.username + "/" + UserDetails.chatWith, messageText);
            openChatsRef.updateChildren(openChatsUpdate);
        }
    };

    public void addMessage(String message, int type){
        TextView textView = new TextView(Chat.this);
        textView.setText(message);

        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.weight = 1.0f;

        if(type == 1) {
            lp2.gravity = Gravity.LEFT;
        }
        else{
            lp2.gravity = Gravity.RIGHT;
        }
        textView.setLayoutParams(lp2);
        layout.addView(textView);
        scrollView.fullScroll(View.FOCUS_DOWN);
    }

    public View.OnClickListener menuListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (v == profileButton) {
                startActivity(new Intent(Chat.this, Profile.class));
            }
            else if (v == suggestionsButton) {
                startActivity(new Intent(Chat.this, Suggestions.class));
            }
            else if (v == chatButton) {
                startActivity(new Intent(Chat.this, OpenChats.class));
            }
        }
    };
}