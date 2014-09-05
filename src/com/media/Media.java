package com.media;
import com.media.load.loadplaylist;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;

public class Media extends Activity implements OnClickListener{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		System.gc();
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main);
		View load_button = findViewById(R.id.load);
		load_button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				nextScreen(com.media.load.loadplaylist.class);
			}
		});
		View exit_button = findViewById(R.id.exit);
		exit_button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.exit(1);
			}
		});
    }
    private void nextScreen(Class<loadplaylist> s)
    {
    	Intent main = new Intent(this,s);
    	startActivity(main);    	
    }
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}