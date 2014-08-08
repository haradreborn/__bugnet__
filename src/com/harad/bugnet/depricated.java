package com.harad.bugnet;

//TODO
//	public String ConfigReader(){
//		//Get the text file
//      	File file = new File(Environment.getExternalStorageDirectory() + "/MANUAL/config.txt");
//      	//Read text from file
//      	StringBuilder text = new StringBuilder();
//      	try {
//      		BufferedReader br = new BufferedReader(new FileReader(file));
//            		String line;
//            		while ((line = br.readLine()) != null) {
//              		text.append(line);
//                		text.append('\n');
//            		}
//     		} catch (IOException e) {
//      		//You'll need to add proper error handling here
//      	}
//      	return text.toString();
//	}
    
//TODO
//	actions.CreateMainFolder();
//	parser();
//	CharSequence[] cs = folders.toArray(new CharSequence[folders.size()]);
//	final int i = folders.size() - 1;
//
//	//Choose project folder
//	//Creating and Building the Dialog
//	AlertDialog.Builder builder = new AlertDialog.Builder(this);
//	builder.setTitle("Select The Difficulty Level");
//	builder.setSingleChoiceItems(cs, -1, new DialogInterface.OnClickListener() {
//	public void onClick(DialogInterface dialog, int item) {
//
//		switch(item) {
//			case i:
//      			// Your code when first option seletced
//              		break;
//		}
////	levelDialog.dismiss();
//	}
//	});
//	AlertDialog alert = builder.create();
//	alert.show();

//TODO
//take bundle
//Bundle b = this.getIntent().getExtras();
//image = (String) getIntent().getSerializableExtra("file name");
//Log.d("Files", "GETBUNDLE: " + image);

