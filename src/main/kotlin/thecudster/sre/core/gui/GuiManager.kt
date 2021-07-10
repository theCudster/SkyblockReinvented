/*
 * SkyblockReinvented - Hypixel Skyblock Improvement Modification for Minecraft
 *  Copyright (C) 2021 theCudster
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as published
 *  by the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */
package thecudster.sre.core.gui

import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.client.event.RenderGameOverlayEvent
import thecudster.sre.core.gui.GuiElement
import thecudster.sre.core.gui.GuiManager
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent
import net.minecraftforge.fml.common.gameevent.TickEvent
import net.minecraft.client.gui.ScaledResolution
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.GlStateManager
import thecudster.sre.core.gui.FloatPair
import net.minecraft.util.EnumChatFormatting
import net.minecraftforge.client.GuiIngameForge
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.eventhandler.EventPriority
import thecudster.sre.SkyblockReinvented
import thecudster.sre.core.PersistentSave
import thecudster.sre.events.RenderHUDEvent
import thecudster.sre.util.Utils
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.lang.Exception
import java.util.HashMap

/**
 * Modified from _root_ide_package_.thecudster.sre.SkyblockReinvented under GNU Affero General Public license.
 * https://github.com/_root_ide_package_.thecudster.sre.SkyblockReinvented/_root_ide_package_.thecudster.sre.SkyblockReinventedMod/blob/main/LICENSE
 * @author Sychic
 * @author My-Name-Is-Jeff
 */

class GuiManager : PersistentSave(File(SkyblockReinvented.modDir, "guipositions.json")) {
    private var counter = 0
    fun registerElement(e: GuiElement): Boolean {
        return try {
            counter++
            Companion.elements[counter] = e
            names[e.name] = e
            true
        } catch (err: Exception) {
            err.printStackTrace()
            false
        }
    }

    fun getByID(ID: Int): GuiElement? {
        return Companion.elements[ID]
    }

    fun getByName(name: String?): GuiElement? {
        return names[name]
    }

    fun searchElements(query: String): List<GuiElement> {
        val results: MutableList<GuiElement> = ArrayList()
        for ((key, value) in names) {
            if (key == query) results.add(value)
        }
        return results
    }

    val elements: Map<Int, GuiElement>
        get() = Companion.elements

    @SubscribeEvent
    fun renderPlayerInfo(event: RenderGameOverlayEvent.Post) {
        if (SkyblockReinvented.usingLabymod && Minecraft.getMinecraft().ingameGUI !is GuiIngameForge) return
        if (event.type != RenderGameOverlayEvent.ElementType.HOTBAR) return
        MinecraftForge.EVENT_BUS.post(RenderHUDEvent(event))
    }

