import { TestBed } from '@angular/core/testing';

import { CollectionExplorerService } from './collection-explorer.service';

describe('CollectionExplorerService', () => {
  let service: CollectionExplorerService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CollectionExplorerService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
