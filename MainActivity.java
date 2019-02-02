  package com.example.hoyahacks19;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


/*
TO DO
- add pills for each class (class name, number, professor, semester taken)
-  multiple-select drop down for interested in (mentor, mentee, peer)
- multiple-select drop down menus for interested career fields (input if doensn't exist)
-  multiple-select drop down menu for ideal job (input if doensn't exist)

*/

public class MainActivity extends AppCompatActivity {

    ImageView propicImage;
    EditText nameInput;
    Spinner yearDropdown;
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

    String name;
    public static String year;
    String email;
    String hometown;
    String hobbies;
    String curMajorCode;
    Integer curCourseNum;
    Integer currentCourseRows = 0;
    String pastMajorCode;
    Integer pastCourseNum;
    Integer pastCourseRows = 10; // views with ID >= 10 are past courses


    // [ [courseCode, courseNum][]...]
    ArrayList<ArrayList<String>> currentCoursesList = new ArrayList();
    ArrayList<ArrayList<String>> pastCoursesList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        nameInput.addTextChangedListener(nameInputListener);
        emailInput.addTextChangedListener(emailInputListener);
        hometownInput.addTextChangedListener(hometownInputListener);
        hobbiesInput.addTextChangedListener(hobbiesInputListener);
        majorInput.addTextChangedListener(majorInputListener);
        courseNumInput.addTextChangedListener(courseNumInputListener);
        addButton.setOnClickListener(addButtonListener);
        majorInput2.addTextChangedListener(majorInputListener2);
        courseNumInput2.addTextChangedListener(courseNumInputListener2);
        addButton2.setOnClickListener(addButtonListener2);

        String[] yearItems = new String[] { "Freshman,", "Sophomore", "Junior", "Senior"};
        // adapters decide how items are displayed
        ArrayAdapter<CharSequence> yearAdapter = ArrayAdapter.createFromResource(this,
                R.array.year_array, android.R.layout.simple_spinner_item);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearDropdown.setAdapter(yearAdapter);

        yearDropdown.setOnItemSelectedListener(new OnYearItemSelected());

    }

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

    private TextWatcher majorInputListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            curMajorCode = s.toString();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private TextWatcher courseNumInputListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            curCourseNum = Integer.parseInt(s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private View.OnClickListener addButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            // save input to currentCoursesList
            String code = majorInput.getText().toString();
            Integer num = Integer.parseInt(courseNumInput.getText().toString());
            ArrayList<String> newCourseEntry = new ArrayList();
            newCourseEntry.add(code);
            newCourseEntry.add(num.toString());
            currentCoursesList.add(newCourseEntry);
            majorInput.setText("");
            courseNumInput.setText("");
            currentCourseRows++;

            // create new TableRow
//            TableRow newRow = new TableRow(MainActivity.this);
//            EditText newMajorCode = new EditText(MainActivity.this);
//            newMajorCode.setId(currentCourseRows);
//            majorInput.setHint("Major code (eg, COSC");
//            EditText newCourseNum = new EditText(MainActivity.this);
//            courseNumInput.setHint("Course # (eg, 255)");
//            courseNumInput.setText("New course code");
//            addButton = new Button(MainActivity.this);
//            addButton.setText("ADD");
//            newRow.addView(EditText("newrow"));
//            newRow.addView(courseNumInput);
//            newRow.addView(addButton);

//            newRow.addView(createNewAddButton());
            //add new TableRow to courseTableLayout:
//            courseTable.addView(newRow);

        }
    };

    private TextWatcher majorInputListener2 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            pastMajorCode = s.toString();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private TextWatcher courseNumInputListener2 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            pastCourseNum = Integer.parseInt(s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private View.OnClickListener addButtonListener2 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            // save input to currentCoursesList
            String code = majorInput.getText().toString();
            Integer num = Integer.parseInt(courseNumInput.getText().toString());
            ArrayList<String> newCourseEntry = new ArrayList();
            newCourseEntry.add(code);
            newCourseEntry.add(num.toString());
            pastCoursesList.add(newCourseEntry);
            majorInput.setText("");
            courseNumInput.setText("");
            pastCourseRows++;
        }
    };

//    Bitmoji.fetchAvatarUrl(context, new FetchAvatarUrlCallback() {
//        @Override
//        public void onSuccess(@Nullable String avatarUrl) {
//            // do something
//        }
//
//        @Override
//        public void onFailure(boolean isNetworkError, int statusCode) {
//            // do something
//        }
//    });
}
