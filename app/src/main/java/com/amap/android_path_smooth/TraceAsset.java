package com.amap.android_path_smooth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.amap.api.maps.model.LatLng;

import android.content.res.AssetManager;

public class TraceAsset {
	public static List<LatLng> parseLocationsData(
			AssetManager mAssetManager, String filePath) {
		List<LatLng> locLists = new ArrayList<LatLng>();
		InputStream input = null;
		InputStreamReader inputReader = null;
		BufferedReader bufReader = null;
		try {
			input = mAssetManager.open(filePath);
			inputReader = new InputStreamReader(input);
			bufReader = new BufferedReader(inputReader);
			String line = "";
			while ((line = bufReader.readLine()) != null) {
				String[] strArray = null;
				strArray = line.split(",");
				LatLng newpoint = new LatLng(Double.parseDouble(strArray[1]), Double.parseDouble(strArray[2]));
				if (locLists.size()==0 || newpoint.toString()!= locLists.get(locLists.size()-1).toString()) {
					locLists.add(newpoint);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufReader != null) {
					bufReader.close();
					bufReader = null;
				}
				if (inputReader != null) {
					inputReader.close();
					inputReader = null;
				}
				if (input != null) {
					input.close();
					input = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return locLists;
	}
}
