package com.kwolekk.enchantingplus;

import com.kwolekk.enchantingplus.utils.EnchantmentHandlers;
import com.kwolekk.enchantingplus.utils.EventsHandler;
import com.kwolekk.enchantingplus.utils.RegistryHandler;
import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;

@Mod("enchanting_plus")
public class EnchantingPlus
{
    private static final Logger LOGGER = LogManager.getLogger();

    public static final String MOD_ID = "enchanting_plus";

    public EnchantingPlus() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        RegistryHandler.init();

        MinecraftForge.EVENT_BUS.register(new EventsHandler());
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public static void registerEnchants(final RegistryEvent.Register<Enchantment> event) {
        final IForgeRegistry<Enchantment> registry = event.getRegistry();

    }

    private void setup(final FMLCommonSetupEvent event) {
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
    }


}
