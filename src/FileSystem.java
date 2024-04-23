import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileSystem {

    MyHashMap<String, ArrayList<FileData>> nameMap;
    MyHashMap<String, ArrayList<FileData>> dateMap;

    public FileSystem() {
    	nameMap = new MyHashMap<String, ArrayList<FileData>>();
    	dateMap = new MyHashMap<String, ArrayList<FileData>>();
    }

    public FileSystem(String inputFile) {
    	nameMap = new MyHashMap<String, ArrayList<FileData>>();
    	dateMap = new MyHashMap<String, ArrayList<FileData>>();
        try {
            File f = new File(inputFile);
            Scanner sc = new Scanner(f);
            while (sc.hasNextLine()) {
                String[] data = sc.nextLine().split(", ");
                add(data[0], data[1], data[2]);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }

    public boolean add(String fileName, String directory, String modifiedDate) {
    	if(nameMap.containsKey(fileName)) {
    		for(FileData file: nameMap.get(fileName)) {
    			if(file.dir.equals(directory)) {
    				return false;
    			}
    		}
    		ArrayList<FileData> list = nameMap.get(fileName);
    		list.add(new FileData(fileName, directory, modifiedDate));
    	}
    	if(!nameMap.containsKey(fileName)){
    		ArrayList<FileData> list = new ArrayList<>();
    		list.add(new FileData(fileName, directory, modifiedDate));
    		nameMap.put(fileName, list);
    	}
		if(dateMap.containsKey(modifiedDate)) {
    		ArrayList<FileData> dlist = dateMap.get(modifiedDate);
    		dlist.add(new FileData(fileName, directory, modifiedDate));
    		return true;
    	}
		if(!dateMap.containsKey(modifiedDate)) {
			ArrayList<FileData> dlist = new ArrayList<>();
    		dlist.add(new FileData(fileName, directory, modifiedDate));
    		dateMap.put(modifiedDate, dlist);
    		return true;
		}
    	return false;
    }

    public FileData findFile(String name, String directory) {
    	if(nameMap.containsKey(name)) {
    		ArrayList<FileData> list = nameMap.get(name);
    		for(int i = 0; i < list.size(); i++) {
    			if(list.get(i).dir.equals(directory)) {
    				return list.get(i);
    			}
    		}
    	}
    	return null;
    }

    public ArrayList<String> findAllFilesName() {
    	ArrayList<String> list = (ArrayList<String>) nameMap.keys();
    	return list;
    }

    public ArrayList<FileData> findFilesByName(String name) {
    	if(nameMap.containsKey(name)) {
    		return nameMap.get(name);
    	}
    	return new ArrayList<>();
    }

    public ArrayList<FileData> findFilesByDate(String modifiedDate) {
    	if(dateMap.containsKey(modifiedDate)) {
    		return dateMap.get(modifiedDate);
    	}
    	return new ArrayList<>();
    }

    public ArrayList<FileData> findFilesInMultDir(String modifiedDate) {
    	ArrayList<FileData> result = new ArrayList<>();
    	if(!dateMap.containsKey(modifiedDate)) {
    		return result;
    	}
    	else {
    		for(FileData file: dateMap.get(modifiedDate)) {
    			if(nameMap.get(file.name).size() >= 2) {
    				result.add(file);
    			}
    		}
    		return result;
    	}
    }

    public boolean removeByName(String name) {
    	if(nameMap.containsKey(name)) {
    		for(FileData file: nameMap.get(name)) {
    			if(dateMap.containsKey(file.lastModifiedDate)) {
    				ArrayList<FileData> list = dateMap.get(file.lastModifiedDate);
    				if(list.size() > 1) {
    					list.remove(file);
    				}
    				else {
    					dateMap.remove(file.lastModifiedDate);
    				}
    			}
    		}
    		nameMap.remove(name);
    		return true;
    	}
    	return false;
    }

    public boolean removeFile(String name, String directory) {
    	if(nameMap.containsKey(name)) {
    		ArrayList<FileData> list = nameMap.get(name);
    		for(FileData file: list) {
    			if(file.dir.equals(directory)) {
    				if(dateMap.containsKey(file.lastModifiedDate)) {
    					ArrayList<FileData> dlist = dateMap.get(file.lastModifiedDate);
    					if(dlist.size() > 1) {
    						dlist.remove(file);
    					}
    					else {
    						dateMap.remove(file.lastModifiedDate);
    					}
    				}
    				list.remove(file);
    				return true;
    			}
    		}
    	}
    	return false;
    }
}
