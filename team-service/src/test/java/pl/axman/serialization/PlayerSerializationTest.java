package pl.axman.serialization;

import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.axman.TeamServiceApp;
import pl.axman.builders.PlayerFactory;
import pl.axman.domain.Player;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class PlayerSerializationTest {
  @Autowired
  private ObjectMapper mapper;
  @Autowired
  private PlayerFactory playerFactory;

  @Test
  public void should_serializeToJson() throws Exception {
    // given
    final String playerName = "JUZEK";
    Player player = playerFactory.create(playerName);

    // when
    String serialized = mapper.writeValueAsString(player);
    log.info("serialized to {}", serialized);
    Player deserialized = mapper.readValue(serialized, Player.class);

    // then
    assertEquals(playerName, deserialized.getName());
  }
}
