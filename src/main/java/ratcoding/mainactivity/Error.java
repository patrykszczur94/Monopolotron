package ratcoding.mainactivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Error extends AppCompatActivity {

    Intent intentEmail = null ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);

        Button button = (Button) findViewById(R.id.button30);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText message = (EditText) findViewById(R.id.messageError) ;
                String messageFromUserAlkotron = message.getText().toString() ;
                sendError(messageFromUserAlkotron);


            }
        });
    }

        private void sendError(String messageFromUserAlkotron   ) {

            intentEmail = new Intent(Intent.ACTION_SEND);
            intentEmail.setData(Uri.parse("mailto:"));
            String[] to = {"szlik31@gmail.com"};
            intentEmail.putExtra(Intent.EXTRA_EMAIL, to);
            intentEmail.putExtra(Intent.EXTRA_SUBJECT, "error form user Alkotron");
            intentEmail.putExtra(Intent.EXTRA_TEXT, messageFromUserAlkotron );
            intentEmail.setType("message/rfc822");
            startActivity(Intent.createChooser(intentEmail, "select the applications from which you want to send email"));

        }

}
