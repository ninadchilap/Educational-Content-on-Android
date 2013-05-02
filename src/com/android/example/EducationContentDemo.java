package com.android.example;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class EducationContentDemo extends Activity
{
        ArrayList<String> ContentList=null;
        String Path;
        private ProgressDialog mProgressDialog, progressBar;
	    final Context context = this;
	    AlertDialog help_dialog;
	    File checkTar;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        
        ArrayList<String> MyContents = new ArrayList<String>();
  
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
      	checkTar = new File("/mnt/sdcard/EducationalContent.zip");
        if (new File("/mnt/extsd/Educational Content/").exists()||new File("/mnt/sdcard/Educational Content/").exists())
        {
        	if(new File("/mnt/extsd/Educational Content/").exists())
        	{
        		Path = "/mnt/extsd/Educational Content/";
        	}
        	
        	else
        	{
        		Path = "/mnt/sdcard/Educational Content/";
        	}
        	
        	ContentList = getContent(Path);
        
        if(!ContentList.isEmpty())
        	{
	          System.out.println("MyContents"+ContentList.toString());
	          displayList(ContentList);
        	}
        	else
        	{
        		Toast msg = Toast.makeText(EducationContentDemo.this,"Folder "+Path+" is empty", Toast.LENGTH_LONG);
        	}
        
        }
        
        else
        {
        	// download
          	// extract
          	// reboot
          	//Toast.makeText(context, "start downloading", Toast.LENGTH_SHORT).show();
          	LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
              final View layout = inflater.inflate(R.layout.download_source,null);

              // Building DatepPcker dialog
              AlertDialog.Builder builder = new AlertDialog.Builder(
                      EducationContentDemo.this);        	        	
              builder.setView(layout);
              builder.setTitle("Notice");
              builder.setCancelable(false);
              Button btnNO = (Button) layout.findViewById(R.id.btnNo);
             
              btnNO.setOnClickListener(new OnClickListener() {
              	public void onClick(View v) {
              		
              		AlertDialog.Builder builder = new AlertDialog.Builder(context);
                  	builder.setIcon(R.drawable.demo);
                  	builder.setTitle("blender animation videos are not present in the tablet!!!");
                  	builder.setMessage(	"Store the lecture videos at any one of the"+"\n"
                  	+"following locations"+"\n"+"\n"+"1. mnt/sdcard/Educational Content"
                  			+"\n"+"2. mnt/extsd/Educational Content")
                  	
                  	       .setCancelable(false)
                  	       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                  	           public void onClick(DialogInterface dialog, int id) {
                  	        	
                  	        	   EducationContentDemo.this.finish();
                  	        	
                  	           }
                  	       });
                  	AlertDialog alert = builder.create();   
                  	alert.show();
                  }	
              });	
            
              Button btnyes = (Button) layout.findViewById(R.id.btnyes);
              btnyes.setOnClickListener(new OnClickListener() {
                  public void onClick(View v) {
                      startDownload();
                      mProgressDialog = new ProgressDialog(context);
                      mProgressDialog.setMessage("Downloading file..");
                      mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                      mProgressDialog.setCancelable(false);
                      mProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE,"Cancel",new DialogInterface.OnClickListener() {
                        
                          public void onClick(DialogInterface dialog, int which) {
                              final AlertDialog.Builder builder = new AlertDialog.Builder(EducationContentDemo.this);
                              builder.setMessage("Are you sure you want cancel downloading?")
                                      .setCancelable(false)
                                      .setPositiveButton("Yes",
                                              new DialogInterface.OnClickListener() {
                                                  public void onClick(DialogInterface dialog, int id) {
                                                      dialog.dismiss();
                                                      help_dialog.dismiss();
                                                      finish();
                                                      android.os.Process.killProcess(android.os.Process.myPid());
                                                  }
                                              })
                                      .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                          public void onClick(DialogInterface dialog, int id) {
                                              mProgressDialog.show();
                                          }
                                      });
                              AlertDialog alert = builder.create();
                              alert.show();
                            
                          }
                      });
                      mProgressDialog.show();
                  }
              });
              
              help_dialog = builder.create();
              help_dialog.show();
              WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
              // customizing the width and location of the dialog on screen
              lp.copyFrom(help_dialog.getWindow().getAttributes());
              lp.width = 500;
              help_dialog.getWindow().setAttributes(lp);
          }
        }
    private void startDownload() {
     	if(isInternetOn()) {
             // INTERNET IS AVAILABLE, DO STUFF..
                 Toast.makeText(context, "Connected to network", Toast.LENGTH_SHORT).show();
             }else{
             // NO INTERNET AVAILABLE, DO STUFF..
                 Toast.makeText(context, "Network disconnected", Toast.LENGTH_SHORT).show();
                 //rebootFlag = 1;
                 AlertDialog.Builder builder = new AlertDialog.Builder(EducationContentDemo.this);
                 builder.setMessage("No Connection Found, please check your network setting!")
                         .setCancelable(false)
                         .setPositiveButton("OK",
                                 new DialogInterface.OnClickListener() {
                                     public void onClick(DialogInterface dialog, int id) {
                                         finish();
                                         android.os.Process
                                                 .killProcess(android.os.Process.myPid());
                                     }
                                 });
                 AlertDialog alert = builder.create();
                 alert.show();
               
             }  
     	/**
     	 * global github link for downloading image
     	 **/
     	String url = "http://www.it.iitb.ac.in/AakashApps/repo/EducationalContent.zip";
     	new DownloadFileAsync().execute(url);
     }    
 	
     private final boolean isInternetOn() {
     	// check internet connection via wifi   
     	 	ConnectivityManager connec =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
     	 	//NetworkInfo mwifi = connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
     	 	//mwifi = connec.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
             if( connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED ||
             connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTING ||
             connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTING ||
             connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED ) {
             	//Toast.makeText(this, connectionType + ” connected”, Toast.LENGTH_SHORT).show();
             	return true;
             } 
             else if( connec.getNetworkInfo(0).getState() == NetworkInfo.State.DISCONNECTED ||  
             		connec.getNetworkInfo(1).getState() == NetworkInfo.State.DISCONNECTED  ) {
             		//System.out.println(“Not Connected”);
             		return false;
             	}
             	return false;
     }

     private void spinner() {
	    	// will start spinner first and then extraction
	    	
	    	// start spinner to show extraction progress
	    	progressBar = new ProgressDialog(context);
	        progressBar.setCancelable(false);
	        progressBar.setMessage("Extracting files, please wait...");
	        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	        progressBar.show();
	        String zipFile = Environment.getExternalStorageDirectory() + "/EducationalContent.zip"; 
	        String unzipLocation = Environment.getExternalStorageDirectory()+"/"; 
	        new File("mnt/sdcard/Educational Content").mkdir();
	        new File("mnt/sdcard/Educational Content/HTML Contents").mkdir();
	        new File("mnt/sdcard/Educational Content/PDF Contents").mkdir();
	        new File("mnt/sdcard/Educational Content/Video Contents").mkdir();
	        new File("mnt/sdcard/Educational Content/HTML Contents/css").mkdir();
	        new File("mnt/sdcard/Educational Content/HTML Contents/images").mkdir();
	        new File("mnt/sdcard/Educational Content/HTML Contents/images_force_pressure_VIII").mkdir();
	        new File("mnt/sdcard/Educational Content/HTML Contents/images_motion_IX").mkdir();
	        new File("mnt/sdcard/Educational Content/HTML Contents/images_m_t_VII").mkdir();
	        new File("mnt/sdcard/Educational Content/HTML Contents/javascript").mkdir();
	        new File("mnt/sdcard/Educational Content/HTML Contents/images/new").mkdir();
	        
	       
	        
	        Decompress d = new Decompress(zipFile, unzipLocation); 
	        d.unzip(); 
	      //  Toast.makeText(context, "unzipped", Toast.LENGTH_SHORT).show();
	        
	       
	    }
	
	 class DownloadFileAsync extends AsyncTask<String, String, String> {
	    	/**
	    	 * download zip from URL and write in '/mnt/sdcard'
	    	 **/
	        @Override        	
	        public void onPreExecute() {
	            super.onPreExecute();
	        }

	        public String doInBackground(String... aurl) {
	            int count;

	            try {
	                URL url = new URL(aurl[0]);
	                URLConnection conexion = url.openConnection();
	                conexion.connect();

	                int lenghtOfFile = conexion.getContentLength();

	                InputStream input = new BufferedInputStream(url.openStream());
	                OutputStream output = new FileOutputStream(
	                        "/mnt/sdcard/EducationalContent.zip");

	                byte data[] = new byte[1024];

	                long total = 0;

	                while ((count = input.read(data)) != -1) {
	                    total += count;
	                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));
	                    output.write(data, 0, count);
	                }
	                output.flush();
	                output.close();
	                input.close();
	            } catch (Exception e) {
	            }
	            return null;

	        }

	        public void onProgressUpdate(String... progress) {
	            mProgressDialog.setProgress(Integer.parseInt(progress[0]));
	        }
	        
	        public void onPostExecute(String unused) {
	        	mProgressDialog.dismiss();
	        	help_dialog.dismiss();
	        	if (checkTar.exists()){
	        		spinner();
	        		
	        	}
	        	  new Thread() {
	        		    public void run() {
	        		        try{
	        		            // just doing some long operation
	        		            Thread.sleep(10000);
	        		         } catch (Exception e) {  }
	        		           // handle the exception somehow, or do nothing
	        		         

	        		         // run code on the UI thread
	        		        runOnUiThread(new Runnable() {

	        		            public void run() {
	        		                progressBar.dismiss();
	        		                Intent intent = getIntent();
	       	        		     finish();
	       	        		     startActivity(intent);
	        		            }
	        		        });
	        		    }
	        		     }.start();
	        		     
	        		    
	       // 	progressBar.dismiss();
	        	
	    }
	        //delete internal files during un-installation 
	        public boolean deleteFile (String name){
	            name = "aakash.sh";
	            name = "help_flag";
	            name = "copyFilesFlag.txt";
	            return false;
	           
	        }
	}



public ArrayList<String> getContent(String DirectoryPath)
{
      ArrayList<String> MyFiles = new ArrayList<String>();
        File f = new File(DirectoryPath);
        File[] files = f.listFiles();
        if (files.length == 0)
            return null;
        else {
            for (int i=0; i<files.length; i++){            
                MyFiles.add(files[i].getName());
              
            }
        }
                return MyFiles;

}

public void displayList(ArrayList<String> Contents)
{
    
           ListView lv = (ListView) findViewById(R.id.lstCourses); 
            
            if(!Contents.isEmpty())
            {
             ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.row,R.id.rowTitle,Contents);
 
            lv.setAdapter(arrayAdapter);
            
              lv.setOnItemClickListener(new OnItemClickListener() {
        
            public void onItemClick(AdapterView<?> parent, View view, int position,
                    long id) 
            {
                   
                String item = ContentList.get(position);  
                	
                Intent myIntent = new Intent(view.getContext(), AfterClick.class);
			 	myIntent.putExtra("selectedItem", item);
                myIntent.putExtra("completePath",Path+item+"/");
                myIntent.putExtra("Path", Path);
               	startActivity(myIntent);
                        
                
               
        }});
            }
                      }
            
            
    
}


