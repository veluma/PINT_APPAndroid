package glody.com.bizdirect;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import glody.com.bizdirect.util.Empresa;
import glody.com.bizdirect.util.MySingleton;
import glody.com.bizdirect.util.SessionHandler;
import glody.com.bizdirect.util.bottonnavhelper;
import glody.com.bizdirect.util.user;

public class loja_pontos extends AppCompatActivity {
    private static final String TAG = "HomeActivity";
    private static final int ACTIVITY_NUM = 1;
    private Context mContext = loja_pontos.this;
    private TextView etname, etmorada, etemail,etpontos;
    private Empresa userModel;
    private SessionHandler session;
    private user cliente;
    private ProgressDialog pDialog;

    private static final String KEY_STATUS = "status";
    private static final String KEY_MESSAGE = "message";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loja_pontos);
        setupBottomNavigationView();
        Intent intent = getIntent();

        session=new SessionHandler(getApplicationContext());
        cliente=session.getUserDetails();


        userModel = (Empresa) intent.getSerializableExtra("user");
        etname = (TextView) findViewById(R.id.empresanome);
        etmorada = (TextView) findViewById(R.id.empresamorada);
        etemail = (TextView) findViewById(R.id.empresaemail);
        etpontos = (TextView) findViewById(R.id.epontos);
        etname.setText(userModel.getNomeempresa());
        etmorada.setText(userModel.getMorada());
        etemail.setText(userModel.getEmail());
        etpontos.setText(String.valueOf(userModel.getPontos()));

        Button desfidelizar = findViewById(R.id.desfidelizar);



        desfidelizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                desfidelizar();
                onBackPressed();
            }
        });



    }



    private void desfidelizar() {
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put("cliente", cliente.getId());
            request.put("empresa", userModel.getId());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, "http://193.137.7.33/~estgv17276/app/desfideliza.php", request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.dismiss();
                        try {
                            //Check if user got logged in successfully

                            if (response.getInt(KEY_STATUS) == 1||response.getInt(KEY_STATUS) == 2) {

                                Toast.makeText(getApplicationContext(),
                                        response.getString(KEY_MESSAGE), Toast.LENGTH_SHORT).show();

                            }
                            Intent intent = new Intent(loja_pontos.this, lojas.class);
                            intent.putExtra("user", userModel);
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        pDialog.dismiss();

                        //Display error message whenever an error occurs
                     //   Toast.makeText(getApplicationContext(),
                          //      "Você já esta fidelizado!", Toast.LENGTH_SHORT).show();

                    }
                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);
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