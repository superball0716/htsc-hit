package com.htsc.consumer.util;

import java.io.File;
import java.io.FileOutputStream;

public class FileUtil {
	
	public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
		File saveFile = new File(filePath);
		
		if (!saveFile.exists())
			saveFile.mkdirs();
		
		FileOutputStream out = new FileOutputStream(filePath + fileName);
		out.write(file);
		out.flush();
		out.close();
	}

}

