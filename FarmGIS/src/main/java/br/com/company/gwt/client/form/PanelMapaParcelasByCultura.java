package br.com.company.gwt.client.form;

import java.util.List;

import br.com.company.gwt.client.InstanceService;
import br.com.company.gwt.client.PropriedadeServiceAsync;
import br.com.company.gwt.client.component.WebWindow;
import br.com.company.gwt.client.dto.DTOLatLng;
import br.com.company.gwt.client.dto.DTOParcela;

import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Status;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.LabelToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.maps.client.MapType;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.control.LargeMapControl3D;
import com.google.gwt.maps.client.event.MapMouseMoveHandler;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Polygon;

public class PanelMapaParcelasByCultura extends WebWindow {
	
	final PropriedadeServiceAsync propriedadeService = InstanceService.PROPRIEDADE_SERVICE;
	
	private MapWidget mapWidget;
	
	private ToolBar toolBarMapa;

	private Status statusLatLong;
	
	public PanelMapaParcelasByCultura() {
		setIconStyle("contato_window");
		setSize(800, 600);
	}

	public PanelMapaParcelasByCultura(List<DTOParcela> culturas) {
		this();
		loadCulturasMapa(culturas);
	}

	@Override
	public void initComponents() {
		
		LatLng centerMap = LatLng.newInstance(-9.329662, -40.373468);
		mapWidget = new MapWidget(centerMap, 12);
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
	
	private void loadCulturasMapa(List<DTOParcela> parcelas) {
		for (DTOParcela parcela : parcelas) {
			LatLng[] points = new LatLng[parcela.getTheGeom().size()];
			for (int i = 0; i < parcela.getTheGeom().size(); i++) {
				DTOLatLng dtoLatLng = parcela.getTheGeom().get(i);
				LatLng latLng = LatLng.newInstance(dtoLatLng.getLatitude(), dtoLatLng.getLongitude());
				points[i] = latLng;
			}
			Polygon parcelaArea = new Polygon(points, "#000", 2, 0.5, parcela.getDTOCultura().getCorMapa(), 0.7);
			mapWidget.addOverlay(parcelaArea);
			
			mapWidget.setCenter(parcelaArea.getVertex(0));
		}
		setHeading("Mapa: Parcelas da Cultura de ");

	}

}