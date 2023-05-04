package dev.mikhail.blog.controllers;

import dev.mikhail.blog.models.Post;
import dev.mikhail.blog.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@Controller
public class BlogController {

    @Autowired
    private PostRepository postRepository;


    @GetMapping("/blog")
    public String blogMain(Model model) {
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts",posts);
        return "blog-main";
    }

    @GetMapping("/blog/add")
    public String blogAdd(Model model) {
        return "blog-add";
    }

    @PostMapping("/blog/add")
    public String blogPostAdd(@RequestParam String title, @RequestParam String anons,@RequestParam String full_text, Model model) {
        Post post = new Post(title,anons,full_text);
        postRepository.save(post);
        return "redirect:/blog";
    }

    @GetMapping("/blog/{id}")
    public String blogDetails(@PathVariable(value = "id") Long id, Model model) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isEmpty())
            return "redirect:/blog";
        model.addAttribute("post", post.get());
        return "blog-details";
    }

    @GetMapping("/blog/{id}/edit")
    public String blogDetailsToEdit(@PathVariable(value = "id") Long id, Model model) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isEmpty())
            return "redirect:/blog";
        model.addAttribute("post", post.get());
        return "blog-edit";
    }

    @PostMapping("/blog/{id}/edit")
    public String blogPostUpdate(@RequestParam String title, @RequestParam String anons,@RequestParam String full_text, @PathVariable(value = "id") Long id, Model model) {
        Post post = postRepository.findById(id).orElseThrow();
        post.setTitle(title);
        post.setAnons(anons);
        post.setFullText(full_text);
        postRepository.save(post);

        return "redirect:/blog";
    }

    @PostMapping("/blog/{id}/remove")
    public String blogPostDelete(@PathVariable(value = "id") Long id, Model model) {
        Post post = postRepository.findById(id).orElseThrow();
        postRepository.delete(post);

        return "redirect:/blog";
    }
}
