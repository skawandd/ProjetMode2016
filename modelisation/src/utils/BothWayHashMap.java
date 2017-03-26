package utils;

import java.util.HashMap;

public class BothWayHashMap<T1, T2> extends HashMap{
	
	public Object getByValue(Object value){
		for(Object obj : this.keySet()){
			if(this.get(obj) == value) return obj;
		}
		return null;
	}
	
}
