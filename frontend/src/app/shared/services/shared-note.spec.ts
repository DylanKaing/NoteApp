import { TestBed } from '@angular/core/testing';

import { SharedNote } from './shared-note';

describe('SharedNote', () => {
  let service: SharedNote;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SharedNote);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
