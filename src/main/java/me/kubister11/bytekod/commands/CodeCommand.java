package me.kubister11.bytekod.commands;

import me.kubister11.bytekod.ByteKod;
import me.kubister11.bytekod.commands.api.ByteCommand;
import me.kubister11.bytekod.objects.Code;
import me.kubister11.bytekod.storage.files.Config;
import me.kubister11.bytekod.utils.TextUtil;
import me.kubister11.bytekod.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CodeCommand extends ByteCommand {
    public CodeCommand(String command, List<String> aliases, String correctUse, String permission, boolean onlyForPlayer) {
        super(command, aliases, correctUse, permission, onlyForPlayer);
    }

    @Override
    public boolean execute(CommandSender sender, boolean player, String[] args) {
        if (args.length != 1) return false;
        Player p = (Player) sender;
        String code = args[0];

        Code kod = ByteKod.getCodesManager().getByName(code);
        if (kod == null) {
            TextUtil.sendMessage(sender, Config.MESSAGES_CODE$NOT$FOUND);
            return true;
        }
        if (kod.getClaimed().contains(p.getUniqueId().toString())) {
            TextUtil.sendMessage(sender, Config.MESSAGES_ALREADY$RECEIVED);
            return true;
        }

        kod.getClaimed().add(p.getUniqueId().toString());
        Utils.runAsync(ByteKod.getInstance(), kod::update);
        kod.getCommands().forEach(s -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s.replace("[PLAYER]", p.getName())));
        TextUtil.sendMessage(sender, Config.MESSAGES_RECEIVED);
        Bukkit.broadcastMessage(TextUtil.fix(kod.getReceiveBroadcast().replace("[PLAYER]", p.getName())));

        return true;
    }
}
