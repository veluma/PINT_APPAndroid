package glody.com.bizdirect;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import glody.com.bizdirect.util.Empresa;
import glody.com.bizdirect.util.bottonnavhelper;

public class loja_pontos extends AppCompatActivity {
    private static final String TAG = "HomeActivity";
    private static final int ACTIVITY_NUM = 1;
    private Context mContext = loja_pontos.this;
    private TextView etname, etmorada, etemail,etpontos;
    private Empresa userModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loja_pontos);
        setupBottomNavigationView();
        Intent intent = getIntent();
        userModel = (Empresa) intent.getSerializableExtra("user");
        etname = (TextView) findViewById(R.id.empresanome);
        etmorada = (TextView) findViewById(R.id.empresamorada);
        etemail = (TextView) findViewById(R.id.empresaemail);
        etpontos = (TextView) findViewById(R.id.epontos);
        etname.setText(userModel.getNomeempresa());
        etmorada.setText(userModel.getMorada());
        etemail.setText(userModel.getEmail());
        etpontos.setText(String.valueOf(userModel.getPontos()));

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