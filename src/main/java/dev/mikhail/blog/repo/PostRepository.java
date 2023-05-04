package dev.mikhail.blog.repo;

import dev.mikhail.blog.models.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {
}
