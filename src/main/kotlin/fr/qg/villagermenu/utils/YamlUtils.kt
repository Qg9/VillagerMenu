package fr.qg.villagermenu.utils

import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.inventory.ItemStack
import org.bukkit.Material.AIR
import org.bukkit.enchantments.Enchantment

fun ConfigurationSection.getHumanReadableItemStack(path: String): ItemStack {

    val section = getConfigurationSection(path) ?: return ItemStack(AIR)

    if (section.contains("loadtype")) {
        val loadtype = section.getString("loadtype") ?: return ItemStack(AIR)

        when(loadtype.uppercase()) {
            "BASE64" -> {
                val base64 = section.getString("data") ?: return ItemStack(AIR)
                return base64.toItemStackFromBase64()
            }
        }

        if (loadtype == "copy") {
            val copy = section.getString("copy") ?: return ItemStack(AIR)
            return getHumanReadableItemStack(copy)
        }
    }



    val material = Material.matchMaterial(section.getString("material") ?: "APPLE") ?: return ItemStack(AIR)
    val amount = section.getInt("amount", 1)
    val name = section.getString("name", "")!!.replace("&", "§")
    val lore = section.getStringList("lore").map { it.replace("&", "§") }
    val modelData = section.getInt("model-data", 0)
    val enchantments = section.getStringList("enchantments").map { it.split(":") }.associate { Enchantment.getByName(it[0]) to it[1].toInt() }

    val item = ItemStack(material, amount)
    val meta = item.itemMeta

    if (name != "default") meta.displayName(Component.text(name))
    if (lore.isNotEmpty()) meta.lore(lore.map { Component.text(it) })

    meta.setCustomModelData(modelData)

    item.itemMeta = meta

    item.addUnsafeEnchantments(enchantments)

    return item
}
