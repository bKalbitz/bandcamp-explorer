import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CollectionExplorerService } from '../collection-explorer.service';
import { CollectionIntersectionEntry } from '../collection-data';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { ButtonModule } from 'primeng/button';
import { Location } from '@angular/common';

@Component({
  selector: 'app-collection-intersection',
  imports: [ProgressSpinnerModule, ButtonModule],
  templateUrl: './collection-intersection.component.html',
  styleUrl: './collection-intersection.component.css'
})
export class CollectionIntersectionComponent implements OnInit {
  dataReady: boolean = false; 
  collectionNames: string[] = [];
  entries: CollectionIntersectionEntry[] | undefined;
  currentCollection: string | undefined;

  constructor(private router: Router,
    private location: Location,
    private route: ActivatedRoute,
    private service: CollectionExplorerService
  ){}
  
  ngOnInit(): void {
    const p = this.route.snapshot.queryParams;
    this.currentCollection = p['currentCollection'];
    if(Array.isArray(p['name'])) {
      this.collectionNames = p['name'];
      this.service.getIntersection( this.collectionNames).then(d => {
        this.entries = d ? d.entries : [];
        this.dataReady = true;
      })
    } else {
      this.collectionNames = [];
      this.dataReady = true;
    }
  }

  back(): void {
    if(this.currentCollection) {
      this.router.navigate(['collection', this.currentCollection]);
    } else {
      this.location.back();
    }
  }
}
