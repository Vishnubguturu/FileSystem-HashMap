import java.util.List;

/**
 * @param <K> Key
 * @param <V> Value
 */
public interface DefaultMap<K, V> {
	
	interface Entry<K, V> {
		K getKey();
		V getValue();
		
		void setValue(V value);
	}

	/**
	 * Adds the specified key, value pair to this DefaultMap, duplicate keys are not allowed
	 * 
	 * @return true if the addition was successful
	 * @throws IllegalArgument exception if the key is null
	 */
	boolean put(K key, V value) throws IllegalArgumentException;
	
	/**
	 * Replaces the value that maps to the key
	 * @param key The key whose value is being replaced
	 * @param newValue The value to replace the existing value with
	 * @return true if the key was in this DefaultMap
	 * @throws IllegalArgument exception if the key is null
	 */
	boolean replace(K key, V newValue) throws IllegalArgumentException;
	
	/**
	 * Remove the entry corresponding to the given key
	 * 
	 * @return true if remove was successful
	 * @throws IllegalArgument exception if the key is null
	 */
	boolean remove(K key) throws IllegalArgumentException;
	
	/**
	 * Adds the key, value pair to this DefaultMap if it is not present,
	 * otherwise, replaces the value with the given value
	 * Does add() and remove() in the same method
	 * @throws IllegalArgument exception if the key is null
	 */
	void set(K key, V value) throws IllegalArgumentException; 
	
	/**
	 * @return the value corresponding to the specified key
	 * @throws IllegalArgument exception if the key is null
	 */
	V get(K key) throws IllegalArgumentException;
	
	/**
	 * @return The number of (key, value) pairs in this DefaultMap
	 */
	int size();
	
	/**
	 * @return true iff the DefaultMap is empty
	 */
	boolean isEmpty();
	
	/**
	 * @return true if the specified key is in this DefaultMap
	 * @throws IllegalArgument exception if the key is null
	 */
	boolean containsKey(K key) throws IllegalArgumentException;
	
	/**
	 * @return an array containing the keys of this DefaultMap.
	 */
	List<K> keys();
}
