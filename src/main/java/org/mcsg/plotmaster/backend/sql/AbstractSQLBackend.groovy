package org.mcsg.plotmaster.backend.sql

import groovy.lang.Closure;
import groovy.sql.Sql;
import groovy.transform.CompileStatic;

import javax.sql.DataSource

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.mcsg.plotmaster.Plot
import org.mcsg.plotmaster.PlotType;
import org.mcsg.plotmaster.Region;
import org.mcsg.plotmaster.backend.Backend;

abstract class AbstractSQLBackend implements Backend{

	DataSource ds;
	String world;

	def regions, plots, users, access_list

	public void load(String world){
		this.world = world;

		regions = "${world}_regions"
		plots  = "${world}_plots"
		users  = "${world}_users"
		access_list = "${world}_access_list"

		def sql = getSql();

		sql.execute("""
			CREATE TABLE IF NOT EXISTS `${regions}` (
				 `id` int(11) NOT NULL AUTO_INCREMENT,
				 `name` varchar(128) NOT NULL,
				 `world` int(11) NOT NULL,
				 `x` int(11) NOT NULL,
				 `z` int(11) NOT NULL,
				 `h` int(11) NOT NULL,
				 `w` int(11) NOT NULL,
				 `createdAt` int(11) NOT NULL,
				 PRIMARY KEY (`id`)
			) ENGINE=InnoDB DEFAULT CHARSET=latin1 ;
		""")

		sql.execute("""
			CREATE TABLE IF NOT EXISTS `${plots}` (
				 `id` int(11) NOT NULL AUTO_INCREMENT,
				 `region` int(11) NOT NULL,
				 `name` varchar(64) NOT NULL,
				 `owner` varchar(16) NOT NULL,
				 `uuid` varchar(36) NOT NULL,
				 `x` int(11) NOT NULL,
				 `z` int(11) NOT NULL,
				 `h` int(11) NOT NULL,
				 `w` int(11) NOT NULL,
				 `createdAt` int(11) NOT NULL,
				 `type` int(11) NOT NULL,
				  PRIMARY KEY (`id`)
			) ENGINE=InnoDB DEFAULT CHARSET=latin1 ;
		""")

		sql.execute("""
			CREATE TABLE IF NOT EXISTS`${access_list}` (
				 `id` int(11) NOT NULL AUTO_INCREMENT,
				 `name` varchar(16) NOT NULL,
				 `uuid` varchar(36) NOT NULL,
				 `mode` enum('MEMBER','ALLOW','DENY') NOT NULL,
				 PRIMARY KEY (`id`)
			) ENGINE=InnoDB DEFAULT CHARSET=latin1
		""")

		sql.close()
	}



	public Region getRegion(int id) {
		Sql sql = getSql()

		def res = sql.firstRow "SELECT * FROM ${regions} WHERE id=${id}"
		def reg = regionFromQuery(res)

		loadPlotsForRegion(sql, reg)
		closeReturn(sql, res)
	}


	public Region getRegionByPlotId(int id){
		Sql sql = getSql()
		def res = sql.firstRow "SELECT region FROM ${plots} WHERE id=${id}"

		getRegion(res.region);
		closeReturn(sql, res)
	}


	public Region getRegionByLocation(int x, int z) {
		Sql sql = getSql()

		def res = sql.firstRow "SELECT * FROM ${regions} WHERE x=${x} AND z=${z}"
		def req = regionFromQuery(res)

		loadPlotsForRegion(sql, req)
		closeReturn(sql, res)
	}

	public void saveRegion(Region region) {
		Sql sql = getSql()

		def reg = [region.name, region.x, region.z, region.h, region.w, region.id]
		sql.execute "UPDATE {$regions} SET name=?, x=?, z=?, h=?, w=? WHERE id=${region.id}", reg

		region.getPlots().values().each {
			def plot = [it.ownerName, it.OwnerUUID, it.plotName, it.x, it.z, it.h, it.w]
			sql.execute "UPDATE ${plots} SET owner=?, uuid=?, name=?, x=?, z=?, h=?, w=? WHERE id={$it.id}", plot
		}
		sql.close()
	}

	public Plot getPlot(int id) {
		Sql sql = getSql()

		def row = sql.firstRow "SELECT * FROM ${plots} WHERE id=${id}"
		def plot = plotFromQuery(row)

		closeReturn(sql, plot)
	}

	public Region createRegion(String world, int x, int y, int h, int w) {
		Sql sql = getSql()

		def res = sql.firstRow ("""INSERT INTO ${regions} (world, x, z, h, w) VALUES(?,?,?,?,?); 
			SELECT * FROM ${regions} WHERE id=LAST_INSERT_ID();""", [world, x, y, h, w])

		def reg = regionFromQuery(res)
		closeReturn(sql, reg)
	}

	public Plot createPlot(Region region, int x, int z, int h, int w, PlotType type) {
		Sql sql = getSql()
		
		def res = sql.firstRow( """INSERT INTO ${plots} (world, region, x, z, h, w, type) VALUES(?,?,?,?,?,?,?);
			SELECT * FROM ${plots} WHERE id=LAST_INSERT_ID()""", [region.world, region.id, x, z, h, w, type.name])

		def plot = plotFromQuery(res)
		closeReturn(sql, plot)
	}

	private Region regionFromQuery(row){
		new Region(id: row.id, name: row.name, world: row.world, x: row.x, z: row.z,
				h: row.h, w: row.w, createdAt: row.createdAt)
	}

	private Plot plotFromQuery(row){
		new Plot(id: row.id, region: reg, plotName: row.name, ownerName: row.owner,
				ownerUUID: row.uuid, x: row.x, z: row.z, h: row.h, w: row.w,
				createdAt: row.createdAt)
	}


	private Region loadPlotsForRegion(Sql sql, Region reg){
		sql.eachRow("SELECT * FROM ${world}_plots WHERE region=${id}") { row ->
			def plot = plotFromQuery(row)
			reg.plots.put(plot.id, plot)
		}
		return reg
	}


	private Sql getSql(){
		new Sql(ds)
	}

	private closeReturn(sql, ret){
		sql.close()
		ret
	}

}
