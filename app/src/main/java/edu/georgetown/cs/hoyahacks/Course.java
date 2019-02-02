package edu.georgetown.cs.hoyahacks;

public class Course implements Comparable<Course> {
    private String dept;
    private int courseNum;
    public Professor prof;

    public Course(String dept, int courseNum, Professor prof) {
        this.dept = dept;
        this.courseNum = courseNum;
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
        if (!dept.equals(course.dept))
            return dept.compareTo(course.dept);
        else
            return (courseNum<course.courseNum) ? -1 : (courseNum>course.courseNum) ? 1 : 0;
        
    }
}
