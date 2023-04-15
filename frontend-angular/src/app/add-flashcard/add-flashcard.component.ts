import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NewFlashcardData} from '../data-model/new-flashcard-data';
import { Category} from '../data-model/category';
import { DataFormatType} from '../data-model/data-format-type';
import { FlashcardDataServiceService } from '../flashcard-data-service.service';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';
import { firstValueFrom } from 'rxjs';

@Component({
  selector: 'app-add-flashcard',
  templateUrl: './add-flashcard.component.html',
  styleUrls: ['./add-flashcard.component.css']
})
export class AddFlashcardComponent implements OnInit {

  flashcardForm!: FormGroup;
  categories: Category[] = [];
  dataFormatTypes: DataFormatType[] = [];

  isFlipped = false;
  flashcard: NewFlashcardData = {    
    category_id: -1,
    question: "Pytanie",
    question_type_id: -1,
    question_image: null,
    answer: "Odpowiedź",
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
      private sanitizer: DomSanitizer) {this.createForm();}

      createForm() {
        this.flashcardForm = this.formBuilder.group({
        category: ['', Validators.required],
        question: ['', Validators.required],
        questionType: ['', Validators.required],

        answer: ['', Validators.required],
        answerType: ['', Validators.required],

        });
        }


  ngOnInit(): void {

    this.service.getCategories().subscribe((response: any) => {
      this.categories = response.categories;
    });
    
    this.service.getDataFormatTypes().subscribe((response: any) => {
      this.dataFormatTypes = response.dataFormats;
    });

    console.log(this.categories);
    console.log(this.dataFormatTypes);
  }
  
  onSubmit() {
    console.log("Category: ", this.flashcardForm.value.category_id);
    console.log("Category2: ", this.flashcard.category_id);

    if (this.flashcardForm.invalid) {
      console.log("Form is invalid");
      this.flashcardForm.markAllAsTouched(); // mark all fields as touched to show validation errors

      return;
    }
    console.log("Form is valid");
    const flashcard: NewFlashcardData = { 
      category_id: this.flashcardForm.controls['category'].value,
      question: this.flashcardForm.controls['question'].value,
      question_type_id: this.flashcardForm.controls['questionType'].value,
      question_image: this.flashcard.question_image,
      answer: this.flashcardForm.controls['answer'].value,
      answer_type_id: this.flashcardForm.controls['answerType'].value,
      answer_image: this.flashcard.answer_image,
    };
  
    this.service.addFlashcard(flashcard).subscribe((response) => {
     console.log(response);
    });
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
      this.flashcard.question_image = file;
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
      this.flashcard.answer_image = file;
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = () => {
        
        this.answerImageUrl = reader.result;
      };
    }
  }
  


}
