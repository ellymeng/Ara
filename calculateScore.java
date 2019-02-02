

public class Course {
    private String dept;
    private int courseNum;
    private Professor prof;

    public Course(String dept, int courseNum, Professor prof, boolean sem) {
        this.dept = dept;
        this.courseNum = courseNum;
        this.prof = prof;
    }

    // -1 if diff course (dept and courseNum)
    // 0 if everything equal
    // 1 if everything equal but prof
    public int similarity(Course other) {
        if (!dept.equals(other.dept) || courseNum!=other.courseNum)
            return -1;
        if (!prof.equals(other.prof))
            return 1;
        return 0;
    }
}

public Professor {
    private String lastName;
    private String firstName;

    public Professor(String lastName, String firstName) {
        this.lastName=lastName;
        this.firstName=firstName;
    }

    public Professor(String lastName) {
        Professor(lastName, "");
    }

    public boolean equals(Professor other) {
        if (firstName.isEmpty() || other.firstName.isEmpty())
            return lastName.equals(other.lastName);
        return lastName.equals(other.lastName) && firstName.equals(other.firstName);
    }
}

public class Profile {
    private String lastName;
    private String firstName;
    private int gradYear;
    private String major;
    private List<Course> currCourses;
    private List<Course> prevCourses;
    private List<String> fields;
    private List<String> career;
    private List<String> hobbies;
    
    public void setInfo(String lastName, String firstName, int gradYear, String major) {
        this.lastName=lastName;
        this.firstName=firstName;
        this.gradYear=gradYear;
        this.major=major;
    }
    
    public void addCurrCourses(List<Course> currCourses) {
        this.currCourses.addAll(currCourses);
    }
    
    public void addPrevCourses(List<Course>)
}

public class CalculateScore {
    private int numTagsInCommon(List<String> tagList1, List<String> tagList2) {
        List<String> intersection=new ArrayList<>();
        intersection.addAll(tagList1);
        intersection.retainAll(tagList2);
        return intersection.size();
    }
    
    private double tagScore(List<String> tagList1, List<String> tagList2) {
        double inCommon=(double) numTagsInCommon(tagList1, tagList2);
        
        return inCommon * (inCommon/(double) tagList1.size() + inCommon/(double) tagList2.size());
    }
    
    private double totalTagScore(Profile p1, Profile p2) {
        return tagScore(p1.fields, p2.fields) + tagScore(p1.career, p2.career) 
                + 0.4*tagScore(p1.hobbies, p2.hobbies);
    }
    
    private double 
    // 0 to 40 linear
    // how well p1 is a mentor to p2
    public double mentorScore(Profile p1, Profile p2) {
        if 
    }
    
    // 60 to 100 log scaled
    public double transform(int input) {
        
    }
}
