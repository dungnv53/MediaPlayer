package com.media.load;
import com.media.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class listvideo extends Activity {
	private static Cursor cursor;
    private ListView Videolist;
    int i;
    int count;
    private static int video_column_index;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        Videolist = (ListView) findViewById(R.id.List);
        init();
    }
        private void init() {
              System.gc();
              String[] str = { MediaStore.Video.Media._ID,
              		MediaStore.Video.Media.DATA,
              		MediaStore.Video.Media.TITLE,
              		MediaStore.Video.Media.SIZE,
              		MediaStore.Video.Media.DURATION};
              cursor = managedQuery(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            		  str, null, null, null);
              count = cursor.getCount();
              if(count==0) Toast.makeText(getApplicationContext(), "Please copy some video into your SD card", Toast.LENGTH_SHORT);
              Videolist = (ListView) findViewById(R.id.List);
              Videolist.setAdapter(new VideoAdapter(getApplicationContext()));
              Videolist.setOnItemClickListener(videogridlistener);
        }

    private OnItemClickListener videogridlistener = new OnItemClickListener() {
          public void onItemClick(AdapterView<?> parent, View v, int position,long id) {
                System.gc();
                cursor.moveToPosition(position); 
                Intent intent = new Intent(listvideo.this, ViewVideo.class);
                intent.putExtra("positionVideo", ""+position);
                
                startActivity(intent);
          }
    };

    public class VideoAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        private Bitmap mIcon1;

        public VideoAdapter(Context context) {
            // Cache the LayoutInflate to avoid asking for a new one each time.
            mInflater = LayoutInflater.from(context);

            // Icons bound to the rows.
            mIcon1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.video_2);
        }
        public int getCount() {
            return count;
        }
        public Object getItem(int position) {
            return position;
        }
        public long getItemId(int position) {
            return position;
        }
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.list_item_icon_text, null);
                holder = new ViewHolder();
                holder.text = (TextView) convertView.findViewById(R.id.text);
                holder.icon = (ImageView) convertView.findViewById(R.id.icon);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            video_column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE);
            cursor.moveToPosition(position);
            String id = cursor.getString(video_column_index);
            video_column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION);
            cursor.moveToPosition(position);
            String duration=convertTime(cursor.getInt(video_column_index));
            System.out.println(video_column_index);
            id += "         " + duration;
            holder.text.setHeight(40);
            holder.text.setTextColor(Color.rgb(33,255,33));
            holder.text.setText(id);
            holder.text.setText(id);
            holder.icon.setImageBitmap(mIcon1);

            return convertView;
        }

        class ViewHolder {
            TextView text;
            ImageView icon;
        }
    }
	private static String convertTime(int n)
	 {
		 String s = "";
		 int sec =(n/1000);
		 String hours = String.valueOf(sec/3600);
		 if(hours.length() ==1) hours = "0"+hours;
		 String minute = String.valueOf(sec/60);
		 if(minute.length()==1) minute = "0"+minute;
		 String secon = String.valueOf((sec%60));
		 if(secon.length()==1) secon = "0"+secon;
		 return s+= hours+":"+minute+":"+secon;
	 }
}