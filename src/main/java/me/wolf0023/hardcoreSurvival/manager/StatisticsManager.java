package me.wolf0023.hardcoreSurvival.manager;

import me.wolf0023.hardcoreSurvival.repository.GameStateRepository;
import me.wolf0023.hardcoreSurvival.model.PlayerStats;

import org.bukkit.entity.Player;
import org.bukkit.Statistic;
import org.bukkit.Material;

import java.util.Set;
import java.util.EnumSet;

/**
 * プレイヤー情報を管理するクラス
 * 統計データの取得と、観戦モードかどうかの取得結果を提供する
 */
public class StatisticsManager {

    /** ゲーム状態リポジトリ */
    private final GameStateRepository gameStateRepository;

    /** 対象となる鉱石の種類 */
    private static final Set<Material> ORE_MATERIALS = EnumSet.of(
        Material.COAL_ORE,
        Material.IRON_ORE,
        Material.GOLD_ORE,
        Material.DIAMOND_ORE,
        Material.EMERALD_ORE,
        Material.LAPIS_ORE,
        Material.REDSTONE_ORE,
        Material.COPPER_ORE,
        Material.NETHER_QUARTZ_ORE,
        Material.NETHER_GOLD_ORE,
        Material.ANCIENT_DEBRIS,
        Material.DEEPSLATE_COAL_ORE,
        Material.DEEPSLATE_IRON_ORE,
        Material.DEEPSLATE_GOLD_ORE,
        Material.DEEPSLATE_DIAMOND_ORE,
        Material.DEEPSLATE_EMERALD_ORE,
        Material.DEEPSLATE_LAPIS_ORE,
        Material.DEEPSLATE_REDSTONE_ORE,
        Material.DEEPSLATE_COPPER_ORE
    );

    /** 1秒あたりのティック数 */
    private static final int TICKS_PER_SECOND = 20;

    /**
     * コンストラクタ
     * @param gameStateRepository ゲーム状態リポジトリのインスタンス
     */
    public StatisticsManager(GameStateRepository gameStateRepository) {
        this.gameStateRepository = gameStateRepository;
    }

    /**
     * プレイヤー情報を取得する
     * @param player プレイヤー情報を取得する対象のプレイヤー
     * @return プレイヤー情報のオブジェクト
     */
    public PlayerStats getPlayerStats(Player player) {
        PlayerStats stats = new PlayerStats();

        // データの取得
        stats.setInGhostMode(this.gameStateRepository.isPlayerDead(player.getUniqueId()));
        stats.setMobKills(player.getStatistic(Statistic.MOB_KILLS));
        stats.setPlayerKills(player.getStatistic(Statistic.PLAYER_KILLS));
        stats.setTimesMined(this.getOreMinedCount(player));
        stats.setTimePlayed(player.getStatistic(Statistic.PLAY_ONE_MINUTE) / TICKS_PER_SECOND);

        return stats;
    }

    /**
     * 鉱石の採掘数を取得する
     * @param player 鉱石の採掘数を取得する対象の
     * @return 鉱石の採掘数
     */
    private int getOreMinedCount(Player player) {
        int count = 0;

        // 鉱石の種類ごとに採掘数を取得して合計する
        for (Material ore : ORE_MATERIALS) {
            count += player.getStatistic(Statistic.MINE_BLOCK, ore);
        }

        return count;
    }
}
