package me.wolf0023.hardcoreSurvival.listener.ghostRestriction;

import me.wolf0023.hardcoreSurvival.manager.GameStateManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

import java.util.Set;
import java.util.EnumSet;

/**
 * プレイヤーがインベントリを操作するイベントを処理するリスナー
 * 観戦モードのプレイヤーがコンテナを操作できないようにする
 * @param gameStateManager ゲーム状態管理クラスのインスタンス
 */
public class InventoryInteractListener implements Listener {

    /** ゲーム状態管理のインスタンス */
    private final GameStateManager gameStateManager;

    /** コンストラクタ */
    public InventoryInteractListener(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
    }

    /** 許可するインベントリタイプのセット */
    private static final Set<InventoryType> ALLOWED_INVENTORY_TYPES = EnumSet.of(
        InventoryType.PLAYER,
        InventoryType.CRAFTING,
        InventoryType.WORKBENCH,
        InventoryType.CREATIVE,
        InventoryType.ENDER_CHEST
    );

    /**
     * プレイヤーがインベントリをクリックするイベントを処理する
     * @param event プレイヤーがインベントリをクリックする時のイベント
     */
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }

        // スロットのタイプを確認
        InventoryType inventoryType = event.getInventory().getType();
        
        // プレイヤーのインベントリやクラフト関連のインベントリは許可
        if (ALLOWED_INVENTORY_TYPES.contains(inventoryType)) {
            return;
        }

        Player player = (Player) event.getWhoClicked();

        // プレイヤーが観戦モードの場合、インベントリ操作をキャンセルする
        if (this.gameStateManager.isPlayerInGhostMode(player)) {
            event.setCancelled(true);
        }
    }
}
