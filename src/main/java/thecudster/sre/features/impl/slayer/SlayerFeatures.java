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

package thecudster.sre.features.impl.slayer;

import net.minecraft.entity.passive.EntityWolf;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thecudster.sre.SkyblockReinvented;
import thecudster.sre.util.sbutil.Utils;

public class SlayerFeatures {
    public static final String[] svenLocs = {"Ruins", "Howling Cave"};
    public static final String[] taraLocs = {"Spider's Den"};
    public static final String[] revLocs = {"Graveyard", "Coal Mine"};
    public static final String[] slayerLocs = {"Graveyard", "Coal Mine", "Spider's Den", "Ruins", "Howling Cave"};
    @SubscribeEvent
    public void onEntity(RenderLivingEvent.Pre event) {
        if (SkyblockReinvented.config.svenPups) {
            if (event.entity instanceof EntityWolf) {
                EntityWolf wolf = ((EntityWolf) event.entity);
                if (wolf.isChild() && Utils.inLoc(svenLocs)) {
                    event.setCanceled(true);
                    return;
                }
            }
        }
    }
}