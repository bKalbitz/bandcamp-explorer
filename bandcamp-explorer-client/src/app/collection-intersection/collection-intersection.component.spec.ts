import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CollectionIntersectionComponent } from './collection-intersection.component';
import { ActivatedRoute, provideRouter } from '@angular/router';
import { CollectionIntersectionService } from '../collection-intersection.service';
import { By } from '@angular/platform-browser';
import { routes } from '../app.routes';

describe('CollectionIntersectionComponent', () => {

  let dataRequest = {
        "id": 1,
        "location": "/api/collection-intersection/loading-state/1"
    };
  
  let requestComplete = {
        "all": [
            "collection-1",
            "collection-2",
            "collection-3"
        ],
        "loaded": [
            "collection-1",
            "collection-2",
            "collection-3"
        ],
        "toLoad": [],
        "state": "done",
        "relativeCompletion": 100,
        "location": "/api/collection-intersection/result/1"
    };

  let collectionsData = {
        "entries": [
            {
                "album": {
                    "title": "album-1",
                    "artist": "artist-1",
                    "type": "album",
                    "urls": [
                        "https://arist-1.bandcamp.com/album/album-1"
                    ],
                    "artUrl": "https://placehold.co/600x400"
                },
                "inCollections": [
                    "collection-1",
                    "collection-2",
                    "collection-3"
                ]
            },
            {
                "album": {
                    "title": "album-2",
                    "artist": "artist-1",
                    "type": "album",
                    "urls": [
                        "https://arist-1.bandcamp.com/album/album-2"
                    ],
                    "artUrl": "https://placehold.co/600x400"
                },
                "inCollections": [
                    "collection-1",
                    "collection-3"
                ]
            },
            {
                "album": {
                    "title": "album-3",
                    "artist": "artist-2",
                    "type": "album",
                    "urls": [
                        "https://arist-2.bandcamp.com/album/album-3"
                    ],
                    "artUrl": "https://placehold.co/600x400"
                },
                "inCollections": [
                    "collection-2",
                    "collection-3"
                ]
            }
        ]
    };
  let component: CollectionIntersectionComponent;
  let fixture: ComponentFixture<CollectionIntersectionComponent>;

  beforeAll(async () => {
    const service = jasmine.createSpyObj('CollectionIntersectionService', {'loadIntersection':dataRequest, 'getIntersectionLoadingState':requestComplete, 'getIntersectionResult':collectionsData});
    await TestBed.configureTestingModule({
      imports: [CollectionIntersectionComponent],
      providers: [provideRouter(routes), {
        provide: ActivatedRoute,
        useValue: {
          snapshot: {
            queryParams: {
              currentColelction: 'collection-1',
              name: ['collection-1','collection-2','collection-3']
            }
          }
        }
      },
      {
         provide: CollectionIntersectionService,
         useValue: service
      }
      ],
    })
    .compileComponents();

    fixture = TestBed.createComponent(CollectionIntersectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('has headline', () => {
    const h1 = fixture.debugElement.query(By.css('h1'));
    expect(h1).toBeTruthy();
    expect(h1.nativeElement.innerText).toBe('Collection Intersection');
  });

  it('has three entries', async () => {
     while(component.loadingState < 100) {
      await new Promise(resolve => setTimeout(resolve, 200));
    }
    await fixture.whenStable();
    expect(component.dataReady).toBeTruthy();
    expect(component.entries?.length).toBe(3);
  });
});
