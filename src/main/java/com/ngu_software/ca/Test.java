package com.ngu_software.ca;

import java.io.File;

import com.ngu_software.ca.model.LogMonitor;

public class Test {

	public static void main(String[] args) {
//		File asd = DialogBox.getLogFile();
//		System.out.println(asd.toString());
		LogMonitor build = new LogMonitor(new File("/Users/nguyen/Projects/Programming/Java/Code Andon/build.txt"));
		
		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(build.isSuccessful() + "\t" + build.isWarning() + "\t" + build.isError());
		}
	}

}
