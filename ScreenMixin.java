package com.hackui.mixin;

import com.hackui.HackStyle;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public abstract class ScreenMixin {
    @Shadow public int width;
    @Shadow public int height;

    @Inject(method = "renderBackground", at = @At("HEAD"), cancellable = true)
    private void hackui$bg(DrawContext ctx, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (HackStyle.active()) {
            HackStyle.drawBackground(ctx, this.width, this.height);
            ci.cancel();
        }
    }
}
