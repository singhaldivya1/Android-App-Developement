# Project Title
SONGS PLAYLIST

## Overview
The main activity in the app displays a list of songs from various artists. When the user clicks on a list item,
the main activity is stopped, and a new activity containing a WebView is displayed instead. The web view
shows a page with a publicly available video segment, such as a YouTube video, playing the song. By
clicking a play button on the view, the device user can watch the video and listen to the song. A user can
return to the main activity by pressing the device’s “back” soft key, e.g., when the video clip is complete.

This activity also an options menu. The options menu supports 3 kinds of functionality. 
* It will allow a user to add a song to the list displayed in the main activity. When the user selects this option, a dialog window pops up to enter  that allows the user to enter (1) a song
title ,the artist ,the URL of the wikipedia page  and the URL of the public video segment playing the song. 
* It will allow a user to remove a song from the list. When this item is selected, a submenu is displayed that shows the current song
titles. The user will select one of the current songs and remove it from the list. The activity’s display will be
immediately updated. 
* The third item in the options menu allows a user to exit the app.
Finally, each list item support “long click” functionality. A long click on any list item brings up a “context menu” showing the following three options for the song under consideration: <br>
(1) View the video clip <br>
(2) View the song’s wikipedia page in the second activity <br>
(3) View the artist (or band) wikipedia page in the second activity <br>
