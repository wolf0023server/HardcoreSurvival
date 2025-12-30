package me.wolf0023.hardcoreSurvival.listener;

import me.wolf0023.hardcoreSurvival.manager.GameStateManager;
import me.wolf0023.hardcoreSurvival.util.MessageUtil;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.entity.Player;

/**
 * プレイヤーの死亡イベントを処理するリスナー
 * プレイヤーが死亡した際に観戦モードに切り替える
 * @param gameStateManager ゲーム状態管理のインスタンス
 */
public class DeathListener implements Listener {

    /** ゲーム状態管理のインスタンス */
    private final GameStateManager gameStateManager;

    /** コンストラクタ */
    public DeathListener(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
    }

    /**
     * プレイヤーの死亡イベントを処理する
     * @param event プレイヤー死亡イベント
     */
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        // プレイヤーを観戦モードに設定
        Player player = event.getEntity();

        // すでに観戦モードの場合は何もしない
        if (this.gameStateManager.isPlayerInGhostMode(player)) {
            return;
        }

        gameStateManager.setGhostMode(player, true);
        MessageUtil.sendGhostModeEnabledMessage(player);
    }
}
