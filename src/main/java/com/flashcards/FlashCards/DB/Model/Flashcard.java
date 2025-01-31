package com.flashcards.FlashCards.DB.Model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Flashcard {
    int id;
    String categoryName;
    String question;
    String answer;
}
