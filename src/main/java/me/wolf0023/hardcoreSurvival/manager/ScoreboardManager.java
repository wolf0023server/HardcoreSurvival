package me.wolf0023.hardcoreSurvival.manager;

import me.wolf0023.hardcoreSurvival.model.PlayerStats;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Criteria;
import net.kyori.adventure.text.Component;

import java.util.Map;
import java.util.HashMap;

import java.util.UUID;

/**
 * スコアボード管理クラス
 * プレイヤーのスコアボードを管理・更新する
 */
public class ScoreboardManager {

    /** スコアボード一覧 */
    private final Map<UUID, Scoreboard> playerScoreboards = new HashMap<>();

    /** 秒から時間への変換定数 */
    private static final int SECONDS_IN_HOUR = 3600;

    /** コンストラクタ */
    public ScoreboardManager() {
    }

    /**
     * スコアボードの作成
     * @param player スコアボードを作成するプレイヤー
     */
    public void createScoreboard(Player player) {
        // スコアボードの初期設定
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("hardcoreSurvival", Criteria.DUMMY, Component.text("§c§lHardcore Survival"));
        objective.setDisplaySlot(org.bukkit.scoreboard.DisplaySlot.SIDEBAR);

        // スコアボードをプレイヤーに適応
        player.setScoreboard(scoreboard);
        this.playerScoreboards.put(player.getUniqueId(), scoreboard);
    }

    /**
     * スコアボードの削除
     * @param player スコアボードを削除するプレイヤー
     */
    public void removeScoreboard(Player player) {
        this.playerScoreboards.remove(player.getUniqueId());
    }

    /**
     * スコアボードの更新
     * @param player スコアボードを更新するプレイヤー
     * @param statistics 更新する統計情報
     */
    public void updateScoreboard(Player player, PlayerStats statistics) {
        Scoreboard scoreboard = this.playerScoreboards.get(player.getUniqueId());
        if (scoreboard == null) {
            return; // スコアボードが存在しない場合は何もしない
        }
        Objective objective = scoreboard.getObjective("hardcoreSurvival");
        if (objective == null) {
            return; // スコアボード上の Objective が存在しない場合は何もしない
        }

        // スコアボードのクリア
        for (String entry : scoreboard.getEntries()) {
            scoreboard.resetScores(entry);
        }

        // スコアボードの内容を更新
        objective.getScore("§6--------------------").setScore(15);
        objective.getScore("§r ").setScore(14);
        objective.getScore("§bYour Name: §r" + player.getName()).setScore(13);
        objective.getScore("§r§r ").setScore(12);
        objective.getScore("§bGhost Mode: §r" + (statistics.isInGhostMode() ? "§aEnabled" : "§cDisabled")).setScore(11);
        objective.getScore("§r§r§r ").setScore(10);
        objective.getScore("§bMob Kills: §r" + statistics.getMobKills()).setScore(9);
        objective.getScore("§r§r§r§r ").setScore(8);
        objective.getScore("§bPlayer Kills: §r" + statistics.getPlayerKills()).setScore(7);
        objective.getScore("§r§r§r§r§r ").setScore(6);
        objective.getScore("§bTimes Mined: §r" + statistics.getTimesMined()).setScore(5);
        objective.getScore("§r§r§r§r§r§r ").setScore(4);
        objective.getScore("§bTime Played: §r" + (statistics.getTimePlayed() / SECONDS_IN_HOUR) + " h").setScore(3);
        objective.getScore("§r§r§r§r§r§r§r ").setScore(2);
        objective.getScore("§6--------------------§r").setScore(1);
    }
}
