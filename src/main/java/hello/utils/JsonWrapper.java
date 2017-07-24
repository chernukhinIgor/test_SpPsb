package hello.utils;

import hello.model.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * Created by Tom on 24.07.2017.
 */
public class JsonWrapper {
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
}
