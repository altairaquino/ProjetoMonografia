package br.com.company.gwt.client.dto;

import com.extjs.gxt.ui.client.data.BaseModelData;
import com.google.gwt.user.client.rpc.IsSerializable;

public class DTOLatLng extends BaseModelData implements IsSerializable{
	
	private static final long serialVersionUID = 5799981071547114154L;

	public DTOLatLng() {
		super();
	}
	
	public DTOLatLng(Double latitude, Double longitude) {
		set("latitude", latitude);
		set("longitude", longitude);
	}
	
	public Double getLatitude() {
		return get("latitude");
	}

	public void setLatitude(Double latitude) {
		set("latitude", latitude);
	}

	public Double getLongitude() {
		return get("longitude");
	}

	public void setLongitude(Double longitude) {
		set("longitude", longitude);
	}
	
	@Override
	public int hashCode() {
		int hashCode = 0;
		if (this.getLatitude() != null && this.getLongitude() != null){
			hashCode = (int)((double)this.getLatitude() - this.getLatitude().intValue());
		}
		return hashCode;
	}
	
	@Override
	public String toString() {
		return "Latitude = ["+this.getLatitude()+", "+this.getLongitude()+"]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof DTOLatLng)){
			return false;
		}
		if(this.toString().equals(obj.toString())) {
			return true;
		}
		return false;
	}

}
