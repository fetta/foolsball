package pl.axman.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
@JsonIgnoreProperties(ignoreUnknown=true)
public class Player {
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Long id;
  @Column
  private String name;
}
