package com.naturalspawn.bluenatural;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import net.md_5.bungee.api.ChatColor;

public class Config {
	private static FileConfiguration cl = null;
	private static FileConfiguration cp = null;
	
	private static File customCl = null;
	private static File customCp = null;
	
	public static void reloadConfig(){
		if(customCl == null && customCp == null){
			customCl = new File(Main.plugin.getDataFolder(), "location.yml");
			customCp = new File(Main.plugin.getDataFolder(), "punishment.yml");
		}
		cl = YamlConfiguration.loadConfiguration(customCl);
		cp = YamlConfiguration.loadConfiguration(customCp);
			try{
				Reader defaultLocation = new InputStreamReader(Main.plugin.getResource("location.yml"), "UTF8");
				Reader defaultPunishment = new InputStreamReader(Main.plugin.getResource("punishment.yml"), "UTF8");
				if(defaultLocation != null && defaultPunishment != null){
					YamlConfiguration defLocation = YamlConfiguration.loadConfiguration(defaultLocation);
					YamlConfiguration defPunishment = YamlConfiguration.loadConfiguration(defaultPunishment);
					cl.setDefaults(defLocation);
					cp.setDefaults(defPunishment);
				}
			}catch (UnsupportedEncodingException e){
				e.printStackTrace();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		public static FileConfiguration getLocation(){
			if(cl == null){
				reloadConfig();
			}
			return cl;
			
		}
		public static FileConfiguration getPunishment(){
			if(cp == null){
				reloadConfig();
			}
			return cp;
		}
		public static void saveLocation(){
			if(cl == null && customCl == null){
				return;
			}
			try{
				getLocation().save(customCl);
				
			}catch(IOException e){
				System.out.println(ChatColor.RED + "Could not load location.yml because of errors");
			}
		}
		public static void savePunishment(){
			if(cp == null && customCp == null){
				return;
			}
			try{
				getPunishment().save(customCp);
			}catch(IOException e){
				System.out.println(ChatColor.RED + "Could not load punishment.yml because of errors");
				
			}
		}
		public static void saveDefaultLocation(){
			if(customCl == null){
				customCl = new File(Main.plugin.getDataFolder(), "location.yml");
				
			}
			if(!customCl.exists()){
				Main.plugin.saveResource("location.yml", false);
				
			}
		}
		public static void saveDefaultPunishment(){
			if(customCp == null){
				customCp = new File(Main.plugin.getDataFolder(), "punishment.yml");
				
			}
			if(!customCp.exists()){
				Main.plugin.saveResource("punishment.yml", false);
			}
		}
		
	}


