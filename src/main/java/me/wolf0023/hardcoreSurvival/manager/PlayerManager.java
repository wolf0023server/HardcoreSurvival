package me.wolf0023.hardcoreSurvival.manager;

import me.wolf0023.hardcoreSurvival.repository.GameStateRepository;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.GameMode;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * プレイヤーを管理するクラス
 * 観戦モードの切り替えや、状態の保存などを担当する
 */
public class PlayerManager {

    /** ゲーム状態リポジトリ */
    private final GameStateRepository gameStateRepository;

    /** 初期参加キットの内容 */
    private final ArrayList<ItemStack> initialKit = new ArrayList<>(
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


    /** コンストラクタ */
    public PlayerManager(GameStateRepository gameStateRepository) {
        this.gameStateRepository = gameStateRepository;
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

            // 死亡者リストから削除
            this.gameStateRepository.removeDeadPlayer(player.getUniqueId());
            player.sendMessage("観戦モードが解除されました！");
            return;
        }

        this.applyGhostModeRestrictions(player);

        // 死亡者リストに追加
        this.gameStateRepository.addDeadPlayer(player.getUniqueId());
        player.sendMessage("観戦モードになりました！");
    }

    /**
     * プレイヤーが観戦モードかどうかを判定する
     * @param player 対象のプレイヤー
     * @return 観戦モードであればtrue、そうでなければfalse
     */
    public boolean isPlayerInGhostMode(Player player) {
        return this.gameStateRepository.isPlayerDead(player.getUniqueId());
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
        for (ItemStack item : initialKit) {
            player.getInventory().addItem(item);
        }
        player.sendMessage("初回参加キットを受け取りました！");

        // 受取済みリストに追加
        this.gameStateRepository.addReceivedInitialKit(player.getUniqueId());
    }

    /**
     * プレイヤーが初回参加キットを受け取ったかどうかを判定する
     * @param player 対象のプレイヤー
     * @return 受け取っていればtrue、そうでなければfalse
     */
    public boolean hasPlayerReceivedInitialKit(Player player) {
        return this.gameStateRepository.hasPlayerReceivedInitialKit(player.getUniqueId());
    }
}
