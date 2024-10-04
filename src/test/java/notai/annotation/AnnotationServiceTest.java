package notai.annotation;

import notai.annotation.application.AnnotationQueryService;
import notai.annotation.application.AnnotationService;
import notai.annotation.presentation.AnnotationController;
import notai.annotation.presentation.request.CreateAnnotationRequest;
import notai.annotation.presentation.response.AnnotationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class AnnotationControllerTest {

    @Mock
    private AnnotationService annotationService;

    @Mock
    private AnnotationQueryService annotationQueryService;

    @InjectMocks
    private AnnotationController annotationController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(annotationController).build();
    }

    @Test
    void testCreateAnnotation_success() throws Exception {
        CreateAnnotationRequest request = new CreateAnnotationRequest(1, 100, 200, 300, 100, "<span class=\"bold\">굵은글씨</span>");
        LocalDateTime now = LocalDateTime.now();
        AnnotationResponse response = new AnnotationResponse(1L, 1L, 1, 100, 200, 300, 100, "<span class=\"bold\">굵은글씨</span>", now.toString(), now.toString());

        when(annotationService.createAnnotation(anyLong(), anyInt(), anyInt(), anyInt(), anyInt(), anyInt(), anyString()))
                .thenReturn(response);

        mockMvc.perform(post("/api/documents/1/annotations")
                        .contentType("application/json")
                        .content("{\"pageNumber\": 1, \"x\": 100, \"y\": 200, \"width\": 300, \"height\": 100, \"content\": \"<span class='bold'>굵은글씨</span>\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.pageNumber").value(1))
                .andExpect(jsonPath("$.x").value(100))
                .andExpect(jsonPath("$.y").value(200))
                .andExpect(jsonPath("$.width").value(300))
                .andExpect(jsonPath("$.height").value(100))
                .andExpect(jsonPath("$.content").value("<span class=\"bold\">굵은글씨</span>"));
    }

    @Test
    void testGetAnnotations_success() throws Exception {
        LocalDateTime now = LocalDateTime.now();

        // Mock 데이터 설정
        List<AnnotationResponse> responses = List.of(
                new AnnotationResponse(1L, 1L, 1, 100, 200, 300, 100, "<span class=\"bold\">굵은글씨</span> 그냥 글씨 <span class=\"italic\">이탤릭체</span>", now.toString(), now.toString()),
                new AnnotationResponse(2L, 1L, 2, 150, 250, 350, 120, "", now.toString(), now.toString())
        );

        when(annotationQueryService.getAnnotationsByDocumentAndPageNumbers(anyLong(), anyList())).thenReturn(responses);

        mockMvc.perform(get("/api/documents/1/annotations?pageNumbers=1,2")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].pageNumber").value(1))
                .andExpect(jsonPath("$[0].x").value(100))
                .andExpect(jsonPath("$[0].y").value(200))
                .andExpect(jsonPath("$[0].width").value(300))
                .andExpect(jsonPath("$[0].height").value(100))
                .andExpect(jsonPath("$[0].content").value("<span class=\"bold\">굵은글씨</span> 그냥 글씨 <span class=\"italic\">이탤릭체</span>"))
                .andExpect(jsonPath("$[0].createdAt").value(now.toString()))
                .andExpect(jsonPath("$[0].updatedAt").value(now.toString()))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].pageNumber").value(2))
                .andExpect(jsonPath("$[1].x").value(150))
                .andExpect(jsonPath("$[1].y").value(250))
                .andExpect(jsonPath("$[1].width").value(350))
                .andExpect(jsonPath("$[1].height").value(120));
    }


    @Test
    void testUpdateAnnotation_success() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        AnnotationResponse response = new AnnotationResponse(1L, 1L, 1, 150, 250, 350, 120, "수정된 주석", now.toString(), now.toString());

        when(annotationService.updateAnnotation(anyLong(), anyLong(), anyInt(), anyInt(), anyInt(), anyInt(), anyString()))
                .thenReturn(response);

        mockMvc.perform(put("/api/documents/1/annotations/1")
                        .contentType("application/json")
                        .content("{\"pageNumber\": 1, \"x\": 150, \"y\": 250, \"width\": 350, \"height\": 120, \"content\": \"수정된 주석\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.pageNumber").value(1))
                .andExpect(jsonPath("$.content").value("수정된 주석"))
                .andExpect(jsonPath("$.x").value(150))
                .andExpect(jsonPath("$.y").value(250))
                .andExpect(jsonPath("$.width").value(350))
                .andExpect(jsonPath("$.height").value(120));
    }


    @Test
    void testDeleteAnnotation_success() throws Exception {
        doNothing().when(annotationService).deleteAnnotation(anyLong(), anyLong());

        mockMvc.perform(delete("/api/documents/1/annotations/1")
                        .contentType("application/json"))
                .andExpect(status().isNoContent());

        verify(annotationService, times(1)).deleteAnnotation(1L, 1L);
    }
}
