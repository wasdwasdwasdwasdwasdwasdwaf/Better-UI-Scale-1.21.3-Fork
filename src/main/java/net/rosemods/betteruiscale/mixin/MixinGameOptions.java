package net.rosemods.betteruiscale.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import net.rosemods.betteruiscale.MaxSuppliableIntSliderCallbacks;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(GameOptions.class)
public class MixinGameOptions {
    @SuppressWarnings("unchecked")
    @ModifyArg(
        method = "<init>",
        index = 3,
        at = @At(
            value = "INVOKE",
            target = "net/minecraft/client/option/SimpleOption.<init> (Ljava/lang/String;Lnet/minecraft/client/option/SimpleOption$TooltipFactoryGetter;Lnet/minecraft/client/option/SimpleOption$ValueTextGetter;Lnet/minecraft/client/option/SimpleOption$Callbacks;Ljava/lang/Object;Ljava/util/function/Consumer;)V"
        ),
        allow = 1,
        slice = @Slice(
            from = @At(
                value = "FIELD",
                opcode = Opcodes.PUTFIELD,
                shift = At.Shift.AFTER,
                target = "Lnet/minecraft/client/option/GameOptions;gamma:Lnet/minecraft/client/option/SimpleOption;"
            ),
            to = @At(
                value = "FIELD",
                opcode = Opcodes.PUTFIELD,
                shift = At.Shift.BEFORE,
                target = "Lnet/minecraft/client/option/GameOptions;guiScale:Lnet/minecraft/client/option/SimpleOption;"
            )
        )
    )
    private <T> SimpleOption.Callbacks<T> modifyGuiScaleOption(SimpleOption.Callbacks<T> callbacks) {
        return (SimpleOption.Callbacks<T>) new MaxSuppliableIntSliderCallbacks(0, () -> {
            MinecraftClient minecraftClient = MinecraftClient.getInstance();
            if (!minecraftClient.isRunning()) {
                return 0x7FFFFFFE;
            }
            return minecraftClient.getWindow().calculateScaleFactor(0, minecraftClient.forcesUnicodeFont());
        });
    }
}
