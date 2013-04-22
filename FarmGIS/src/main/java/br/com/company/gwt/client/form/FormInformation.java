package br.com.company.gwt.client.form;

import java.util.ArrayList;
import java.util.List;

import br.com.company.gwt.client.InstanceService;
import br.com.company.gwt.client.component.WebMessageBox;
import br.com.company.gwt.client.dto.DTOPropriedade;
import br.com.company.gwt.shared.dto.DTOConsulta03;

import com.extjs.gxt.charts.client.Chart;
import com.extjs.gxt.charts.client.model.BarDataProvider;
import com.extjs.gxt.charts.client.model.ChartModel;
import com.extjs.gxt.charts.client.model.Legend;
import com.extjs.gxt.charts.client.model.Legend.Position;
import com.extjs.gxt.charts.client.model.ScaleProvider;
import com.extjs.gxt.charts.client.model.charts.BarChart;
import com.extjs.gxt.charts.client.model.charts.BarChart.BarStyle;
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.grid.AggregationRowConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.grid.RowNumberer;
import com.extjs.gxt.ui.client.widget.grid.SummaryType;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.table.NumberCellRenderer;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class FormInformation extends Window {

	private ListStore<DTOConsulta03> store;
	private Button showMapa;

	public FormInformation() {
		
		setCollapsible(true);
		setAnimCollapse(false);
		setFrame(true);  
		setHeading("Percentual de área plantada");  
		setButtonAlign(HorizontalAlignment.CENTER);  
		setLayout(new BorderLayout());  
		setSize(640, 480);

		store = new ListStore<DTOConsulta03>();
		
		final NumberFormat number = NumberFormat.getFormat("0.00");
		final NumberCellRenderer<Grid<DTOConsulta03>> numberRenderer = new NumberCellRenderer<Grid<DTOConsulta03>>(number);  

		GridCellRenderer<DTOConsulta03> gridNumber = new GridCellRenderer<DTOConsulta03>() {  
			public String render(DTOConsulta03 model, String property, ColumnData config, int rowIndex, int colIndex,  
					ListStore<DTOConsulta03> store, Grid<DTOConsulta03> grid) {  
				return numberRenderer.render(null, property, model.get(property));
			}
		};
		
		final Chart chart = new Chart("gxt/chart/open-flash-chart.swf");
	  
	    ChartModel model = new ChartModel("Gráfico de HA (Propriedade x Plantado) ",  
	        "font-size: 14px; font-family: Verdana; text-align: center;");
	    model.setBackgroundColour("#fefefe");
	    model.setLegend(new Legend(Position.TOP, true));  
	    model.setScaleProvider(ScaleProvider.ROUNDED_NEAREST_SCALE_PROVIDER);  
	  
	    BarChart bar = new BarChart(BarStyle.GLASS);
	    bar.setColour("#0000cc");
	    bar.setAnimateOnShow(true);
	    bar.setText("Total HA");  
	    BarDataProvider barProvider = new BarDataProvider("hectaresPropriedade", "propriedade");
	    barProvider.bind(store);
	    bar.setDataProvider(barProvider);  
	    model.addChartConfig(bar);
	  
	    bar = new BarChart(BarStyle.GLASS);
	    bar.setAnimateOnShow(true);
	    bar.setColour("#ff6600");
	    bar.setText("HA Plantados");  
	    barProvider = new BarDataProvider("hectaresPlantados");
	    barProvider.bind(store);
	    bar.setDataProvider(barProvider);  
	    model.addChartConfig(bar);  
	  
//	    bar = new BarChart(BarStyle.GLASS);
//	    bar.setAnimateOnShow(true);
//	    bar.setText("Percentual");  
//	    bar.setColour("#FF0000");  
//	    barProvider = new BarDataProvider("percentual");  
//	    barProvider.bind(store);  
//	    bar.setDataProvider(barProvider);  
//	    model.addChartConfig(bar);  
	  
	    chart.setChartModel(model);  

		List<ColumnConfig> configs = new ArrayList<ColumnConfig>();
		
		configs.add(new RowNumberer());

		ColumnConfig column = new ColumnConfig();
		column.setId("propriedade");
		column.setHeader("Propriedade");
		column.setWidth(200);
		configs.add(column);

		column = new ColumnConfig();
		column.setId("hectaresPropriedade");
		column.setHeader("Total H.A");
		column.setWidth(100);
		column.setAlignment(HorizontalAlignment.CENTER); 
		column.setRenderer(gridNumber); 
		configs.add(column);

		column = new ColumnConfig();
		column.setId("hectaresPlantados");
		column.setHeader("H.A. Plantados");
		column.setWidth(100);
		column.setAlignment(HorizontalAlignment.CENTER);
		column.setRenderer(gridNumber);
		configs.add(column);
		
		column = new ColumnConfig();
		column.setId("percentual");
		column.setHeader("Percentual (%)");
		column.setAlignment(HorizontalAlignment.CENTER);
		column.setWidth(100);
		column.setRenderer(gridNumber);
		configs.add(column);


		ColumnModel cm = new ColumnModel(configs);

		AggregationRowConfig<DTOConsulta03> averages = new AggregationRowConfig<DTOConsulta03>();  
		averages.setHtml("propriedade", "Totais");

		averages.setSummaryType("hectaresPropriedade", SummaryType.SUM);
		averages.setSummaryFormat("hectaresPropriedade", number);

		averages.setSummaryType("hectaresPlantados", SummaryType.SUM);
		averages.setSummaryFormat("hectaresPlantados", number);
		
//		averages.setSummaryType("percentual", SummaryType.AVG);
//		averages.setSummaryFormat("percentual", number);
		
		cm.addAggregationRow(averages);

		final Grid<DTOConsulta03> grid = new Grid<DTOConsulta03>(store, cm); 
		grid.setBorders(false);
		grid.getView().setForceFit(true);
		
		showMapa = new Button("Show Mapa");
	    showMapa.addSelectionListener(new SelectionListener<ButtonEvent>() {
	    	
	    	@Override
	    	public void componentSelected(ButtonEvent ce) {
	    		DTOConsulta03 dto = grid.getSelectionModel().getSelectedItem();
	    		if (dto != null){
	    			
	    			InstanceService.PROPRIEDADE_SERVICE.getById(dto.getPropriedadeId(), new AsyncCallback<DTOPropriedade>() {
	    				public void onFailure(Throwable caught) {
	    					
	    				};
	    				
	    				public void onSuccess(DTOPropriedade result) {
	    					FormPanelMapaPropriedade panelMapa = new FormPanelMapaPropriedade(result);
	    					panelMapa.setVisible(true);
	    				};
					});
	    			
	    		}else{
	    			Info.display("Info", "Selecione a consulta!");
	    		}
	    	}
	    });
	    
	    ToolBar toolBar = new ToolBar();
	    toolBar.add(showMapa);
		
	    setTopComponent(toolBar);
		add(grid, new BorderLayoutData(LayoutRegion.SOUTH, 130));
		add(chart, new BorderLayoutData(LayoutRegion.CENTER));
		
		loadDados();

	}

	private void loadDados() {
		InstanceService.PROPRIEDADE_SERVICE.getDadosConsulta03(new AsyncCallback<List<DTOConsulta03>>() {
			
			@Override
			public void onSuccess(List<DTOConsulta03> listaConsulta03) {
				store.add(listaConsulta03);	
			}
			
			@Override
			public void onFailure(Throwable caught) {
				WebMessageBox.error(caught.getLocalizedMessage());				
			}
		});
	}

}
