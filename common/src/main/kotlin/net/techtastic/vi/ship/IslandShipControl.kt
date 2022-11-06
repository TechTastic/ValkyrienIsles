package net.techtastic.vi.ship

import com.fasterxml.jackson.annotation.JsonIgnore
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.phys.Vec3
import org.joml.AxisAngle4d
import org.joml.Math.clamp
import org.joml.Math.cos
import org.joml.Quaterniond
import org.joml.Vector3d
import org.valkyrienskies.core.api.ForcesApplier
import org.valkyrienskies.core.api.ServerShip
import org.valkyrienskies.core.api.ServerShipUser
import org.valkyrienskies.core.api.ShipForcesInducer
import org.valkyrienskies.core.api.Ticked
import org.valkyrienskies.core.api.getAttachment
import org.valkyrienskies.core.api.saveAttachment
import org.valkyrienskies.core.api.shipValue
import org.valkyrienskies.core.game.ships.PhysShip
import org.valkyrienskies.core.pipelines.SegmentUtils
import net.techtastic.vi.ModConfig
import org.joml.primitives.AABBd
import org.valkyrienskies.core.game.ships.ShipObject
import org.valkyrienskies.core.game.ships.ShipObjectServer
import org.valkyrienskies.mod.api.SeatedControllingPlayer
import org.valkyrienskies.mod.common.shipObjectWorld
import org.valkyrienskies.mod.common.util.toJOMLD
import org.valkyrienskies.mod.common.util.toMinecraft
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.min

class IslandShipControl(var elevationTarget: Double) : ShipForcesInducer, ServerShipUser, Ticked {

    @JsonIgnore
    override var ship: ServerShip? = null

    /*var level: Level? = null

    var hasActiveAnchor = false
    var hasActiveShield = false

    override fun applyForces(forcesApplier: ForcesApplier, physShip: PhysShip) {
        val shipBlocks = BlockPos.betweenClosed(
            BlockPos(ship.shipAABB.maxX(), ship.shipAABB.maxY(), ship.shipAABB.maxZ()),
            BlockPos(ship.shipAABB.minX(), ship.shipAABB.minY(), ship.shipAABB.minZ())
        )

        var islanditeCount = 0

        for (pos: BlockPos in shipBlocks) {
            //CHECK FOR ISLANDITE
            if (false /*is Islandite?*/) {
                islanditeCount++
            }
        }

        if (islanditeCount >= 0) {

            //MAKE THE ISLAND PLUMMET

        } else if (!hasActiveAnchor) {

            val nearbyIslands = level?.let { findNearbyIslands(it) }
            if (nearbyIslands != null) {
                for (pos: Vec3 in nearbyIslands.values) {
                    val allShips = level.server.shipObjectWorld.loadedShips.asIterable()
                    for (otherShip: ShipObjectServer in allShips) {
                        if (otherShip.shipAABB.toMinecraft().center.equals(pos)) {
                            ship.shipAABB.intersectsAABB(otherShip.shipAABB as AABBd?)
                        }
                    }
                }

                //MOVE TOWARD OTHER ISLANDS


            }
        }
    }

    override fun tick() {

    }

    fun getAllIslands(level: Level): HashMap<Vec3, ShipObjectServer> {
        val allShips = level.server.shipObjectWorld.loadedShips.asIterable()

        var islands = HashMap<Vec3, ShipObjectServer>()
        for (ship: ShipObjectServer in allShips) {
            val controller: IslandShipControl? = ship.getAttachment<IslandShipControl>()
            controller?.let {
                val shipCenter = ship.shipAABB.toMinecraft().center

                if (!islands.containsKey(shipCenter)) {
                    islands.put(shipCenter, ship)
                }
            }
        }

        return islands
    }

    fun findNearbyIslands(level: Level): HashMap<Int, Vec3> {
        var nearbyIslands = HashMap<Int, Vec3>()

        var allIslands = getAllIslands(level)
        val shipCenter = ship?.shipAABB.toMinecraft().center
        var key = 0
        while (key < 10) {
            for (pos: Vec3 in allIslands.keys) {
                if (!nearbyIslands.containsKey(key)) {
                    nearbyIslands.set(key, pos)
                } else if (!nearbyIslands.containsValue(pos)) {
                    if (pos.closerThan(shipCenter, shipCenter.distanceTo(nearbyIslands.get(key)))) {
                        nearbyIslands.put(key, pos)
                    }
                }
            }

            key++
        }

        return nearbyIslands
    }*/

    companion object {
        fun getOrCreate(ship: ServerShip): IslandShipControl {
            return ship.getAttachment<IslandShipControl>()
                ?: IslandShipControl(ship.shipTransform.shipPositionInWorldCoordinates.y()).also {
                    ship.saveAttachment(
                        it
                    )
                }
        }
    }

    override fun applyForces(forcesApplier: ForcesApplier, physShip: PhysShip) {
        TODO("Not yet implemented")
    }

    override fun tick() {
        TODO("Not yet implemented")
    }
}
