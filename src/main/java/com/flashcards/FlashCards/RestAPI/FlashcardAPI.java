package com.flashcards.FlashCards.RestAPI;

import com.flashcards.FlashCards.DB.Model.Flashcard;
import com.flashcards.FlashCards.DB.Repository.FlashcardsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Optional;

@RestController
public class FlashcardAPI {
    @Autowired
    FlashcardsRepository flashcardsRepository;

    @GetMapping({"", "/", "flashcard"})
    public String dashboard(){
        Flashcard flashcard =  flashcardsRepository.getRandomFlashcard().orElseThrow(() ->
                new RuntimeException("Failed to retrieve flashcard"));

        String index = "Category: " + flashcard.getCategoryName()+"</br>Question: " + flashcard.getQuestion() + "</br>" ;
        index += "<a href='/answer" + flashcard.getId() + "'>Show Answer</a>";
        return index;
    }

    @GetMapping("/answer{id}")
    public String answer(@PathVariable int id){
        Flashcard flashcard =  flashcardsRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Failed to retrieve flashcard"));

        String index = "Category: " + flashcard.getCategoryName()+"</br>Question: " + flashcard.getQuestion() + "</br>" ;
         index += "Answer: " + flashcard.getAnswer()+"</br>" ;
        index += "<a href='/flashcard'>New Flashcard</a>";
        return index;
    }



}
