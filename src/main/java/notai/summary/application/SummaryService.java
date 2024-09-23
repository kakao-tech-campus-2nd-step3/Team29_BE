package notai.summary.application;

import lombok.RequiredArgsConstructor;
import notai.summary.domain.SummaryRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SummaryService {

    private final SummaryRepository summaryRepository;

}
