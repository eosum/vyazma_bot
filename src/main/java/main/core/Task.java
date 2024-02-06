package main.core;

import java.util.Date;
public class Task {
    private Long serviceId;
    private String problemDescription;
    private Date chosenTime;
    private Date creationTime;

    public Task(Long serviceId, String problemDescription, Date chosenTime, Date creationTime) {
        this.serviceId = serviceId;
        this.problemDescription = problemDescription;
        this.chosenTime = chosenTime;
        this.creationTime = creationTime;
    }

    @Override
    public String toString() {
        return "serviceId = " + serviceId +
                "\n problemDescription='" + problemDescription +
                "\n chosenTime = " + chosenTime +
                "\n creationTime = " + creationTime;
    }
}
