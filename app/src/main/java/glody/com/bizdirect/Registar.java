package glody.com.bizdirect;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import glody.com.bizdirect.util.MySingleton;
import glody.com.bizdirect.util.SessionHandler;

public class Registar extends AppCompatActivity {

    private static final String KEY_STATUS = "status";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_NOME = "nome";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_NIF = "nif";
    private static final String KEY_TELEFONE = "telefone";
    private static final String KEY_MORADA = "morada";
    private static final String KEY_NASC = "dtnascimento";
    private static final String KEY_EMPTY = "";
    private EditText tb_nome;
    private EditText tb_email;
    private EditText tb_nif;
    private EditText tb_password;
    private EditText tb_telefone;
    private EditText tb_nascimento;
    private EditText tb_morada;
    private String nome;
    private String email;
    private String nif;
    private String password;
    private String telefone;
    private String nascimento;
    private String morada;
    private ProgressDialog pDialog;
    private String register_url = "http://193.137.7.33/~estgv17276/app/reg.php";
    private SessionHandler session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registar);

        tb_nome=findViewById(R.id.tv_username);
        tb_email=findViewById(R.id.tb_email);
        tb_nif=findViewById(R.id.tb_nif);
        tb_password=findViewById(R.id.tv_password);
        tb_telefone=findViewById(R.id.tb_tel);
        tb_nascimento=findViewById(R.id.td_data);
        tb_morada=findViewById(R.id.tb_morada);

        TextView login = findViewById(R.id.tv_login);
        Button register = findViewById(R.id.bt_reg);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Registar.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Retrieve the data entered in the edit texts
                nome = tb_nome.getText().toString().toLowerCase().trim();
                email = tb_email.getText().toString().trim();
                password = tb_password.getText().toString().trim();
                nif = tb_nif.getText().toString().trim();
                morada = tb_morada.getText().toString().trim();
                String dia = tb_nascimento.getText().toString().substring(0,2);
                String mes = tb_nascimento.getText().toString().substring(3,5);
                String ano = tb_nascimento.getText().toString().substring(6);
                nascimento = ano+"-"+mes+"-"+dia;

                telefone = tb_telefone.getText().toString().trim();
                if (validateInputs()) {
                    registerUser();
                }

            }
        });



    }
    private void displayLoader() {
        pDialog = new ProgressDialog(Registar.this);
        pDialog.setMessage("Signing Up.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

    }

    private void registerUser() {
        displayLoader();
        //Toast.makeText(getApplicationContext(),
        //   morada, Toast.LENGTH_SHORT).show();

        final JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put(KEY_NOME, nome);
            request.put(KEY_PASSWORD, password);
            request.put(KEY_EMAIL, email);
            request.put(KEY_NIF, nif);
            request.put(KEY_TELEFONE, telefone);
            request.put(KEY_MORADA, morada);
            request.put(KEY_NASC, nascimento);



        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("logx",request.toString());

        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, register_url, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.dismiss();
                        try {
                            //Check if user got registered successfully
                            if (response.getInt(KEY_STATUS) == 0) {
                                //Set the user session



                                startActivity(new Intent(Registar.this,MainActivity.class));


                            }else if(response.getInt(KEY_STATUS) == 1){
                                //Display error message if username is already existsing
                                tb_email.setError("Usuario já usado!");
                                tb_email.requestFocus();

                            }else{

                                Toast.makeText(getApplicationContext(),
                                        response.getString(KEY_MESSAGE), Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {

                            Toast.makeText(getApplicationContext(),
                                    "ERRO", Toast.LENGTH_SHORT).show();
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
    private boolean validateInputs() {
        if (KEY_EMPTY.equals(nome)) {
            tb_nome.setError("O nome não pode ficar vazio ");
            tb_nome.requestFocus();
            return false;

        }
        if (KEY_EMPTY.equals(email)) {
            tb_email.setError("O email não pode ficar vazio ");
            tb_email.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(password)) {
            tb_password.setError("O Password não pode ficar vazio  ");
            tb_password.requestFocus();
            return false;
        }

        if (KEY_EMPTY.equals(nif)) {
            tb_nif.setError("O nif não pode ficar vazio");
            tb_nif.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(telefone)) {
            tb_telefone.setError("O telefone não pode ficar vazio ");
            tb_telefone.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(morada)) {
            tb_morada.setError("O morada não pode ficar vazio");
            tb_morada.requestFocus();
            return false;
        }
       if (KEY_EMPTY.equals(nascimento)) {
            tb_nascimento.setError("O nascimento não pode ficar vazio");
            tb_nascimento.requestFocus();
            return false;
        }



        return true;
    }

}