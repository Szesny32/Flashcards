import { TestBed } from '@angular/core/testing';

import { FlashcardDataServiceService } from './flashcard-data-service.service';

describe('FlashcardDataServiceService', () => {
  let service: FlashcardDataServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FlashcardDataServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
