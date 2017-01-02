package ratcoding.mainactivity;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GetShopsActivity extends ListActivity implements LocationListener {
    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    ArrayList<String> listItems = new ArrayList<String>();

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> adapter;

    // Setting up arrays
    String names[];
    String addresses[];
    Float lats[];
    Float lons[];

    // Setting up variable storing current pos
    LatLng currPosition;

    // Setting up location mumbo-jumbo
    protected LocationManager locationManager;
    protected LocationListener locationListener;

    // Auxiliary variables
    Boolean drawing = false;
    ArrayList<String> latitudes = new ArrayList<String>();
    ArrayList<String> longitudes = new ArrayList<String>();
    ArrayList<String> namesList = new ArrayList<String>();

    private View.OnClickListener btnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            toMaps(view);
        }
    };

    public float distance(float lat_a, float lng_a, float lat_b, float lng_b) {

        Log.d("Dist", "Location A: " + lat_a + ", " + lng_a);
        Log.d("Dist", "Location B: " + lat_b + ", " + lng_b);

        double earthRadius = 3958.75;
        double latDiff = Math.toRadians(lat_b - lat_a);
        double lngDiff = Math.toRadians(lng_b - lng_a);
        double a = Math.sin(latDiff / 2) * Math.sin(latDiff / 2) +
                Math.cos(Math.toRadians(lat_a)) * Math.cos(Math.toRadians(lat_b)) *
                        Math.sin(lngDiff / 2) * Math.sin(lngDiff / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = earthRadius * c;

        int meterConversion = 1609;
        Float out = new Float(distance * meterConversion).floatValue();
        Log.d("Dist", "" + out);

        return out;
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_get_shops);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                listItems);
        setListAdapter(adapter);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        try {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
            drawList();
        } catch(SecurityException e) {
            e.printStackTrace();
        }
        Button btn = (Button) findViewById(R.id.button3);
        btn.setOnClickListener(btnListener);
    }

    //METHOD WHICH WILL HANDLE DYNAMIC INSERTION
    public void drawList() {
        // Setting up listener
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    names = new String[jsonArray.length()];
                    addresses = new String[jsonArray.length()];
                    lats = new Float[jsonArray.length()];
                    lons = new Float[jsonArray.length()];

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject json = jsonArray.getJSONObject(i);
                        names[i] = json.getString("shopname");
                        addresses[i] = json.getString("address");
                        lats[i] = Float.parseFloat(json.getString("lat"));
                        lons[i] = Float.parseFloat(json.getString("lon"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        GetShopsRequest request = new GetShopsRequest(getIntent().getStringExtra("email"),
                getIntent().getStringExtra("pass"), responseListener);
        RequestQueue queue = Volley.newRequestQueue(GetShopsActivity.this);
        queue.start();
        queue.add(request);
    }

    public void draw() {
        listItems.clear();
        try {
            if(!getIntent().getBooleanExtra("searchType", false)) {
                for (int i = 0; i < names.length; i++) {
                    Float dist = distance(lats[i], lons[i], Float.valueOf(Double.toString(currPosition.latitude)),
                            Float.valueOf(Double.toString(currPosition.longitude)));
                    if (dist <= Integer.parseInt(getIntent().getStringExtra("value"))) {
                        listItems.add("" + names[i] + ", " + addresses[i] + "\n\t"
                                + Math.round(dist) + " m");
                        latitudes.add(lats[i].toString());
                        longitudes.add(lons[i].toString());
                        namesList.add(names[i]);

                    }
                }
            } else {
                Float distances[] = new Float[names.length];
                int temp[] = new int[names.length];

                for (int i = 0; i < names.length; i++) {
                    Float dist;
                    dist = distance(lats[i], lons[i], Float.valueOf(Double.toString(currPosition.latitude)),
                            Float.valueOf(Double.toString(currPosition.longitude)));
                    distances[i] = dist;
                    temp[i] = i;
                }

                for(int i = 0; i < names.length - 1; i++) {
                    for(int j = 0; j < names.length - 1; j++) {
                        if(distances[temp[j]] > distances[temp[j + 1]]) {
                            Float fTemp = distances[temp[j]];
                            distances[temp[j]] = distances[temp[j + 1]];
                            distances[temp[j + 1]] = fTemp;
                        }
                    }
                }
                int max;
                if(Integer.parseInt(getIntent().getStringExtra("value")) > names.length) {
                    max = names.length;
                } else {
                    max = Integer.parseInt(getIntent().getStringExtra("value"));
                }

                for(int i = 0; i < max; i++) {
                    Float dist = distance(lats[temp[i]], lons[temp[i]], Float.valueOf(Double.toString(currPosition.latitude)),
                            Float.valueOf(Double.toString(currPosition.longitude)));
                    listItems.add("" + names[temp[i]] + ", " + addresses[temp[i]] + "\n\t"
                            + Math.round(dist) + " m");
                    latitudes.add(lats[temp[i]].toString());
                    longitudes.add(lons[temp[i]].toString());
                    namesList.add(names[temp[i]]);
                }
            }
            adapter.notifyDataSetChanged();
        } catch(NullPointerException e) {
            Log.d("Location", "Next time :x");
        }
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Provider", "Enabled: " + provider);
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Provider", "Disabled: " + provider);
    }

    @Override
    public void onLocationChanged(Location location) {
        currPosition = new LatLng(location.getLatitude(), location.getLongitude());
        Log.d("Location", "" + Float.valueOf(Double.toString(location.getLatitude())) + ", "
                + Float.valueOf(Double.toString(location.getLongitude())));
        draw();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Provider", "Status changed");
    }

    public void toMaps(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putStringArrayListExtra("lats", latitudes);
        intent.putStringArrayListExtra("lons", longitudes);
        intent.putStringArrayListExtra("names", namesList);
        startActivity(intent);
    }
}
