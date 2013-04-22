package br.com.company.gwt.client.form;

import java.util.ArrayList;
import java.util.List;

import br.com.company.gwt.client.InstanceService;
import br.com.company.gwt.client.ParcelaServiceAsync;
import br.com.company.gwt.client.component.WebWindow;
import br.com.company.gwt.client.dto.DTOLatLng;
import br.com.company.gwt.client.dto.DTOParcela;
import br.com.company.gwt.client.dto.DTOPropriedade;

import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.Status;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.LabelToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.maps.client.InfoWindow;
import com.google.gwt.maps.client.InfoWindowContent;
import com.google.gwt.maps.client.MapType;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.control.LargeMapControl3D;
import com.google.gwt.maps.client.event.MapMouseMoveHandler;
import com.google.gwt.maps.client.event.MarkerClickHandler;
import com.google.gwt.maps.client.event.PolygonClickHandler;
import com.google.gwt.maps.client.event.PolygonMouseOutHandler;
import com.google.gwt.maps.client.event.PolygonMouseOverHandler;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Icon;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.MarkerOptions;
import com.google.gwt.maps.client.overlay.Polygon;
import com.google.gwt.maps.client.overlay.Polyline;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class FormPanelMapa extends WebWindow {
	
	final ParcelaServiceAsync parcelaService = InstanceService.PARCELA_SERVICE;
	
	private Polygon polygon;
	
	private MapWidget mapWidget;
	
	private DTOParcela dtoParcela;

	private ToolBar toolBarMapa;

	private Status statusLatLong;
	
	public FormPanelMapa() {
		setIconStyle("contato_window");
		setHeading("Mapa");
		setSize(800, 600);
	}	

	public FormPanelMapa(DTOParcela dto) {
		this();
		loadParcelaMapa(dto);
	}

	@Override
	public void initComponents() {
		
		LatLng centerMap = LatLng.newInstance(-9.329662, -40.373468);
		mapWidget = new MapWidget(centerMap, 10);
		mapWidget.setScrollWheelZoomEnabled(true);
		mapWidget.setCurrentMapType(MapType.getHybridMap());
		mapWidget.setGoogleBarEnabled(true);
		mapWidget.addControl(new LargeMapControl3D());
		mapWidget.addMapMouseMoveHandler(new MapMouseMoveHandler() {
			
			@Override
			public void onMouseMove(MapMouseMoveEvent event) {
				statusLatLong.setText(event.getLatLng().toString());  
			}
		});
		
	    ContentPanel panel = new ContentPanel();
	    panel.setHeaderVisible(false);
	    panel.setLayout(new FitLayout());
	    panel.add(mapWidget);
	    
	    toolBarMapa = new ToolBar();
	    
	    statusLatLong = new Status();
	    statusLatLong.setWidth(300);
	    statusLatLong.setBox(true);
	    toolBarMapa.add(new LabelToolItem("Coordenada "));
	    toolBarMapa.add(statusLatLong);
	    
	    add(panel);
	    setBottomComponent(toolBarMapa);
	}

	@Override
	protected void loadDados() {
				
	}
	
	private void save(Polygon polygon){
		List<DTOLatLng> dtoLatLngs = new ArrayList<DTOLatLng>();
		for (int i = 0; i < polygon.getVertexCount(); i++) {
			LatLng latLng = polygon.getVertex(i);
			dtoLatLngs.add(new DTOLatLng(latLng.getLatitude(), latLng.getLongitude()));			
		}
		dtoParcela.setTheGeom(dtoLatLngs);
		parcelaService.save(dtoParcela, new AsyncCallback<Boolean>() {
			
			@Override
			public void onSuccess(Boolean result) {
				Info.display("", "Salvo com sucesso!");
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Info.display("", "Erro ao Salvar!");				
			}
		});
	}
	
	private void loadParcelaMapa(final DTOParcela dtoParcela) {
		this.dtoParcela = dtoParcela;
		if (dtoParcela.getDTOPropriedade() != null){
			DTOLatLng dtoLatLng = dtoParcela.getDTOPropriedade().getCentroid();
			Icon icon = Icon.newInstance("imagens/icones/icon_farm_37.png");
			MarkerOptions markerOptions = MarkerOptions.newInstance(icon);
			markerOptions.setClickable(true);
			Marker markerPropriedade = new Marker(LatLng.newInstance(dtoLatLng.getLatitude(), dtoLatLng.getLongitude()), markerOptions);
			markerPropriedade.addMarkerClickHandler(new MarkerClickHandler() {
				
				@Override
				public void onClick(MarkerClickEvent event) {
					InfoWindow infoWindow = mapWidget.getInfoWindow();
					infoWindow.open(event.getSender(), new InfoWindowContent(dtoParcela.getDTOPropriedade().getNome()));									
				}
			});
			mapWidget.addOverlay(markerPropriedade);	
		}
		if (dtoParcela.getTheGeom() != null && !dtoParcela.getTheGeom().isEmpty()){
			
			DTOPropriedade propriedade = dtoParcela.getDTOPropriedade();
			List<DTOLatLng> dtoLatLngsProp = propriedade.getTheGeom();
			LatLng pontos [] = new LatLng[dtoLatLngsProp.size()];
			for (int i = 0; i < pontos.length; i++) {
				DTOLatLng latLng = dtoLatLngsProp.get(i);
				pontos[i] = LatLng.newInstance(latLng.getLatitude(), latLng.getLongitude());				
			}
			Polyline polyline = new Polyline(pontos, "#00F");
			setHeading("Mapa: Parcela "+ dtoParcela.getCodigo());
			LatLng[] points = new LatLng[dtoParcela.getTheGeom().size()];
			for (int i = 0; i < dtoParcela.getTheGeom().size(); i++) {
				DTOLatLng dtoLatLng = dtoParcela.getTheGeom().get(i); 
				LatLng latLng = LatLng.newInstance(dtoLatLng.getLatitude(), dtoLatLng.getLongitude());
				points[i] = latLng;
			}
			polygon = new Polygon(points, "#000", 2, 0.5, dtoParcela.getDTOCultura().getCorMapa(), 0.7);
			polygon.addPolygonMouseOverHandler(new PolygonMouseOverHandler() {
				
				@Override
				public void onMouseOver(PolygonMouseOverEvent event) {
					polygon.setEditingEnabled(true);
				}
			});
			polygon.addPolygonMouseOutHandler(new PolygonMouseOutHandler() {
				
				@Override
				public void onMouseOut(PolygonMouseOutEvent event) {
					polygon.setEditingEnabled(false);
				}
			});
			polygon.addPolygonClickHandler(new PolygonClickHandler() {
				
				@Override
				public void onClick(PolygonClickEvent event) {
					Polygon polygon = event.getSender();
					boolean deletado = false;
					LatLng latLng = event.getLatLng();
					for (int i = 0; i < polygon.getVertexCount(); i++) {
						LatLng l1 = polygon.getVertex(i);
						if(latLng.getLatitudeRadians() == l1.getLatitudeRadians() && latLng.getLongitudeRadians() == l1.getLongitudeRadians()){
							polygon.deleteVertex(i);
							deletado = true;
							break;
						}
					}
					
					if (!deletado){
						save(polygon);
					}
				}
			});
			mapWidget.addOverlay(polygon);
			mapWidget.addOverlay(polyline);
			DTOLatLng latLngCenter = dtoParcela.getCentroid();
			mapWidget.setCenter(LatLng.newInstance(latLngCenter.getLatitude(), latLngCenter.getLongitude()), 14);
		}
	}

	public DTOParcela getDtoParcela() {
		return dtoParcela;
	}
	
	public void setDtoParcela(DTOParcela dtoParcela) {
		loadParcelaMapa(dtoParcela);
	}

}