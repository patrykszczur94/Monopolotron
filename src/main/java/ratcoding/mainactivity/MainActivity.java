package ratcoding.mainactivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void displayFavs(View view) {
        Intent intent = new Intent(this, GetFavsActivity.class);
        intent.putExtra("email", getIntent().getStringExtra("email"));
        intent.putExtra("pass", getIntent().getStringExtra("pass"));
        startActivity(intent);
    }

    public void searchPrefs(View view) {
        Intent intent = new Intent(this, FindPropsActivity.class);
        intent.putExtra("email", getIntent().getStringExtra("email"));
        intent.putExtra("pass", getIntent().getStringExtra("pass"));
        startActivity(intent);
    }

    public void mapUser(View view) {
        Intent intent = new Intent(this , MapsActivity.class);
        intent.putExtra("email", getIntent().getStringExtra("email"));
        intent.putExtra("pass", getIntent().getStringExtra("pass"));
        startActivity(intent);
    }

    public void moreButton(View view) {
        Intent intent = new Intent(this, MainActivityMore.class);
        intent.putExtra("email", getIntent().getStringExtra("email"));
        intent.putExtra("pass", getIntent().getStringExtra("pass"));
        startActivity(intent);
    }

    public void exitApp(View view) {

        new AlertDialog.Builder(this)
                .setTitle("Closing Application")
                .setMessage("Do you want to exit ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        moveTaskToBack(true);
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(1);
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }
}


