import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpHeaders, HttpRequest } from '@angular/common/http';
import { FlashcardData } from './data-model/flashcard-data';
import { NewFlashcardData } from './data-model/new-flashcard-data';
import { Category } from './data-model/category';
import { DataFormatType } from './data-model/data-format-type';
import { Observable } from 'rxjs/internal/Observable';
@Injectable({
  providedIn: 'root'
})
export class FlashcardDataServiceService {

  constructor(private http: HttpClient) { }
  readonly ROOT_URL = 'http://localhost:8000/api';

  getFlashcard(){
    let headers = new HttpHeaders();
    headers.append('Accept', 'application/json');
    const requestOptions = { headers: headers};
    return this.http.get<FlashcardData>(this.ROOT_URL + '/getFlashcard', requestOptions);

  }

  getImage(id: number) {
    return this.http.get(`${this.ROOT_URL}/getImage/${id}`, { responseType: 'blob' });
  }

  addFlashcard(flashcard: NewFlashcardData): Observable<NewFlashcardData> {
    const headers = new HttpHeaders({
      'Accept': 'application/json'
    });
  
    const formData = new FormData();
    formData.append('category_id', flashcard.category_id.toString());
    formData.append('question', flashcard.question);
    formData.append('question_type_id', flashcard.question_type_id.toString());
    if (flashcard.question_image) {
      formData.append('question_image', flashcard.question_image);
    }
    formData.append('answer', flashcard.answer);
    formData.append('answer_type_id', flashcard.answer_type_id.toString());
    if (flashcard.answer_image) {
      formData.append('answer_image', flashcard.answer_image);
    }
  
    return this.http.post<NewFlashcardData>(this.ROOT_URL + '/addFlashcard', formData, {headers});
  }
  

  getCategories(): Observable<Category[]> {
    return this.http.get<Category[]>(this.ROOT_URL + '/getCategories');
  }

  getDataFormatTypes(): Observable<DataFormatType[]> {
    return this.http.get<DataFormatType[]>(this.ROOT_URL + '/getDataFormatTypes');
  }

}
