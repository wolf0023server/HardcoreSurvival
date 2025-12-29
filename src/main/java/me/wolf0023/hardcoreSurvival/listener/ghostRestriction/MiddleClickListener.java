package me.wolf0023.hardcoreSurvival.listener.ghostRestriction;

import me.wolf0023.hardcoreSurvival.manager.PlayerManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.entity.Player;
import io.papermc.paper.event.player.PlayerPickBlockEvent;
import io.papermc.paper.event.player.PlayerPickEntityEvent;

/**
 * プレイヤーのホイールクリック時のイベントを処理するリスナー
 * 観戦モードのプレイヤーがホイールクリックで、ブロックやエンティティを選択できないようにする
 * @param playerManager プレイヤー管理クラスのインスタンス
 */
public class MiddleClickListener implements Listener {

    /** プレイヤーマネージャーのインスタンス */
    private final PlayerManager playerManager;

    /** コンストラクタ */
    public MiddleClickListener(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    /**
     * プレイヤーがホイールクリックでブロックを選択するイベントを処理する
     * @param event プレイヤーがホイールクリックでブロックを選択するイベント
     */
    @EventHandler
    public void onPlayerPickBlock(PlayerPickBlockEvent event) {
        Player player = event.getPlayer();

        // プレイヤーが観戦モードの場合、ホイールクリックでのブロック選択をキャンセルする
        if (this.playerManager.isPlayerInGhostMode(player)) {
            event.setCancelled(true);
        }
    }

    /**
     * プレイヤーがホイールクリックでエンティティを選択するイベントを処理する
     * @param event プレイヤーがホイールクリックでエンティティを選択するイベント
     */
    @EventHandler
    public void onPlayerPickEntity(PlayerPickEntityEvent event) {
        Player player = event.getPlayer();

        // プレイヤーが観戦モードの場合、ホイールクリックでのエンティティ選択をキャンセルする
        if (this.playerManager.isPlayerInGhostMode(player)) {
            event.setCancelled(true);
        }
    }
}
