package net.techtastic.vi.block

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.network.chat.MutableComponent
import net.minecraft.network.chat.TranslatableComponent
import net.minecraft.server.level.ServerLevel
import net.minecraft.tags.BlockTags
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.BlockStateProperties.FACING
import net.minecraft.world.level.material.Material
import net.techtastic.vi.ModBlocks.ISLANDITE_BUD
import net.techtastic.vi.ModBlocks.ISLANDITE_CLUSTER
import net.techtastic.vi.ModProperties.GROWTH
import java.util.*

object BuddingIslandite: Block(Properties.of(Material.GLASS).randomTicks()) {
    override fun isRandomlyTicking(state: BlockState?): Boolean {
        return true
    }

    override fun randomTick(state: BlockState?, level: ServerLevel, pos: BlockPos, random: Random?) {
        placeOrGrowBud(level, pos.above(), Direction.UP, random)
        placeOrGrowBud(level, pos.below(), Direction.DOWN, random)
        placeOrGrowBud(level, pos.north(), Direction.NORTH, random)
        placeOrGrowBud(level, pos.south(), Direction.SOUTH, random)
        placeOrGrowBud(level, pos.east(), Direction.EAST, random)
        placeOrGrowBud(level, pos.west(), Direction.WEST, random)
        super.randomTick(state, level, pos, random)
    }

    private fun placeOrGrowBud(level: ServerLevel, pos: BlockPos, direction: Direction, random: Random?) {
        val test = level.getBlockState(pos)
        if (level.isEmptyBlock(pos)) {
            if (random!!.nextFloat() <= 0.3f) {
                val defaultState = ISLANDITE_BUD!!.get().defaultBlockState()
                level.setBlock(pos, defaultState.setValue(FACING, direction), 0)
            }
        } else if (test.block.equals(ISLANDITE_BUD) && test.getValue(FACING).equals(direction)) {
            if (random!!.nextInt(5) == 0) {
                val growth = test.getValue(GROWTH)
                if (growth > 2) {
                    level.setBlock(
                        pos,
                        ISLANDITE_CLUSTER.get().defaultBlockState().setValue(FACING, test.getValue(FACING)),
                        0
                    )
                } else {
                    level.setBlock(pos, test.setValue(GROWTH, growth + 1), 0)
                }
            }
        }
    }
}