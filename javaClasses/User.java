
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;   

public class User {
//    private String lastName;
//    private String firstName;
//    private String username;
//    private int gradYear;
    public String major;
    public List<Course> currCourses;
    public List<Course> prevCourses;
    public PriorityQueue<String> specialty;
    public PriorityQueue<String> career;
    public PriorityQueue<String> hobbies;
    
    User() {
//        lastName=firstName=username=major="";
//        gradYear=0;
        major="";
        currCourses=new ArrayList<Course>();
        prevCourses=new ArrayList<Course>();
        specialty=new PriorityQueue<String>();
        career=new PriorityQueue<String>();
        hobbies=new PriorityQueue<String>();
    }
    
//    public void setInfo(String lastName, String firstName, String username, int gradYear, String major) {
//        this.lastName=lastName;
//        this.firstName=firstName;
//        this.username=username;
//        this.gradYear=gradYear;
//        this.major=major;
//    }
}