package thecudster.sre.core

import net.minecraft.util.EnumChatFormatting
import thecudster.sre.util.Utils.sendMsg

object Changelog {
    private val types: ArrayList<ChangelogEntry>? = null
    fun init() {
        // v1.0-pre1
        types!!.add(ChangelogEntry("", "1.0-pre1", ChangelogType.CHANGE))
        types.add(ChangelogEntry("", "1.0-pre1", ChangelogType.ADDITION))
        types.add(ChangelogEntry("", "1.0-pre1", ChangelogType.REMOVED))
    }

    fun printChangelog(version: String) {
        sendMsg(EnumChatFormatting.GREEN.toString() + "You just got the new version " + version + "! Here's what's new:")
        for (entry in types!!) {
            if (entry.version == version) {
                sendMsg(entry.formattedString)
            }
        }
        sendMsg(EnumChatFormatting.RED.toString() + "❤ " + EnumChatFormatting.GOLD + "Thanks for using SRE!")
    }
}