package Job_scheduling_coursework;

import java.util.ArrayList;
import java.util.Scanner;

public class JobScheduling {

    private int front;
    private int rear;
    private int maxSize;
    private String[] queueArray;

    public static ArrayList<Job> jobList = new ArrayList<>();

    public JobScheduling(int maxSize) {
        this.front = 0;
        this.rear = -1;
        this.maxSize = maxSize;
        this.queueArray = new String[maxSize];
    }

    public boolean isEmpty() {
        return (rear == -1);
    }

    public boolean isFull() {
        return (rear == maxSize - 1);
    }

    public void enqueue(String jobID) {
        if (isFull()) {
            System.out.println("The queue is full");
            return;
        }
        queueArray[++rear] = jobID;
    }

    public String dequeue() {
        if (isEmpty()) {
            System.out.println("The queue is empty, so cannot delete a value");
            return null;
        }
        String removedItem = queueArray[front];
        front++;
        if (front > rear) {
            front = 0;
            rear = -1;
        }
        return removedItem;
    }

    public boolean isDependencyEnqueued(String dependency) {
        if (dependency == null) {
            return true;
        }
        for (int i = 0; i <= rear; i++) {
            if (queueArray[i].equals(dependency)) {
                return true;
            }
        }
        return false;
    }

    public Job getJobByID(String jobID) {
        for (int i = 0; i < jobList.size(); i++) {
            Job job = jobList.get(i);
            if (job.getJobID().equals(jobID)) {
                return job;
            }
        }
        return null;
    }

    public ArrayList<Job> getExecutableJobs() {
        ArrayList<Job> executableJobs = new ArrayList<>();
        for (int i = 0; i < jobList.size(); i++) {
            Job job = jobList.get(i);
            if (job.getStatus().equals("Complete")) {
                continue;
            }
            boolean allDependenciesEnqueued = true;
            Node dependencyNode = job.getDependencies().getHead().next;
            while (dependencyNode != null) {
                if (dependencyNode.jobID != null && !isDependencyEnqueued(dependencyNode.jobID)) {
                    allDependenciesEnqueued = false;
                    break;
                }
                dependencyNode = dependencyNode.next;
            }
            if (allDependenciesEnqueued) {
                executableJobs.add(job);
            }
        }
        return executableJobs;
    }

    public void executeJobs() {
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.println("Which job do you want to execute next? Enter the job ID:");
            String jobID = input.next();
            Job job = getJobByID(jobID);

            if (job == null) {
                System.out.println("Invalid Job ID. Please try again.");
                continue;
            }

            if (job.getStatus().equals("Complete")) {
                System.out.println("Job ID: " + jobID + " has already been executed.");
                continue;
            }

            boolean allDependenciesEnqueued = true;

            Node dependencyNode = job.getDependencies().getHead().next;
            while (dependencyNode != null) {
                if (dependencyNode.jobID != null && !isDependencyEnqueued(dependencyNode.jobID)) {
                    allDependenciesEnqueued = false;
                    System.out.println("The dependency of this job (" + dependencyNode.jobID + ") is not in the queue. Please execute the dependent job first.");
                    break;
                }
                dependencyNode = dependencyNode.next;
            }

            if (!allDependenciesEnqueued) {
                continue;
            }

            enqueue(jobID);
            job.setStatus("Complete");
            System.out.println("Job ID: " + jobID + " has been successfully executed and added to the queue");

            boolean allComplete = true;
            for (int i = 0; i < jobList.size(); i++) {
                Job j = jobList.get(i);
                if (!j.getStatus().equals("Complete")) {
                    allComplete = false;
                    break;
                }
            }

            if (allComplete) {
                System.out.println("All jobs have been successfully executed.");
                break;
            }

            StringBuilder executedJobs = new StringBuilder();
            for (int i = 0; i <= rear; i++) {
                executedJobs.append(queueArray[i]);
                if (i < rear) {
                    executedJobs.append(", ");
                }
            }
            System.out.println("The current complete jobs in their executed order: " + executedJobs);


            ArrayList<Job> executableJobs = getExecutableJobs();
            if (!executableJobs.isEmpty()) {
                System.out.println("The next executable jobs are:");
                for (int i = 0; i < executableJobs.size(); i++) {
                    System.out.println(executableJobs.get(i).getJobID());
                }
            }
        }
    }

    public static void main(String[] args) {
        UserInputs inputs = new UserInputs();
        jobList = inputs.userInput();

        JobScheduling scheduler = new JobScheduling(jobList.size());
        scheduler.executeJobs();
    }
}
