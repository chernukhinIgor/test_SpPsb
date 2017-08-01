package hello.controller;

import hello.redis.Session;
import hello.redis.SessionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisServiceTest {
    @Autowired
    SessionService sessionService;

    @Test
    public void createExistsDeleteSession() throws Exception{

        String testEmail="testEmail";
        String testToken="testToken";

        Session testSession=new Session();
        testSession.setEmail(testEmail);
        testSession.setToken(testToken);

        sessionService.saveOrUpdate(testSession);

        assertNotNull(sessionService.getByEmail(testEmail));
        assertNotNull(sessionService.getByToken(testToken));

        testSession=sessionService.getByEmail(testEmail);
        sessionService.delete(testSession.getId());

        assertNull(sessionService.getByEmail(testEmail));
        assertNull(sessionService.getByToken(testToken));

    }
}
