package com.android.shooshoo.models;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GameMasterResult {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private GameMaster gameMaster;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public GameMaster getGameMaster() {
        return gameMaster;
    }

    public void setGameMaster(GameMaster data) {
        this.gameMaster = data;
    }

}