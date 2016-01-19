package pl.panryba.mc.recipes;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

/**
 * @author PanRyba.pl
 */
public class Plugin extends JavaPlugin {
    private ShapedRecipe recipe;
    private PluginApi api;

    @Override
    public void onEnable() {
        api = new PluginApi(getConfig());

        registerStoneSpongeRecipe();
        registerCobbleRecipe();

        getServer().getPluginManager().registerEvents(new PlayerListener(api), this);

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            api.Generate();
        }, 40, 40);
    }

    private void registerCobbleRecipe() {
        ShapelessRecipe recipe = new ShapelessRecipe(new ItemStack(Material.COBBLESTONE));
        recipe.addIngredient(9, Material.COBBLESTONE);
        getServer().addRecipe(recipe);
    }

    private void registerStoneSpongeRecipe() {
        ItemStack generator = new ItemStack(Material.SPONGE);
        ItemMeta meta = generator.getItemMeta();
        meta.setDisplayName("Kamienna Gabka");
        meta.setLore(Arrays.asList("Gabka generujaca kamien"));
        generator.setItemMeta(meta);

        recipe = new ShapedRecipe(generator)
                .shape(new String[] { "rcr", "rsr", "rpr" })
                .setIngredient('r', Material.REDSTONE)
                .setIngredient('c', Material.WATCH)
                .setIngredient('s', Material.SPONGE)
                .setIngredient('p', Material.PISTON_BASE);


        getServer().addRecipe(recipe);
    }

    @Override
    public void onDisable() {
        getServer().resetRecipes();
    }
}
