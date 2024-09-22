package notai.folder.domain;

import notai.folder.query.FolderQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FolderRepository extends JpaRepository<Folder, Long>, FolderQueryRepository {
}
