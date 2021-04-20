package com.me.section;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;

public class FileWriter implements Runnable {
	private volatile RandomAccessFile raf;
	private FileSection fileSection;
	private Thread thread;
	private volatile boolean isOk = false;

	public FileWriter() {
	}

	public FileWriter(File file, FileSection fileSection) {
		try {
			this.raf = new RandomAccessFile(file, "rw");
			this.fileSection = fileSection;
			this.thread = new Thread(this);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public FileWriter(RandomAccessFile raf, FileSection fileSection) {
		this.raf = raf;
		this.fileSection = fileSection;
		this.thread = new Thread(this);
	}
	
	public void start() {
		this.thread.start();
	}

	public Thread getThread() {
		return thread;
	}

	@Override
	public void run() {
		byte[] value = fileSection.getValue();

		try {
			raf.seek(0);
			raf.write(value);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			isOk = true;
			raf.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isOk() {
		return isOk;
	}
	
}
