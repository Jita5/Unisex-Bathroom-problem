import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * The Class Control.
 */
public class Control {

	/** The bathroom. */
	Bathroom bathroom = new Bathroom();

	/** The person type. */
	private String personType = "";

	/** The arrive time. */
	private int arriveTime = 0;

	/** The service time. */
	private int serviceTime = 0;

	/** The output. */
	public static String output = "";

	/**
	 * Instantiates a new control.
	 *
	 * @param input
	 *            the input
	 * @param threadCount
	 *            the thread count
	 */
	public Control(String input, int threadCount) {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(input);
		Thread[] ThreadSet = new Thread[threadCount];
		ThreadID.reset();
		int x = 0;

		// Loop the input data and initialize a new Person
		while (sc.hasNext()) {

			personType = sc.next();
			arriveTime = sc.nextInt();
			serviceTime = sc.nextInt();
			ThreadSet[x] = new Person(bathroom, personType, arriveTime, serviceTime);
			x++;
		}

		// Starting all the new Person threads
		for (int i = 0; i < threadCount; i++) {
			ThreadSet[i].start();
		}

		// Stopping Threads
		for (int i = 0; i < threadCount; i++) {
			try {
				ThreadSet[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// Writing output to file
		try {
			String directory = System.getProperty("user.dir");
			File outFile = new File(directory + "\\" + "ZombieOutput.txt");
			PrintStream out = new PrintStream(new FileOutputStream(outFile));

			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(output);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				out.println(line);
			}
			out.close();
		} catch (IOException iox) {
			iox.printStackTrace();
		}
	}
}
