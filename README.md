# TheMealApp
An android app that uses food API to display different categories of food
The food api is https://www.themealdb.com/api.php.
What I used in this project?

-Kotlin language.

-REST API (Retrofit).
-MVVM as architecture pattern.
-View binding.
-Hilt as dependency injection.
-Navigation component (one activity and four fragments).
-Shimmer layout from https://facebook.github.io/shimmer-android/.
-Snap helper and attach it to recycler view to snap to the center of items of recyler view.
-list adapter and DiffUtil to get the difference between two lists and outputs a list of update operations that converts the first list into the second one,and show some animations (in search activity).
-Extension functions.
-Koltin coroutines.
-photoview to ZoomIn and Zoomout. https://github.com/Baseflow/PhotoView

#UPDATE
I heave just done Favorite feature:
  -Enable user to mark meal as favorite meal.
  -save favorite meals in local sorage using Room database.
  -Enable user to remark meal as un favorite.
  
Most of UI designs from https://github.com/haerulmuttaqin/FoodsApp-starting-code with some differences.
![](image.jpg)
