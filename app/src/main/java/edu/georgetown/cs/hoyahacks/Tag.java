package edu.georgetown.cs.hoyahacks;

public class Tag implements Comparable<Tag> {
    private String tag;
    private int popularity;
    
    public Tag(String tag) {
        this.tag=tag.toLowerCase();
        popularity=0;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o instanceof Tag)
            return tag.equals(((Tag) o).tag);
        else if (o instanceof String)
            return this.tag.equals(((String) o).toLowerCase());
        else
            return super.equals(o);
    }

    @Override
    public int compareTo(Tag tag) {
        return popularity-tag.popularity;
    }
}
