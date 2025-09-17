# Bandcamp Explorer
Project for exploring Bandcamp collections and finding other music that might also match based on similar collections.  
In the future other sources to discover music could be added as own services.  
  
## Project structure
This project contains Spring Boot Rest-Services as Backend and an Angular based client.  
  
### bandcamp-collection-explorer
['bandcamp-collection-explorer'](bandcamp-collection-explorer)  
  
Based on ['Spring Boot'](https://spring.io/projects/spring-boot).  

Provides one REST Endpoint by now:  
* http://localhost:8080/api/collection/{collection-name}?search={search-query}
	* collection-name: Name of the Bandcamp collection equivalent to Bandcamp URl https://bandcamp.com/{collection-name}
	* search-query: Optional search parameter to search for specific collection entry.
	* Returns the found collection entries grouped by the artist. The number of results are limited by the Bandcamp endpoint. To find additional entries the request has to be specified by the search-query parameter.

The internal Bandcamp ID (fan ID) is retrieved by the collection webpage itself, by basic pattern matching.  
The collection entries are then load by Bandcamp's search API https://bandcamp.com/api/fancollection/1/search_items  
The found results are cached on a local ['SQLite'](https://sqlite.org/index.html) DB to reduce the load on Bandcamp's side.  
  
### bandcamp-item-detail
['bandcamp-item-detail'](bandcamp-item-detail)  
  
Based on ['Spring Boot'](https://spring.io/projects/spring-boot).  

Provides one REST Endpoint by now:  
* http://localhost:8081/api/album?bcUrl={bcUrl}
	* bcUrl: URL to the Bandcamp album URL should look like https://<artist>.bandcamp.com/album/<album>
	* Returns:
		* Collections this album is included (Used for collection-intersection in the client)
		* Tags for the album (Used for aggregated search link to Bandcamp)
		* Recommended Albums by band

Information is retrieved from the Bandcamp album page via ['Jsoup'](https://jsoup.org/).  
The found results are cached on a local ['SQLite'](https://sqlite.org/index.html) DB to reduce the load on Bandcamp's side.  
  
In the future the album information should also be aggregated into an artist based view.  

### bandcamp-collection-intersection
['bandcamp-collection-intersection'](bandcamp-collection-intersection)  
  
Based on ['Spring Boot'](https://spring.io/projects/spring-boot).  

Provides one REST Endpoint by now: 
* http://localhost:8080/api/collection-intersection?name={collection-name-1}&name={collection-name-2}&...
	* collection-name-#: Names of Bandcamp collections for which intersections should be found.
    * Returns the endpoint to check the state of the processing of the data.
* http://localhost:8080/api/collection-intersection/loading-state/{id}
	* Returns the current state of the collection intersection processing and the endpoint to see the current processed data.
* http://localhost:8080/api/collection-intersection/result/{id}
	* Returns the current processed collection intersection based on give state ID.
    * Returns the albums of the collections that could be found in more the one collection, sorted by the most common occurrence.

Loads the bandcamp collections from the ['bandcamp-collection-explorer'](bandcamp-collection-explorer) service.  
The processing of the data is asynchronous.  

### bandcamp-explorer-client
['bandcamp-explorer-client'](bandcamp-explorer-client)  
['Angular'](https://angular.dev/) based client. With use of ['PrimeNg'](https://primeng.org/) components.  

The current provided routes are:  
* http://localhost:4200/
	* Landing page
	* Provides a search field where the Bandcamp collection URL, like https://bandcamp.com/{collection-name}, can be entered.
	* Redirects then to the collection overview
* http://localhost:4200/collection/{collection-name}
	* Collection overview
	* Displays the collection as tree with nodes collection > artist > album
	* Provides album details on selection
	* From here a link is provided to the collection intersection for all collections that's owning the album.
* http://localhost:4200/collection-intersection?currentCollection={collection-name}&name={collection-name}&name={collection-name}&...
	* Provides a list of the albums of the collections that could be found in more the one collection, sorted by the most common occurrence.
	* In the future additional filtering options are provided

## How to run
The project can be run either locally or as a Docker compose.

### Run locally
To run the project locally following dependencies are needed:
* Angular CLI 19.2.13 or above
* JDK 24 or above
* Maven 3.9.9 or above

Start the subproject from the root folder with:
* Got to './bandcamp-collection-explorer' and run 'mvn clean package spring-boot:run'
* Got to './bandcamp-item-detail' and run 'mvn clean package spring-boot:run'
* Got to './bandcamp-collection-intersection' and run 'mvn clean package spring-boot:run'
* Got to './bandcamp-explorer-client' and run 'ng serve'

### Run as Docker compose
Before building and starting the Docker container, you must create 4 files in the root directory that must contain the passwords needed for the MySQL databases.
* ./collectiondb_password.txt -> Password for bandcamp-collection-explorer-db MySQL database service
* ./collectiondb_root_password.txt -> Root password for bandcamp-collection-explorer-db MySQL database service
* ./detaildb_password.txt -> Password for bandcamp-item-detail-db MySQL database service
* ./detaildb_root_password.txt -> Root password for bandcamp-item-detail-db MySQL database service

Then start the Docker compose via 'docker compose up --build' 
