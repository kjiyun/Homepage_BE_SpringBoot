package kahlua.KahluaProject.repository;

import kahlua.KahluaProject.domain.user.Credential;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CredentialRepository extends JpaRepository<Credential, Long> {
}