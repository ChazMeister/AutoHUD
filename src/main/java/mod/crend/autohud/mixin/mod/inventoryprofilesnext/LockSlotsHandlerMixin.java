package mod.crend.autohud.mixin.mod.inventoryprofilesnext;

import mod.crend.autohud.component.Component;
import org.anti_ad.mc.ipnext.event.LockSlotsHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(LockSlotsHandler.class)
public class LockSlotsHandlerMixin {
    @ModifyArg(method="drawHotSprite", at=@At(value = "INVOKE", target = "Lorg/anti_ad/mc/common/math2d/Point;<init>(II)V", ordinal = 0), index = 1, remap = false)
    private int drawAtOffset(int y) {
        return y + (int) Component.Hotbar.getDeltaY();
    }
}
