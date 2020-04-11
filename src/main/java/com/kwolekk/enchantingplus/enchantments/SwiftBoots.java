package com.kwolekk.enchantingplus.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SwiftBoots extends Enchantment {
    public SwiftBoots(EnchantmentType type, Rarity rarity) {
        super(rarity, type, new EquipmentSlotType[]{EquipmentSlotType.FEET});
    }
}
