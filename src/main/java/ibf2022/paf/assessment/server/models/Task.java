package ibf2022.paf.assessment.server.models;

import java.sql.Date;

// TODO: Task 4

public class Task {

    private Integer taskId;
    private String userName;
    private String userId;
    private String description;
    private Integer priority;
    private Date dueDate;

    public Task() {
    }

    public Task(Integer taskId, String userName, String userId, String description, Integer priority, Date dueDate) {
        this.taskId = taskId;
        this.userName = userName;
        this.userId = userId;
        this.description = description;
        this.priority = priority;
        this.dueDate = dueDate;
    }

    public Integer getTaskId() {
        return this.taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPriority() {
        return this.priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Date getDueDate() {
        return this.dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return "{" +
            " taskId='" + getTaskId() + "'" +
            ", description='" + getDescription() + "'" +
            ", priority='" + getPriority() + "'" +
            ", dueDate='" + getDueDate() + "'" +
            "}";
    }

}
