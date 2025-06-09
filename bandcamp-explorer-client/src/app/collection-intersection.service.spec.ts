import { TestBed } from '@angular/core/testing';

import { CollectionIntersectionService } from './collection-intersection.service';

describe('CollectionIntersectionService', () => {
  let service: CollectionIntersectionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CollectionIntersectionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
