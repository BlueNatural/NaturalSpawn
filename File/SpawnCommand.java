package com.naturalspawn.bluenatural;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.md_5.bungee.api.ChatColor;

public class SpawnCommand implements CommandExecutor {
	public static Main plugin;
    @SuppressWarnings("static-access")
	public SpawnCommand(Main plugin) {
        this.plugin = plugin;
    }

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(!(sender instanceof Player)){
		   sender.sendMessage(ChatColor.RED + "[NaturalSpawn] This command only use in game !");
		   return true;
		}else{
			Player p = (Player) sender;
			Location loc = p.getLocation();
			Integer cooldown = plugin.getConfig().getInt("delay-teleport");
			if(args.length < 1){
				if(p.hasPermission("ns.spawn")){
					p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, cooldown * 20, Integer.MAX_VALUE));
					p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, cooldown * 20, Integer.MAX_VALUE));
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("prefix"))+
							ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("msg.waiting")).replace("{delay}", String.valueOf(cooldown)));
					loc.getWorld().playEffect(loc, Effect.FIREWORKS_SPARK, 10);
					loc.getWorld().playEffect(loc, Effect.FIREWORKS_SPARK, 10);
					loc.getWorld().playEffect(loc, Effect.FIREWORKS_SPARK, 10);
					loc.getWorld().playEffect(loc, Effect.FIREWORKS_SPARK, 10);
					loc.getWorld().playEffect(loc, Effect.FIREWORKS_SPARK, 10);
					loc.getWorld().playEffect(loc, Effect.FIREWORKS_SPARK, 10);
					p.playSound(loc, Sound.valueOf(plugin.getConfig().getString("sound.waiting")), 4.0F, 1.0F);
                    p.setGameMode(GameMode.ADVENTURE);
					plugin.getServer().getScheduler().runTaskLater(plugin, new Runnable(){
						public void run(){
							teleportPlayer(p);
							loc.getWorld().playEffect(loc, Effect.HAPPY_VILLAGER, 10);
							loc.getWorld().playEffect(loc, Effect.HAPPY_VILLAGER, 10);
							loc.getWorld().playEffect(loc, Effect.HAPPY_VILLAGER, 10);
							loc.getWorld().playEffect(loc, Effect.HAPPY_VILLAGER, 10);
							loc.getWorld().playEffect(loc, Effect.HAPPY_VILLAGER, 10);
							loc.getWorld().playEffect(loc, Effect.HAPPY_VILLAGER, 10);
							Titles.sendTitle(p, ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("title-teleport")), "", 20, 40, 20);
							p.playSound(loc, Sound.valueOf(plugin.getConfig().getString("sound.teleport")), 4.0F, 1.0F);
							p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("prefix"))+
									ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("msg.teleporting")));
							p.setGameMode(GameMode.SURVIVAL);
							p.removePotionEffect(PotionEffectType.BLINDNESS);
							p.removePotionEffect(PotionEffectType.INVISIBILITY);
						}
					}, 20L * cooldown);
					return true;
					
				}
			}
		}
		return true;
			}
		
	
	public void teleportPlayer(Player p) {
		 Location loc = p.getLocation();
		 Boolean isSpawnLocation = LocationSpawn();
		 if(!isSpawnLocation){
			 p.teleport(loc);
			 return;
		 }
		 Double x = Config.getLocation().getDouble("X");
	     Double y = Config.getLocation().getDouble("Y");
	     Double z = Config.getLocation().getDouble("Z");
	     
	     Integer yaw = Config.getLocation().getInt("Yaw");
	     Integer pitch = Config.getLocation().getInt("Pitch");
	     
	     World world = Bukkit.getWorld(Config.getLocation().getString("world"));
	   						     
	     Location spawn = new Location(world, x, y, z);
	     
	     Float nYaw = Float.intBitsToFloat(yaw);
	     Float nPitch = Float.intBitsToFloat(pitch);
	     
	     spawn.setYaw(nYaw);
	     spawn.setPitch(nPitch);
	     
	     p.teleport(spawn);
	     						     
	}

	public Boolean LocationSpawn() {
		Boolean hasX = Config.getLocation().isSet("X");
		Boolean hasY = Config.getLocation().isSet("Y");
		Boolean hasZ = Config.getLocation().isSet("Z");
		
		Boolean hasYaw = Config.getLocation().isSet("Yaw");
        Boolean hasPitch = Config.getLocation().isSet("Pitch");
        
        Boolean hasWorld = Config.getLocation().isSet("world");
       if (hasX && hasY && hasZ && hasYaw && hasPitch && hasWorld) {
           return true;
       }
		return false;
	
		
	}

}
