package notai.post.presentation;

import lombok.RequiredArgsConstructor;
import notai.post.application.PostService;
import notai.post.application.result.PostSaveResult;
import notai.post.presentation.request.PostSaveRequest;
import notai.post.presentation.response.PostSaveResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostSaveResponse> savePost(
            @RequestBody PostSaveRequest postSaveRequest
    ) {
        PostSaveResult postSaveResult;
        postSaveResult = postService.savePost(postSaveRequest);
        PostSaveResponse response = PostSaveResponse.from(postSaveResult);
        String url = String.format("/api/post/%s", response.id());
        return ResponseEntity.created(URI.create(url)).body(response);
    }

}
