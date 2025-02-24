package elocindev.opacpvp.registry;


import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.*;

//?if fabric {
import com.faux.customentitydata.api.CustomDataHelper;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
//?}

import elocindev.opacpvp.config.Configs;

public class CommandRegistry {
    public static void register() {
        //?if fabric {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("opacpvp")
            .requires(source -> true)
            .executes(ctx -> {
                ServerPlayerEntity player = ctx.getSource().getPlayer();
                NbtCompound data = CustomDataHelper.getPersistentData(player);

                if (data.contains("opacpvp")) {
                    boolean old = data.getBoolean("opacpvp");
                    data.putBoolean("opacpvp", !old);
                    CustomDataHelper.setPersistentData(player, data);

                    Text text = old ? Text.translatable("opacpvp.toggle.disabled") : Text.translatable("opacpvp.toggle.enabled");

                    player.sendMessage(text);
                } else {
                    data.putBoolean("opacpvp", Configs.MAIN.personal_preference_default);
                    CustomDataHelper.setPersistentData(player, data);

                    Text text = Configs.MAIN.personal_preference_default ? Text.translatable("opacpvp.toggle.enabled") : Text.translatable("opacpvp.toggle.disabled");

                    player.sendMessage(text);
                }
            
                return -1;
            }));
        });
        //?}
    }
}
