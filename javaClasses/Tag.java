
public class Tag {
    private String tag;
    private int popularity;
    
    @Override
    public boolean equals(Object o) {
        if (o instanceof Tag)
            return tag.equals(((Tag) o).tag);
        else if (o instanceof String)
            return this.tag.equals(o);
        else
            return super.equals(o);
    }
}
