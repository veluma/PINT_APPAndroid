package glody.com.bizdirect.util;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;





//import glody.com.bizdirect.Lojas;

//import glody.com.bizdirect.MapsActivity;
import glody.com.bizdirect.MapsActivity;
import glody.com.bizdirect.R;
import glody.com.bizdirect.empresas;
import glody.com.bizdirect.home;
import glody.com.bizdirect.settings;
//import glody.com.bizdirect.lojas;
//import glody.com.bizdirect.settings;


public class bottonnavhelper {


    // permite a navegação no menu de baixo

    public static void enableNavigation(final Context context, BottomNavigationView navView) {

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent a = new Intent(context, home.class);
                        context.startActivity(a);
                        break;
                   case R.id.navigation_lojas:
                       Intent c = new Intent(context, empresas.class);
                       context.startActivity(c);
                        break;
                    case R.id.navigation_map:
                        Intent d = new Intent(context, MapsActivity.class);
                       context.startActivity(d);
                        break;
                    case R.id.navigation_perfil:
                        Intent e = new Intent(context, settings.class);
                        context.startActivity(e);
                        break;
                }
                return false;
            }
        });
    }

}

