package me.wolf0023.hardcoreSurvival.command;

import me.wolf0023.hardcoreSurvival.HardcoreSurvival;
import me.wolf0023.hardcoreSurvival.manager.GameStateManager;
import me.wolf0023.hardcoreSurvival.util.MessageUtil;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * 全コマンドのクラス
 * <ul>
 *  <li>/hcs               - Hardcore Survivalのメインコマンド
 *  <li>/hcs start         - ゲームを開始する
 *  <li>/hcs end           - ゲームを強制終了する
 *  <li>/hcs reset         - ゲームをリセットする(通常はゲーム開始時にリセットされる)
 *  <li>/hcs ghost         - 自分の観戦モードを表示する
 *  <li>/hcs ghost enable  - 自分の観戦モードを有効にする
 *  <li>/hcs ghost disable - 自分の観戦モードを無効にする
 * </ul>
 * @param gameStateManager ゲーム状態管理のインスタンス
 */
public class HCSCommand implements CommandExecutor, TabCompleter {

    /** メインクラスのインスタンス */
    private final HardcoreSurvival plugin;

    /** ゲーム状態管理のインスタンス */
    private final GameStateManager gameStateManager;

    /** コンストラクタ */
    public HCSCommand(HardcoreSurvival plugin, GameStateManager gameStateManager) {
        this.plugin = plugin;
        this.gameStateManager = gameStateManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        // プレイヤー以外は実行不可
        if (!(sender instanceof Player)) {
            sender.sendMessage("このコマンドはプレイヤーのみ実行可能です。");
            return true;
        }

        Player player = (Player) sender;

        // 権限のチェック
        if (!player.hasPermission("hcs.admin")) {
            MessageUtil.sendNoPermissionMessage(player);
            return true;
        }

        // 引数がない場合、ヘルプメッセージを表示
        if (args.length == 0) {
            MessageUtil.sendCommandHelpMessage(player);
            return true;
        }

        // サブコマンドを処理
        if (args.length == 1) {
            switch (args[0].toLowerCase()) {
                case "start":
                    // 開始に成功したかどうかのフラグ
                    boolean flag_startGame = this.gameStateManager.startGame();

                    // 既にゲームが開始されている場合、エラーメッセージを表示
                    if (!flag_startGame) {
                        MessageUtil.sendMessage(player, "&c[Error] &f既にゲームが開始されています。");
                        break;
                    }
                    MessageUtil.broadcastGameStartMessage(this.plugin.getServer());
                    break;

                case "end":
                    // 終了に成功したかどうかのフラグ
                    boolean flag_endGame = this.gameStateManager.endGame();

                    // 既にゲームが開始されている場合、エラーメッセージを表示
                    if (!flag_endGame) {
                        MessageUtil.sendMessage(player, "&c[Error] &f既にゲームが開始されています。");
                        break;
                    }
                    MessageUtil.broadcastGameForceEndMessage(this.plugin.getServer());
                    break;

                case "reset":
                    this.gameStateManager.resetGame();
                    MessageUtil.sendMessage(player, "&b[Info] &fゲーム状態がリセットされました。");
                    break;

                case "ghost":
                    boolean isInGhostMode = this.gameStateManager.isPlayerInGhostMode(player);
                    MessageUtil.sendMessage(player, "&b[Info] &f観戦モードの状態: " + (isInGhostMode ? "&a有効" : "&c無効"));
                    break;

                default:
                    MessageUtil.sendCommandHelpMessage(player);
                    break;
            }
        } else if (args.length == 2 && args[0].equalsIgnoreCase("ghost")) {
            switch (args[1].toLowerCase()) {
                case "enable":
                    // 観戦モードへの追加に成功したかどうかのフラグ
                    boolean flag_addGhostMode = this.gameStateManager.addGhostMode(player);

                    // 既に観戦モードが有効な場合、エラーメッセージを表示
                    if (!flag_addGhostMode) {
                        MessageUtil.sendMessage(player, "&c[Error] &f既に観戦モードが有効になっているか、除外リストに登録されています。");
                        break;
                    }
                    MessageUtil.sendGhostModeEnabledMessage(player);
                    break;

                case "disable":
                    // 観戦モードからの削除に成功したかどうかのフラグ
                    boolean flag_removeGhostMode = this.gameStateManager.removeGhostMode(player);

                    // 既に観戦モードが無効な場合、エラーメッセージを表示
                    if (!flag_removeGhostMode) {
                        MessageUtil.sendMessage(player, "&c[Error] &f既に観戦モードが無効になっています。");
                        break;
                    }
                    MessageUtil.sendGhostModeDisabledMessage(player);
                    break;

                default:
                    MessageUtil.sendCommandHelpMessage(player);
                    break;
            }
        } else {
            // 引数が多すぎる場合、エラーメッセージを表示
            MessageUtil.sendMessage(player, "&c[Error] &f引数が長すぎます。");
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String alias, String[] args) {
        // プレイヤー以外は補完しない
        if (!(sender instanceof Player)) {
            return List.of();
        }

        // 権限のチェック
        Player player = (Player) sender;
        if (!player.hasPermission("hcs.admin")) {
            return List.of();
        }

        if (args.length == 1) {
            return List.of("start", "end", "reset", "ghost");
        } else if (args.length == 2 && args[0].equalsIgnoreCase("ghost")) {
            return List.of("enable", "disable");
        }
        return List.of();
    }
}
