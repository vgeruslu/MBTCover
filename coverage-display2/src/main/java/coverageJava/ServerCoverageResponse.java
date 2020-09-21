package coverageJava;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

public class ServerCoverageResponse {

    public static Response response;
    public static ConcurrentHashMap<String,Object> map;
    public static String serverCoverage = "0";

    public ServerCoverageResponse(){

        map = new ConcurrentHashMap<String, Object>();
    }

    public static void authorizaton() {

        /**
        TODO: restApi Service
         */

    }

    public static void getResponseBody(){

        /**
         TODO:
         */
    }

    public static String getCoverage(){

        /**
         TODO:
         */
       // return map.get("Total").toString();
        return "0";
    }

}
