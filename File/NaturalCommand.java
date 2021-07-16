package com.naturalspawn.bluenatural;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class NaturalCommand implements CommandExecutor {
	public static Main plugin;
    @SuppressWarnings("static-access")
	public NaturalCommand(Main plugin) {
        this.plugin = plugin;
    }

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(!(sender instanceof Player)){
			sender.sendMessage("[NaturalSpawn] Can not perform because of having been in Console Board");
			return true;
		}else{
			Player p = (Player) sender;
			Location loc = p.getLocation();
			if(args.length < 1 || args.length == 1 && args[0].equalsIgnoreCase("help")){
				if(p.hasPermission("ns.help")){
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&m====================================="));
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e/naturalspawn help &7- show all commands of NaturalSpawn"));
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e/naturalspawn setspawn &7- set a location to spawn"));
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e/spawn &7- teleport to location spawn"));
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e/naturalspawn reload - &7 reload plugin"));
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&m====================================="));
					return true;
				}
				else{
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("prefix"))+
							ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("no-perm")));
					return true;
			}
			}else if(args.length == 1 && args[0].equalsIgnoreCase("setspawn")){
				if(p.hasPermission("ns.setspawn")){
					saveLocation(loc);
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("prefix"))+
							ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("msg.set-spawn")));
					p.playSound(loc, Sound.valueOf(plugin.getConfig().getString("sound.set-spawn")), 4.0F, 1.0F);
					return true;
				}else{
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("prefix"))+
							ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("no-perm")));
					return true;
				}
				
			
			}else if(args.length == 1 && args[0].equalsIgnoreCase("reload")){
				if(p.hasPermission("ns.reload")){
					ReloadPlugin();
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("prefix"))+
							ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("reload")));
					return true;
				}else{
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("prefix"))+
							ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("no-perm")));
					return true;
				}
				
			}
		}
		return true;
		
	}

	public void ReloadPlugin() {
		Config.reloadConfig();
		Config.saveLocation();
		Config.savePunishment();
		plugin.reloadConfig();
		plugin.saveConfig();
		
	}

	public void saveLocation(Location location) {
		// TODO Auto-generated method stub
			Config.getLocation().set("X", location.getX());
			Config.getLocation().set("Y", location.getY());
		    Config.getLocation().set("Z", location.getZ());
				
		    Config.getLocation().set("world", location.getWorld().getName());
				
				Config.getLocation().set("Yaw", Float.floatToIntBits(location.getYaw()));
		        Config.getLocation().set("Pitch", Float.floatToIntBits(location.getPitch()));
		        
		        Config.saveLocation();
		
	}

}
