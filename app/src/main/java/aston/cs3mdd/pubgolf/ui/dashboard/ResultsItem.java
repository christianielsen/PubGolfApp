package aston.cs3mdd.pubgolf.ui.dashboard;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResultsItem{

	@SerializedName("types")
	private List<String> types;

	@SerializedName("business_status")
	private String businessStatus;

	@SerializedName("icon")
	private String icon;

	@SerializedName("rating")
	private Object rating;

	@SerializedName("icon_background_color")
	private String iconBackgroundColor;

	@SerializedName("photos")
	private List<PhotosItem> photos;

	@SerializedName("reference")
	private String reference;

	@SerializedName("user_ratings_total")
	private int userRatingsTotal;

	@SerializedName("price_level")
	private int priceLevel;

	@SerializedName("scope")
	private String scope;

	@SerializedName("name")
	private String name;

	@SerializedName("opening_hours")
	private OpeningHours openingHours;

	@SerializedName("geometry")
	private Geometry geometry;

	@SerializedName("icon_mask_base_uri")
	private String iconMaskBaseUri;

	@SerializedName("vicinity")
	private String vicinity;

	@SerializedName("plus_code")
	private PlusCode plusCode;

	@SerializedName("place_id")
	private String placeId;

	public void setTypes(List<String> types){
		this.types = types;
	}

	public List<String> getTypes(){
		return types;
	}

	public void setBusinessStatus(String businessStatus){
		this.businessStatus = businessStatus;
	}

	public String getBusinessStatus(){
		return businessStatus;
	}

	public void setIcon(String icon){
		this.icon = icon;
	}

	public String getIcon(){
		return icon;
	}

	public void setRating(Object rating){
		this.rating = rating;
	}

	public Object getRating(){
		return rating;
	}

	public void setIconBackgroundColor(String iconBackgroundColor){
		this.iconBackgroundColor = iconBackgroundColor;
	}

	public String getIconBackgroundColor(){
		return iconBackgroundColor;
	}

	public void setPhotos(List<PhotosItem> photos){
		this.photos = photos;
	}

	public List<PhotosItem> getPhotos(){
		return photos;
	}

	public void setReference(String reference){
		this.reference = reference;
	}

	public String getReference(){
		return reference;
	}

	public void setUserRatingsTotal(int userRatingsTotal){
		this.userRatingsTotal = userRatingsTotal;
	}

	public int getUserRatingsTotal(){
		return userRatingsTotal;
	}

	public void setPriceLevel(int priceLevel){
		this.priceLevel = priceLevel;
	}

	public int getPriceLevel(){
		return priceLevel;
	}

	public void setScope(String scope){
		this.scope = scope;
	}

	public String getScope(){
		return scope;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setOpeningHours(OpeningHours openingHours){
		this.openingHours = openingHours;
	}

	public OpeningHours getOpeningHours(){
		return openingHours;
	}

	public void setGeometry(Geometry geometry){
		this.geometry = geometry;
	}

	public Geometry getGeometry(){
		return geometry;
	}

	public void setIconMaskBaseUri(String iconMaskBaseUri){
		this.iconMaskBaseUri = iconMaskBaseUri;
	}

	public String getIconMaskBaseUri(){
		return iconMaskBaseUri;
	}

	public void setVicinity(String vicinity){
		this.vicinity = vicinity;
	}

	public String getVicinity(){
		return vicinity;
	}

	public void setPlusCode(PlusCode plusCode){
		this.plusCode = plusCode;
	}

	public PlusCode getPlusCode(){
		return plusCode;
	}

	public void setPlaceId(String placeId){
		this.placeId = placeId;
	}

	public String getPlaceId(){
		return placeId;
	}
}