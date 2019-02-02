package edu.georgetown.cs.hoyahacks;

import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.TreeSet;

public class CalculateScore {
    public static void main(String[] args) {
        User p1=new User();
        p1.setInfo("Doe", "Jenn", "jenn_doe", 2020, "CS");
        p1.currCourses.add(new Course("COSC", 255, new Professor("Example", "Prof")));
        p1.currCourses.add(new Course("COSC", 240, new Professor("World", "Hello")));
        p1.fields.add(new Tag("data"));
        p1.fields.add(new Tag("cyber"));
        p1.fields.add(new Tag("who knows"));
        p1.career.add(new Tag("software engineer"));
        
        User p2=new User();
        p2.setInfo("Lam", "kdg;as", "yepitsme", 2019, "CS");
        p2.currCourses.add(new Course("COSC", 255, new Professor("Example", "Prof")));
        p2.currCourses.add(new Course("COSC", 240, new Professor("World", "Hello")));
        p2.fields.add(new Tag("data"));
        p2.career.add(new Tag("software engineer"));
        
        System.out.println(CalculateScore.score(p1, p2));
    }
    
    private static int numTagsInCommon(PriorityQueue<Tag> tagList1, PriorityQueue<Tag> tagList2) {
//        System.out.println(tagList1);
//        System.out.println(tagList2);
        TreeSet<Tag> intersection=new TreeSet<>();
        intersection.addAll(tagList1);
        intersection.retainAll(tagList2);
        
        return intersection.size();
    }
    
    private static double tagScore(PriorityQueue<Tag> tagList1, PriorityQueue<Tag> tagList2) {
        if (tagList1.size()==0 || tagList2.size()==0)
            return 0.0;
        
        double inCommon=(double) numTagsInCommon(tagList1, tagList2);
        
        return inCommon * (inCommon/(double) tagList1.size() + inCommon/(double) tagList2.size());
    }
    
    // calculate how much to take into account each tag category (hobbies is a little less)
    private static double totalTagScore(User p1, User p2) {
//        System.out.println(tagScore(p1.fields, p2.fields) + tagScore(p1.career, p2.career) 
//                + 0.4*tagScore(p1.hobbies, p2.hobbies));
        return tagScore(p1.fields, p2.fields) + tagScore(p1.career, p2.career) 
                + 0.4*tagScore(p1.hobbies, p2.hobbies);
    }
    
    // same course diff prof counts half as much as same course same prof
    private static double commonCourses(List<Course> courseList1, List<Course> courseList2) {
        int iter1=0, iter2=0, sum=0;
        
        Collections.sort(courseList1);
        Collections.sort(courseList2);
//        System.out.println("breakpoint");
        
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
                    iter1+=1;
                    iter2+=2;
                    if (course1.prof.equals(course2.prof))
                        sum+=1;
                    else
                        sum+=0.5;
            }
        }
//        System.out.println("Sum: " + sum);
        
        return sum;
    }
    
    private static double totalCourseScore(User p1, User p2) {
        return  1.5*commonCourses(p1.currCourses, p2.currCourses) +
                commonCourses(p1.currCourses, p2.prevCourses) +
                commonCourses(p1.prevCourses, p2.currCourses);
    }
    // 0.5 to 1 through sigmoid function
    public static double score(User p1, User p2) {
        double rawScore=totalTagScore(p1, p2) + totalCourseScore(p1, p2);
//        System.out.println(rawScore);
        
        double score=1.0/(1.0+Math.exp(-0.7*rawScore));
        
        return score;
    }
}
