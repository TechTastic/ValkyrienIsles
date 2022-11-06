package net.techtastic.vi

import net.minecraft.world.level.block.state.properties.IntegerProperty

object ModProperties {
    val HEAT = IntegerProperty.create("heat", 0, 4)
    val GROWTH = IntegerProperty.create("growth", 0, 2)
}
