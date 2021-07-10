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
package thecudster.sre.core.gui.colours

import thecudster.sre.core.gui.colours.CustomColor.SetBase
import thecudster.sre.core.gui.colours.MinecraftChatColors
import thecudster.sre.core.gui.colours.ColorSet

/**
 * Taken from Skytils under GNU Affero General Public license.
 * https://github.com/Skytils/SkytilsMod/blob/main/LICENSE
 * @author My-Name-Is-Jeff
 * @author Sychic
 */
class MinecraftChatColors private constructor(rgb: Int) : SetBase(rgb) {
    companion object {
        val BLACK = MinecraftChatColors(0x000000)
        val DARK_BLUE = MinecraftChatColors(0x0000AA)
        val DARK_GREEN = MinecraftChatColors(0x00AA00)
        val DARK_AQUA = MinecraftChatColors(0x00AAAA)
        val DARK_RED = MinecraftChatColors(0xAA0000)
        val DARK_PURPLE = MinecraftChatColors(0xAA00AA)
        val GOLD = MinecraftChatColors(0xFFAA00)
        val GRAY = MinecraftChatColors(0xAAAAAA)
        val DARK_GRAY = MinecraftChatColors(0x555555)
        val BLUE = MinecraftChatColors(0x5555FF)
        val GREEN = MinecraftChatColors(0x55FF55)
        val AQUA = MinecraftChatColors(0x55FFFF)
        val RED = MinecraftChatColors(0xFF5555)
        val LIGHT_PURPLE = MinecraftChatColors(0xFF55FF)
        val YELLOW = MinecraftChatColors(0xFFFF55)
        val WHITE = MinecraftChatColors(0xFFFFFF)
        private val colors = arrayOf(
            BLACK, DARK_BLUE, DARK_GREEN, DARK_AQUA,
            DARK_RED, DARK_PURPLE, GOLD, GRAY,
            DARK_GRAY, BLUE, GREEN, AQUA,
            RED, LIGHT_PURPLE, YELLOW, WHITE
        )
        private val names = arrayOf(
            "BLACK", "DARK_BLUE", "DARK_GREEN", "DARK_AQUA",
            "DARK_RED", "DARK_PURPLE", "GOLD", "GRAY",
            "DARK_GRAY", "BLUE", "GREEN", "AQUA",
            "RED", "LIGHT_PURPLE", "YELLOW", "WHITE"
        )
        @JvmField
        val set = ColorSet(colors, names)
    }
}