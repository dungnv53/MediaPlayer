package com.media.load;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.media.R;

public class ViewVideo extends Activity implements OnClickListener{
      private String filename;
      private TextView title;
      private ImageButton open;
      private ImageButton back;
      private ImageButton next;
      private Cursor mCursor;
      private VideoView viview;
      private int count;
      private int curId;
      int column_index;
      Animation anim1;
      @Override
      public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            System.gc();
        	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.video_play);
            initComponents();
      }
      private void initComponents() {
    	  System.gc();
    	  
          String[] proj = { MediaStore.Video.Media._ID,
          		MediaStore.Video.Media.DATA,
          		MediaStore.Video.Media.DISPLAY_NAME,
          		MediaStore.Video.Media.SIZE,
          		MediaStore.Video.Media.DURATION};
          mCursor = managedQuery(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
          		proj, null, null, null);
          count = mCursor.getCount();
          
          Intent i = getIntent();
          Bundle extras = i.getExtras();
          curId = Integer.parseInt(extras.getString("positionVideo"));
          column_index = mCursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
          mCursor.moveToPosition(curId);
          filename = mCursor.getString(column_index);
          viview = (VideoView)findViewById(R.id.video_view);
          viview.setVideoPath(filename);
          viview.setMediaController(new MediaController(this));
          viview.requestFocus();
          viview.setDrawingCacheEnabled(true);
          viview.setDrawingCacheQuality(VideoView.DRAWING_CACHE_QUALITY_AUTO);
          viview.setKeepScreenOn(true);
          viview.setPadding(40, 40, 40,40);
          viview.start();
          viview.setOnCompletionListener(new OnCompletionListener()
          {

			@Override
			public void onCompletion(MediaPlayer arg0) {
				// TODO Auto-generated method stub
				next();
			}});
          String text = filename.substring(filename.lastIndexOf("/")+1);
          title = (TextView)findViewById(R.id.title_text_view_video);
          title.setText(text);
          title.setTextColor(Color.rgb(33,255,33));
          anim1=AnimationUtils.loadAnimation(this, R.anim.anim);
          anim1.setDuration(2000);
          anim1.setRepeatCount(Animation.INFINITE);
          anim1.setRepeatMode(Animation.RESTART);
          title.setAnimation(anim1);
    	  back = (ImageButton)findViewById(R.id.back_button);
    	  open = (ImageButton)findViewById(R.id.stop_button);
    	  next = (ImageButton)findViewById(R.id.next_button);
    	  back.setOnClickListener(this);
    	  open.setOnClickListener(this);
    	  next.setOnClickListener(this);
      }
      @Override
      public void onClick(View v) {
    	  switch(v.getId()) {
    	  	//back
    	  	case R.id.back_button:
    	  		
    	  		if(curId == 0) {
					curId = count-1;
				} else {
					curId--;
				}
				
    	  		//int column_index = mCursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
				mCursor.moveToPosition(curId);
				filename = mCursor.getString(column_index);
				viview.setVideoPath(filename);
	            viview.setMediaController(new MediaController(this));
	            viview.requestFocus();
	            viview.start();
	            
	            String text = filename.substring(filename.lastIndexOf("/")+1);
	            title.setText(text);
				break;
    	  		
			//open
    	  	case R.id.stop_button:
    	  		viview.stopPlayback();
    	  		finish();
    	  		break;
    	  	
    	  	//next
    	  	case R.id.next_button:
    	  		next();
				break;
    	  }
      }
      private void next()
      {
	  		if(curId == count-1) {
				curId = 0;
			} else {
				curId++;
			}
	  		int column_index1 = mCursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
			mCursor.moveToPosition(curId);
			filename = mCursor.getString(column_index1);
			viview.setVideoPath(filename);
            viview.setMediaController(new MediaController(this));
            viview.requestFocus();
            viview.start();
            String text1 = filename.substring(filename.lastIndexOf("/")+1);
            title.setText(text1);
      }
}

