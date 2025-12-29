package me.wolf0023.hardcoreSurvival.listener;

import me.wolf0023.hardcoreSurvival.HardcoreSurvival;
import me.wolf0023.hardcoreSurvival.manager.PlayerManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import org.bukkit.entity.Player;

/**
 * プレイヤーの参加イベントを処理するリスナー
 * プレイヤーが参加した際に観戦モードの制約を適応する
 * @param plugin メインクラスのインスタンス
 * @param playerManager プレイヤーマネージャーのインスタンス
 */
public class JoinListener implements Listener {

    /** メインクラスのインスタンス */
    private final HardcoreSurvival plugin;

    /** プレイヤーマネージャーのインスタンス */
    private final PlayerManager playerManager;

    /** コンストラクタ */
    public JoinListener(HardcoreSurvival plugin, PlayerManager playerManager) {
        this.plugin = plugin;
        this.playerManager = playerManager;
    }

    /**
     * プレイヤーのリスポーンイベントを処理する
     * @param event プレイヤー参加イベント
     */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // プレイヤーが観戦モードの場合、制約を適応する
        if (this.playerManager.isPlayerInGhostMode(player)) {
            this.playerManager.applyGhostModeRestrictions(player);
            player.sendMessage("あなたは、現在観戦モードです。");
        }
    }
}
