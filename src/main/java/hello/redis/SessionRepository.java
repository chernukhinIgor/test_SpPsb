package hello.redis;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SessionRepository extends CrudRepository<Session, String> {
    Session findByEmail(String email);
    Session findByToken(String token);
}
