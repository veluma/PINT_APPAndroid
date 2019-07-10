package glody.com.bizdirect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import glody.com.bizdirect.util.Campanha;
import glody.com.bizdirect.util.Empresa;

public class campanhasAdapter extends BaseAdapter {
    private Context context;
    public ArrayList<Campanha> allItemsResourceID;

    public campanhasAdapter(Context context,  ArrayList<Campanha> allItemsResourceID){
        this.context = context;
        this.allItemsResourceID = allItemsResourceID;
    }

    @Override
    public int getCount() {
        return allItemsResourceID.size();
    }

    @Override
    public Object getItem(int position) {
        return allItemsResourceID.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        Campanha name = allItemsResourceID.get(position);
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.campanhas_item, null);
            viewHolder = new ViewHolder();
            viewHolder.nomec = convertView.findViewById(R.id.tv_Cnome);
            viewHolder.nomee = convertView.findViewById(R.id.tv_Enome);
            viewHolder.desc = convertView.findViewById(R.id.tv_desc);
            viewHolder.pontos = convertView.findViewById(R.id.tv_pontos);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        int ponto=100;
        viewHolder.nomec.setText(name.getNome());
        viewHolder.nomee.setText(name.getNomeempresa());
        viewHolder.desc.setText(name.getDesricao());
        viewHolder.pontos.setText("necessario "+name.getPontos_ganhos()+" para usar");


        return convertView;
    }

    class ViewHolder{
        TextView nomec;
        TextView nomee;
        TextView desc;
        TextView pontos;
    }
}