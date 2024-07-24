package me.wavetech.wavecarpet.mixins.command.player.loadItems;

import carpet.commands.PlayerCommand;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.minecraft.commands.Commands.literal;

@Mixin(value = PlayerCommand.class, remap = false)
public class PlayerCommandMixin {
	@Shadow private static ServerPlayer getPlayer(CommandContext<CommandSourceStack> context) {
		throw new AssertionError();
	}

	@Shadow private static boolean cantManipulate(CommandContext<CommandSourceStack> context) {
		throw new AssertionError();
	}

	@Inject(method = "stop", at = @At(value = "RETURN", ordinal = 1))
	private static void disableLoadingItems(CommandContext<CommandSourceStack> context, CallbackInfoReturnable<Integer> cir, @Local ServerPlayer player) {
		player.setLoadItems$wavecarpet(false);
	}

	@ModifyExpressionValue(
		method = "register",
		at = @At(
			value = "INVOKE",
			target = "Lcom/mojang/brigadier/builder/RequiredArgumentBuilder;then(Lcom/mojang/brigadier/builder/ArgumentBuilder;)Lcom/mojang/brigadier/builder/ArgumentBuilder;",
			ordinal = 1
		)
	)
	private static ArgumentBuilder<CommandSourceStack, ?> insertLoadItemsParameter(ArgumentBuilder<CommandSourceStack, ?> original) {
		return original.then(literal("loadItems").executes(context -> {
			if (cantManipulate(context))
				return 0;

			ServerPlayer player = getPlayer(context);
			player.setLoadItems$wavecarpet(true);

			context.getSource().sendSuccess(
				Component.literal("Enabled item loading for " + player.getDisplayName().getString()),
				false
			);
			return 1;
		}));
	}
}
