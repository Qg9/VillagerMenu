package fr.qg.villagermenu.commands

import fr.qg.villagermenu.VillagerMenuPlugin
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class OpenCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {


        if (args.size == 1 && args[0] == "reload") {
            VillagerMenuPlugin.instance.reloadConfig()
            VillagerMenuPlugin.instance.merchants.clear()
            VillagerMenuPlugin.instance.load()
            sender.sendMessage(Component.text("§aReloaded"))
            return true
        }

        if (args.size != 2) sender.sendMessage(Component.text("§cUsage: /openTrade <player> <merchant>"))

        val target = Bukkit.getPlayer(args[0]) ?: run {
            sender.sendMessage(Component.text("§cPlayer not found"))
            return@onCommand false
        }

        val menu = VillagerMenuPlugin.instance.merchants[args[1]] ?: run {
            sender.sendMessage(Component.text("§cMerchant Menu not found"))
            return@onCommand false
        }

        target.openMerchant(menu, true)
        return true
    }
}