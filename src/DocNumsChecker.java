import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;

public class DocNumsChecker {

	public static void toCheckNums(File file) throws IOException {
		Path path = Paths.get(file.toURI());
		try {
			BufferedReader reader = Files.newBufferedReader(path);
			while (reader.ready()) {
				String line = reader.readLine();
				System.out.println(getValid(line));
			}
			System.out.println("Checking is finished");
		} catch (IOException e) {
			System.out.println("file not found");
		}
	}

	public static void toRecordNums(File file) throws IOException {
		Path path = Paths.get(file.toURI());
		try {
			BufferedReader reader = Files.newBufferedReader(path);
			BufferedWriter writerValid = new BufferedWriter(new FileWriter("validDocs.txt"));
			BufferedWriter writerInvalid = new BufferedWriter(new FileWriter("invalidDocs.txt"));
			while (reader.ready()) {
				String line = reader.readLine();
				toWrite(line, writerValid, writerInvalid);
			}
			writerValid.close();
			writerInvalid.close();
			System.out.println("Writting is finished");
		} catch (IOException e) {
			System.out.println("file not found");
		}
	}

	private static boolean getValid(String line) {
		String patternType1 = "^docnum?([A-Za-z0-9]{9})$";
		String patternType2 = "^contract?([A-Za-z0-9]{7})$";
		if (Pattern.matches(patternType1, line.trim()) || Pattern.matches(patternType2, line.trim())) {
			return true;
		} else {
			return false;
		}
	}

	private static void toWrite(String line, BufferedWriter writerValid, BufferedWriter writerInvalid)
			throws IOException {
		try {
			if (getValid(line)) {
				writerValid.append(line.trim() + "\n");
			} else {
				writerInvalid.append(line.trim() + getReasonInvalid(line) + "\n");
			}
		} catch (IOException e) {
			System.out.println("file not found");
		}
	}

	private static String getReasonInvalid(String line) throws StringIndexOutOfBoundsException {
		StringBuilder checkResult = new StringBuilder("");
		if (line.trim().length() != 15) {
			checkResult.append(" (wrong length)");
		}
		if (line.trim().replaceAll(" ", "").equals("")) {
			checkResult.append(" (empty) (must begin with \"docnum\" or \"contract\")");
		}
		if (line.trim().replaceAll("[0-9a-zA-Z]", "").matches("[^0-9a-zA-Z]+")) {
			checkResult.append(" (contains forbidden symbols)");
		}
		try {
			if (!line.trim().substring(0, 6).equals("docnum") & !line.trim().substring(0, 8).equals("contract")) {
				checkResult.append(" (must begin with \"docnum\" or \"contract\")");
			}
		} catch (StringIndexOutOfBoundsException e) {
		}
		return checkResult.toString();
	}
}
