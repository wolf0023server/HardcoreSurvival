package me.wolf0023.hardcoreSurvival.manager;

import me.wolf0023.hardcoreSurvival.HardcoreSurvival;

import org.bukkit.entity.Player;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataType;

/**
 * プレイヤーを管理するクラス
 * 観戦モードの切り替えや、状態の保存などを担当する
 * @param plugin メインクラスのインスタンス
 */
public class PlayerManager {

    /** メインクラスのインスタンス */
    private final HardcoreSurvival plugin;
    private final NamespacedKey ghostModeKey;

    /** コンストラクタ */
    public PlayerManager(HardcoreSurvival plugin) {
        this.plugin = plugin;
        this.ghostModeKey = new NamespacedKey(plugin, "ghost_mode");
    }

    /**
     * 観戦モードの制約を適用する
     * ただし、観戦モードかどうかの判定は行わない
     * @param player 対象のプレイヤー
     */
    public void applyGhostModeRestrictions(Player player) {
        player.setGameMode(org.bukkit.GameMode.CREATIVE);
        return;
    }

    /**
     * 観戦モードの制約を解除する
     * ただし、観戦モードかどうかの判定は行わない
     * @param player 対象のプレイヤー
     */
    public void removeGhostModeRestrictions(Player player) {
        player.setGameMode(org.bukkit.GameMode.SURVIVAL);
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
}
