package glody.com.bizdirect;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import glody.com.bizdirect.util.DatabaseHelper;
import glody.com.bizdirect.util.MySingleton;
import glody.com.bizdirect.util.NetworkStateChecker;
import glody.com.bizdirect.util.SessionHandler;
import glody.com.bizdirect.util.user;

public class MainActivity extends AppCompatActivity {

    private static final String KEY_STATUS = "status";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_FULL_NAME = "full_name";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_EMPTY = "";
    private EditText etUsername;
    private EditText etPassword;

    private String username;
    private String password;
    private ProgressDialog pDialog;
    private String login_url = "http://193.137.7.33/~estgv17276/app/Login.php";
    private SessionHandler session;
    private DatabaseHelper db;
    private user cliente;
    private NetworkStateChecker cheker;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionHandler(getApplicationContext());
        cliente=session.getUserDetails();
        cheker = new NetworkStateChecker(getApplicationContext());
        db = new DatabaseHelper(this);
        //verifica o estado da conectção
        if(cheker.isNetworkConnected())
        {
            if(session.isLoggedIn()){
                Log.e("url","http://193.137.7.33/~estgv17276/PINT4/index.php/Api/api_get_fide/?id="+cliente.getId());
                Log.e("url","http://193.137.7.33/~estgv17276/PINT4/index.php/Api/api_get_camp/?id="+cliente.getId());
                loadDashboard();

            }
            //loadProducts();
            Toast.makeText(getApplicationContext(),
                    "estas conectado", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(),
                    "estas disconectado", Toast.LENGTH_SHORT).show();
        }

        setContentView(R.layout.activity_main);

        etUsername = findViewById(R.id.tv_username);
        etPassword = findViewById(R.id.tv_password);

        TextView register = findViewById(R.id.tv_registar);
        TextView recuperar = findViewById(R.id.tv_rpass);
        TextView termos = findViewById(R.id.termos);
        Button login = findViewById(R.id.bt_login);



        termos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://193.137.7.33/~estgv17276/PINT4/index.php/PINT_Biz/CarregaTermos"));
                startActivity(browserIntent);

            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Registar.class));
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = etUsername.getText().toString().toLowerCase().trim();
                password = etPassword.getText().toString().trim();
                if (validateInputs()) {

                    login();


                }
            }
        });
        recuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,RecuperarSenha.class));
            }
        });


    }
    //carega o home
    private void loadDashboard() {
        Intent i = new Intent(getApplicationContext(), home.class);
        startActivity(i);
        finish();

    }

    /**
     * Display Progress bar while Logging in
     */

    private void displayLoader() {
        pDialog = new ProgressDialog(MainActivity.this);
        pDialog.setMessage("Logging In.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

    }

    // faz o login envia os dados para a api e verifica o login
    private void login() {
        displayLoader();
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put(KEY_USERNAME, username);
            request.put(KEY_PASSWORD, password);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, login_url, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.dismiss();
                        try {
                            //Check if user got logged in successfully

                            if (response.getInt(KEY_STATUS) == 0) {

                                session.loginUser(response.getInt("idcliente"),response.getString(KEY_FULL_NAME),response.getString("email")
                                       ,response.getString("morada"),response.getString("telefone"));
                                loadDashboard();

                            }else{
                                Toast.makeText(getApplicationContext(),
                                        response.getString(KEY_MESSAGE), Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();

                        //Display error message whenever an error occurs
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);
    }

    /**
     * Validates inputs and shows error if any
     * @return
     */
    //valida as entradas
    private boolean validateInputs() {
        if(KEY_EMPTY.equals(username)){
            etUsername.setError("Username cannot be empty");
            etUsername.requestFocus();
            return false;
        }
        if(KEY_EMPTY.equals(password)){
            etPassword.setError("Password cannot be empty");
            etPassword.requestFocus();
            return false;
        }
        return true;
    }


}
