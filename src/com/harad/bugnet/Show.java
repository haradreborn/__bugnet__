package com.harad.bugnet;
import android.app.*;
import android.os.*;
import android.webkit.*;
import java.io.*;

public class Show extends Activity {
	/** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebView wb = new WebView(this);
        String url = "file:///" + Environment.getExternalStorageDirectory().toString() + File.separator + "MANUAL/test.html";

        wb.loadUrl(url);
        setContentView(wb);
	}
}
