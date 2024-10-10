package notai.post.application;

import lombok.RequiredArgsConstructor;
import notai.member.domain.Member;
import notai.member.domain.MemberRepository;
import notai.post.application.result.PostSaveResult;
import notai.post.domain.Post;
import notai.post.domain.PostRepository;
import notai.post.presentation.request.PostSaveRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public PostSaveResult savePost(PostSaveRequest postSaveRequest) {
        Member member = memberRepository.getById(postSaveRequest.memberId());
        Post post = new Post(member, postSaveRequest.title(), postSaveRequest.content());
        Post savedPost = postRepository.save(post);
        return PostSaveResult.of(savedPost.getId(), savedPost.getTitle());
    }
}
