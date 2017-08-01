package hello.redis;

import java.util.List;

public interface SessionService {
    List<Session> listAll();
    Session getById(String id);
    Session getByEmail(String email);
    Session getByToken(String token);
    Session saveOrUpdate(Session session);
    void delete(String id);
    void deleteByEmail(String email);
    void deleteByToken(String token);
    void deleteAll();
}
