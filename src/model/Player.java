package model;

import java.util.UUID;

public class Player {
    private String uuid;

    public Player() {
        this.uuid = UUID.randomUUID().toString();
    }

    public String getUuid() {
        return uuid;
    }
}
