package net.rosemods.betteruiscale.access;

import net.minecraft.client.option.DoubleOption;
import net.minecraft.text.Text;

public interface OptionAccess {
    Text betterUIScale$getGenericLabel(int value);
    DoubleOption DOUBLE_GUI_SCALE = new DoubleOption("options.guiscale", 1.0D, 30.0D, 1.0F,
            (gameOptions) -> (double) gameOptions.guiScale,
            (gameOptions, scale) -> gameOptions.guiScale = scale.intValue(),
            (gameOptions, option) -> ((OptionAccess)option).betterUIScale$getGenericLabel((int)option.get(gameOptions)));
}
