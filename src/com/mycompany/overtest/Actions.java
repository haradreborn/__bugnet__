package com.mycompany.overtest;

import android.annotation.*;
import android.content.Context;
import android.graphics.*;
import android.os.*;
import android.util.*;
import android.view.Display;
import android.view.WindowManager;

import java.io.*;
import java.util.*;

import java.lang.Process;

/**
 * Created by harad on 15.03.14.
 */
public class Actions {

    public StringBuilder getDescription(String file){

        //Read text from file
        StringBuilder text = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
        }
        catch (IOException e) {
            //You'll need to add proper error handling here
        }

        return text;
    }
	
	public StringBuilder head(){
		StringBuilder head = new StringBuilder();
		head.append("<DOCTYPE html>" + "\n");
        head.append("<html lang='en-US'>" + "\n");
        head.append("<head>" + "\n");
        head.append("<meta charset=utf-8>" + "\n");
        head.append("<title>Hello</title>" + "\n");
        head.append("</head>" + "\n");
        head.append("<body>" + "\n");
        
		return head;
	}
	
	public StringBuilder foot() {
		StringBuilder foot = new StringBuilder();
		foot.append("</body>" + "\n");
        foot.append("</html>" + "\n");
		
		return foot;
	}
	
    /**
     * Take screenshot
     */  
    public void TakeShot(String id){
        try {
            Process sh = Runtime.getRuntime().exec("su", null,null);
            OutputStream os = sh.getOutputStream();
            os.write(("/system/bin/screencap -p " + Environment.getExternalStorageDirectory() + "/MANUAL/workflow" + "/img" +
                     id +
                    ".png").getBytes("ASCII"));
            os.flush();
            os.close();
            sh.waitFor();
        } catch (Exception e) {
//            Log.e(TAG, e.getMessage().toString());
        }
    }
    // generate file names
    public String id() {
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();
        return ts;
    }

    /**
     * Return list of files from path. <FileName, FilePath>
     *
     * @return Files name and path all files in a directory, that have ext = "jpeg", "jpg","png", "bmp", "gif"
     */
    public List<String> getListOfFiles() {
        //todo
        File files = new File(Environment.getExternalStorageDirectory() + "/MANUAL/workflow");
        FileFilter filter = new FileFilter() {

            private final List<String> exts = Arrays.asList("png");

            @Override
            public boolean accept(File pathname) {
                String ext;
                String path = pathname.getPath();
                ext = path.substring(path.lastIndexOf(".") + 1);
                return exts.contains(ext);
            }
        };

        final File [] filesFound = files.listFiles(filter);
        List<String> list = new ArrayList<String>();
        if (filesFound != null && filesFound.length > 0) {
            for (File file : filesFound) {
                list.add(file.getName());
            }
        }
        return list;
    }

   /**
    * Create main folder 
    */
    public void CreateMainFolder() {   
        File folder = new File(Environment.getExternalStorageDirectory() + "/MANUAL/workflow");
        if (!folder.exists()) {
//            Toast.makeText(this, "New Folder Created", Toast.LENGTH_SHORT).show();
            folder.mkdirs();
        }
    }

   /**
    * Create settings file
    */
	public void Settings(String sFileName, String[] sBody){
		try
		{
			File root = new File(Environment.getExternalStorageDirectory() + "/MANUAL", "settings");
			if (!root.exists()) {
				root.mkdirs();
			}
			File gpxfile = new File(root, sFileName);
			if (!gpxfile.exists()) {
				PrintWriter out = new PrintWriter(new FileWriter(gpxfile));  
				for (String s : sBody) {  
					out.println(s);  
				}  
				out.close();  
				
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}  

}
