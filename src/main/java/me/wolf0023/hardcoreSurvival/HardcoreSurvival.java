package me.wolf0023.hardcoreSurvival;

import me.wolf0023.hardcoreSurvival.listener.*;
import me.wolf0023.hardcoreSurvival.listener.ghostRestriction.*;
import me.wolf0023.hardcoreSurvival.manager.*;
import me.wolf0023.hardcoreSurvival.repository.*;
import me.wolf0023.hardcoreSurvival.command.*;
import me.wolf0023.hardcoreSurvival.model.*;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;

public final class HardcoreSurvival extends JavaPlugin {

    /** ゲーム状態管理 */
    private GameStateManager gameStateManager;

    /** スコアボード管理 */
    private ScoreboardManager scoreboardManager;

    /** プレイヤー情報管理 */
    private StatisticsManager statisticsManager;

    /** ゲーム状態リポジトリ */
    private GameStateRepository gameStateRepository;

    @Override
    public void onEnable() {
        getLogger().info("HardcoreSurvival has been enabled!");

        // マネージャーとリポジトリの初期化
        this.gameStateRepository = new GameStateRepository(this);
        this.gameStateManager = new GameStateManager(gameStateRepository);
        this.scoreboardManager = new ScoreboardManager();
        this.statisticsManager = new StatisticsManager(gameStateRepository);

        // コマンドの登録
        this.getCommand("hcs").setExecutor(new HCSCommand(this, this.gameStateManager));
        this.getCommand("hcs").setTabCompleter(new HCSCommand(this, this.gameStateManager));
        this.getCommand("initialkit").setExecutor(new KitCommand(this.gameStateManager));

        // イベントリスナーの登録
        this.registerEvents();

        // 定期更新タスクの登録
        // スコアボードの定期更新
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    PlayerStats stats = statisticsManager.getPlayerStats(player);
                    scoreboardManager.updateScoreboard(player, stats);
                }
            }
        }, 0L, 40L); // 20L = 1秒)
    }

    @Override
    public void onDisable() {
        getLogger().info("HardcoreSurvival has been disabled.");
    }

    /** イベントリスナーの登録 */
    private void registerEvents() {
        // 死亡時の観戦モードへの設定
        Bukkit.getPluginManager().registerEvents(new DeathListener(this.gameStateManager), this);
        // リスポーン時の観戦モード制約の適応
        Bukkit.getPluginManager().registerEvents(new RespawnListener(this.gameStateManager), this);
        // プレイヤー参加時の観戦モード制約の適応 および 初回参加キットの配布
        Bukkit.getPluginManager().registerEvents(new JoinListener(this.gameStateManager, this.scoreboardManager), this);
        // エンダードラゴン討伐時のゲームクリア処理
        Bukkit.getPluginManager().registerEvents(new DragonDefeatListener(this, this.gameStateManager), this);

        /* 観戦モード制限リスナーの登録 */
        // アイテムドロップ制限
        Bukkit.getPluginManager().registerEvents(new PlayerDropItemListener(this.gameStateManager), this); 
        // アイテムピックアップ制限
        Bukkit.getPluginManager().registerEvents(new PlayerPickupItemListener(this.gameStateManager), this);
        // ホイールクリックによるブロックおよびエンティティの選択制限
        Bukkit.getPluginManager().registerEvents(new MiddleClickListener(this.gameStateManager), this);
        // 飛び道具発射制限
        Bukkit.getPluginManager().registerEvents(new ProjectileLaunchListener(this.gameStateManager), this);
        // 攻撃制限
        Bukkit.getPluginManager().registerEvents(new PlayerAttackListener(this.gameStateManager), this);
        // コンテナ操作制限
        Bukkit.getPluginManager().registerEvents(new InventoryInteractListener(this.gameStateManager), this);
        // ブロック設置・破壊制限
        Bukkit.getPluginManager().registerEvents(new BlockModifyListener(this.gameStateManager), this);
        // エンティティの配置制限
        Bukkit.getPluginManager().registerEvents(new EntityPlaceListener(this.gameStateManager), this);
        // エンティティとのインタラクト制限
        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(this.gameStateManager), this);
    }
}
