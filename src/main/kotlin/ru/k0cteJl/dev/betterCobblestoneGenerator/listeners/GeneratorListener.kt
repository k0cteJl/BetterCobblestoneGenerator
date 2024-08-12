package ru.k0cteJl.dev.betterCobblestoneGenerator.listeners

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockFormEvent
import ru.k0cteJl.dev.betterCobblestoneGenerator.BetterCobblestoneGenerator
import ru.k0cteJl.dev.betterCobblestoneGenerator.utils.GeneratorBlockSpawnEvent
import ru.k0cteJl.dev.betterCobblestoneGenerator.utils.GeneratorType
import java.util.stream.Collectors
import kotlin.random.Random

class GeneratorListener: Listener {

    @EventHandler
    fun blockGenerated(e: BlockFormEvent){
        if(e.newState.type == Material.STONE || e.newState.type == Material.COBBLESTONE || e.newState.type == Material.BASALT){
            val generatorType: GeneratorType
            var generateChange = 0.0

            lateinit var blockMaterial: String
            val cfg: FileConfiguration = BetterCobblestoneGenerator.instance!!.mainConfig!!

            val currentPreset = cfg.getString("current-preset", "default")

            if(e.newState.type == Material.BASALT){
                generatorType = GeneratorType.BASALT
                blockMaterial = cfg.getString("presets.$currentPreset.basalt-generator.default", "BASALT")!!

                val blocks = cfg.getConfigurationSection("presets.$currentPreset.basalt-generator")!!.getValues(false).keys.stream().collect(Collectors.toList())
                for(blockId in blocks){
                    val rndDouble: Double = Random.nextDouble(0.0, 100.0)
                    val chance: Double = cfg.getDouble("presets.$currentPreset.basalt-generator.$blockId.chance", 0.0)
                    if(rndDouble <= chance) blockMaterial = blockId else break
                }
            }
            else{
                generatorType = GeneratorType.COBBLESTONE
                val layer: String = if(cfg.getInt("lower-layer-height", -60 ) >= e.block.location.y && cfg.getBoolean("lower-layer-enabled")) "lower" else "upper"
                blockMaterial = cfg.getString("presets.$currentPreset.cobblestone-generator.default-$layer", "COBBLESTONE")!!

                val blocks = cfg.getConfigurationSection("presets.$currentPreset.cobblestone-generator.$layer-layer")!!.getValues(false).keys.stream().collect(Collectors.toList())

                for(blockId in blocks){
                    val rndDouble: Double = Random.nextDouble(0.0, 100.0)
                    val chance: Double = cfg.getDouble("presets.$currentPreset.cobblestone-generator.$layer-layer.$blockId.chance", 0.0)

                    if(rndDouble <= chance) blockMaterial = blockId else break
                }
            }

            e.newState.type = Material.valueOf(blockMaterial)

            Bukkit.getPluginManager().callEvent(GeneratorBlockSpawnEvent(e.block.y, Material.valueOf(blockMaterial), generatorType, generateChange))
        }
    }

}