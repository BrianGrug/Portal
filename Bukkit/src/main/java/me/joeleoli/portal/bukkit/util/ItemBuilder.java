package me.joeleoli.portal.bukkit.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class ItemBuilder {

    private ItemStack itemStack;
    private final ItemMeta itemMeta;
    private Material material = Material.AIR;

    public ItemBuilder(Material material) {
        itemStack = new ItemStack(material);
        itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder amount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }

    public ItemBuilder setDisplayName(String displayName) {
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));
        return this;
    }

    public ItemBuilder color(int hex) {
        if (itemMeta instanceof LeatherArmorMeta) {
            ((LeatherArmorMeta) itemMeta).setColor(Color.fromRGB(hex));
        }
        return this;
    }

    public ItemBuilder addLore(String... strings) {
        List<String> loreArray = new ArrayList<String>();
        for (String loreBit : strings) {
            loreArray.add(ChatColor.WHITE + loreBit);
        }
        itemMeta.setLore(loreArray);
        return this;
    }

    public ItemBuilder addLore(List<String> strings) {
        List<String> loreArray = new ArrayList<String>();
        for (String loreBit : strings) {
            loreArray.add(ChatColor.translateAlternateColorCodes('&', loreBit));
        }
        itemMeta.setLore(Arrays.asList(loreArray.toString()));
        return this;
    }
    public ItemBuilder enchant(Enchantment enchanement, int level, boolean ignoreLevelRestriction) {
        itemMeta.addEnchant(enchanement, level, ignoreLevelRestriction);
        return this;
    }

    public ItemBuilder setDurability(int durability) {
        itemStack.setDurability((short) durability);
        return this;
    }

    public ItemBuilder asMaterial(Material material) {
        this.material = material;
        return this;
    }

    public SkullBuilder toSkullBuilder() {
        return new SkullBuilder(this);
    }

    public class SkullBuilder {

        // Fundamentals
        private ItemBuilder stackBuilder;

        // Meta
        private String owner;

        private SkullBuilder(ItemBuilder stackBuilder) {
            this.stackBuilder = stackBuilder;
        }

        // Meta
        public SkullBuilder withOwner(String ownerName) {
            this.owner = ownerName;
            return this;
        }

        /**
         * Builds a skull from a owner
         *
         * @return ItemStack skull with owner
         */
        public ItemStack buildSkull() {
            // Build the stack first, edit to make sure it's a skull
            ItemStack skull = stackBuilder
                    .asMaterial(Material.SKULL_ITEM)
                    .setDurability(3)
                    .create();

            // Edit skull meta
            SkullMeta meta = (SkullMeta) skull.getItemMeta();
            meta.setOwner(owner);
            skull.setItemMeta(meta);

            // Lastly, return the skull
            return skull;
        }
    }

    public ItemStack create() {
        ItemStack clonedStack = itemStack.clone();
        clonedStack.setItemMeta(itemMeta.clone());
        return clonedStack;
    }
}