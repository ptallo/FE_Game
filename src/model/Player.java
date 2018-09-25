package model;

import lombok.Getter;

import java.util.UUID;

@Getter
public class Player {
    private String uuid;

    Player() {
        this.uuid = UUID.randomUUID().toString();
    }
}
