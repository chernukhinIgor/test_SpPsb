package hello.redis;

import org.springframework.data.repository.CrudRepository;

public interface SessionRepository extends CrudRepository<Session, String> {
    Session findByEmail(String email);
    Session findByToken(String token);
}
