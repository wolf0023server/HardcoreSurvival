package me.wolf0023.hardcoreSurvival.command;

import me.wolf0023.hardcoreSurvival.manager.GameStateManager;
import me.wolf0023.hardcoreSurvival.util.MessageUtil;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * キットコマンドを処理するクラス
 * プレイヤーに初回参加キットを配布する
 * /initialkit または /ik コマンドで実行可能
 * @param gameStateManager ゲーム状態管理のインスタンス
 */
public class KitCommand implements CommandExecutor {

    /** ゲーム状態管理のインスタンス */
    private final GameStateManager gameStateManager;

    /** コンストラクタ */
    public KitCommand(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // コマンド送信者がプレイヤーであることを確認
        if (!(sender instanceof Player)) {
            sender.sendMessage("このコマンドはプレイヤーのみ使用できます。");
            return true;
        }

        Player player = (Player) sender;

        // 引数がある場合、エラーメッセージを表示
        if (args.length > 0) {
            MessageUtil.sendMessage(player, "&c[Error] &f引数が多すぎます。");
            return true;
        }

        // 初回参加キットを配布
        // 配布に成功したかどうかのフラグ
        boolean flag_giveInitialKit = this.gameStateManager.giveInitialKit(player);

        // ゲーム開始されていないまたは、すでにキットを受け取っている場合、エラーメッセージを表示
        if (!flag_giveInitialKit) {
            MessageUtil.sendMessage(player, "&c[Error] &fゲームが開始されていないか、すでに受け取り済みです。");
        }

        return true;
    }
}

