package com.swm.ventybackend.comment;

import com.swm.ventybackend.collection.Collection;
import com.swm.ventybackend.collection.CollectionService;
import com.swm.ventybackend.comment.Comment;
import com.swm.ventybackend.comment.CommentService;
import com.swm.ventybackend.feed.Feed;
import com.swm.ventybackend.feed.FeedService;
import com.swm.ventybackend.post.Post;
import com.swm.ventybackend.post.PostService;
import com.swm.ventybackend.users.Users;
import com.swm.ventybackend.users.UsersService;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final FeedService feedService;
    private final PostService postService;
    private final UsersService usersService;
    

    @PostMapping("/create")
    public String create(@RequestParam String context, @Nullable Long postId, @Nullable Long feedId,
                         @Nullable Long usersId){

        Comment comment = new Comment();
        comment.setContext(context);

        if (postId != null) {
            Post post = postService.findPostById(postId);
            comment.setPost(post);
        }

        if (feedId != null) {
            Feed feed = feedService.findFeedById(feedId);
            comment.setFeed(feed);
        }

        if (usersId != null) {
            Users users = usersService.findUsersById(usersId);
            comment.setUsers(users);
        }

        Long commentId = commentService.saveComment(comment);
        return commentId + "번 댓글 등록 완료";
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam Long id) {
        commentService.removeComment(id);
        return id + "번 피드 삭제 완료";
    }

//    @GetMapping("/findByIdOrTitle")
//    public String read(@RequestParam @Nullable Long id, String title) {
//        if(id != null) {
//            return commentService.findCommentById(id).toString();
//        } else {
//            return commentService.findCommentByTitle(title).toString();
//        }
//    }

    @GetMapping("/all")
    public String readAll() { return commentService.findAllComment().toString(); }
}

