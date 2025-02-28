package com.huanli233.mechvibes.v1_8_9;

import com.huanli233.mechvibes.referenceable.InputHelper;
import net.labymod.api.models.Implements;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

@Implements(InputHelper.class)
public class VersionedInputHelper implements InputHelper {

    @Override
    public boolean isKeyDown(int key) {
        return Keyboard.isKeyDown(key);
    }

    @Override
    public boolean isButtonDown(int button) {
        return Mouse.isButtonDown(button);
    }

}
