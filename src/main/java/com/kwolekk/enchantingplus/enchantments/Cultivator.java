package com.kwolekk.enchantingplus.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.AxeItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemStack;

public class Cultivator extends Enchantment {
    private final int MAX_LEVEL = 3;
    public Cultivator() {
        super(Rarity.UNCOMMON, EnchantmentType.ALL, new EquipmentSlotType[]{EquipmentSlotType.MAINHAND});
    }
    public int getMinEnchantability(int enchLvl) {
        return 5 + (enchLvl - 1)*5;
    }

    public int getMaxEnchantability(int enchLvl) {
        return this.getMinEnchantability(enchLvl) + 15;
    }

    public int getMaxLevel() {
        return MAX_LEVEL;
    }

    @Override
    public boolean canApply(ItemStack stack) {
        return stack.getItem() instanceof HoeItem;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return stack.getItem() instanceof HoeItem;
    }

}
