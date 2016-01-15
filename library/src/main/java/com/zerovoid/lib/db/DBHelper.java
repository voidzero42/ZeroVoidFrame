package com.zerovoid.lib.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Map;


public class DBHelper extends SQLiteOpenHelper{
	
	private static final int DATABASE_VERSION = 1;
	private static final long serialVersionUID = 1L;
	private Map map=null;
	
	
	public V getContext(Context context,String dbName){
		 V v = V.getV(this);
		 v.map=map;
		 return v;
	}

	public DBHelper(Context context, String name) {
		super(context, name, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}
	
	public void setDataMap(Map map){
		this.map=map;
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	
	
}
