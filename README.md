<h1>Image Finder</h1>
<p>Android application to search images using <a href="https://www.flickr.com/services/api/">Flickr API</a>.</p>

<h2>Technologies</h2>
<ul>
    <li>Jetpack Compose</li>
    <li>Clean Architecture</li>
    <li>Coil</li>
    <li>Retrofit</li>
    <li>Dagger Hilt</li>
    <li>Paging</li>
    <li>DataStore</li>
</ul>

<h2>Setup</h2>
<ol>
    <li>Request API key <a href="https://www.flickr.com/services/apps/create/">here</a>.</li>
    <li>Paste your key into the <b>API_KEY</b> variable in core/data/ApiService.</li>
</ol>

<h2>Functions</h2>
<h3><u>Search</u></h3>
<p> 
We can input keywords to search for images using the search bar. Below this, the application displays the search results in a grid list.

The list initially contains 20 items, and as the user scrolls to the bottom, it dynamically loads the next 20 items.

Every subsequent launch, the application displays the search results of the <b>last searched keyword</b> first.
</p>
<img src="images/search.png" style="width: 256px">

<h3><u>Details</u></h3>
<p>
When we click on a list item, the application navigates to the details screen where the image can be further enlarged and additional information is displayed about it.
</p>
<img src="images/details.png" style="width: 256px">
