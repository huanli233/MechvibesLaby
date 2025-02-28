package com.huanli233.mechvibes;

import net.labymod.api.addon.AddonConfig;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget;
import net.labymod.api.configuration.loader.annotation.ConfigName;
import net.labymod.api.configuration.loader.property.ConfigProperty;

@ConfigName("settings")
public class MechvibesConfiguration extends AddonConfig {

    @SwitchSetting
    private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(true);

    @Override
    public ConfigProperty<Boolean> enabled() {
        return this.enabled;
    }

    @SwitchSetting
    public final ConfigProperty<Boolean> keyboard = new ConfigProperty<>(false);
    @DropdownWidget.DropdownSetting
    public final ConfigProperty<KeyboardMode> keyboardMode = new ConfigProperty<>(KeyboardMode.NK_CREAM);
    @SliderWidget.SliderSetting(steps = 0.1f, min = 0, max = 1)
    public final ConfigProperty<Float> keyboardVolume = new ConfigProperty<>(0.5f);

    @SwitchSetting
    public final ConfigProperty<Boolean> mouse = new ConfigProperty<>(false);
    @SliderWidget.SliderSetting(steps = 0.1f, min = 0, max = 1)
    public final ConfigProperty<Float> mouseVolume = new ConfigProperty<>(0.5f);


    public enum KeyboardMode {
        NK_CREAM("nk_cream"), MX_BLUE("mx_blue"),
        MX_SILVER("mx_silver"), RAZER_GREEN("razer_green"),
        HYPERX_AQUA("hyperX_aqua"), MX_BLACK("mx_black"),
        TOPRE_PURPLE("topre_purple");

        private final String value;

        KeyboardMode(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
