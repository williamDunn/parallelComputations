import java.util.Properties;

public class Pi {

	public static void main(String[] args) throws InterruptedException {
		id();
		// Checks for pairs
		if (args.length % 2 != 0) {
			System.err.println("**ERROR: Arguments must be in pairs (0)");
			System.exit(0);
		}
		else {

			// Creates 2 arrays and enters each pair of arguments
			int[] start = new int[args.length / 2];
			int[] finish = new int[args.length / 2];
			int ii = 0;
			for (int i = 0; i < start.length; i++) {
				try {
					start[i] = Integer.parseInt(args[ii]);
					finish[i] = Integer.parseInt(args[ii + 1]);
					ii += 2;
				} catch (NumberFormatException e) {
					System.err.println("**ERROR: Arguments must be integers (0)");
					System.exit(0);
				}
			}

			// Creates workers per argument pair
			Worker[] worker = new Worker[start.length];
			for (int i = 0; i < start.length; i++) {
				worker[i] = new Worker(start[i], finish[i]);

			}
			//Creates threads per worker
			Thread[] thread = new Thread[worker.length];
			for(int i = 0; i < start.length; i++) {
				Runnable r = worker[i];
				thread[i] = new Thread(r);
			}

			System.out.printf("Creating %d thread(s).\n", thread.length);
			for (int i = 0; i < thread.length; i++) {
				//thread[i].run();
				thread[i].start();
				System.out.printf("Thread [%03d] started.\n", thread[i].getId());
			}
			
			for (int i = 0; i < thread.length; i++) {
				thread[i].join();
			}


			// adds total from each worker and prints each individual total
			double total = 0;
			for (int i = 0; i < worker.length; i++) {
				total += worker[i].computeTotal(start[i], finish[i]);
				System.out.printf("Sum of all primes [%,8d - %,9d) is %,12.0f\n", worker[i].getVal1(),
						worker[i].getVal2(), worker[i].getTotal());
			}
			
			//for (int i = 0; i < thread.length; i++) {
				//thread[i].join();
			//}

			System.out.printf("\nThe grand sum of all primes calculations is %,12.0f\n", total);

			id();
		}
	}

	//////////////////////////////////////////////////////////////////
	private static void id() {
		final String YOUR_NAME = "William Dunn";

		int cores = Runtime.getRuntime().availableProcessors();
		String osName = System.getProperty("os.name");
		String osVer = System.getProperty("os.version");
		String osArch = System.getProperty("os.arch");
		String javaVer = System.getProperty("java.version");
		String javaName = System.getProperty("java.vm.name");

		System.out.printf("\n%14s | %s (%s) | Cores: %d\n", osArch, osName, osVer, cores);
		System.out.printf("%14s | %s\n", javaVer, javaName);
		System.out.printf("%14s | %s\n\n", "PiThreads", YOUR_NAME);
	}
}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

class Worker implements Runnable {
	private int val1;
	private int val2;
	private long threadId;
	private double total;

	// constructor
	Worker(int val1, int val2) {
		this.setVal1(val1);
		this.setVal2(val2);
	}

	/////////////////////////////////////////////////////////////////

	// required method
	public void run() {
		threadId = Thread.currentThread().getId();
		total = computeTotal(val1, val2);
	}

	/////////////////////////////////////////////////////////////////

	// does the actual work
	public double computeTotal(int val1, int val2) {
		double total = 0;

		for (int i = val1; i < val2; i++)
			if (Slow_isPrime(i)) {
				total += i;
			}

		return total;
	}

	/////////////////////////////////////////////////////////////////

	// ------------------------------------------------
	// determines if a number is a prime number
	// you must use this code, and can't modify it.
	// -----------------------------------------------
	private static boolean Slow_isPrime(int val) {
		int i = 0;

		if (val <= 1)
			return false;

		if (val == 2)
			return true;

		if ((val % 2) == 0)
			return false;

		for (i = 3; i < val; i++)
			if ((val % i) == 0)
				return false;

		return true;
	}

	/////////////////////////////////////////////////////////////////

	public int getVal1() {
		return val1;
	}

	public void setVal1(int val1) {
		this.val1 = val1;
	}

	public int getVal2() {
		return val2;
	}

	public void setVal2(int val2) {
		this.val2 = val2;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}
}