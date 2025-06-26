import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CollectionSearchComponent } from './collection-search.component';
import { DebugElement } from '@angular/core';
import { By } from '@angular/platform-browser';

describe('CollectionSearchComponent', () => {
  let component: CollectionSearchComponent;
  let fixture: ComponentFixture<CollectionSearchComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CollectionSearchComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CollectionSearchComponent);
    component = fixture.componentInstance;

    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('has headline', () => {
    const h1 = fixture.debugElement.query(By.css('h1'));
    expect(h1).toBeTruthy();
    expect(h1.nativeElement.innerText).toBe('Search your bandcamp collection');
  });

  it('has disabled search button', () => {
    expect(component.buttonDisalbed).toBeTruthy();
  });

  it('search button also disabled with invalid BC collection URL', () => {
    component.collectioName = 'test';
    expect(component.buttonDisalbed).toBeTruthy();
  });

  it('search button  enabled with BC collection URL', () => {
    component.collectioName = 'https://bandcamp.com/my-collection-name';
    expect(component.buttonDisalbed).toBeFalsy();
  });
});
