package notai.problem.application;

import lombok.RequiredArgsConstructor;
import notai.problem.domain.ProblemRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProblemService {

    private final ProblemRepository problemRepository;

}
