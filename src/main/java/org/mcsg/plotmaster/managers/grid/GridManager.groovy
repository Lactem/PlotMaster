package org.mcsg.plotmaster.managers.grid

import groovy.lang.Closure;

import org.bukkit.Location;
import org.mcsg.plotmaster.Plot;
import org.mcsg.plotmaster.PlotType;
import org.mcsg.plotmaster.Region;
import org.mcsg.plotmaster.cache.Cache
import org.mcsg.plotmaster.managers.PlotManager

class GridManager extends PlotManager{

	Cache RegionCache;
	Cache xyRegionCache;
	
	Cache idPlots;
	Cache xyPlots;
	
	
	def GridManager() {
		
		
		
		
		
	}


	@Override
	public Region getRegionAt(int x, int z, Closure c) {
		this.asyncWrap(c) {
			Region r = xyRegionCache.get("$x:$z")
			if(r) {
				return r
			} else {
				
			}
		}
	}


	@Override
	public Region getRegionAt(Location l, Closure c) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Region getRegion(int id, Closure c) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Plot getPlot(int x, int z, Closure c) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Plot getPlotAt(Location l, Closure c) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Plot getPlot(int id, Closure c) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean plotExist(int x, int z, Closure c) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean regionExist(int x, int z, Closure c) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public Object createPlot(int x, int y, PlotType type, Closure c) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Object createRegion(int x, int y, int h, int w, Closure c) {
		// TODO Auto-generated method stub
		return null;
	}
	

	
	
	
	
	
	


}
