import java.util.List;
import java.util.Objects;
import java.util.ArrayList;
import java.lang.Math;

public class MyHashMap<K, V> implements DefaultMap<K, V> {
	public static final double DEFAULT_LOAD_FACTOR = 0.75;
	public static final int DEFAULT_INITIAL_CAPACITY = 16;
	public static final String ILLEGAL_ARG_CAPACITY = "Initial Capacity must be non-negative";
	public static final String ILLEGAL_ARG_LOAD_FACTOR = "Load Factor must be positive";
	public static final String ILLEGAL_ARG_NULL_KEY = "Keys must be non-null";
	
	private double loadFactor;
	private int capacity;
	private int size;

	private List<HashMapEntry<K, V>>[] buckets;  
	
	public MyHashMap() {
		this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
	}
	
	/**
	 * @param initialCapacity the initial capacity of this MyHashMap
	 * @param loadFactor the load factor for rehashing this MyHashMap
	 * @throws IllegalArgumentException if initialCapacity is negative or loadFactor not positive
	 */
	@SuppressWarnings("unchecked")
	public MyHashMap(int initialCapacity, double loadFactor) throws IllegalArgumentException {
		if(initialCapacity < 0) {
			throw new IllegalArgumentException(ILLEGAL_ARG_CAPACITY);
		}
		if(loadFactor <= 0) {
			throw new IllegalArgumentException(ILLEGAL_ARG_LOAD_FACTOR);
		}
		this.size = 0;
		this.capacity = DEFAULT_INITIAL_CAPACITY;
		this.loadFactor = size/capacity;
		this.buckets = (List<HashMapEntry<K, V>>[]) new List<?>[capacity];
	}

	@Override
	public boolean put(K key, V value) throws IllegalArgumentException {
		if(key == null) {
			throw new IllegalArgumentException(ILLEGAL_ARG_NULL_KEY);
		}
		if(loadFactor >= 0.75) {
			rehash();
		}
		int keyHash = Objects.hashCode(key);
		int index = Math.abs(keyHash % capacity);
		if(containsKey(key)) {
			return false;
		}
		else if(buckets[index] == null) {
			buckets[index] = new ArrayList<HashMapEntry<K, V>>();
			buckets[index].add(new HashMapEntry<K, V>(key, value));
			size++;
			return true;
		}
		return false;
	}

	@Override
	public boolean replace(K key, V newValue) throws IllegalArgumentException {
		if(key == null) {
			throw new IllegalArgumentException(ILLEGAL_ARG_NULL_KEY);
		}
		int keyHash = Objects.hashCode(key); 
		int index = Math.abs(keyHash % capacity);
		if(buckets[index] != null) {
			for(HashMapEntry<K, V> entry: buckets[index]) {
				if(entry.key.equals(key)) {
					entry.setValue(newValue);
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean remove(K key) throws IllegalArgumentException {
		if(key == null) {
			throw new IllegalArgumentException(ILLEGAL_ARG_NULL_KEY);
		}
		int keyHash = Objects.hashCode(key);
		int index = Math.abs(keyHash % capacity);
		if(buckets[index] != null) {
			for(HashMapEntry<K, V> entry: buckets[index]) {
				if(entry.key.equals(key)) {
					size--;
					return buckets[index].remove(entry);
				}
			}
		}
		return false;
	}

	@Override
	public void set(K key, V value) throws IllegalArgumentException {
		if(key == null) {
			throw new IllegalArgumentException(ILLEGAL_ARG_NULL_KEY);
		}
		if(loadFactor >= 0.75) {
			rehash();
		}
		if(!containsKey(key)) {
			put(key, value);
		}
		else {
			replace(key, value);
		}
	}

	@Override
	public V get(K key) throws IllegalArgumentException {
		if(key == null) {
			throw new IllegalArgumentException(ILLEGAL_ARG_NULL_KEY);
		}
		int keyHash = Objects.hashCode(key);
		int index = Math.abs(keyHash % capacity);
		if(buckets[index] == null) {
			return null;
		}
		else {
			for(HashMapEntry<K, V> entry: buckets[index]) {
				if(entry.key.equals(key)) {
					return entry.value;
				}
			}
		}
		return null;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public boolean containsKey(K key) throws IllegalArgumentException {
		if(key == null) {
			throw new IllegalArgumentException(ILLEGAL_ARG_NULL_KEY);
		}
		if(get(key) != null) {
			return true;
		}
		return false;
	}

	@Override
	public List<K> keys() {
		List<K> keysList = new ArrayList<K>();
		if(size == 0) {
			return keysList;
		}
		for(int i = 0; i < buckets.length; i++) {
			if(buckets[i] != null) {
				for(HashMapEntry<K, V> entry: buckets[i]) {
					if(!entry.key.equals(null)) {
						keysList.add(entry.key);
					}
				}
			}
		}
		return keysList;
	}
	
	@SuppressWarnings("unchecked")
	public void rehash(){
		List<HashMapEntry<K, V>>[] newBuckets = (List<HashMapEntry<K, V>>[]) new List<?>[2*capacity];
		List<HashMapEntry<K, V>>[] oldBuckets = this.buckets;
		this.buckets = newBuckets;
		this.size = 0;
		for(int i =0; i < oldBuckets.length; i++) {
			if(oldBuckets[i] != null) {
				for(HashMapEntry<K, V> entry: oldBuckets[i]) {
					put(entry.getKey(), entry.getValue());
				}
			}
		}
	}
	
	private static class HashMapEntry<K, V> implements DefaultMap.Entry<K, V> {
		
		K key;
		V value;
		
		private HashMapEntry(K key, V value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public K getKey() {
			return key;
		}

		@Override
		public V getValue() {
			return value;
		}
		
		@Override
		public void setValue(V value) {
			this.value = value;
		}
	}
}
