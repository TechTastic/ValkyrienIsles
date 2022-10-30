package net.techtastic.vi.ship

import org.joml.Vector3d
import org.valkyrienskies.core.api.ForcesApplier
import org.valkyrienskies.core.game.ships.PhysShip
import org.valkyrienskies.core.pipelines.SegmentUtils
import net.techtastic.vi.ModConfig
import org.valkyrienskies.physics_api.SegmentDisplacement

fun stabilize(
    ship: PhysShip,
    omega: Vector3d,
    vel: Vector3d,
    segment: SegmentDisplacement,
    forces: ForcesApplier,
    linear: Boolean,
    yaw: Boolean
) {
    val shipUp = Vector3d(0.0, 1.0, 0.0)
    val worldUp = Vector3d(0.0, 1.0, 0.0)
    SegmentUtils.transformDirectionWithoutScale(ship.poseVel, segment, shipUp, shipUp)

    val angleBetween = shipUp.angle(worldUp)
    val idealAngularAcceleration = Vector3d()
    if (angleBetween > .01) {
        val stabilizationRotationAxisNormalized = shipUp.cross(worldUp, Vector3d()).normalize()
        idealAngularAcceleration.add(
            stabilizationRotationAxisNormalized.mul(
                angleBetween,
                stabilizationRotationAxisNormalized
            )
        )
    }

    // Only subtract the x/z components of omega.
    // We still want to allow rotation along the Y-axis (yaw).
    // Except if yaw is true, then we stabilize
    idealAngularAcceleration.sub(
        omega.x(),
        if (!yaw) 0.0 else omega.y(),
        omega.z()
    )

    val stabilizationTorque = SegmentUtils.transformDirectionWithScale(
        ship.poseVel,
        segment,
        ship.inertia.momentOfInertiaTensor.transform(
            SegmentUtils.invTransformDirectionWithScale(
                ship.poseVel,
                segment,
                idealAngularAcceleration,
                idealAngularAcceleration
            )
        ),
        idealAngularAcceleration
    )

    stabilizationTorque.mul(ModConfig.SERVER.stabilizationTorqueConstant)
    forces.applyInvariantTorque(stabilizationTorque)

    if (linear) {
        val idealVelocity = Vector3d(vel).negate()
        idealVelocity.y = 0.0

        if (idealVelocity.lengthSquared() > (ModConfig.SERVER.linearStabilizeMaxAntiVelocity * ModConfig.SERVER.linearStabilizeMaxAntiVelocity))
            idealVelocity.normalize(ModConfig.SERVER.linearStabilizeMaxAntiVelocity)

        idealVelocity.mul(ship.inertia.shipMass * (10 - ModConfig.SERVER.antiVelocityMassRelevance))
        forces.applyInvariantForce(idealVelocity)
    }
}
