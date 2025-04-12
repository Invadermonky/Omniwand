package com.invadermonky.omniwand.client;

import java.util.*;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.invadermonky.omniwand.Omniwand;
import com.invadermonky.omniwand.network.MessageGuiTransform;
import com.invadermonky.omniwand.util.ItemHelper;
import com.invadermonky.omniwand.util.WandHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class GuiWand extends GuiScreen {

    ItemStack wand;

    public GuiWand(ItemStack wand) {
        this.wand = wand;
    }

    @SideOnly(Side.CLIENT)
    public static void renderTooltip(int x, int y, List<String> tooltipData) {
        int color1 = 1347420415;
        int color2 = -267386864;

        boolean lighting = GL11.glGetBoolean(2896);
        if (lighting) RenderHelper.disableStandardItemLighting();

        if (!tooltipData.isEmpty()) {
            int var1 = 0;
            FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;

            int var2;
            int var3;
            for (var2 = 0; var2 < tooltipData.size(); ++var2) {
                var3 = fontRenderer.getStringWidth(tooltipData.get(var2));
                if (var3 > var1) {
                    var1 = var3;
                }
            }

            var2 = x + 12;
            var3 = y - 12;
            int var4 = 8;
            if (tooltipData.size() > 1) {
                var4 += 2 + (tooltipData.size() - 1) * 10;
            }

            Minecraft mc = Minecraft.getMinecraft();
            ScaledResolution res = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
            int right = var2 + var1 + 5;
            int scaledWidth = res.getScaledWidth();
            int bottom;
            if (right > scaledWidth) {
                bottom = right - scaledWidth;
                var2 -= bottom;
            }

            bottom = var3 + var4 + 5;
            int scaledHeight = res.getScaledHeight();
            if (bottom > scaledHeight) {
                int diff = bottom - scaledHeight;
                var3 -= diff;
            }

            float z = 300.0F;
            drawGradientRect(var2 - 3, var3 - 4, z, var2 + var1 + 3, var3 - 3, color2, color2);
            drawGradientRect(var2 - 3, var3 + var4 + 3, z, var2 + var1 + 3, var3 + var4 + 4, color2, color2);
            drawGradientRect(var2 - 3, var3 - 3, z, var2 + var1 + 3, var3 + var4 + 3, color2, color2);
            drawGradientRect(var2 - 4, var3 - 3, z, var2 - 3, var3 + var4 + 3, color2, color2);
            drawGradientRect(var2 + var1 + 3, var3 - 3, z, var2 + var1 + 4, var3 + var4 + 3, color2, color2);

            int var5 = (color1 & 16777215) >> 1 | color1 & -16777216;
            drawGradientRect(var2 - 3, var3 - 3 + 1, z, var2 - 3 + 1, var3 + var4 + 3 - 1, color1, var5);
            drawGradientRect(var2 + var1 + 2, var3 - 3 + 1, z, var2 + var1 + 3, var3 + var4 + 3 - 1, color1, var5);
            drawGradientRect(var2 - 3, var3 - 3, z, var2 + var1 + 3, var3 - 3 + 1, color1, color1);
            drawGradientRect(var2 - 3, var3 + var4 + 2, z, var2 + var1 + 3, var3 + var4 + 3, var5, var5);

            GL11.glDisable(GL11.GL_DEPTH_TEST);

            for (int i = 0; i < tooltipData.size(); i++) {
                String tooltip = tooltipData.get(i);
                fontRenderer.drawStringWithShadow(tooltip, var2, var3, -1);
                if (i == 0) {
                    var3 += 2;
                }

                var3 += 10;
            }
            GL11.glEnable(GL11.GL_DEPTH_TEST);
        }

        if (!lighting) RenderHelper.disableStandardItemLighting();

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        // GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }

    @SideOnly(Side.CLIENT)
    public static void drawGradientRect(int par1, int par2, float z, int par3, int par4, int par5, int par6) {
        float red_1 = (float) (par5 >> 24 & 255) / 255.0F;
        float green_1 = (float) (par5 >> 16 & 255) / 255.0F;
        float blue_1 = (float) (par5 >> 8 & 255) / 255.0F;
        float alpha_1 = (float) (par5 & 255) / 255.0F;
        float red_2 = (float) (par6 >> 24 & 255) / 255.0F;
        float green_2 = (float) (par6 >> 16 & 255) / 255.0F;
        float blue_2 = (float) (par6 >> 8 & 255) / 255.0F;
        float alpha_2 = (float) (par6 & 255) / 255.0F;

        GL11.glDisable(GL11.GL_TEXTURE);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_ALPHA);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        // GlStateManager.disableTexture2D();
        // GlStateManager.enableBlend();
        // GlStateManager.disableAlpha();
        // GlStateManager.blendFunc(770, 771);
        // GlStateManager.shadeModel(7425);

        Tessellator tessellator = Tessellator.instance;

        tessellator.startDrawing(7);
        tessellator.setColorRGBA_F(green_1, blue_1, alpha_1, red_1);
        tessellator.addVertex(par3, par2, z);
        tessellator.addVertex(par1, par2, z);
        tessellator.setColorRGBA_F(green_2, blue_2, alpha_2, red_2);
        tessellator.addVertex(par1, par4, z);
        tessellator.addVertex(par3, par4, z);

        // BufferBuilder buff = tessellator.getBuffer();
        // buff.begin(7, DefaultVertexFormats.POSITION_COLOR);
        // buff.pos(par3, par2, z).color(green_1, blue_1, alpha_1, red_1).endVertex();
        // buff.pos(par1, par2, z).color(green_1, blue_1, alpha_1, red_1).endVertex();
        // buff.pos(par1, par4, z).color(green_2, blue_2, alpha_2, red_2).endVertex();
        // buff.pos(par3, par4, z).color(green_2, blue_2, alpha_2, red_2).endVertex();

        tessellator.draw();
        GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glEnable(GL11.GL_ALPHA);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_TEXTURE);
        // GlStateManager.shadeModel(7424);
        // GlStateManager.disableBlend();
        // GlStateManager.enableAlpha();
        // GlStateManager.enableTexture2D();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        Map<String, ItemStack> stackMap = new HashMap<>();

        if (this.wand.hasTagCompound()) {
            NBTTagCompound data = WandHelper.getWandData(this.wand);
            ArrayList<String> keys = new ArrayList<>(data.func_150296_c());
            Collections.sort(keys);

            for (String key : keys) {
                NBTTagCompound compoundTag = data.getCompoundTag(key);

                if (!ItemHelper.isEmpty(compoundTag)) stackMap.put(key, ItemStack.loadItemStackFromNBT(compoundTag));
            }
        }

        ScaledResolution res = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
        int centerX = res.getScaledWidth() / 2;
        int centerY = res.getScaledHeight() / 2;
        int amountPerRow = Math.min(12, Math.max(8, stackMap.size() / 3));
        int rows = (int) Math.ceil((double) stackMap.size() / amountPerRow);
        int iconSize = 20;
        int startX = centerX - amountPerRow * iconSize / 2;
        int startY = centerY - rows * iconSize + Math.min(90, Math.max(40, 10 * rows));
        int padding = 4;
        int extra = 2;

        drawRect(
            startX - padding,
            startY - padding,
            startX + iconSize * amountPerRow + padding,
            startY + iconSize * rows + padding,
            570425344);
        drawRect(
            startX - padding - extra,
            startY - padding - extra,
            startX + iconSize * amountPerRow + padding + extra,
            startY + iconSize * rows + padding + extra,
            570425344);

        ItemStack tooltipStack = null;
        String itemKey = "";

        if (!stackMap.isEmpty()) {
            RenderHelper.enableGUIStandardItemLighting();

            int i = 0;
            for (String key : stackMap.keySet()) {
                int x = startX + i % amountPerRow * iconSize;
                int y = startY + i / amountPerRow * iconSize;
                i++;
                if (mouseX > x && mouseY > y && mouseX <= x + 16 && mouseY <= y + 16) {
                    tooltipStack = stackMap.get(key);
                    itemKey = key;
                    y -= 2;
                }
                ItemStack stack = stackMap.get(key);
                if (!ItemHelper.isEmpty(stack)) {
                    FontRenderer font = stack.getItem().getFontRenderer(stack);
                    if (font == null) font = this.fontRendererObj;
                    itemRender.renderItemAndEffectIntoGUI(font, this.mc.getTextureManager(), stack, x, y);
                }
            }
            RenderHelper.disableStandardItemLighting();
        }

        if (!ItemHelper.isEmpty(tooltipStack)) {
            String name = WandHelper.getDisplayNameCache(tooltipStack);
            String mod = EnumChatFormatting.GRAY + WandHelper.getModName(WandHelper.getModOrAlias(tooltipStack));
            //renderTooltip(mouseX, mouseY, Arrays.asList(name, mod));

            if (Mouse.isButtonDown(0)) {
                Omniwand.network.sendToServer(new MessageGuiTransform(itemKey));
                this.mc.displayGuiScreen(null);
            }
        }
    }
}
