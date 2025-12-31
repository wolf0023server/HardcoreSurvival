package me.wolf0023.hardcoreSurvival.listener;

import me.wolf0023.hardcoreSurvival.manager.GameStateManager;
import me.wolf0023.hardcoreSurvival.manager.ScoreboardManager;
import me.wolf0023.hardcoreSurvival.util.MessageUtil;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import org.bukkit.entity.Player;

/**
 * プレイヤーの参加イベントを処理するリスナー
 * プレイヤーが参加した際に観戦モードの制約を適応する
 * また、初回参加キットを配布する
 * @param gameStateManager ゲーム状態管理のインスタンス
 */
public class JoinListener implements Listener {

    /** ゲーム状態管理のインスタンス */
    private final GameStateManager gameStateManager;

    /** スコアボード管理クラスのインスタンス */
    private final ScoreboardManager scoreboardManager;

    /** コンストラクタ */
    public JoinListener(GameStateManager gameStateManager, ScoreboardManager scoreboardManager) {
        this.gameStateManager = gameStateManager;
        this.scoreboardManager = scoreboardManager;
    }

    /**
     * プレイヤーのリスポーンイベントを処理する
     * @param event プレイヤー参加イベント
     */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // プレイヤーが観戦モードの場合、制約を適応する
        if (this.gameStateManager.isPlayerInGhostMode(player)) {
            this.gameStateManager.applyGhostModeRestrictions(player);
            MessageUtil.sendGhostModeMessage(player);
        }

        // スコアボードの作成
        this.scoreboardManager.createScoreboard(player);

        // 初回参加キットを配布
        this.gameStateManager.giveInitialKit(player);
    }
}
