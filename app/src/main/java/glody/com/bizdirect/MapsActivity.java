package glody.com.bizdirect;

import android.content.Context;
import android.support.design.widget.BottomNavigationView;
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

import java.util.ArrayList;

import glody.com.bizdirect.util.DatabaseHelper;
import glody.com.bizdirect.util.Empresa;
import glody.com.bizdirect.util.bottonnavhelper;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final String TAG = "HomeActivity";
    private static final int ACTIVITY_NUM = 2;
    private Context mContext = MapsActivity.this;
    private DatabaseHelper databaseHelper;
    private ArrayList<Empresa> userModelArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setupBottomNavigationView();
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
        LatLng viseu = new LatLng(40.656820, -7.913944);
        for (int i = 0; i < userModelArrayList.size(); i++) {
            Empresa name = userModelArrayList.get(i);
            LatLng forun = new LatLng(name.getLatitude(), name.getLongitude());
            mMap.addMarker(new MarkerOptions().position(forun).title(name.getNomeempresa()));

            Log.e("lat", String.valueOf(name.getLatitude())+"--"+String.valueOf(name.getLongitude()) );
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(viseu,14));


    }
    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
        BottomNavigationView navView = (BottomNavigationView) findViewById(R.id.nav_view);
        bottonnavhelper.enableNavigation(mContext, navView);
        Menu menu = navView.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
}
