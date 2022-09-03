package io.github.schntgaispock.slimehud;


import javax.annotation.Nonnull;

import io.github.schntgaispock.slimehud.waila.HudController;
//import net.guizhanss.guizhanlibplugin.updater.GuizhanBuildsUpdaterWrapper;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
import org.bukkit.NamespacedKey;

import io.github.mooy1.infinitylib.core.AbstractAddon;
import io.github.mooy1.infinitylib.core.AddonConfig;
import io.github.schntgaispock.slimehud.command.CommandManager;
import io.github.schntgaispock.slimehud.waila.WAILAManager;
import lombok.Getter;

public class SlimeHUD extends AbstractAddon {

    @Getter AddonConfig playerData;
    static @Getter SlimeHUD instance;
    private HudController hudController;

    public SlimeHUD() {
        super("SchnTgaiSpock", "SlimeHUD", "master", "options.auto-update");
    }

    @Override
    public void enable() {
        instance = this;

        getLogger().info("#=============================================#");
        getLogger().info("# SlimeHUDPlus by SchnTgaiSpock,鬼斩,AlanZhao #");
        getLogger().info("#=============================================#");

        Metrics metrics = new Metrics(this, 16233);
        metrics.addCustomChart(
            new SimplePie("disabled", () -> {
                return "" + getConfig().getBoolean("waila.disabled");
            })
        );
        metrics.addCustomChart(
            new SimplePie("waila_location", () -> {
                return getConfig().getString("waila.location");
            })
        );

        playerData = new AddonConfig("player.yml");

        WAILAManager.setup();
        CommandManager.setup();
        hudController = new HudController();

//        if (getConfig().getBoolean("options.auto-update") && getDescription().getVersion().startsWith("Build")) {
//            GuizhanBuildsUpdaterWrapper.start(this, getFile(), "SlimefunGuguProject", "SlimeHUD", "master", false);
//        }
    }

    @Override
    public void disable() {
        instance = null;
        getPlayerData().save();
        getConfig().save();
    }

    public static HudController getHudController() {
        return instance.hudController;
    }

    public static NamespacedKey newNamespacedKey(@Nonnull String name) {
        return new NamespacedKey(SlimeHUD.getInstance(), name);
    }
}
