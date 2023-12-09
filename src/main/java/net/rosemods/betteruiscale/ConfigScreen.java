package net.rosemods.betteruiscale;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.OptionListWidget;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

public class ConfigScreen
    extends Screen {
    protected final Screen parent;
    protected final Config config;
    private OptionListWidget list;

    public ConfigScreen(Screen parent, Config config) {
        super(Text.translatable("betteruiscale.options.title"));
        this.parent = parent;
        this.config = config;
    }

    @Override
    protected void init() {
        list = new OptionListWidget(client, width, height-64, 32, 25);
        list.addAll(config.getOptions().values().toArray(SimpleOption[]::new));
        addSelectableChild(list);
        addDrawableChild(ButtonWidget.builder(ScreenTexts.DONE, button -> {
            config.save(Main.configPath());
            client.setScreen(parent);
        }).dimensions(width / 2 - 100, height - 27, 200, 20).build());
    }

    @Override
    public void removed() {
        config.save(Main.configPath());
    }

    @Override
    public void close() {
        client.setScreen(parent);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float tickDelta) {
        super.render(context, mouseX, mouseY, tickDelta);
        list.render(context, mouseX, mouseY, tickDelta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20, 16777215);
    }

    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackgroundTexture(context);
    }
}

