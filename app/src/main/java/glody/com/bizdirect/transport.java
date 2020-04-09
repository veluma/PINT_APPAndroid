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

public class transport extends Fragment {
    private GridView gridView;
    private TypedArray allImages;
    private ArrayList<Empresa> userModelArrayList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.transporte, null);
        getAllWidgets(rootView);
        setAdapter();
        return rootView;
    }
    private void getAllWidgets(View view) {
        gridView = (GridView) view.findViewById(R.id.gridViewFragmentOne);
        //allImages = getResources().obtainTypedArray(R.array.all_images);
    }
    private void setAdapter()
    {
        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());

        userModelArrayList = databaseHelper.getEmpresa(4);
        /*for (int i = 0; i < allImages.length(); i++) {
            allDrawableImages.add(allImages.getDrawable(i));
        }*/
        GridViewAdapter gridViewAdapter = new GridViewAdapter(empresas.getInstance(), userModelArrayList);
        gridView.setAdapter(gridViewAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), loja_detail.class);
                intent.putExtra("user", userModelArrayList.get(position));
                startActivity(intent);
            }
        });
    }
}