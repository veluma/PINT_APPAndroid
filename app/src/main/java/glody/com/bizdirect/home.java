package glody.com.bizdirect;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import glody.com.bizdirect.util.Campanha;
import glody.com.bizdirect.util.DatabaseHelper;
import glody.com.bizdirect.util.Empresa;
import glody.com.bizdirect.util.MySingleton;
import glody.com.bizdirect.util.NetworkStateChecker;
import glody.com.bizdirect.util.SessionHandler;
import glody.com.bizdirect.util.bottonnavhelper;
import glody.com.bizdirect.util.user;

public class home extends AppCompatActivity {

    private static final String TAG = "HomeActivity";
    private static final int ACTIVITY_NUM = 0;
    private Context mContext = home.this;
    private SessionHandler session;
    private DatabaseHelper db;
    private user cliente;
    private NetworkStateChecker cheker;
    private GridView gridView;
    private ArrayList<Campanha> userModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setupBottomNavigationView();
        session = new SessionHandler(getApplicationContext());
        cliente=session.getUserDetails();
        cheker = new NetworkStateChecker(getApplicationContext());
        db = new DatabaseHelper(this);
        if(cheker.isNetworkConnected())
        {
            loadEmpresas();
            loadfidelizados(cliente.getId());
            loadcamanha(cliente.getId());
        }


        DatabaseHelper databaseHelper = new DatabaseHelper(home.this);
        userModelArrayList = databaseHelper.getAllCampanha();
        gridView = findViewById(R.id.gridView);
        campanhasAdapter customAdapter = new campanhasAdapter(getApplicationContext(), userModelArrayList);
        gridView.setAdapter(customAdapter);

    }
    private void loadEmpresas() {

        /*
         * Creating a String Request
         * The request type is GET defined by first parameter
         * The URL is defined in the second parameter
         * Then we have a Response Listener and a Error Listener
         * In response listener we will get the JSON response as a String
         * */
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://193.137.7.33/~estgv17276/PINT4/index.php/Api/api_get_empresa",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {


                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);

                                //adding the product to product list

                                db.addempresa(
                                        product.getInt("idempresa"),
                                        product.getString("nomeempresa"),
                                        product.getString("marca"),
                                        product.getString("morada"),
                                        product.getString("logo"),
                                        product.getString("email"),
                                        product.getString("telefone"),
                                        product.getInt("ramoatuacao"),
                                        product.getDouble("latitude"),
                                        product.getDouble("longitude")

                                        );
                                Log.e("late", String.valueOf(product.getDouble("latitude"))+"--"+String.valueOf(product.getDouble("longitude")) );
                            }

                            //creating adapter object and setting it to recyclerview

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //adding our stringrequest to queue
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }


    private void loadcamanha(int cid) {

        /*
         * Creating a String Request
         * The request type is GET defined by first parameter
         * The URL is defined in the second parameter
         * Then we have a Response Listener and a Error Listener
         * In response listener we will get the JSON response as a String
         * */
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://193.137.7.33/~estgv17276/PINT4/index.php/Api/api_get_camp/?id="+cid,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {


                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);

                                //adding the product to product list

                                db.addcampanha(
                                        product.getInt("idcampanha"),
                                        product.getInt("empresa"),
                                        product.getString("nome"),
                                        product.getString("nomeempresa"),
                                        product.getString("desricao"),
                                        product.getDouble("valor_venda"),
                                        product.getInt("pontos_ganhos"),
                                        product.getString("mail_duvidas"),
                                        product.getString("dt_inicio"),
                                        product.getString("dt_fim"),
                                        product.getInt("tipo_campanha")

                                );

                            }

                            //creating adapter object and setting it to recyclerview

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //adding our stringrequest to queue
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
    private void loadfidelizados(int cid) {

        /*
         * Creating a String Request
         * The request type is GET defined by first parameter
         * The URL is defined in the second parameter
         * Then we have a Response Listener and a Error Listener
         * In response listener we will get the JSON response as a String
         * */
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://193.137.7.33/~estgv17276/PINT4/index.php/Api/api_get_fide/?id="+cid,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {


                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);
                            db.reload_pontos();
                            db.reload_fidelizados();
                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);

                                //adding the product to product list

                                Log.e("client",String.valueOf(product.getInt("cliente")));
                                Log.e("empresa",String.valueOf(product.getInt("empresa")));
                                db.addempresa_pontos(
                                        product.getInt("cliente"),
                                        product.getInt("empresa"),
                                        product.getInt("pontos_cliente_empresa")
                                );
                            }

                            //creating adapter object and setting it to recyclerview

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //adding our stringrequest to queue
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
//configurao menu
    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
        BottomNavigationView navView = (BottomNavigationView) findViewById(R.id.nav_view);
        bottonnavhelper.enableNavigation(mContext, navView);
        Menu menu = navView.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
}
