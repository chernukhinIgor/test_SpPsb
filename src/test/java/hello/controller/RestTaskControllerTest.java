package hello.controller;

import com.solidfire.gson.Gson;
import hello.model.Task;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Tom on 25.07.2017.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class RestTaskControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void getTaskByID() throws Exception {
    }

    @Test
    public void getPaginationTasks() throws Exception {
    }

    @Test
    public void getAllTasksReturnsOkHeader() throws Exception {

//        User user = new User();
//        user.setName("123");
//        user.setSurname("234");
//        String json = new Gson().toJson(user);
        mockMvc.perform(
                get("/tasks")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        //.content(json)
        )
                //.andExpect(status().isCreated());
                //.andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getTaskCount() throws Exception {
    }

    @Test
    public void addTask() throws Exception {
        Task task = new Task();
        task.setCreatorUserId(1);
        task.setName("name");
        task.setDescription("descr");
        task.setResponsibleUserId(2);
        task.setStatus(Task.statusStates.canceled);
        String json = new Gson().toJson(task);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post("/task")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andReturn();
        net.minidev.json.parser.JSONParser parser=new net.minidev.json.parser.JSONParser();
        String content = result.getResponse().getContentAsString();
        Object obj=parser.parse(content);
        JSONObject jsn = JSONObject.fromObject(obj);
        System.out.println(jsn.get("data"));
    }

    @Test
    public void updateTask() throws Exception {
    }

    @Test
    public void deleteTask() throws Exception {
    }

}