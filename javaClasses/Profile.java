
import java.util.List;
import java.util.PriorityQueue;   

public class Profile {
    private String lastName;
    private String firstName;
    private String email;
    private int gradYear;
    private String major;
    public List<Course> currCourses;
    public List<Course> prevCourses;
    public PriorityQueue<Tag> fields;
    public PriorityQueue<Tag> career;
    public PriorityQueue<Tag> hobbies;
    
    public void setInfo(String lastName, String firstName, String email, int gradYear, String major) {
        this.lastName=lastName;
        this.firstName=firstName;
        this.email=email;
        this.gradYear=gradYear;
        this.major=major;
    }
}