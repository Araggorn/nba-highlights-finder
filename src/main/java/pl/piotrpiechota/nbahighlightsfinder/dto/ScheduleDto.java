package pl.piotrpiechota.nbahighlightsfinder.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.piotrpiechota.nbahighlightsfinder.entity.Game;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ScheduleDto {

    private boolean played;
    private List<Game> games = new ArrayList<>();

    @SuppressWarnings("unchecked")
    @JsonProperty("data")
    private void unpackNestedData(Map<String, Object>[] data) {
        for (Map<String, Object> gameData : data) {
            Game tempGame = new Game();

            tempGame.setDate(Instant.parse((String) gameData.get("date")));
            tempGame.setHomeTeamScore((Integer) gameData.get("home_team_score"));
            tempGame.setVisitorTeamScore(((Integer) gameData.get("visitor_team_score")));

            Map<String, Object> homeTeamMap = (Map<String, Object>) gameData.get("home_team");
            tempGame.setHomeTeam((String) homeTeamMap.get("name"));

            Map<String, Object> visitorTeamMap = (Map<String, Object>) gameData.get("visitor_team");
            tempGame.setVisitorTeam((String) visitorTeamMap.get("name"));

            games.add(tempGame);
        }
    }

    @JsonProperty("meta")
    private void unpackNestedMeta(Map<String, Integer> meta) {
        Integer totalCount = meta.get("total_count");
        this.played = totalCount > 0;
    }

    public boolean wasPlayed() {
        return played;
    }

    public List<Game> getGames() {
        return games;
    }
}
