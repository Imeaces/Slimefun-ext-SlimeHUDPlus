package io.github.schntgaispock.slimehud.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.schntgaispock.slimehud.SlimeHUD;

/**
 * Functionality for the '/slimehud'command
 */
public class SlimeHUDCommandExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length == 0) {
                sendInfo(player);
                return true;
            }

            UUID uuid = player.getUniqueId();

            // May add more to the command in the future
            switch (args[0]) {
                case "toggle":
                    if (!player.hasPermission("slimehud.togglewaila")) {
                        player.sendMessage("§a§lSlimeHUD§7> §cYou don't have permission to toggle your WAILA HUD!");
                        return true;
                    }
                    if (SlimeHUD.getInstance().getConfig().getBoolean("waila.disabled", false)) {
                        player.sendMessage("§a§lSlimeHUD§7> §cThe WAILA HUD is disabled!");
                        return true;
                    }
                    if (SlimeHUD.getInstance().getConfig().getList("waila.disabled-in", Collections.EMPTY_LIST).contains(player.getWorld().getName())) {
                        player.sendMessage("§a§lSlimeHUD§7> §cThe WAILA HUD is disabled in this world!");
                        return true;
                    }
                    boolean wailaOn = SlimeHUD.getInstance().getPlayerData().getBoolean(uuid + ".waila", false);
                    SlimeHUD.getInstance().getPlayerData().set(uuid + ".waila", !wailaOn);

                    Map<UUID, PlayerWAILA> wailas = WAILAManager.getInstance().getWailas();
                    wailas.get(uuid).setPaused(wailaOn);

                    SlimeHUD.getInstance().getPlayerData().save();
                    player.sendMessage("§a§l粘液方块信息§7> 粘液准星方块信息显示已设置为 " + (wailaOn ? "§c关闭" : "§a开启"));
                    return true;
                case "enable":
                    if (args.length == 1) {
                        SlimeHUD.getInstance().getPlayerData().set(uuid + ".waila", true);
                        SlimeHUD.getInstance().getPlayerData().save();
                        player.sendMessage("§a§l粘液方块信息§7> 粘液准星方块信息显示已设置为 §a开启");
                        return true;
                    } else if (args.length == 2) {
                        if (!sender.hasPermission("slimehud.admin.togglewaila")) {
                            sender.sendMessage("§a§l粘液方块信息§7> 你没有权限");
                            return false;
                        }
                        //根据玩家名获取uuid
                        Player targetPlayer = Bukkit.getPlayer(args[1]);
                        if (targetPlayer == null) {
                            player.sendMessage("§a§l粘液方块信息§7> 玩家不存在或不在线");
                            return false;
                        }
                        UUID uniqueId = targetPlayer.getUniqueId();
                        SlimeHUD.getInstance().getPlayerData().set(uniqueId + ".waila", true);
                        SlimeHUD.getInstance().getPlayerData().save();
                        player.sendMessage("§a§l粘液方块信息§7> 已将玩家"+args[1]+"粘液准星方块信息显示已设置为 §a开启");
                        return true;
                    } else {
                        player.sendMessage("§a§l粘液方块信息§7> 用法: /slimehud enable 或 /slimehud enable [PlayerName]");
                        return false;
                    }
                case "disable":
                    if (args.length == 1) {
                        SlimeHUD.getInstance().getPlayerData().set(uuid + ".waila", false);
                        SlimeHUD.getInstance().getPlayerData().save();
                        player.sendMessage("§a§l粘液方块信息§7> 粘液准星方块信息显示已设置为 §c关闭");
                        return true;
                    } else if (args.length == 2) {
                        if (!sender.hasPermission("slimehud.admin.togglewaila")) {
                            sender.sendMessage("§a§l粘液方块信息§7> 你没有权限");
                            return false;
                        }
                        //根据玩家名获取uuid
                        Player targetPlayer = Bukkit.getPlayer(args[1]);
                        if (targetPlayer == null) {
                            player.sendMessage("§a§l粘液方块信息§7> 玩家不存在或不在线");
                            return false;
                        }
                        UUID uniqueId = targetPlayer.getUniqueId();
                        SlimeHUD.getInstance().getPlayerData().set(uniqueId + ".waila", false);
                        SlimeHUD.getInstance().getPlayerData().save();
                        player.sendMessage("§a§l粘液方块信息§7> 已将玩家"+args[1]+"粘液准星方块信息显示已设置为 §c关闭");
                        return true;
                    } else {
                        player.sendMessage("§a§l粘液方块信息§7> 用法: /slimehud disable 或 /slimehud disable [PlayerName]");
                        return false;
                    }

                default:
                    break;
            }
        }else {
            if (args.length == 0) {
                sendInfo(sender);
                return true;
            }

            // May add more to the command in the future
            switch (args[0]) {
                case "enable":
                    if (args.length == 1) {
                        sender.sendMessage("§a§l粘液方块信息§7> &c控制台无法使用该命令");
                        return false;
                    } else if (args.length == 2) {
                        //根据玩家名获取uuid
                        Player targetPlayer = Bukkit.getPlayer(args[1]);
                        if (targetPlayer == null) {
                            sender.sendMessage("§a§l粘液方块信息§7> 玩家不存在或不在线");
                            return false;
                        }
                        UUID uniqueId = targetPlayer.getUniqueId();
                        SlimeHUD.getInstance().getPlayerData().set(uniqueId + ".waila", true);
                        SlimeHUD.getInstance().getPlayerData().save();
                        sender.sendMessage("§a§l粘液方块信息§7> 已将玩家"+args[1]+"粘液准星方块信息显示已设置为 §a开启");
                        return true;
                    } else {
                        sender.sendMessage("§a§l粘液方块信息§7> 用法: /slimehud enable 或 /slimehud enable [PlayerName]");
                        return false;
                    }
                case "disable":
                    if (args.length == 1) {
                        sender.sendMessage("§a§l粘液方块信息§7> &c控制台无法使用该命令");
                        return false;
                    } else if (args.length == 2) {
                        //根据玩家名获取uuid
                        Player targetPlayer = Bukkit.getPlayer(args[1]);
                        if (targetPlayer == null) {
                            sender.sendMessage("§a§l粘液方块信息§7> 玩家不存在或不在线");
                            return false;
                        }
                        UUID uniqueId = targetPlayer.getUniqueId();
                        SlimeHUD.getInstance().getPlayerData().set(uniqueId + ".waila", false);
                        SlimeHUD.getInstance().getPlayerData().save();
                        sender.sendMessage("§a§l粘液方块信息§7> 已将玩家"+args[1]+"粘液准星方块信息显示已设置为 §c关闭");
                        return true;
                    } else {
                        sender.sendMessage("§a§l粘液方块信息§7> 用法: /slimehud disable 或 /slimehud disable [PlayerName]");
                        return false;
                    }
                default:
                    break;
            }
        }

        return false;
    }

    private void sendInfo(Player player) {
        player.sendMessage(
                "",
                "§a§l粘液方块信息 §7- §2Version " + SlimeHUD.getInstance().getPluginVersion(),
                "§7------",
                "§a§lWiki §7- §2https://github.com/mxlt02/SlimeHUDPlus/wiki",
                "§a§lIssues §7- §2https://github.com/mxlt02/SlimeHUDPlus/issues",
                ""
        );
    }

    private void sendInfo(CommandSender sender) {
        sender.sendMessage(
                "",
                "§a§l粘液方块信息 §7- §2Version " + SlimeHUD.getInstance().getPluginVersion(),
                "§7------",
                "§a§lWiki §7- §2https://github.com/mxlt02/SlimeHUDPlus/wiki",
                "§a§lIssues §7- §2https://github.com/mxlt02/SlimeHUDPlus/issues",
                ""
        );
    }

}
