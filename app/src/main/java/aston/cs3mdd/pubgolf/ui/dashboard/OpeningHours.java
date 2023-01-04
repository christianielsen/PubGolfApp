package aston.cs3mdd.pubgolf.ui.dashboard;

import com.google.gson.annotations.SerializedName;

public class OpeningHours{

	@SerializedName("open_now")
	private boolean openNow;

	public void setOpenNow(boolean openNow){
		this.openNow = openNow;
	}

	public boolean isOpenNow(){
		return openNow;
	}
}