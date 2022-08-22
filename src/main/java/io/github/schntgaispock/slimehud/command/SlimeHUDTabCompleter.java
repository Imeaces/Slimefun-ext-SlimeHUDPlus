package io.github.schntgaispock.slimehud.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

/**
 * Tab completion for the '/slimehud' command
 */
public class SlimeHUDTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> hints = new ArrayList<>();
        
        if (args.length == 1) {
            hints.add("toggle");
            hints.add("enable");
            hints.add("disable");
            return hints;
        }

        if (args.length == 2) {
            hints.add("[PlayerName]");
            return hints;
        }

        return hints;
    }
    
}
