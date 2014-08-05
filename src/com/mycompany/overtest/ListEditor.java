package com.mycompany.overtest;

import android.app.*;
import android.content.*;
import android.database.*;
import android.graphics.*;
import android.net.*;
import android.os.*;
import android.provider.*;
import android.util.*;
import android.view.*;
import android.view.ContextMenu.*;
import android.widget.*;
import java.io.*;
import java.util.*;
import android.speech.tts.*;


public class ListEditor extends Activity {

	Actions actions = new Actions();
    Intent draw;

    //draw bundle
	public static String str = "";
    //file name for switch
	public String id = "";
    //bid name
    public String bid = "";
    //screen params
    public int width;
    public int height;

	//new gallery intent privates
	private static final int SELECT_PICTURE = 1;
	private String selectedImagePath;
	private static final int SWITCH_PICTURE = 2;

	ListView listview;
	//init main ArrayList
	ArrayList<String> array = new ArrayList<String>();
	//ArrayList<String> ss = new ArrayList<String>();

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        String path = Environment.getExternalStorageDirectory().toString()+"/MANUAL/workflow";
        Log.d("Files", "Path: " + path);
        File f = new File(path);
        File file[] = f.listFiles();
        Log.d("Files", "Size: "+ file.length);

        for (int i=0; i < file.length; i++) {
            if (file[i].getName().endsWith(".png")) {
                array.add(file[i].getName());
            }
        }

        Log.d("Files", "###############################################");
        Log.d("Files", "###############################################");
        Log.d("Files", "###############################################");
        Log.d("Files", "ARRAY ARRAY: "+ array);
        Log.d("Files", "###############################################");
        Log.d("Files", "###############################################");
        Log.d("Files", "###############################################");

        String[] place = array.toArray(new String[array.size()]);
        Arrays.sort(place);
        //actions.Settings("sequence.txt", place);

        listview = (ListView) findViewById(R.id.listview);
        listview.setAdapter(new yourAdapter(this, place));
        getIntent().setAction("Already created");

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        width = dm.widthPixels;
        height = dm.heightPixels;
        //Toast.makeText(getApplicationContext(), Integer.toString(width) + Integer.toString(height), Toast.LENGTH_LONG).show();

