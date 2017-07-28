package hello.controller;

import com.solidfire.gson.Gson;
import hello.model.User;
import net.minidev.json.parser.ParseException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import net.minidev.json.parser.JSONParser;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@RunWith(SpringRunner.class)
@SpringBootTest
//@Sql(statements = "insert into User(name) values('ggg')")
public class RestUserControllerTest {

    private MockMvc mockMvc;
    private int USER_COUNT=3;


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

    private void initializeTestValues() throws Exception {
        // CREATE USERS
        for(int i=0;i<USER_COUNT;i++){
            User user = new User();
            user.setName("testUser");
            user.setSurname("testUser");
            user.setEmail("email@gmail.com");
            String json = new Gson().toJson(user);

            mockMvc.perform(MockMvcRequestBuilders
                    .post("/user")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json));
        }
    }

    @Test
    public void getUsersCount() throws Exception{
        initializeTestValues();

        MvcResult result = mockMvc.perform(
                get("/userCount")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        JSONObject jsn = toJSONObject(result.getResponse().getContentAsString());

        assertEquals(USER_COUNT, jsn.get("data"));
    }

    @Test
    public void getAllUsers() throws Exception {

        MvcResult result = mockMvc.perform(
                get("/users")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andReturn();

        JSONObject jsn = toJSONObject(result.getResponse().getContentAsString());

        System.out.println(jsn);

        // If result is success
        assertEquals(true, jsn.get("success"));
    }

    @Test
    public void createExistsDeleteUser() throws Exception {

        // CREATE USER
        User user = new User();
        user.setName("testUser");
        user.setSurname("testUser");
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


        // EXISTS USER
        JSONObject userId=JSONObject.fromObject(jsn.get("data"));

        result=mockMvc.perform(MockMvcRequestBuilders
            .get("/user/"+userId.getString("userId"))
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        jsn=toJSONObject(result.getResponse().getContentAsString());

        // If result is success
        assertEquals(true, jsn.get("success"));


        // DELETE USER

        result=mockMvc.perform(MockMvcRequestBuilders
            .delete("/user/"+userId.getString("userId"))
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        jsn=toJSONObject(result.getResponse().getContentAsString());

        // If result is success
        assertEquals(true, jsn.get("success"));

    }

    @Test
    public void updateUser() throws Exception{
        String updatedName="updatedName";
        String updatedSurname="updatedSurname";
        String updatedGender="female";
        String updatedBirth="2011-11-11";
        String updatedTelephone="12345";
        String updatedEmail="email@email.com";

        // CREATE USER
        User user = new User();
        user.setName("testUser");
        user.setSurname("testUser");
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


        // Geeting new user id
        JSONObject userId=JSONObject.fromObject(jsn.get("data"));

        // UPDATE USER
        user.setName(updatedName);
        user.setSurname(updatedSurname);
        user.setGender(updatedGender);
        user.setBirth(updatedBirth);
        user.setTelephone(updatedTelephone);
        user.setEmail(updatedEmail);
        user.setUserId(Integer.parseInt(userId.getString("userId")));
        json = new Gson().toJson(user);

        result = mockMvc.perform(MockMvcRequestBuilders
                .put("/user")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andReturn();

        jsn = toJSONObject(result.getResponse().getContentAsString());

        // If result is success
        assertEquals(true, jsn.get("success"));

        // CHECK NEW USER DATA

        result = mockMvc.perform(MockMvcRequestBuilders
                .get("/user/"+userId.getString("userId"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        jsn = toJSONObject(result.getResponse().getContentAsString());
        JSONArray userArray=JSONArray.fromObject(jsn.get("data"));
        JSONObject userObject=JSONObject.fromObject(userArray.getJSONObject(0));

        assertEquals(updatedName, userObject.get("name"));
        assertEquals(updatedSurname, userObject.get("surname"));
        assertEquals(updatedGender, userObject.get("gender"));
        assertEquals(updatedBirth, userObject.get("birth"));
        assertEquals(updatedTelephone, userObject.get("telephone"));
        assertEquals(updatedEmail, userObject.get("email"));

    }
}