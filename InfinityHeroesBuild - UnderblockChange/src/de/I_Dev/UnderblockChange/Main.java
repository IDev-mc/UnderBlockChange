package de.I_Dev.UnderblockChange;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	@Override
	public void onEnable() {
		super.onEnable();
		Bukkit.getPluginManager().registerEvents(new Listener() {
			@EventHandler
			public void a(PlayerInteractEvent e) {
				if(e.getAction() != Action.RIGHT_CLICK_AIR) return;
				
				ItemStack stack = e.getPlayer().getInventory().getItemInMainHand();
				if(stack != null && stack.getType() != Material.STICK) return;
			
				Block b = e.getPlayer().getTargetBlockExact(20);
				replaceAllBlocksUnder(b.getLocation(), 3);
			}
			
			private void replaceAllBlocksUnder(Location loc, int distance) {
				for(int x = -distance; x <= distance; x++) {
					for(int z = -distance; z <= distance; z++) {
						if(!isInCombi(loc.clone().add(x, 0, z))) continue;
						loc.getWorld().getBlockAt(loc.clone().add(x, -1, z)).setType(Material.DIRT);
					}
				}
			}
		
			private boolean isInCombi(Location loc) {
				if(!loc.getBlock().getType().toString().contains("GRASS")) return false;
				if(loc.getBlock().getType().toString().split("_").length != 2) return false;
				if(!loc.getWorld().getBlockAt(loc.clone().add(0, -1, 0)).toString().contains("GRASS")) return false;
				return true;
			}
			
		}, this);
	}
}
