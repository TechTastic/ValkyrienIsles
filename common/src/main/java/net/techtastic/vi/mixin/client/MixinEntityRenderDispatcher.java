package net.techtastic.vi.mixin.client;

import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.techtastic.vi.ModEntities;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderDispatcher.class)
public class MixinEntityRenderDispatcher {

    // TODO keep or remove?
    @Inject(method = "registerRenderers", at = @At("TAIL"))
    void registerRenderers(final ItemRenderer itemRenderer,
                           final ReloadableResourceManager reloadableResourceManager,
                           final CallbackInfo ci) {
        ModEntities.registerRenderers((EntityRenderDispatcher) (Object) this, itemRenderer);
    }

}
