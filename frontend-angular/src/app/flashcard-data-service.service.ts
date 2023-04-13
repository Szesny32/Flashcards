import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpHeaders, HttpRequest } from '@angular/common/http';
import { FlashcardData } from './data-model/flashcard-data';
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
}
