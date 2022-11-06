package net.techtastic.vi.block

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.DirectionalBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.material.Material
import net.techtastic.vi.ModBlocks.ISLANDITE_CLUSTER
import net.techtastic.vi.ModProperties.GROWTH
import java.util.*

object IslanditeBud: DirectionalBlock(Properties.of(Material.GLASS).randomTicks().noOcclusion()) {
    init {
        registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.UP).setValue(GROWTH, 0))
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(FACING).add(GROWTH)
    }

    override fun getStateForPlacement(ctx: BlockPlaceContext): BlockState? {
        var growth = 0
        val nbt = ctx.itemInHand.orCreateTag
        if (nbt.contains("VI#growth")) {
            growth = nbt.getInt("VI#growth")
        }

        return defaultBlockState().setValue(FACING, ctx.clickedFace).setValue(GROWTH, growth)
    }

    override fun randomTick(state: BlockState?, level: ServerLevel?, pos: BlockPos?, random: Random?) {
        if (random!!.nextInt(5) == 0) {
            if (state!!.getValue(GROWTH) == 2) {
                val defaultState = ISLANDITE_CLUSTER!!.get().defaultBlockState()
                level!!.setBlock(
                    pos,
                    defaultState.setValue(BlockStateProperties.FACING, state.getValue(BlockStateProperties.FACING)),
                    0
                )
            } else {
                level!!.setBlock(pos, state.setValue(GROWTH, state.getValue(GROWTH) + 1), 0)
            }
        }
    }
}