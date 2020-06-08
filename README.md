# Bitprophet
This is my homework-project for the "Android alapú szoftverfejlesztés" class, using the MVVM design pattern, written in Kotlin.
## Technologies
* Room
* Retrofit
* Moshi
* Coroutines
* Workmanager
* Navigation component
* Recyclerview
* Databinding
## What's this?
A dumb game, which let you tip the price of one Bitcoin _24 hours from now._\
You start with 1000 points, and every round you lose the difference between the real price and your tipped one. The goal is to keep your points for as many rounds as possible.
## How does it look like?
Pretty ugly and simple. The goal was to write an application from scratch, using as many learned technologies as possible.
\
\
![Starter-screen](https://raw.githubusercontent.com/r2d297c3po/Bitprophet/master/app/images/starter_screen.png) ![Starter-screen-error](https://raw.githubusercontent.com/r2d297c3po/Bitprophet/master/app/images/starter_screen_error.png)
After placing your tip, you are presented with this _informative_ and _aesthetically pleasing_ screen.
\
\
![Waiting-screen](https://raw.githubusercontent.com/r2d297c3po/Bitprophet/master/app/images/waiting_screen.png)
\
\
Finally, after 24 hours (or in case of testing, in ~5 minutes) you'll recive a notification, and you can see your score.
\
\
![Notification-screen](https://raw.githubusercontent.com/r2d297c3po/Bitprophet/master/app/images/notification.png) ![Highscore-screen](https://raw.githubusercontent.com/r2d297c3po/Bitprophet/master/app/images/highscore_screen.png)
\
\
Of course, you can check your previous tips as well.
## Things to improve
As a classic university project with a strict deadline, it's nowhere near perfect. 
* Im not sure that I'm using the repository pattern right.
* Navigation should be the job of the ViewModel
* Databinding could be used for much more than just inflate the layouts
* I used WorkManager for the background job, which is not guaranteed to trigger the API call exactly 24 hours from now. In my case, I just ask for a price in a specific timestamp, so the real issue is 
when the user enters the application, the time is over, but they still can't see their scores/make their next tip. 
I can stop the ongoing job and trigger it when the application is initialized, if the time is up, _**OR**_ I could use AlarmManager which is _arguably_ the more suited solution for this problem.
* Also, it'll be much more user-friendly, if the notification would be clickable.
