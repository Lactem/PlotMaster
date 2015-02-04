package org.mcsg.plotmaster.cache

import org.mcsg.plotmaster.utils.TaskUtils;
import groovy.transform.CompileStatic;

@CompileStatic
class MemCache<K, V extends Cacheable<K>> implements Cache{
	HashMap<K, V> cache;
	
	int cullPeriod;
	
	def MemCache(int cullPeriod){
		cache = new HashMap<>();
		this.cullPeriod = cullPeriod;
		
		cullProccessor()
	}
	
	
	boolean contains(K id){
		cache.containsKey(id)
	}

	V get(K id){
		cache.get(id);
	}
	
	
	def cache(K id, V value){
		cache.put(id, value)
	}
	
	 private cullProccessor(){
		 TaskUtils.asyncRepeating(cullPeriod, cullPeriod) {
			 cache = cache.findAll {
				 !it.value.canCull()
			 }
		 }
	 }
}
