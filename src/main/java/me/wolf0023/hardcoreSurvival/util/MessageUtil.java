package me.wolf0023.hardcoreSurvival.util;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.title.TitlePart;

import java.util.List;

/**
 * メッセージユーティリティクラス
 * プレイヤーやサーバーに対して様々な形式でメッセージを送信するための静的メソッドを提供する
 */
public class MessageUtil {
    /**
     * 指定したプレイヤーに、チャット欄にメッセージを送信する
     * @param player 送信先のプレイヤー
     * @param message 送信するメッセージ
     */
    public static void sendMessage(Player player, String message) {
        player.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(message));
    }

    /**
     * 指定したプレイヤーに、アクションバーにメッセージを送信する
     * @param player 送信先のプレイヤー
     * @param message 送信するメッセージ
     */
    public static void sendActionBar(Player player, String message) {
        player.sendActionBar(LegacyComponentSerializer.legacyAmpersand().deserialize(message));
    }

    /**
     * 指定したプレイヤーに、タイトルにメッセージを送信する
     * @param player 送信先のプレイヤー
     * @param title タイトルメッセージ
     */
    public static void sendTitle(Player player, String title, String subtitle) {
        player.sendTitlePart(
            TitlePart.TITLE,
            LegacyComponentSerializer.legacyAmpersand().deserialize(title)
        );
    }

    /**
     * サーバー全体に、メッセージを送信する
     * @param server 送信先のサーバー
     * @param message 送信するメッセージ
     */
    public static void broadcastMessage(Server server, String message) {
        server.broadcast(LegacyComponentSerializer.legacyAmpersand().deserialize(message));
    }

    /**
     * 指定したプレイヤーに、観戦モードへの切り替えを通知する
     * <br>
     * 送り先: チャット欄
     * @param player 送信先のプレイヤー
     */
    public static void sendGhostModeEnabledMessage(Player player) {
        String message = "&b[Info] &aあなたは観戦モードになりました。";
        sendMessage(player, message);
    }

    /**
     * 指定したプレイヤーに、観戦モードの解除を通知する
     * <br>
     * 送り先: チャット欄
     * @param player 送信先のプレイヤー
     */
    public static void sendGhostModeDisabledMessage(Player player) {
        String message = "&b[Info] &cあなたは観戦モードが解除されました。";
        sendMessage(player, message);
    }

    /**
     * 指定したプレイヤーに、観戦モードであることを通知する
     * <br>
     * 送り先: チャット欄
     * @param player 送信先のプレイヤー
     */
    public static void sendGhostModeMessage(Player player) {
        String message = "&b[Info] &fあなたは現在観戦モードです。";
        sendMessage(player, message);
    }

    /**
     * 指定したプレイヤーに、観戦モードの制限メッセージを通知する
     * <br>
     * 送り先: アクションバー
     * @param player 送信先のプレイヤー
     */
    public static void showGhostModeRestriction(Player player) {
        String message = "&c観戦モードのため、この操作はできません。";
        sendActionBar(player, message);
    }

    /**
     * 指定したプレイヤーに、初回参加キット配布メッセージを送信する
     * <br>
     * 送り先: チャット欄
     * @param player 送信先のプレイヤー
     */
    public static void sendFirstJoinKitMessage(Player player) {
        String message = "&b[Info] &a初回参加キットを配布しました。";
        String message2 = "&b[Info] &a目標はエンダードラゴン討伐です！頑張ってください！！";

        sendMessage(player, message);
        sendMessage(player, message2);
    }

    /**
     * 指定したプレイヤーに、権限がない旨のメッセージを送信する
     * <br>
     * 送り先: チャット欄
     * @param player 送信先のプレイヤー
     */
    public static void sendNoPermissionMessage(Player player) {
        String message = "&c[Error] &c権限がありません。";
        sendMessage(player, message);
    }

    /**
     * ゲーム開始メッセージをサーバー全体に送信する
     * <br>
     * 送り先: サーバー全体
     * @param server 送信先のサーバー
     */
    public static void broadcastGameStartMessage(Server server) {
        String message = "&6[Broadcast] &f&lゲームが開始されました！";
        String message2 = "&6[Broadcast] &f&l`/kit` で初回参加キットを受け取れます！";
        broadcastMessage(server, message);
        broadcastMessage(server, message2);
    }

    /**
     * ゲーム終了メッセージをサーバー全体に送信する
     * <br>
     * 送り先: サーバー全体
     * @param server 送信先のサーバー
     */
    public static void broadcastGameEndMessage(Server server) {
        String message = "&6[Broadcast] &f&lゲームが終了しました！お疲れ様でした！";
        broadcastMessage(server, message);
    }

    /**
     * ゲーム強制終了メッセージをサーバー全体に送信する
     * <br>
     * 送り先: サーバー全体
     * @param server 送信先のサーバー
     */
    public static void broadcastGameForceEndMessage(Server server) {
        String message = "&6[Broadcast] &f&lゲームが強制終了されました。";
        broadcastMessage(server, message);
    }

    /**
     * 指定したプレイヤーに、コマンドのヘルプメッセージを送信する
     * <br>
     * 送り先: チャット欄
     * @param player 送信先のプレイヤー
     */
    public static void sendCommandHelpMessage(Player player) {
        List<String> helpMessages = List.of(
            "",
            "&6[Help] &f/hcs &7- このヘルプを表示します。",
            "&6[Help] &f/hcs start &7- ゲームを開始します。",
            "&6[Help] &f/hcs end &7- ゲームを終了します。",
            "&6[Help] &f/hcs reset &7- ゲームをリセットします。",
            "&6[Help] &f/hcs ghost &7- 自分のモードを表示します。",
            "&6[Help] &f/hcs ghost enable &7- 自分の観戦モードを有効にします。",
            "&6[Help] &f/hcs ghost disable &7- 自分の観戦モードを無効にします。",
            ""
        );

        for (String message : helpMessages) {
            sendMessage(player, message);
        }
    }
}

