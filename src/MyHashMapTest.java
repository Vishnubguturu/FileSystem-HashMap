import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.*;

public class MyHashMapTest {
	
	private DefaultMap<String, String> testMap;
	private DefaultMap<String, String> mapWithCap;
	public static final String TEST_KEY = "Test Key";
	public static final String TEST_VAL = "Test Value";
	
	@Before
	public void setUp() {
		testMap = new MyHashMap<>();
		mapWithCap = new MyHashMap<>(4, MyHashMap.DEFAULT_LOAD_FACTOR);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testPut_nullKey() {
		testMap.put(null, TEST_VAL);
	}

	@Test
	public void testKeys_nonEmptyMap() {
		List<String> expectedKeys = new ArrayList<>(5);
		for(int i = 0; i < 5; i++) {
			testMap.put(TEST_KEY + i, TEST_VAL + i);
			expectedKeys.add(TEST_KEY + i);
		}
		List<String> resultKeys = testMap.keys();
		Collections.sort(resultKeys);
		assertEquals(expectedKeys, resultKeys);
	}
	
	@Test
	public void testput() {
		testMap = new MyHashMap<>();
		testMap.put(TEST_KEY, TEST_VAL);
		assertEquals(false, testMap.put(TEST_KEY, "val"));
		testMap.put("Tester", "Test");
		testMap.set("Tester", TEST_VAL);
		testMap.set("Two", TEST_VAL);
		testMap.remove("Tester");
	}
	
	@Test
	public void testreplace() {
		testMap = new MyHashMap<>();
		testMap.put(TEST_KEY, TEST_VAL);
		assertEquals(true, testMap.replace(TEST_KEY, "Test"));
		assertEquals(false, testMap.isEmpty());
	}
	
	@Test
	public void testget() {
		testMap = new MyHashMap<>();
		testMap.put(TEST_KEY, TEST_VAL);
		testMap.set(TEST_KEY, "Test");
		assertEquals("Test", testMap.get(TEST_KEY));
		assertEquals(true, testMap.containsKey(TEST_KEY));
	}
	
	@Test
	public void testrehash() {
		mapWithCap = new MyHashMap<>(4, 0.75);
		mapWithCap.put(TEST_KEY, TEST_VAL);
		mapWithCap.put("TEST", "VALUE");
		mapWithCap.put("Tester", "value");
		mapWithCap.set("Testing", "V");
		mapWithCap.set("Testing", "T");
		mapWithCap.set("Tinytest", "Tiny");
		assertEquals(5, mapWithCap.size());
	}
	
}
