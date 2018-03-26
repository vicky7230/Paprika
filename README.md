# Paprika
[![License](https://img.shields.io/badge/LICENSE-Apache%20License%202.0-blue.svg)](https://github.com/vicky7230/Paprika/blob/master/LICENSE)
<!-- ![alt text](https://github.com/vicky7230/Paprika/blob/master/app/src/main/res/mipmap-xhdpi/ic_launcher.png "Logo")
<br>
 -->
### A recipe finding app for people who love to cook.

#### This repository implements MVP architecture using Dagger2, GreenDao, RxJava and Retrofit2. 

<p align="center">
  <img src="https://github.com/vicky7230/Paprika/blob/master/graphics/1f.png" width="220">
  <img src="https://github.com/vicky7230/Paprika/blob/master/graphics/2f.png" width="220">
  <img src="https://github.com/vicky7230/Paprika/blob/master/graphics/3f.png" width="220">
</p>

#### Beautiful animations using RecyclerView ItemAnimator
[ItemAnimator Code](https://github.com/vicky7230/Paprika/blob/master/app/src/main/java/com/vicky7230/eatit/ui/home/recipes/RecipesItemAnimator.java)

<p align="center">
  <img src="https://github.com/vicky7230/Paprika/blob/master/graphics/4.gif" width="250">
</p>
<br>

# Architecture Blueprint
![Blueprint](https://janishar.github.io/images/mvp-app-pics/mvp-arch.png)
<br>

#### The app has following packages:
1. **data**: It contains all the data accessing and manipulating components.
2. **di**: Dependency providing classes using Dagger2.
3. **ui**: View classes along with their corresponding Presenters.
4. **service**: Services for the application.
5. **utils**: Utility classes.

#### Classes have been designed in such a way that it could be inherited and maximize the code reuse.

### Libraries used:
1. RxJava2: https://github.com/ReactiveX/RxAndroid
2. Dagger2: https://github.com/google/dagger
3. Retrofit2: https://github.com/square/retrofit
4. Calligraphy: https://github.com/chrisjenx/Calligraphy
5. GreenDao: http://greenrobot.org/greendao
6. ButterKnife: http://jakewharton.github.io/butterknife
7. Timber: https://github.com/JakeWharton/timber
8. Glide: https://github.com/bumptech/glide
9. Okhttp3: https://github.com/square/okhttp
10. Gson: https://github.com/google/gson

### License
```
   Copyright (C) 2017 VIPIN KUMAR

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```