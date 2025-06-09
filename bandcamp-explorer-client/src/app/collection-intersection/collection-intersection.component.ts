import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CollectionExplorerService } from '../collection-explorer.service';
import { CollectionIntersectionEntry, CollectionIntersectionLoadingState } from '../collection-data';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { ButtonModule } from 'primeng/button';
import { Location } from '@angular/common';
import { ProgressBarModule } from 'primeng/progressbar';
import { ToastModule } from 'primeng/toast';
import { CollectionIntersectionService } from '../collection-intersection.service';

@Component({
  selector: 'app-collection-intersection',
  imports: [ProgressSpinnerModule, ButtonModule, ProgressBarModule, ToastModule],
  templateUrl: './collection-intersection.component.html',
  styleUrl: './collection-intersection.component.css'
})
export class CollectionIntersectionComponent implements OnInit {
  dataReady: boolean = false; 
  collectionNames: string[] = [];
  entries: CollectionIntersectionEntry[] | undefined;
  currentCollection: string | undefined;
  loadingState = 0;

  constructor(private router: Router,
    private location: Location,
    private route: ActivatedRoute,
    private service: CollectionIntersectionService
  ){}
  
  ngOnInit(): void {
    this.currentCollection = this.route.snapshot.queryParams['currentColelction'];
    this.loadData();
  }

  async loadData(): Promise<void> {
    const p = this.route.snapshot.queryParams;
     if(Array.isArray(p['name'])) {
      this.loadingState = 0;
      this.collectionNames = p['name'];
      const start = await this.service.loadIntersection(this.collectionNames);
      while(this.loadingState < 100) {
        const newState = await this.service.getIntersectionLoadingState(start);
        if(newState.loaded.length > 2 && newState.relativeCompletion > this.loadingState) {
          this.dataReady = false;
          const result = await this.service.getIntersectionResult(newState);
          if(result) {
            this.entries = result.entries;
            this.dataReady = true;
          }
        }
        await this.sleep(1000);
        this.loadingState = newState.relativeCompletion;
      }
    } else {
      this.collectionNames = [];
      this.dataReady = true;
    }
  }

  sleep(ms: number): Promise<void> {
     return new Promise(resolve => setTimeout(resolve, ms));
  }

  back(): void {
    if(this.currentCollection) {
      this.router.navigate(['collection', this.currentCollection]);
    } else {
      this.location.back();
    }
  }
}
