package com.flashcards.FlashCards.DB.Model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Flashcard {
    int id = -1;
    String categoryName="";
    String categoryPath="";
    String question="";
    String answer="";
}
