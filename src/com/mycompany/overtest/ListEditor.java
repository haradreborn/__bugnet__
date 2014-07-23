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
                array.add(file[i].getName());
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
                	Bitmap resized = Bitmap.createScaledBitmap(bmp,(int)(bmp.getWidth()*0.5), (int)(bmp.getHeight()*0.5), true);
                	bmp.recycle();
					FileOutputStream out = new FileOutputStream(path);
                	resized.compress(Bitmap.CompressFormat.PNG, 70, out);
					resized.recycle();		
                	out.flush();
                	out.close();
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

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
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
				
			//save file
			case R.id.report:
				//Create folder
                actions.CreateMainFolder();
                Toast.makeText(this, "Generating report ", Toast.LENGTH_SHORT).show();
                try {
                    File file = new File(Environment.getExternalStorageDirectory() + "/MANUAL/test.html");
                    file.createNewFile();
                    //String data = "hello";
                    //write the bytes in file
                    if(file.exists()) {
                        OutputStream fo = new FileOutputStream(file);
                        PrintWriter pw = new PrintWriter(fo);
                        pw.println(genHtml());
                        pw.flush();
                        pw.close();
                        //fo.write(data);
                        fo.close();
                        System.out.println("file created: "+file);
                    }
                } catch (IOException io){io.printStackTrace();}

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
            Log.d("Files", "###############################################");
            Log.d("Files", "###############################################");
            Log.d("Files", "###############################################");
            Log.d("Files", " ARRAY RESUME: "+ array);
            Log.d("Files", "###############################################");
            Log.d("Files", "###############################################");
            Log.d("Files", "###############################################");
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
		File file = new File(Environment.getExternalStorageDirectory() + "/MANUAL/workflow/" +  bid);
		if (file.exists()){ 
			file.delete();	
		}
        File text = new File(Environment.getExternalStorageDirectory() + "/MANUAL/settings/" +  bid + ".txt");
        if (text.exists()){
            text.delete();
        }
		array.remove(bid);
		String[] place = array.toArray(new String[array.size()]);
		Arrays.sort(place);
		listview.setAdapter(new yourAdapter(this, place));	
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
        String bid = b.getContentDescription().toString();

        String[] def = new String[1];
        def[0] = "step";
        actions.Settings(bid + ".txt", def);

        Intent intent = new Intent();
        intent.setAction(android.content.Intent.ACTION_VIEW);
        File file = new File(Environment.getExternalStorageDirectory() + "/MANUAL/settings/" +  bid + ".txt");
        intent.setDataAndType(Uri.fromFile(file), "text/plain");
        startActivity(intent);
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
			tHtml.append("<br><p>Step " + j + "</p>");
			tHtml.append("<p>Description: " + actions.getDescription(Environment.getExternalStorageDirectory() + "/MANUAL/settings/" + place[i] + ".txt") + "</p>");
            tHtml.append("<img src='" + Environment.getExternalStorageDirectory() + "/MANUAL/workflow/" + place[i] + "' width='200px' height='340px' /><br>" + "\n");
			//Toast.makeText(getApplicationContext(), getDescription(Environment.getExternalStorageDirectory() + "/MANUAL/settings" + "/" + array.get(i) + ".txt"), Toast.LENGTH_LONG).show();
        }

        //tHtml.append("<img src='/sdcard/MANUAL/" + glo.getListOfFiles().get(0) + "' width='200px' height='300px' />" + "\n");
        tHtml.append(actions.foot());
        return tHtml.toString();
    }
}


class yourAdapter extends BaseAdapter {

    Actions actions = new Actions();

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
        View vi = convertView;
        //if (vi == null) {
        vi = inflater.inflate(R.layout.row, null);

        Log.d("Files", "###############################################");
        Log.d("Files", "ARRAY DATA: "+ data[position]);
        Log.d("Files", "###############################################");


        StringBuilder desc = new StringBuilder();
        desc.append("Step: " + Integer.toString(position + 1) + "\n");
        desc.append(actions.getDescription(Environment.getExternalStorageDirectory() + "/MANUAL/settings/" + data[position] + ".txt"));

        TextView text = (TextView) vi.findViewById(R.id.text);
        text.setText(desc);

        //Button btnUp = (Button) vi.findViewById(R.id.buttonUp);
        //btnUp.setText("up");
        //btnUp.setContentDescription(data[position]);

        //Button btnDown = (Button) vi.findViewById(R.id.buttonDown);
        //btnDown.setText("down");
        //btnDown.setContentDescription(data[position]);

        Button btnDraw = (Button) vi.findViewById(R.id.buttonDraw);
        btnDraw.setText("dr");
        btnDraw.setContentDescription(data[position]);

        Button btnEdit = (Button) vi.findViewById(R.id.buttonEdit);
        btnEdit.setText("ed");
        btnEdit.setContentDescription(data[position]);

        Button btnSwitch = (Button) vi.findViewById(R.id.buttonSwitch);
        btnSwitch.setText("sw");
        btnSwitch.setContentDescription(data[position]);

        Button btnDel = (Button) vi.findViewById(R.id.buttonDel);
        btnDel.setText("de");
        btnDel.setContentDescription(data[position]);

        //Log.d("Files", "DATA: " + data[position]);

        try{
            ImageView mImg = (ImageView) vi.findViewById(R.id.imageView);
            BitmapTask btmt = new BitmapTask();
            btmt.setImageView(mImg);
            btmt.execute(data[position]);
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

