package fr.qg.villagermenu.utils

import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.util.io.BukkitObjectInputStream
import org.bukkit.util.io.BukkitObjectOutputStream
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi


@OptIn(ExperimentalEncodingApi::class)
fun ItemStack.toBase64(): String {
    val str = ByteArrayOutputStream()
    val data = BukkitObjectOutputStream(str)
    data.writeObject(this)
    data.close()
    return Base64.encode(str.toByteArray())
}

@OptIn(ExperimentalEncodingApi::class)
fun String.toItemStackFromBase64(): ItemStack {
    val stream = ByteArrayInputStream(Base64.decode(this))
    val data = BukkitObjectInputStream(stream)
    val result = data.readObject() as ItemStack
    data.close()
    return result
}