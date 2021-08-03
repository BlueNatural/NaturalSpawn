package com.naturalspawn.bluenatural;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Main extends JavaPlugin{
	public static Plugin plugin;
	public static Main instance;
	public static Server server;
	public static String pluginName;
	public static String pluginVersion;
	public static Economy econ;
	public static EconomyResponse r;
	public List<UUID> spawn = new ArrayList<>();
	String cslprefix = "[NaturalSpawn] ";
	@Override
    public void onLoad()
    {
	    plugin = this;
        server = plugin.getServer();
        NLog.setPluginLogger(plugin.getLogger());
        NLog.setPluginLogger(plugin.getLogger());
        pluginName = plugin.getDescription().getName();
        pluginVersion = plugin.getDescription().getVersion();
        this.saveDefaultConfig();
    }
	public void loadingConfiguration(){
		String prefix = "prefix";
		plugin.getConfig().addDefault(prefix, "&e&l(&aNatural&7Spawn&e&l) ");
		
		String soundwaiting = "sound.waiting";
		plugin.getConfig().addDefault(soundwaiting, "BLOCK_NOTE_PLING");
		String soundteleport = "sound.teleport";
		plugin.getConfig().addDefault(soundteleport, "ENTITY_WITHER_HURT");
		String soundsetspawn = "sound.set-spawn";
		plugin.getConfig().addDefault(soundsetspawn, "ENTITY_PLAYER_LEVELUP");
		String soundjoin = "sound.joining";
		plugin.getConfig().addDefault(soundjoin, "ENTITY_EXPERIENCE_ORB_PICKUP");
		String soundfirstjoin = "sound.first-join";
		plugin.getConfig().addDefault(soundfirstjoin, "ENTITY_PLAYER_LEVELUP");
		
		String titleteleport = "title-teleport";
		plugin.getConfig().addDefault(titleteleport, "&d&lTELEPORTED !");
		
		String msgsetspawn = "msg.set-spawn";
		plugin.getConfig().addDefault(msgsetspawn, "&aSet spawn successfully !");
		String msgteleporting = "msg.teleporting";
		plugin.getConfig().addDefault(msgteleporting, "&dTeleported !");
		String msgwaiting = "msg.waiting";
		plugin.getConfig().addDefault(msgwaiting, "&ePlease wait in {delay} to teleport");
		String msgjoining = "msg.joining";
		plugin.getConfig().addDefault(msgjoining, "&a{name} joined in the Server");
		String msgquiting = "msg.quiting";
		plugin.getConfig().addDefault(msgquiting, "&c{name} quited the Server");
		String msgfirstjoin = "msg.first-join";
		plugin.getConfig().addDefault(msgfirstjoin, "&a{firstjoin} is a new player first join the Server !");
		
		String activefirework = "active-firework";
		plugin.getConfig().addDefault(activefirework, Boolean.valueOf(true));
		
		String delayteleport = "delay-teleport";
		plugin.getConfig().addDefault(delayteleport, Integer.valueOf(3));
		
		String reload = "reload";
		plugin.getConfig().addDefault(reload, "&aReload Successfully !");
		
		String noperm = "no-perm";
		plugin.getConfig().addDefault(noperm, "&cI am sorry because you are not allowed to do this so please contact to Admintrastor to perform command");
		
		getConfig().options().copyDefaults(true);
		saveDefaultConfig();
	}
	PluginDescriptionFile pdf = getDescription();
	ConsoleCommandSender console = getServer().getConsoleSender();
	@Override
	public void onEnable(){
		if(ServerVersion.isMC19() || ServerVersion.isMC18() || ServerVersion.isMC110() || ServerVersion.isMC111() || ServerVersion.isMC112() || ServerVersion.isMC113() || ServerVersion.isMC114() || ServerVersion.isMC115()){
			if(setupEconomy()){
				plugin = this;
				NaturalEvents.plugin = this;
				NaturalCommand.plugin = this;
				SpawnCommand.plugin = this;
			console.sendMessage(this.cslprefix + ChatColor.GREEN + "The plugin will start in a few seconds");
			loadNaturalEventandNaturalCommand();
			Config.getPunishment().options().copyDefaults(true);
			Config.getLocation().options().copyDefaults(true);
			Config.getPunishment().set("active-punishment", Boolean.valueOf(true));
			Config.getPunishment().set("vault.active", Boolean.valueOf(true));
			Config.savePunishment();
			Config.saveLocation();
			loadingConfiguration();
			console.sendMessage(this.cslprefix + ChatColor.BLUE + "The plugin started");
			console.sendMessage(this.cslprefix + ChatColor.RED + pdf.getName() + pdf.getVersion());
			}else{
				plugin = this;
				NaturalEvents.plugin = this;
				NaturalCommand.plugin = this;
				SpawnCommand.plugin = this;
				NLog.info(this.cslprefix + ChatColor.GREEN + "The plugin will start in a few seconds");
				loadNaturalEventandNaturalCommand();
				Config.getPunishment().options().copyDefaults(true);
				Config.getLocation().options().copyDefaults(true);
				Config.getPunishment().set("active-punishment", Boolean.valueOf(true));
				Config.getPunishment().set("vault.active", Boolean.valueOf(false));
				Config.savePunishment();
				Config.saveLocation();
				loadingConfiguration();
				console.sendMessage(this.cslprefix + ChatColor.BLUE + "The plugin started");
				console.sendMessage(this.cslprefix + ChatColor.RED + pdf.getName() + pdf.getVersion());
			}
		}else{
			NLog.warning(ChatColor.RED + "Incorrect version of this plugin,please check your version is failed");
			NLog.warning(ChatColor.RED + "Error of loading plugin,incorrect version,please check your version it is failed ");
			Bukkit.getServer().getPluginManager().disablePlugin(this);
		}
		
	}
	public void loadNaturalEventandNaturalCommand() {
		getServer().getPluginManager().registerEvents(new NaturalEvents(this), this);
		getCommand("naturalspawn").setExecutor(new NaturalCommand(this));
		getCommand("spawn").setExecutor(new SpawnCommand(this));
	}
	public boolean setupEconomy() {
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		econ = (Economy) rsp.getProvider();
		return econ != null;

	}
	

}