//TODO from DRAW
//Options menu item for non full screen view mode
//@Override
//public boolean onCreateOptionsMenu(Menu menu) {
//	// Inflate the menu; this adds items to the action bar if it is present.
//	getMenuInflater().inflate(R.menu.edit, menu);
//	return true;
//}
//@Override
//public boolean onOptionsItemSelected(MenuItem item) {
//	switch (item.getItemId()) {
//		//save file
//    		case R.id.save:
//        		File folder = new File(Environment.getExternalStorageDirectory().toString());
//        		boolean success = false;
//        		if (!folder.exists()){
//            			success = folder.mkdirs();
//       		}
//        		System.out.println(success+"folder");
//        		File file = new File(Environment.getExternalStorageDirectory().toString() + "/MANUAL/workflow/" + sttr);
//        		if ( !file.exists() ){
//            			try {
//                			success = file.createNewFile();
//            			} catch (IOException e) {
//                			e.printStackTrace();
//            			}
//        		}
//        		System.out.println(success+"file");
//        		FileOutputStream ostream = null;
//        		try{
//            			//create fos
//				ostream = new FileOutputStream(file);
//            			System.out.println(ostream);
//            			View targetView = dv;
//myDrawView.setDrawingCacheEnabled(true);
//Bitmap save = Bitmap.createBitmap(myDrawView.getDrawingCache());
//myDrawView.setDrawingCacheEnabled(false);// copy this bitmap otherwise distroying the cache will destroy
//copy this bitmap otherwise distroying the cache will destroy
//get the captured view
//Bitmap save = b1.copy(Bitmap.Config.ARGB_8888, false);
//BitmapDrawable d = new BitmapDrawable(b);
//canvasView.setBackgroundDrawable(d);
//myDrawView.destroyDrawingCache();
//Bitmap save = myDrawView.getBitmapFromMemCache("0");
//myDrawView.setDrawingCacheEnabled(true);
//Bitmap save = myDrawView.getDrawingCache(false);
//				Bitmap well = dv.getBitmap();
//            			Bitmap save = Bitmap.createBitmap(800,1280, Bitmap.Config.ARGB_8888);
//            			Paint paint = new Paint();
//            			paint.setColor(Color.WHITE);
//            			Canvas now = new Canvas(save);
//            			now.drawRect(new Rect(0,0,800,1280), paint);
//            			now.drawBitmap(well, new Rect(0,0,well.getWidth(),well.getHeight()), new Rect(0,0,800,1280), null);
//Canvas now = new Canvas(save);
//myDrawView.layout(0, 0, 100, 100);
//myDrawView.draw(now);
//
//				if(save == null) {
//                			System.out.println("NULL bitmap save\n");
//            			}
//            			save.compress(Bitmap.CompressFormat.PNG, 100, ostream);
//bitmap.compress(Bitmap.CompressFormat.PNG, 100, ostream);
//ostream.flush();
//ostream.close();
//        		}catch (NullPointerException e){
//            			e.printStackTrace();
//            			Toast.makeText(getApplicationContext(), "Null error", Toast.LENGTH_SHORT).show();
//        		}catch (FileNotFoundException e){
//            			e.printStackTrace();
//            			Toast.makeText(getApplicationContext(), "File error", Toast.LENGTH_SHORT).show();
//        		}catch (IOException e){
//            			e.printStackTrace();
//            			Toast.makeText(getApplicationContext(), "IO error", Toast.LENGTH_SHORT).show();
//        		}
//
//			listeditor = new Intent(this,ListEditor.class);
//			listeditor.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//			startActivity(listeditor);
//	    		//return true;
//case R.id.gallery:
//	// in onCreate or any event where your want the user to
//	// select a file
//	Intent intent = new Intent();
//	intent.setType("image/*");
//	intent.setAction(Intent.ACTION_GET_CONTENT);
//	startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
//cancel editing
//		case R.id.cancel:
//        		finish();
//        		return true;
//    		default:
//        		return super.onOptionsItemSelected(item);
//	}
//}
//public void onActivityResult(int requestCode, int resultCode, Intent data) {
//	if (resultCode == RESULT_OK) {
//      	if (requestCode == SELECT_PICTURE) {
//              	Uri selectedImageUri = data.getData();
//                	selectedImagePath = getPath(selectedImageUri);
//			//Bitmap bm = BitmapFactory.decodeFile(selectedImagePath);
//            	}
//    	}
//}
//
///**
// * helper to retrieve the path of an image URI
// */
//public String getPath(Uri uri) {
//	// just some safety built in
//	if( uri == null ){
//		//perform some logging or show user feedback
//		return null;
//	}
//	// try to retrieve the image from the media store first
//	// this will only work for images selected from gallery
//	String[] projection = { MediaStore.Images.Media.DATA };
//	Cursor cursor = managedQuery(uri, projection, null, null, null);
//	if( cursor != null ){
//		int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//		cursor.moveToFirst();
//		return cursor.getString(column_index);
//	}
//	// this is our fallback here
//	return uri.getPath();
//}
//Actions actions = new Actions();
//
//@Override
//public void onCreate(Bundle savedInstanceState) {
//	super.onCreate(savedInstanceState);
//
//   	Draw2d d = new Draw2d(this);
//    	setContentView(R.layout.edit);
//    	Toast.makeText(this, actions.ConfigReader(), Toast.LENGTH_SHORT).show();
//}
//
//@Override
//public boolean onCreateOptionsMenu(Menu menu) {
//   	// Inflate the menu; this adds items to the action bar if it is present.
//      getMenuInflater().inflate(R.menu.edit, menu);
//      return true;
//}
//
//@Override
//public boolean onOptionsItemSelected(MenuItem item) {
//     	switch (item.getItemId()) {
//        	case R.id.save:
//                	LinearLayout v = (LinearLayout) findViewById(R.id.linearLayout);
//                	Draw2d myView = new Draw2d(this);
//                	v.addView(myView);
//                	return true;
//            	case R.id.cancel:
//              	  finish();
//                	return true;
//            	default:
//               	 return super.onOptionsItemSelected(item);
//        }
//}
//
//public class Draw2d extends View {
//        public Draw2d(Context context) {
//         	super(context);
//        	setDrawingCacheEnabled(true);
//        }
//
//        @Override
//        protected void onDraw(Canvas c) {
///           	File file = new File(actions.ConfigReader() + "/img.png");
//            	File file = new File(Environment.getExternalStorageDirectory() + "/MANUAL/workflow" + "/img.png");
//            	if (file.exists()) {
//                	Bitmap bm = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/MANUAL/workflow" + "/img.png");
//                	int targetWidth  = bm.getWidth() / 1;
//                	int targetHeight = bm.getHeight() / 1;
//
//                	Matrix matrix = new Matrix();
//                	matrix.postScale(0.7f, 0.65f);
//                	Bitmap size = Bitmap.createBitmap(bm, 0, 0, targetWidth, targetHeight, matrix, true);
//
//                	Paint paint = new Paint();
//                	paint.setColor(Color.RED);
//
//                	c.drawBitmap(size, 0, 0, paint);
//                	c.drawCircle(100, 200, 30, paint);
//
//                	try {
//                    		getDrawingCache().compress(Bitmap.CompressFormat.PNG, 70, new FileOutputStream(new File(Environment.getExternalStorageDirectory() + "/MANUAL/workflow" + "/img.png")));
//                	} catch (Exception e) {
//                    		Log.e("Error--------->", e.toString());
//                	}
//                	super.onDraw(c);
//            	}
//            	else {
//              	finish();
//            	}
//        }
//}

