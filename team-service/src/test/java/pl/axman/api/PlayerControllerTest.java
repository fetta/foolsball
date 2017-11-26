package pl.axman.api;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.sun.media.jfxmedia.events.PlayerEvent;
import java.net.URI;
import java.util.List;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import pl.axman.builders.PlayerFactory;
import pl.axman.domain.Player;
import pl.axman.repository.PlayerRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PlayerControllerTest {
  @LocalServerPort
  private int port;
  private String baseUrl;

  @Autowired
  private TestRestTemplate restTemplate;

  @Autowired
  private PlayerFactory playerFactory;

  @Before
  public void setUp() throws Exception {
    baseUrl = "http://localhost:" + port + "/player";
  }

  @Test
  public void Should_GetAllPlayers() throws Exception {
    // given
    Player player1 = playerFactory.create("player1");
    player1 = restTemplate.postForObject(baseUrl, player1, Player.class);
    Player player2 = playerFactory.create("player2");
    player2 = restTemplate.postForObject(baseUrl, player2, Player.class);

    ParameterizedTypeReference<List<Player>> playersTypeReference =
        new ParameterizedTypeReference<List<Player>>() {};

    // when
    ResponseEntity<List<Player>> response = restTemplate.exchange(baseUrl,
        HttpMethod.GET, null, playersTypeReference);

    // then
    assertEquals(response.getStatusCode(), HttpStatus.OK);
    List<Player> playerList = response.getBody();
    assertTrue(playerList.contains(player1));
    assertTrue(playerList.contains(player2));

    // cleanup
    restTemplate.delete(baseUrl + "/" + player1.getId());
    restTemplate.delete(baseUrl + "/" + player2.getId());
  }

  @Test
  public void Should_GetPlayer() throws Exception {
    // given
    Player player1 = playerFactory.create("player1");
    player1 = restTemplate.postForObject(baseUrl, player1, Player.class);

    // when
    ResponseEntity<Player> response =
        restTemplate.getForEntity(baseUrl + "/" + player1.getId(), Player.class);

    // then
    assertEquals(response.getStatusCode(), HttpStatus.OK);
    assertEquals(response.getBody(), player1);

    // cleanup
    restTemplate.delete(baseUrl + "/" + player1.getId());
  }

  @Test
  public void Should_CreateValidPlayer() throws Exception {
    // given
    Player player = playerFactory.create("Zbyszek");

    // when
    ResponseEntity<Player> response =
        restTemplate.postForEntity(baseUrl, player, Player.class);

    // then
    assertEquals(HttpStatus.CREATED, response.getStatusCode());

    Player createdPlayer = response.getBody();
    assertNotNull(createdPlayer.getId());
    assertEquals("Zbyszek", createdPlayer.getName());

    // cleanup
    restTemplate.delete(baseUrl + "/" + player.getId());
  }

  @Test
  public void Should_GetBadRequest_When_CreatePlayerWithAssignedId() {
    // given
    Player player = playerFactory.create("Zbyszek");
    player.setId(1L);

    // when
    ResponseEntity<Player> response =
        restTemplate.postForEntity(baseUrl, player, Player.class);

    // then
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    assertNull(response.getBody());
  }

  @Test
  public void Should_UpdatePlayer() throws Exception {
    // given
    // saving a player and retrieving it so that it has assigned id
    Player player = playerFactory.create("Zbyszek");
    ResponseEntity<Player> saved = restTemplate.postForEntity(baseUrl, player, Player.class);

    // preparing a player to use in update request
    Player toUpdate = playerFactory.create("Zbigniew");
    toUpdate.setId(saved.getBody().getId());

    // when
    restTemplate.put(baseUrl + "/" + toUpdate.getId(), toUpdate, Player.class);

    // then
    Player retrieved = restTemplate.getForObject(baseUrl + "/" + toUpdate.getId(), Player.class);
    assertEquals("Zbigniew", retrieved.getName());
    assertEquals(toUpdate.getId(), retrieved.getId());
  }

  @Test
  public void Should_GetBadRequest_When_UpdatingNonexistentPlayer() throws Exception {
    // given
    Player player = playerFactory.create("Roman");
    player.setId(1L);
    RequestEntity<Player> request = new RequestEntity<>(player, HttpMethod.PUT,
        new URI(baseUrl + "/" + player.getId())
    );

    // when
    ResponseEntity<Void> response = restTemplate.exchange(request, Void.class);

    // then
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  @Test
  public void Should_GetBadRequest_When_PlayerIdDifferentInPathAndRequest() throws Exception {
    // given
    // saving a player and retrieving it so that it has assigned id
    Player player = playerFactory.create("Zbyszek");
    ResponseEntity<Player> saved = restTemplate.postForEntity(baseUrl, player, Player.class);

    RequestEntity<Player> request = new RequestEntity<>(saved.getBody(), HttpMethod.PUT,
        new URI(baseUrl + "/5")
    );

    // when
    ResponseEntity<Void> response = restTemplate.exchange(request, Void.class);

    // then
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

}
