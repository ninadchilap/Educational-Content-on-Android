package com.android.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.MediaController;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;
import android.widget.TextView;
import android.widget.Toast;

public class Videoview extends Activity implements OnClickListener {
	public static final int OPEN_SRT_ACTIVITY = 1;

	public static final int OPEN_BOOKMARK_ACTIVITY = 2;
	public static final int OPEN_AUDIO_ACTIVITY = 3;
	NewVideoView myVideoView;
	String SrcPath = null;
	File file;
	TextView translation;
	Thread thread;
	boolean stop = false;
	int index = 0;
	String audioPath = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.videoview);
		
		Intent i = getIntent();
		Bundle extras = i.getExtras();
		
		SrcPath = extras.getString("videofilename");
		myVideoView = (NewVideoView) findViewById(R.id.videoView1);
		 MediaController mediaController = new MediaController(this);
	     mediaController.setAnchorView(myVideoView);
		myVideoView.requestFocus();
		myVideoView.setMediaController(mediaController);
		myVideoView.setVideoPath(SrcPath);
		myVideoView.start();
		
	}

	
	
		
	@Override
	public void onPause() {
		super.onPause();
		stop = true;
		index = 0;
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	
	

	



}