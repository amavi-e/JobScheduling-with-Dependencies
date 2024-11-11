package Job_scheduling_coursework;

import java.util.ArrayList;
import java.util.Scanner;

public class UserInputs {

    public static boolean containsJobID(ArrayList<Job> jobList, String jobID) {
        for (int i = 0; i < jobList.size(); i++) {
            Job job = jobList.get(i);
            if (job.getJobID().equals(jobID)) {
                return true;
            }
        }
        return false;
    }

    public static ArrayList<Job> userInput() {
        Scanner input = new Scanner(System.in);
        int numberOfJobs = 0;
        ArrayList<Job> jobList = new ArrayList<>();

        while (true) {
            try {
                System.out.println("How many jobs do you want to add?");
                numberOfJobs = input.nextInt();
                input.nextLine();
                if (numberOfJobs <= 0) {
                    System.out.println("You have to enter a positive value for the number of jobs.");
                } else {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Invalid number. You have to enter an integer.");
                input.nextLine();
            }
        }

        for (int i = 0; i < numberOfJobs; i++) {
            while (true) {
                System.out.println("Please enter the " + (i + 1) + " job ID:");
                String jobID = input.nextLine();

                boolean exists = containsJobID(jobList, jobID);

                if (!exists) {
                    Job job = new Job(jobID);
                    jobList.add(job);
                    break;
                } else {
                    System.out.println("This ID already exists, please enter a new ID.");
                }
            }
        }

        while (true) {
            boolean atLeastOneNoDependency = false;

            for (int i = 0; i < jobList.size(); i++) {
                Job job = jobList.get(i);
                job.getDependencies().clear();
            }

            System.out.println("Now you have to state the dependencies of some of the jobs. Note that it is mandatory for at least one job to not have a dependency.");
            for (int i = 0; i < jobList.size(); i++) {
                Job job = jobList.get(i);
                while (true) {
                    System.out.println("How many dependencies does the job " + job.getJobID() + " have? If there are no dependencies please enter 0");
                    int numberOfDependencies;
                    while (true) {
                        try {
                            numberOfDependencies = input.nextInt();
                            input.nextLine();
                            if (numberOfDependencies < 0) {
                                System.out.println("Please enter a number greater than 0");
                            }
                            else if (numberOfDependencies >= numberOfJobs) {
                                System.out.println("The number of dependencies a job can have has to be less than the number of jobs.");
                            }
                            else {
                                break;
                            }
                        } catch (Exception e) {
                            System.out.println("Invalid number. You have to enter an integer.");
                            input.nextLine();
                        }
                    }

                    if (numberOfDependencies == 0) {
                        atLeastOneNoDependency = true;
                        break;
                    }

                    ArrayList<String> dependencies = new ArrayList<>();
                    for (int j = 0; j < numberOfDependencies; j++) {
                        while (true) {
                            System.out.println("Enter dependency " + (j + 1) + " for job " + job.getJobID() + ":");
                            String dependency = input.nextLine();

                            if (!dependency.isEmpty() && containsJobID(jobList, dependency)) {
                                dependencies.add(dependency);
                                break;
                            } else {
                                System.out.println("Invalid dependency. Please enter again.");
                            }
                        }
                    }

                    job.getDependencies().addDependencies(dependencies);
                    break;
                }
            }

            if (atLeastOneNoDependency) {
                break;
            } else {
                System.out.println("At least one job must have no dependency. Please enter the dependencies again.");
            }
        }

        return jobList;
    }
}
