package me.kubister11.bytekod.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ChatInputRequest {
    private final ChatInputType chatInputType;
    private final Code code;
}
