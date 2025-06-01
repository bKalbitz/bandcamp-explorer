import { Routes } from '@angular/router';
import { CollectionSearchComponent } from './collection-search/collection-search.component';
import { CollectionOverviewComponent } from './collection-overview/collection-overview.component';
import { CollectionIntersectionComponent } from './collection-intersection/collection-intersection.component';

export const routes: Routes = [
    {
        path: '',
        component: CollectionSearchComponent,
        title: 'Bandcamp Collection search'
    },
    {
    path: 'collection/:name',
    component: CollectionOverviewComponent,
    title: 'Bandcamp Collection overview'
    },
    {
    path: 'collection-intersection',
    component: CollectionIntersectionComponent,
    title: 'Bandcamp Collection intersection'
    }
];
