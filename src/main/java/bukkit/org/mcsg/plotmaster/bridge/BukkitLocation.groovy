package bukkit.org.mcsg.plotmaster.bridge

import org.bukkit.Location
import org.mcsg.plotmaster.bridge.PMLocation;

class BukkitLocation implements PMLocation{

	Location location
	
	def BukkitLocation(Location loc){
		this.location = loc
	}
	
	
	@Override
	public int getX() {
		location.getBlockX()
	}

	@Override
	public int getY() {
		location.getBlockY()
	}

	@Override
	public int getZ() {
		location.getBlockZ()
	}

	@Override
	public String getWorld() {
		location.getWorld().getName()
	}

}
