
/**
 * Author: David Brazeau
 * Date: Nov 8, 2015
 */
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.JFileChooser;

/**
 * The Class ZombieBathroom.
 */
class ZombieBathroom {

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void main(String[] args) throws IOException {

		// Setting dir to where executing from
		String directory = System.getProperty("user.dir");
		// New JFileChooser with path
		JFileChooser fileChooser = new JFileChooser(directory);
		// Showing dialog
		int result = fileChooser.showOpenDialog(fileChooser);
		// Creating variables to be used
		String fileName;
		String input = "";
		int threadCount = 0;
		// JFileChooser to pick file
		if (result == JFileChooser.APPROVE_OPTION) {
			// Getting selected file
			File selectedFile = fileChooser.getSelectedFile();
			// Getting file name
			fileName = selectedFile.getName();
			// Removing file extension
			fileName = fileName.substring(0, fileName.lastIndexOf("."));
			// Creating scanner to read in the file
			@SuppressWarnings("resource")
			Scanner sc = new Scanner(selectedFile);
			while (sc.hasNextLine()) {
				input += sc.nextLine() + "\r\n";
				threadCount++;
			}
		}
		// Creating new instance
		new Control(input, threadCount);
	} // End main()
}
