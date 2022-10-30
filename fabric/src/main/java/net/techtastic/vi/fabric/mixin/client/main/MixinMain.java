package net.techtastic.vi.fabric.mixin.client.main;

import net.minecraft.client.main.Main;
import net.techtastic.vi.fabric.AutoDependenciesFabric;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Main.class)
public class MixinMain {

    @Inject(
            at = @At("HEAD"),
            method = "main"
    )
    private static void beforeMain(final String[] args, final CallbackInfo ci) {
        AutoDependenciesFabric.runUpdater();
    }

}
