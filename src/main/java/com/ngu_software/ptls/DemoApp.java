package com.ngu_software.ptls;

public class DemoApp {

	public static void main(String[] args) throws Exception {
		DemoConnector ad = new DemoConnector();
		ad.initialize();
		Thread t = new Thread() {
			public void run() {

				while (true) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException ie) {
						
					}
				}
			}
		};
		t.start();
		System.out.println("Started");
	}

}
