package com.flashcards.FlashCards.RestAPI;

import com.flashcards.FlashCards.DB.Model.Category;
import com.flashcards.FlashCards.DB.Model.Flashcard;
import com.flashcards.FlashCards.DB.Repository.CategoryRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
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
        refreshCategories();
    }

    @Scheduled(fixedRate = 10000)
    public void refreshCategories() {
        this.categories = categoryRepository.getAll();
    }

    @RequestMapping(value = {"/", "/flashcard"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String dashboard(@RequestParam(value = "category", required = false) String pathCategory) {

        String index;


        List<Flashcard> flashcards = flashcardAPI.byCategory(pathCategory);
        if (!flashcards.isEmpty()) {
            Random random = new Random();
            int randomIndex = random.nextInt(flashcards.size());
            Flashcard flashcard = flashcards.get(randomIndex);
            index = navMenu(pathCategory, flashcard.getId());
            index += "Category: " + flashcard.getCategoryName() + "</br>";
            index += "Question: " + flashcard.getQuestion() + "</br>";
            index += "Answer: <a href='/answer/" + flashcard.getId() + "'>[SHOW]</a></br>";
        } else index = navMenu(pathCategory, -1);

        index += categoryOptionList(pathCategory);
        return index;
    }


    @GetMapping("/answer/{id}")
    public String answer(@PathVariable int id) {
        Flashcard flashcard = flashcardAPI.findById(id);

        Category matchedCategory = categories.stream()
                .filter(category -> category.getName().equals(flashcard.getCategoryName()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Category not found for flashcard"));

        String index = navMenu(matchedCategory.getPath(), id);
        index += categoryOptionList(matchedCategory.getPath());
        index += "Category: " + flashcard.getCategoryName() + "</br>";
        index += "Question: " + flashcard.getQuestion() + "</br>";
        index += "Answer: " + flashcard.getAnswer() + "</br>";


        return index;
    }

    @RequestMapping(value = "/addFlashcard", method = {RequestMethod.GET, RequestMethod.POST})
    public String AddFlashcard(@ModelAttribute("flashcard") Flashcard flashcard) {
        if (!flashcard.getCategoryName().isEmpty() && !flashcard.getQuestion().isEmpty() && !flashcard.getAnswer().isEmpty()) {
            flashcardAPI.AddFlashcard(flashcard);
            flashcard.setQuestion("");
            flashcard.setAnswer("");
        }
        String index = navMenu("/", flashcard.getId());

        index += "<form action='/addFlashcard' method='post'>";
        index += "<table border=1 cellpadding=10>";
        index += "<tr>";
        index += "<th>Category</th>";
        index += "<th><select id='categoryName' name='categoryName'>";
        for (Category category : categories) {
            if (category.getName().equals(flashcard.getCategoryName())) {
                index += "<option value='" + category.getName() + "' selected>" + category.getName() + "</option>";
            } else {
                index += "<option value='" + category.getName() + "' >" + category.getName() + "</option>";
            }
        }
        index += "</select></th>";
        index += "</tr>";

        index += "<tr>";
        index += "<th>Question</th>";
        index += "<th><input type='text' id='question' name='question' value='" + flashcard.getQuestion() + "' required></th>";
        index += "</tr>";

        index += "<tr>";
        index += "<th>Answer</th>";
        index += "<th><input type='text' id='answer' name='answer' value='" + flashcard.getAnswer() + "' required></th>";
        index += "</tr>";

        index += "</table>";

        index += "<input type='submit' value='Add Flashcard'>";
        index += "</form>";


        return index;
    }

    @RequestMapping(value = "/modifyFlashcard/{flashcardId}", method = {RequestMethod.GET, RequestMethod.POST})
    public String ModifyFlashcard(@PathVariable int flashcardId, @ModelAttribute("flashcard") Flashcard flashcard) {
        String index = navMenu("/", flashcardId);

        if (flashcard.getId() == -1) {
            flashcard = flashcardAPI.findById(flashcardId);
        } else {
            flashcardAPI.UpdateFlashcard(flashcard);
        }



        index += "<form action='/modifyFlashcard/"+flashcardId+"' method='post'>";
        index += "<input type='hidden' name='id' value='" + flashcard.getId() + "'>";

        index += "<table border=1 cellpadding=10>";
        index += "<tr>";
        index += "<th>Category</th>";
        index += "<th><select id='categoryName' name='categoryName'>";
        for (Category category : categories) {
            if (category.getName().equals(flashcard.getCategoryName())) {
                index += "<option value='" + category.getName() + "' selected>" + category.getName() + "</option>";
            } else {
                index += "<option value='" + category.getName() + "' >" + category.getName() + "</option>";
            }
        }
        index += "</select></th>";
        index += "</tr>";

        index += "<tr>";
        index += "<th>Question</th>";
        index += "<th><input type='text' id='question' name='question' value='" + flashcard.getQuestion() + "' required></th>";
        index += "</tr>";

        index += "<tr>";
        index += "<th>Answer</th>";
        index += "<th><input type='text' id='answer' name='answer' value='" + flashcard.getAnswer() + "' required></th>";
        index += "</tr>";

        index += "</table>";
        index += "<input type='submit' value='Modify Flashcard'>";
        index += "</form>";


        return index;
    }

    @GetMapping("/listCategories")
    public String listCategories() {
        String index = navMenu("/", -1);
        index += "<table border=1 cellpadding=10>";
        index += "<tr>";
        index += "<th>id</th>";
        index += "<th>name</th>";
        index += "<th>path</th>";
        index += "</tr>";

        for (Category category : categories) {
            index += "<tr>";
            index += "<td>" + category.getId() + "</td>";
            index += "<td>" + category.getName() + "</td>";
            index += "<td>" + category.getPath() + "</td>";
            index += "</tr>";
        }

        index += "</table>";
        return index;
    }

    @GetMapping("/listFlashcards")
    public String listFlashcards() {

        List<Flashcard> flashcards = flashcardAPI.byCategory("/");
        String index = navMenu("/", -1);
        index += "<table border=1 cellpadding=10>";
        index += "<tr>";
        index += "<th></th>";
        index += "<th>id</th>";
        index += "<th>category</th>";
        index += "<th>question</th>";
        index += "<th>answer</th>";
        index += "</tr>";

        for (Flashcard flashcard : flashcards) {
            index += "<tr>";
            index += "<td><form action='/api/deleteFlashcard/" + flashcard.getId() + "' method='get' >";
            index += "<button type='submit'>Delete</button>";
            index += "</form>";
            index += "<form action='/modifyFlashcard/" + flashcard.getId() + "' method='get' >";
            index += "<button type='submit'>Modify</button>";
            index += "</form>";
            index += "</td>";
            index += "<td>" + flashcard.getId() + "</td>";
            index += "<td>" + flashcard.getCategoryName() + "</td>";
            index += "<td>" + flashcard.getQuestion() + "</td>";
            index += "<td>" + flashcard.getAnswer() + "</td>";
            index += "</tr>";
        }

        index += "</table>";
        return index;
    }


    private String categoryOptionList(String selectedCategory) {
        if (categories.isEmpty()) return "";

        String index = "<form action='/flashcard' method='POST'>";
        index += "<label for='category'>Choose a category:</label>";
        index += "<select id='category' name='category'>";
        for (Category category : categories) {
            if (category.getPath().equals(selectedCategory)) {
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

    private String navMenu(String path, int flashcardId) {
        String index = "<table border=1 cellpadding=10>";
        index += "<tr>";
        index += "<th><a href='/flashcard?category=" + path + "'>FLASHCARD</a></th>";
        index += "<th><a href='/addFlashcard'>ADD FLASHCARD</a></th>";
        index += "<th><a href='/modifyFlashcard/" + flashcardId + "'>MODIFY FLASHCARD</a></th>";
        index += "<th><a href='/listFlashcards'>LIST FLASHCARDS</a></th>";
        index += "<th><a href='/listCategories'>LIST CATEGORIES</a></th>";
        index += "</tr>";
        index += "</table></br></br>";

        return index;
    }


}
