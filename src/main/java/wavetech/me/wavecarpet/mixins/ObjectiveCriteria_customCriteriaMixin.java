package wavetech.me.wavecarpet.mixins;

import net.minecraft.world.scores.criteria.ObjectiveCriteria;
import org.spongepowered.asm.mixin.Mixin;
import wavetech.me.wavecarpet.core.ObjectiveCriteriaRegistry;

@Mixin(ObjectiveCriteria.class)
public class ObjectiveCriteria_customCriteriaMixin {
	static {
		ObjectiveCriteriaRegistry.init();
	}
}
