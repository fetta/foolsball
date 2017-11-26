package pl.axman.builders;

import org.springframework.stereotype.Component;
import pl.axman.domain.Player;
import pl.axman.domain.Team;

@Component
public class TeamFactory {
  public Team create(Player player1, Player player2, String name) {
    Team team = new Team();
    team.setPlayer1(player1);
    team.setPlayer2(player2);
    team.setName(name);
    return team;
  }
}
