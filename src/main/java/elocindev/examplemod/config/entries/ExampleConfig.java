package elocindev.examplemod.config.entries;

import elocindev.examplemod.ExampleMod;
import elocindev.necronomicon.api.config.v1.NecConfigAPI;
import elocindev.necronomicon.config.NecConfig;

public class ExampleConfig {
    @NecConfig public static ExampleConfig INSTANCE;


    public static String getFile() {
        return NecConfigAPI.getFile(ExampleMod.MODID+".json5");
    }

    public String myString = "example";
    public int myNumber = 1;
    public boolean myBoolean = true;
    public double myDouble = 1.0;
}
