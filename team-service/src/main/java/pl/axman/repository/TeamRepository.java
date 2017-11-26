package pl.axman.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import pl.axman.domain.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {

}
