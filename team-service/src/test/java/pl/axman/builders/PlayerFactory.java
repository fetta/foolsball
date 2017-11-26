package pl.axman.builders;

import org.springframework.stereotype.Component;
import pl.axman.domain.Player;

@Component
public class PlayerFactory {
  public Player create(String name) {
    Player player = new Player();
    player.setName(name);
    return player;
  }
}
