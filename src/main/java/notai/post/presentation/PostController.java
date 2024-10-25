package notai.post.presentation;

import lombok.RequiredArgsConstructor;
import notai.post.application.command.PostSaveCommand;
import notai.post.application.PostService;
import notai.post.application.result.PostFindResult;
import notai.post.application.result.PostSaveResult;
import notai.post.presentation.request.PostSaveRequest;
import notai.post.presentation.response.PostFindResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Void> savePost(
            @RequestBody PostSaveRequest postSaveRequest
    ) {
        PostSaveCommand postSaveCommand = postSaveRequest.toCommand();
        PostSaveResult postSaveResult = postService.savePost(postSaveCommand);
        String url = String.format("/api/post/%s", postSaveResult.id());
        return ResponseEntity.created(URI.create(url)).build();
    }

    @GetMapping(value = "/{postId}")
    public ResponseEntity<PostFindResponse> getPost(
            @PathVariable Long postId
    ) {
        PostFindResult postFindResult = postService.findPost(postId);
        PostFindResponse response = PostFindResponse.from(postFindResult);
        return ResponseEntity.ok(response);
    }

}
