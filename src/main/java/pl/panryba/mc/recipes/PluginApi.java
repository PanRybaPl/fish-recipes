package pl.panryba.mc.recipes;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.Random;
import java.util.logging.Level;

import static java.util.Arrays.fill;

/**
 * @author PanRyba.pl
 */
public class PluginApi {
    private Random random;
    private Material[] materials;

    private StoneGenerator stoneGenerator;

    public PluginApi(FileConfiguration config) {
        setupCobble(config);
        setupStoneGenerator(config);
    }

    private void setupStoneGenerator(FileConfiguration config) {
        this.stoneGenerator = new StoneGenerator();
    }

    private void setupCobble(FileConfiguration config) {
        this.random = new Random();
        this.materials = new Material[10000];
        int current = 0;

        ConfigurationSection chancesSection = config.getConfigurationSection("cobble.chances");

        for(String key : chancesSection.getKeys(false)) {
            Material material = Material.getMaterial(key);
            int chance = chancesSection.getInt(key);

            Bukkit.getLogger().log(Level.INFO, "Cobble chance - {0}: {1}", new Object[]{material, chance});

            fill(materials, current, current + chance, material);
            current += chance;
        }
    }

    public ItemStack getRandomItem() {
        int roll = this.random.nextInt(materials.length);
        Material m = this.materials[roll];
        if(m == null) {
            return null;
        }

        return new ItemStack(m, 1);
    }

    public void blockPlaced(ItemStack itemInHand, Block blockPlaced) {
        this.stoneGenerator.BlockPlaced(itemInHand, blockPlaced);
    }

    public void Generate() {
        this.stoneGenerator.Generate();
    }
}
