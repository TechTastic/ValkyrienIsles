package net.techtastic.vi.fabric;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.techtastic.vi.ValkyrienIslesMod;
import org.valkyrienskies.core.config.VSConfigClass;
import net.techtastic.vi.ModConfig;
import org.valkyrienskies.mod.compat.clothconfig.VSClothConfig;

public class ValkyrienIslesModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        try {
            Class.forName("org.valkyrienskies.mod.fabric.common.ValkyrienSkiesModFabric");
        } catch (final ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        ValkyrienIslesMod.init();
    }

    @Environment(EnvType.CLIENT)
    public static class Client implements ClientModInitializer {

        @Override
        public void onInitializeClient() {
            ValkyrienIslesMod.initClient();
        }
    }

    public static class ModMenu implements ModMenuApi {
        @Override
        public ConfigScreenFactory<?> getModConfigScreenFactory() {
            return (parent) -> VSClothConfig.createConfigScreenFor(
                    parent,
                    VSConfigClass.Companion.getRegisteredConfig(ModConfig.class)
            );
        }
    }
}
