package com.swm.ventybackend.content_rds;

import com.swm.ventybackend.collection.Collection;
import com.swm.ventybackend.collection.CollectionService;
import com.swm.ventybackend.comment.Comment;
import com.swm.ventybackend.comment.CommentService;
import com.swm.ventybackend.contact.Contact;
import com.swm.ventybackend.contact.ContactService;
import com.swm.ventybackend.feed.Feed;
import com.swm.ventybackend.feed.FeedService;
import com.swm.ventybackend.post.Post;
import com.swm.ventybackend.post.PostService;
import com.swm.ventybackend.users.Users;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/content")
@RequiredArgsConstructor
public class ContentController {

    private final ContentService contentService;
    private final PostService postService;
    private final FeedService feedService;
    private final ContactService contactService;
    private final CommentService commentService;
//    private final ReportService reportService;

    @PostMapping("/create")
    public String create(@RequestParam @Nullable String originalUrl, @Nullable String thumbnailUrl,
                         @Nullable Integer status, Integer isImageOrVideo, String extension, String size,
                         @Nullable Long postId, @Nullable Long feedId, @Nullable Long contactId,
                         @Nullable Long commentId, List<MultipartFile> multipartFiles) {

        // @TODO : 업로드된 파일 개수만큼 content DB 생성되어야 함 (이미지 5개, DB도 5개)
        List<String> fileNameList = contentService.uploadFile(multipartFiles);

        Content content = new Content();
        content.setOriginalUrl(fileNameList.get(0));
        content.setThumbnailUrl(fileNameList.get(1));

        // @TODO : 아래 3개 관리해줘야함
        content.setIsImageOrVideo(isImageOrVideo);
        content.setExtension(extension);
        content.setSize(size);

        if (status != null) content.setStatus(status);

        if (postId != null) {
            Post post = postService.findPostById(postId);
            content.setPost(post);
        }

        if (feedId != null) {
            Feed feed = feedService.findFeedById(feedId);
            content.setFeed(feed);
        }

        if (contactId != null) {
            Contact contact = contactService.findContactByContactId(contactId);
            content.setContact(contact);
        }

        if (commentId != null) {
            Comment comment = commentService.findCommentById(commentId);
            content.setComment(comment);
        } else {
            content.setComment(null);
        }

        Long contentId = contentService.saveContent(content);
        return contentId + "번 콘텐츠 등록 완료";
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam Long id) {
        contentService.removeContent(id);
        return id + "번 콘텐츠 삭제 완료";
    }

    @GetMapping("/findById")
    public String read(@RequestParam Long id) {
        return contentService.findContentById(id).toString();
    }

    @GetMapping("/all")
    public String readAll() { return contentService.findAllContent().toString(); }
}