//TODO from Global
//           	if(event.getAction() == MotionEvent.ACTION_DOWN
//              	|| event.getAction() == MotionEvent.ACTION_UP
//                     	){
//              	/**
//              	 * OnTouch method
//                     	 */
//                    	Display display = mWindowManager.getDefaultDisplay();
//                    	Bitmap size = Bitmap.createBitmap(display.getWidth(), display.getHeight(), Bitmap.Config.ARGB_8888);
//                    	Paint paint = new Paint();
//                    	Canvas can = new Canvas(size);
//
//                    	if (event.getAction() == MotionEvent.ACTION_DOWN){
//                        	Log.i(TAG, "Action :" + event.getAction() + "\t X :" + event.getRawX() + "\t Y :"+ event.getRawY());
//
//                        	x = event.getX();
//                        	y = event.getY();
//
//                        	//painter
//                        	paint.setColor(Color.RED);
//                        	BitmapDrawable ob = new BitmapDrawable(size);
//                        	viewFarm.setBackgroundDrawable(ob);
//                        	can.drawBitmap(size, 0, 0, paint);
//                        	can.drawCircle(x, y, 30, paint);
//                        	Log.i(TAG, "LOGGER: draw");
//                    	}
//
//                    	if (event.getAction() == MotionEvent.ACTION_MOVE){
//                      	x = event.getX();
//                        	y = event.getY();
//
//                        	paint.setColor(Color.GREEN);
//                        	BitmapDrawable ob = new BitmapDrawable(size);
//                        	viewFarm.setBackgroundDrawable(ob);
//                        	can.drawBitmap(size, 0, 0, paint);
//                        	can.drawCircle(x, y, 30, paint);
//
//                    	}
//
//                    	if (event.getAction() == MotionEvent.ACTION_UP){
//                        	Log.i(TAG, "Action :" + event.getAction() + "\t X :" + event.getRawX() + "\t Y :"+ event.getRawY());
//
//                        	try {
//                            		Thread.sleep(1000);
//                        	} catch (InterruptedException e) {
//                            		e.printStackTrace();
//                        	}
//                        	//take screenshot
//                        	Log.i(TAG, "LOGGER: shot");
//                        	actions.TakeShot(actions.id());
//
//                        	//erase paint
//                        	BitmapDrawable ob = new BitmapDrawable(size);
//                        	viewFarm.setBackgroundDrawable(ob);
//                        	can.drawColor(Color.TRANSPARENT);
//
//                        	switcher.removeView(view);
//                        	switcher.removeView(viewFarm);
//                        	mParams.width = 200;
//                        	mParams.height = 740;
//                        	//add asinc task
//                        	if (mParams.gravity == 51){
//                            		//51
//                            		mParams.gravity = Gravity.LEFT | Gravity.TOP;
////                            	gravitation = 53;
//                            		Log.i(TAG, "move right " + mParams.gravity);
//                        	}
//                        	else {
//                           		mParams.gravity = Gravity.RIGHT | Gravity.TOP;
////                            	gravitation = 51;
//                            		Log.i(TAG, "move left " + mParams.gravity);
//                        	}
//
////                        	mParams.gravity = Gravity.LEFT | Gravity.TOP;
//                        	switcher.setBackgroundColor(Color.TRANSPARENT);
//                        	mWindowManager.updateViewLayout(switcher, mParams);
//
//                        	switcher.addView(view, mParams);
//              	}
//             	}
//TODO touch start
//	// btn touch
//        Button touch = (Button) view.findViewById(R.id.btnTouch);
//        touch.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//        	new TakeTouchScreen ().execute();
//        }

//	class TakeTouchScreen extends AsyncTask<Void, String, String> {
//                private ProgressDialog progress;
//
//                @Override
//                protected void onPreExecute() {
//                    	//progress = ProgressDialog.show(this, "Information",
//                    	//							   "Please Wait.. ", true);
//                    	switcher.removeView(view);
//                }
//                @Override
//                public String doInBackground(Void... params) {
//                    	return "";
//                }
//                @Override
//                protected void onPostExecute(String result) {
//                   	//progress.dismiss();
//
//                    	switcher.setBackgroundColor(0x3394C2E3);
//                    	mParams.width = mParams.MATCH_PARENT;
//                    	mParams.height = mParams.MATCH_PARENT;
//                    	mWindowManager.updateViewLayout(switcher, mParams);
//                    	switcher.addView(viewFarm, mParams);
//                    	Log.i(TAG, "LOGGER: view farm added " + mParams.width);
//                }
//
//        }
//        });
//TODO touch end

