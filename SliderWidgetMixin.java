package com.hackui.mixin;

import com.hackui.HackStyle;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SliderWidget.class)
public abstract class SliderWidgetMixin {
    @Shadow protected double value;

    @Inject(method = "renderWidget", at = @At("HEAD"), cancellable = true)
    private void hackui$render(DrawContext ctx, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (!HackStyle.active()) return;
        ClickableWidget w = (ClickableWidget) (Object) this;
        int x = w.getX(), y = w.getY(), width = w.getWidth(), h = w.getHeight();
        boolean hov = w.isHovered();
        HackStyle.drawBox(ctx, x, y, width, h, hov, w.active);
        int fillW = (int) ((width - 4) * this.value);
        ctx.fill(x + 2, y + h - 4, x + 2 + fillW, y + h - 2, HackStyle.ACCENT);
        int hx = x + 2 + fillW;
        ctx.fill(hx - 1, y + 2, hx + 2, y + h - 2, HackStyle.rainbow(0f));
        HackStyle.drawLabel(ctx, w.getMessage(), x, y, width, h, hov, w.active);
        ci.cancel();
    }
}
