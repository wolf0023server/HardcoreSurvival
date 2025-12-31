package me.wolf0023.hardcoreSurvival.manager;

import me.wolf0023.hardcoreSurvival.repository.GameStateRepository;
import me.wolf0023.hardcoreSurvival.util.MessageUtil;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.GameMode;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * ゲーム状態管理クラス
 * 観戦モードの切り替えや、状態の保存などを担当する
 */
public class GameStateManager {

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
    public GameStateManager(GameStateRepository gameStateRepository) {
        this.gameStateRepository = gameStateRepository;
    }

    /**
     * 観戦モードの適応を除外するかどうかを判定する
     * @param player 対象のプレイヤー
     * @return 除外する場合はtrue、そうでなければfalse
     */
    public boolean isExemptFromGhostMode(Player player) {
        // ゲームフェーズがFREEの場合は観戦モードの適応を除外する
        if (this.isGamePhase(GamePhase.FREE)) {
            return true;
        }

        // 除外権限を持っている場合は観戦モードの適応を除外する
        if (player.hasPermission("hcs.exempt")) {
            return true;
        }

        return false;
    }

    /**
     * 観戦モードの制約を適用する
     * ただし、観戦モードかどうかの判定は行わない
     * @param player 対象のプレイヤー
     */
    public void applyGhostModeRestrictions(Player player) {
        player.setGameMode(GameMode.CREATIVE);
    }

    /**
     * 観戦モードの制約を解除する
     * ただし、観戦モードかどうかの判定は行わない
     * @param player 対象のプレイヤー
     */
    public void removeGhostModeRestrictions(Player player) {
        player.setGameMode(GameMode.SURVIVAL);
    }

    /**
     * プレイヤーの観戦モードを有効にする
     * @param player 対象のプレイヤー
     * @return 観戦モードが有効になった場合はtrue, すでに有効になっていた場合はfalse
     */
    public boolean addGhostMode(Player player) {
        // すでに観戦モードの場合は何もしない
        if (this.isPlayerInGhostMode(player)) {
            return false;
        }

        // 観戦モードの除外
        if (this.isExemptFromGhostMode(player)) {
            return false;
        }

        this.applyGhostModeRestrictions(player);
        // 死亡者リストに追加
        this.gameStateRepository.addDeadPlayer(player.getUniqueId());
        return true;
    }

    /**
     * プレイヤーの観戦モードを無効する
     * @param player 対象のプレイヤー
     * @return 観戦モードが無効になった場合はtrue, すでに無効になっていた場合はfalse
     */
    public boolean removeGhostMode(Player player) {
        // すでに観戦モードでない場合は何もしない
        if (!this.isPlayerInGhostMode(player)) {
            return false;
        }

        this.removeGhostModeRestrictions(player);
        // 死亡者リストから削除
        this.gameStateRepository.removeDeadPlayer(player.getUniqueId());
        return true;
    }

    /**
     * プレイヤーが観戦モードかどうかを判定する
     * ただし、FREEフェーズの場合は常にfalseを返す
     * @param player 対象のプレイヤー
     * @return 観戦モードであればtrue、そうでなければfalse
     */
    public boolean isPlayerInGhostMode(Player player) {
        // 観戦モードの除外
        if (this.isExemptFromGhostMode(player)) {
            return false;
        }

        return this.gameStateRepository.isPlayerDead(player.getUniqueId());
    }

    /**
     * プレイヤーに初回参加キットを配布する
     * @param player 対象のプレイヤー
     * @return 配布に成功した場合はtrue、既に受け取っている場合やFREEフェーズの場合はfalse
     */
    public boolean giveInitialKit(Player player) {
        // FREEフェーズの場合はキットを配布しない
        if (this.isGamePhase(GamePhase.FREE)) {
            return false;
        }

        // 既に受け取っている場合は何もしない
        if (this.hasPlayerReceivedInitialKit(player)) {
            return false;
        }

        // 初回参加キットの配布
        for (ItemStack item : initialKit) {
            player.getInventory().addItem(item);
        }
        MessageUtil.sendFirstJoinKitMessage(player);

        // 受取済みリストに追加
        this.gameStateRepository.addReceivedInitialKit(player.getUniqueId());
        return true;
    }

    /**
     * プレイヤーが初回参加キットを受け取ったかどうかを判定する
     * @param player 対象のプレイヤー
     * @return 受け取っていればtrue、そうでなければfalse
     */
    public boolean hasPlayerReceivedInitialKit(Player player) {
        return this.gameStateRepository.hasPlayerReceivedInitialKit(player.getUniqueId());
    }

    /**
     * ゲームフェーズが入力されたフェーズかどうかを判定する
     * @param gamePhase 判定するゲームフェーズ
     * @return 一致していればtrue、そうでなければfalse
     */
    public boolean isGamePhase(GamePhase gamePhase) {
        return this.gameStateRepository.getGamePhase() == gamePhase;
    }

    /**
     * ゲームを開始する
     * @return 開始に成功した場合はtrue、既に開始している場合はfalse
     */
    public boolean startGame() {
        // 既にゲームが開始している場合は何もしない
        if (this.isGamePhase(GamePhase.HARDCORE)) {
            return false;
        }
        this.resetGame(); // ゲーム開始前に状態をリセットする
        this.gameStateRepository.setGamePhase(GamePhase.HARDCORE);
        return true;
    }

    /**
     * ゲームを終了する
     * @return 終了に成功した場合はtrue、既に終了している場合はfalse
     */
    public boolean endGame() {
        // 既にゲームが終了している場合は何もしない
        if (this.isGamePhase(GamePhase.FREE)) {
            return false;
        }

        this.gameStateRepository.setGamePhase(GamePhase.FREE);
        return true;
    }


    /**
     * ゲームをリセットする
     */
    public void resetGame() {
        this.gameStateRepository.resetGameState();
    }
}
