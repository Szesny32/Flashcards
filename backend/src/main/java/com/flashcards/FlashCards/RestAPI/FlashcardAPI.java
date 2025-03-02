package com.flashcards.FlashCards.RestAPI;

import com.flashcards.FlashCards.DB.Model.Flashcard;
import com.flashcards.FlashCards.DB.Repository.FlashcardsRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RestController
public class FlashcardAPI {
    @Autowired
    FlashcardsRepository flashcardsRepository;

    @RequestMapping(value = "/api/flashcards/", method = {RequestMethod.GET, RequestMethod.POST})
    public List<Flashcard> byCategory(@RequestParam(value = "category", required = false) String pathCategory) {
        return flashcardsRepository.findByCategory(pathCategory);
    }

    @PostMapping("/api/addFlashcard")
    public boolean AddFlashcard(@ModelAttribute("flashcard") Flashcard flashcard) {
        return flashcardsRepository.save(flashcard);
    }

    @PostMapping("/api/updateFlashcard")
    public boolean UpdateFlashcard(@ModelAttribute("flashcard") Flashcard flashcard) {
        return flashcardsRepository.update(flashcard);
    }


    @GetMapping("/api/flashcard/{id}")
    public Flashcard findById(@PathVariable int id) {
        return flashcardsRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Failed to retrieve flashcard"));
    }

    @GetMapping("/api/deleteFlashcard/{id}")
    public boolean deleteById(@PathVariable int id) {
        return flashcardsRepository.deleteById(id);
    }

}
