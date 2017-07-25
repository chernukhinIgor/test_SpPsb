package hello.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Task {

    public enum statusStates{
        accepted,
        canceled,
        finished,
        created
    }

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

    @Column(name="status")
    private statusStates status;

    public statusStates getStatus() {
        return status;
    }

    public void setStatus(statusStates status) {
        this.status = status;
    }

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
