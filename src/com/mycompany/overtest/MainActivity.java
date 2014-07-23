package com.mycompany.overtest;

import android.app.*;
import android.content.*;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.*;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;
import android.view.*;
import android.webkit.MimeTypeMap;
import android.webkit.WebChromeClient;
import android.widget.*;
import java.io.*;
import java.lang.Process;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends Activity {

    Actions actions = new Actions();
    Intent globalService;
	Intent list;
	Intent show;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //init
        globalService = new Intent(this,GlobalTouchService.class);

        //create WF folder
        actions.CreateMainFolder();
	}

	//btn start
    public void buttonStartClicked(View start){
        actions.CreateMainFolder();
        File folder = new File(Environment.getExternalStorageDirectory() + "/MANUAL/workflow");
        if (!folder.exists()) {
            folder.mkdirs();
        }
        //start service
        startService(globalService);
 	}

	//btn stop
	public void buttonStopClicked(View stop){

        stopService(globalService);
        Toast.makeText(this, "Stop Service", Toast.LENGTH_SHORT).show();
	}

	//btn empty
	public void buttonEmptyClicked(View empty){

        Toast.makeText(this, "Delete old files", Toast.LENGTH_SHORT).show();
		stopService(globalService);
        //todo
		File flow = new File(Environment.getExternalStorageDirectory() + "/MANUAL/workflow");
        deleteDirPng(flow);
		File sett = new File(Environment.getExternalStorageDirectory() + "/MANUAL/settings");
		deleteDirTxt(sett);
	}

	//btn edit
    public void buttonEditClicked(View edit){

        list = new Intent(this,ListEditor.class);
        startActivity(list);

    }

	//btn view
    public void buttonViewClicked(View view){

        //show = new Intent(this,Show.class);
        //startActivity(show);

        Intent intent = new Intent();
        intent.setAction(android.content.Intent.ACTION_VIEW);
        File file = new File(Environment.getExternalStorageDirectory() + "/MANUAL/test.html");
        intent.setDataAndType(Uri.fromFile(file), "text/html");
        startActivity(intent);

        //File file = new File(Environment.getExternalStorageDirectory().toString() + "/MANUAL/test.html");
        //Uri webPageUri = Uri.fromFile(file);

        //Intent intent = new Intent(Intent.ACTION_VIEW);
        //intent.setDataAndType(webPageUri, "text/html");
        //startActivity(intent);

    }

    private static boolean deleteDirPng(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDirPng(new File(dir, children[i]));
                if (!success) {
                        return false;
                }
            }
        }
        return (dir.getName().contains(".png"))? dir.delete() : false;
    }
		
	private static boolean deleteDirTxt(File dir) {
		if (dir != null && dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDirTxt(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		return (dir.getName().contains(".txt"))? dir.delete() : false;
	}

}

