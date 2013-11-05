package me.shawshark.LilyRestarter;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin implements Listener {

	public void onEnable()
	  {
		saveDefaultConfig(); 
	      getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			@Override
			public void run() {
				getServer().dispatchCommand(getServer().getConsoleSender(), 
						"alert " + (getConfig().getString("alertmessage")));	
			}	
	    }, 200L);
	  }
	  
	  public void onDisable()
	  {
		  saveDefaultConfig(); 
		  getServer().dispatchCommand(getServer().getConsoleSender(), 
				  "alert " + (getConfig().getString("shutdownalertmessage")));
		  sendplayers();
	  }
	  
	  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){ 
	   {
        Player p = (Player)sender;
        if (p.hasPermission("lilyrestarter.restart") || (p.hasPermission("lilyrestarter.admin"))) {
            if (cmd.getName().equalsIgnoreCase("restartserver")) {
            	
            	Bukkit.broadcastMessage(ChatColor.RED + (getConfig().getString("beforerestartmessage")));
            	
            	getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
	            	public void run() {
	            		
	            		Bukkit.broadcastMessage(ChatColor.RED + (getConfig().getString("broadcastmessage")));
	            		shutdown(); // shuts the server down || Sends players to target server.
	            	}
	            }
	            , 60L);         
            }
            
            if (p.hasPermission("lilyrestarter.sendallplayers") || (p.hasPermission("lilyrestarter.admin"))) {
              if (cmd.getName().equalsIgnoreCase("sendallplayers")) {
                	
                sendplayers();
                
                getServer().dispatchCommand(getServer().getConsoleSender(), 
                   "send " + sender.getName() + " "+ (getConfig().getString("servername")));
                
          		getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable(){
          			
          	@Override
            public void run() {

            	Bukkit.broadcastMessage(ChatColor.GRAY + "[" + ChatColor.RED + "LilyRestarter" + ChatColor.GRAY + "]"
            			
                    + ChatColor.GRAY + " All players were successfully sent to the target server!");
                			
          					}	
          				}, 20L);
              		}
            	} 
        	}	     
	   	}
	   return true;     
	  }
    	
      public void sendplayers() {
	   for (Player p : getServer().getOnlinePlayers())
        	p.performCommand(getConfig().getString("playercommand"));
      }
      
      public void shutdown() {
    	  Bukkit.getServer().shutdown(); 
       }
      
      public void debug() { /* Not being used at the moment */
    	  Bukkit.broadcastMessage(ChatColor.RED + "DEBUG MESSAGE!");
    	  Bukkit.broadcastMessage(ChatColor.RED + "DEBUG MESSAGE!");
    	  Bukkit.broadcastMessage(ChatColor.RED + "DEBUG MESSAGE!");
      }  
}
