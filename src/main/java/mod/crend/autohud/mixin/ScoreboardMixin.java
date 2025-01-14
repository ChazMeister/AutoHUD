package mod.crend.autohud.mixin;

import mod.crend.autohud.component.ScoreboardHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.ScoreboardPlayerScore;
import net.minecraft.scoreboard.Team;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Scoreboard.class)
public class ScoreboardMixin {

    @Inject(method="updateExistingObjective", at=@At("HEAD"))
    public void updateExistingObjective(ScoreboardObjective objective, CallbackInfo ci) {
        // objective description changed
        ScoreboardHelper.updateObjective(objective);
    }

    @Inject(method="updateScore", at=@At("HEAD"))
    public void updateScore(ScoreboardPlayerScore score, CallbackInfo ci) {
        // score changed
        ScoreboardHelper.updateScore(score);
    }

    @Inject(method="updatePlayerScore(Ljava/lang/String;)V", at=@At("HEAD"))
    public void updatePlayerScore(String playerName, CallbackInfo ci) {
        // player score reset
        ScoreboardHelper.removeScore(playerName);
    }

    @Inject(method="updatePlayerScore(Ljava/lang/String;Lnet/minecraft/scoreboard/ScoreboardObjective;)V", at=@At("HEAD"))
    public void updatePlayerScore(String playerName, ScoreboardObjective objective, CallbackInfo ci) {
        // player score reset
        ScoreboardHelper.removeScore(playerName, objective);
    }

    @Inject(method="updateScoreboardTeam", at=@At("HEAD"))
    public void updateScoreboardTeam(Team team, CallbackInfo ci) {
        // team modified
        // The null safeguard is a workaround for DisguiseLib compatibility.
        // That mod modifies teams in its mod initialiser, before any teams may even exist.
        // Since it might get initialised before AutoHud, our config has not been read yet,
        // and instantiating Component will fail to generate static initialisers and crash
        // the game.
        if (MinecraftClient.getInstance().world != null) {
            ScoreboardHelper.updateTeam(team);
        }
    }

}
