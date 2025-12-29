package me.wolf0023.hardcoreSurvival.listener.ghostRestriction;

import me.wolf0023.hardcoreSurvival.manager.PlayerManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.entity.Player;

/** プレイヤー攻撃制限リスナー 
 * 観戦モードのプレイヤーが他のエンティティを攻撃するのを防ぐ
 * @param playerManager プレイヤー管理クラスのインスタンス
 */
public class PlayerAttackListener implements Listener {

    /** プレイヤーマネージャーのインスタンス */
    private final PlayerManager playerManager;

    /** コンストラクタ */
    public PlayerAttackListener(PlayerManager playerManager) {
        this.playerManager = playerManager;
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
        if (this.playerManager.isPlayerInGhostMode(player)) {
            event.setCancelled(true);
        }
    }
}
