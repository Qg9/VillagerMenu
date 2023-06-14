package fr.qg.villagermenu

import fr.qg.villagermenu.commands.OpenCommand
import fr.qg.villagermenu.commands.ToBase64Command
import fr.qg.villagermenu.utils.getHumanReadableItemStack
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.inventory.Merchant
import org.bukkit.inventory.MerchantRecipe
import org.bukkit.plugin.java.JavaPlugin

class VillagerMenuPlugin : JavaPlugin() {

    companion object  {
        lateinit var instance: VillagerMenuPlugin
    }

    val merchants = mutableMapOf<String, Merchant>()

    override fun onEnable() {
        instance = this
        saveDefaultConfig()
        getCommand("serialize")?.setExecutor(ToBase64Command())
        load()
        getCommand("openTrade")?.setExecutor(OpenCommand())
    }

    fun load() {
        val mainSection = config.getConfigurationSection("merchants") ?: return
        for (merchantKey in mainSection.getKeys(false)) {

            val merchant = Bukkit.createMerchant(Component.text(mainSection.getString("title", "")!!))

            val recipes = mutableListOf<MerchantRecipe>()

            val tradesSection = mainSection.getConfigurationSection(merchantKey)?.getConfigurationSection("trades") ?: continue
            for (tradeKey in tradesSection.getKeys(false)) {
                val tradeSection = tradesSection.getConfigurationSection(tradeKey) ?: continue

                val result = tradeSection.getHumanReadableItemStack("result")
                val ingredients = tradeSection.getConfigurationSection("ingredients")?.getKeys(false)?.map {
                    tradeSection.getHumanReadableItemStack("ingredients.$it")
                } ?: emptyList()

                recipes.add(MerchantRecipe(result, 0, 9999, false,
                    0, 1f, true).apply {
                    ingredients.forEach { addIngredient(it) }
                })
            }

            merchant.recipes = recipes
            merchants[merchantKey] = merchant
        }
    }

}