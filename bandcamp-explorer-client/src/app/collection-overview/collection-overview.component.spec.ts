import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CollectionOverviewComponent } from './collection-overview.component';
import { provideRouter } from '@angular/router';

describe('CollectionOverviewComponent', () => {
  let component: CollectionOverviewComponent;
  let fixture: ComponentFixture<CollectionOverviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CollectionOverviewComponent],
      providers: [provideRouter([])],
    })
    .compileComponents();

    fixture = TestBed.createComponent(CollectionOverviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
