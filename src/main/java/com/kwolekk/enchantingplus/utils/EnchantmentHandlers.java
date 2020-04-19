package com.kwolekk.enchantingplus.utils;

import com.kwolekk.enchantingplus.EnchantingPlus;
import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.GrassBlock;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber(modid = EnchantingPlus.MOD_ID, value = Dist.CLIENT)
public class EnchantmentHandlers {

    @SubscribeEvent
    public static void swiftness(LivingEvent.LivingUpdateEvent event) {
        final Enchantment SWIFTNESS = RegistryHandler.SWIFTNESS.get();   //Get class in method instead of getting it in class, fix later
        LivingEntity living = event.getEntityLiving();
        int level = EnchantmentHelper.getEnchantmentLevel(SWIFTNESS, living.getItemStackFromSlot(EquipmentSlotType.FEET));
        if(level > 0) {
            living.addPotionEffect(
                    new EffectInstance(Effects.SPEED, 30, level-1, false, false));
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

        double attackerVerticalMotion = attacker.getMotion().getY();
        boolean isFalling = attackerVerticalMotion < -0.0784000015258789;
        boolean hasCooldown = attacker.getCooldownTracker().getCooldown(heldItem.getItem(), 0)> 0.0f;

        if(level > 0 && isFalling && !hasCooldown) {
            attacker.swingArm(Hand.MAIN_HAND);
            attacker.playSound(new SoundEvent(new ResourceLocation("minecraft",
                    "entity.player.attack.sweep")), 5, 0.4f);
            AxisAlignedBB hitArea = new AxisAlignedBB(pos.add(0, 1, 0)).grow(3.0D, 0.0D, 3.0D);
            List<LivingEntity> entities =
                    world.getEntitiesWithinAABB(MobEntity.class, hitArea);
            BlockState blockState = world.getBlockState(pos);
            for(int i = 0; i < 35; i++) {
                Random random = new Random();
                double xModifier = random.nextDouble();
                double zModifier = random.nextDouble();
                int directionX = random.nextBoolean() ? 1 : -1;
                int directionZ = random.nextBoolean() ? 1 : -1;
                world.addParticle(
                        new BlockParticleData(ParticleTypes.BLOCK, blockState),
                        pos.getX() + xModifier*3*directionX, pos.getY()+1, pos.getZ() + zModifier*3*directionZ,
                        0, 3+(-attackerVerticalMotion*10), 0);
            }
            entities.forEach(mob -> {
                mob.performHurtAnimation();
                mob.attackEntityFrom(DamageSource.causePlayerDamage(attacker), 1f);
                mob.move(MoverType.PLAYER, new Vec3d(0D, 4D, 0D));
            });
            float cooldown = attacker.getCooldownPeriod();
            attacker.getCooldownTracker().setCooldown(heldItem.getItem(), 3*(int)cooldown);
        }
    }

    @SubscribeEvent
    public static void cultivator(UseHoeEvent event) {
        PlayerEntity player = event.getPlayer();
        World world = event.getEntity().world;

        BlockPos targetPos = event.getContext().getPos();
        Block targetBlock = world.getBlockState(targetPos).getBlock();

        boolean noBlockAbove = world.getBlockState(targetPos.add(0,1,0)).getBlock() instanceof AirBlock;
        final Enchantment CULTIVATOR = RegistryHandler.CULTIVATOR.get();
        int level = EnchantmentHelper.getEnchantmentLevel(CULTIVATOR, player.getItemStackFromSlot(EquipmentSlotType.MAINHAND));

        if(level > 0 && targetBlock instanceof GrassBlock && noBlockAbove) {
            Random random = new Random();
            final int BASE_CHANCE = 20;
            int randInt = random.nextInt(100);
            if(randInt > (100 - BASE_CHANCE - (level - 1)*8)) {
                world.addEntity(new ItemEntity(world, targetPos.getX()+0.5, targetPos.getY()+1, targetPos.getZ()+0.5, new ItemStack(Items.WHEAT_SEEDS, 1)));
            }
        }
    }

    @SubscribeEvent
    public static void springBoots(LivingEvent.LivingUpdateEvent event) {
        final Enchantment SPRING_BOOTS = RegistryHandler.SPRING_BOOTS.get();   //Get class in method instead of getting it in class, fix later
        LivingEntity living = event.getEntityLiving();
        int level = EnchantmentHelper.getEnchantmentLevel(SPRING_BOOTS, living.getItemStackFromSlot(EquipmentSlotType.FEET));
        if(level > 0) {
            living.addPotionEffect(
                    new EffectInstance(Effects.JUMP_BOOST, 40, level-1, false, false));
        }
    }
}
