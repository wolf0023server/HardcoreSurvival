package me.wolf0023.hardcoreSurvival.listener.ghostRestriction;

import me.wolf0023.hardcoreSurvival.manager.GameStateManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.EnumSet;

/**
 * ブロック変更制限リスナー
 * 観戦モードのプレイヤーが特定のブロックを設置、破壊できないようにする
 * @param gameStateManager ゲーム状態管理クラスのインスタンス
 */
public class BlockModifyListener implements Listener {

    /** ゲーム状態管理クラスのインスタンス */
    private final GameStateManager gameStateManager;

    /** 禁止するブロック */
    private static final Set<Material> PROHIBITED_BLOCKS = EnumSet.of(
        Material.BEDROCK,
        Material.BARRIER,
        Material.STRUCTURE_VOID,
        Material.END_PORTAL,
        Material.END_PORTAL_FRAME,
        Material.OBSIDIAN,
        Material.CRYING_OBSIDIAN,
        Material.TNT,
        Material.RESPAWN_ANCHOR,
        Material.DIAMOND_BLOCK,
        Material.EMERALD_BLOCK,
        Material.GOLD_BLOCK,
        Material.IRON_BLOCK,
        Material.NETHERITE_BLOCK,
        Material.ANCIENT_DEBRIS,
        Material.SHULKER_BOX,
        Material.DECORATED_POT,
        Material.SCAFFOLDING,
        Material.CACTUS
    );

    /** 禁止する色付きブロックの接尾辞 */
    private static final Set<String> COLORED_BLOCK_SUFFIXES = Set.of(
        "_BED",
        "_SHULKER_BOX"
    );

    /** コンストラクタ */
    public BlockModifyListener(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
    }

    /**
     * プレイヤーがブロックを破壊するイベントを処理する
     * @param event プレイヤーがブロックを破壊する時のイベント
     */
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Material blockType = event.getBlock().getType();

        // プレイヤーが観戦モードの場合、特定のブロックの破壊をキャンセルする
        if (this.gameStateManager.isPlayerInGhostMode(player)
            && PROHIBITED_BLOCKS.contains(blockType)) {
            event.setCancelled(true);
        }
    }

    /**
     * プレイヤーがブロックを設置するイベントを処理する
     * @param event プレイヤーがブロックを設置する時のイベント
     */
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Material blockType = event.getBlock().getType();

        // プレイヤーが観戦モードの場合、特定のブロックの設置をキャンセルする
        if (this.gameStateManager.isPlayerInGhostMode(player)
            && PROHIBITED_BLOCKS.contains(blockType)) {
            event.setCancelled(true);
        }
    }

    /**
     * プレイヤーが色付きブロックを設置するイベントを処理する
     * @param event プレイヤーがブロックを設置する時のイベント
     */
    @EventHandler
    public void onColoredBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();

        // プレイヤーが観戦モードでない場合は処理を終了
        if (!this.gameStateManager.isPlayerInGhostMode(player)) {
            return;
        }

        String blockName = event.getBlock().getType().name();

        // プレイヤーが観戦モードの場合、色付きブロックの設置をキャンセルする
        // RED_BED, RED_SHULKER_BOX など
        for (String suffix : COLORED_BLOCK_SUFFIXES) {
            if (blockName.endsWith(suffix)) {
                event.setCancelled(true);
                return;
            }
        }
    }

    /**
     * プレイヤーがバケツを空にするイベントを処理する
     * @param event プレイヤーがバケツを空にする時のイベント
     */
    @EventHandler
    public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event) {
        Player player = event.getPlayer();

        if (this.gameStateManager.isPlayerInGhostMode(player)) {
            event.setCancelled(true);
        }
    }

    /**
     * データを持つブロックの設置を処理する
     * @param event プレイヤーがブロックを設置する時のイベント
     */
    @EventHandler
    public void onDataBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        boolean isIncludeData = event.getItemInHand().hasItemMeta();

        // プレイヤーが観戦モードの場合、データを持つブロックの設置をキャンセルする
        if (isIncludeData
            && this.gameStateManager.isPlayerInGhostMode(player)) {
            event.setCancelled(true);
        }
    }
}

