package hello.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Task {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="task_id")
    private int taskId;

    @NotNull
    @Column(name="creator_user_id")
    private int creatorUserId;

    @NotNull
    @Size(max=45)
    @Column(name="name")
    private String name;

    @NotNull
    @Column(name="responsible_user_id")
    private int responsibleUserId;

    @Size(max=145)
    @Column(name="description")
    private String description;

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getCreatorUserId() {
        return creatorUserId;
    }

    public void setCreatorUserId(int creatorUserId) {
        this.creatorUserId = creatorUserId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getResponsibleUserId() {
        return responsibleUserId;
    }

    public void setResponsibleUserId(int responsibleUserId) {
        this.responsibleUserId = responsibleUserId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
