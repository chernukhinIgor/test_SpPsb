package hello.utils;

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

    public static JSONObject jsonSuccessObject(Object ojbs){
        JSONObject response = new JSONObject();
        response.put("success",true);
        response.element("data",ojbs);
        return response;
    }

    public static JSONObject wrapError(String errorMessage, int errorCode){
        JSONObject response = new JSONObject();
        response.put("errorMessage",errorMessage);
        response.put("code",errorCode);
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
}