    // LabyMod Support
    @SubscribeEvent
    fun renderPlayerInfoLabyMod(event: RenderGameOverlayEvent) {
        if (!SkyblockReinvented.usingLabymod) return
        if (event.type != null) return
        MinecraftForge.EVENT_BUS.post(RenderHUDEvent(event))
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    fun onRenderHUD(event: RenderHUDEvent) {
        for ((_, element) in Companion.elements) {
            try {
                GlStateManager.pushMatrix()
                GlStateManager.translate(element.actualX, element.actualY, 0f)
                GlStateManager.scale(element.scale, element.scale, 0f)
                element.render()
                GlStateManager.popMatrix()
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
        renderTitles(event.event.resolution)
    }

    @SubscribeEvent
    fun onTick(event: ClientTickEvent) {
        if (event.phase != TickEvent.Phase.START) return
        if (titleDisplayTicks > 0) {
            titleDisplayTicks--
        } else {
            titleDisplayTicks = 0
            title = null
        }
        if (subtitleDisplayTicks > 0) {
            subtitleDisplayTicks--
        } else {
            subtitleDisplayTicks = 0
            subtitle = null
        }
    }

    /**
     * Adapted from SkyblockAddons under MIT license
     * @link https://github.com/BiscuitDevelopment/SkyblockAddons/blob/master/LICENSE
     * @author BiscuitDevelopment
     */
    private fun renderTitles(scaledResolution: ScaledResolution) {
        val mc = Minecraft.getMinecraft()
        if (mc.theWorld == null || mc.thePlayer == null || !Utils.inSkyblock) {
            return
        }
        val scaledWidth = scaledResolution.scaledWidth
        val scaledHeight = scaledResolution.scaledHeight
        if (title != null) {
            val stringWidth = mc.fontRendererObj.getStringWidth(title)
            var scale = 4f // Scale is normally 4, but if its larger than the screen, scale it down...
            if (stringWidth * scale > scaledWidth * 0.9f) {
                scale = scaledWidth * 0.9f / stringWidth.toFloat()
            }
            GlStateManager.pushMatrix()
            GlStateManager.translate((scaledWidth / 2).toFloat(), (scaledHeight / 2).toFloat(), 0.0f)
            GlStateManager.enableBlend()
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0)
            GlStateManager.pushMatrix()
            GlStateManager.scale(scale, scale, scale) // TODO Check if changing this scale breaks anything...
            mc.fontRendererObj.drawString(
                title,
                (-mc.fontRendererObj.getStringWidth(title) / 2).toFloat(),
                -20.0f,
                0xFF0000,
                true
            )
            GlStateManager.popMatrix()
            GlStateManager.popMatrix()
        }
        if (subtitle != null) {
            val stringWidth = mc.fontRendererObj.getStringWidth(subtitle)
            var scale = 2f // Scale is normally 2, but if its larger than the screen, scale it down...
            if (stringWidth * scale > scaledWidth * 0.9f) {
                scale = scaledWidth * 0.9f / stringWidth.toFloat()
            }
            GlStateManager.pushMatrix()
            GlStateManager.translate((scaledWidth / 2).toFloat(), (scaledHeight / 2).toFloat(), 0.0f)
            GlStateManager.enableBlend()
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0)
            GlStateManager.pushMatrix()
            GlStateManager.scale(scale, scale, scale) // TODO Check if changing this scale breaks anything...
            mc.fontRendererObj.drawString(
                subtitle, -mc.fontRendererObj.getStringWidth(subtitle) / 2f, -23.0f,
                0xFF0000, true
            )
            GlStateManager.popMatrix()
            GlStateManager.popMatrix()
        }
    }

    companion object {
        val GUIPOSITIONS = HashMap<String, FloatPair>()
        val GUISCALES = HashMap<String, Float>()
        private val elements: MutableMap<Int, GuiElement> = HashMap()
        private val names: MutableMap<String, GuiElement> = HashMap()

        @JvmField
        var title: String? = null
        var subtitle: String? = null
        var titleDisplayTicks = 0
        var subtitleDisplayTicks = 0

        @JvmStatic
        fun createTitle(title: String?, ticks: Int) {
            Utils.playLoudSound("random.orb", 0.5)
            Companion.title = title
            titleDisplayTicks = ticks
        }
    }

    override fun read(reader: FileReader) {
        for ((key, value) in gson.fromJson(reader, JsonObject::class.java).entrySet()) {
            GUIPOSITIONS[key] = FloatPair(
                value.asJsonObject["x"].asFloat,
                value.asJsonObject["y"].asFloat
            )
            GUISCALES[key] = value.asJsonObject["scale"].asFloat
        }
    }

    override fun write(writer: FileWriter) {
        val data = JsonObject()
        for ((key, value) in names) {
            GUIPOSITIONS[key] = value.pos
            GUISCALES[key] = value.scale
            val obj = JsonObject()
            obj.addProperty("x", value.pos.getX())
            obj.addProperty("y", value.pos.getY())
            obj.addProperty("scale", value.scale)
            data.add(key, obj)
        }
        gson.toJson(data, writer)
    }

    override fun setDefault(writer: FileWriter) {
        gson.toJson(JsonObject(), writer)
    }

}