        Log.d("Files", "###############################################");
        Log.d("Files", "###############################################");
        Log.d("Files", "###############################################");
        for (int i=0; i<place.length; i++){
            Log.d("Files", "ARRAY PLACE: "+ place[i]);
        }
        Log.d("Files", "###############################################");
        Log.d("Files", "###############################################");
        Log.d("Files", "###############################################");
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	//Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit, menu);
		return true;
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == SELECT_PICTURE) {
				Uri selectedImageUri = data.getData();
		        selectedImagePath = getPath(selectedImageUri);
        	    //Toast.makeText(this, selectedImagePath, Toast.LENGTH_SHORT).show();
				File sdcard = Environment.getExternalStorageDirectory();
				String path = "MANUAL/workflow/img" + actions.id() + ".png";
                String pathCom = "/MANUAL/workflow/img" + actions.id() + ".png";
				
				File from = new File(selectedImagePath);
				File to = new File(sdcard, path);
				try {
					
					copy(from, to);
					compress(pathCom);
					
				} catch (IOException io){}
					
				//from.renameTo(to); 
			}
			
			if (requestCode == SWITCH_PICTURE){
				
				File file = new File(Environment.getExternalStorageDirectory() + "/MANUAL/workflow/" +  id);
				if (file.exists()){ 
					file.delete();	
				}
				array.remove(id);
				
				Uri selectedImageUri = data.getData();
		        selectedImagePath = getPath(selectedImageUri);
        	    //Toast.makeText(this, selectedImagePath, Toast.LENGTH_SHORT).show();
				File sdcard = Environment.getExternalStorageDirectory();
				String path = "MANUAL/workflow/" + id;
                String pathCom = "/MANUAL/workflow/" + id;
				
				File from = new File(selectedImagePath);
				File to = new File(sdcard, path);
				try {

					copy(from, to);
					compress(pathCom);

				} catch (IOException io){}
			}
		}
	}


	public void compress(String comp) {
        try {

            String path = Environment.getExternalStorageDirectory() + comp;

            BitmapFactory.Options buffer = new BitmapFactory.Options();
            buffer.inSampleSize = 2;
            Bitmap bmp = BitmapFactory.decodeFile(path, buffer);
			try {
            	if (bmp.getWidth() > width || bmp.getHeight() > height){
					if (bmp.getWidth() > bmp.getHeight()){
						Bitmap resized = Bitmap.createScaledBitmap(bmp, height, width, true); //(int)(bmp.getHeight()*0.5)
						bmp.recycle();
						
						FileOutputStream out = new FileOutputStream(path);
						resized.compress(Bitmap.CompressFormat.PNG, 70, out);
						resized.recycle();		
						out.flush();
						out.close();
					}
					else {
						Bitmap resized = Bitmap.createScaledBitmap(bmp, width, height, true); //(int)(bmp.getHeight()*0.5)
						bmp.recycle();

						FileOutputStream out = new FileOutputStream(path);
						resized.compress(Bitmap.CompressFormat.PNG, 70, out);
						resized.recycle();		
						out.flush();
						out.close();
					}
				}
				
			} catch (OutOfMemoryError oum) {}

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("saveBitmap", e.getMessage());
        }
	}

	public String getPath(Uri uri) {
		if( uri == null ) {
			return null;
		}
		String[] projection = { MediaStore.Images.Media.DATA };
	        Cursor cursor = managedQuery(uri, projection, null, null, null);
		if( cursor != null ){
			int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			return cursor.getString(column_index);
		}
		return uri.getPath();
	}
	
	public void copy(File src, File dst) throws IOException { 
		InputStream in = new FileInputStream(src); 
		OutputStream out = new FileOutputStream(dst); 
		// Transfer bytes from in to out 
		byte[] buf = new byte[1024]; 
		int len; 
		while ((len = in.read(buf)) > 0) { 
			out.write(buf, 0, len); 
		} in.close(); 
		out.close(); 
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
	
	public void reload() {

		Intent intent = getIntent();
		overridePendingTransition(0, 0);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		finish();

		overridePendingTransition(0, 0);
		startActivity(intent);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			//case R.id.empty:
				//Toast.makeText(this, "Delete old files", Toast.LENGTH_SHORT).show();
				////stopService(globalService);
				////todo
				//File flow = new File(Environment.getExternalStorageDirectory() + "/MANUAL/workflow");
				//deleteDirPng(flow);
				//File sett = new File(Environment.getExternalStorageDirectory() + "/MANUAL/settings");
				//deleteDirTxt(sett);
				////String[] place = array.toArray(new String[array.size()]);
				////Arrays.sort(place);
				////listview.setAdapter(new yourAdapter(this, place));
				//reload();
				//break;
				
			//case R.id.view:
				//Intent intentView = new Intent();
				//intentView.setAction(android.content.Intent.ACTION_VIEW);
				//File file = new File(Environment.getExternalStorageDirectory() + "/MANUAL/test.html");
				//intentView.setDataAndType(Uri.fromFile(file), "text/html");
				//startActivity(intentView);
				//break;
				
			case R.id.expected:

                String[] def = new String[3];
                def[0] = "Expected result:";
                def[1] = "Actual result:";
                def[2] = "Error log:";
                actions.Settings("expected.txt", def);

				Intent intentRes = new Intent();
				intentRes.setAction(android.content.Intent.ACTION_VIEW);
				File fileRes = new File(Environment.getExternalStorageDirectory() + "/MANUAL/settings/expected.txt");
				intentRes.setDataAndType(Uri.fromFile(fileRes), "text/plain");
				startActivity(intentRes);
				break;
				
			case R.id.name:

                String[] deff = new String[1];
                deff[0] = "New bug report";
                actions.Settings("description.txt", deff);

				Intent intentAct = new Intent();
				intentAct.setAction(android.content.Intent.ACTION_VIEW);
				File fileAct = new File(Environment.getExternalStorageDirectory() + "/MANUAL/settings/description.txt");
				intentAct.setDataAndType(Uri.fromFile(fileAct), "text/plain");
				startActivity(intentAct);
				break;
				
			case R.id.gallery:
				// in onCreate or any event where your want the user to
				// select a file
				
				if (array.size() < 10){
					Intent intent = new Intent();
					intent.setType("image/*");
					intent.setAction(Intent.ACTION_GET_CONTENT);
					startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
				} else {
					Toast.makeText(this, "theee are " + Integer.toString(array.size()) + " images in the list, max 10", Toast.LENGTH_SHORT).show();
				}
				break;
				
			//save file
			case R.id.report:
				//Create folder
                actions.CreateMainFolder();
                Toast.makeText(this, "Generating report ", Toast.LENGTH_SHORT).show();
                try {
                    File fileRep = new File(Environment.getExternalStorageDirectory() + "/MANUAL/workflow/test.html");
                    fileRep.createNewFile();
                    //String data = "hello";
                    //write the bytes in file
                    if(fileRep.exists()) {
                        OutputStream fo = new FileOutputStream(fileRep);
                        PrintWriter pw = new PrintWriter(fo);
                        pw.println(genHtml());
                        pw.flush();
                        pw.close();
                        //fo.write(data);
                        fo.close();
                        System.out.println("file created: "+fileRep);
                    }
                } catch (IOException io){io.printStackTrace();}
				break;

        }
		return true;
	}

    @Override
	protected void onResume() {
		String action = getIntent().getAction();
		if(action == null || !action.equals("Already created")) {
            Log.v("Example", "Force restart");
            Intent intent = new Intent(this, ListEditor.class);
            startActivity(intent);
            finish();
		}
		else {
            getIntent().setAction(null);
	 	}
	    	super.onResume();	
            //super.onResume();
            //this.onCreate(null);
	}

	//String[] place = {"data1", "data2"};

    public int getIndex(String category) {
        return array.indexOf(category);
    }

    public void buttonUpClicked(View start) {

        Button b = (Button)start;
        String bid = b.getContentDescription().toString();

        Log.d("Files", "TEXT: " + bid);
        Log.d("Files", "TEXT: " + getIndex(bid));

        int buttonIndex = getIndex(bid);
        if (buttonIndex == 0) {
                Log.d("Files", "ITEM IS LAST");
        }
        else {
            Log.d("Files", "SIZE: " + array.size());
            Collections.swap(array,getIndex(bid),getIndex(bid) - 1);
        }

        String[] place = array.toArray(new String[array.size()]);
        listview.setAdapter(new yourAdapter(this, place));
    }


    public void buttonDownClicked(View start){

        Button b = (Button)start;
        String bid = b.getContentDescription().toString();

        Log.d("Files", "TEXT: " + bid);
        Log.d("Files", "TEXT: " + getIndex(bid));

        int buttonIndex = getIndex(bid);
        if (buttonIndex == array.size() - 1) {
                Log.d("Files", "ITEM IS LAST");
        }
        else {
                Log.d("Files", "SIZE: " + array.size());
                Collections.swap(array,getIndex(bid),getIndex(bid) + 1);
        }

        String[] place = array.toArray(new String[array.size()]);
        listview.setAdapter(new yourAdapter(this, place));
    }
	
	public void buttonDrawClicked(View drawview) {

		Button b = (Button)drawview;
        String bid = b.getContentDescription().toString();
		
		Bundle bundle = new Bundle();
		String k = "file name";
		bundle.putString(k, bid);
		Log.d("Files", "BUNDLE: " + bid);
		
		str = bid;
		draw = new Intent(this,Draw.class);
		draw.putExtras(bundle);
        startActivity(draw);
	}

	public void buttonDelClicked(View start) {
		
		Button b = (Button)start;
		String bid = b.getContentDescription().toString();
		final String fb = bid;
		
		new AlertDialog.Builder(this)
			.setTitle("Delete screenshot")
			.setMessage("This screenshot will be deleted!")
			.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) { 
					File file = new File(Environment.getExternalStorageDirectory() + "/MANUAL/workflow/" +  fb);
					if (file.exists()){ 
						file.delete();	
					}
					File text = new File(Environment.getExternalStorageDirectory() + "/MANUAL/settings/" +  fb + ".txt");
					if (text.exists()){
						text.delete();
					}
					array.remove(fb);
					String[] place = array.toArray(new String[array.size()]);
					Arrays.sort(place);
					reload();
					
						}
			})
			.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) { 
					// do nothing
				}
			})
			.setIcon(android.R.drawable.ic_dialog_alert)
			.show();
			
	}
	
	public void buttonSwitchClicked(View swi) {

		Button b = (Button)swi;
        String bid = b.getContentDescription().toString();
		
		id = bid;
		
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(Intent.createChooser(intent, "Switch Picture"), SWITCH_PICTURE);
		//Toast.makeText(getApplicationContext(), bid + " " + id, Toast.LENGTH_LONG).show();
		
		String[] place = array.toArray(new String[array.size()]);
		Arrays.sort(place);
		listview.setAdapter(new yourAdapter(this, place));	
	}

	public void buttonEditClicked(View ed) {

        Button b = (Button)ed;
        bid = b.getContentDescription().toString();

        //for long click
        //ed.setOnCreateContextMenuListener(this);

        //for single click
        registerForContextMenu(ed);
        openContextMenu(ed);
        unregisterForContextMenu(ed);
    }

    public void TextClicked(View tc) {
        TextView b = (TextView)tc;
        bid = b.getContentDescription().toString();

        //for long click
        //ed.setOnCreateContextMenuListener(this);

        //for single click
        registerForContextMenu(tc);
        openContextMenu(tc);
        unregisterForContextMenu(tc);
    }

    public void ImageClicked(View ic) {
        ImageView b = (ImageView)ic;
        bid = b.getContentDescription().toString();

        //for long click
        //ed.setOnCreateContextMenuListener(this);

        //for single click
        registerForContextMenu(ic);
        openContextMenu(ic);
        unregisterForContextMenu(ic);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view,ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, view, menuInfo);
        switch(view.getId())
		{
			case R.id.text:
				CreateMenu(menu);
				break;
			case R.id.imageView:
				CreateMenuImage(menu);
				break;
				
		}
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        return MenuChoice(item);
    }
	
	private void CreateMenuImage(Menu menu)
    {
        MenuItem mnu1 = menu.add(0, 8, 8, "Draw");
        {
            mnu1.setAlphabeticShortcut('i');
        }
        MenuItem mnu3 = menu.add(0, 10, 10, "Switch");
        {
            mnu3.setAlphabeticShortcut('k');
        }
        MenuItem mnu4 = menu.add(0, 11, 11, "Delete");
        {
            mnu4.setAlphabeticShortcut('l');
        }
    }

    private void CreateMenu(Menu menu)
    {
        MenuItem mnu1 = menu.add(0, 0, 0, "Add \"Tap\"");
        {
            mnu1.setAlphabeticShortcut('a');
        }
        MenuItem mnu2 = menu.add(0, 1, 1, "Add \"Double tap\"");
        {
            mnu2.setAlphabeticShortcut('b');
        }
        MenuItem mnu3 = menu.add(0, 2, 2, "Add \"Press\"");
        {
            mnu3.setAlphabeticShortcut('c');
        }
        MenuItem mnu4 = menu.add(0, 3, 3, "Add \"Press long\"");
        {
            mnu4.setAlphabeticShortcut('d');
        }
        MenuItem mnu5 = menu.add(0, 4, 4, "Add \"Swipe\"");
        {
            mnu5.setAlphabeticShortcut('e');
        }
        MenuItem mnu6 = menu.add(0, 5, 5, "Add \"Drug\"");
        {
            mnu6.setAlphabeticShortcut('f');
        }
        MenuItem mnu7 = menu.add(0, 6, 6, "Add \"Pinch\"");
        {
            mnu7.setAlphabeticShortcut('g');
        }
        MenuItem mnu8 = menu.add(0, 7, 7, "Add description");
        {
            mnu8.setAlphabeticShortcut('h');
        }
    }
	
    private boolean MenuChoice(MenuItem item)
    {
		File fileTap = new File(Environment.getExternalStorageDirectory() + "/MANUAL/settings/" + bid + ".txt");
        switch (item.getItemId())
        {
            case 0:
                //Toast.makeText(this, "You clicked on Item 1",Toast.LENGTH_LONG).show();
                
				if (fileTap.exists()){
					fileTap.delete();
					String[] deftap = new String[1];
					deftap[0] = "Tap";
					actions.Settings(bid + ".txt", deftap);
					reload();
				}
				else {
					String[] deftap = new String[1];
					deftap[0] = "Tap";
					actions.Settings(bid + ".txt", deftap);
					reload();
				}			
				return true;
            case 1:
                //Toast.makeText(this, "You clicked on Item 2",Toast.LENGTH_LONG).show();
                
				if (fileTap.exists()){
					fileTap.delete();
					String[] defdt = new String[1];
					defdt[0] = "Double tap";
					actions.Settings(bid + ".txt", defdt);
					reload();
				}
				else {
					String[] defdt = new String[1];
					defdt[0] = "Double tap";
					actions.Settings(bid + ".txt", defdt);
					reload();
				}			
				return true;
            case 2:
                //Toast.makeText(this, "You clicked on Item 3",Toast.LENGTH_LONG).show();
                
				if (fileTap.exists()){
					fileTap.delete();
					String[] defpres = new String[1];
					defpres[0] = "Press";
					actions.Settings(bid + ".txt", defpres);
					reload();
				}
				else {
					String[] defpres = new String[1];
					defpres[0] = "Press";
					actions.Settings(bid + ".txt", defpres);
					reload();
				}			
				return true;
            case 3:
                //Toast.makeText(this, "You clicked on Item 4",Toast.LENGTH_LONG).show();
				
				if (fileTap.exists()){
					fileTap.delete();
					String[] defpl = new String[1];
					defpl[0] = "Press long";
					actions.Settings(bid + ".txt", defpl);
					reload();
				}
				else {
					String[] defpl = new String[1];
					defpl[0] = "Press long";
					actions.Settings(bid + ".txt", defpl);
					reload();
				}			
				return true;
            case 4:
                //Toast.makeText(this, "You clicked on Item 5",Toast.LENGTH_LONG).show();
                
				if (fileTap.exists()){
					fileTap.delete();
					String[] defsw = new String[1];
					defsw[0] = "Swipe";
					actions.Settings(bid + ".txt", defsw);
					reload();
				}
				else {
					String[] defsw = new String[1];
					defsw[0] = "Swipe";
					actions.Settings(bid + ".txt", defsw);
					reload();
				}			
				return true;
            case 5:
                //Toast.makeText(this, "You clicked on Item 6",Toast.LENGTH_LONG).show();
                
				if (fileTap.exists()){
					fileTap.delete();
					String[] defdra = new String[1];
					defdra[0] = "Drag";
					actions.Settings(bid + ".txt", defdra);
					reload();
				}
				else {
					String[] defdra = new String[1];
					defdra[0] = "Drag";
					actions.Settings(bid + ".txt", defdra);
					reload();
				}			
				return true;
            case 6:
                //Toast.makeText(this, "You clicked on Item 7",Toast.LENGTH_LONG).show();
                
				if (fileTap.exists()){
					fileTap.delete();
					String[] defpi = new String[1];
					defpi[0] = "Pinch";
					actions.Settings(bid + ".txt", defpi);
					reload();
				}
				else {
					String[] defpi = new String[1];
					defpi[0] = "Pinch";
					actions.Settings(bid + ".txt", defpi);
					reload();
				}			
				return true;
            case 7:
                //Toast.makeText(this, "You clicked on Item 7",Toast.LENGTH_LONG).show();
                String[] defdes = new String[1];
                defdes[0] = "";
                actions.Settings(bid + ".txt", defdes);

                Intent intent = new Intent();
                intent.setAction(android.content.Intent.ACTION_VIEW);
                File file = new File(Environment.getExternalStorageDirectory() + "/MANUAL/settings/" +  bid + ".txt");
                intent.setDataAndType(Uri.fromFile(file), "text/plain");
                startActivity(intent);
                return true;
			case 8:
				Bundle bundle = new Bundle();
				String k = "file name";
				bundle.putString(k, bid);
				Log.d("Files", "BUNDLE: " + bid);

				str = bid;
				draw = new Intent(this,Draw.class);
				draw.putExtras(bundle);
				startActivity(draw);
				return true;
			case 10:
				id = bid;

				Intent intentSwi = new Intent();
				intentSwi.setType("image/*");
				intentSwi.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(Intent.createChooser(intentSwi, "Switch Picture"), SWITCH_PICTURE);
				//Toast.makeText(getApplicationContext(), bid + " " + id, Toast.LENGTH_LONG).show();

				String[] place = array.toArray(new String[array.size()]);
				Arrays.sort(place);
				listview.setAdapter(new yourAdapter(this, place));	
				return true;
			case 11:
				final String fb = bid;

				new AlertDialog.Builder(this)
					.setTitle("Delete screenshot")
					.setMessage("This screenshot will be deleted!")
					.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) { 
							File file = new File(Environment.getExternalStorageDirectory() + "/MANUAL/workflow/" +  fb);
							if (file.exists()){ 
								file.delete();	
							}
							File text = new File(Environment.getExternalStorageDirectory() + "/MANUAL/settings/" +  fb + ".txt");
							if (text.exists()){
								text.delete();
							}
							array.remove(fb);
							String[] place = array.toArray(new String[array.size()]);
							Arrays.sort(place);
							reload();

						}
					})
					.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) { 
							// do nothing
						}
					})
					.setIcon(android.R.drawable.ic_dialog_alert)
					.show();
        }
        return false;
    }


    public void showToast(String message) {
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }
	
	public String genEmail(){
		StringBuilder text = new StringBuilder();
		int scrCount = array.size();

        String[] place = array.toArray(new String[array.size()]);
        Arrays.sort(place);
		text.append("Short description: " + actions.getDescription(Environment.getExternalStorageDirectory() + "/MANUAL/settings/description.txt") + "\n");
		
        for (int i=0; i<scrCount; i++) {
			int j = i + 1;
		text.append("Step " + j);
		text.append("Description: " + actions.getDescription(Environment.getExternalStorageDirectory() + "/MANUAL/settings/" + place[i] + ".txt"));
		}
		text.append(actions.getHtmlDescription(Environment.getExternalStorageDirectory() + "/MANUAL/settings/expected.txt"));
		
		return text.toString();
	}
	
    public String genHtml() {
        StringBuilder tHtml = new StringBuilder();
        tHtml.append(actions.head());
		//tHtml.append("<p>Hello <a href='/sdcard/MANUAL/img1394709648.png'>Java</a></p>" + "\n");
        int scrCount = array.size();

        String[] place = array.toArray(new String[array.size()]);
        Arrays.sort(place);

        for (int i=0; i<scrCount; i++) {
			int j = i + 1;
            //todo
			
			String path = Environment.getExternalStorageDirectory() + "/MANUAL/workflow/" + place[i];

            BitmapFactory.Options buffer = new BitmapFactory.Options();
            buffer.inSampleSize = 6;
            Bitmap bmp = BitmapFactory.decodeFile(path, buffer);
			
			tHtml.append("<p>Step " + j + "</p>" + "\n");
			tHtml.append("<p>Description: " + actions.getDescription(Environment.getExternalStorageDirectory() + "/MANUAL/settings/" + place[i] + ".txt") + "</p>" + "\n");
			if (bmp.getWidth() > bmp.getHeight()){
				tHtml.append("<img src='" + place[i] + "' width='340px' height='200px' />" + "\n");
			}
			else {
				tHtml.append("<img src='" + place[i] + "' width='200px' height='340px' />" + "\n");
				//base64 encoded example
				//tHtml.append("<img src='data:image/jpeg;base64," + actions.decoder(place[i]) + "' width='200px' height='340px' /><br>" + "\n");
			}
            //Toast.makeText(getApplicationContext(), getDescription(Environment.getExternalStorageDirectory() + "/MANUAL/settings" + "/" + array.get(i) + ".txt"), Toast.LENGTH_LONG).show();
        }

        //tHtml.append("<img src='/sdcard/MANUAL/" + glo.getListOfFiles().get(0) + "' width='200px' height='300px' />" + "\n");
        tHtml.append(actions.foot());
        return tHtml.toString();
    }
}


