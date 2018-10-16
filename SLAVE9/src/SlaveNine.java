import java.io.*;
import java.io.IOException;

public class SlaveNine {

	private static final String PATH_TO_FILES = "/tmp/abec/";
	public String mode;
	public String key;
	public String[] filename;

	public SlaveNine(String mode, String[] filename) {
		this.mode = mode;
		if (this.mode.equals("1")) {
			int nb_files = filename.length - 1;

			String[] files = new String[nb_files];
			for (int i = 0; i < nb_files; i++) {
				files[i] = filename[i + 1];
			}

			this.key = filename[0];
			this.filename = files;

		} else {
			this.filename = filename;
		}
	}
	
	public void CreateUM() throws IOException {
		
		String parts = this.filename[0].substring(
				this.filename[0].length() - 5, this.filename[0].length() - 4);
		
		PrintWriter writer = new PrintWriter(PATH_TO_FILES + "maps/UM" + parts + ".txt", "UTF-8"); 
		
		FileReader in = null;
		try {
			in = new FileReader(this.filename[0]);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		BufferedReader br = new BufferedReader(in);
		String line;
		while ((line = br.readLine()) != null) {
			System.out.println(line);

			// Suppression symbols ASCII 00 à 7F
			String line_adjusted = line.replaceAll("[^\\x00-\\x7f]+", "");

			String[] words = line_adjusted.split(" ");

			for (int i = 0; i < words.length; i++) {
				if (!words[i].equals("")){
					writer.println(words[i] + "," + 1);
					System.out.println(words[i]+",1");
				}
			}
		}
		
		writer.close();
	}

	public static void create_folder(String folder) throws IOException, InterruptedException {

		System.out.println("Create folder /tmp/abec/" + folder);
		ProcessBuilder pb_mkdir = new ProcessBuilder("mkdir", "-p", "/tmp/abec/" + folder);
		Process p = pb_mkdir.start();
		p.waitFor();

	}
	
	public void launchSlave(String smx) throws IOException {
		if (this.mode.equals("0")) {
			this.CreateUM();
		} else {
			System.out.println("WIP");
		}
	}

	public static void main(String[] args) throws InterruptedException, IOException {

		System.out.println("Launching Slave in mode : " + args[0]);

		int files_nb = args.length - 1;
		System.out.println("Number of files : " + files_nb);

		// New maps folder
		create_folder("maps");

		// Handle multiple files
		String[] files = new String[files_nb];
		for (int i = 0; i < files_nb; i++) {
			files[i] = args[i + 1];
		}

		SlaveNine slave = new SlaveNine(args[0], files);
		slave.launchSlave("0");
	}

}
