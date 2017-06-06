package study;

import org.gudy.azureus2.ui.swt.Main;

public class Test1 {
	public static void main(String[] args) throws InterruptedException {
		// Main.main(args);

		Thread a = new Thread(new Runnable() {

			public void run() {
				System.out.println("before aaa");
				//TestSwtGUI.aaa();
				System.out.println("after aaa");
			}
		});
		a.start();

		Main.main(args);

	}




}
