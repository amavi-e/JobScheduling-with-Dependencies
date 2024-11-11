package Job_scheduling_coursework;

public class Node {
    String jobID;  // variable to hold the job ID
    Node next;     // reference pointing to the next node (dependency)

    public Node(String jobID) {
        this.jobID = jobID;  // assigning job ID to the node
        this.next = null;    // default pointer is null
    }
}
