package com.kwolekk.enchantingplus.utils;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EventsHandler {

    @SubscribeEvent
    public void playerTickEvent(TickEvent.PlayerTickEvent event) {
//        PlayerEntity player = event.player;
//        player.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.15d);
    }

}
