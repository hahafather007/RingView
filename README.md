**RingView**是一种适用于多种布局方式的轮播图，其基于`ViewPager`打造，可随时暂停轮播和调整轮播时间，并为轮播视图提供自定义的点击或触摸事件。
##导入：
首先在工程的`build.gradle`添加如下代码：
```gradle
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
然后在app的`build.gradle`添加依赖即可：
```gradle
dependencies {
        implementation 'com.github.hahafather007:RingView:0.1'
}
```
##使用：
在layout中添加控件：
```xml
<com.hahafather007.ringview.view.RingView
    android:layout_width="match_parent"
    android:layout_height="240dp" />
```
为RingView增加图片资源或视图资源，如下：（其list可以是图片的URL数组或自定义的View）
```java
ringView.updateView(list);
```
设置图片资源的点击事件：（自定义View可在外部设置点击事件之后再调用`ringView.updateView()`方法）
```java
ringView.setPhotoClickListener(new RingView.OnPhotoClickListener() {
    @Override
    public void click(int position, String url) {
        Logger.i("Click position=== " + position + " Url=== " + url);
    }
});
```
调用`ringView.startRing(boolean withAnim);`方法可开始进行轮播，其参数表示轮播是否带有切换动画。调用`ringView.stopRing()`停止轮播。并可通过`ringView.setRingTime(int ringTime)`方法设置轮播间隔时间，单位ms，默认1000
##最后
如果有任何疑问，请在issue中反馈。
**Thank you!!!**
