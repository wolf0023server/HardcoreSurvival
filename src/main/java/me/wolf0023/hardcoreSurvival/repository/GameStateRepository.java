package me.wolf0023.hardcoreSurvival.repository;

import me.wolf0023.hardcoreSurvival.HardcoreSurvival;
import me.wolf0023.hardcoreSurvival.manager.GamePhase;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * ゲームの状態を管理するリポジトリクラス
 */
public class GameStateRepository {

    /** メインクラスのインスタンス */
    private final HardcoreSurvival plugin;

    /** ゲーム状態を保存するファイルコンフィグレーション */
    private FileConfiguration state;

    /** ゲーム状態を保存するファイル */
    private final File stateFile;

    /** ゲーム状態を保存するファイル名 */
    private static final String STATE_FILE_NAME = "state.yml";

    /* ゲーム状態のキー */
    /** ゲームフェーズのキー */
    public static final String GAME_PHASE_KEY = "phase";
    /** 死亡プレイヤーリストのキー */
    public static final String DEAD_PLAYERS_KEY = "deadPlayers";
    /** 初期キット受取済みプレイヤーリストのキー */
    public static final String RECEIVED_INITIAL_KIT_KEY = "recievedInitialKit";

    /** コンストラクタ */
    public GameStateRepository(HardcoreSurvival plugin) {
        this.plugin = plugin;
        this.stateFile = new File(this.plugin.getDataFolder(), STATE_FILE_NAME);

        this.loadState();
    }

    /**
     * ゲーム状態を取得する
     */
    private void loadState() {
        // ファイルが存在する場合はそのまま読み込む
        if (this.stateFile.exists()) {
            this.state = YamlConfiguration.loadConfiguration(this.stateFile);
            return;
        }

        // ファイルが存在しない場合リセットをかける
        this.resetGameState();
    }

    /**
     * ゲーム状態を保存する
     */
    private void saveState() {
        try {
            this.state.save(this.stateFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ゲーム状態をリセットする
     */
    public void resetGameState() {
        this.state = new YamlConfiguration();

        this.state.set(GAME_PHASE_KEY, GamePhase.FREE.getPhaseName());
        this.state.set(DEAD_PLAYERS_KEY, new ArrayList<String>());
        this.state.set(RECEIVED_INITIAL_KIT_KEY, new ArrayList<String>());

        this.saveState();
    }

    /**
     * ゲーム状態の取得
     * ただし、状態が存在しない場合はFREEフェーズを返す
     * @return 現在のゲームフェーズ
     */
    public GamePhase getGamePhase() {
        String phaseString = this.state.getString(GAME_PHASE_KEY);
        return GamePhase.fromString(phaseString, GamePhase.FREE);
    }

    /**
     * ゲーム状態の設定
     * @param phase 新しいゲームフェーズ
     */
    public void setGamePhase(GamePhase phase) {
        this.state.set(GAME_PHASE_KEY, phase.getPhaseName());
        this.saveState();
    }

    /**
     * 指定したプレイヤーを死亡リストに追加する
     * @param UUID プレイヤーのUUID
     */
    public void addDeadPlayer(UUID uuid) {
        List<String> deadPlayers = this.state.getStringList(DEAD_PLAYERS_KEY);
        String uuidString = uuid.toString();

        // 既にリストに存在する場合は追加しない
        if (!deadPlayers.contains(uuidString)) {
            deadPlayers.add(uuidString);
            this.state.set(DEAD_PLAYERS_KEY, deadPlayers);
            this.saveState();
        }
    }

    /**
     * 指定したプレイヤーを死亡リストから削除する
     * @param UUID プレイヤーのUUID
     */
    public void removeDeadPlayer(UUID uuid) {
        List<String> deadPlayers = this.state.getStringList(DEAD_PLAYERS_KEY);
        String uuidString = uuid.toString();

        // リストに存在する場合のみ削除する
        if (deadPlayers.remove(uuidString)) {
            this.state.set(DEAD_PLAYERS_KEY, deadPlayers);
            this.saveState();
        }
    }

    /**
     * 指定したプレイヤーが死亡リストに存在するか確認する
     * @param UUID プレイヤーのUUID
     * @return 存在する場合はtrue、存在しない場合はfalse
     */
    public boolean isPlayerDead(UUID uuid) {
        List<String> deadPlayers = this.state.getStringList(DEAD_PLAYERS_KEY);
        String uuidString = uuid.toString();

        return deadPlayers.contains(uuidString);
    }

    /**
     * 指定したプレイヤーを初回参加キット受取済みリストに追加する
     * @param UUID プレイヤーのUUID
     */
    public void addReceivedInitialKit(UUID uuid) {
        List<String> receivedPlayers = this.state.getStringList(RECEIVED_INITIAL_KIT_KEY);
        String uuidString = uuid.toString();

        // 既にリストに存在する場合は追加しない
        if (!receivedPlayers.contains(uuidString)) {
            receivedPlayers.add(uuidString);
            this.state.set(RECEIVED_INITIAL_KIT_KEY, receivedPlayers);
            this.saveState();
        }
    }

    /**
     * 指定したプレイヤーを初回参加キット受取済みリストから削除する
     * @param UUID プレイヤーのUUID
     */
    public void removeReceivedInitialKit(UUID uuid) {
        List<String> receivedPlayers = this.state.getStringList(RECEIVED_INITIAL_KIT_KEY);
        String uuidString = uuid.toString();

        // リストに存在する場合のみ削除する
        if (receivedPlayers.remove(uuidString)) {
            this.state.set(RECEIVED_INITIAL_KIT_KEY, receivedPlayers);
            this.saveState();
        }
    }

    /**
     * 指定したプレイヤーが初回参加キット受取済みリストに存在するか確認する
     * @param UUID プレイヤーのUUID
     * @return 存在する場合はtrue、存在しない場合はfalse
     */
    public boolean hasPlayerReceivedInitialKit(UUID uuid) {
        List<String> receivedPlayers = this.state.getStringList(RECEIVED_INITIAL_KIT_KEY);
        String uuidString = uuid.toString();

        return receivedPlayers.contains(uuidString);
    }
}
