package hello.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Task {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int task_id;

    @NotNull
    private int creator_user_id;

    @NotNull
    @Size(max=45)
    private String name;

    @NotNull
    private int responsible_user_id;

    @Size(max=145)
    private String description;

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public int getCreator_user_id() {
        return creator_user_id;
    }

    public void setCreator_user_id(int creator_user_id) {
        this.creator_user_id = creator_user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getResponsible_user_id() {
        return responsible_user_id;
    }

    public void setResponsible_user_id(int responsible_user_id) {
        this.responsible_user_id = responsible_user_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
