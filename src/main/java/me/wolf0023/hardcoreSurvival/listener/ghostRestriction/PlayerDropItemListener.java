package me.wolf0023.hardcoreSurvival.listener.ghostRestriction;

import me.wolf0023.hardcoreSurvival.manager.PlayerManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.entity.Player;

/**
 * プレイヤーがアイテムをドロップするイベントを処理するリスナー
 * 観戦モードのプレイヤーがアイテムをドロップできないようにする
 * @param playerManager プレイヤー管理クラスのインスタンス
 */
public class PlayerDropItemListener implements Listener {

    /** プレイヤーマネージャーのインスタンス */
    private final PlayerManager playerManager;

    /** コンストラクタ */
    public PlayerDropItemListener(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    /**
     * プレイヤーがアイテムをドロップするイベントを処理する
     * @param event プレイヤーがアイテムをドロップする時のイベント
     */
    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();

        // プレイヤーが観戦モードの場合、アイテムドロップをキャンセルする
        if (this.playerManager.isPlayerInGhostMode(player)) {
            event.setCancelled(true);
        }
    }
}
