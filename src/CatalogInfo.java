import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Stream;

public class CatalogInfo {
	private static StringBuilder next;
	private static StringBuilder next2;

	public static void listAll(File file) {
		System.out.println(Arrays.deepToString(file.listFiles()));
		for (File item : Objects.requireNonNull(file.listFiles())) {
			if (item.isDirectory()) {
				System.out.print(item.getName() + "\t [folder] \n");
				next  = new StringBuilder("\t > ");
				listAll(item, next);
			} else {
				System.out.print(item.getName() + "\t [file] \n");
			}
		}
	}

	private static void listAll(File file, StringBuilder next) {
		StringBuilder extra= new StringBuilder(next);
		File[] obj = file.listFiles();
		Stream<File> sorted = Arrays.stream(obj).sorted((o1, o2) -> {
			if (o1.isDirectory() && o2.isFile()){
				return -1;
			}
			if (o1.isFile() && o2.isDirectory()){
				return 1;
			}
			if (o1.isFile() && o2.isFile()) {
				return o1.getName().compareTo(o2.getName());
			}
			if (o1.isDirectory() && o2.isDirectory()) {
				return o1.getName().compareTo(o2.getName());
			}
			return 0;
		});
		for (File item : sorted.toList()) {
			next2 = new StringBuilder(extra.insert(0, "\t").toString());
			if (item.isDirectory()) {
				System.out.print(next + item.getName() + "\t [folder] \n");
				listAll(item, next2);
			} else {
				System.out.print(next + item.getName() + "\t [file] \n");
			}
			next2 = new StringBuilder(extra.delete(0, 1).toString());
		}
	}
}
