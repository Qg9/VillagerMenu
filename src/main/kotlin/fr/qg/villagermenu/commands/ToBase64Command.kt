package fr.qg.villagermenu.commands

import fr.qg.villagermenu.utils.toBase64
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import net.md_5.bungee.api.ChatMessageType
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

import org.bukkit.entity.Player




class ToBase64Command : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        val player = sender as? Player ?: return false;
        val item = player.inventory.itemInMainHand.toBase64()
        val component = Component.text("§a[Copier]")
            .hoverEvent(Component.text("§a$item")).clickEvent(ClickEvent.copyToClipboard(item))
        player.sendMessage("")
        player.sendMessage("§7Inventaire Serializé !")
        player.sendMessage(component)
        player.sendMessage("")
        return true
    }
}