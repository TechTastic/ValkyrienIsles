package net.techtastic.vi.block

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.projectile.Projectile
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult
import org.valkyrienskies.core.api.getAttachment
import net.techtastic.vi.ModConfig
import net.techtastic.vi.ship.IslandShipControl
import org.valkyrienskies.mod.common.getShipManagingPos
import org.valkyrienskies.mod.common.getShipObjectManagingPos

class BalloonBlock(properties: Properties) : Block(properties) {

    override fun fallOn(level: Level, blockPos: BlockPos, entity: Entity, f: Float) {
        entity.causeFallDamage(f, 0.2f)
    }

    override fun onPlace(state: BlockState, level: Level, pos: BlockPos, oldState: BlockState, isMoving: Boolean) {
        super.onPlace(state, level, pos, oldState, isMoving)

        if (level.isClientSide) return
        level as ServerLevel

        val ship = level.getShipObjectManagingPos(pos) ?: level.getShipManagingPos(pos) ?: return
        IslandShipControl.getOrCreate(ship).balloons += 1
    }

    override fun onRemove(state: BlockState, level: Level, pos: BlockPos, newState: BlockState, isMoving: Boolean) {
        super.onRemove(state, level, pos, newState, isMoving)

        if (level.isClientSide) return
        level as ServerLevel

        level.getShipManagingPos(pos)?.getAttachment<IslandShipControl>()?.let {
            it.balloons -= 1
        }
    }

    override fun onProjectileHit(level: Level, state: BlockState, hit: BlockHitResult, projectile: Projectile) {
        if (level.isClientSide) return

        level.destroyBlock(hit.blockPos, false)
        Direction.values().forEach {
            val neighbor = hit.blockPos.relative(it)
            if (level.getBlockState(neighbor).block == this &&
                level.random.nextFloat() < ModConfig.SERVER.popSideBalloonChance
            ) {
                level.destroyBlock(neighbor, false)
            }
        }
    }
}
