import { Component, OnInit } from '@angular/core';
import { FlashcardData } from '../data-model/flashcard-data';
import { FlashcardDataServiceService } from '../flashcard-data-service.service';

@Component({
  selector: 'app-flip-card',
  templateUrl: './flip-card.component.html',
  styleUrls: ['./flip-card.component.css']
})
export class FlipCardComponent implements OnInit {

  constructor(private service: FlashcardDataServiceService) { }
  
  ngOnInit(): void {
  }
  
  isFlipped = false;
  flashcard: FlashcardData = {id: -1, category: "unknown", question:"404", answer:"Not Found"};


  getFlashcard(){
    this.service.getFlashcard().subscribe(flashcard => this.flashcard = flashcard);
  }


}
