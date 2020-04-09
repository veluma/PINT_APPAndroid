package glody.com.bizdirect;

import android.content.Context;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import glody.com.bizdirect.util.DatabaseHelper;
import glody.com.bizdirect.util.bottonnavhelper;

//Implementing the interface OnTabSelectedListener to our MainActivity
//This interface would help in swiping views
public class empresas extends AppCompatActivity {
    public static empresas instance;
    private ViewPagerAdapter adapter;
    private lojas loja;
    private vestuario vest;
    private restaurantes rest;
    private barbeiro barbe;
    private transport trans;
    private outros outro;
    private fidelizados fidel;
    private ViewPager viewPager;
    private TabLayout allTabs;
    private DatabaseHelper db;

    private static final String TAG = "empresas";
    private static final int ACTIVITY_NUM = 1;
    private Context mContext = empresas.this;
    private int[] tabIcons = {
            R.drawable.ic_todos, //todas
            R.drawable.ic_2, // vestuario
            R.drawable.ic_3, //restauracao
            R.drawable.ic_ioga, // Estetica
            R.drawable.servico, //servicos
            R.drawable.outros, //outros
            R.drawable.ic_6//fidelizadas
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresas);
        db = new DatabaseHelper(this);
        setupBottomNavigationView();

        instance=this;

        getAllWidgets();
        setupViewPager();
    }

    public static empresas getInstance() {
        return instance;
    }
    //inicializa o view pager e as tabs
    private void getAllWidgets() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        allTabs = (TabLayout) findViewById(R.id.tabs);

    }
    //configura o view pager
    private void setupViewPager() {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        loja = new lojas();
        vest = new vestuario();
        rest = new restaurantes();
        barbe = new barbeiro();
        trans = new transport();
        outro = new outros();
        fidel = new fidelizados();

        adapter.addFragment(loja, "");
        adapter.addFragment(vest, "");
        adapter.addFragment(rest, "");
        adapter.addFragment(barbe, "");
        adapter.addFragment(trans, "");
        adapter.addFragment(outro, "");
        adapter.addFragment(fidel, "");

        setViewPageAdapter();
    }
    //adpta o view pager
    private void setViewPageAdapter() {
        viewPager.setAdapter(adapter);
        allTabs.setupWithViewPager(viewPager);
        allTabs.getTabAt(0).setIcon(tabIcons[0]);
        allTabs.getTabAt(1).setIcon(tabIcons[1]);
        allTabs.getTabAt(2).setIcon(tabIcons[2]);
        allTabs.getTabAt(3).setIcon(tabIcons[3]);
        allTabs.getTabAt(4).setIcon(tabIcons[4]);
        allTabs.getTabAt(5).setIcon(tabIcons[5]);
        allTabs.getTabAt(6).setIcon(tabIcons[6]);
    }
    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
        BottomNavigationView navView = findViewById(R.id.nav_view);
        bottonnavhelper.enableNavigation(mContext, navView);
        Menu menu = navView.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
}
