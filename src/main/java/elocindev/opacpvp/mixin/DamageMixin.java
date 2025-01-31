package elocindev.opacpvp.mixin;

import java.util.UUID;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//?if fabric {
import com.faux.customentitydata.api.CustomDataHelper;
//?}

import elocindev.opacpvp.config.Configs;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import xaero.pac.common.server.api.OpenPACServerAPI;

@Mixin(PlayerEntity.class)
public class DamageMixin {

    //?if fabric {
    @Inject(at = @At("HEAD"), method = "onSpawn")
    public void opacpvp$onSpawn(CallbackInfo ci) {
        PlayerEntity ths = (PlayerEntity) (Object) this;
        if (ths instanceof ServerPlayerEntity player) {
            NbtCompound data = CustomDataHelper.getPersistentData(player);
            if (!data.contains("opacpvp")) {
                data.putBoolean("opacpvp", Configs.MAIN.personal_preference_default);

                CustomDataHelper.setPersistentData(player, data);
            }
        }
    }
    //?}

    @Inject(at = @At("HEAD"), method = "damage")
    public void opacpvp$damage(DamageSource src, float amount, CallbackInfoReturnable<Boolean> ci) {
        PlayerEntity ths = (PlayerEntity) (Object) this;

        if (ths instanceof ServerPlayerEntity player) {
            boolean status = true;

            //?if fabric {
            NbtCompound data = CustomDataHelper.getPersistentData(player);
            
            if ((data.contains("opacpvp") && Configs.MAIN.enable_personal_preference_command)) {
                status = data.getBoolean("opacpvp");
            }
            //?}
            
            if (src.getAttacker() instanceof PlayerEntity attacker) {
                if (arePlayersInSameParty(ths, attacker) && status) {
                    ci.setReturnValue(false);
                }
            }
        }
    }

    private static boolean arePlayersInSameParty(PlayerEntity player1, PlayerEntity player2) {
        MinecraftServer server = player1.getServer();
        if (server == null) {
            return false;
        }

        UUID player1UUID = player1.getUuid();
        UUID player2UUID = player2.getUuid();

        try {
            OpenPACServerAPI api = OpenPACServerAPI.get(server);
            var partyManager = api.getPartyManager();

            var player1Party = partyManager.getPartyByMember(player1UUID);
            var player2Party = partyManager.getPartyByMember(player2UUID);

            if (player1Party != null && player2Party != null) {
                return player1Party.getId().equals(player2Party.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return false;
    }
}
