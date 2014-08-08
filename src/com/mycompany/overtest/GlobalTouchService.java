package com.mycompany.overtest;

import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.widget.LinearLayout.*;

import android.view.View.OnTouchListener;




/**
 * Created by shabanov_a on 07.02.14.
 */
public class GlobalTouchService extends Service implements OnTouchListener {
	Actions actions = new Actions();

    private String TAG = this.getClass().getSimpleName();
    // window manager
    public WindowManager mWindowManager;
    // linear layout will use to detect touch event
    private LinearLayout switcher;

    //Side GRAVITY
    public int gravitation = 0;

    WindowManager.LayoutParams mParams = new WindowManager.LayoutParams(
        200, // width of layout 30 px
        500, // height is equal to full screen - 560 for 3 btns
        WindowManager.LayoutParams.TYPE_PHONE, // Type Phone, These are non-application windows providing user interaction with the phone (in particular incoming calls).
        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, // this window won't ever get key input focus
        PixelFormat.TRANSLUCENT);

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    //farm data
    public float startX;
    public float startY;

    @Override
    public void onCreate() {

        super.onCreate();

        // create linear layout
        switcher = new LinearLayout(this);
        // set layout width 30 px and height is equal to full screen
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        switcher.setLayoutParams(lp);
        // set color if you want layout visible on screen
        switcher.setBackgroundColor(Color.TRANSPARENT);
        // set on touch listener
        switcher.setOnTouchListener(this);

        // create views
        LayoutInflater li;
        li = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        final View view;
        view = li.inflate(R.layout.side, null);
        final View viewHidden;
        viewHidden = li.inflate(R.layout.hidden, null);
        final View viewFarm;
        viewFarm = li.inflate(R.layout.farm, null);

        // set touch listener to the transparent layout
        viewHidden.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switcher.removeView(viewHidden);
                switcher.removeView(view);
                switcher.addView(view, mParams);
                return true;
            }
        });

        // set touch listener to the farm layout
        viewFarm.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float x;
                float y;

                /* CANVAS */
                //init global paint params
                Display display = mWindowManager.getDefaultDisplay();
                Bitmap size = Bitmap.createBitmap(display.getWidth(), display.getHeight(), Bitmap.Config.ARGB_8888);
                Paint paint = new Paint();
                Paint paintSecond = new Paint();
                Canvas can = new Canvas(size);
                Path path = new Path();

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x = event.getX();
                        y = event.getY();
                        startX = x;
                        startY = y;

//                        	//painter
//                        	paint.setColor(Color.GREEN);
//                        	BitmapDrawable obDOWN = new BitmapDrawable(size);
//                        	viewFarm.setBackgroundDrawable(obDOWN);
//                        	can.drawBitmap(size, 0, 0, paint);
//                        	can.drawCircle(x, y, 30, paint);
//                        	Log.i(TAG, "LOGGER: draw");

                        break;

                    case MotionEvent.ACTION_MOVE:
                        x = event.getX();
                        y = event.getY();

//                        	paint.setColor(Color.RED);
//                        	paintSecond.setColor(Color.GREEN);
//                        	BitmapDrawable obMOVE = new BitmapDrawable(size);
//                        	viewFarm.setBackgroundDrawable(obMOVE);
//                        	can.drawBitmap(size, 0, 0, paint);
//                        	can.drawCircle(x, y, 20, paint);
////                        can.drawCircle(startX, startY, 50, paint);
//                        	paint.setStyle(Paint.Style.STROKE);
//                        	paint.setStrokeWidth(10);
//                        	path.moveTo(startX, startY);
//                        	path.lineTo(x, y);
//                        	path.close();
//                        	can.drawPath(path, paint);

                        break;

                    case MotionEvent.ACTION_UP:
                        //take screenshot
                        Log.i(TAG, "LOGGER: shot");
                        actions.TakeShot(actions.id());

//                        	//erase paint
//                        	BitmapDrawable obUP = new BitmapDrawable(size);
//                        	viewFarm.setBackgroundDrawable(obUP);
//                        	can.drawColor(Color.TRANSPARENT);

                        switcher.removeView(view);
                        switcher.removeView(viewFarm);
                        mParams.width = 200;
                        mParams.height = 500;

                        if (mParams.gravity == 51){
                            mParams.gravity = Gravity.LEFT | Gravity.TOP;
                            Log.i(TAG, "move right " + mParams.gravity);
                        }
                        else {
                            mParams.gravity = Gravity.RIGHT | Gravity.TOP;
                            Log.i(TAG, "move left " + mParams.gravity);
                        }

                        switcher.setBackgroundColor(Color.TRANSPARENT);
                        mWindowManager.updateViewLayout(switcher, mParams);
                        switcher.addView(view, mParams);

                        break;
                }

            return true;
            }
        });

        // set main view
        switcher.addView(view, mParams);

        // set btn screen
        Button screen = (Button) view.findViewById(R.id.btnScreen);
        screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TakePrintScreen ().execute();
            }

            class TakePrintScreen extends AsyncTask<Void, String, String> {
                private ProgressDialog progress;

                @Override
                protected void onPreExecute() {
                    switcher.removeView(view);
                    switcher.addView(viewHidden, mParams);
                }

                @Override
                public String doInBackground(Void... params) {
                        actions.TakeShot(actions.id());
                        return "";
                }

                @Override
                protected void onPostExecute(String result) {
                        //progress.dismiss();
                        switcher.removeView(viewHidden);
                        switcher.removeView(view);
                        switcher.addView(view, mParams);
                }

                //switcher.removeView(view);
                //switcher.addView(viewHidden, mParams);
            }
        });

        // btn switch
        Button swi = (Button) view.findViewById(R.id.btnSwitch);
        swi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mParams.gravity == 53){
                        mParams.gravity = Gravity.LEFT | Gravity.TOP;
                        gravitation = 51;
                        Log.i(TAG, "move right " + mParams.gravity);
                        mWindowManager.updateViewLayout(switcher, mParams);
                    }
                    else {
                        mParams.gravity = Gravity.RIGHT | Gravity.TOP;
                        gravitation = 53;
                        Log.i(TAG, "move left " + mParams.gravity);
                        mWindowManager.updateViewLayout(switcher, mParams);
                    }

            }
        });

        // btn hide
        Button hide = (Button) view.findViewById(R.id.btnHide);
        hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switcher.removeView(view);
                switcher.addView(viewHidden, mParams);
            }
        });

        // fetch window manager object
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        // set layout parameter of window manager
        mParams.gravity = Gravity.LEFT | Gravity.TOP;
        Log.i(TAG, "add View");
        mWindowManager.addView(switcher, mParams);
    }

    @Override
    public void onDestroy() {
        if(mWindowManager != null) {
            if(switcher != null) mWindowManager.removeView(switcher);
        }
        super.onDestroy();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if(event.getAction() == MotionEvent.ACTION_DOWN
                //    || event.getAction() == MotionEvent.ACTION_UP
                ){}
        return true;
    }

}
