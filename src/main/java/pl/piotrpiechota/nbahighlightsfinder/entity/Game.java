package pl.piotrpiechota.nbahighlightsfinder.entity;

import java.time.LocalDate;

public class Game {

    private String homeTeam;
    private String visitorTeam;
    private LocalDate date;
//    private boolean played;
//
//    public boolean isPlayed() {
//        return played;
//    }
//
//    public void setPlayed(boolean played) {
//        this.played = played;
//    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getVisitorTeam() {
        return visitorTeam;
    }

    public void setVisitorTeam(String visitorTeam) {
        this.visitorTeam = visitorTeam;
    }
}