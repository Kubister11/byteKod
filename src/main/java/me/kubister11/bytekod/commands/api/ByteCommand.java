package me.kubister11.bytekod.commands.api;

import me.kubister11.bytekod.storage.files.Config;
import me.kubister11.bytekod.utils.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class ByteCommand extends org.bukkit.command.Command {
    private final String permission;
    private final String correctUse;
    private final boolean onlyForPlayer;


    public ByteCommand(String command, List<String> aliases, String correctUse, String permission, boolean onlyForPlayer) {
        super(command, "", "", aliases);
        this.permission = permission;
        this.onlyForPlayer = onlyForPlayer;
        this.correctUse = correctUse;

        try {
            CommandMap cmap = Bukkit.getCommandMap();
            cmap.register("byteKod",this);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        boolean player = sender instanceof Player;
        if (onlyForPlayer && !player) {
            return true;
        }
        if (!permission.isBlank() && !permission.isEmpty()) if (!sender.hasPermission(permission)) return true;

        boolean correct = execute(sender, player, args);
        if (!correct) {
            TextUtil.sendMessage(sender, Config.MESSAGES_COMMAND$CORRECT$USE.replace("[CMD]", correctUse));
        }
        return true;
    }

    public boolean execute(CommandSender sender, boolean player, String[] args) {
        return false;
    }

}
