import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { Router } from '@angular/router';
import { NavigationDataService } from '../navigation-data.service';

@Component({
  selector: 'app-collection-search',
  imports: [FormsModule, InputTextModule, ButtonModule],
  templateUrl: './collection-search.component.html',
  styleUrl: './collection-search.component.css'
})
export class CollectionSearchComponent implements OnInit {
  collectioName: string = ''
  constructor(private router: Router,
    private navigationService: NavigationDataService) {
  }
  ngOnInit(): void {
    const previewSearch = this.navigationService.getCurrentState().data;
    this.collectioName = previewSearch ? previewSearch : '';
  }
  
  search() {
    const cut = 'https://bandcamp.com/'
    if(this.collectioName.startsWith(cut)) {
      this.navigationService.registerState(this.collectioName);
      const search = this.collectioName.substring(cut.length);
      this.router.navigate(['collection', search]);
    }
    
  }
}
