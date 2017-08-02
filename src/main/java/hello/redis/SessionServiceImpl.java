package hello.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SessionServiceImpl implements SessionService {

    private SessionRepository sessionRepository;

    @Autowired
    public SessionServiceImpl(SessionRepository sessionRepository){
        this.sessionRepository=sessionRepository;
    }

    @Override
    public List<Session> listAll() {
        List<Session> sessions=new ArrayList<>();
        sessionRepository.findAll().forEach(sessions::add);
        return sessions;
    }

    @Override
    public Session getById(String id) {
        return sessionRepository.findOne(id);
    }

    @Override
    public Session getByEmail(String email) {
        return sessionRepository.findByEmail(email);
    }

    @Override
    public Session getByToken(String token) {
        return sessionRepository.findByToken(token);
    }

    @Override
    public Session saveOrUpdate(Session session) {
        sessionRepository.save(session);
        return session;
    }

    @Override
    public void delete(String id) {
        sessionRepository.delete(id);

    }

    @Override
    public void deleteAll() {
        sessionRepository.deleteAll();
    }
}
