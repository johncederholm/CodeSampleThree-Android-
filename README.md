# CodeSampleThree-Android-

# SAGESAMPLE

This app is based on an inventory app shown in an interview. Several things to note:

This Android app uses the Parse library: this handles networking and local datastore. However, this project is fully
compatible with Realm.io and other third-party data store libraries.

The app pulls data from a server I maintain, with permission from the server operators. ALL IMAGES ARE COPYRIGHTED. At any point, the permission for images may be removed or denied. If this happens, I will replace the image pointer string ion the server.

The app works as follows:
1. Instantiates required objects (ListView)
2. Pulls available data from the server (hosted on AWS and ran on node.js. If this were a production app, there would be signifigant authentication or data segmentation, ensuring the app user has access to the correct data store).
3. Parses data and displays it on device.
4. Allows user to change the inventory of available items through an "up/down" arrow system (this could be modified to a number typing system easily).
5. After inventory changes, the app saves (either immediately or when available) the changes in "value" to the server. If you try changing the value, exit the app, and then reopen the app, you will see the value is different.

There are many, many, many improvements that can be made to this app. But for a start, this app performs successfully (...hopefully, I do not have access to an Android device so all testing was done on the Android Virtual Device).
