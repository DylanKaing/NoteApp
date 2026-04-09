import { TestBed } from '@angular/core/testing';

import { SharedNoteService } from './shared-note-service';

describe('SharedNoteService', () => {
  let service: SharedNoteService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SharedNoteService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
