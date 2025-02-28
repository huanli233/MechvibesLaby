package com.huanli233.mechvibes;

import com.huanli233.mechvibes.core.generated.DefaultReferenceStorage;
import com.huanli233.mechvibes.listener.MechvibesTickListener;
import com.huanli233.mechvibes.referenceable.InputHelper;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.models.addon.annotation.AddonMain;

@AddonMain
public class MechvibesAddon extends LabyAddon<MechvibesConfiguration> {

    private InputHelper inputHelper;

    @Override
    protected void enable() {
        this.registerSettingCategory();
        this.registerListener(new MechvibesTickListener(this));
        this.inputHelper = ((DefaultReferenceStorage) referenceStorageAccessor()).inputHelper();
    }

    public InputHelper getInputHelper() {
        return this.inputHelper;
    }

    @Override
    protected Class<MechvibesConfiguration> configurationClass() {
        return MechvibesConfiguration.class;
    }
}
