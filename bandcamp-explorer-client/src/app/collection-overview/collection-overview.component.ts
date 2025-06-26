import { Component, ElementRef, inject, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CollectionExplorerService } from '../collection-explorer.service';
import { CollectionAlbum, CollectionArtist, CollectionData } from '../collection-data';
import { TreeNode } from 'primeng/api';
import { TreeModule, TreeNodeSelectEvent } from 'primeng/tree';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { FormsModule } from '@angular/forms';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { AlbumDetailComponent } from '../album-detail/album-detail.component';
import { NavigationDataService } from '../navigation-data.service';

@Component({
  selector: 'app-collection-overview',
  imports: [TreeModule, FormsModule, InputTextModule, ButtonModule, ProgressSpinnerModule, AlbumDetailComponent],
  templateUrl: './collection-overview.component.html',
  styleUrl: './collection-overview.component.css'
})
export class CollectionOverviewComponent implements OnInit  {

  dataReady: boolean = false;
  apiSearch: string = '';
  collectionName: string = '';
  artistCount: number = 0;
  albumCount: number = 0;
  treeData: TreeNode[] = [];
  selected: TreeNode | undefined;

  constructor(private router: Router,
    private route: ActivatedRoute,
    private service: CollectionExplorerService,
    private navigationService: NavigationDataService){}

  ngOnInit() {
  this.collectionName = this.route.snapshot.params['name'];
  this.artistCount = 0;
  this.albumCount = 0;
    if(this.collectionName) {
      this.service.getCollectionData(this.collectionName).then( d => {
        this.treeData = this.collectionToTreeNodes(d);
        this.dataReady = true;
        this.selectNode(this.treeData[0], this.navigationService.getCurrentState()?.data);
      });
    }
  }

  private selectNode(currentNode: TreeNode, key: string) {
    if(!key || !this.treeData) {
      return;
    }
    if(currentNode.key === key) {
      this.selected = currentNode;
      let parent = this.selected.parent;
      while(parent) {
        parent.expanded = true;
        parent = parent.parent;
      }
      return;
    }
    if(currentNode.children) {
      for(const subNode of currentNode.children) {
        this.selectNode(subNode, key);
      }
    }
  }

  onNodeSelect(event: TreeNodeSelectEvent) {
    this.navigationService.registerState(event?.node?.key);
  }

  private collectionToTreeNodes(collection: CollectionData): TreeNode[] {
  const result: TreeNode[] = [{
    key: `collection-${collection.name}`,
    type: "collection",
    label: collection.name,
    data: collection,
    icon: 'pi pi-headphones'
  }];
  result[0].children = this.artistsToTreeNodes(collection.artists, result[0]);
  return result;
  }

  private artistsToTreeNodes(artists: CollectionArtist[], parent: TreeNode): TreeNode[] {
      const result: TreeNode[] = [];
      for(const artist of artists) {
      this.artistCount++;
      const node: TreeNode = {
        key: `artist-${artist.name}`,
        type: "artist",
        label: artist.name,
        data: artist,
        icon: 'pi pi-star',
        parent: parent
      }
      node.children =  this.albumsToTreeNode(artist.albums, node);
      result.push(node);
      }
      return result;
  }

  private albumsToTreeNode(albums: CollectionAlbum[], parent: TreeNode): TreeNode[] {
    const result: TreeNode[] = [];
    for(const album of albums) {
      this.albumCount++;
      result.push({
        key: `artist-${album.artist}-album-${album.title}`,
        type: "album",
        label: album.title,
        data: album,
        icon: 'pi pi-play',
        parent: parent
      })
    }
    return result;
  }

  search() {
    if(this.apiSearch && this.apiSearch.length > 1) {
      this.dataReady = false;
      this.service.searchCollectionData(this.collectionName, this.apiSearch).then(d => {
        // keep it simple here. We now that the result is persisted on the local server, so refresh the whole collection
        this.service.getCollectionData(this.collectionName).then( d => {
          this.artistCount = 0;
          this.albumCount = 0;
          this.treeData = this.collectionToTreeNodes(d);
          this.dataReady = true;
        });
      });
    }
  }

  back() {
    this.router.navigate(['/']);
  }
}
