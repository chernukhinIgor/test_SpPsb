package hello.repository;

import hello.model.Task;
import hello.model.User;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository  extends CrudRepository<Task, Long> {

}
