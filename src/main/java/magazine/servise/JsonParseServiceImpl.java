package magazine.servise;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by pvc on 27.06.2016.
 */
@Service
public class JsonParseServiceImpl implements JsonParseService {

    private static Logger log = Logger.getLogger(JsonParseServiceImpl.class);

    @Override
    public Map<String, String> getUserParameters (String userJSON){

        log.info("getUserParameters method");

        Map<String, String> userParameters = new HashMap<>();
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(userJSON);
            JSONObject jsonObj = (JSONObject) obj;

            String username = (String) jsonObj.get("username");
            userParameters.put("username", username);
            String password = (String) jsonObj.get("password");
            userParameters.put("password", password);
            String name = (String) jsonObj.get("name");
            userParameters.put("name", name);
            String surname = (String) jsonObj.get("surname");
            userParameters.put("surname", surname);
            String middleName = (String) jsonObj.get("middleName");
            userParameters.put("middleName", middleName);
            String university = (String) jsonObj.get("university");
            userParameters.put("university", university);
            String institute = (String) jsonObj.get("institute");
            userParameters.put("institute", institute);
            String chair = (String) jsonObj.get("chair");
            userParameters.put("chair", chair);
            String position = (String) jsonObj.get("position");
            userParameters.put("position", position);
            String phone = (String) jsonObj.get("phone");
            userParameters.put("phone", phone);
            String acadStatus = (String) jsonObj.get("acadStatus");
            userParameters.put("acadStatus", acadStatus);
            String sciDegree = (String) jsonObj.get("sciDegree");
            userParameters.put("sciDegree", sciDegree);
            String userSex =(String) jsonObj.get("userSex");
            userParameters.put("userSex", userSex);
            String photoName = (String) jsonObj.get("photo");
            userParameters.put("photoName", photoName);
            String adminRole = (String) jsonObj.get("isAdministrator");
            userParameters.put("adminRole", adminRole);
            String interestsStr = (String) jsonObj.get("interests");
            userParameters.put("interestsStr", interestsStr);

        } catch (ParseException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Неможливо зареєструватись. Перевірте вірність введених даних.");
        }
        return userParameters;
    }
}
