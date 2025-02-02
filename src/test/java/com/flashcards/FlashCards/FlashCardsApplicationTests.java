package com.flashcards.FlashCards;

import com.flashcards.FlashCards.DB.Model.Category;
import com.flashcards.FlashCards.DB.Repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class FlashCardsApplicationTests {

	@Autowired
	CategoryRepository categoryRepository;
	@Test
	void searchCategory() {
		String searchedCategory = "Programowanie";
		Optional<Category> optCategory = categoryRepository.findByName(searchedCategory);
        assertTrue(optCategory.isPresent());
		assertEquals(searchedCategory, optCategory.get().getName());
	}
}
