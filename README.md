HotelMenuApp
=========

**Developed by:[Prashanth Ramakrishnan](prashanth_r03@yahoo.co.in)**

**Features**
- Has 3 micro apps each doing the job as described in the task

**restaurantmenudata**
- No launcher activity, has a hardcoded json file as given, I use this json to convert the response and provide it to other apps
via a content provider

**restauranthttpproxyserver**
- Uses the littleproxy open source library to create a local proxy server which is then used to intercept requests, thanks to github for prompting this 
when I googled httpproxy for java/android!
- Since the littleproxy library is not maintained [anymore](https://github.com/adamfisk/LittleProxy/issues/424), I decided to use a recent fork which you can find it [here](https://github.com/mrog/LittleProxy) 
- The proxy runs on localhost port 8080 (127.0.0.1:8080)
- This proxy intercepts the requests and if it finds the same URI already then the cached response is returned else the normal call
goes through
- Make sure you are connected to the network and set the proxy manually to host 127.0.0.1 and port 8080 in the WiFi settings,
 programmatically this is not possible to set proxy to a [WebView](https://stackoverflow.com/questions/4488338/webview-android-proxy), see the link [here](https://stackoverflow.com/questions/3629644/how-can-you-set-the-http-proxy-programmatically)
 and the [link](https://developer.android.com/about/versions/marshmallow/android-6.0-changes.html#behavior-network) Only apps that are signed with the same key as system apps can get this permission (i.e.: if you cook your own rom, you could add that functionality) - comment from StackOverflow
- This [link](https://github.com/MediumOne/littleproxy-example) really helped me to see how littleproxy can be consumed for different scenarios

**restaurantmenuapp**
- Contains a hardcode html which is loaded from assets folder
- When the app is launched, this webview is inflated and loads the menu list.


**Note**
- Tested on Motorola Moto G4, Android version 7.0

**Closing comments**
- I decided to write in Kotlin first, but I faced a lot of issues with certain components with littleproxy, so I decided to switch back to java
- To intercept https requests man in the middle approach is needed, which has of course the same trust authority as that of the original sender
- littleproxy has an extension that could be used as shown [here](https://github.com/ganskef/LittleProxy-mitm)
- I would also consider using [Okhttp](https://github.com/square/okhttp) and running a custom client which intercepts all requests as shown
[here] (https://square.github.io/okhttp/interceptors/)


**Open source libraries used**

- **[Commons-io](https://commons.apache.org/proper/commons-io/)**
- **[littleproxy](https://github.com/mrog/LittleProxy)**

### License

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.