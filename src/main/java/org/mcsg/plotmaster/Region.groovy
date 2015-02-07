package org.mcsg.plotmaster

import org.bukkit.World


class Region {

	int id;	
	int x, z
	int h, w
	
	long createdAt
	long loadedAt
	
	boolean changed
	
	String world;
	Map<Integer, Plot> plots;	
	
}
