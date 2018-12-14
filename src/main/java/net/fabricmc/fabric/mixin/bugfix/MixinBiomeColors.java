/*
 * Copyright (c) 2016, 2017, 2018 FabricMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.fabricmc.fabric.mixin.bugfix;

import net.minecraft.client.render.block.BiomeColors;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ExtendedBlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BiomeColors.class)
public class MixinBiomeColors {
	// As of 18w50a, BiomeColors.colorAt violates Mojang's contract and doesn't check for the view (or position!) being null.
	// In some cases we could probably live with var1 being null, but...
	@Inject(at = @At("HEAD"), method = "colorAt", cancellable = true)
	private static void colorAt(ExtendedBlockView var0, BlockPos var1, @Coerce Object var2, CallbackInfoReturnable<Integer> info) {
		if (var0 == null || var1 == null) {
			info.setReturnValue(-1);
			info.cancel();
		}
	}
}
