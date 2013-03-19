/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.android.example;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pradnya
 */
public class AfterClick extends Activity {
  static List<String> ListDisplayed = new ArrayList<String>();
   String selectedItem;
        String completePath;
        String Path;
     public void onCreate(Bundle savedInstanceState)
    {
        
        //final String itemClicked = null;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
       Intent stdintent=getIntent();
       selectedItem = stdintent.getStringExtra("selectedItem");
       completePath = stdintent.getStringExtra("completePath");
       Path=stdintent.getStringExtra("Path");
            if(selectedItem.equalsIgnoreCase("HTML Contents"))
       {
           ListDisplayed = getHTMLFiles(completePath);
           
       }
       else if(selectedItem.equalsIgnoreCase("Video Contents"))
        {
             ListDisplayed = getFiles(completePath);       
        }
       else if(selectedItem.equalsIgnoreCase("PDF Contents"))
       {
         
           ListDisplayed= getFiles(completePath);     
            // System.out.println("pdf complete path"+ListDisplayed);  
          
       }
       
        ListView lv = (ListView) findViewById(R.id.lstCourses); 
            
            if(!ListDisplayed.isEmpty())
            {
             ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.row,R.id.rowTitle,ListDisplayed);
 
            lv.setAdapter(arrayAdapter);
            
              lv.setOnItemClickListener(new OnItemClickListener() {
        
            public void onItemClick(AdapterView<?> parent, View view, int position,
                    long id) 
            {
                   
                String item = ListDisplayed.get(position); 
                TextView tv = (TextView) view.findViewById(R.id.rowTitle);
                String ss = tv.getText().toString();
                String v_path = Path+"/Video Contents/"+ss;
                String p_path = Path+"/PDF Contents/"+ss;
                //String h_path = "/mnt/extsd/Educational Content/HTML Contents/"+ss;
                
               // System.out.println("item"+item);
                //System.out.println("selecteditem"+selectedItem);           
                     
                
                 if(selectedItem.equalsIgnoreCase("HTML Contents"))
                  {
                        File targetFile = new File(completePath+item);
                        Uri targetUri = Uri.fromFile(targetFile);
                        Intent intent;
                        intent = new Intent(Intent.ACTION_VIEW);                        
                        intent.setData(targetUri);
                        intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
                        startActivity(intent);                        
                        
                  }
                  if(selectedItem.equalsIgnoreCase("Video Contents"))
                  {
                        //System.out.println("path in video contents"+completePath+item);
                       Intent myIntent = new Intent(AfterClick.this, Videoview.class);			
                        myIntent.putExtra("videofilename",v_path);                     
             		startActivity(myIntent);
                
                  }
                    else if(selectedItem.equalsIgnoreCase("PDF Contents"))
                   {     
                   
                   
                        File file = new File(p_path);
                         Intent intent = new Intent(Intent.ACTION_VIEW);
                         intent.setDataAndType(Uri.fromFile(file),"application/pdf");
                       intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(intent); 
                    
                       }
                
               
        }});
            }
            
         
            }
            
    
    
     public List<String> getFiles(String DirectoryPath)
{
      ArrayList<String> MyFiles = new ArrayList<String>();
       System.out.println("Afterclick getcontent directory path"+DirectoryPath);
        
       File files = new File(DirectoryPath);
		List<String> list = new ArrayList<String>();
		 for (File f : files.listFiles()) {	          
		
					list.add(f.getName());
                                        System.out.println(f.getName());
		
	        }

                return list;

}
     
     
      public List<String> getHTMLFiles(String DirectoryPath)
{
      ArrayList<String> MyFiles = new ArrayList<String>();
       System.out.println("Afterclick getcontent directory path"+DirectoryPath);
        
       File files = new File(DirectoryPath);
		List<String> list = new ArrayList<String>();
		 for (File f : files.listFiles()) {
                     
		//list.add(f.getName());
                 System.out.println(f.getName());
                                        
                String[] filenameArray = f.getName().toString().split("\\.");
               	String extension = filenameArray[filenameArray.length-1];
    
            	if(extension.equalsIgnoreCase("html"))
                list.add(f.getName());
		
	        }

                return list;

}
}
