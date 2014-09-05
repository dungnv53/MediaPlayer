package com.media.load;
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
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.media.R;

public class listaudio extends Activity {
      private static Cursor cursor;
      private static int audio_column_index;
      ListView Audiolist;
      int count;
      /** Called when the activity is first created. */
      @Override
      public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);         
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.list);
            init();
            setTitle(R.string.audio_list);
      }

      private void init() {
            System.gc();
            String[] proj = { MediaStore.Audio.Media._ID,
            		MediaStore.Audio.Media.DATA,
            		MediaStore.Audio.Media.TITLE,
            		MediaStore.Audio.Media.SIZE,
            		MediaStore.Audio.Media.DURATION,
            		MediaStore.Audio.Media.DISPLAY_NAME};
            cursor = managedQuery(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            		proj, null, null, null);
            count = cursor.getCount();
            if(count==0) Toast.makeText(getApplicationContext(), "Please copy some music into your SD card", Toast.LENGTH_SHORT);
            Audiolist = (ListView) findViewById(R.id.List);
            Audiolist.setAdapter(new AudioAdapter(getApplicationContext()));
            Audiolist.setOnItemClickListener(audiogridlistener);
      }

      private OnItemClickListener audiogridlistener = new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position,long id) {
                  System.gc();
                  cursor.moveToPosition(position); 
                  Intent intent = new Intent(listaudio.this, ViewAudio.class);
                  intent.putExtra("positionAudio", ""+position);
                  
                  startActivity(intent);
            }
      };

      public class AudioAdapter extends BaseAdapter {
          private LayoutInflater mInflater;
          private Bitmap mp3icon,wavicon,othericon,wmaicon;

          public AudioAdapter(Context context) {
              // Cache the LayoutInflate dùng một lần duy nhất cho lần đầu gọi ,lần sau không phải khởi tạo lại.
              mInflater = LayoutInflater.from(context);

              // gán các icon và biến
              mp3icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.audio_mp3);
              wavicon = BitmapFactory.decodeResource(context.getResources(), R.drawable.audio_wav);
              wmaicon = BitmapFactory.decodeResource(context.getResources(), R.drawable.audio_wma);
              othericon=BitmapFactory.decodeResource(context.getResources(), R.drawable.file_audio_01);
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
              audio_column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE);
              cursor.moveToPosition(position);
              String id = cursor.getString(audio_column_index);
              audio_column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION);
              cursor.moveToPosition(position);
              String duration=convertTime(cursor.getInt(audio_column_index));
              System.out.println(audio_column_index);
              id += "         " + duration;
              holder.text.setHeight(40);
              holder.text.setTextColor(Color.rgb(33,255,33));
              holder.text.setText(id);
              holder.text.setText(id);
              audio_column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME);
              String name=cursor.getString(audio_column_index);
              int format= getFormat(name);
              switch(format)
              {
              	case 1:
              	{
                    holder.icon.setImageBitmap(mp3icon);
                    break;
              	}
              	case 2:
              	{
                    holder.icon.setImageBitmap(wavicon);
                    break;              		
              	}
              	case 3:
              	{
                    holder.icon.setImageBitmap(wmaicon);
                    break;              		
              	}
              	default:
              	{
              		 holder.icon.setImageBitmap(othericon);
              	}
              }
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
  	private int getFormat(String s)
  	{
  		String i="";
  		i=s.substring(s.length()-3, s.length());
  		if(i.equals("mp3"))
		return 1;
  		if (i.equals("wav"))return 2;
  		if(i.equals("wma"))return 3;
  		return 0;
  	}
}