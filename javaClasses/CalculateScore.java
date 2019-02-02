
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.TreeSet;

public class CalculateScore {
    private static int numTagsInCommon(PriorityQueue<Tag> tagList1, PriorityQueue<Tag> tagList2) {
        TreeSet<Tag> intersection=new TreeSet<>();
        intersection.addAll(tagList1);
        intersection.retainAll(tagList2);
        
        return intersection.size();
    }
    
    private static double tagScore(PriorityQueue<Tag> tagList1, PriorityQueue<Tag> tagList2) {
        double inCommon=(double) numTagsInCommon(tagList1, tagList2);
        
        return inCommon * (inCommon/(double) tagList1.size() + inCommon/(double) tagList2.size());
    }
    
    // calculate how much to take into account each tag category (hobbies is a little less)
    private static double totalTagScore(Profile p1, Profile p2) {
        return tagScore(p1.fields, p2.fields) + tagScore(p1.career, p2.career) 
                + 0.4*tagScore(p1.hobbies, p2.hobbies);
    }
    
    // same course diff prof counts half as much as same course same prof
    private static double commonCourses(List<Course> courseList1, List<Course> courseList2) {
        int iter1=0, iter2=0, sum=0;
        
        Collections.sort(courseList1);
        Collections.sort(courseList2);
        
        // find intersection of 2 sorted lists
        while (iter1<courseList1.size() && iter2<courseList2.size()) {
            switch (courseList1.get(iter1).compareTo(courseList2.get(iter2))) {
                case -1:
                    iter1+=1;
                    break;
                case 1:
                    iter2+=1;
                    break;
                case 0:
                    Course course1=courseList1.get(iter1);
                    Course course2=courseList2.get(iter2);
                    if (course1.prof.equals(course2.prof))
                        sum+=1;
                    else
                        sum+=0.5;
            }
        }
        
        return sum;
    }
    
    private static double totalCourseScore(Profile p1, Profile p2) {
        return  1.5*commonCourses(p1.currCourses, p2.currCourses) +
                commonCourses(p1.currCourses, p2.prevCourses) +
                commonCourses(p1.prevCourses, p2.currCourses);
    }
    // 0.5 to 1 through sigmoid function
    public static double score(Profile p1, Profile p2) {
        double rawScore=totalTagScore(p1, p2) + totalCourseScore(p1, p2);
        
        double score=1.0/(1.0+Math.exp(-0.5*rawScore));
        
        return score;
    }
}
