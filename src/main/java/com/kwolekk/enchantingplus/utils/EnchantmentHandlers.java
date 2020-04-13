package com.kwolekk.enchantingplus.utils;

import com.kwolekk.enchantingplus.EnchantingPlus;
import com.kwolekk.enchantingplus.enchantments.Swiftness;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.IParticleData;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.CooldownTracker;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
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
            LivingEntity target = (LivingEntity)event.getTarget();

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

    @SubscribeEvent
    public static void groundshaker(PlayerInteractEvent.RightClickBlock event) {
        PlayerEntity attacker = event.getPlayer();
        BlockPos pos = event.getPos();
        World world = event.getWorld();
        ItemStack heldItem = attacker.getItemStackFromSlot(EquipmentSlotType.MAINHAND);

        final Enchantment GROUNDSHAKER = RegistryHandler.GROUNDSHAKER.get();
        int level = EnchantmentHelper.getEnchantmentLevel(GROUNDSHAKER, attacker.getItemStackFromSlot(EquipmentSlotType.MAINHAND));

        boolean isFalling = attacker.getMotion().getY() < -0.0784000015258789;
        boolean hasCooldown = attacker.getCooldownTracker().getCooldown(heldItem.getItem(), 0)> 0.0f;

        if(level > 0 && isFalling && !hasCooldown) {
            List<LivingEntity> entities =
                    world.getEntitiesWithinAABB(MobEntity.class,
                            new AxisAlignedBB(pos.add(0, 1, 0)).grow(3.0D, 0.0D, 3.0D));
            entities.forEach(mob -> {
                mob.performHurtAnimation();
                mob.attackEntityFrom(DamageSource.causePlayerDamage(attacker), 1f);
                mob.move(MoverType.PLAYER, new Vec3d(0D, 4D, 0D));
            });
            float cooldown = attacker.getCooldownPeriod();
            attacker.getCooldownTracker().setCooldown(heldItem.getItem(), 3*(int)cooldown);
        }
    }
}
