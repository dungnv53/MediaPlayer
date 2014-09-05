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
public class ViewAudio extends Activity implements OnClickListener{
      private String path;
      private TextView title;
      private ImageButton back,open,next;
      private Cursor mCursor;
      private VideoView auview;
      private int count;
      private int curId,title_col;
      int column_index ;
      Animation anim1;
      @Override
      public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            System.gc();
        	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.audio_play);
            initComponents();
      }
      
      private void initComponents() {
    	  System.gc();
    	  
          String[] proj = { MediaStore.Audio.Media._ID,
          		MediaStore.Audio.Media.DATA,
          		MediaStore.Audio.Media.DISPLAY_NAME,
          		MediaStore.Audio.Media.SIZE,
          		MediaStore.Audio.Media.TITLE,
          		MediaStore.Audio.Media.DURATION};
          mCursor = managedQuery(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
          		proj, null, null, null);
          count = mCursor.getCount();
          
          Intent i = getIntent();
          Bundle extras = i.getExtras();
          curId = Integer.parseInt(extras.getString("positionAudio"));
          column_index = mCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
          title_col=mCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
          mCursor.moveToPosition(curId);
          path = mCursor.getString(column_index);
          auview = (VideoView)findViewById(R.id.audio_view);
          auview.setVideoPath(path);
          auview.setBackgroundResource(R.drawable.background_play);
          auview.setMediaController(new MediaController(this));
          auview.setOnCompletionListener(new OnCompletionListener()
          {

			@Override
			public void onCompletion(MediaPlayer arg0) {
				// TODO Auto-generated method stub
				next();
			}});
          auview.requestFocus();
          auview.start();
          
          String text = mCursor.getString(title_col);
          title = (TextView)findViewById(R.id.title_text_view);
          title.setText(text);
          title.setTextColor(Color.rgb(33,255,33));
          anim1=AnimationUtils.loadAnimation(this, R.anim.anim);
          anim1.setDuration(3000);
          anim1.setRepeatCount(Animation.INFINITE);
          anim1.setRepeatMode(Animation.RESTART);
          title.setAnimation(anim1);
    	  back = (ImageButton)findViewById(R.id.back);
    	  open = (ImageButton)findViewById(R.id.stop);
    	  next = (ImageButton)findViewById(R.id.next);
    	  back.setOnClickListener(this);
    	  open.setOnClickListener(this);
    	  next.setOnClickListener(this);
      }
      @Override
      public void onClick(View v) {
    	  switch(v.getId()) {
    	  	case R.id.back:
    	  		
    	  		if(curId == 0) {
					curId = count-1;
				} else {
					curId--;
				}
				mCursor.moveToPosition(curId);
				path = mCursor.getString(column_index);
				auview.setVideoPath(path);
	            auview.setMediaController(new MediaController(this));
	            auview.requestFocus();
	            auview.start();
	            String text = path.substring(path.lastIndexOf("/")+1);
	            title.setText(text);
				break;
    	  	case R.id.stop:
    	  		auview.stopPlayback();
    	  		finish();
    	  		
    	  		break;
    	  	case R.id.next:
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
			path = mCursor.getString(column_index1);
			auview.setVideoPath(path);
            auview.setMediaController(new MediaController(this));
            auview.requestFocus();
            auview.start();
            
            String text1 = path.substring(path.lastIndexOf("/")+1);
            title.setText(text1);
      }
}