package elocindev.opacpvp.config;

import elocindev.opacpvp.config.entries.OpacPvPConfig;
import elocindev.necronomicon.api.config.v1.NecConfigAPI;

public class Configs {
    public static OpacPvPConfig MAIN;

    public static void loadCommonConfigs() {
        //?if fabric {
        NecConfigAPI.registerConfig(OpacPvPConfig.class);
        MAIN = OpacPvPConfig.INSTANCE;
        //?}
    }

    public static void loadClientConfigs() {}
}
