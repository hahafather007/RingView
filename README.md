**RingView**是一种适用于多种布局方式的轮播图，其基于`ViewPager`打造，可随时暂停轮播和调整轮播时间，并为轮播视图提供自定义的点击或触摸事件。

导入：
---
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
        implementation 'com.github.hahafather007:RingView:1.0.0'
}
```

使用：
---
在layout中添加控件：
```xml
<com.hahafather007.ringview.view.RingView
    android:layout_width="match_parent"
    android:layout_height="240dp" />
```
为RingView增加图片资源或视图资源，如下：（其list可以是图片的URL数组或自定义的View）
```java
List<Object> list = new ArrayList<>();
list.add("https://gss0.bdstatic.com/-4o3dSag_xI4khGkpoWK1HF6hhy/baike/w%3D268%3Bg%3D0/" +
	"sign=4f7bf38ac3fc1e17fdbf8b3772ab913e/d4628535e5dde7119c3d076aabefce1b9c1661ba.jpg");
list.add(LayoutInflater.from(this).inflate(R.layout.item_ring_view, null));

ringView.updateView(list);
```
设置图片资源的触摸和点击事件：（自定义View可在外部设置相应事件之后再调用`ringView.updateView()`方法）
```java
ringView.setPhotoTouchListener(new RingView.OnPhotoTouchListener() {
    @Override
    public void click(int position, String url) {
	Logger.i("Click position=== " + position + " Url=== " + url);
    }

    @Override
    public void touch(View v, MotionEvent event) {
	Logger.i("MotionEvent===" + event.toString());
    }
});
```
调用`ringView.startRing(boolean withAnim)`方法可开始进行轮播，其参数表示轮播是否带有切换动画。

调用`ringView.stopRing()`停止轮播。并可通过`ringView.setRingTime(int ringTime)`方法设置轮播间隔时间，单位ms，默认1000。

调用`ringView.setTouchToPause(boolean touchToPause)`方法可设置是否在手指触摸屏幕时暂停播放，默认为true。

并可以通过调用`ringView.moveToLast(boolean withAnim)`和`ringView.resetToFirst(boolean withAnim)`、`ringView.resetToFirst(boolean withAnim)`方法分别跳转下一张、上一站和重置为第一张，其bool参数表示跳转时是否带有动画（建议在调用`resetToFirst`方法是参数设置为false）。

最后：
---
在Activity结束时不要忘记在onDestory中调用`ringView.release();`释放资源，避免内存泄漏。

最后的最后：
--
如果有任何疑问，请在issue中反馈。
**Thank you!!!**
