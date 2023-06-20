package me.kubister11.bytekod.commands;

import me.kubister11.bytekod.commands.api.ByteCommand;
import org.bukkit.command.CommandSender;

import java.util.List;

public class CodeCommand extends ByteCommand {
    public CodeCommand(String command, List<String> aliases, String correctUse, String permission, boolean onlyForPlayer) {
        super(command, aliases, correctUse, permission, onlyForPlayer);
    }

    @Override
    public boolean execute(CommandSender sender, boolean player, String[] args) {


        return true;
    }
}
