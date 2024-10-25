package notai.post.application;

import lombok.RequiredArgsConstructor;
import notai.member.domain.Member;
import notai.member.domain.MemberRepository;
import notai.post.application.command.PostSaveCommand;
import notai.post.application.result.PostFindResult;
import notai.post.application.result.PostSaveResult;
import notai.post.domain.Post;
import notai.post.domain.PostRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public PostSaveResult savePost(PostSaveCommand postSaveCommand) {
        Member member = memberRepository.getById(postSaveCommand.memberId());
        Post post = new Post(member, postSaveCommand.title(), postSaveCommand.content());
        Post savedPost = postRepository.save(post);
        return PostSaveResult.of(savedPost.getId(), savedPost.getTitle());
    }

    public PostFindResult findPost(Long postId) {
        Post post = postRepository.findById(postId).get();
        return PostFindResult.of(post);
    }
}
