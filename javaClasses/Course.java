

public class Course implements Comparable<Course> {
    private String courseName;
    public String prof;

    public Course(String courseName, String prof) {
        this.courseName=courseName;
        this.prof = prof;
    }

//    // -1 if diff course (dept and courseNum)
//    // 0 if everything equal
//    // 1 if everything equal but prof
//    public int similarity(Course other) {
//        if (!dept.equals(other.dept) || courseNum!=other.courseNum)
//            return -1;
//        if (!prof.equals(other.prof))
//            return 1;
//        return 0;
//    }
    
//    @Override
//    public boolean equals(Object o) {
//        if (o instanceof Course) {
//            Course course=(Course) o;
//            return dept.equals(course.dept) && courseNum==course.courseNum;
//        } else 
//            return super.equals(o);
//    }

    @Override
    public int compareTo(Course course) {
        return courseName.compareTo(course.courseName);        
    }
}