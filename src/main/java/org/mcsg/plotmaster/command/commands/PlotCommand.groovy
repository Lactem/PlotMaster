package org.mcsg.plotmaster.command.commands;

import org.mcsg.plotmaster.PlotMaster;
import org.mcsg.plotmaster.bridge.PMCommandSender;
import org.mcsg.plotmaster.command.ConsoleCommand;
import org.mcsg.plotmaster.command.commands.sub.BorderSubCommand
import org.mcsg.plotmaster.command.commands.sub.ClaimSubCommand
import org.mcsg.plotmaster.command.commands.sub.ClearSubCommand
import org.mcsg.plotmaster.command.commands.sub.InfoSubCommand
import org.mcsg.plotmaster.command.commands.sub.ResetSubCommand
import org.mcsg.plotmaster.command.commands.sub.SchematicSubCommand

public class PlotCommand extends ConsoleCommand{

	@Override
	public boolean onCommand(PMCommandSender player, List<String> args) {
		player.sendMessage("&aPlot Master Suite, Version ${PlotMaster.getVersion()}. By Double0negative")
		return true;
	}

	@Override
	public void registerCommands() {
		registerCommand("border", new BorderSubCommand())
		registerCommand("claim", new ClaimSubCommand())
		registerCommand("clear", new ClearSubCommand())
		registerCommand("reset", new ResetSubCommand())
		registerCommand("schematic", new SchematicSubCommand())
		registerCommand("info", new InfoSubCommand())
	}

	
	
	
}
