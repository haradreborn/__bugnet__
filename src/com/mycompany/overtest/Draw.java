package com.mycompany.overtest;

import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.util.DisplayMetrics;
import android.view.*;
import android.view.ContextMenu.*;
import android.widget.*;
import android.widget.AdapterView.*;
import java.io.*;

/**
 * Created by harad on 15.03.14.
 */
public class Draw extends Activity{

	DrawingView dv;
	private Paint mPaint;

    public int width;
    public int height;
	
	//new gallery intent privates
	private static final int SELECT_PICTURE = 1;
	private String selectedImagePath;

	//TODO
	String sttr = ListEditor.str;

	/**
	 * Decode bitmap from path 
	 * @filename 
	 * @size
	 */
	public Bitmap Decoder(String filename){
		//set image to the bm
        BitmapFactory.Options buffer = new BitmapFactory.Options();
        buffer.inSampleSize = 1;
		Bitmap bm = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/MANUAL/workflow/" + filename, buffer);

		int targetWidth  = bm.getWidth() / 1;
		int targetHeight = bm.getHeight() / 1;
		Bitmap size = Bitmap.createBitmap(bm, 0, 0, targetWidth, targetHeight, matrix(), true);
		return size;
	}

	//rescale
	public Matrix matrix(){
        Matrix matrix = new Matrix();
        matrix.postScale(1.0f, 1.0f);
        return matrix;
    }

	//TODO
	Bitmap result = Decoder(sttr);
    Paint paint = new Paint();
	
	/**
	 * Main onCreate
	 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		//fullscreen flag
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//set view		
        dv = new DrawingView(this);
        setContentView(dv);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(12);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        width = dm.widthPixels;
        height = dm.heightPixels;
        //Toast.makeText(getApplicationContext(), Integer.toString(width) + Integer.toString(height),
        // Toast.LENGTH_LONG).show();
    }

	/**
	 * Menu items
	 */
	
	public boolean onKeyDown(int keyCode, KeyEvent event) { 
		if (keyCode == KeyEvent.KEYCODE_BACK) { 
			dialogOnBackPress(); 
			return true; 
			} 
		return super.onKeyDown(keyCode, event); 
		} 
	
	protected void dialogOnBackPress() { 
		new AlertDialog.Builder(this) .setMessage("Do you want to save your drawings?") .setCancelable(true) .setPositiveButton("Yes", new DialogInterface.OnClickListener() { 
			public void onClick(DialogInterface dialog, int id) { 
				
				//File folder = new File(Environment.getExternalStorageDirectory().toString());
                boolean success = false;
                //if (!folder.exists()){
                //        success = folder.mkdirs();
                //}

                System.out.println(success+"folder");

                File file = new File(Environment.getExternalStorageDirectory().toString() + "/MANUAL/workflow/" + sttr);

                if ( !file.exists() ){
                        try {
                            success = file.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                }

                System.out.println(success+"file");

                FileOutputStream ostream = null;
                try{
                    //create fos
                    ostream = new FileOutputStream(file);

                    System.out.println(ostream);
                    //View targetView = dv;

                    Bitmap well = dv.getBitmap();

//int tW  = result.getWidth() / 1;
//int tH = result.getHeight() / 1;

                    Bitmap save = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                    Paint paint = new Paint();
                    paint.setColor(Color.WHITE);
                    Canvas now = new Canvas(save);
                    now.drawRect(new Rect(0,0,width, height), paint);
                    now.drawBitmap(well, new Rect(0,0,well.getWidth(),well.getHeight()), new Rect(0,0,width, height), null);

//Canvas now = new Canvas(save);
//myDrawView.layout(0, 0, 100, 100);
//myDrawView.draw(now);
                    
					if(save == null) {
                        save.recycle();
                        Toast.makeText(getApplicationContext(), "Null error", Toast.LENGTH_SHORT).show();
                    }
                    save.compress(Bitmap.CompressFormat.PNG, 100, ostream);
                    save.recycle();

//bitmap.compress(Bitmap.CompressFormat.PNG, 100, ostream);
					ostream.flush();
					ostream.close();

                }catch (NullPointerException e){
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Null error", Toast.LENGTH_SHORT).show();
                }catch (FileNotFoundException e){
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "File error", Toast.LENGTH_SHORT).show();
                }catch (IOException e){
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "IO error", Toast.LENGTH_SHORT).show();
                }

				Draw.this.finish();
			} 
		}) .setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				Draw.this.finish();
			}
		}) .show();
    }


	public class DrawingView extends View {

        public int width;
        public  int height;

        private Bitmap  mBitmap;
        private Canvas  mCanvas;
        private Path    mPath;
        private Paint   mBitmapPaint;
        Context context;
        private Paint circlePaint;
        private Path circlePath;

        public DrawingView(Context c) {
            super(c);
            context=c;
            setDrawingCacheEnabled(true);

            mPath = new Path();
            mBitmapPaint = new Paint(Paint.DITHER_FLAG);
            circlePaint = new Paint();
            circlePath = new Path();
            circlePaint.setAntiAlias(true);
            circlePaint.setColor(Color.BLUE);
            circlePaint.setStyle(Paint.Style.STROKE);
            circlePaint.setStrokeJoin(Paint.Join.MITER);
            circlePaint.setStrokeWidth(4f);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);

            mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            mCanvas = new Canvas(mBitmap);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            //super.onDraw(canvas);

            paint.setColor(Color.RED);
            canvas.drawBitmap(result, 0, 0, paint);

            canvas.drawBitmap( mBitmap, 0, 0, mBitmapPaint);
            canvas.drawPath( mPath,  mPaint);
            canvas.drawPath( circlePath,  circlePaint);
        }

        private float mX, mY;
        private static final float TOUCH_TOLERANCE = 4;

        private void touch_start(float x, float y) {
            mPath.reset();
            mPath.moveTo(x, y);
            mX = x;
            mY = y;
        }

        private void touch_move(float x, float y) {
            float dx = Math.abs(x - mX);
            float dy = Math.abs(y - mY);
            if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
                mPath.quadTo(mX, mY, (x + mX)/2, (y + mY)/2);
                mX = x;
                mY = y;

                circlePath.reset();
                circlePath.addCircle(mX, mY, 30, Path.Direction.CW);
            }
        }

        private void touch_up() {
            mPath.lineTo(mX, mY);
            circlePath.reset();
            // commit the path to our off_screen
            mCanvas.drawPath(mPath,  mPaint);
            // kill this so we don't double draw
            mPath.reset();
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float x = event.getX();
            float y = event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                        touch_start(x, y);
                        invalidate();
                        break;
                case MotionEvent.ACTION_MOVE:
                        touch_move(x, y);
                        invalidate();
                        break;
                case MotionEvent.ACTION_UP:
                        touch_up();
                        invalidate();
                        break;
            }
            return true;
        }
		
		public Bitmap getBitmap(){

//this.measure(100, 100);
//this.layout(0, 0, 100, 100);

			this.setDrawingCacheEnabled(true);  
			this.buildDrawingCache();
			Bitmap bmp = Bitmap.createBitmap(this.getDrawingCache());   
			this.setDrawingCacheEnabled(false);
			return bmp;
		}

		public void clear(){
			mBitmap.eraseColor(Color.GREEN);
			invalidate();
			System.gc();
		}
	}

}
