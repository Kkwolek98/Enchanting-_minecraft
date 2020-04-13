package com.kwolekk.enchantingplus.utils;

import com.kwolekk.enchantingplus.EnchantingPlus;
import com.kwolekk.enchantingplus.enchantments.Groundshaker;
import com.kwolekk.enchantingplus.enchantments.LifeLeech;
import com.kwolekk.enchantingplus.enchantments.Swiftness;
import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RegistryHandler {
    private static final Logger LOGGER = LogManager.getLogger();

    public static final DeferredRegister<Enchantment> ENCHANTMENTS =
            new DeferredRegister<>(ForgeRegistries.ENCHANTMENTS, EnchantingPlus.MOD_ID);

    public static final RegistryObject<Enchantment> SWIFTNESS
            = ENCHANTMENTS.register("swiftness", () -> new Swiftness());

    public static final RegistryObject<Enchantment> LIFE_LEECH
            = ENCHANTMENTS.register("life_leech", LifeLeech::new);

    public static final RegistryObject<Enchantment> GROUNDSHAKER
            = ENCHANTMENTS.register("groundshaker", Groundshaker::new);

    public static void init() {
        ENCHANTMENTS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
