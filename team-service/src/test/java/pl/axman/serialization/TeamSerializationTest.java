package pl.axman.serialization;

import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.axman.builders.PlayerFactory;
import pl.axman.builders.TeamFactory;
import pl.axman.domain.Player;
import pl.axman.domain.Team;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class TeamSerializationTest {
  @Autowired
  private ObjectMapper mapper;
  @Autowired
  private TeamFactory teamFactory;
  @Autowired
  private PlayerFactory playerFactory;

  @Test
  public void should_serializeToJson() throws Exception {
    // given
    final Player player1 = playerFactory.create("player1");
    final Player player2 = playerFactory.create("player2");
    final Team team = teamFactory.create(player1, player2, "teamName");

    // when
    String serialized = mapper.writeValueAsString(team);
    Team deserialized = mapper.readValue(serialized, Team.class);

    // then
    assertEquals("teamName", deserialized.getName());
    assertEquals("player1", deserialized.getPlayer1().getName());
    assertEquals("player2", deserialized.getPlayer2().getName());
  }
}
