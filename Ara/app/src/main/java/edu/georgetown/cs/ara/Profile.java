package edu.georgetown.cs.ara;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.client.Firebase;
import com.google.firebase.FirebaseApp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Profile extends AppCompatActivity {
    Button profileButton;
    Button suggestionsButton;
    Button chatButton;
    //TextView profile;

    // begin elly
    ImageView propicImage;
    EditText nameInput;
    Spinner yearDropdown;
    Spinner majorDropdown;
    EditText emailInput;
    EditText hometownInput;
    EditText hobbiesInput;
    EditText majorInput;
    EditText courseNumInput;
    Button addButton;
    EditText majorInput2;
    EditText courseNumInput2;
    Button addButton2;
    TableLayout courseTable;

    Integer currentCourseRows = 0;
    Integer pastCourseRows = 10; // views with ID >= 10 are past course

    // mentor/mentee stuff
    Button mentorButton;
    //    TextView mentorDebug; // for debugging
    String[] mentorListItems;
    boolean[] mentorCheckedItems;
    ArrayList<Integer> mentorSelectedList = new ArrayList<>();

    // specialties stuff
    Button specialtyButton;
    //    TextView specialtyDebug;
    String[] specialtyListItems;
    boolean[] specialtyCheckedItems;
    ArrayList<Integer> specialtySelectedList = new ArrayList<>();

    // jobs stuff
    Button jobButton;
    TextView jobDebug;
    String[] jobListItems;
    boolean[] jobCheckedItems;
    ArrayList<Integer> jobSelectedList = new ArrayList<>();

    String name; // done
    public static Integer year;
    public static String major;
    String hometown; // done
    String email; // done
    String mentorSelectedFinal; // done
    ArrayList<String> currentCoursesList = new ArrayList<>(); // done
    ArrayList<String> pastCoursesList = new ArrayList<>(); // done
    ArrayList<String> specialtiesFinal = new ArrayList<>(); // done
    ArrayList<String> jobsFinal = new ArrayList<>(); // to do
    String hobbies; // done
    // end elly

    // firebase stuff
    String user;
    Button submitButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Firebase.setAndroidContext(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileButton = (Button) findViewById(R.id.profileButton);
        suggestionsButton = (Button) findViewById(R.id.suggestionsButton);
        chatButton = (Button) findViewById(R.id.chatButton);
        profileButton.setOnClickListener(menuListener);
        suggestionsButton.setOnClickListener(menuListener);
        chatButton.setOnClickListener(menuListener);

        //profile = (TextView) findViewById(R.id.profile);

        // begin elly
        propicImage = findViewById(R.id.propicImage);
        nameInput = findViewById(R.id.nameInput);
        emailInput = findViewById(R.id.emailInput);
        hometownInput = findViewById(R.id.hometownInput);
        hobbiesInput = findViewById(R.id.hobbiesInput);
        majorInput = findViewById(R.id.majorInput);
        courseNumInput = findViewById(R.id.courseNumInput);
        addButton = findViewById(R.id.addCourseButton);
        courseTable = findViewById(R.id.courseTableLayout);
        majorInput2 = findViewById(R.id.majorInput2);
        courseNumInput2 = findViewById(R.id.courseNumInput2);
        addButton2 = findViewById(R.id.addCourseButton2);
        yearDropdown = findViewById(R.id.yearSpinner);
        majorDropdown = findViewById(R.id.majorSpinner);

        mentorButton = findViewById(R.id.mentorButton);
//        mentorDebug = findViewById(R.id.mentorDebug);
        specialtyButton = findViewById(R.id.specialtyButton);
//        specialtyDebug = findViewById(R.id.specialtyDebug);
        jobButton = findViewById(R.id.jobButton);
//        jobDebug = findViewById(R.id.jobsDebug);

//        yearDebug = findViewById(R.id.yearDebug);
//        majorDebug = findViewById(R.id.majorDebug);

        submitButton = findViewById(R.id.submitButton);

        nameInput.addTextChangedListener(nameInputListener);
        emailInput.addTextChangedListener(emailInputListener);
        hometownInput.addTextChangedListener(hometownInputListener);
        hobbiesInput.addTextChangedListener(hobbiesInputListener);
        addButton.setOnClickListener(addButtonListener);
        addButton2.setOnClickListener(addButtonListener2);

        // firebase stuff ////////////////////////////////////////////////////////////////////////////////

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){ // send all user fields to FireBase

                String url = "https://hoyahacks-96238.firebaseio.com/users.json";

                StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Firebase reference = new Firebase("https://hoyahacks-96238.firebaseio.com/users");


                        try {

                            JSONObject obj = new JSONObject(s);

                            String user = UserDetails.username;

                            // add new user to updated users
                            reference.child("updated_users").child(user).setValue(true);

                            Map<String, Boolean> currCoursesMap = new HashMap<>();
                            for (String course : currentCoursesList) {
                                System.out.println("current courses:" + course + " ");
                                currCoursesMap.put(course, true);
                            }

                            Map<String, Boolean> pastCoursesMap = new HashMap<>();
                            for (String course : pastCoursesList) {
                                System.out.println("past courses:" + course + " ");
                                pastCoursesMap.put(course, true);
                            }

                            Map<String, Boolean> specialtiesMap = new HashMap<>();
                            for (String specialty : specialtiesFinal) {
                                System.out.println("specialties:" + specialty + " ");
                                specialtiesMap.put(specialty, true);
                            }

                            Map<String, Boolean> jobsMap = new HashMap<>();
                            for (String job : jobsFinal) {
                                System.out.println("dream jobs:" + job + " ");
                                jobsMap.put(job, true);
                            }

                            reference.child(user).child("full_name").setValue(name);
                            System.out.println("name:" + name + " ");
                            reference.child(user).child("hometown").setValue(hometown);
                            System.out.println("hometown:" + hometown + " ");
                            reference.child(UserDetails.username).child("grad_year").setValue(year);
                            reference.child(UserDetails.username).child("major").setValue(major);
                            System.out.println("major: " + major + " ");
                            reference.child(user).child("email").setValue(email);
                            reference.child(UserDetails.username).child("looking_for").setValue(mentorSelectedFinal);
                            System.out.println("looking for:" + mentorSelectedFinal + " ");
                            reference.child(user).child("curr_courses").setValue(currCoursesMap);
                            reference.child(user).child("prev_courses").setValue(pastCoursesMap);
                            reference.child(user).child("specialty").setValue(specialtiesMap);
                            reference.child(user).child("career").setValue(jobsMap);
                            reference.child(user).child("hobbies").setValue(hobbies);


                            Toast.makeText(Profile.this, "Profile successfully created!", Toast.LENGTH_LONG).show();
//                                  startActivity(new Intent(Profile.this, Login.class)); // go to match page?

//                                  if (!obj.has(user)) {
//                                      reference.child(user).child("password").setValue(pass);
//                                      Toast.makeText(Profile.this, "registration successful", Toast.LENGTH_LONG).show();
//                                      startActivity(new Intent(Profile.this, Login.class));
//                                  } else {
//                                      Toast.makeText(Profile.this, "username already exists", Toast.LENGTH_LONG).show();
//                                  }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
//                          System.out.println("" + volleyError);
//                          pd.dismiss();
                    }
                });

                RequestQueue rQueue = Volley.newRequestQueue(Profile.this);
                rQueue.add(request);
            }
        });

        // year dropdown
        ArrayAdapter<CharSequence> yearAdapter = ArrayAdapter.createFromResource(this,
                R.array.year_array, android.R.layout.simple_spinner_item);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearDropdown.setAdapter(yearAdapter);
        yearDropdown.setOnItemSelectedListener(new OnYearItemSelected());

        // major dropdown
        ArrayAdapter<CharSequence> majorAdapter = ArrayAdapter.createFromResource(this,
                R.array.majors_array, android.R.layout.simple_spinner_item);
        majorAdapter.setDropDownViewResource(android.R.layout. simple_spinner_dropdown_item);
        majorDropdown.setAdapter(majorAdapter);
        majorDropdown.setOnItemSelectedListener(new OnMajorItemSelected());

        // mentor/mentee multi-select dropdown ///////////////////////////////////////////////////////
        mentorListItems = getResources().getStringArray(R.array.mentor_array);
        mentorCheckedItems = new boolean[mentorListItems.length];

        mentorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(Profile.this);
                mBuilder.setMultiChoiceItems(mentorListItems, mentorCheckedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                        // when item is checked, do this
                        if(isChecked) {
                            if(!mentorSelectedList.contains(position)) {
                                mentorSelectedList.add(position);
                            }
                        }
                        else if(mentorSelectedList.contains(position)) {
                            mentorSelectedList.remove(position);
                        }
                    }
                });

                mBuilder.setCancelable(true);

                // store all clicked options for "looking for"
                mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {

                        String item = "";
                        for (int i = 0; i < mentorSelectedList.size(); i++) {
                            // for debugging
//                            item = item + mentorListItems[mentorSelectedList.get(i)];

                            // store user input
                            mentorSelectedFinal = " " + mentorSelectedFinal + mentorListItems[mentorSelectedList.get(i)];
                            // debugging (prints commas correctly during debugging)
//                            if (i != mentorSelectedList.size() - 1) {
//                                item = item + ", ";
//                            }
                        }
//                        mentorDebug.setText(item);
                    }
                });

                mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                mBuilder.setNeutralButton("Clear all", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for (int i = 0; i < mentorCheckedItems.length; i++) {
                            mentorCheckedItems[i] = false;
                            mentorSelectedList.clear();
//                            mentorDebug.setText("");
                        }
                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });

        // specialty multi-select dropdown ///////////////////////////////////////////////////////
        specialtyListItems = getResources().getStringArray(R.array.specialties_array);
        specialtyCheckedItems = new boolean[specialtyListItems.length];

        specialtyButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder sBuilder = new AlertDialog.Builder(Profile.this);
                sBuilder.setMultiChoiceItems(specialtyListItems, specialtyCheckedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                        // when item is checked, do this
                        if(isChecked) {
                            if(!specialtySelectedList.contains(position)) {
                                specialtySelectedList.add(position);
                            }
                        }
                        else if(specialtySelectedList.contains(position)) {
                            specialtySelectedList.remove(position);
                        }
                    }
                });

                sBuilder.setCancelable(true);
