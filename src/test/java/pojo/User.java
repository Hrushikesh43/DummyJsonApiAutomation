package pojo;

public class User {
    private String name;
    private String job;

    // Constructor
    public User(String name, String job) {
        this.name = name;
        this.job = job;
    }

    // Getters and Setters (Required for Jackson)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
