# SuperDuo
Project 3 from Udacity nanodegree

# Alexandria
I've significantly modified this app. Basically there are *a lot problems* with it. They are described below. 
## User feadback
> This app is terrible. They say you can scan books, but that functionality isn’t in the app yet. It also crashed on me when I tried to add the book my sister was reading on the flight to London.

We check if we have internet connection in `onStart()` method in `SearchBookFragment`. If no connection is available we notify a user with a toast and disable interface (both `mEanEditText` and `searchButton`).

> The app could use some work. Sometimes when I add a book and don’t double-check the ISBN, it just disappears!

We think that automatic search that is currently available in the app is awesome. it looks like live search in Google. But some users may not like it. So we decided to make it optional. Default option is to press `Search` button to get search resaults but user can choose switch to live search. Again this is probably very familiar UI for those who use it in Google search.
We add boolean `isLiveSearch` variable to `SearchBookFragment` and and option to `Settings`. So we remove `TextChangedListener` from `mEanEditText` in case user choose not to use live search. In this case `searchButton` is invisible - we just don't use it.

## Major changes
| Problem        | Description           | Solution  |
|-------------|:-------------|:-----|
| Settings      | 1) no summary in `ListPreference` 2) using `Preference Activity` instead of `Preference Fragment` 3) no default values | we've fixed these problems and also add `CheckBoxPreference` for user to choose live search option. |
| Material Design      | not used      |   1) we've added colors to theme 2) we've redesigned buttons (see below)  |
| Side Navigation | 1) we think that `ViewPager` would be more appropriate in this case. 2) wrong icon for navigation - arror is used instead of drawer (usually arrow is used for navigation to parent activity)        |     1) not fixed 2) TODO |
| Service | see below       |   see below |
| zebra stripes | are neat      |    $1 |
| zebra stripes | are neat      |    $1 |
### Service
1. When a user enters ISBN `SearchBookFragment` starts a service that calls Google API. This service downloads JSON file, parse it and add entry to DB automatically. Than the fragment calls DB using `Loader`. This is somewhat arguable solution.
2. Why do we use a service? This is a task that closely related to the foreground activity. A user is waiting for a search result, so this activity will probably still be in foreground when results ariives. Should we use simple AsyncTask in this case? And a proper use of `Loader` is probably in `LibraryFragment`.
3. While a user is waiting for a result we insert data into database and than call it. It's better to show the result ASAP. So we refactor service - we just show result ASAP. We insert a book into DB after user clicks on `Add` button using another AsyncTask. 
## Design
### Buttons
> From documentation. Only mix button types when you have a good reason to, such as emphasizing an important function. If your app requires actions to be persistent and readily available to the user, consider using persistent footer buttons, which are also easily accessible. Never use raised buttons within persistent button areas.

We use both raised button `Search` and flat buttons `Clear` and `Add`. We emphasize our search function. And we use flat buttons to clear form or add a book to our library.  

> From documentation. "add padding around flat buttons so the user can easily find them". 

We use a separate linear layout that contains these buttons. We use `Minimum width: 88dp` and `Height: 36dp`.


