package partNine;

import java.io.*;
import java.util.*;

public class MasterDev {

	private final static String COMPUTER = "/Users/Alex/Desktop/java";
	private String[] computers;
	private String fileDispos;
	private final static int COUNT = 3;

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

		/*
		 * Map<String, String> umx_machines = ms.getMapsComputer();
		 * 
		 * ms.buildUMs();
		 * 
		 * Map<String, ArrayList<String>> keys_umx = ms.waitForMap();
		 * System.out.println("Keys / UMx : " + keys_umx.toString());
		 * System.out.println("UMX / Machines : " + umx_machines.toString());
		 * ms.shuffle(umx_machines, keys_umx);
		 * 
		 * Map<String, ArrayList<String>> distributedKeys = ms
		 * .distributeKeys(keys_umx); System.out.println("Distributed keys : " +
		 * distributedKeys.toString());
		 * 
		 * ms.startReducingSortedMaps(keys_umx, distributedKeys);
		 */
	}

}
