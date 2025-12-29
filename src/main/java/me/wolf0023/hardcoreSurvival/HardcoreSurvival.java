package me.wolf0023.hardcoreSurvival;

import me.wolf0023.hardcoreSurvival.listener.DeathListener;
import me.wolf0023.hardcoreSurvival.listener.RespawnListener;
import me.wolf0023.hardcoreSurvival.listener.JoinListener;
import me.wolf0023.hardcoreSurvival.manager.PlayerManager;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class HardcoreSurvival extends JavaPlugin {

    /** プレイヤー管理 */
    private final PlayerManager playerManager = new PlayerManager(this);

    @Override
    public void onEnable() {
        getLogger().info("HardcoreSurvival has been enabled!");

        // イベントリスナーの登録
        this.registerEvents();
    }

    @Override
    public void onDisable() {
        getLogger().info("HardcoreSurvival has been disabled.");
    }

    /** イベントリスナーの登録 */
    private void registerEvents() {
        // 死亡時の観戦モードへの設定
        Bukkit.getPluginManager().registerEvents(new DeathListener(this, this.playerManager), this);
        // リスポーン時の観戦モード制約の適応
        Bukkit.getPluginManager().registerEvents(new RespawnListener(this, this.playerManager), this);
        // プレイヤー参加時の観戦モード制約の適応 および 初回参加キットの配布
        Bukkit.getPluginManager().registerEvents(new JoinListener(this, this.playerManager), this);
    }
}
