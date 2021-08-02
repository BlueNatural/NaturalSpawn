package com.naturalspawn.bluenatural;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffectType;
import net.md_5.bungee.api.ChatColor;

public class NaturalEvents implements Listener {
	 public static Main plugin;
	    @SuppressWarnings("static-access")
		public NaturalEvents(Main plugin) {
	        this.plugin = plugin;
	    }
	    @EventHandler
	    public void onJoin(PlayerJoinEvent e){
	    	Player p = e.getPlayer();
	    	Location loc = p.getLocation();
	    	for(Player pl : Bukkit.getServer().getOnlinePlayers()){
	    		Location lo = pl.getLocation();
	    	if(!p.hasPlayedBefore()){
	    		if(plugin.getConfig().getBoolean("active-firework")){
	    			Firework f = (Firework) p.getWorld().spawn(loc, Firework.class);
	    			FireworkMeta fm = f.getFireworkMeta();
	    			fm.addEffect(FireworkEffect.builder()
	    					.flicker(false)
	    					.trail(true)
	    					.with(org.bukkit.FireworkEffect.Type.STAR)
	    					.withColor(Color.GREEN)
	    					.withFade(Color.YELLOW)
	    					.build());
	    			fm.setPower(3);
	    			f.setFireworkMeta(fm);
	    			Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("prefix")) + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("msg.first-join")).replace("{firstjoin}", p.getDisplayName()));
	    			pl.playSound(lo, Sound.valueOf(plugin.getConfig().getString("sound.first-join")), 4F, 1F);
	    			teleportPlayer(p);
	    		}else{
	    			Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("prefix")) + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("msg.first-join")).replace("{firstjoin}", p.getDisplayName()));
	    			pl.playSound(lo, Sound.valueOf(plugin.getConfig().getString("sound.first-join")), 4F, 1F);
	    			teleportPlayer(p);
	    			}
	    		}else{
	    			Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("prefix")) + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("msg.joining")).replace("{name}", p.getDisplayName()));
	    			pl.playSound(lo, Sound.valueOf(plugin.getConfig().getString("sound.joining")), 4F, 1F);
	    			teleportPlayer(p);
	    		}
	    	}
	    

	    }
	    
		@EventHandler
	    public void onQuit(PlayerQuitEvent e){
	    	Player p = e.getPlayer();
	        	Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("prefix"))+
	        			ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("msg.quiting")).replace("{name}", p.getDisplayName()));        
	    }
		@SuppressWarnings("static-access")
		@EventHandler
		public void onMove(PlayerMoveEvent e){
			Player p = e.getPlayer();
			Location loc = p.getLocation();
			double hasmoney = plugin.econ.getBalance(p);
			if(plugin.spawn.contains(p.getUniqueId()){
				if(Config.getPunishment().getBoolean("active-punishment")){
					if(Config.getPunishment().getBoolean("vault.active")){
						if(hasmoney >= Config.getPunishment().getDouble("vault.taking")){
							plugin.econ.withdrawPlayer(p, Config.getPunishment().getDouble("vault.taking"));
							if(plugin.r.transactionSuccess()){
								p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("prefix"))+
										ChatColor.translateAlternateColorCodes('&', Config.getPunishment().getString("vault.send-message")).replace("{money}", String.valueOf(Config.getPunishment().getDouble("vault.taking"))));
								plugin.spawn.remove(p.getUniqueId());
								plugin.getServer().getScheduler().cancelAllTasks();
								p.removePotionEffect(PotionEffectType.BLINDNESS);
								p.removePotionEffect(PotionEffectType.INVISIBILITY);
							}else{
								p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("prefix"))+
										ChatColor.translateAlternateColorCodes('&', "&4Error of loading taking money,please report to Admin or Owner to repair it"));
								plugin.spawn.remove(p.getUniqueId());
								plugin.getServer().getScheduler().cancelAllTasks();
								p.removePotionEffect(PotionEffectType.BLINDNESS);
								p.removePotionEffect(PotionEffectType.INVISIBILITY);
							}
						}else if(hasmoney <= Config.getPunishment().getDouble("vault.taking")){
							plugin.econ.withdrawPlayer(p, Config.getPunishment().getDouble("vault.taking"));
							if(plugin.r.transactionSuccess()){
								p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("prefix"))+
										ChatColor.translateAlternateColorCodes('&', Config.getPunishment().getString("vault.send-message")).replace("{money}", String.valueOf(Config.getPunishment().getDouble("vault.taking"))));
								plugin.spawn.remove(p.getUniqueId());
								plugin.getServer().getScheduler().cancelAllTasks();
								p.removePotionEffect(PotionEffectType.BLINDNESS);
								p.removePotionEffect(PotionEffectType.INVISIBILITY);
							}else{
								p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("prefix"))+
										ChatColor.translateAlternateColorCodes('&', "&4Error of loading taking money,please report to Admin or Owner to repair it"));
								plugin.spawn.remove(p.getUniqueId());	
								plugin.getServer().getScheduler().cancelAllTasks();
								p.removePotionEffect(PotionEffectType.BLINDNESS);
								p.removePotionEffect(PotionEffectType.INVISIBILITY);
							}
							
						}
						
						
					}else{
						plugin.getServer().dispatchCommand(p, Config.getPunishment().getString("command.command").replace("{player}", p.getName()));
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("prefix"))+
								ChatColor.translateAlternateColorCodes('&', Config.getPunishment().getString("command.punishment-msg")));
						plugin.spawn.remove(p.getUniqueId());
				}
				}else{
					return;
				}
			}
			if(loc.getDirection().equals(Material.AIR) || loc.getDirection().equals(loc.getBlock().getType())){
				return;
				
			}
		}
		
	        		
	  
		
		@EventHandler
		public void onChat(AsyncPlayerChatEvent e){
			Player p = e.getPlayer();
			if(plugin.spawn.contains(p.getUniqueId()){
				try{
				e.setCancelled(true);
				e.getRecipients().remove(p);
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("prefix"))+
						ChatColor.translateAlternateColorCodes('&', "&cYou can not talk when have time to teleport"));
				}catch(Exception e2){
					e2.printStackTrace();
			}
			}else{
				e.setCancelled(false);
				e.getRecipients().add(p);
			}
		}
		@EventHandler
		public void onChangeLevel(FoodLevelChangeEvent e){
			Player p = (Player) e.getEntity();
			if(plugin.spawn.contains(p.getUniqueId()){
				try{
					e.setCancelled(true);	
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("prefix"))+
							ChatColor.translateAlternateColorCodes('&', "&e&lYou can not change food level because you have been in time of teleport"));
				}catch(Exception e2){
					e2.printStackTrace();
				
			}
			}else{
				e.setCancelled(false);
			}
		}
		@EventHandler
		public void onRespawn(PlayerRespawnEvent e){
			Player p = e.getPlayer();
			teleportPlayer(p);
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
			Boolean hasWorld = Config.getLocation().isSet("world");
			
			Boolean hasYaw = Config.getLocation().isSet("Yaw");
	        Boolean hasPitch = Config.getLocation().isSet("Pitch");
	        
	        if (hasX && hasY && hasZ && hasYaw && hasPitch && hasWorld) {
	            return true;
	        }
			return false;
		
			
		}
				
			}

