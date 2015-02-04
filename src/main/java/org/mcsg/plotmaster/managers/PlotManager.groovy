package org.mcsg.plotmaster.managers

import org.bukkit.Location
import org.fusesource.jansi.AnsiRenderer.Code;
import org.mcsg.plotmaster.Plot;
import org.mcsg.plotmaster.PlotType;
import org.mcsg.plotmaster.Region;
import org.mcsg.plotmaster.backend.Backend
import org.mcsg.plotmaster.backend.BackendManager;
import org.mcsg.plotmaster.utils.TaskUtils;

abstract class PlotManager {

	Backend backend;
	
	def PlotManager(){
		backend = BackendManager.getBackend();
	}
	
	// For all methods, if Closure is not null, will run async with the Closure being the callback
	
	abstract Region getRegionAt(int x, int z, Closure c)
	
	abstract Region getRegionAt(Location l, Closure c)
	
	abstract Region getRegion(int id, Closure c)
	
	abstract Plot getPlot(int x, int z, Closure c)
	
	abstract Plot getPlotAt(Location l, Closure c)
	
	abstract Plot getPlot(int id, Closure c)
	
	abstract boolean plotExist(int x, int z, Closure c)
	
	abstract boolean regionExist(int x, int z, Closure c)
	
	abstract createPlot(int x, int y, PlotType type, Closure c)
	
	abstract createRegion(int x, int y, int h, int w, Closure c)
	
	
	def asyncWrap(Closure callback, Closure code){
		if (callback){
			Thread.start {
				def result = code()
				callback(result)
			}	
		}else {
			return code()	
		}
	}
	
}
