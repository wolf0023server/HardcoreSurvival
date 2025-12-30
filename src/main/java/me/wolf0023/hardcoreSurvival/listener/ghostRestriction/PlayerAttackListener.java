package me.wolf0023.hardcoreSurvival.listener.ghostRestriction;

import me.wolf0023.hardcoreSurvival.manager.GameStateManager;
import me.wolf0023.hardcoreSurvival.util.MessageUtil;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.entity.Player;

/** プレイヤー攻撃制限リスナー 
 * 観戦モードのプレイヤーが他のエンティティを攻撃するのを防ぐ
 * @param gameStateManager ゲーム状態管理クラスのインスタンス
 */
public class PlayerAttackListener implements Listener {

    /** ゲーム状態管理のインスタンス */
    private final GameStateManager gameStateManager;

    /** コンストラクタ */
    public PlayerAttackListener(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
    }

    /**
     * プレイヤーが他のエンティティを攻撃するイベントを処理する
     * @param event エンティティがエンティティによってダメージを受ける時のイベント
     */
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getDamager();

        // プレイヤーが観戦モードの場合、攻撃をキャンセルする
        if (this.gameStateManager.isPlayerInGhostMode(player)) {
            event.setCancelled(true);
            MessageUtil.showGhostModeRestriction(player);
        }
    }
}
