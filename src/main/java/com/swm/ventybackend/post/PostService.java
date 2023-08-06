package com.swm.ventybackend.post;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Long savePost(Post post) {
        postRepository.save(post);
        return post.getPostId();
    }

    public void removePost(Long id) { postRepository.remove(id); }

    public Post findPostById(Long id) { return postRepository.findById(id); }

    public Post findPostByTitle(String title) { return postRepository.findByTitle(title); }

    public List<Post> findAllPost() { return postRepository.findAll(); }

}
