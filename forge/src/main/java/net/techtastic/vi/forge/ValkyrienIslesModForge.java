package net.techtastic.vi.forge;

import me.shedaniel.architectury.platform.forge.EventBuses;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.techtastic.vi.ModConfig;
import net.techtastic.vi.ValkyrienIslesMod;
import org.valkyrienskies.core.config.VSConfigClass;
import org.valkyrienskies.mod.compat.clothconfig.VSClothConfig;

@Mod(ValkyrienIslesMod.MOD_ID)
public class ValkyrienIslesModForge {
    boolean happendClientSetup = false;

    public ValkyrienIslesModForge() {
        // Submit our event bus to let architectury register our content on the right time

        EventBuses.registerModEventBus(ValkyrienIslesMod.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);

        ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.CONFIGGUIFACTORY,
                () -> (Minecraft client, Screen parent) ->
                        VSClothConfig.createConfigScreenFor(parent,
                                VSConfigClass.Companion.getRegisteredConfig(ModConfig.class))
        );

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);

        ValkyrienIslesMod.init();
    }

    void clientSetup(final FMLClientSetupEvent event) {
        if (happendClientSetup) return;
        happendClientSetup = true;

        ValkyrienIslesMod.initClient();
    }
}
