package ru.k0cteJl.dev.betterCobblestoneGenerator

import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.plugin.java.JavaPlugin
import ru.k0cteJl.dev.betterCobblestoneGenerator.listeners.GeneratorListener

class BetterCobblestoneGenerator : JavaPlugin() {

    var mainConfig: FileConfiguration? = null

    override fun onEnable() {

        instance = this

        saveDefaultConfig()
        mainConfig = getConfig()

        server.pluginManager.registerEvents(GeneratorListener(), this)

        getCommand("bettercg")?.setExecutor(Command())

    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

    companion object{
        @JvmStatic
        var instance: BetterCobblestoneGenerator? = null
            private set
    }

    @JvmName("getPluginConfig")
    fun getPluginConfig(): FileConfiguration? {
        return mainConfig
    }

}
