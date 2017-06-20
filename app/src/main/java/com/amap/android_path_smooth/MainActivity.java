package com.amap.android_path_smooth;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AMap.OnMapLoadedListener, CompoundButton.OnCheckedChangeListener {
    private MapView mMapView = null;
    private AMap amap = null;
    private List<LatLng> mOriginList = new ArrayList<LatLng>();
    private Polyline mOriginPolyline, mkalmanPolyline;
    private CheckBox mOriginbtn, mkalmanbtn;
    private PathSmoothTool mpathSmoothTool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMapView = (MapView) findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        init();
        mpathSmoothTool = new PathSmoothTool();
        mpathSmoothTool.setIntensity(4);
        addLocpath();
    }

    private void init() {
        if (amap == null){
            amap = mMapView.getMap();
        }
        amap.setOnMapLoadedListener(this);
        mOriginbtn = (CheckBox) findViewById(R.id.record_show_activity_origin_button);
        mkalmanbtn = (CheckBox) findViewById(R.id.record_show_activity_kalman_button);
        mOriginbtn.setOnCheckedChangeListener(this);
        mkalmanbtn.setOnCheckedChangeListener(this);
    }

    //在地图上添加本地轨迹数据，并处理
    private void addLocpath() {
        mOriginList = TraceAsset.parseLocationsData(this.getAssets(),
                "traceRecord" + File.separator + "AMapTrace2.csv");
        if (mOriginList != null && mOriginList.size()>0) {
            mOriginPolyline = amap.addPolyline(new PolylineOptions().addAll(mOriginList).color(Color.GREEN));
            amap.moveCamera(CameraUpdateFactory.newLatLngBounds(getBounds(mOriginList), 200));
//            amap.moveCamera(CameraUpdateFactory.newLatLngZoom(mOriginList.get(0),15));
        }
        pathOptimize(mOriginList);
    }

    //轨迹平滑优化
    public List<LatLng> pathOptimize(List<LatLng> originlist){
       List<LatLng> pathoptimizeList = mpathSmoothTool.pathOptimize(originlist);
        mkalmanPolyline = amap.addPolyline(new PolylineOptions().addAll(pathoptimizeList).color(Color.parseColor("#FFC125")));
        return pathoptimizeList;
    }

    @Override
    public void onMapLoaded() {
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        int id = compoundButton.getId();
        switch (id) {
            case R.id.record_show_activity_origin_button:
                if(mOriginPolyline != null){
                    mOriginPolyline.setVisible(b);
                }
                break;
            case R.id.record_show_activity_kalman_button:
                if(mkalmanPolyline != null){
                    mkalmanPolyline.setVisible(b);
                }
                break;
        }
    }

    private LatLngBounds getBounds(List<LatLng> pointlist) {
        LatLngBounds.Builder b = LatLngBounds.builder();
        if (pointlist == null) {
            return b.build();
        }
        for (int i = 0; i < pointlist.size(); i++) {
            b.include(pointlist.get(i));
        }
        return b.build();

    }
}
