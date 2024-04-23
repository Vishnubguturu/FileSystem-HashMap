import static org.junit.Assert.*;

import org.junit.*;

public class FileDataTest {
	@Test
	public void testFileData1() {
		FileData file = new FileData("Sample.txt", "/home", "02/01/2021");
		String f = "{Name: Sample.txt, Directory: /home, Modified Date: 02/01/2021}";
		assertEquals(f, file.toString());
	}
	
	@Test
	public void testFileData2() {
		FileData file = new FileData("input.txt", "/Users/home", "12/06/2004");
		String f = "{Name: input.txt, Directory: /Users/home, ModifiedDate: 12/06/2004}";
		String g = f.substring(1, 16);
		assertEquals(g, file.toString().substring(1,16));
	}
}
