package com.flashcards.FlashCards.RestAPI;

import com.flashcards.FlashCards.DB.Model.Category;
import com.flashcards.FlashCards.DB.Model.Flashcard;
import com.flashcards.FlashCards.DB.Repository.CategoryRepository;
import com.flashcards.FlashCards.DB.Repository.FlashcardsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/api/flashcard/{id}")
    public Flashcard byId(@PathVariable int id){
        return flashcardsRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Failed to retrieve flashcard"));
    }

}
