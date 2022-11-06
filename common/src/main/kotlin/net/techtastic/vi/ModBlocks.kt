package net.techtastic.vi

import me.shedaniel.architectury.registry.DeferredRegister
import net.minecraft.core.Registry
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.material.Material
import net.techtastic.vi.block.*

@Suppress("unused")
object ModBlocks {
    private val BLOCKS = DeferredRegister.create(ValkyrienIslesMod.MOD_ID, Registry.BLOCK_REGISTRY)

    val ISLANDITE_CLUSTER = IslanditeCluster byName "islandite_cluster"
    val ISLANDITE_BUD = IslanditeBud byName "islandite_bud"
    val BUDDING_ISLANDITE = BuddingIslandite byName "budding_islandite"
    val ISLANDITE_BLOCK = Block(BlockBehaviour.Properties.of(Material.GLASS)) byName "islandite_block"

    fun registerModBlocks() {
        BLOCKS.register()
        ValkyrienIslesMod.LOGGER.info("Registering all Mod Blocks for " + ValkyrienIslesMod.MOD_ID + "!")
    }

    fun registerItems(items: DeferredRegister<Item>) {
        BLOCKS.iterator().forEach {
            items.register(it.id) { BlockItem(it.get(), Item.Properties().tab(ModItems.TAB)) }
        }
    }

    private infix fun Block.byName(name: String) = BLOCKS.register(name) { this }
}
