package partSix;

import java.io.*;

class MyThread extends Thread {
	BufferedReader reader;
	String result;
	

	public MyThread(BufferedReader reader) {
		this.reader = reader;
	}

	public String getResult(){
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


public class DeployDev {
	
	public void startThreads(ProcessBuilder pb) throws IOException{
		Process p = pb.start();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				p.getInputStream()));
		
		BufferedReader reader_err = new BufferedReader(new InputStreamReader(
				p.getErrorStream()));
		
		MyThread input = new MyThread(reader);
		input.start();
		
		MyThread input_err = new MyThread(reader_err);
		input_err.start();
	
	}
	
	public static void main(String[] args) throws IOException {
		
		DeployDev dp = new DeployDev();
		
		String fileName = "/Users/Alex/Desktop/java/file_dispos.txt";

		FileReader in = new FileReader(fileName);
		BufferedReader br = new BufferedReader(in);

		String line;
		while ((line = br.readLine()) != null) {
			
			
			
			/* CREATE FOLDER in /tmp/abec 
			System.out.println("Create folder in /tmp/abec");
			ProcessBuilder pb_mkdir = new ProcessBuilder(
					"ssh", 
					"-o",
					"StrictHostKeyChecking=no",
					"abec@" + line, 
					"mkdir", "-p", "/tmp/abec/");
			Process p1 = pb_mkdir.start();
			
			dp.startThreads(pb_mkdir);
			*/

			System.out.println("Create file in /tmp/" + line);
			ProcessBuilder pb_scp = new ProcessBuilder(
					"scp",
					"-o",
					"StrictHostKeyChecking=no",
					"/Users/Alex/Desktop/java/slave.jar",
					"abec@" + line + ":/tmp/abec/slave.jar");
			
			dp.startThreads(pb_scp);
			
		}
		
	}

}
