# Paprika

<!-- ![alt text](https://github.com/vicky7230/Paprika/blob/master/app/src/main/res/mipmap-xhdpi/ic_launcher.png "Logo")
<br>
 -->
### A recipe finding app for people who love to cook.

#### This repository implements MVP architecture using Dagger2, GreenDao, RxJava and Retrofit2. 

<p align="center">
  <img src="https://github.com/vicky7230/Paprika/blob/master/graphics/1.png" width="250">
  <img src="https://github.com/vicky7230/Paprika/blob/master/graphics/2.png" width="250">
  <img src="https://github.com/vicky7230/Paprika/blob/master/graphics/3.png" width="250">
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
