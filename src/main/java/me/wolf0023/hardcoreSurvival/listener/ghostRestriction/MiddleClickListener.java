package me.wolf0023.hardcoreSurvival.listener.ghostRestriction;

import me.wolf0023.hardcoreSurvival.manager.GameStateManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.entity.Player;
import io.papermc.paper.event.player.PlayerPickBlockEvent;
import io.papermc.paper.event.player.PlayerPickEntityEvent;

/**
 * プレイヤーのホイールクリック時のイベントを処理するリスナー
 * 観戦モードのプレイヤーがホイールクリックで、ブロックやエンティティを選択できないようにする
 * @param gameStateManager ゲーム状態管理クラスのインスタンス
 */
public class MiddleClickListener implements Listener {

    /** ゲーム状態管理のインスタンス */
    private final GameStateManager gameStateManager;

    /** コンストラクタ */
    public MiddleClickListener(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
    }

    /**
     * プレイヤーがホイールクリックでブロックを選択するイベントを処理する
     * @param event プレイヤーがホイールクリックでブロックを選択するイベント
     */
    @EventHandler
    public void onPlayerPickBlock(PlayerPickBlockEvent event) {
        Player player = event.getPlayer();

        // プレイヤーが観戦モードの場合、ホイールクリックでのブロック選択をキャンセルする
        if (this.gameStateManager.isPlayerInGhostMode(player)) {
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
        if (this.gameStateManager.isPlayerInGhostMode(player)) {
            event.setCancelled(true);
        }
    }
}
