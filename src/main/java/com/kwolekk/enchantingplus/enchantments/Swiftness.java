package com.kwolekk.enchantingplus.enchantments;

import com.kwolekk.enchantingplus.EnchantingPlus;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.ResourceLocation;


public class Swiftness extends Enchantment {
    private final int MAX_LEVEL = 2;
    public Swiftness() {
        super(Rarity.UNCOMMON, EnchantmentType.ARMOR_FEET, new EquipmentSlotType[]{EquipmentSlotType.FEET});
    }

    public int getMinEnchantability(int enchLvl) {
        return 10 + (enchLvl - 1)*10;
    }

    public int getMaxEnchantability(int enchLvl) {
        return this.getMinEnchantability(enchLvl) + 20;
    }

    public int getMaxLevel() {
        return MAX_LEVEL;
    }
}
