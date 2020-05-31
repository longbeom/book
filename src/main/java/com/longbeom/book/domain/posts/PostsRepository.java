package com.longbeom.book.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts, Long> {
    @Query("SELECT posts FROM Posts posts ORDER BY posts.id DESC")
    List<Posts> findAllDesc();
}
