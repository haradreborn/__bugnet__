package com.harad.bugnet;

import android.content.*;
import android.database.sqlite.*;
import android.util.*;

public class sql extends SQLiteOpenHelper {
	// Database Version
    private static final int DATABASE_VERSION = 2;
    // Database Name
    private static final String DATABASE_NAME = "manual";

	public sql(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);  
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// SQL statement to create book table
		String CREATE_MAN_TABLE = "CREATE TABLE manual ( " +
			"id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
			"position INT, " +
			"file TEXT )";

		// create books table
		db.execSQL(CREATE_MAN_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS manual");

        // create fresh books table
        this.onCreate(db);
	}

	/**
	 * CRUD operations (create "add", read "get", update, delete)
	 */

	// table name
    private static final String TABLE_SEQ = "sequence";

    // Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_POSITION = "position";
    private static final String KEY_FILE = "file";

    private static final String[] COLUMNS = {KEY_ID,KEY_POSITION,KEY_FILE};

	public void addFile(String name){
		Log.d("addFile", name.toString());
		// 1. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();

		// 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_POSITION, 1);
        values.put(KEY_FILE, "TEST");

        // 3. insert
        db.insert(TABLE_SEQ, // table
        null, //nullColumnHack
        values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
	}

}

