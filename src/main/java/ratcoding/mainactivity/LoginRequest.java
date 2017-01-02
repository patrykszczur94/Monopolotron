package ratcoding.mainactivity;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {

    private static final String LOGIN_REQUEST_URL = "http://access.ratcoding.x14.eu/Login.php";
    private Map<String , String> params ;

    public LoginRequest (String email, String pass, Response.Listener<String> listener) {
        super(Request.Method.POST, LOGIN_REQUEST_URL, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.toString());
            }
        });
        params = new HashMap<>();
        params.put("pass", pass);
        params.put("email", email);
    }

    public Map<String, String> getParams() {
        return params;
    }
}