package com.kwolekk.enchantingplus.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;

public class Groundshaker extends Enchantment {
    final int MAX_LEVEL = 1;
    public Groundshaker() {
        super(Enchantment.Rarity.RARE, EnchantmentType.ALL, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND});
    }

    public int getMinEnchantability(int enchLvl) {
        return 22;
    }

    public int getMaxEnchantability(int enchLvl) {
        return this.getMinEnchantability(enchLvl) + 8;
    }

    public int getMaxLevel() {
        return MAX_LEVEL;
    }

    @Override
    public boolean canApply(ItemStack stack) {
        return stack.getItem() instanceof AxeItem;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return stack.getItem() instanceof AxeItem;
    }
}
