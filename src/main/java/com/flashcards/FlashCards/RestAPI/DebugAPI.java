package com.flashcards.FlashCards.RestAPI;

import com.flashcards.FlashCards.DB.Model.Category;
import com.flashcards.FlashCards.DB.Model.Flashcard;
import com.flashcards.FlashCards.DB.Repository.CategoryRepository;
import com.flashcards.FlashCards.DB.Repository.FlashcardsRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
public class DebugAPI {

    @Autowired
    FlashcardAPI flashcardAPI;

    @Autowired
    CategoryRepository categoryRepository;

    List<Category> categories;

    @PostConstruct
    public void init() {
        this.categories = categoryRepository.getAll();
    }

    @RequestMapping(value = {"/", "/flashcard"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String dashboard(@RequestParam(value = "category", required = false) String pathCategory){

        String index = categoryOptionList(categories, pathCategory);

        List<Flashcard> flashcards = flashcardAPI.byCategory(pathCategory);
        if(!flashcards.isEmpty()){
            Random random = new Random();
            int randomIndex = random.nextInt(flashcards.size());
            Flashcard flashcard = flashcards.get(randomIndex);
            index += "Category: " + flashcard.getCategoryName() + "</br>";
            index += "Question: " + flashcard.getQuestion() + "</br>";
            index += "Answer: <a href='/answer/" + flashcard.getId() + "'>[SHOW]</a></br>";

        }
        return index;
    }


    @GetMapping("/answer/{id}")
    public String answer(@PathVariable int id){
        Flashcard flashcard =  flashcardAPI.byId(id);

        Category matchedCategory = categories.stream()
                .filter(category -> category.getName().equals(flashcard.getCategoryName()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Category not found for flashcard"));

        String index = categoryOptionList(categories, matchedCategory.getPath() );
        index += "Category: " + flashcard.getCategoryName() + "</br>";
        index += "Question: " + flashcard.getQuestion() + "</br>";
        index += "Answer: " + flashcard.getAnswer()+"</br>" ;


        return index;
    }

    private String categoryOptionList(List<Category> categories, String selectedCategory) {
        if(categories.isEmpty()) return "";

        String index = "<form action='/flashcard' method='POST'>";
        index += "<label for='category'>Choose a category:</label>";
        index += "<select id='category' name='category'>";
        for(Category category : categories){
            if(category.getPath().equals(selectedCategory)) {
                index += "<option value='" + category.getPath() + "' selected>" + category.getName() + "</option>";
            } else {
                index += "<option value='" + category.getPath() + "'>" + category.getName() + "</option>";
            }
        }
        index += "</select>";
        index += "<button type='submit'>RAND NEW</button>";
        index += "</form>";
        return index;
    }
}
