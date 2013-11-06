package me.shawshark.LilyRestarter;

import lilypad.client.connect.api.Connect;
import lilypad.client.connect.api.request.RequestException;
import lilypad.client.connect.api.request.impl.RedirectRequest;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin implements Listener {

	Connect connect;
	
        public void onEnable()
          {   
        	 this.connect = ((Connect)getServer().getServicesManager().getRegistration(Connect.class).getProvider());
        	  
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
        	redirectevent();      
            saveDefaultConfig(); 
            getServer().dispatchCommand(getServer().getConsoleSender(), 
                                  "alert " + (getConfig().getString("shutdownalertmessage")));
        }
          
        public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) { 
        
         {
        Player p = (Player)sender;
        
        if (p.hasPermission("lilyrestarter.restart") || (p.hasPermission("lilyrestarter.admin"))) {
            if (cmd.getName().equalsIgnoreCase("restartserver")) {
                    
                    Bukkit.broadcastMessage(ChatColor.RED + (getConfig().getString("beforerestartmessage")));
                    
                    getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
                            public void run() {
                                    Bukkit.broadcastMessage(ChatColor.RED + (getConfig().getString("broadcastmessage")));
                                    shutdown();
                            }
                    }
                    , 60L);         
            }
            
            if (p.hasPermission("lilyrestarter.redirectall") || (p.hasPermission("lilyrestarter.admin"))) {
              if (cmd.getName().equalsIgnoreCase("redirectall") || (cmd.getName().equalsIgnoreCase("sendallplayers"))) {
                        
            	  
            	  Bukkit.getServer().broadcastMessage(ChatColor.RED + "Teleporting all players to the " + 
            			  							getConfig().getString("redirectserver" + ChatColor.RED + " server!"));
            	  
            	  getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
                      public void run() {
                      redirectevent();     
                      }
              }
              , 60L);         
            	  
             }
           } 
         }             
        }
       return true;     
    }
        
        /* @EventHandler(priority=EventPriority.HIGHEST)
        public void kickevent(final PlayerKickEvent e){
            Player player = e.getPlayer();
            {
            	e.setCancelled(true);
            	player.sendMessage(ChatColor.RED + "You have been kicked from the server for");
                player.sendMessage(ChatColor.GREEN + e.getReason());            }
            redirectplayer();
            
        } */
        
        public void redirectevent() {
    	  for (Player p : getServer().getOnlinePlayers())
    	  redirect(getConfig().getString("redirectserver"), p); // Only use to send all players
      }
        
        /* public void redirectplayer() {
      	  redirect(getConfig().getString("redirectserver"), p); // Only use to send 1 player
        } */
            
      public void sendplayers() {
           for (Player p : getServer().getOnlinePlayers())
                p.performCommand(getConfig().getString("playercommand"));
      } // Removing this one step at a time.
      
      public void shutdown() {
              Bukkit.getServer().shutdown(); 
       }
      
      public void shutdowndebug() 
      { getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {

           @Override
           public void run() 
           { shutdown(); }}, 20L);
      }
      
      
      public void redirect(String string, Player player) {
          try { connect.request(new RedirectRequest(getConfig().getString("redirectServer"), player.getName()));
          } catch (RequestException e) { e.printStackTrace(); }
      }
      
      public void debug() { /* Not being used at the moment */
              Bukkit.broadcastMessage(ChatColor.RED + "DEBUG MESSAGE!");
              Bukkit.broadcastMessage(ChatColor.RED + "DEBUG MESSAGE!");
              Bukkit.broadcastMessage(ChatColor.RED + "DEBUG MESSAGE!");
      }
}
