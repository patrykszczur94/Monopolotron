package ratcoding.mainactivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void bRegisterOnClick(View v) {
        Log.d("Button", "Clicked");

        final EditText etName = (EditText) findViewById(R.id.etName) ;
        final EditText etUsername = (EditText) findViewById(R.id.etUsername) ;
        final EditText etPassword = (EditText) findViewById(R.id.etPassword) ;
        final Button bRegister = (Button) findViewById(R.id.bRegister) ;

        final String name = etName.getText().toString();
        final String upass = etPassword.getText().toString();
        final String email = etUsername.getText().toString();


        Response.Listener<String> responseListener = new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success") ;

                    if(success){
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class) ;
                        RegisterActivity.this.startActivity(intent) ;
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this) ;
                        builder.setMessage("Register failed ")
                                .setNegativeButton("Retry" , null)
                                .create()
                                .show();
                    }
                } catch(JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        RegisterRequest registerRequest = new RegisterRequest(name, upass, email, responseListener);
        RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this) ;
        queue.add(registerRequest) ;
        Log.d("Register", "Added to queue");
    }

}