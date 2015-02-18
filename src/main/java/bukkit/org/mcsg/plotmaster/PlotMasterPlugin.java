package bukkit.org.mcsg.plotmaster;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcsg.plotmaster.PlotMaster;
import org.mcsg.plotmaster.bridge.PMCommandSender;
import org.mcsg.plotmaster.command.CommandHandler;

import bukkit.org.mcsg.plotmaster.bridge.BukkitCommand;
import bukkit.org.mcsg.plotmaster.bridge.BukkitConsole;
import bukkit.org.mcsg.plotmaster.bridge.BukkitPlayer;
import bukkit.org.mcsg.plotmaster.listeners.SelectionListener;

public class PlotMasterPlugin extends JavaPlugin{

	private static PlotMasterPlugin plugin;
	
	public void onEnable(){
		PlotMaster plotMaster = null;
		try{
			plotMaster = new PlotMaster();
		} catch (Exception e){
			e.printStackTrace();
			
			sendConsoleMessage(ChatColor.RED+"**********************************************************************************");
			sendConsoleMessage("");
			sendConsoleMessage(ChatColor.GOLD+"                GroovyRuntimeSupport is required to run this plugin!");
			sendConsoleMessage(ChatColor.GOLD+"  http://ci.mc-sg.org/job/GroovyRuntime/lastStableBuild/org.mcsg.groovy$GroovyRuntime/");
			sendConsoleMessage("");
			sendConsoleMessage(ChatColor.RED+"***********************************************************************************");
			
			return;
		}
		
		Bukkit.getPluginManager().registerEvents(new SelectionListener(), this);
		
		plotMaster.onEnable();
	}
	
	
	public void onDisable(){
		
	}
	
	public static PlotMasterPlugin getPlugin(){
		return plugin;
	}
	
	private void sendConsoleMessage(String message){
		Bukkit.getConsoleSender().sendMessage(message);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
			BukkitCommand command = new BukkitCommand(cmd);
			PMCommandSender send = null;
			
			if(sender instanceof Player){
				send = new BukkitPlayer((Player) sender);
			} else if (sender instanceof ConsoleCommandSender){
				send = new BukkitConsole(sender);
			} else {
				throw new RuntimeException("Invalid sender");
			}
			
			return CommandHandler.sendCommand(send, command, args);
			
			
	}
	

	
	
}
