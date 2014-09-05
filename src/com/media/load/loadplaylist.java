package com.media.load;
import com.media.R;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
public class loadplaylist extends TabActivity {
@Override
public void onCreate(Bundle icicle) {
	super.onCreate(icicle);
	setContentView(R.layout.tabs);
	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	TabHost tab=getTabHost();  ;
	TabSpec audioTabSpec = tab.newTabSpec("tid1");
	audioTabSpec.setIndicator("Audio").setContent(new Intent(this,listaudio.class));
	tab.addTab(audioTabSpec);
	TabSpec videoTabSpec = tab.newTabSpec("tid");
	videoTabSpec.setIndicator("Video").setContent(new Intent(this,listvideo.class));
	tab.addTab(videoTabSpec);
/*	TabSpec imageTabSpec = tab.newTabSpec("tid");
	imageTabSpec.setIndicator("Image").setContent(new Intent(this,listimage.class));
	tab.addTab(imageTabSpec);*/
	}
	}