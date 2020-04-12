package com.kwolekk.enchantingplus.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;

public class LifeLeech extends Enchantment {
    final int MAX_LEVEL = 3;
    public LifeLeech() {
        super(Rarity.VERY_RARE, EnchantmentType.WEAPON, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND});
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
