import { Component, OnInit } from '@angular/core';
import { FlashcardData } from '../data-model/flashcard-data';
import { FlashcardDataServiceService } from '../flashcard-data-service.service';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';

@Component({
  selector: 'app-flip-card',
  templateUrl: './flip-card.component.html',
  styleUrls: ['./flip-card.component.css']
})
export class FlipCardComponent implements OnInit {

  constructor(private service: FlashcardDataServiceService, private sanitizer: DomSanitizer) { }
  
  ngOnInit(): void {
  }
  
  isFlipped = false;
  flashcard: FlashcardData = {    
    id: -1,
    main_category_id: -1,
    sub_category_id: -1,
    question: "unknown",
    question_type_id: -1,
    question_image: null,
    answer: "unknown",
    answer_type_id: -1,
    answer_image: null
  };
  imageBlobUrl: string ="";
  imageBlob2Url: string ="";
  answerImageUrl: any;
  questionImageUrl: any;


  async getFlashcard(){
    this.service.getFlashcard().subscribe(flashcard => this.flashcard = flashcard);
    this.isFlipped = false;
    this.questionImageUrl = null;
    this.answerImageUrl = null;
    // Pobranie zdjęcia z serwera
    if(this.flashcard && this.flashcard.question_image)
    await  this.service.getImage(this.flashcard.question_image).subscribe((imageBlob: Blob) => {
      this.imageBlobUrl = URL.createObjectURL(imageBlob);
      this.questionImageUrl = this.sanitizer.bypassSecurityTrustUrl(this.imageBlobUrl);
    });

    if(this.flashcard && this.flashcard.answer_image)
    await this.service.getImage(this.flashcard.answer_image).subscribe((imageBlob: Blob) => {
      this.imageBlob2Url = URL.createObjectURL(imageBlob);
      this.answerImageUrl = this.sanitizer.bypassSecurityTrustUrl(this.imageBlob2Url);
    });

  }

  getSafeHtml(text: string): SafeHtml {
    return this.sanitizer.bypassSecurityTrustHtml(text);
  }




}
