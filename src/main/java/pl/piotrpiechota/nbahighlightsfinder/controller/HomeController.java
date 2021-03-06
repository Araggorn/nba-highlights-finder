package pl.piotrpiechota.nbahighlightsfinder.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.piotrpiechota.nbahighlightsfinder.entity.Game;
import pl.piotrpiechota.nbahighlightsfinder.entity.Team;
import pl.piotrpiechota.nbahighlightsfinder.repository.TeamRepository;
import pl.piotrpiechota.nbahighlightsfinder.service.BallApiService;
import pl.piotrpiechota.nbahighlightsfinder.service.YoutubeService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    private final YoutubeService youtubeService;
    private final BallApiService ballApiService;
    private final TeamRepository teamRepo;
    private final int YESTERDAY = 1;

    public HomeController(YoutubeService youtubeService, BallApiService ballApiService, TeamRepository teamRepo) {
        this.youtubeService = youtubeService;
        this.ballApiService = ballApiService;
        this.teamRepo = teamRepo;
    }

    @ModelAttribute("teams")
    public List<Team> getTeams() {
        return teamRepo.findAll();
    }

    @RequestMapping("/")
    public String getHomepage(HttpServletRequest request, Model model) {
        String lastDay = LocalDate.now().minusDays(YESTERDAY).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        List<Game> scheduledGames = ballApiService.getGamesFromLastNight();

        request.getSession().setAttribute("schedule", scheduledGames);
        model.addAttribute("date", lastDay);
        return "home";
    }

    @RequestMapping("/game")
    public String getGame(@RequestParam Integer id, HttpSession session, Model model) {
        List<Game> scheduledGames;
        if (session.getAttribute("schedule") == null) {
            scheduledGames = new ArrayList<>();
        } else {
            scheduledGames = (List<Game>) session.getAttribute("schedule");
        }

        Game clickedGame = scheduledGames.get(id);
        List<String> videoId = youtubeService.executeSearch(clickedGame);

        model.addAttribute("video", videoId);
        model.addAttribute("game", clickedGame);
        return "game";
    }

    @RequestMapping("/about")
    public String getAbout() {
        return "about";
    }
}
