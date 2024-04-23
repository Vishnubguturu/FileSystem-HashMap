import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.*;

public class FileSystemTest{
	
	@Test
	public void testFileSystem() {
		FileSystem file = new FileSystem();
		file.add("mySample.txt", "/user", "02/06/2021");
		file.add("mySample.txt", "/root", "02/01/2021");
		file.add("mySample2.txt", "/home", "02/01/2021");
		ArrayList<String> list = new ArrayList<>(Arrays.asList("mySample2.txt", "mySample.txt"));
		assertEquals(true, file.removeFile("mySample.txt", "/user"));
		assertEquals(false, file.removeByName("mySampleDummy.txt"));
		assertEquals(list, file.findAllFilesName());
	}
}
