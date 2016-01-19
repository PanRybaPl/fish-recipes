package pl.panryba.mc.recipes;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

import java.util.logging.Level;

/**
 * @author PanRyba.pl
 */
public class PlayerListener implements Listener {

    private final PluginApi api;

    public PlayerListener(PluginApi api) {
        this.api = api;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onBlockPlace(BlockPlaceEvent event)
    {
        api.blockPlaced(event.getItemInHand(), event.getBlockPlaced());
    }


    @EventHandler
    private void onCraft(PrepareItemCraftEvent event) {
        ItemStack stack = event.getRecipe().getResult();

        if(stack.getType() != Material.COBBLESTONE) {
            return;
        }

        for(ItemStack contentItem : event.getInventory().getMatrix()) {
            if(contentItem != null && (contentItem.getType() != Material.COBBLESTONE || contentItem.getAmount() != 64)) {
                event.getInventory().setResult(new ItemStack(Material.AIR));
                return;
            }
        }

        event.getInventory().setMatrix(new ItemStack[10]);

        Player player = (Player)event.getView().getPlayer();
        player.updateInventory();

        ItemStack item = api.getRandomItem();
        Bukkit.getLogger().log(Level.INFO, "COBBLE: {0}", item);
        if(item != null) {
            event.getInventory().setResult(item);
        }
    }
}
