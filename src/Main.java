import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

//	file path is:
//	D:\java-workspace\TMS_homework12\documentNumbers.txt

	public static void main(String[] args) throws IOException {
//		File file = new File(new Scanner(System.in).nextLine().trim());
//		DocNumsChecker.toCheckNums(file);
//		DocNumsChecker.toRecordNums(file);
		
		File fileDir = new File("../TMS_homework12/");
		CatalogInfo.listAll(fileDir);
	}
}
