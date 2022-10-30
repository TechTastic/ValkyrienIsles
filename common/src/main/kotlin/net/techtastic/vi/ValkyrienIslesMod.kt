package net.techtastic.vi

import me.shedaniel.architectury.event.events.TickEvent
import org.valkyrienskies.core.config.VSConfigClass

object ValkyrienIslesMod {
    const val MOD_ID = "vi"
    private val nextTick1 = mutableListOf<() -> Unit>()
    private val nextTick2 = mutableListOf<() -> Unit>()
    private var isTick1 = false

    @JvmStatic
    fun init() {
        ModBlocks.register()
        ModBlockEntities.register()
        ModItems.register()
        ModEntities.register()
        VSConfigClass.registerConfig("vi", ModConfig::class.java)

        TickEvent.SERVER_POST.register {
            val list = switchTickList()
            list.forEach { it() }
            list.clear()
        }
    }

    private fun switchTickList(): MutableList<() -> Unit> {
        isTick1 = !isTick1

        return if (isTick1) nextTick1 else nextTick2
    }

    fun queueNextTick(task: () -> Unit) {
        if (isTick1) {
            nextTick2.add(task)
        } else {
            nextTick1.add(task)
        }
    }

    @JvmStatic
    fun initClient() {
    }
}
