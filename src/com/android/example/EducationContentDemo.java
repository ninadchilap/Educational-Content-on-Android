package com.android.example;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.io.File;
import java.util.ArrayList;


import android.content.DialogInterface;
import android.content.Intent;

public class EducationContentDemo extends Activity
{
        ArrayList<String> ContentList=null;
        String Path;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        
        ArrayList<String> MyContents = new ArrayList<String>();
  
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
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
        	AlertDialog.Builder builder = new AlertDialog.Builder(this);
        	builder.setIcon(R.drawable.demo);
        	builder.setTitle("Educational Content files are not present in the tablet!!!");
        	builder.setMessage(	"Please check whether files are present at any one of the"+"\n"
        	+"following locations"+"\n"+"\n"+"1. go to mnt/sdcard/Educational Content"
        			+"\n"+"2. go to mnt/extsd/Educational Content")
        	
        	       .setCancelable(false)
        	       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
        	           public void onClick(DialogInterface dialog, int id) {
        	        	
        	        	   EducationContentDemo.this.finish();
        	        	
        	           }
        	       });
        	AlertDialog alert = builder.create();   
        	alert.show();
    	
    	
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


