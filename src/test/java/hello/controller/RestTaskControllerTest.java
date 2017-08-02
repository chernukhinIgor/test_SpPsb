package hello.controller;

import com.solidfire.gson.Gson;
import hello.model.Task;
import hello.service.TaskService;
import hello.utils.JsonWrapper;
import hello.utils.ReplyCodes;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static hello.utils.JsonWrapper.getJsonArrayFromObjects;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by Tom on 25.07.2017.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class RestTaskControllerTest {
    private MockMvc mockMvc;

//    @Autowired
//    private WebApplicationContext wac;

    @Mock
    private TaskService taskService;

    @InjectMocks
    private RestTaskController restTaskController;

    @Before
    public void init() {
        //mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(restTaskController)
                .build();
    }

    @Test
    public void get_task_by_id_and_task_exist() throws Exception {
        Task newTask = new Task(2, "Create new Task", 3,
                "Task description", Task.statusStates.accepted);
        int taskId = 1;
        newTask.setTaskId(taskId);
        JSONObject assumedResponse = JsonWrapper.wrapObject(newTask);
        when(taskService.getTaskById(taskId)).thenReturn(newTask);
        // look jsonPath expressions here - http://goessner.net/articles/JsonPath/
        mockMvc.perform(get("/task/" + taskId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", is(assumedResponse)))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data[0].taskId", is(1)))
                .andExpect(jsonPath("$.data[0].creatorUserId", is(2)))
                .andExpect(jsonPath("$.data[0].name", is("Create new Task")))
                .andExpect(jsonPath("$.data[0].responsibleUserId", is(3)))
                .andExpect(jsonPath("$.data[0].description", is("Task description")))
                .andExpect(jsonPath("$.data[0].status", is("accepted")));
        verify(taskService, times(1)).getTaskById(1);
        verifyNoMoreInteractions(taskService);
    }

    @Test
    public void get_task_by_id_and_task_not_exist() throws Exception {
        JSONObject jsonError = JsonWrapper.jsonErrorObject("Task does not exist", ReplyCodes.NOT_EXIST_ERROR);
        when(taskService.getTaskById(253)).thenReturn(null);
        mockMvc.perform(get("/task/" + 253))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", is(jsonError)))
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.error.errorMessage", is("Task does not exist")))
                .andExpect(jsonPath("$.error.code", is(100)));
        verify(taskService, times(1)).getTaskById(253);
        verifyNoMoreInteractions(taskService);
    }


    @Test
    public void getPaginationTasks() throws Exception {
    }

    @Test
    public void getTaskCount() throws Exception {
        when(taskService.getTaskCount()).thenReturn(5);
        mockMvc.perform(get("/taskCount"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data", is(5)));
        verify(taskService, times(1)).getTaskCount();
        verifyNoMoreInteractions(taskService);
    }

    @Test
    public void updateTask() throws Exception {
    }

    @Test
    public void deleteTask() throws Exception {
    }

    @Test
    public void get_all_tasks_without_parametric_query() throws Exception {
        List<Task> tasks = Arrays.asList(
                new Task(1, "Create unit tests for test controller", 2,
                        "No description", Task.statusStates.created),
                new Task(3, "Create unit tests for user controller", 4,
                        "No description here too", Task.statusStates.finished));
        tasks.get(0).setTaskId(1);
        tasks.get(1).setTaskId(2);
        JSONObject assumedResponse = JsonWrapper.wrapTasks(tasks);
        when(taskService.getAllTasks()).thenReturn(tasks);
        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$", is(assumedResponse)))
                .andExpect(jsonPath("$.data[0].taskId", is(1)))
                .andExpect(jsonPath("$.data[0].creatorUserId", is(1)))
                .andExpect(jsonPath("$.data[0].name", is("Create unit tests for test controller")))
                .andExpect(jsonPath("$.data[0].responsibleUserId", is(2)))
                .andExpect(jsonPath("$.data[0].description", is("No description")))
                .andExpect(jsonPath("$.data[0].status", is("created")))
                .andExpect(jsonPath("$.data[1].taskId", is(2)))
                .andExpect(jsonPath("$.data[1].creatorUserId", is(3)))
                .andExpect(jsonPath("$.data[1].name", is("Create unit tests for user controller")))
                .andExpect(jsonPath("$.data[1].responsibleUserId", is(4)))
                .andExpect(jsonPath("$.data[1].description", is("No description here too")))
                .andExpect(jsonPath("$.data[1].status", is("finished")));
        verify(taskService, times(1)).getAllTasks();
        verifyNoMoreInteractions(taskService);
    }

    @Test
    public void get_all_tasks_with_parametric_query() throws Exception {
        List<Object[]> oTasks = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Object[] objects = new Object[4];
            objects[0] = i + 1;
            objects[1] = "Task" + (i + 1);
            objects[2] = "task description" + (i + 1);
            objects[3] = i + 2;
            oTasks.add(objects);
        }
        List<String> parametricParams = Arrays.asList("taskId", "name", "description", "responsibleUserId");
        JSONArray array = getJsonArrayFromObjects(parametricParams, oTasks);
        JSONObject assumedResponse = JsonWrapper.wrapList(array);
        when(taskService.getParametricTasks(parametricParams)).thenReturn(oTasks);
        mockMvc.perform(get("/tasks")
                .param("params", "taskId")
                .param("params", "name")
                .param("params", "description")
                .param("params", "responsibleUserId")
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$", is(assumedResponse)))
                .andExpect(jsonPath("$.data[0].taskId", is(1)))
                .andExpect(jsonPath("$.data[0].name", is("Task1")))
                .andExpect(jsonPath("$.data[0].responsibleUserId", is(2)))
                .andExpect(jsonPath("$.data[0].description", is("task description1")));
        verify(taskService, times(1)).getParametricTasks(parametricParams);
        verifyNoMoreInteractions(taskService);
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
    public void add_task_completed() throws Exception {
        Task task = new Task(2, "Create new Task", 3,
                "Task description", Task.statusStates.accepted);
        Integer taskId = 1;
        JSONObject jsonResponse = JsonWrapper.wrapSuccessIntegerInTaskId(taskId);
        when(taskService.addTask(task)).thenReturn(taskId);
        String gsonTask = new Gson().toJson(task);
        mockMvc.perform(post("/task")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gsonTask)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", is(jsonResponse)))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.data", is(5)));
        verify(taskService, times(1)).getTaskCount();
        verifyNoMoreInteractions(taskService);
    }


    @Test
    public void addTask() throws Exception {

        Task newTask = new Task(2, "Create new Task", 3,
                "Task description", Task.statusStates.accepted);
        int taskId = 1;
        newTask.setTaskId(taskId);
        JSONObject assumedResponse = JsonWrapper.wrapObject(newTask);
        when(taskService.getTaskById(taskId)).thenReturn(newTask);

        Task task = new Task(1, "name", 2, "descr", Task.statusStates.canceled);
        String json = new Gson().toJson(task);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post("/task")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andReturn();
        net.minidev.json.parser.JSONParser parser = new net.minidev.json.parser.JSONParser();
        String content = result.getResponse().getContentAsString();
        Object obj = parser.parse(content);
        JSONObject jsn = JSONObject.fromObject(obj);
        System.out.println(jsn.get("data"));
    }


}