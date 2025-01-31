package elocindev.opacpvp.config.entries;

import elocindev.opacpvp.OpacPvP;
import elocindev.necronomicon.api.config.v1.NecConfigAPI;
import elocindev.necronomicon.config.Comment;
import elocindev.necronomicon.config.NecConfig;

public class OpacPvPConfig {
    @NecConfig public static OpacPvPConfig INSTANCE;


    public static String getFile() {
        return NecConfigAPI.getFile(OpacPvP.MODID+".json5");
    }

    @Comment("Enable the personal pvp preference command (/opacpvp) to allow players to enable or disable pvp for themselves, an extra check on top of the party status")
    public boolean enable_personal_preference_command = true;
    @Comment("The default value for the personal pvp preference command (/opacpvp), true will enable pvp by default, false will disable it by default")
    public boolean personal_preference_default = false;
}