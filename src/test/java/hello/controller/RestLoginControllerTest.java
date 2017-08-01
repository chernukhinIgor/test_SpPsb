package hello.controller;

import com.solidfire.gson.Gson;
import hello.model.LoginUser;
import hello.model.User;
import hello.redis.SessionService;
import hello.secure.service.TokenAuthenticationService;
import hello.service.UserService;
import hello.utils.ReplyCodes;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import net.sf.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RestLoginControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private TokenAuthenticationService authenticationService;

    @Autowired
    private UserService userService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    private JSONObject toJSONObject(String input) throws ParseException {
        JSONParser parser=new JSONParser();
        Object obj=parser.parse(input);
        JSONObject jsn = JSONObject.fromObject(obj);
        return jsn;
    }


    @Test
    public void wrongEmail() throws Exception{
        LoginUser loginUser=new LoginUser();
        loginUser.setEmail("mvdkbmelk");

        String json = new Gson().toJson(loginUser);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post("/login")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andReturn();

        JSONObject jsn = toJSONObject(result.getResponse().getContentAsString());

        // If result is fail
        assertEquals(false, jsn.get("success"));

        JSONObject data=JSONObject.fromObject(jsn.get("error"));

        // Checking code
        assertEquals(ReplyCodes.EMAIL_OR_PASSWORD_ERROR, data.get("code"));
    }

    @Test
    public void emailNotConfirmed() throws Exception{

        // CREATE USER
        User user = new User();
        user.setName("testUser");
        user.setSurname("testUser");
        user.setEmail("test@testemail.com");
        user.setConfirmedEmail(false);
        String json = new Gson().toJson(user);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post("/user")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andReturn();

        JSONObject jsn = toJSONObject(result.getResponse().getContentAsString());

        // If result is success
        assertEquals(true, jsn.get("success"));




    }
}
