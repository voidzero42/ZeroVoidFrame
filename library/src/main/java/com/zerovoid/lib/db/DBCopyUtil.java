package com.zerovoid.lib.db;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class DBCopyUtil {

	/* ========== Properties ========== */
    /** DB name */
	private String DB_NAME = "";
	
	/** activity object */
	private Context context;

	/** db save sdcard path */
	private final String SDCARD_PATH = Environment
			.getExternalStorageDirectory().getAbsolutePath() + "/";
	/** db save file path */
	private final String DATA_PATH = "/data"
			+ Environment.getDataDirectory().getAbsolutePath() + "/";
	/** porject package name  */
	private String PACKAGE_NAME = null;
	
	private InputStream data;
	
	public DBCopyUtil(Context context, String DBName, InputStream data) {
		this.context = context;
		DB_NAME = DBName;
		this.data=data;
	}
	
	/**
	 * copy the raw database in the phone memory
	 */
	public boolean copyDbToData() {
		
		PACKAGE_NAME = context.getApplicationInfo().packageName + "/";
		
		String outputPath = DATA_PATH + PACKAGE_NAME + "databases/" + DB_NAME;
		
		File dir = new File(
				outputPath.substring(0, outputPath.lastIndexOf("/")));
		if (!dir.exists()) {
			dir.mkdirs();
		}
	
		boolean isSuccess = copy(outputPath);
		return isSuccess;
	}
	
	/**
	 * copy the raw database in the sdcard
	 */
	@SuppressWarnings("unused")
	private boolean copyDbToSDCard() {
		
		String outputPath = SDCARD_PATH + DB_NAME;
		
		boolean isSuccess = copy(outputPath);
		return isSuccess;
	}
	
	/**
	 * The db raw copy to the appropriate path of public methods
	 * 
	 * @param fileName
	 *            
	 * @return true or flase
	 */
	private boolean copy(String fileName) {
		try {
			
			InputStream is = data;
		
			FileOutputStream fos = new FileOutputStream(fileName);
			byte[] buf = new byte[10240];
			int count = 0;
			while ((count = is.read(buf)) > 0) {
				fos.write(buf, 0, count);
			}
			is.close();
			fos.flush();
			fos.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}
