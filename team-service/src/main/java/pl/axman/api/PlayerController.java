package pl.axman.api;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.axman.domain.Player;
import pl.axman.repository.PlayerRepository;

@RestController
@RequestMapping("/player")
@AllArgsConstructor
public class PlayerController {
  private PlayerRepository playerRepository;

  @GetMapping
  public ResponseEntity<List<Player>> getPlayers() {
    List<Player> playerList = playerRepository.findAll();
    return new ResponseEntity<>(playerList, HttpStatus.OK);
  }

  @GetMapping("/{playerId}")
  public ResponseEntity<Player> getPlayer(@PathVariable("playerId") Long playerId) {
    Player player = playerRepository.findOne(playerId);
    return new ResponseEntity<>(player, HttpStatus.OK);
  }

  @PutMapping(path = "/{playerId}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> updatePlayer(@PathVariable("playerId") Long playerId,
      @RequestBody Player player) {
    if (!playerId.equals(player.getId())) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    if (!playerRepository.exists(playerId)) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    playerRepository.save(player);
    return new ResponseEntity<>(HttpStatus.ACCEPTED);
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Player> createPlayer(@RequestBody Player player) {
    if (player.getId() != null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    Player savedPlayer = playerRepository.save(player);
    return new ResponseEntity<>(savedPlayer, HttpStatus.CREATED);
  }

  @DeleteMapping("/{playerId}")
  public ResponseEntity<Void> deletePlayer(@PathVariable("playerId") Long playerId) {
    playerRepository.delete(playerId);
    return new ResponseEntity<>(HttpStatus.ACCEPTED);
  }
}
