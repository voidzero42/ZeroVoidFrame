package com.zerovoid.lib.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Iterator;
import java.util.Map;


public class V {

	private DBHelper dbhelper = null;
	private String[] colValue = new String[100];
	private String[] selectionArgs = new String[100];
	private String selection;
	private int pos = 0;
	private String tbName = null;
	private String gbValue = null;
	private String hvValue = null;
	private String obValue = null;
	@SuppressWarnings("rawtypes")
	public Map map=null;

	public static V getV(DBHelper dbhelper) {
		V v = new V();
		v.dbhelper = dbhelper;
		return v;
	}

	public V getDB(String tbName) {
		this.tbName = tbName;
		return this;
	}

	public V setPrimaryKey(String key)
			throws Exception {
		if ((key == null) || ("".equals(key))) {
			throw new Exception("主键不能为空值！");
		}
		if (key.indexOf("=") >= 0) {
			throw new Exception("主键不能包括‘=’号！");
		}
		if (map == null) {
			throw new Exception("查询条件值不能为空！");
		}
		key = key + "=?";

		return setFilter(key);
	}

	@SuppressWarnings("unchecked")
	public V setFilter(String filter) {
		selection = filter;
		Iterator<Map.Entry<Integer, String>> it = map.entrySet().iterator();
		int count=0;
		while (it.hasNext()) {
			Map.Entry<Integer, String> entry = it.next();
			selectionArgs[count]=entry.getValue();
			count++;
		}

		return this;
	}

	public V getCell(String fieldName) throws Exception {
		if ((fieldName == null) || ("".equals(fieldName))) {
			throw new Exception("单元格字段名不能为空值！");
		}
		if (fieldName.split(",").length > 0)
			colValue = fieldName.split(",");
		else
			colValue[pos] = fieldName;
		return this;
	}

	// String groupBy, String having, String orderBy
	public V groupBy(String columns) {
		this.gbValue = columns;
		return this;
	}

	public V having(String columns) {
		this.hvValue = columns;
		return this;
	}

	public V orderBy(String columns) {
		this.obValue = columns;
		return this;
	}

	public R toR() throws Exception {
		if (this.tbName == null) {
			throw new Exception("数据库表名为初始化");
		}
		R r = R.getR();
		r.cursor = query();
		return r;
	}

	private Cursor query() {
		SQLiteDatabase db = dbhelper.getReadableDatabase();
		Cursor cursor = db.query(tbName, colValue, selection, selectionArgs,
				gbValue, hvValue, obValue);
		db.close();
		return cursor;
	}


	@SuppressWarnings("unchecked")
	public long insert() throws Exception {
		
		SQLiteDatabase db = dbhelper.getWritableDatabase();
		Map <String,String> data=this.map;
		Iterator<Map.Entry<String, String>> it = data.entrySet().iterator();
		ContentValues cv = new ContentValues();
		while (it.hasNext()) {
			Map.Entry<String, String> entry = it.next();
			cv.put(entry.getKey(), entry.getValue());
		}
		long id=db.insert(tbName, null, cv);
		db.close();
		return id;
	}
	
	
	@SuppressWarnings("unchecked")
	public long update() throws Exception {
		
		SQLiteDatabase db = dbhelper.getWritableDatabase();
		Map <String,String> data=this.map;
		Iterator<Map.Entry<String, String>> it = data.entrySet().iterator();
		ContentValues cv = new ContentValues();
		while (it.hasNext()) {
			Map.Entry<String, String> entry = it.next();
			cv.put(entry.getKey(), entry.getValue());
		}
		long id=db.update(tbName, cv, selection, selectionArgs);
		db.close();
		return id;
	}
}
