package me.wolf0023.hardcoreSurvival.listener.ghostRestriction;

import me.wolf0023.hardcoreSurvival.manager.PlayerManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

import java.util.Set;
import java.util.EnumSet;

/**
 * アイテムなどの使用制限リスナー
 * 観戦モードのプレイヤーが特定のアイテムを使用
 * @param playerManager プレイヤー管理クラスのインスタンス
 */
public class PlayerInteractListener implements Listener {

    /** プレイヤーマネージャーのインスタンス */
    private final PlayerManager playerManager;

    /** 使用を禁止するアイテム */
    private static final Set<Material> PROHIBITED_ITEMS = EnumSet.of(
        Material.ENDER_EYE,
        Material.FLINT_AND_STEEL,
        Material.FIRE_CHARGE,
        Material.TRIAL_KEY,
        Material.OMINOUS_TRIAL_KEY
    );

    /** インタラクトを禁止するブロック */
    private static final Set<Material> PROHIBITED_BLOCKS = EnumSet.of(
        Material.DECORATED_POT,
        Material.COMPOSTER,
        Material.LECTERN,
        Material.OAK_SHELF,
        Material.SPRUCE_SHELF,
        Material.BIRCH_SHELF,
        Material.JUNGLE_SHELF,
        Material.ACACIA_SHELF,
        Material.DARK_OAK_SHELF,
        Material.MANGROVE_SHELF,
        Material.CHERRY_SHELF,
        Material.PALE_OAK_SHELF,
        Material.BAMBOO_SHELF,
        Material.CRIMSON_SHELF,
        Material.WARPED_SHELF
    );

    /** コンストラクタ */
    public PlayerInteractListener(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    /**
     * プレイヤーがアイテムを使用するイベントを処理する
     * @param event プレイヤーがオブジェクトに右クリックする時のイベント
     */
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Material itemType = event.getItem() != null ? event.getItem().getType() : null;
        Action action = event.getAction();

        // アイテムが存在しない場合は処理を終了
        if (itemType == null) {
            return;
        }

        // アイテムを持った状態でのブロックの破壊は許可する
        if (action == Action.LEFT_CLICK_BLOCK) {
            return;
        }

        if (this.playerManager.isPlayerInGhostMode(player)
            && PROHIBITED_ITEMS.contains(itemType)) {
            event.setCancelled(true);
        }
    }

    /**
     * プレイヤーがスポーンエッグを使用するイベントを処理する
     * @param event プレイヤーがオブジェクトに右クリックする時のイベント
     */
    @EventHandler
    public void onPlayerUseSpawnEgg(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Material itemType = event.getItem() != null ? event.getItem().getType() : null;

        // アイテムが存在しない場合は処理を終了
        if (itemType == null) {
            return;
        }

        if (this.playerManager.isPlayerInGhostMode(player)
            && itemType.name().endsWith("_SPAWN_EGG")) {
            event.setCancelled(true);
        }
    }

    /**
     * プレイヤーが特定のブロックを操作するイベントを処理する
     * @param event プレイヤーがオブジェクトに右クリックする時のイベント
     */
    @EventHandler
    public void onPlayerInteractBlock(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Material blockType = event.getClickedBlock() != null ? event.getClickedBlock().getType() : null;
        Action action = event.getAction();

        // ブロックが存在しない場合は処理を終了
        if (blockType == null) {
            return;
        }

        // 右クリック(使用) の禁止
        // ただし、ブロックへの設置もできなくなる
        if (this.playerManager.isPlayerInGhostMode(player)
            && PROHIBITED_BLOCKS.contains(blockType)
            && action == Action.RIGHT_CLICK_BLOCK) {
            event.setCancelled(true);
        }
    }
}
