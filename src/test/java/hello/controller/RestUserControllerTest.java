package hello.controller;

import com.solidfire.gson.Gson;
import hello.model.User;
import hello.service.UserService;
import junit.framework.Assert;
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
import net.minidev.json.parser.JSONParser;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by Tom on 25.07.2017.
 */


//@ContextConfiguration(locations = { "classpath:/test/BeanConfig.xml" })
//@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class)
//@RunWith(SpringJUnit4ClassRunner.class)
//@WebAppConfiguration
//@WebMvcTest(value = RestUserController.class, secure = false)


@RunWith(SpringRunner.class)
@SpringBootTest
public class RestUserControllerTest {

    @Autowired
    private UserService userService;

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setUp() {
        //MockitoAnnotations.initMocks(this);
        //mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).dispatchOptions(true).build();
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void testGetAllUsersSucceeds() throws Exception {
        User user = new User();
        user.setName("testUser");
        user.setSurname("testUser");
        String json = new Gson().toJson(user);

        mockMvc.perform(
                get("/users")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        )
                //.andExpect(status().isCreated());
                //.andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void addUser() throws Exception {
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

        String content = result.getResponse().getContentAsString();

        JSONParser parser=new JSONParser();
        Object obj=parser.parse(content);
        JSONObject jsn = JSONObject.fromObject(obj);

        assertEquals(true, jsn.get("success"));

        System.out.println(jsn.get("data"));


       // System.out.println(jsn.get("data"));

        System.out.println(obj);

//        mockMvc.perform(
//                post("/user")
//                        .accept(MediaType.APPLICATION_JSON)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json)
//        ).andExpect(status().isOk());
    }

}