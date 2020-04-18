package com.kwolekk.enchantingplus.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;

public class SpringBoots extends Enchantment {
    private final int MAX_LEVEL = 2;
    public SpringBoots() {
        super(Rarity.RARE, EnchantmentType.ARMOR_FEET, new EquipmentSlotType[]{EquipmentSlotType.FEET});
    }

    public int getMinEnchantability(int enchLvl) {
        return 20 + (enchLvl - 1)*10;
    }

    public int getMaxEnchantability(int enchLvl) {
        return this.getMinEnchantability(enchLvl) + 10;
    }

    public int getMaxLevel() {
        return MAX_LEVEL;
    }

}
