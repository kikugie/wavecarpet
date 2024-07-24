package me.wavetech.wavecarpet.mixins.utils.customCriteria;

import me.wavetech.wavecarpet.core.ObjectiveCriteriaRegistry;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ObjectiveCriteria.class)
public class ObjectiveCriteriaMixin {
	static {
		ObjectiveCriteriaRegistry.init();
	}
}
