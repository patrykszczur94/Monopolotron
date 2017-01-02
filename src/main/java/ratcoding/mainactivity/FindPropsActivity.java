package ratcoding.mainactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

public class FindPropsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_props);
        Switch switch_1 = (Switch) findViewById(R.id.switch1);
        switch_1.setText(getString(R.string.switch_off));
    }

    public void onSwitchButton(View view) {
        Switch switch_1 = (Switch) findViewById(R.id.switch1);
        TextView editText_label = (TextView) findViewById(R.id.editTextLabel);

        if(switch_1.isChecked()) {
            switch_1.setText(getString(R.string.switch_on));
            editText_label.setText(getString(R.string.switch_on_label));
        }
        else {
            switch_1.setText(getString(R.string.switch_off));
            editText_label.setText(getString(R.string.switch_off_label));
        }
    }

    public void toShops(View view) {
        Switch sw = (Switch) findViewById(R.id.switch1);
        TextView tv = (TextView) findViewById(R.id.etValue);

        Intent intent = new Intent(this, GetShopsActivity.class);
        intent.putExtra("email", getIntent().getStringExtra("email"));
        intent.putExtra("pass", getIntent().getStringExtra("pass"));
        intent.putExtra("searchType", sw.isChecked());
        intent.putExtra("value", tv.getText().toString());

        startActivity(intent);
    }
}
