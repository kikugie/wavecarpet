package wavetech.me.wavecarpet.mixins;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.network.PacketListener;
import net.minecraft.network.protocol.PacketUtils;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.scores.Score;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wavetech.me.wavecarpet.core.ObjectiveCriteriaRegistry;

@Mixin(PacketUtils.class)
public class PacketUtils_suppressionCountMixin {
	@Inject(method = "lambda$ensureRunningOnSameThread$0", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/PacketListener;shouldPropagateHandlingExceptions()Z"))
	private static void countSuppression(CallbackInfo ci, @Local(argsOnly = true) PacketListener packetListener) {
		if (packetListener instanceof ServerGamePacketListenerImpl gamePL) {
			gamePL.player.server.getScoreboard()
				.forAllObjectives(ObjectiveCriteriaRegistry.SUPPRESSION_COUNT, gamePL.player.getScoreboardName(), Score::increment);
		}
	}
}
