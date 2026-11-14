package com.hackui.mixin;

import com.hackui.HackStyle;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.client.gui.widget.CheckboxWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PressableWidget.class)
public abstract class PressableWidgetMixin {
    @Inject(method = "renderWidget", at = @At("HEAD"), cancellable = true)
    private void hackui$render(DrawContext ctx, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (!HackStyle.active()) return;
        Object self = this;
        if (self instanceof CheckboxWidget) return; // giu nguyen o tick
        ClickableWidget w = (ClickableWidget) self;
        HackStyle.drawBox(ctx, w.getX(), w.getY(), w.getWidth(), w.getHeight(), w.isHovered(), w.active);
        HackStyle.drawLabel(ctx, w.getMessage(), w.getX(), w.getY(), w.getWidth(), w.getHeight(), w.isHovered(), w.active);
        ci.cancel();
    }
}