//
//                // store all clicked options for "looking for"
                sBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {

                        String item = "";
                        for (int i = 0; i < specialtySelectedList.size(); i++) {
//                            // for debugging
//                            item = item + specialtyListItems[specialtySelectedList.get(i)];
//
//                            // store user input
                            String newItem = specialtyListItems[specialtySelectedList.get(i)];
                            System.out.println("new specialty: " + newItem);
                            specialtiesFinal.add(newItem);
//
//                            debugging (prints commas correctly during debugging)
//                            if (i != specialtySelectedList.size() - 1) {
//                                item = item + ", ";
//                            }
                        }
//                        specialtyDebug.setText(item);
                    }
                });

                sBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
//
                sBuilder.setNeutralButton("Clear all", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for (int i = 0; i < specialtyCheckedItems.length; i++) {
                            specialtyCheckedItems[i] = false;
                            specialtySelectedList.clear();
//                            specialtyDebug.setText("");
                        }
                    }
                });
//
                AlertDialog mDialog = sBuilder.create();
                mDialog.show();
            }
        });

        // jobs multi-select dropdown ///////////////////////////////////////////////////////
        jobListItems = getResources().getStringArray(R.array.jobs_array);
        jobCheckedItems = new boolean[jobListItems.length];

        jobButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder sBuilder = new AlertDialog.Builder(Profile.this);
                sBuilder.setMultiChoiceItems(jobListItems, jobCheckedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                        // when item is checked, do this
                        if(isChecked) {
                            if(!jobSelectedList.contains(position)) {
                                jobSelectedList.add(position);
                            }
                        }
                        else if(jobSelectedList.contains(position)) {
                            jobSelectedList.remove(position);
                        }
                    }
                });

                sBuilder.setCancelable(true);
