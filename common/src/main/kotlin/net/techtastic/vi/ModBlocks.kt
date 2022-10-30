package net.techtastic.vi

import me.shedaniel.architectury.registry.DeferredRegister
import net.minecraft.core.Registry
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.FireBlock
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.material.Material
import net.minecraft.world.level.material.MaterialColor
import net.techtastic.vi.block.AnchorBlock
import net.techtastic.vi.block.BalloonBlock
import net.techtastic.vi.block.FloaterBlock
import org.valkyrienskies.mod.event.RegistryEvents

@Suppress("unused")
object ModBlocks {
    private val BLOCKS = DeferredRegister.create(ValkyrienIslesMod.MOD_ID, Registry.BLOCK_REGISTRY)

    val ANCHOR = AnchorBlock byName "anchor"
    val FLOATER = FloaterBlock byName "floater"

    // region Balloons
    val BALLOON = BalloonBlock(
        BlockBehaviour.Properties.of(Material.WOOL, MaterialColor.WOOL).sound(SoundType.WOOL)
    ) byName "balloon"

    fun register() {
        BLOCKS.register()

        RegistryEvents.onRegistriesComplete {
            makeFlammables()
        }
    }

    // region Flammables
    // TODO make this part of the registration sequence
    fun flammableBlock(block: Block?, flameOdds: Int, burnOdds: Int) {
        val fire = Blocks.FIRE as FireBlock
        fire.setFlammable(block, flameOdds, burnOdds)
    }

    fun makeFlammables() {
        flammableBlock(BALLOON.get(), 30, 60)
        flammableBlock(FLOATER.get(), 5, 20)
    }
    // endregion

    // Blocks should also be registered as items, if you want them to be able to be held
    // aka all blocks
    fun registerItems(items: DeferredRegister<Item>) {
        BLOCKS.iterator().forEach {
            items.register(it.id) { BlockItem(it.get(), Item.Properties().tab(ModItems.TAB)) }
        }
    }

    private infix fun Block.byName(name: String) = BLOCKS.register(name) { this }
}
