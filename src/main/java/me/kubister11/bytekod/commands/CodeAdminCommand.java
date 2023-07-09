package me.kubister11.bytekod.commands;

import me.kubister11.bytekod.ByteKod;
import me.kubister11.bytekod.commands.api.ByteCommand;
import me.kubister11.bytekod.gui.ConfirmGui;
import me.kubister11.bytekod.gui.CreateGui;
import me.kubister11.bytekod.objects.Code;
import me.kubister11.bytekod.storage.files.Config;
import me.kubister11.bytekod.utils.TextUtil;
import me.kubister11.bytekod.utils.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CodeAdminCommand extends ByteCommand {
    public CodeAdminCommand(String command, List<String> aliases, String correctUse, String permission, boolean onlyForPlayer) {
        super(command, aliases, correctUse, permission, onlyForPlayer);
    }

    @Override
    public boolean execute(CommandSender sender, boolean player, String[] args) {
        if (args.length != 1) return false;
        Player p = (Player) sender;
        String code = args[0];

        Code kod = ByteKod.getCodesManager().getByName(code);
        if (kod == null) {
            ConfirmGui.open(p,"Tworzenie nowego kodu", () -> {
                Code created = new Code(code, new ArrayList<>(), "&8>> &7Gracz &f[P] &7odebrał nagrodę!", new ArrayList<>());
                ByteKod.getCodesManager().getCodes().add(created);
                Utils.runAsync(ByteKod.getInstance(), created::insert);
                CreateGui.open(p, created);
            });
        } else {
            CreateGui.open(p, kod);
        }

        return true;
    }
}
