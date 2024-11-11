package Job_scheduling_coursework;

public class Job {
    private String jobID;
    private String status;
    private LinkedList dependencies;

    public Job(String jobID) {
        this.jobID = jobID;
        this.status = "Not Complete";
        this.dependencies = new LinkedList();
    }

    public String getJobID() {
        return jobID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LinkedList getDependencies() {
        return dependencies;
    }
}

