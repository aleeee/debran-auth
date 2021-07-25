package et.debran.debranauth.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import et.debran.debranauth.model.DebranRole;
import et.debran.debranauth.model.DebranUserRole;

@Repository
public interface DebranRolesRepository extends JpaRepository<DebranRole, Long>{
	Optional<DebranRole> findByName(DebranUserRole name);
}