class yourAdapter extends BaseAdapter {

    Actions actions = new Actions();

    //screen params
    public int width;
    public int height;

	public Matrix matrix() {
        	Matrix matrix = new Matrix();
        	matrix.postScale(1f, 1f);
        	return matrix;
    	}

    //ADAPTER
    Context context;
    String[] data;
    private static LayoutInflater inflater = null;

    public yourAdapter(Context context, String[] data) {

        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        width = dm.widthPixels;
        height = dm.heightPixels;


        Log.d("Files", "###############################################");
        Log.d("Files", "###############################################");
        Log.d("Files", "###############################################");
        for (int i=0; i<data.length; i++){
            Log.d("Files", "ARRAY DATA: "+ data[i]);
        }
        Log.d("Files", "###############################################");
        Log.d("Files", "###############################################");
        Log.d("Files", "###############################################");
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return data[position];
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // TODO Auto-generated method stub
        View vi;
        //if (vi == null) {
        vi = inflater.inflate(R.layout.row, null);

        Log.d("Files", "###############################################");
        Log.d("Files", "ARRAY DATA: " + Integer.toString(width));
        Log.d("Files", "###############################################");


        StringBuilder desc = new StringBuilder();
        desc.append("Step: " + Integer.toString(position + 1) + "\n\n");
        desc.append(actions.getDescription(Environment.getExternalStorageDirectory() + "/MANUAL/settings/" + data[position] + ".txt"));

        TextView text = (TextView) vi.findViewById(R.id.text);
        text.setText(desc);
        text.setContentDescription(data[position]);
        text.setWidth(width/3);

        //Button btnUp = (Button) vi.findViewById(R.id.buttonUp);
        //btnUp.setText("up");
        //btnUp.setContentDescription(data[position]);

        //Button btnDown = (Button) vi.findViewById(R.id.buttonDown);
        //btnDown.setText("down");
        //btnDown.setContentDescription(data[position]);

//        Button btnDraw = (Button) vi.findViewById(R.id.buttonDraw);
//        btnDraw.setText("Dr");
//        btnDraw.setContentDescription(data[position]);
//
//        Button btnEdit = (Button) vi.findViewById(R.id.buttonEdit);
//        btnEdit.setText("Ed");
//        btnEdit.setContentDescription(data[position]);
//
//        Button btnSwitch = (Button) vi.findViewById(R.id.buttonSwitch);
//        btnSwitch.setText("Sw");
//        btnSwitch.setContentDescription(data[position]);
//
//        Button btnDel = (Button) vi.findViewById(R.id.buttonDel);
//        btnDel.setText("Dl");
//        btnDel.setContentDescription(data[position]);

        //Log.d("Files", "DATA: " + data[position]);

        try{
            ImageView mImg = (ImageView) vi.findViewById(R.id.imageView);
            BitmapTask btmt = new BitmapTask();
            btmt.setImageView(mImg);
            btmt.execute(data[position]);
            mImg.setContentDescription(data[position]);
        } catch (Exception e){
            e.printStackTrace();
        }

        return vi;
    }
	
	public class BitmapTask extends AsyncTask<String, Void, Bitmap> {

		private ImageView imageView;
		
        @Override
        protected Bitmap doInBackground(String... bmt) {

            BitmapFactory.Options buffer = new BitmapFactory.Options();
            buffer.inSampleSize = 4;
            Bitmap bm = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/MANUAL/workflow/" + bmt[0], buffer);
            int targetWidth  = bm.getWidth() / 1;
            int targetHeight = bm.getHeight() / 1;

            Bitmap size = Bitmap.createBitmap(bm, 0, 0, targetWidth, targetHeight, matrix(), true);
            return size;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
                this.imageView.setImageBitmap(result);
        }
		
		public void setImageView(ImageView ImageView){
			this.imageView = ImageView;
		}

    }

}

