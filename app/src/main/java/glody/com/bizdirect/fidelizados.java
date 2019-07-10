package glody.com.bizdirect;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

import glody.com.bizdirect.util.DatabaseHelper;
import glody.com.bizdirect.util.Empresa;
import glody.com.bizdirect.util.SessionHandler;
import glody.com.bizdirect.util.user;

public class fidelizados extends Fragment {
    private GridView gridView;
    private TypedArray allImages;
    private ArrayList<Empresa> userModelArrayList;
    private user cliente;
    private SessionHandler session;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fidelizados, null);
        session = new SessionHandler(getContext());
        cliente=session.getUserDetails();
        getAllWidgets(rootView);
        setAdapter();
        return rootView;
    }
    private void getAllWidgets(View view) {
        gridView = (GridView) view.findViewById(R.id.gridViewFragmentOne);
        // allImages = getResources().obtainTypedArray(R.array.all_images);
    }
    private void setAdapter()
    {
        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
//vai buscar os dados da empresa
        userModelArrayList = databaseHelper.getFidelizados(cliente.getId());
        /*for (int i = 0; i < allImages.length(); i++) {
            allDrawableImages.add(allImages.getDrawable(i));
        }*/
        // adapta o fragmento para item_list
        GridViewAdapter gridViewAdapter = new GridViewAdapter(empresas.getInstance(), userModelArrayList);
        gridView.setAdapter(gridViewAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), loja_pontos.class);
                intent.putExtra("user", userModelArrayList.get(position));
                startActivity(intent);
            }
        });
    }
}