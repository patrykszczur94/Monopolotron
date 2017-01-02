package ratcoding.mainactivity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mAlkoMap;
    private GoogleApiClient client;

    String toastMessage;

    final String[] mapsNames = {"HYBRID MAP", "TERRAIN MAP"};
    int category = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.website);
        mapFragment.getMapAsync(this);
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mAlkoMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mAlkoMap.setMyLocationEnabled(true);

        ArrayList<String> lats = getIntent().getStringArrayListExtra("lats");
        ArrayList<String> lons = getIntent().getStringArrayListExtra("lons");
        ArrayList<String> names = getIntent().getStringArrayListExtra("names");

        for(int i = 0; i < lats.size(); i++) {
            LatLng temp = new LatLng(Double.parseDouble(lats.get(i)), Double.parseDouble(lons.get(i)));
            mAlkoMap.addMarker(new MarkerOptions().position(temp).title(names.get(i)));
        }
    }

    public void changeMapType(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Map types");
        builder.setItems(mapsNames, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                category = which;
                if (category == 0) {
                    if (mAlkoMap.getMapType() == GoogleMap.MAP_TYPE_NORMAL) {
                        mAlkoMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                    } else {
                        mAlkoMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    }
                }
                if (category == 1) {
                    if (mAlkoMap.getMapType() == GoogleMap.MAP_TYPE_NORMAL) {
                        mAlkoMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                    } else {
                        mAlkoMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    }
                }
                if (category == 0) {
                    toastMessage = "you chose the hybrid map";
                } else {
                    toastMessage = "you chose the terrain map";
                }
                Context context = getApplicationContext();
                Toast toast = Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        builder.create();
        builder.show();
    }
}

