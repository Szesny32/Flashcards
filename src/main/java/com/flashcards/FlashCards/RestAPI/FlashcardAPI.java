package com.flashcards.FlashCards.RestAPI;

import com.flashcards.FlashCards.DB.Model.Category;
import com.flashcards.FlashCards.DB.Model.Flashcard;
import com.flashcards.FlashCards.DB.Repository.CategoryRepository;
import com.flashcards.FlashCards.DB.Repository.FlashcardsRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.*;

@RestController
public class FlashcardAPI {
    @Autowired
    FlashcardsRepository flashcardsRepository;

    @RequestMapping(value = "/api/flashcards/", method = {RequestMethod.GET, RequestMethod.POST})
    public List<Flashcard> byCategory(@RequestParam(value = "category", required = false) String pathCategory){
        return flashcardsRepository.findByCategory(pathCategory);
    }

    @PostMapping("/api/addFlashcard")
    public boolean AddFlashcard(@ModelAttribute("flashcard") Flashcard flashcard){
        return flashcardsRepository.save(flashcard);
    }



    @GetMapping("/api/flashcard/{id}")
    public Flashcard findById(@PathVariable int id){
        return flashcardsRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Failed to retrieve flashcard"));
    }

    @GetMapping("/api/deleteFlashcard/{id}")
    public RedirectView deleteById(@PathVariable int id, HttpServletRequest request){
        flashcardsRepository.deleteById(id);
        String referer = request.getHeader("Referer");
        return new RedirectView(referer != null ? referer : "/");
    }

}
