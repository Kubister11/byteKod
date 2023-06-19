package me.kubister11.bytekod.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class Code {
    private final String code;
    private final List<String> commands;
    private final String receiveBroadcast;
    private final List<UUID> claimed;
}
