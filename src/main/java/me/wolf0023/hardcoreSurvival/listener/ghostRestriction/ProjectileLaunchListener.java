package me.wolf0023.hardcoreSurvival.listener.ghostRestriction;

import me.wolf0023.hardcoreSurvival.manager.PlayerManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.entity.Player;

/**
 * プレイヤーが飛び道具を発射するイベントを処理するリスナー
 * 観戦モードのプレイヤーが飛び道具を発射できないようにする
 * @param playerManager プレイヤー管理クラスのインスタンス
 */
public class ProjectileLaunchListener implements Listener {

    /** プレイヤーマネージャーのインスタンス */
    private final PlayerManager playerManager;

    /** コンストラクタ */
    public ProjectileLaunchListener(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    /**
     * プレイヤーが飛び道具を発射するイベントを処理する
     * @param event プレイヤーが飛び道具を発射する時のイベント
     */
    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        if (!(event.getEntity().getShooter() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getEntity().getShooter();

        // プレイヤーが観戦モードの場合、飛び道具の発射をキャンセルする
        if (this.playerManager.isPlayerInGhostMode(player)) {
            event.setCancelled(true);
        }
    }
}
