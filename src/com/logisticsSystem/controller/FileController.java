package com.logisticsSystem.controller;

import java.io.File;

public class FileController {

	public long getFileSize(String filePath){
		String size = "";
		long IFileSize = 0;
		
		File mFile = new File(filePath);
		if( mFile.exists() ){
			IFileSize = mFile.length();
			size = Long.toString(IFileSize) + " bytes";
		}
		else{
			size = "File not exist";
		}
		
		return IFileSize;
	}
}
