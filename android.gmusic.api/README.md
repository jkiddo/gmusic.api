#gmusic.api: an unofficial Java API for Google Play Music
#android package

As currently built, the android package is hardwired to the loopj/android-async-http project, which is also on github. Be sure to pull that down and reference it as an Android library project reference.
There are two changes that need to be made to the loopj project. An issue has been submitted to the loopj project authors, but as yet, it has not been resolved.

Find the SyncHttpClient class in the loopj project. Within that class, find these two lines (lines 17 and 18 at the time of this writing).

private String result;

AsyncHttpResponseHandler responseHandler = new AsyncHttpResponseHandler() {...

*And change them to:*

*protected* String result;

*protected* AsyncHttpResponseHandler responseHandler = new AsyncHttpResponseHandler() {...

This project is licensed under GNU GPL, Version 3.

Copyright 2012
[Jens Kristian Villadsen] (http://www.genuswillehadus.net).
[Baron Keith Hall, Sapien Mobile, LLC] (http://www.sapienmobile.com).
