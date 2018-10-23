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
	
	public static void main(String[] args) throws IOException, InterruptedException {
		
		DeployDev dp = new DeployDev();
		
		String fileName = "/Users/Alex/Desktop/java/file_dispos.txt";

		FileReader in = new FileReader(fileName);
		BufferedReader br = new BufferedReader(in);

		String line;
		while ((line = br.readLine()) != null) {
			
			
			
			/* CREATE FOLDER in /tmp/abec 
			System.out.println("Create folder : /tmp/abec");
			
			ProcessBuilder pb_mkdir_tmp = new ProcessBuilder(
					"ssh", 
					"-o",
					"StrictHostKeyChecking=no",
					"abec@" + line, 
					"mkdir", "-p", "/tmp/abec/");
			Process p1 = pb_mkdir_tmp.start();
			
			dp.startThreads(pb_mkdir_tmp);
			
			System.out.println("Create folder : /tmp/abec/splits");
			
			ProcessBuilder pb_mkdir_splits = new ProcessBuilder(
					"ssh", 
					"-o",
					"StrictHostKeyChecking=no",
					"abec@" + line, 
					"mkdir", "-p", "/tmp/abec/splits");
			Process p2 = pb_mkdir_splits.start();
			
			dp.startThreads(pb_mkdir_splits);
			*/
			
			System.out.println("Delete existing jar" + line);
			ProcessBuilder rm_scp = new ProcessBuilder(
					"ssh",
					"-o",
					"StrictHostKeyChecking=no",
					"abec@" + line,
					"rm /tmp/abec/slavenine.jar");
			dp.startThreads(rm_scp);
			
			Thread.sleep(2000);
			
			System.out.println("Create file in /tmp/" + line);
			ProcessBuilder pb_scp = new ProcessBuilder(
					"scp",
					"-o",
					"StrictHostKeyChecking=no",
					"/Users/Alex/Desktop/java/slavenine.jar",
					"abec@" + line + ":/tmp/abec/slavenine.jar");
			
			dp.startThreads(pb_scp);
			
		}
		
	}

}
