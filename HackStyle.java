package com.hackui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.text.Text;
import net.minecraft.util.Util;

import java.awt.Color;

public final class HackStyle {
    private HackStyle() {}

    public static final int BG_TOP = 0xFF05060A;
    public static final int BG_BOTTOM = 0xFF0B1020;
    public static final int BTN = 0xF0101420;
    public static final int BTN_HOVER = 0xF01A2236;
    public static final int BTN_OFF = 0xF00C0F18;
    public static final int ACCENT = 0xFF00E5FF;

    /** Chi ap dung cho man hinh Tuy chon va cac man hinh con. */
    public static boolean active() {
        Screen s = MinecraftClient.getInstance().currentScreen;
        return s instanceof OptionsScreen || s instanceof GameOptionsScreen;
    }

    public static int rainbow(float offset) {
        float h = ((Util.getMeasuringTimeMs() / 4000f) + offset) % 1f;
        return Color.HSBtoRGB(h, 0.75f, 1f) | 0xFF000000;
    }

    public static void rainbowLine(DrawContext ctx, int x, int y, int w, int h) {
        for (int i = 0; i < w; i += 2) {
            ctx.fill(x + i, y, Math.min(x + i + 2, x + w), y + h, rainbow(i / 400f));
        }
    }

    public static void drawBackground(DrawContext ctx, int w, int h) {
        ctx.fillGradient(0, 0, w, h, BG_TOP, BG_BOTTOM);
        int grid = 0x1000E5FF;
        for (int x = 0; x < w; x += 24) ctx.fill(x, 0, x + 1, h, grid);
        for (int y = 0; y < h; y += 24) ctx.fill(0, y, w, y + 1, grid);
        int scan = (int) ((Util.getMeasuringTimeMs() / 12) % Math.max(h, 1));
        ctx.fillGradient(0, scan, w, scan + 24, 0x0000E5FF, 0x2000E5FF);
        rainbowLine(ctx, 0, 0, w, 2);
        rainbowLine(ctx, 0, h - 2, w, 2);
    }

    public static void drawBox(DrawContext ctx, int x, int y, int w, int h, boolean hovered, boolean enabled) {
        int fill = !enabled ? BTN_OFF : hovered ? BTN_HOVER : BTN;
        ctx.fill(x, y, x + w, y + h, fill);
        int border = !enabled ? 0xFF2A2F3C : hovered ? ACCENT : 0xFF2E3A52;
        ctx.drawBorder(x, y, w, h, border);
        if (enabled) ctx.fill(x, y, x + 2, y + h, hovered ? rainbow(y / 300f) : ACCENT);
    }

    public static void drawLabel(DrawContext ctx, Text text, int x, int y, int w, int h, boolean hovered, boolean enabled) {
        TextRenderer tr = MinecraftClient.getInstance().textRenderer;
        int color = !enabled ? 0xFF6B7280 : hovered ? 0xFFFFFFFF : 0xFFD6F8FF;
        ctx.drawCenteredTextWithShadow(tr, text, x + w / 2, y + (h - 8) / 2, color);
    }
}
