package me.wolf0023.hardcoreSurvival.listener;

import me.wolf0023.hardcoreSurvival.manager.GameStateManager;
import me.wolf0023.hardcoreSurvival.util.MessageUtil;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import org.bukkit.entity.Player;

/**
 * プレイヤーのリスポーンイベントを処理するリスナー
 * リスポーン時に観戦モードの制約を適応する
 * @param gameStateManager ゲーム状態管理のインスタンス
 */
public class RespawnListener implements Listener {

    /** ゲーム状態管理のインスタンス */
    private final GameStateManager gameStateManager;

    /** コンストラクタ */
    public RespawnListener(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
    }

    /**
     * プレイヤーのリスポーンイベントを処理する
     * @param event プレイヤーリスポーンイベント
     */
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();

        // プレイヤーが観戦モードの場合、制約を適応する
        if (this.gameStateManager.isPlayerInGhostMode(player)) {
            this.gameStateManager.applyGhostModeRestrictions(player);
            MessageUtil.sendGhostModeMessage(player);
        }
    }
}
