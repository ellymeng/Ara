
package edu.georgetown.cs.hoyahacks;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;   

public class User {
    private String lastName;
    private String firstName;
    private String username;
    private int gradYear;
    private String major;
    public List<Course> currCourses;
    public List<Course> prevCourses;
    public PriorityQueue<Tag> fields;
    public PriorityQueue<Tag> career;
    public PriorityQueue<Tag> hobbies;
    
    User() {
        lastName=firstName=username=major="";
        gradYear=0;
        currCourses=new ArrayList<Course>();
        prevCourses=new ArrayList<Course>();
        fields=new PriorityQueue<Tag>();
        career=new PriorityQueue<Tag>();
        hobbies=new PriorityQueue<Tag>();
    }
    
    public void setInfo(String lastName, String firstName, String username, int gradYear, String major) {
        this.lastName=lastName;
        this.firstName=firstName;
        this.username=username;
        this.gradYear=gradYear;
        this.major=major;
    }
}
