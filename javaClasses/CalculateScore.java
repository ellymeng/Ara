
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.TreeSet;

import org.json.JSONObject;
import com.google.firebase.database;

public class CalculateScore {
    public static void main(String[] args) {
        String urlUpdated = "https://hoyahacks-96238.firebaseio.com/updated_users.json";
        String urlAll = "https://hoyahacks-96238.firebaseio.com/users.json";
        String urlSuggested= "https://hoyahacks-96238.firebaseio.com/suggestions.json";
        
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("server/saving-data/fireblog");
//        while (true) {
            try {
                JSONObject updatedUsers=JsonReader.readJsonFromUrl(urlUpdated);
                // only continues if there are updated users. otherwise go to catch
                
                JSONObject allUsers=JsonReader.readJsonFromUrl(urlAll);
                Iterator<String> keys=updatedUsers.keys();

                String username=keys.next();
                JSONObject userJson=allUsers.getJSONObject(username);
                User user=JSONToUser(userJson);
                
                Iterator<String> keysAll=allUsers.keys();
                
                JSONObject suggested=JsonReader.readJsonFromUrl(urlSuggested);
                
                // looking for matches
                while (keysAll.hasNext()) {
                    String username2=keys.next();
                    if (username.equals(username2)) // skip if self
                        continue;
                    
                    JSONObject otherUserJson=allUsers.getJSONObject(username2);
                    
                    if (!suggested.getJSONObject(username).has(username2))
                    
                    // compare years, look at mentor/mentee/peer status
                    if (((int) userJson.get("grad_year") < (int) otherUserJson.get("grad_year") &&
                            userJson.get("looking_for").toString().contains("Mentor") &&
                            otherUserJson.get("looking_for").toString().contains("Mentee")) ||
                            ((int) userJson.get("grad_year") > (int) otherUserJson.get("grad_year") &&
                            userJson.get("looking_for").toString().contains("Mentee") &&
                            otherUserJson.get("looking_for").toString().contains("Mentor")) ||
                            ((int) userJson.get("grad_year") == (int) otherUserJson.get("grad_year") &&
                            userJson.get("looking_for").toString().contains("Peer") &&
                            otherUserJson.get("looking_for").toString().contains("Peer"))) {
                        
                        int compatibility=score(user, JSONToUser(otherUserJson));
                        
                        Iterator<String> iter=suggested.getJSONObject(username2).keys();
                        String key=iter.next();
                        String minUser=key;
                        for (; iter.hasNext(); key=iter.next()) {
                            if ((int) suggested.getJSONObject(username2).get(key) < 
                                    (int) suggested.getJSONObject(username2).get(minUser))
                                minUser=key;
                        }
                        
                        if ((int) suggested.getJSONObject(username2).get(username) > 
                                (int) suggested.getJSONObject(username2).get(minUser)) {
                            if (suggested.getJSONObject(username2).length()>=6)
                        }
                    }
                }
                

            } catch (Exception e) {
            }
//        }
        
//        User p1=new User();
//        p1.setInfo("Doe", "Jenn", "jenn_doe", 2020, "CS");
//        p1.currCourses.add(new Course("COSC", 255, new Professor("Example", "Prof")));
//        p1.currCourses.add(new Course("COSC", 240, new Professor("World", "Hello")));
//        p1.fields.add(new Tag("data"));
//        p1.fields.add(new Tag("cyber"));
//        p1.fields.add(new Tag("who knows"));
//        p1.career.add(new Tag("software engineer"));
//        
//        User p2=new User();
//        p2.setInfo("Lam", "kdg;as", "yepitsme", 2019, "CS");
//        p2.currCourses.add(new Course("COSC", 255, new Professor("Example", "Prof")));
//        p2.currCourses.add(new Course("COSC", 240, new Professor("World", "Hello")));
//        p2.fields.add(new Tag("data"));
//        p2.career.add(new Tag("software engineer"));
//        
//        System.out.println(CalculateScore.score(p1, p2));
    }
    
    private static User JSONToUser(JSONObject json) {
        User user=new User();
        Iterator<String> iter;
        String key="";
        
        if (json.has("curr_courses")) {
            JSONObject curr_courses=json.getJSONObject("curr_courses");
            for (iter=curr_courses.keys(), key=iter.next(); iter.hasNext(); key=iter.next()) {
                user.currCourses.add(new Course(key, curr_courses.getJSONObject(key).get("prof").toString()));
            }
        }
        
        if (json.has("prev_courses")) {
            JSONObject prev_courses=json.getJSONObject("prev_courses");
            for (iter=prev_courses.keys(), key=iter.next(); iter.hasNext(); key=iter.next()) {
                user.prevCourses.add(new Course(key, prev_courses.getJSONObject(key).get("prof").toString()));
            }
        }
        
        if (json.has("specialty")) {
            JSONObject specialty=json.getJSONObject("specialty");
            for (iter=specialty.keys(), key=iter.next(); iter.hasNext(); key=iter.next()) {
                user.specialty.add(key);
            }
        }
        
        if (json.has("career")) {
            JSONObject career=json.getJSONObject("career");
            for (iter=career.keys(), key=iter.next(); iter.hasNext(); key=iter.next()) {
                user.career.add(key);
            }
        }
        
        if (json.has("hobbies")) {
            JSONObject hobbies=json.getJSONObject("hobbies");
            for (iter=hobbies.keys(), key=iter.next(); iter.hasNext(); key=iter.next()) {
                user.hobbies.add(key);
            }
        }
        
        return user;
    }
//    
    private static int numTagsInCommon(PriorityQueue<String> tagList1, PriorityQueue<String> tagList2) {
//        System.out.println(tagList1);
//        System.out.println(tagList2);
        TreeSet<String> intersection=new TreeSet<>();
        intersection.addAll(tagList1);
        intersection.retainAll(tagList2);
        
        return intersection.size();
    }
    
    private static double tagScore(PriorityQueue<String> tagList1, PriorityQueue<String> tagList2) {
        if (tagList1.size()==0 || tagList2.size()==0)
            return 0.0;
        
        double inCommon=(double) numTagsInCommon(tagList1, tagList2);
        
        return inCommon * (inCommon/(double) tagList1.size() + inCommon/(double) tagList2.size());
    }
    
    // calculate how much to take into account each tag category (hobbies is a little less)
    private static double totalTagScore(User p1, User p2) {
//        System.out.println(tagScore(p1.fields, p2.fields) + tagScore(p1.career, p2.career) 
//                + 0.4*tagScore(p1.hobbies, p2.hobbies));
        return tagScore(p1.specialty, p2.specialty) + tagScore(p1.career, p2.career) 
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
        return  commonCourses(p1.currCourses, p2.currCourses) +
                0.6*commonCourses(p1.currCourses, p2.prevCourses) +
                0.6*commonCourses(p1.prevCourses, p2.currCourses);
    }
    // 50 to 100 through sigmoid function
    public static int score(User p1, User p2) {
        double rawScore=totalTagScore(p1, p2) + totalCourseScore(p1, p2) + ((p1.major.equals(p2.major)) ? 2 : 0);
//        System.out.println(rawScore);
        
        double score=1.0/(1.0+Math.exp(-0.6*rawScore));
        
        return (int) Math.ceil(score*100);
    }
}
