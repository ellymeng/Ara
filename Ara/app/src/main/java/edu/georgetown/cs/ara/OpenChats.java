package edu.georgetown.cs.ara;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class OpenChats extends AppCompatActivity {
    Button profileButton;
    Button suggestionsButton;
    Button chatButton;
    ListView usersList;
    TextView noUsersText;
    ArrayList<String> al = new ArrayList<>();
    int totalUsers = 0;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_openchats);

        usersList = (ListView)findViewById(R.id.usersList);
        noUsersText = (TextView)findViewById(R.id.noUsersText);

        pd = new ProgressDialog(OpenChats.this);
        pd.setMessage("Loading...");
        pd.show();

        String url = "https://hoyahacks-96238.firebaseio.com/open_chats/" + UserDetails.username + ".json";

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                doOnSuccess(s);
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("" + volleyError);
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(OpenChats.this);
        rQueue.add(request);

        usersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String tappedOn = al.get(position);
                String[] arr = tappedOn.split("[:]");
                UserDetails.chatWith = arr[0];
                startActivity(new Intent(OpenChats.this, Chat.class));
            }
        });


        profileButton = (Button) findViewById(R.id.profileButton);
        suggestionsButton = (Button) findViewById(R.id.suggestionsButton);
        chatButton = (Button) findViewById(R.id.chatButton);
        profileButton.setOnClickListener(menuListener);
        suggestionsButton.setOnClickListener(menuListener);
        chatButton.setOnClickListener(menuListener);
    }

    public void doOnSuccess(String s){
        try {
            JSONObject obj = new JSONObject(s);

            Iterator i = obj.keys();
            String key = "";
            String val = "";

            while(i.hasNext()){
                key = i.next().toString();
                val = obj.getString(key);

                if(!key.equals(UserDetails.username)) {
                    al.add(key + ": " + val);
                }

                totalUsers++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(totalUsers <1){
            noUsersText.setVisibility(View.VISIBLE);
            usersList.setVisibility(View.GONE);
        }
        else{
            noUsersText.setVisibility(View.GONE);
            usersList.setVisibility(View.VISIBLE);
            usersList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, al));
        }

        pd.dismiss();
    }

    public View.OnClickListener menuListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (v == profileButton) {
                startActivity(new Intent(getApplicationContext(), Profile.class));
            }
            else if (v == suggestionsButton) {
                startActivity(new Intent(getApplicationContext(), Suggestions.class));
            }
        }
    };
}