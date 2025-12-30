package me.wolf0023.hardcoreSurvival.model;

/**
 * プレイヤーの統計情報を表すクラス
 * <ul>
 *  <li>mobKills: モブのキル数</li>
 *  <li>playerKills: プレイヤーのキル数</li>
 *  <li>timePlayed: プレイ時間</li>
 *  <li>timesMined: 鉱石採掘数</li>
 *  <li>isInGhostMode: プレイヤーが観戦モードかどうか</li>
 * </ul>
 */
public class PlayerStats {
    /** モブのキル数 */
    private int mobKills;

    /** プレイヤーのキル数 */
    private int playerKills;

    /** プレイ時間（秒） */
    private long timePlayed;

    /** 鉱石採掘数 */
    private int timesMined;

    /** プレイヤーが観戦モードかどうか */
    private boolean isInGhostMode;

    /** コンストラクタ */
    public PlayerStats() {
        this.mobKills = 0;
        this.playerKills = 0;
        this.timePlayed = 0L;
        this.timesMined = 0;
        this.isInGhostMode = false;
    }

    /**
     * モブのキル数を取得する
     * @return モブのキル数
     */
    public int getMobKills() {
        return mobKills;
    }

    /**
     * モブのキル数を設定する
     * @param mobKills モブのキル数
     */
    public void setMobKills(int mobKills) {
        this.mobKills = mobKills;
    }

    /**
     * プレイヤーのキル数を取得する
     * @return プレイヤーのキル数
     */
    public int getPlayerKills() {
        return playerKills;
    }

    /**
     * プレイヤーのキル数を設定する
     * @param playerKills プレイヤーのキル数
     */
    public void setPlayerKills(int playerKills) {
        this.playerKills = playerKills;
    }

    /**
     * プレイ時間を取得する
     * @return プレイ時間（秒）
     */
    public long getTimePlayed() {
        return timePlayed;
    }

    /**
     * プレイ時間を設定する
     * @param timePlayed プレイ時間（秒）
     */
    public void setTimePlayed(long timePlayed) {
        this.timePlayed = timePlayed;
    }

    /**
     * 鉱石採掘数を取得する
     * @return 鉱石採掘数
     */
    public int getTimesMined() {
        return timesMined;
    }

    /**
     * 鉱石採掘数を設定する
     * @param timesMined 鉱石採掘数
     */
    public void setTimesMined(int timesMined) {
        this.timesMined = timesMined;
    }

    /**
     * プレイヤーが観戦モードかどうかを取得する
     * @return 観戦モードの場合はtrue、そうでない場合はfalse
     */
    public boolean isInGhostMode() {
        return isInGhostMode;
    }

    /**
     * プレイヤーの観戦モードを設定する
     * @param inGhostMode 観戦モードの場合はtrue、そうでない場合はfalse
     */
    public void setInGhostMode(boolean inGhostMode) {
        isInGhostMode = inGhostMode;
    }
}
