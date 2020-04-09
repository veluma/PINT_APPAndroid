package glody.com.bizdirect;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

import glody.com.bizdirect.util.DatabaseHelper;
import glody.com.bizdirect.util.Empresa;
import glody.com.bizdirect.util.bottonnavhelper;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    private static final String TAG = "HomeActivity";
    private static final int ACTIVITY_NUM = 2;
    private Context mContext = MapsActivity.this;
    private DatabaseHelper databaseHelper;
    private ArrayList<Empresa> userModelArrayList;
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    LatLng meuLocal = new LatLng(40.656820, -7.913944);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setupBottomNavigationView();


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);


// Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        databaseHelper = new DatabaseHelper(MapsActivity.this);
//vai buscar os dados da empresa
        userModelArrayList = databaseHelper.getAllEmpresa();


        // Add a marker in Sydney and move the camera
        //LatLng viseu = new LatLng(40.656820, -7.913944);





        for (int i = 0; i < userModelArrayList.size(); i++) {
            Empresa name = userModelArrayList.get(i);
            LatLng forun = new LatLng(name.getLatitude(), name.getLongitude());
            mMap.addMarker(new MarkerOptions().position(forun).title(name.getNomeempresa()));

            Log.e("lat", String.valueOf(name.getLatitude())+"--"+String.valueOf(name.getLongitude()) );
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(meuLocal,14));


    }
    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
        BottomNavigationView navView = (BottomNavigationView) findViewById(R.id.nav_view);
        bottonnavhelper.enableNavigation(mContext, navView);
        Menu menu = navView.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }

    @Override
    public void onLocationChanged(Location location) {
        this.meuLocal = new LatLng(location.getLatitude(), location.getLongitude());
        Log.d("location", String.valueOf(location.getLatitude()));
        Log.d("location", String.valueOf(location.getLongitude()));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(meuLocal,14));

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("location","status");

    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("location","enable");

    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("location","disable");

    }
}
