package partFive;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

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

/* 39. Gestion des timeouts du master */
class Producer implements Runnable {
	private BlockingQueue<String> queue;
	
	private BufferedReader reader;
	
	public Producer(BlockingQueue<String> queue, BufferedReader reader) throws InterruptedException {
		this.queue = queue;
		this.reader = reader;
	}

	@Override
	public void run() {
		try {
			String line = null;
			while ((line = this.reader.readLine()) != null) {
				this.queue.put(line);
			}
			this.queue.put("fin");
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}
	}
}

class Consumer implements Runnable {
	private BlockingQueue<String> queue;
	private int time; // Time in seconds
	
	public Consumer(BlockingQueue<String> queue, int time){
		this.queue = queue;
		this.time = time;
	}

	@Override
	public void run() {
		String s;
		try {
			s = queue.poll(this.time, TimeUnit.SECONDS);
			if (s == null){
				System.out.println("--- TIMEOUT ---");
			}
			else{
				System.out.println("--- Print result ---");
				while(s != null){
					System.out.println(s);
					s = queue.poll();
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

public class Master {
	public static void main(String[] args) throws IOException, InterruptedException {
		ProcessBuilder pb = new ProcessBuilder("ls", "-al", "/Users/Alex");

		/** QUESTION 36 : Errors **/
		Process p = pb.start();

		/* INPUT STREAM */
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				p.getInputStream()));

		MyThread threadInputStream = new MyThread(reader);
		threadInputStream.start();

		/* IN CASE OF ERRORS */
		BufferedReader reader_err = new BufferedReader(new InputStreamReader(
				p.getErrorStream()));

		MyThread threadErrorStream = new MyThread(reader_err);
		threadErrorStream.start();

		/** QUESTION 37 : Run slave from master **/
		// Let's create a blocking queue that can hold at most 5 elements.
		BlockingQueue<String> queue = new ArrayBlockingQueue<>(5);

		// The two threads will access the same queue, in order to test its blocking capabilities.
		ProcessBuilder pb_slave = new ProcessBuilder("java", "-jar", "/Users/Alex/Desktop/slave.jar");
		Process p_slave = pb_slave.start();

		BufferedReader reader_slave = new BufferedReader(new InputStreamReader(p_slave.getInputStream()));
		
		Thread slaveInputStream = new Thread(new Producer(queue, reader_slave));
		
		slaveInputStream.start();
		
		/*
		BufferedReader reader_slave_err = new BufferedReader(new InputStreamReader(p_slave.getInputStream()));
		Thread slaveErrStream = new Thread(new RunSlave(queue, reader_slave_err));
		slaveErrStream.start();
		*/
		
		Thread consumer = new Thread(new Consumer(queue, 15));
		consumer.start();

	}
}