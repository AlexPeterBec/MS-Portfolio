package partSix;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Deploy {
	
	public void startThreads(ProcessBuilder pb) throws IOException, InterruptedException{
		Process p = pb.start();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				p.getInputStream()));
		
		BufferedReader reader_err = new BufferedReader(new InputStreamReader(
				p.getErrorStream()));
		
		MyThread input = new MyThread(reader);
		input.start();
		
		MyThread input_err = new MyThread(reader_err);
		input_err.start();
		p.waitFor();
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		Deploy dp = new Deploy();
		String fileName = "/Users/Alex/Desktop/java/file_dispos.txt";

		FileReader in = new FileReader(fileName);
		BufferedReader br = new BufferedReader(in);

		String line;
		while ((line = br.readLine()) != null) {
			System.out.println("Create file in /tmp/" + line);

			// Create new dir
			
			ProcessBuilder pb_mkdir = new ProcessBuilder("ssh", "abec@"
					+ line, "mkdir", "-p", "/tmp/abec/splits");
			dp.startThreads(pb_mkdir);
			

			// Copy slave.jar
			/*
			ProcessBuilder pb_scp = new ProcessBuilder(
					"scp",
					"/Users/Alex/Desktop/java/slave.jar",
					"abec@" + line + ":/tmp/abec/slave.jar");
			dp.startThreads(pb_scp);
			*/
		}
	}

}
