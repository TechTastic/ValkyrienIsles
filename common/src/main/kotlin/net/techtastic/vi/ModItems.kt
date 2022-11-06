package net.techtastic.vi

import me.shedaniel.architectury.registry.CreativeTabs
import me.shedaniel.architectury.registry.DeferredRegister
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack

@Suppress("unused")
object ModItems {
    private val ITEMS = DeferredRegister.create(ValkyrienIslesMod.MOD_ID, Registry.ITEM_REGISTRY)
    val TAB: CreativeModeTab = CreativeTabs.create(
        ResourceLocation(
            ValkyrienIslesMod.MOD_ID,
            "vi_tab"
        )
    ) { ItemStack(ModBlocks.ISLANDITE_CLUSTER.get()) }

    fun register() {
        ModBlocks.registerItems(ITEMS)
        ITEMS.register()
    }

    private infix fun Item.byName(name: String) = ITEMS.register(name) { this }
}