//
//                // store all clicked options for "looking for"
                sBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {

                        String item = "";
                        for (int i = 0; i < jobSelectedList.size(); i++) {
//                            // for debugging
//                            item = item + jobListItems[jobSelectedList.get(i)];
//
//                            // store user input
                            String newItem = jobListItems[jobSelectedList.get(i)];
                            jobsFinal.add(newItem);
//
//                            debugging (prints commas correctly during debugging)
//                            if (i != jobSelectedList.size() - 1) {
//                                item = item + ", ";
//                            }
                        }
//                        jobDebug.setText(item);
                    }
                });

                sBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
//
                sBuilder.setNeutralButton("Clear all", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for (int i = 0; i < jobCheckedItems.length; i++) {
                            jobCheckedItems[i] = false;
                            jobSelectedList.clear();
                            jobDebug.setText("");
                        }
                    }
                });
//
                AlertDialog mDialog = sBuilder.create();
                mDialog.show();
            }
        }); // end elly

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


    private TextWatcher nameInputListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            name = s.toString();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private TextWatcher emailInputListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            email = s.toString();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private TextWatcher hometownInputListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            hometown = s.toString();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private TextWatcher hobbiesInputListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            hobbies = s.toString();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    private View.OnClickListener addButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            // save input to currentCoursesList
            String newCourse = majorInput.getText().toString();
            newCourse += courseNumInput.getText().toString();
            currentCoursesList.add(newCourse);
            majorInput.setText("");
            courseNumInput.setText("");
            currentCourseRows++;
        }
    };

    private View.OnClickListener addButtonListener2 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            // save input to pastCoursesList
            String newCourse = majorInput2.getText().toString();
            newCourse += courseNumInput2.getText().toString();
            pastCoursesList.add(newCourse);
            majorInput2.setText("");
            courseNumInput2.setText("");
            pastCourseRows++;
        }
    };

}
