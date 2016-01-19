package pl.panryba.mc.recipes;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

/**
 * @author PanRyba.pl
 */
public class StoneGenerator {
    private Set<GenLocation> locations;

    public StoneGenerator() {
        this.locations = new HashSet<>();
    }

    public void AddLocation(Location location) {
        GenLocation info = new GenLocation(location.clone(), new Date().getTime() + 1000 * 60 * 15);
        this.locations.remove(info);
        this.locations.add(info);

        Bukkit.getLogger().info("STONESPONGE [" + this.locations.size() + "] - " + location);
    }

    public void Generate() {
        List<GenLocation> toRemove = new ArrayList<>();
        long now = new Date().getTime();

        for(GenLocation info : this.locations) {
            try {
                if (info.getValidity() < now) {
                    toRemove.add(info);
                    continue;
                }

                Location sourceLoc = info.getLocation();

                if (sourceLoc.getBlock().getType() != Material.SPONGE) {
                    toRemove.add(info);
                    continue;
                }

                Location targetLoc = sourceLoc.clone().add(0, 1, 0);

                Block targetBlock = targetLoc.getBlock();
                if (targetBlock.getType() != Material.AIR) {
                    continue;
                }

                targetBlock.setType(Material.STONE);
            }
            catch(Exception ex) {
            }
        }

        locations.removeAll(toRemove);
    }

    public void BlockPlaced(ItemStack item, Block block) {
        if(block.getType() != Material.SPONGE) {
            return;
        }

        ItemMeta meta = item.getItemMeta();
        if(meta == null) {
            return;
        }

        if(!"Kamienna Gabka".equals(meta.getDisplayName())) {
            return;
        }

        this.AddLocation(block.getLocation());
    }

}
