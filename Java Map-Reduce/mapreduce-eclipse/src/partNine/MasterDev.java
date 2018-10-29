package partNine;

import java.io.*;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MasterDev {

	private final static String COMPUTER = "/Users/Alex/Desktop/java";
	private String[] computers;
	private String fileDispos;
	private final static int COUNT = 3;
	
	public Map<String, ArrayList<String>> distributeKeys(
			Map<String, ArrayList<String>> keys_umx) {
		Map<String, ArrayList<String>> hm = new HashMap<String, ArrayList<String>>();

		int n_computers = this.computers.length;

		int distribution = 0;
		Set<String> keySet = keys_umx.keySet();
		Iterator<String> itr = keySet.iterator();
		while (itr.hasNext()) {
			String key = itr.next();
			int comp = distribution % n_computers;
			// System.out.println("Computer n°" + comp);
			if (!hm.keySet().contains(this.computers[comp])) {
				ArrayList<String> list = new ArrayList<String>();
				hm.put(this.computers[comp], list);
			}
			hm.get(this.computers[comp]).add(key);
			hm.put(this.computers[comp], hm.get(this.computers[comp]));
			distribution += 1;
		}
		return hm;
	}
	
	public void startKeys(ProcessBuilder pb, Map<String, ArrayList<String>> hm, String um) throws IOException {
		Process p = pb.start();

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				p.getInputStream()));

		//Keys input = new Keys(reader, hm, um);
		//input.start();

	}
	
	public void buildUMs() throws IOException {

		int count = 0;

		/* Create Hashmap with keys : <KEY, <UMx, UMy>> */
		Map<String, ArrayList<String>> hm = new HashMap<String, ArrayList<String>>();

		while ((count < COUNT)) {
			ProcessBuilder[] pb_slaves = new ProcessBuilder[COUNT];

			/* GO TO COMPUTER */
			System.out.println("COMPUTER : " + this.computers[count]);

			/* RUN SLAVE. JAR */

			for (int i = 0; i < COUNT; i++) {

				System.out.println("| Run slave jar on file s" + i + ".txt with " + this.computers[count]);
				ProcessBuilder pb_slave = new ProcessBuilder(
						"ssh", "-o", "StrictHostKeyChecking=no", 
						"abec@" + this.computers[count], 
						"java", "-jar", "/tmp/abec/slavenine.jar", 
						"0", "/tmp/abec/splits/s" + i + ".txt");

				pb_slaves[i] = pb_slave;
				// this.startKeys(pb_slave, hm, "UM" + count);

			}
			this.startKeys(pb_slaves[0], hm, "UM" + 0);
			this.startKeys(pb_slaves[1], hm, "UM" + 1);
			this.startKeys(pb_slaves[2], hm, "UM" + 2);

			count++;

		}

		// br.close();
	}

	public void startThreads(ProcessBuilder pb) throws IOException {
		Process p = pb.start();

		BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

		BufferedReader reader_err = new BufferedReader(new InputStreamReader(p.getErrorStream()));

		MyThread input = new MyThread(reader);
		input.start();

		MyThread input_err = new MyThread(reader_err);
		input_err.start();
	}

	public void pasteSplits() throws IOException {

		int count = 0;
		int count_splits = 3;

		while (count < count_splits) {
			ProcessBuilder[] pb_scps = new ProcessBuilder[count_splits];
			System.out.println("COMPUTER : " + this.computers[count]);

			for (int i = 0; i < count_splits; i++) {
				System.out.println("Copying");
				ProcessBuilder pb_scp = new ProcessBuilder("scp", COMPUTER + "/splits/S" + i + ".txt",
						"abec@" + this.computers[count] + ":/tmp/abec/splits/s" + i + ".txt");

				pb_scps[i] = pb_scp;
			}
			count++;

			this.startThreads(pb_scps[0]);
			this.startThreads(pb_scps[1]);
			this.startThreads(pb_scps[2]);
		}
	}
	
	public void launchMap() throws IOException {
		
		int count = 0;

		Map<String, ArrayList<String>> hm = new HashMap<String, ArrayList<String>>();

		while ((count < COUNT)) {
			ProcessBuilder[] pb_slaves = new ProcessBuilder[COUNT];

			System.out.println("COMPUTER : " + this.computers[count]);

			/* RUN SLAVE. JAR */
			for (int i = 0; i < COUNT; i++) {
				
				System.out.println("| Run slave jar on file s" + i + ".txt with " + this.computers[count]);
				
				ProcessBuilder pb_slave = new ProcessBuilder(
						"ssh", "-o", "StrictHostKeyChecking=no", 
						"abec@" + this.computers[count], 
						"java", "-jar", "/tmp/abec/slave.jar", "0", 
						"/tmp/abec/splits/s" + i + ".txt");

				pb_slaves[i] = pb_slave;

			}
			
			this.startKeys(pb_slaves[0], hm, "UM" + 0);
			this.startKeys(pb_slaves[1], hm, "UM" + 1);
			this.startKeys(pb_slaves[2], hm, "UM" + 2);

			count++;

		}
		
	}

	public MasterDev(String fileDispos) throws IOException {

		this.fileDispos = "/Users/Alex/Desktop/java/file_dispos.txt";
		this.computers = new String[COUNT];

		FileReader in = new FileReader(this.fileDispos);
		BufferedReader br = new BufferedReader(in);

		String line;
		int count = 0;
		while ((count < COUNT) && (line = br.readLine()) != null) {
			this.computers[count] = line;
			count++;
		}
		
		br.close();
		
	}

	public static void main(String[] args) throws IOException, InterruptedException {

		MasterDev ms = new MasterDev("file_dispos.txt");

		ms.pasteSplits();

		ms.launchMap();
	
	}

}
