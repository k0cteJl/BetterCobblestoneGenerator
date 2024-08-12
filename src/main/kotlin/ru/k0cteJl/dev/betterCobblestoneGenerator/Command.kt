package ru.k0cteJl.dev.betterCobblestoneGenerator

import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.configuration.ConfigurationSection

class Command: CommandExecutor {
    override fun onCommand(p0: CommandSender, p1: Command, p2: String, p3: Array<out String>): Boolean {
        if(p3[0] == "reload"){
            BetterCobblestoneGenerator.instance!!.reloadConfig()
            BetterCobblestoneGenerator.instance!!.mainConfig = BetterCobblestoneGenerator.instance!!.config
            p0.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[BetterCobblestoneGenerator]&f Plugin reloaded"))
            return true
        }
        if (p3[0] == "preset") {
            if(p3[1].isNotEmpty()){
                val preset: ConfigurationSection? = BetterCobblestoneGenerator.instance!!.mainConfig?.getConfigurationSection("presets."+p3[1])
                if (preset != null) {
                    BetterCobblestoneGenerator.instance!!.mainConfig?.set("current-preset", p3[1])
                    BetterCobblestoneGenerator.instance!!.saveConfig()
                    BetterCobblestoneGenerator.instance!!.reloadConfig()
                    BetterCobblestoneGenerator.instance!!.mainConfig = BetterCobblestoneGenerator.instance!!.config
                    p0.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[BetterCobblestoneGenerator]&f Current preset: " + p3[1]))
                }
            }else{
                return false
            }
        }
        return false
    }

}