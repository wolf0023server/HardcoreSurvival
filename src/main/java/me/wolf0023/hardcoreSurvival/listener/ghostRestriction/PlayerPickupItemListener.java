package me.wolf0023.hardcoreSurvival.listener.ghostRestriction;

import me.wolf0023.hardcoreSurvival.manager.PlayerManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.entity.Player;

/**
 * プレイヤーがアイテムを拾うイベントを処理するリスナー
 * 観戦モードのプレイヤーがアイテムを拾えないようにする
 * @param playerManager プレイヤー管理クラスのインスタンス
 */
public class PlayerPickupItemListener implements Listener {

    /** プレイヤーマネージャーのインスタンス */
    private final PlayerManager playerManager;

    /** コンストラクタ */
    public PlayerPickupItemListener(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    /**
     * プレイヤーがアイテムを拾うイベントを処理する
     * @param event エンティティが地面からアイテムを拾う時のイベント
     */
    @EventHandler
    public void onEntityPickupItem(EntityPickupItemEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getEntity();

        // プレイヤーが観戦モードの場合、アイテムピックアップをキャンセルする
        if (this.playerManager.isPlayerInGhostMode(player)) {
            event.setCancelled(true);
        }
    }
}
