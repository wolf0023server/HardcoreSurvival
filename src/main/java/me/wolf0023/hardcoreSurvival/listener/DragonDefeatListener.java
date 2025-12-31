package me.wolf0023.hardcoreSurvival.listener;

import me.wolf0023.hardcoreSurvival.manager.GameStateManager;
import me.wolf0023.hardcoreSurvival.HardcoreSurvival;
import me.wolf0023.hardcoreSurvival.util.MessageUtil;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.entity.EntityType;

/**
 * エンダードラゴンの死亡イベントを処理するリスナー
 * ドラゴンが倒された際にゲームクリアとし、ゲーム状態を更新する
 * @param plugin メインクラスのインスタンス
 * @param gameStateManager ゲーム状態管理のインスタンス
 */
public class DragonDefeatListener implements Listener {

    /** メインクラスのインスタンス */
    private final HardcoreSurvival plugin;

    /** ゲーム状態管理のインスタンス */
    private final GameStateManager gameStateManager;

    /** コンストラクタ */
    public DragonDefeatListener(HardcoreSurvival plugin, GameStateManager gameStateManager) {
        this.plugin = plugin;
        this.gameStateManager = gameStateManager;
    }
    /**
     * エンダードラゴンの死亡イベントを処理する
     * ドラゴンが倒された際にゲームクリアとする
     * @param event 
     */
    @EventHandler
    public void onDragonDefeat(EntityDeathEvent event) {
        // エンダードラゴンが倒された場合
        if (event.getEntityType() == EntityType.ENDER_DRAGON) {
            this.gameStateManager.endGame();
            MessageUtil.broadcastGameEndMessage(this.plugin.getServer());
        }
    }
}