//TODO from list
//  public boolean onKeyDown(int keyCode, KeyEvent event) {
//    	if (keyCode == KeyEvent.KEYCODE_BACK) {
//    		dialogOnBackPress();
//    	   	return true;
//    	}
//    	return super.onKeyDown(keyCode, event);
//    }
//	protected void dialogOnBackPress() {
//		new AlertDialog.Builder(this) .setMessage("Do you want to save your list order?") .setCancelable(true) .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//			public void onClick(DialogInterface dialog, int id) {
//
//				File sdcard = Environment.getExternalStorageDirectory();
//		       		String path = "MANUAL/workflow/";
//
//				for (int i = 0; i < array.size(); i++) {
//					Log.d("Files", "TEXT: " + array.get(i));
//					File from = new File(sdcard, path + array.get(i));
//					File to = new File(sdcard, path + i + ".png");
//					//from.renameTo(to);
//				}
//				ListEditor.this.finish();
//			}
//
//		}) .setNegativeButton("No", new DialogInterface.OnClickListener() {
//			public void onClick(DialogInterface dialog, int id) {
//				ListEditor.this.finish();
//                        }
//		}) .show();
//	}
//            	File sdcard = Environment.getExternalStorageDirectory();
//            	File from = new File(sdcard + "/MANUAL/workflow", bid);
//            	File to = new File(sdcard + "/MANUAL/workflow", Integer.toString(getIndex(bid) - 1) + ".png");
//            	from.renameTo(to);
//
////            try{
////            	copy(from, to);
////            } catch (IOException io){}
//            		Log.d("Files", "RENAME " + Integer.toString(getIndex(bid) - 1));
//            		Log.d("Files", "RENAME " + bid);
//            		Log.d("Files", "RENAME " + from.renameTo(to));
//public Bitmap setBitmap (Object bmData) {
//BITMAP
//Bitmap bm = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/MANUAL/workflow/" + bmData);
//int targetWidth  = bm.getWidth() / 1;
//int targetHeight = bm.getHeight() / 1;

//Bitmap size = Bitmap.createBitmap(bm, 0, 0, targetWidth, targetHeight, matrix(), true);
//return size;
//}

//TODO
//    	public void copy(File src, File dst) throws IOException {
//       	InputStream in = new FileInputStream(src);
//        	OutputStream out = new FileOutputStream(dst);
//
//        	// Transfer bytes from in to out
//        	byte[] buf = new byte[1024];
//        	int len;
//        	while ((len = in.read(buf)) > 0) {
//            		out.write(buf, 0, len);
//        	}
//        	in.close();
//        	out.close();
//    	}

