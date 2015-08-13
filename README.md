# SuperDuo
Project 3 from Udacity nanodegree

# Alexandria
## User feadback
> This app is terrible. They say you can scan books, but that functionality isn’t in the app yet. It also crashed on me when I tried to add the book my sister was reading on the flight to London.

We check if we have internet connection in `onStart()` method in `SearchBookFragment`. If no connection is available we notify a user with a toast and disable interface (both `mEanEditText` and `searchButton`).

> The app could use some work. Sometimes when I add a book and don’t double-check the ISBN, it just disappears!

We think that automatic search that is currently available in the app is awesome. it looks like live search in Google. But some users may not like it. So we decided to make it optional. Default option is to press `Search` button to get search resaults but user can choose switch to live search. Again this is probably very familiar UI for those who use it in Google search.
We add boolean `isLiveSearch` variable to `SearchBookFragment` and and option to `Settings`. So we remove `TextChangedListener` from `mEanEditText` in case user choose not to use live search. In this case `searchButton` is invisible - we just don't use it.

## Design
### Buttons
> From documentation. Only mix button types when you have a good reason to, such as emphasizing an important function. If your app requires actions to be persistent and readily available to the user, consider using persistent footer buttons, which are also easily accessible. Never use raised buttons within persistent button areas.

We use both raised button `Search` and flat buttons `Clear` and `Add`. We emphasize our search function. And we use flat buttons to clear form or add a book to our library.  

> From documentation. "add padding around flat buttons so the user can easily find them". 

We use a separate linear layout that contains these buttons. We use `Minimum width: 88dp` and `Height: 36dp`.


