package me.wavetech.wavecarpet.mixins.utils;

import carpet.commands.PlayerCommand;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import me.wavetech.wavecarpet.access.PlayerLoader;

import static net.minecraft.commands.Commands.literal;

@Mixin(value = PlayerCommand.class, remap = false)
public class CarpetPlayerCommand_loadItemsCommandMixin {
	@Inject(method = "stop", at = @At("RETURN"))
	private static void disableDumpingItems(CommandContext<CommandSourceStack> context, CallbackInfoReturnable<Integer> cir) {
		var player = (PlayerLoader) getPlayer(context);
		player.wavecarpet$setLoadItems(false);
	}

	@Unique
	private static ServerPlayer getPlayer(CommandContext<CommandSourceStack> context) {
		String playerName = StringArgumentType.getString(context, "player");
		MinecraftServer server = context.getSource().getServer();
		return server.getPlayerList().getPlayerByName(playerName);
	}

	@ModifyExpressionValue(
			method = "register", at = @At(
			value = "INVOKE",
			target = "Lcom/mojang/brigadier/builder/RequiredArgumentBuilder;then(Lcom/mojang/brigadier/builder/ArgumentBuilder;)Lcom/mojang/brigadier/builder/ArgumentBuilder;",
			ordinal = 1
	)
	)
	private static ArgumentBuilder<CommandSourceStack, ?> insertLoadItemsParameter(ArgumentBuilder<CommandSourceStack, ?> original) {
		return original.then(literal("loadItems").executes(context -> {
			ServerPlayer player = getPlayer(context);
			((PlayerLoader) player).wavecarpet$setLoadItems(true);

			context.getSource().sendSuccess(
					Component.literal("Enabled item dump for " + player.getDisplayName().getString()),
					false
			);
			return 1;
		}));
	}
}