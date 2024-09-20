package notai.post.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface PostRepository extends JpaRepository<Post, BigInteger> {

}
