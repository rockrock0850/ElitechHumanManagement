package com.elitech.human.resource.vo.other;

import java.io.File;

/**
 * 檔案類型物件
 * 
 * @create by Adam
 */
public class FileVO {

	private String name;
	private String path;
	private String extension;
	private File file;
	private byte[] fileBinary;

	public String getName () {
		return name;
	}

	public void setName (String name) {
		this.name = name;
	}

	public String getPath () {
		return path;
	}

	public void setPath (String path) {
		this.path = path;
	}

	public String getExtension () {
		return extension;
	}

	public void setExtension (String extension) {
		this.extension = extension;
	}

	public File getFile () {
		return file;
	}

	public void setFile (File file) {
		this.file = file;
	}

	public byte[] getFileBinary () {
		return fileBinary;
	}

	public void setFileBinary (byte[] fileBinary) {
		this.fileBinary = fileBinary;
	}
	
}
