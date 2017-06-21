# android-path-smooth
轨迹平滑处理示例

本工程为基于高德地图Android SDK进行封装，实现了定位轨迹的平滑优化处理。
## 前述 ##
- [高德官网申请Key](http://lbs.amap.com/dev/#/).
- 阅读[参考手册](http://a.amap.com/lbs/static/unzip/Android_Map_Doc/index.html).
- 工程基于Android 3D地图SDK实现

## 功能描述 ##
基于3D地图SDK，对真实轨迹进行处理，实现去噪、平滑和抽稀。

## 效果展示 ##
原始轨迹
![Screenshot]( https://github.com/amap-demo/android-path-smooth/raw/master/apk/Screenshot1.png )
处理后轨迹
![Screenshot]( https://github.com/amap-demo/android-path-smooth/raw/master/apk/Screenshot12png )


## 扫一扫安装 ##
![Screenshot]( https://raw.githubusercontent.com/amap-demo/android-path-smooth/master/apk/download.png)  

## 使用方法 ##
### 1:配置搭建AndroidSDK工程 ###
- [Android Studio工程搭建方法](http://lbs.amap.com/api/android-sdk/guide/create-project/android-studio-create-project).
- [通过maven库引入SDK方法](http://lbs.amap.com/api/android-sdk/guide/create-project/android-studio-create-project#gradle_sdk).

### 2:实现方法 ###

``` 
// 获取轨迹坐标点
List<LatLng> mOriginList = new ArrayList<LatLng>();

PathSmoothTool mpathSmoothTool = new PathSmoothTool();
//设置平滑处理的等级
mpathSmoothTool.setIntensity(4);
List<LatLng> pathoptimizeList = mpathSmoothTool.pathOptimize(originlist);
//绘制轨迹，移动地图显示
if (mOriginList != null && mOriginList.size()>0) {
    mOriginPolyline = amap.addPolyline(new PolylineOptions().addAll(mOriginList).color(Color.GREEN));
    amap.moveCamera(CameraUpdateFactory.newLatLngBounds(getBounds(mOriginList), 200));
}
```
