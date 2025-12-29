package me.wolf0023.hardcoreSurvival.manager;

import me.wolf0023.hardcoreSurvival.HardcoreSurvival;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.GameMode;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * プレイヤーを管理するクラス
 * 観戦モードの切り替えや、状態の保存などを担当する
 * @param plugin メインクラスのインスタンス
 */
public class PlayerManager {

    /** メインクラスのインスタンス */
    private final HardcoreSurvival plugin;

    /** 観戦モードのPDCキー */
    private final NamespacedKey ghostModeKey;

    /** 初期参加キット受取済みのPDCキー */
    private final NamespacedKey hasReceivedKit;

    /** 初期参加キットの内容 */
    private final ArrayList<ItemStack> initialKit;

    /** コンストラクタ */
    public PlayerManager(HardcoreSurvival plugin) {
        this.plugin = plugin;
        this.ghostModeKey = new NamespacedKey(plugin, "ghost_mode");
        this.hasReceivedKit = new NamespacedKey(plugin, "has_received_kit");

        this.initialKit = new ArrayList<>(
            Arrays.asList(
                new ItemStack(Material.STONE_AXE),
                new ItemStack(Material.STONE_HOE),
                new ItemStack(Material.STONE_PICKAXE),
                new ItemStack(Material.STONE_SHOVEL),
                new ItemStack(Material.STONE_SWORD),
                new ItemStack(Material.LEATHER_HELMET),
                new ItemStack(Material.LEATHER_CHESTPLATE),
                new ItemStack(Material.LEATHER_LEGGINGS),
                new ItemStack(Material.LEATHER_BOOTS),
                new ItemStack(Material.BREAD, 32),
                new ItemStack(Material.SPYGLASS)
            )
        );
    }

    /**
     * 観戦モードの制約を適用する
     * ただし、観戦モードかどうかの判定は行わない
     * @param player 対象のプレイヤー
     */
    public void applyGhostModeRestrictions(Player player) {
        player.setGameMode(GameMode.CREATIVE);
        return;
    }

    /**
     * 観戦モードの制約を解除する
     * ただし、観戦モードかどうかの判定は行わない
     * @param player 対象のプレイヤー
     */
    public void removeGhostModeRestrictions(Player player) {
        player.setGameMode(GameMode.SURVIVAL);
        return;
    }

    /**
     * プレイヤーの観戦モードを切り替える
     * @param player 対象のプレイヤー
     * @param isGhost 観戦モードにするかどうか
     */
    public void setGhostMode(Player player, boolean isGhost) {
        // 観戦モードを解除する場合
        if (!isGhost) {
            this.removeGhostModeRestrictions(player);

            // PDCから観戦モードの情報を削除
            player.getPersistentDataContainer().remove(ghostModeKey);
            player.sendMessage("観戦モードが解除されました！");
            return;
        }

        this.applyGhostModeRestrictions(player);

        // PDCに観戦モードの情報を保存
        player.getPersistentDataContainer().set(ghostModeKey, PersistentDataType.BOOLEAN, true);
        player.sendMessage("観戦モードになりました！");
    }

    /**
     * プレイヤーが観戦モードかどうかを判定する
     * @param player 対象のプレイヤー
     * @return 観戦モードであればtrue、そうでなければfalse
     */
    public boolean isPlayerInGhostMode(Player player) {
        Boolean isGhost = player.getPersistentDataContainer().get(ghostModeKey, PersistentDataType.BOOLEAN);
        return isGhost != null && isGhost;
    }

    /**
     * プレイヤーに初回参加キットを配布する
     * ただし、既に受け取っているかどうかの
     * @param player 対象のプレイヤー
     */
    public void giveInitialKit(Player player) {
        // 既に受け取っている場合は何もしない
        if (this.hasPlayerReceivedInitialKit(player)) {
            return;
        }

        // 初回参加キットの配布
        for (ItemStack item : this.initialKit) {
            player.getInventory().addItem(item);
        }
        player.sendMessage("初回参加キットを受け取りました！");

        // PDCに受取済みの情報を保存
        player.getPersistentDataContainer().set(hasReceivedKit, PersistentDataType.BOOLEAN, true);
    }

    /**
     * プレイヤーが初回参加キットを受け取ったかどうかを判定する
     * @param player 対象のプレイヤー
     * @return 受け取っていればtrue、そうでなければfalse
     */
    public boolean hasPlayerReceivedInitialKit(Player player) {
        Boolean hasReceived = player.getPersistentDataContainer().get(hasReceivedKit, PersistentDataType.BOOLEAN);
        return hasReceived != null && hasReceived;
    }
}
