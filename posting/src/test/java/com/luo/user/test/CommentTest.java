package com.luo.user.test;

import com.luo.model.posting.entity.Comment;
import com.luo.posting.PostingApplication;
import com.luo.posting.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;

import java.util.Optional;

//@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PostingApplication.class})
public class CommentTest extends AbstractTransactionalTestNGSpringContextTests {
    @Autowired
    private CommentRepository commentRepository;

    @Test
    public void commentFindTest() {
        Iterable<Comment> all = commentRepository.findAll();
        System.out.println(all);
    }

    @Test
    public void commentSaveTest() {
    }

    @Test
    public void commentUpdateTest() {
        Comment comment = new Comment();
        comment.setPostId(1);
        Optional<Comment> one = commentRepository.findOne(Example.of(comment));
        if (one.isPresent()) {
            Comment comment1 = one.get();
            comment1.setPostId(2);
            comment1.setContent("test update");
            commentRepository.save(comment1);
        }
    }

    @Test
    public void commentUpdate2Test() {
        Comment build = Comment.builder().postId(1).build();
        Optional<Comment> one = commentRepository.findOne(Example.of(build));
        if (one.isPresent()) {
            Comment comment = one.get();
            String id = comment.getId();
            Comment comment1 = Comment.builder().parentId(id).build();
            one = commentRepository.findOne(Example.of(comment1));
            if (one.isPresent()) {
                Comment comment2 = one.get();
                System.out.println("                                          "+comment2);
            }
        }
    }
    @Test void commentDeleteTest() {
//        commentRepository.deleteById(1L);
    }


    @AfterSuite
    protected void testAfterSuite() {
        Iterable<Comment> all = commentRepository.findAll();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println(all);
    }
}
