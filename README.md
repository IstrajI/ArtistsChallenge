# Mobile Technical Challenge

The goal of the mobile technical challenge is to build a simple application to browse artists. The purpose of this test is to make it possible for you to show us what you can do, how do you structure your code and what kind of tools do you use when building applications. 

The preferable way to deliver the technical challenge is a public git repository - GitHub for instance. If for any reason, you don’t want to put the challenge on your git page, you can deliver the whole project as a zip package.

## GraphQL API

To fetch necessary data you should use a [GraphQL](https://graphql.org/) API which you can find here: [https://graphbrainz.herokuapp.com](https://graphbrainz.herokuapp.com/)/. Apart from documentation, you can play with the API directly in the provided link.

Some example queries that you can play with:

```graphql
query Artists {
  search {
    artists(query: "John Mayer", first: 10, after: null) {
      nodes {
        ...ArtistBasicFragment
      }
    }
  }
}

query Artist {
  node(id: "QXJ0aXN0OjE0NGVmNTI1LTg1ZTktNDBjMy04MzM1LTAyYzMyZDA4NjFmMw==") {
    ...ArtistDetailsFragment
  }
}

fragment ArtistDetailsFragment on Artist {
  id
  name
  disambiguation
  rating {
    value
    voteCount
  }
}

fragment ArtistBasicFragment on Artist {
  id
  name
  disambiguation
}
```

If you don't know how to connect to GraphQL API we suggest using Apollo client, you will find it here: 

- Apollo Android client: [https://github.com/apollographql/apollo-android](https://github.com/apollographql/apollo-android)
- Apollo iOS client: [https://github.com/apollographql/apollo-ios](https://github.com/apollographql/apollo-ios)

## Functional requirements

Functional requirements are very general. Details and the UI are up to you, surprise us 😊 This is the moment to show off your knowledge of libraries you like, code structure, and application architecture. Please configure the app as if it was a project to be released in production directly to Google Play or App Store. 

**iOS Limitations:**

- Implement the app using UIKit. Though we slowly adopt SwiftUI. We still heavily use UIKit. And many features won't be possible with SwiftUI. So we would like to see how do you work with UIKit.

**The app should have 4 features:** 

1. **List of artists.** Use search query from the API mentioned before. This list should have an infinite scroll, and should fetch data with 15 items on each page. 
    
    <aside>
    💡 Note: implement this view as a first tab in the app.
    
    </aside>
    
    <aside>
    💡 Note: query with empty search string will throw an error.
    
    </aside>
    
2. **Artist details.** It's up to you to decide which information should be displayed on this page. 
    
    <aside>
    ⚠️ Warning: We encountered an error when fetching Relationships field on Artist type. So we advise you to avoid fetching this field.
    
    </aside>
    
3. **Bookmark artist.** User should be able to bookmark/unbookmark any artist from any part of the app.
    
    <aside>
    💡 Note: the given API doesn't let you save any data on the server-side, so you have to save this information locally.
    
    </aside>
    
4. **List of bookmarked artists.** List of artists that user bookmarked.
    
    <aside>
    💡 Note: implement this view as a second tab in the app.
    
    </aside>
    

## Last notes

We don't want to set any more constraints or requirements on how to design the app or what tools you should use. We want you to demonstrate to us your skills, your favorite tools, your creativity, and your proactive approach when coding. 

Good luck! 🎉
