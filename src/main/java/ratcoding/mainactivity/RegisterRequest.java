package ratcoding.mainactivity;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://access.ratcoding.x14.eu/Register.php"  ;
    private Map<String , String> params ;

    public RegisterRequest (String name, String email, String upass  , Response.Listener<String> listener ) {
        super(Method.POST , REGISTER_REQUEST_URL , listener , null ) ;
        params = new HashMap<>();
        params.put("name", name);
        params.put("password_digest" , upass);
        params.put("email", email);
        Log.d("Register", "Request prepared");
    }

    public Map<String, String> getParams() {
        return params;
    }
}