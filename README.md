# Bandcamp Explorer
Project for exploring Bandcamp collections and finding other music that might also match based on similar collections.
In the future other source to discover music could be added as own services.

## Project structure
This project contains Spring Boot Rest-Services as Backend and an Angular based client.

### bandcamp-collection-explorer
['bandcamp-collection-explorer'](bandcamp-collection-explorer)

Based on ['Spring Boot'](https://spring.io/projects/spring-boot).
Provides two REST endpoints:
* http://localhost:8080/api/collection/\<collection-name\>?search=\<search-query\>
	* collection-name: Name of the Bandcamp collection equivalent to Bandcamp URl https://bandcamp.com/\<collection-name\>
	* search-query: Optional search parameter to search for specific collection entry.
	* Returns the found collection entries grouped by the artist. The number of results are limted by the Bandcamp endpoint. To find additional entries the request has to be specified by the search-query parameter.
* http://localhost:8080/api/collection-intersection?name=\<collection-name-1\>&name=\<collection-name-2\>&...
	* collection-name-#: Names of Bandcamp collections for which intersections should be found.
    * Returns the albums of the collections that could be found in more the one collection, sorted by the most common occurrence.
The internal Bandcamp ID (fan ID) is retrieved by the collection webpage itself, by basic pattern matching.
The collection entries is then load by Bandcamp's search API https://bandcamp.com/api/fancollection/1/search_items
The found results are cached on a local ['SQLite'](https://sqlite.org/index.html) DB to reduce the load on Bandcamp's side.

### bandcamp-item-detail
['bandcamp-item-detail'](bandcamp-item-detail)

Based on ['Spring Boot'](https://spring.io/projects/spring-boot).
Provides one REST Endpoint ny now:
* http://localhost:8081/api/album?bcUrl=\<bcUrl\>
	* bcUrl: URL to the Bandcamp album URL should look like https://<artist>.bandcamp.com/album/<album>
	* Returns:
		* Collections this album is included (Used for collection-intersection in the client)
		* Tags for the album (Used for aggregatted search link to Bandcamp)
		* Recommended Albums by band
Information is rerieved from the Bandcamp album page via ['Jsoup'](https://jsoup.org/).
The found results are cached on a local ['SQLite'](https://sqlite.org/index.html) DB to reduce the load on Bandcamp's side.

In the future the album information should also be aggregated into an arist based view.

### bandcamp-explorer-client
['bandcamp-explorer-client'](bandcamp-explorer-client)

['Angular'](https://angular.dev/) based client. With use of ['PrimeNg'](https://primeng.org/) components.
The current provided routes are:
* http://localhost:4200/
	* Landing page
	* provides a search field where the Bancamp collection URL, like https://bandcamp.com/<collection-name>, can be entered.
	* Redirects then to the collection overview
* http://localhost:4200/collection/\<collection-name\>
	* Collection overview
	* Displays the collection as tree with nodes collection > aritst > album
	* Provides album details on selection
	* From here a link is provided to the collection intersection for all collections thats owning the album.
* http://localhost:4200/collection-intersection?currentCollection=\<collection-name\>&name=\<collection-name\>&name=\<collection-name\>&...
	* Provides a list of the albums of the collections that could be found in more the one collection, sorted by the most common occurrence.
	* In the future additonal filtering options are provided
