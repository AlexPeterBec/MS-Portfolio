package partSix;

import java.io.BufferedReader;
import java.io.IOException;

class MyThread extends Thread {
	BufferedReader reader;
	String result;

	public MyThread(BufferedReader reader) {
		this.reader = reader;
	}

	public String getResult() {
		return this.result;
	}

	public void run() {
		String line = null;
		StringBuilder builder = new StringBuilder();
		try {
			while ((line = this.reader.readLine()) != null) {
				builder.append(line);
				builder.append(System.getProperty("line.separator"));
			}
			String result = builder.toString();
			System.out.println(result);
			this.result = result;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
