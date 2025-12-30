package me.wolf0023.hardcoreSurvival.listener.ghostRestriction;

import me.wolf0023.hardcoreSurvival.manager.GameStateManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPlaceEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.EnumSet;

/**
 * エンティティ設置制限リスナー
 * 観戦モードのプレイヤーがエンティティを設置できないようにする
 * @param gameStateManager ゲーム状態管理クラスのインスタンス
 */
public class EntityPlaceListener implements Listener {

    /** ゲーム状態管理のインスタンス */
    private final GameStateManager gameStateManager;

    /** 設置を禁止するエンティティのリスト */
    private static final Set<EntityType> PLACEMENT_BANNED_ENTITIES = EnumSet.of(
        EntityType.END_CRYSTAL,
        EntityType.MINECART,
        EntityType.CHEST_MINECART,
        EntityType.TNT_MINECART,
        EntityType.SPAWNER_MINECART,
        EntityType.COMMAND_BLOCK_MINECART
    );

    /** 操作を制限するエンティティのリスト */
    private static final Set<EntityType> INTERACTION_PROTECTED_ENTITIES = EnumSet.of(
        EntityType.ITEM_FRAME,
        EntityType.GLOW_ITEM_FRAME
    );

    /** コンストラクタ */
    public EntityPlaceListener(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
    }

    /**
     * プレイヤーがエンティティを設置するイベントを処理する
     * @param event エンティティが設置される時のイベント
     */
    @EventHandler
    public void onEntityPlace(EntityPlaceEvent event) {
        Player player = event.getPlayer();
        EntityType entityType = event.getEntityType();

        if (this.gameStateManager.isPlayerInGhostMode(player)
            && PLACEMENT_BANNED_ENTITIES.contains(entityType)) {
            event.setCancelled(true);
        }
    }

    /**
     * プレイヤーが額縁などにアイテムを設置するイベントを処理する
     * @param event プレイヤーがエンティティに右クリックする時のイベント
     */
    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        EntityType entityType = event.getRightClicked() != null ? event.getRightClicked().getType() : null;

        // アイテムが存在しない場合は処理を終了
        if (entityType == null) {
            return;
        }

        // 額縁などへ設置を制限
        if (this.gameStateManager.isPlayerInGhostMode(player)
            && INTERACTION_PROTECTED_ENTITIES.contains(entityType)) {
            event.setCancelled(true);
        }
    }

    /**
     * プレイヤーがアーマースタンドを操作するイベントを処理する
     * @param event プレイヤーがアーマースタンドを操作する時のイベント
     */
    @EventHandler
    public void onPlayerArmorStandManipulate(PlayerArmorStandManipulateEvent event) {
        Player player = event.getPlayer();

        if (this.gameStateManager.isPlayerInGhostMode(player)) {
            event.setCancelled(true);
        }
    }
}
