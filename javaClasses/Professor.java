

public class Professor {
    private String lastName;
    private String firstName;

    public Professor(String lastName, String firstName) {
        this.lastName=lastName;
        this.firstName=firstName;
    }

    public Professor(String lastName) {
        this(lastName, "");
    }

    public boolean equals(Professor other) {
        if (firstName.isEmpty() || other.firstName.isEmpty())
            return lastName.equals(other.lastName);
        return lastName.equals(other.lastName) && firstName.equals(other.firstName);
    }
}