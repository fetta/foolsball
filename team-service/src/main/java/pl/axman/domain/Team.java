package pl.axman.domain;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
public class Team {
  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  private Long id;
  @Column
  private String name;
  @Basic
  private Date creationDate;

  @ManyToOne
  @JoinColumn(name = "player1")
  private Player player1;
  @ManyToOne
  @JoinColumn(name = "player2")
  private Player player2;

  public Team() {
    this.creationDate = Date.valueOf(LocalDate.now());
  }

  public Team(String name, Player player1, Player player2) {
    this.name = name;
    this.player1 = player1;
    this.player2 = player2;
    this.creationDate = Date.valueOf(LocalDate.now());
  }
}
