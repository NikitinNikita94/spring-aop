package t1.springaop.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import t1.springaop.model.entity.TrackTimeEntity;

import java.util.UUID;

public interface TrackTimeRepository extends JpaRepository<TrackTimeEntity, UUID> {
}
