package glody.com.bizdirect;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import glody.com.bizdirect.util.Empresa;

public class GridViewAdapter extends BaseAdapter {
    public ArrayList<Empresa> allItemsResourceID;
    private LayoutInflater inflater;
    Context context;
    public GridViewAdapter(Context context, ArrayList<Empresa> images) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        //
        allItemsResourceID = images;
        Log.d("Adapter", "Create Image Adapter " + allItemsResourceID.size());
    }
    GridViewAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }
    //conta o numero de empresa
    @Override
    public int getCount() {
        return allItemsResourceID.size();
    }
    //pega a empresa
    @Override
    public Object getItem(int position) {
        return allItemsResourceID.get(position);
    }
    //pega o id da empresa
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    //Configura a view
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        View view = convertView;
        Empresa name = allItemsResourceID.get(position);
        if (view == null) {
            view = inflater.inflate(R.layout.item_list, parent, false);
            holder = new ViewHolder();
            assert view != null;
            holder.imageView = (ImageView) view.findViewById(R.id.image);
            holder.title = (TextView) view.findViewById(R.id.cardTitle);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        if (!name.equals("")) {
            new DownLoadImageTask(holder.imageView).execute("http://193.137.7.33/~estgv17276/PINT4/uploads/"+name.getLogo());

            Log.e("image","http://193.137.7.33/~estgv17276/PINT4/uploads/"+name.getLogo());
            holder.imageView.setImageResource(R.drawable.logobiz);
            holder.title.setText(name.getNomeempresa());
        }
        return view;
    }
    private class DownLoadImageTask extends AsyncTask<String,Void, Bitmap> {
        ImageView imageView;

        public DownLoadImageTask(ImageView imageView){
            this.imageView = imageView;
        }

        /*
            doInBackground(Params... params)
                Override this method to perform a computation on a background thread.
         */
        protected Bitmap doInBackground(String...urls){
            String urlOfImage = urls[0];
            Bitmap logo = null;
            try{
                InputStream is = new URL(urlOfImage).openStream();
                /*
                    decodeStream(InputStream is)
                        Decode an input stream into a bitmap.
                 */
                logo = BitmapFactory.decodeStream(is);
            }catch(Exception e){ // Catch the download exception
                e.printStackTrace();
            }
            return logo;
        }

        /*
            onPostExecute(Result result)
                Runs on the UI thread after doInBackground(Params...).
         */
        protected void onPostExecute(Bitmap result){
            imageView.setImageBitmap(result);
        }
    }
}
class ViewHolder {
    ImageView imageView;
    TextView title;
}


