package lee.joohan.thejavamockito.repository;

import lee.joohan.thejavamockito.domain.Study;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyRepository extends JpaRepository<Study, Long> {

}
