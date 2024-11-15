package notai.folder.application;

import notai.folder.application.result.FolderFindResult;
import notai.folder.domain.Folder;
import notai.folder.domain.FolderRepository;
import notai.member.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FolderQueryServiceTest {

    @Mock
    private FolderRepository folderRepository;
    @InjectMocks
    private FolderQueryService folderQueryService;

    @Mock
    private Member member;

    @BeforeEach
    void setUp() {
        given(member.getId()).willReturn(1L);
    }

    @Test
    @DisplayName("루트 폴더 조회")
    void getFolders_success_parentFolderIdIsNull() {
        //given
        Folder folder = getFolder(1L, null, "루트폴더");
        List<Folder> expectedResults = List.of(folder);

        when(folderRepository.findAllByMemberIdAndParentFolderIsNull(any(Long.class))).thenReturn(expectedResults);
        //when
        List<FolderFindResult> folders = folderQueryService.getFolders(member.getId(), null);

        Assertions.assertThat(folders.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("계층적 구조의 폴더 조회")
    void getFolders_success_parentFolderId() {
        //given
        Folder folder1 = getFolder(1L, null, "루트폴더");
        Folder folder2 = getFolder(2L, folder1, "서브폴더");
        Folder folder3 = getFolder(3L, folder1, "서브폴더");
        List<Folder> expectedResults = List.of(folder2, folder3);

        when(folderRepository.findAllByMemberIdAndParentFolderId(any(Long.class), any(Long.class))).thenReturn(
                expectedResults);
        //when
        List<FolderFindResult> folders = folderQueryService.getFolders(member.getId(), 1L);

        Assertions.assertThat(folders.size()).isEqualTo(2);
    }

    private Folder getFolder(Long id, Folder parentFolder, String name) {
        Member member = mock(Member.class);
        Folder folder = spy(new Folder(member, name, parentFolder));
        lenient().when(folder.getId()).thenReturn(id);
        return folder;
    }
}
