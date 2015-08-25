# SuperDuo
Project 3 from Udacity nanodegree

# Alexandria
I've significantly modified this app. Basically there are *a lot problems* with it. They are described below. *Disclaimer. We don't touch tablet layout in any way. It can be broken after our modifications.*

## Major problems
### User feadback
> This app is terrible. They say you can scan books, but that functionality isn’t in the app yet. It also crashed on me when I tried to add the book my sister was reading on the flight to London.

We check if we have internet connection in `onStart()` method in `SearchBookFragment`. If no connection is available we notify a user with a toast and disable interface (both `mEanEditText` and `searchButton`).

> The app could use some work. Sometimes when I add a book and don’t double-check the ISBN, it just disappears!

We think that automatic search that is currently available in the app is awesome. it looks like live search in Google. But some users may not like it. So we decided to make it optional. Default option is to press `Search` button to get search resaults but user can choose switch to live search. Again this is probably very familiar UI for those who use it in Google search.
We add boolean `isLiveSearch` variable to `SearchBookFragment` and and option to `Settings`. So we remove `TextChangedListener` from `mEanEditText` in case user choose not to use live search. In this case `searchButton` is invisible - we just don't use it.

### Service
1. When a user enters ISBN `SearchBookFragment` starts a service that calls Google API. This service downloads JSON file, parse it and add entry to DB automatically. Than the fragment calls DB using `Loader`. This is somewhat arguable solution.
2. Why do we use a service? This is a task that closely related to the foreground activity. A user is waiting for a search result, so this activity will probably still be in foreground when results ariives. Should we use simple AsyncTask in this case? And a proper use of `Loader` is probably in `LibraryFragment`.
3. While a user is waiting for a result we insert data into database and than call it. It's better to show the result ASAP. So we refactor service - we just show result ASAP. We insert a book into DB after user clicks on `Add` button using another AsyncTask. 

### Configuration changes
`SearchBookFragment` has `onSaveInstanceState()` method where we save *only* `ean` value. In `onCreateView()` we check if `savedInstanceState != null` and get back this value. Than we use `Service` to get book details from DB or network. In other words we fetch book detail again. In fact we don't need `onSaveInstanceState()` - `EditText` field will be saved during configuration changes automatically.
We have 2 options: a) put `mBook` into `savedInstanceState` or b) just retain the fragment with `setRetainInstance(true)`. We use the second option - we have to handle only configuration changes, not process termination.

### Miscellaneous bugs
1. `deleteBook()` in `BookService` is incorrect. We delete only an entry from books table but not entries from `authors` and `categories` tables. We refactor this method.
2. When we delete a book in `BookDetail` we return to `ListView` where this book is still presented.

## Changes in components
### Settings      
We've added summary in `ListPreference` and use `Preference Fragment`istead of `Preference Activity`. We've also added default values. We've added `CheckBoxPreference` for user to choose live search option.

### BookSearchFragment
1. We add check for connectivity. If no connection is available we: a) notify a user with a toast and b) make both `mEanEditText` and `mSearchButton` inactive;
2. We add live search option. Default case - no live search. In this case a user have to press `mSearchButton` manually to get results. If user choose live search option in Settings then we hide `mSearchButton`. When a user enters an appropriate code search is perfoming automatically. The app behavior is the same as in the provided version.
3. We refactor buttons. We don't use service and we don't add a book to DB automatically. Now we have: a) `Clear` button - just clear screen (doesn't delete from DB); b) `Add` button - adds a book to DB.
4. We created `Book` class and store an instance in a member variable. We assign the value after search and than we use it for creating book details layout.
5. We add scan functionality to `MainActivity` and a `Scanner` button to `ActionBar`. We use `ZXing` library as described in Mark Murphy manual. After scanning is performed we create new BookSearchFragment with ISBN field filled in. Fragment behavior after that depends on live search option.
6. We use `Picasso library` to download book covers. And we refactor book details layout - it renders poorly on our devices (Nexus 5 etc.) both for portrait and landscape.
7. We add `setRetainInstance(true)` to retain fragment instance after configuration change (usually - after rotation).

### BookAdapter
We've added authors to adapter instead of subtitle - a lot of books just don't have a subtitle. We also use Picasso to download a cover of a book.

## Design
### Buttons
> From documentation. Only mix button types when you have a good reason to, such as emphasizing an important function. If your app requires actions to be persistent and readily available to the user, consider using persistent footer buttons, which are also easily accessible. Never use raised buttons within persistent button areas.

We use both raised button `Search` and flat buttons `Clear` and `Add`. We emphasize our search function. And we use flat buttons to clear form or add a book to our library.  

> From documentation. Add padding around flat buttons so the user can easily find them. 

We use a separate linear layout that contains these buttons. We use `Minimum width: 88dp` and `Height: 36dp`.

# FootballScores

## Widgets
We have two widgets: 
1. *Small* widget shows the first match Today. On click it opens `MainActivity`. We get information about this match from DB in background using `SmallWidgetService`. We use a separate layout for a widget - `small_widget.xml`, but probably we can use `scores_list_item.xml`.
2. *Collection* widget shows all matches Today. We use here specialized classes like `RemoteViewsService` and `RemoteViewsFactory`. We use this sample [project](https://github.com/commonsguy/cw-omnibus/tree/master/AppWidget/LoremWidget). On click this widget opens `DetailActivity` that we created for this purpose. We send only `matchId` via intent and then get information from DB using `LoaderCallbacks`.

## Debugging data
1. We can not get enough information from API call so we prepared test data. We may switch between test and API data in `ScoresService` using flags like `IS_TEST_DATA_GENERATED`. We also have flag `IS_ALL_LEAGUES` to get information from *all* leagues, not only from our restricted list. When we generate data we use `setSeed(0)` to simplify testing.
2. We use detail activity for handling `onClick()` in `ScoresAdapter` with flag `IS_DETAIL_ACTIVITY` in `MainFragment`.

## ScoresService
We use this service to get data from API and put them into DB. Then we use `LoaderCallbacks` in `MainFragment` to get them back. We use `AlarmManager` to start our service in two places: `MainActivity` and `BootReceiver` (on boot of device). We schedule our alarm to work every 12 hours. 
We have also a button to manually update data on `ActionBar`. This is useful for the first run. Service can start only in a few seconds.


