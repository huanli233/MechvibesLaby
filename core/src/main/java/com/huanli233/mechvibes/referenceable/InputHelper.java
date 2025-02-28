package com.huanli233.mechvibes.referenceable;

import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface InputHelper {

    boolean isKeyDown(int key);

    boolean isButtonDown(int button);

}