//TODO from Main
//SQL
//sql db = new sql(this);
//db.addFile("test");
//        	//create settings
//        	try {
//            		File file = new File(Environment.getExternalStorageDirectory() + "/MANUAL/config.txt");
//            		//write the bytes in file
//            		if(!file.exists()) {
//                    		file.createNewFile();
//                    		OutputStream fo = new FileOutputStream(file);
//                    		PrintWriter pw = new PrintWriter(fo);
//                    		pw.println("sdcard/MANUAL/workflow");
//                    		pw.flush();
//                    		pw.close();
//                    		//fo.write(data);
//                    		fo.close();
//                    		System.out.println("file created: "+file);
//                	}
//        	} catch (IOException io){io.printStackTrace();}
//	//btn folder
//    	public void buttonFolderClicked(View folder){
//        	actions.CreateMainFolder();
//        	stopService(globalService);
//        	AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        	builder.setTitle("Set path to the project folder");
//        	// Set up the input
//        	final EditText input = new EditText(this);
//        	// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
//        	input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
//        	input.setText("sdcard/MANUAL/workflow", TextView.BufferType.EDITABLE);
//        	builder.setView(input);
//        	// Set up the buttons
//        	builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            	@Override
//            	public void onClick(DialogInterface dialog, int which) {
//                	ProjectFolder = input.getText().toString();
//
//                	try {
//                    		File file = new File(Environment.getExternalStorageDirectory() + "/MANUAL/config.txt");
//                    		//write the bytes in file
//                    		if(file.exists()) {
//                        		PrintWriter writer = new PrintWriter(file);
//                        		writer.print("");
//                        		writer.close();
//                        		OutputStream fo = new FileOutputStream(file);
//                        		PrintWriter pw = new PrintWriter(fo);
//                        		pw.println(ProjectFolder);
//                        		pw.flush();
//                        		pw.close();
//                        		//fo.write(data);
//                        		fo.close();
//                    		}
//                    		else {
//                        		//create file
//                        		file.createNewFile();
//                        		OutputStream fo = new FileOutputStream(file);
//                        		PrintWriter pw = new PrintWriter(fo);
//                        		pw.println(ProjectFolder);
//                        		pw.flush();
//                        		pw.close();
//                        		//fo.write(data);
//                        		fo.close();
//                    		}
//                	} catch (IOException io){io.printStackTrace();}
//
//
////                	actions.folders.clear();
////                	actions.folders.add(actions.ProjectFolder);
//
//                	File folder = new File(actions.ConfigReader());
//                	if (!folder.exists()) {
//                    		folder.mkdirs();
//                	}
//            	}
//        	});
//
//    	builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            	@Override
//            	public void onClick(DialogInterface dialog, int which) {
//                	dialog.cancel();
//            	}
//        	});
//        	builder.show();
//        	Toast.makeText(this, actions.ConfigReader(), Toast.LENGTH_SHORT).show();
//    	}
//    	public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        	if (resultCode == RESULT_OK) {
//            		if (requestCode == SELECT_PICTURE) {
//                		Uri selectedImageUri = data.getData();
//                		selectedImagePath = getPath(selectedImageUri);
//
//            		}
//        	}
//    	}
//
//    	/**
//     	 * helper to retrieve the path of an image URI
//     	 */
//    	public String getPath(Uri uri) {
//		// just some safety built in
//		if( uri == null ){
//			return null;
//		}
//		// try to retrieve the image from the media store first
//		// this will only work for images selected from gallery
//		String[] projection = { MediaStore.Images.Media.DATA };
//		Cursor cursor = managedQuery(uri, projection, null, null, null);
//		if( cursor != null ){
//			int column_index = cursor
//               		.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//			cursor.moveToFirst();
//			return cursor.getString(column_index);
//		}
//		// this is our fallback here
//		return uri.getPath();
//    	}
////	btn gal
//    	public void buttonGalClicked(View gal){
//
//		// in onCreate or any event where your want the user to
//		// select a file
//		Intent intent = new Intent();
//		intent.setType("image/*");
//		intent.setAction(Intent.ACTION_GET_CONTENT);
//		startActivityForResult(Intent.createChooser(intent,
//													"Select Picture"), SELECT_PICTURE);
//
//		String bid = selectedImagePath;
//		Bundle bundle = new Bundle();
//		String k = "file name";
//		bundle.putString(k, bid);
//		draw = new Intent(this,Draw.class);
//		draw.putExtras(bundle);
//		Log.d("Files", "BUNDLE: " + bid);
//		//startActivity(draw);
//	}
//	//btn create
//	public void buttonCreateClicked(View stop){
//
//		//Create folder
//		actions.CreateMainFolder();
//		try {
//			File file = new File(Environment.getExternalStorageDirectory() + "/MANUAL/test.html");
//			file.createNewFile();
//			//String data = "hello";
//			//write the bytes in file
//			if(file.exists()) {
//				OutputStream fo = new FileOutputStream(file);
//				PrintWriter pw = new PrintWriter(fo);
//				pw.println(report.genHtml());
//				pw.flush();
//				pw.close();
//				//fo.write(data);
//				fo.close();
//				System.out.println("file created: "+file);
//			}
//		} catch (IOException io){io.printStackTrace();}
//
//		Toast.makeText(this, "Generating report ", Toast.LENGTH_SHORT).show();
//	}
//
//btn view
//	public void buttonStepClicked(View stp){

//	step = new Intent(this, Step.class);
//	startActivity(step);
//}
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        	super.onActivityResult(requestCode, resultCode, data);
//
//        	if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
//
//            		Uri selectedImage = data.getData();
//            		String[] filePathColumn = {MediaStore.Images.Media.DATA};
//            		Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
//            		cursor.moveToFirst();
//            		int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//            		String picturePath = cursor.getString(columnIndex);
//            		cursor.close();
//            		thumbnail = (BitmapFactory.decodeFile(picturePath));
//
//        	}
//    	}