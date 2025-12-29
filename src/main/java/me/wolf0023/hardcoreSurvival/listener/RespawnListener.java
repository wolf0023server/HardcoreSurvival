package me.wolf0023.hardcoreSurvival.listener;

import me.wolf0023.hardcoreSurvival.HardcoreSurvival;
import me.wolf0023.hardcoreSurvival.manager.PlayerManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.entity.Player;

/**
 * プレイヤーのリスポーンイベントを処理するリスナー
 * リスポーン時に観戦モードの制約を適応する
 * @param plugin メインクラスのインスタンス
 * @param playerManager プレイヤーマネージャーのインスタンス
 */
public class RespawnListener implements Listener {

    /** メインクラスのインスタンス */
    private final HardcoreSurvival plugin;

    /** プレイヤーマネージャーのインスタンス */
    private final PlayerManager playerManager;

    /** コンストラクタ */
    public RespawnListener(HardcoreSurvival plugin, PlayerManager playerManager) {
        this.plugin = plugin;
        this.playerManager = playerManager;
    }

    /**
     * プレイヤーのリスポーンイベントを処理する
     * @param event プレイヤーリスポーンイベント
     */
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();

        // プレイヤーが観戦モードの場合、制約を適応する
        if (this.playerManager.isPlayerInGhostMode(player)) {
            this.playerManager.applyGhostModeRestrictions(player);
            player.sendMessage("あなたは、現在観戦モードです。");
        }
    }
}
