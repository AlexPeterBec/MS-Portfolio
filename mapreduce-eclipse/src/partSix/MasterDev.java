package partSix;

import java.io.*;
import java.nio.charset.*;
import java.util.Timer;
import java.util.concurrent.TimeUnit;


public class MasterDev {
	
	private static void readOutput(Process p) throws IOException, InterruptedException {
		
		System.out.println("=== Standard output ===");
		
		InputStream is = p.getInputStream();
		InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
		BufferedReader br = new BufferedReader(isr);
		
		// Test output status
		boolean b = p.waitFor(12, TimeUnit.SECONDS);
		
		if (b != false ) {
			String line;
			while ((line = br.readLine()) != null ) {
				System.out.println(line);
			}
		} else {
			System.out.println("Destroyed process");
			p.destroy();
		}
	}
	
	/*
	private static void readError(Process p) throws IOException {
		
		System.out.println("=== Error output    ===");
		
		InputStream is2 = p.getErrorStream();
		InputStreamReader isr = new InputStreamReader(is2, StandardCharsets.UTF_8);
		BufferedReader br = new BufferedReader(isr);

		// Affichage output
		String line;
		while ((line = br.readLine()) != null ) {
			System.out.println(line);
		}
	}
	*/
	
	private static void launchSlave(String machine) throws IOException {
		ProcessBuilder pb = new ProcessBuilder(
				"ssh",
				"abec@"+machine,
				"java", 
				"-jar", 
				"/tmp/abec/slave.jar");
		Process p = pb.start();
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
		
		launchSlave("c45-14");
		
		/*
		ProcessBuilder pb = new ProcessBuilder(
				"ssh",
				"abec@c45-20",
				"java", 
				"-jar", 
				"/tmp/abec/slave.jar");
		Process p = pb.start();
		*/
		//readOutput(p);
	}

}
