package com.example.hlf;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter
{
    private Context miContext;
    private ArrayList <Bitmap> files;

    //connstructor
    public ImageAdapter(Context context, ArrayList <Bitmap> files){
        this.miContext = context;
        this.files = files;
    }

    @Override
    public int getCount() {
        return files.size();
    }

    @Override
    public Object getItem(int position) {
        return files.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent ) {
        //con "getSystemService" recuperamos la instancia de layoutinflater conectada a miContext.
        LayoutInflater miLayoutInflater = (LayoutInflater) miContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //añade nueva jerarquía desde el recurso "grid_elemento.xml"
        ImageView vista = (ImageView) miLayoutInflater.inflate(R.layout.grid_elemento, null);

        //enlazamos los recursos de la interfaz
        ImageView visView = vista.findViewById(R.id.grid);

        visView.post((Runnable) new AsyncTask<Void, Void, Void>() {
            Bitmap miBitmap;

            @Override
            protected Void doInBackground(Void... voids) {
                miBitmap = getPicFromAsset(visView, files);
                return null;
            }
            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
                visView.setImageBitmap(miBitmap);
            }
        }.execute());

        //if (files.size() > 0) {
        //    imageView.setImageBitmap(files.get(position));
        //}

        //imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        //imageView.setLayoutParams(new GridView.LayoutParams(150, 150));
        return vista;
    }

    private Bitmap getPicFromAsset(ImageView visView, ArrayList<Bitmap> files) {
        final int targetw = visView.getWidth();
        final int targetH = visView.getHeight();
        AssetManager assetManager = null;

        if (targetw == 0 || targetH == 0) {
            //view has no dimension set

        } else try {
            //InputStream img = this.miContext.getAssets().open("img");
            //proporcionamos acceso al archivo sin procesar
            assetManager = this.miContext.getAssets();
            InputStream img = assetManager.open("cuadricul1.jpg");
            BitmapFactory.Options obj = new BitmapFactory.Options();
            obj.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(img, new Rect(-1, -1, -1, -1), obj);

            final int fotoWidth = obj.outWidth;
            final int fotoHeight = obj.outHeight;

            final int escala = Math.min(fotoWidth / targetw / fotoHeight, targetH);

            obj.inJustDecodeBounds = false;
            obj.inSampleSize = escala;
            obj.inPurgeable = true;

            BitmapFactory.decodeStream(img, new Rect(-1, -1, -1, -1), obj);

        } catch (IOException e) {
            e.printStackTrace();

        }
        
        try{
            assetManager.list("cuadricul1.jpg");
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
