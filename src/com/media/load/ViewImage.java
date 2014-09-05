package com.media.load;


import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ViewImage extends Activity {
      private String filename;
      private Cursor actualimagecursor;
      private int image_column_index, count;
      @Override
      public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            System.gc();
        	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            Intent i = getIntent();
            final Bundle extras = i.getExtras();
            final BitmapFactory.Options bfo = new BitmapFactory.Options();
            bfo.inSampleSize = 2;
            filename = extras.getString("filename");
            image_column_index=Integer.parseInt(extras.getString("position"));
            final ImageView iv = new ImageView(getApplicationContext());
            String[] proj = { MediaStore.Images.Media.DATA };
            actualimagecursor = managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, proj,null, null, null);
            count=actualimagecursor.getCount();
            Bitmap bm = BitmapFactory.decodeFile(filename, bfo);
            iv.setImageBitmap(bm);
            iv.setOnClickListener(new OnClickListener()
            {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
        	  		if(image_column_index == count-1) {
        	  			image_column_index = 0;
        			} else {
        				image_column_index++;
        			}
        	  		actualimagecursor.moveToPosition(image_column_index);
        	  		int column_index1 = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        	  		filename=actualimagecursor.getString(column_index1);
                    Bitmap bm = BitmapFactory.decodeFile(filename, bfo);
                    iv.setImageBitmap(bm);
				}
            	
            });
            setContentView(iv);
      }
}
