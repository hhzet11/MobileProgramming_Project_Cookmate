package com.example.tts;

public enum PlayState {
    PLAY("재생 중"), WAIT("일시정지"), STOP("멈춤");

    private String state;

    PlayState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public boolean isStopping() {
        return this == STOP;
    }

    public boolean isWaiting() {
        return this == WAIT;
    }

    public boolean isPlaying() {
        return this == PLAY;
    }
}