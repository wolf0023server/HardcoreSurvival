package me.wolf0023.hardcoreSurvival.listener.ghostRestriction;

import me.wolf0023.hardcoreSurvival.manager.GameStateManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.entity.Player;

/**
 * プレイヤーがアイテムをドロップするイベントを処理するリスナー
 * 観戦モードのプレイヤーがアイテムをドロップできないようにする
 * @param gameStateManager ゲーム状態管理クラスのインスタンス
 */
public class PlayerDropItemListener implements Listener {

    /** ゲーム状態管理のインスタンス */
    private final GameStateManager gameStateManager;

    /** コンストラクタ */
    public PlayerDropItemListener(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
    }

    /**
     * プレイヤーがアイテムをドロップするイベントを処理する
     * @param event プレイヤーがアイテムをドロップする時のイベント
     */
    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();

        // プレイヤーが観戦モードの場合、アイテムドロップをキャンセルする
        if (this.gameStateManager.isPlayerInGhostMode(player)) {
            event.setCancelled(true);
        }
    }
}
