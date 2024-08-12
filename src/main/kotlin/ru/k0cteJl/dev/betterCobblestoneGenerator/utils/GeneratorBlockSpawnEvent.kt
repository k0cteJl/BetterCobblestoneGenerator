package ru.k0cteJl.dev.betterCobblestoneGenerator.utils

import org.bukkit.Material
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class GeneratorBlockSpawnEvent(private val y: Int, private val material: Material, private val generatorType: GeneratorType, private val spawnChance: Double) : Event() {

    private val handlers: HandlerList = HandlerList()

    override fun getHandlers(): HandlerList {
        return handlers
    }

}