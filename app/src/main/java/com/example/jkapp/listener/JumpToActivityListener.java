package com.example.jkapp.listener;

public interface JumpToActivityListener {

//	public void jumpToFenceActivity(String deviceName, LatLng latlng);
//	public void jumpToRoutNaviActivity(String deviceName);
//	public void jumpToTrackPlayActivity(String deviceName);
//	public void jumpToOrderSetActivity(String deviceName);
//	public void jumpToCarInfoActivity(String deviceName);
	public void jumpToSystemSetActivity();
//	public void jumpToHomeShowCarActivity(AlarmCarGrid mCarGrid);
//	public void jumpToHomeShowCarAlarmActivity(AlarmCarGrid mAlarmGrid);
//	public void jumpToTongjiActivity();
	public void jumpToAboutActivity();
	public void jumpToModifyPawActivity(String loginName,String pwdStr);
//	public void resetAppData();
	public void jumpToReturnAdvActivity(String loginName);
}
