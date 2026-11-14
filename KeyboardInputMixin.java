package com.example.sneaklock.mixin;

import com.example.sneaklock.SneakLockClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.Input;
import net.minecraft.client.input.KeyboardInput;
import net.minecraft.util.PlayerInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardInput.class)
public abstract class KeyboardInputMixin extends Input {

    @Inject(method = "tick", at = @At("TAIL"))
    private void sneaklock$forceSneak(CallbackInfo ci) {
        SneakLockClient.update(MinecraftClient.getInstance().options.sneakKey.isPressed());

        if (SneakLockClient.latched) {
            PlayerInput p = this.playerInput;
            this.playerInput = new PlayerInput(
                    p.forward(), p.backward(), p.left(), p.right(),
                    p.jump(), true, p.sprint());
        }
    }
}
