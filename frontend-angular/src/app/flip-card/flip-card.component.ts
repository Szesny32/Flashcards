import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { FlashcardData} from '../data-model/flashcard-data';
import { Category} from '../data-model/category';
import { DataFormatType} from '../data-model/data-format-type';
import { FlashcardDataServiceService } from '../flashcard-data-service.service';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';
import { firstValueFrom } from 'rxjs';
@Component({
  selector: 'app-flip-card',
  templateUrl: './flip-card.component.html',
  styleUrls: ['./flip-card.component.css']
})
export class FlipCardComponent implements OnInit {

  flashcardForm!: FormGroup;
  categories: Category[] = [];
  dataFormatTypes: DataFormatType[] = [];

  category: number = -1;
  isFlipped = false;
  flashcard: FlashcardData = {    
    id: -1,
    category_id: -1,
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

  constructor(
      private formBuilder: FormBuilder, 
      private service: FlashcardDataServiceService, 
      private sanitizer: DomSanitizer) { }
  
  ngOnInit(): void {
    this.flashcardForm = this.formBuilder.group({
      category: ['', Validators.required],
      question: ['', Validators.required],
      questionType: ['', Validators.required],
      questionImage: [''],
      answer: ['', Validators.required],
      answerType: ['', Validators.required],
      answerImage: ['']
    });

    this.service.getCategories().subscribe((response: any) => {
      this.categories = response.categories; 
      if(this.categories && this.categories[0].id){
        this.category = this.categories[0].id;
      }
    });
    
    this.service.getDataFormatTypes().subscribe((response: any) => {
      this.dataFormatTypes = response.dataFormats;
    });

  }
  
 


  async getFlashcard() {

    if(this.category > 0){
      console.log(`getFlashcard/${this.category}`);
      this.service.getFlashcard(this.category).subscribe(async flashcard => {
        this.flashcard = flashcard;
        this.isFlipped = false;
        // Pobranie zdjęcia z serwera
        if (this.flashcard && this.flashcard.question_image) {
          const imageBlob = await firstValueFrom(this.service.getImage(this.flashcard.question_image));
          if (imageBlob instanceof Blob) {
            this.imageBlobUrl = URL.createObjectURL(imageBlob);
            this.questionImageUrl = this.sanitizer.bypassSecurityTrustUrl(this.imageBlobUrl);
          }
        } else {
          this.questionImageUrl = null;
        }
        if (this.flashcard && this.flashcard.answer_image) {
          const imageBlob2 = await firstValueFrom(this.service.getImage(this.flashcard.answer_image));
          if (imageBlob2 instanceof Blob) {
            this.imageBlob2Url = URL.createObjectURL(imageBlob2);
            this.answerImageUrl = this.sanitizer.bypassSecurityTrustUrl(this.imageBlob2Url);
          }
        } else {
          this.answerImageUrl = null;
        }
      });
    }
  }
  
  
  getSafeHtml(text: string): SafeHtml {
    return this.sanitizer.bypassSecurityTrustHtml(text);
  }


  getCategoryName(categoryId: number): string {
    if (!this.categories || !Array.isArray(this.categories)) {
      return '';
    }
    const category = this.categories.find(c => c.id === categoryId);
    return category ? category.category : '';
  }
  
  


  onQuestionImageSelected(event: any) {
    if (event.target.files.length > 0) {
      const file = event.target.files[0];
      this.flashcardForm.patchValue({
        questionImage: file
      });
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = () => {
        this.questionImageUrl = reader.result;
      };
    }
  }

  onAnswerImageSelected(event: any) {
    if (event.target.files.length > 0) {
      const file = event.target.files[0];
      this.flashcardForm.patchValue({
        answerImage: file
      });
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = () => {
        this.answerImageUrl = reader.result;
      };
    }
  }


}
