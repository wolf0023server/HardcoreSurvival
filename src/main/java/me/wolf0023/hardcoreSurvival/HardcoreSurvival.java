package me.wolf0023.hardcoreSurvival;

import me.wolf0023.hardcoreSurvival.listener.*;
import me.wolf0023.hardcoreSurvival.listener.ghostRestriction.*;
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

        /* 観戦モード制限リスナーの登録 */
        // アイテムドロップ制限
        Bukkit.getPluginManager().registerEvents(new PlayerDropItemListener(this.playerManager), this); 
        // アイテムピックアップ制限
        Bukkit.getPluginManager().registerEvents(new PlayerPickupItemListener(this.playerManager), this);
        // ホイールクリックによるブロックおよびエンティティの選択制限
        Bukkit.getPluginManager().registerEvents(new MiddleClickListener(this.playerManager), this);
        // 飛び道具発射制限
        Bukkit.getPluginManager().registerEvents(new ProjectileLaunchListener(this.playerManager), this);
        // 攻撃制限
        Bukkit.getPluginManager().registerEvents(new PlayerAttackListener(this.playerManager), this);
        // コンテナ操作制限
        Bukkit.getPluginManager().registerEvents(new InventoryInteractListener(this.playerManager), this);
        // ブロック設置・破壊制限
        Bukkit.getPluginManager().registerEvents(new BlockModifyListener(this.playerManager), this);
        // エンティティの配置制限
        Bukkit.getPluginManager().registerEvents(new EntityPlaceListener(this.playerManager), this);
        // エンティティとのインタラクト制限
        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(this.playerManager), this);
    }
}
