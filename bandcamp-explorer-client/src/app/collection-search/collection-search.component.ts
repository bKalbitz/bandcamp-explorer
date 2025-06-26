import { Component, OnChanges, OnInit, SimpleChanges } from '@angular/core';
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
export class CollectionSearchComponent implements OnInit{
  readonly  validUrl = 'https://bandcamp.com/';
  _collectioName: string = ''
  buttonDisalbed = true;
  constructor(private router: Router,
    private navigationService: NavigationDataService) {
  }

  ngOnInit(): void {
    const previewSearch = this.navigationService.getCurrentState().data;
    this.collectioName = previewSearch ? previewSearch : '';
  }

  get collectioName() {
    return this._collectioName;
  }

  set collectioName(_collectioName: string) {
    this.buttonDisalbed = !_collectioName?.startsWith(this.validUrl);
    this._collectioName = _collectioName;
  }
  
  search() {
    if(this.collectioName.startsWith(this.validUrl)) {
      this.navigationService.registerState(this.collectioName);
      const search = this.collectioName.substring(this.validUrl.length);
      this.router.navigate(['collection', search]);
    }
    
  }
}
