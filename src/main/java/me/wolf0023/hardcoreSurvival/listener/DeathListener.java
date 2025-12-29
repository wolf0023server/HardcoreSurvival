package me.wolf0023.hardcoreSurvival.listener;

import me.wolf0023.hardcoreSurvival.HardcoreSurvival;
import me.wolf0023.hardcoreSurvival.manager.PlayerManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.entity.Player;

/**
 * プレイヤーの死亡イベントを処理するリスナー
 * プレイヤーが死亡した際に観戦モードに切り替える
 * @param plugin メインクラスのインスタンス
 * @param playerManager プレイヤーマネージャーのインスタンス
 */
public class DeathListener implements Listener {

    /** メインクラスのインスタンス */
    private final HardcoreSurvival plugin;

    /** プレイヤーマネージャーのインスタンス */
    private final PlayerManager playerManager;

    /** コンストラクタ */
    public DeathListener(HardcoreSurvival plugin, PlayerManager playerManager) {
        this.plugin = plugin;
        this.playerManager = playerManager;
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
        if (this.playerManager.isPlayerInGhostMode(player)) {
            return;
        }

        playerManager.setGhostMode(player, true);
    }
}
