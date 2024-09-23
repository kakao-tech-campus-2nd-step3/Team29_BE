package notai.folder.presentation;

import lombok.RequiredArgsConstructor;
import notai.folder.application.FolderQueryService;
import notai.folder.application.FolderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/folders")
@RequiredArgsConstructor
public class FolderController {

    private final FolderService folderService;
    private final FolderQueryService folderQueryService;
}
