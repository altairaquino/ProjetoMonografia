package br.com.company.gwt.client.form;

import java.util.ArrayList;
import java.util.List;

import br.com.company.gwt.client.InstanceService;
import br.com.company.gwt.client.PropriedadeServiceAsync;
import br.com.company.gwt.client.component.WebMessageBox;
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
import com.google.gwt.maps.client.MapType;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.control.LargeMapControl3D;
import com.google.gwt.maps.client.event.MapMouseMoveHandler;
import com.google.gwt.maps.client.event.PolygonClickHandler;
import com.google.gwt.maps.client.event.PolygonMouseOutHandler;
import com.google.gwt.maps.client.event.PolygonMouseOverHandler;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.Polygon;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class FormPanelMapaPropriedade extends WebWindow {
	
	final PropriedadeServiceAsync propriedadeService = InstanceService.PROPRIEDADE_SERVICE;
	
	private Polygon polygon;
	
	private MapWidget mapWidget;
	
	private DTOPropriedade dtoPropriedade;

	private ToolBar toolBarMapa;

	private Status statusLatLong;
	
	public FormPanelMapaPropriedade() {
		setIconStyle("contato_window");
		setHeading("Mapa de propriedades");
		setSize(800, 600);
	}

	public FormPanelMapaPropriedade(DTOPropriedade dto) {
		this();
		loadPropriedadeMapa(dto);
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
		dtoPropriedade.setTheGeom(dtoLatLngs);
		propriedadeService.save(dtoPropriedade, new AsyncCallback<Boolean>() {
			
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
	
	private void loadPropriedadeMapa(DTOPropriedade dtoPropriedade) {
		this.dtoPropriedade = dtoPropriedade;
		if (dtoPropriedade.getDtoCidade() != null){
			DTOLatLng dtoLatLng = dtoPropriedade.getDtoCidade().getTheGeom();
			Marker markerCidade = new Marker(LatLng.newInstance(dtoLatLng.getLatitude(), dtoLatLng.getLongitude()));
			mapWidget.addOverlay(markerCidade);				
		}
		if (dtoPropriedade.getTheGeom() != null && !dtoPropriedade.getTheGeom().isEmpty()){
			
			loadParcelas(dtoPropriedade.getId());
			
			setHeading("Mapa: Propriedade "+ dtoPropriedade.getNome());
			LatLng[] points = new LatLng[dtoPropriedade.getTheGeom().size()];
			for (int i = 0; i < dtoPropriedade.getTheGeom().size(); i++) {
				DTOLatLng dtoLatLng = dtoPropriedade.getTheGeom().get(i);
				LatLng latLng = LatLng.newInstance(dtoLatLng.getLatitude(), dtoLatLng.getLongitude());
				points[i] = latLng;
			}
			polygon = new Polygon(points, "#000", 2, 0.5, "#FFF", 0.7);
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
			DTOLatLng latLngCenter = dtoPropriedade.getCentroid();
			mapWidget.setCenter(LatLng.newInstance(latLngCenter.getLatitude(), latLngCenter.getLongitude()), 14);
		}
	}
	
	private void loadParcelas(Integer propriedadeId){
		
		InstanceService.PARCELA_SERVICE.listByPropriedade(propriedadeId, new AsyncCallback<List<DTOParcela>>() {
			@Override
			public void onSuccess(List<DTOParcela> parcelas) {
				
				for (DTOParcela parcela : parcelas) {
					LatLng[] points = new LatLng[parcela.getTheGeom().size()];
					for (int i = 0; i < parcela.getTheGeom().size(); i++) {
						DTOLatLng dtoLatLng = parcela.getTheGeom().get(i);
						LatLng latLng = LatLng.newInstance(dtoLatLng.getLatitude(), dtoLatLng.getLongitude());
						points[i] = latLng;
					}
					Polygon parcelaArea = new Polygon(points, "#000", 2, 0.5, parcela.getDTOCultura().getCorMapa(), 0.7);
					mapWidget.addOverlay(parcelaArea);		
				}
				
			}
			@Override
			public void onFailure(Throwable caught) {
				WebMessageBox.error(caught.getLocalizedMessage());
			}
		});
	}

	public DTOPropriedade getDtoPropriedade() {
		return dtoPropriedade;
	}
	
	public void setDtoParcela(DTOPropriedade dtoPropriedade) {
		loadPropriedadeMapa(dtoPropriedade);
	}

}