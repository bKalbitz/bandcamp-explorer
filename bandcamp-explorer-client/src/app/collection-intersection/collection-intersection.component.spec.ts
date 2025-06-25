import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CollectionIntersectionComponent } from './collection-intersection.component';
import { provideRouter } from '@angular/router';

describe('CollectionIntersectionComponent', () => {
  let component: CollectionIntersectionComponent;
  let fixture: ComponentFixture<CollectionIntersectionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CollectionIntersectionComponent],
      providers: [provideRouter([])],
    })
    .compileComponents();

    fixture = TestBed.createComponent(CollectionIntersectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
