package me.shawshark.LilyRestarter;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin {

	public void onEnable()
	  {
		saveDefaultConfig(); 
	      getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable(){
			@Override
			public void run() {
				getServer().dispatchCommand(getServer().getConsoleSender(), "alert " + (getConfig().getString("alertmessage")));	
			}	
	    }, 100L);
	  }
	  
	  public void onDisable()
	  {
		  saveDefaultConfig(); 
		  getServer().dispatchCommand(getServer().getConsoleSender(), "alert " + (getConfig().getString("shutdownalertmessage")));
		  { sendplayers(); }  
	  }
	  
	  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){ 
	   {
        Player p = (Player)sender;
        if (p.hasPermission("lilyrestarter.restart")) {
            if (cmd.getName().equalsIgnoreCase("restartserver")){
            	Bukkit.broadcastMessage(ChatColor.RED + (getConfig().getString("broadcastmessage")));
	            getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
	            	public void run()
	            	{ main.this.getServer().shutdown(); }
	            }
	            , 100L);
	            	{ sendplayers(); } 
                         
            }
           }     
	    }
	return true;
   }
    	
      public void sendplayers() {
	   for (Player p : getServer().getOnlinePlayers())
        	p.performCommand(getConfig().getString("playercommand"));
      }
}
