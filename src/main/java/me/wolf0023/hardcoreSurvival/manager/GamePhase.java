package me.wolf0023.hardcoreSurvival.manager;

/**
 * ゲームのフェーズを表す列挙型
 * <ul>
 *  <li>FREE: 自由フェーズ - 通常のゲームプレイが可能</li>
 *  <li>HARDCORE: ハードコアフェーズ - 一度死んだら観戦</li>
 * </ul>
 */
public enum GamePhase {
    FREE("FREE"), // 自由フェーズ: 通常のゲームプレイが可能
    HARDCORE("HARDCORE"); // ハードコアフェーズ: 一度死んだら観戦モードになる

    /** フェーズの名前 */
    private final String phaseName;

    /** コンストラクタ */
    GamePhase(String phaseName) {
        this.phaseName = phaseName;
    }

    /**
     * フェーズの名前を取得する
     * @return フェーズの名前
     */
    public String getPhaseName() {
        return this.phaseName;
    }

    /**
     * 文字列から対応するGamePhaseを取得する
     * @param phaseName フェーズの名前
     * @param defaultPhase 見つからなかった場合に返すデフォルト
     * @return 対応するGamePhase、またはデフォルト
     */
    public static GamePhase fromString(
        String phaseName,
        GamePhase defaultPhase) {

        // nullチェック
        if (phaseName == null) {
            return defaultPhase;
        }

        for (GamePhase phase : GamePhase.values()) {
            // 大文字小文字を区別せずに比較
            if (phase.getPhaseName().equalsIgnoreCase(phaseName)) {
                return phase;
            }
        }

        return defaultPhase;
    }
}
