package hello.utils;

import hello.model.Task;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * Created by Tom on 24.07.2017.
 */
public class JsonWrapper {

    public static JSONObject jsonErrorObject(String errorMessage, int errorCode){
        JSONObject jsonError = new JSONObject();
        jsonError.put("success", false);
        jsonError.put("error", JsonWrapper.wrapError(errorMessage, errorCode));
        return jsonError;
    }

    public static JSONObject jsonSuccessObject(Object ojb){
        JSONObject response = new JSONObject();
        response.put("success",true);
        response.element("data",ojb);
        return response;
    }

    public static JSONObject wrapError(String errorMessage, int errorCode){
        JSONObject error = new JSONObject();
        error.put("errorMessage",errorMessage);
        error.put("code",errorCode);
        return error;
    }








    public static JSONObject wrapSuccessIntegerInTaskId(Integer i){
        JSONObject response = new JSONObject();
        response.put("success", true);
        JSONObject data = new JSONObject();
        data.put("taskId", i);
        response.put("data", data);
        return response;
    }

    public static JSONObject wrapObject(Object obj){
        JSONArray userJsonArray = new JSONArray();
        userJsonArray.element(obj);
        JSONObject response = new JSONObject();
        response.put("success",true);
        response.element("data",userJsonArray);
        return response;
    }

    public static JSONObject wrapList(List<Object> ojbs){
        JSONObject response = new JSONObject();
        response.put("success",true);
        response.element("data",ojbs);
        return response;
    }

    public static JSONObject wrapTasks(List<Task> tasks){
        JSONObject response = new JSONObject();
        response.put("success",true);
        response.element("data",tasks);
        return response;
    }








    public static JSONArray getJsonArrayFromObjects(List<String> columns, List<Object[]> allTasksAsObjects) {
        JSONArray array = new JSONArray();
        if (columns.size() == 1) {
            for (Object row : allTasksAsObjects) {
                array.add(JsonWrapper.returnJsonFromObjectWithSingleRow(columns, row));
            }
        } else {
            for (Object[] row : allTasksAsObjects) {
                array.add(JsonWrapper.returnJsonFromObjectWithMultipleRows(columns, row));
            }
        }
        return array;
    }

    public static JSONObject returnJsonFromObjectWithMultipleRows(List<String> columns, Object[] row) {
        JSONObject data = new JSONObject();
        for (int i = 0; i < columns.size(); i++) {
            data.element(columns.get(i), row[i]);
        }
        return data;
    }

    public static JSONObject returnJsonFromObjectWithSingleRow(List<String> columns, Object row) {
        JSONObject data = new JSONObject();
        for (String column : columns) {
            data.element(column, row);
        }
        return data;
    }

}
