package com.kwolekk.enchantingplus.utils;

import com.kwolekk.enchantingplus.EnchantingPlus;
import com.kwolekk.enchantingplus.enchantments.Swiftness;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.IParticleData;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.CooldownTracker;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber(modid = EnchantingPlus.MOD_ID)
public class EnchantmentHandlers {

    @SubscribeEvent
    public static void swiftness(LivingEvent.LivingUpdateEvent event) {
        final Enchantment SWIFTNESS = RegistryHandler.SWIFTNESS.get();   //Get class in method instead of getting it in class, fix later
        LivingEntity living = event.getEntityLiving();
        int level = EnchantmentHelper.getEnchantmentLevel(SWIFTNESS, living.getItemStackFromSlot(EquipmentSlotType.FEET));
        if(level > 0) {
            living.addPotionEffect(new EffectInstance(Effects.SPEED, 30, level-1));
        }
    }

    @SubscribeEvent
    public static void lifeLeech(AttackEntityEvent event) {
        Object attacker = event.getEntity();
        if(attacker instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity)attacker;
            final Enchantment LIFE_LEECH = RegistryHandler.LIFE_LEECH.get();
            int level = EnchantmentHelper.getEnchantmentLevel(LIFE_LEECH, player.getItemStackFromSlot(EquipmentSlotType.MAINHAND));
            boolean hasCooldown = player.getCooledAttackStrength(0) < 1F; //1F = fully charged
            if(!player.getEntityWorld().isRemote && level > 0 && !hasCooldown) {
                Random rand = new Random();
                float fLvl = (float) level;
                float healing = fLvl;
                final int BASE_CHANCE = 20;
                int rnum = rand.nextInt(100);
                if(rnum >= (100 - BASE_CHANCE - 5*(level-1))) {
                    if(rnum >= 90) healing += 2f;
                    player.heal(healing);
                }
            }
        }
    }
}
