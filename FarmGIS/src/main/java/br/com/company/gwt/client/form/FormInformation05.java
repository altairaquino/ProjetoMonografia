package br.com.company.gwt.client.form;

import java.util.ArrayList;
import java.util.List;

import br.com.company.gwt.client.InstanceService;
import br.com.company.gwt.client.component.WebMessageBox;
import br.com.company.gwt.shared.dto.DTOConsulta05;

import com.extjs.gxt.charts.client.Chart;
import com.extjs.gxt.charts.client.model.BarDataProvider;
import com.extjs.gxt.charts.client.model.ChartModel;
import com.extjs.gxt.charts.client.model.Legend;
import com.extjs.gxt.charts.client.model.Legend.Position;
import com.extjs.gxt.charts.client.model.ScaleProvider;
import com.extjs.gxt.charts.client.model.charts.BarChart;
import com.extjs.gxt.charts.client.model.charts.BarChart.BarStyle;
import com.extjs.gxt.charts.client.model.charts.PieChart;
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
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
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class FormInformation05 extends Window {

	private ListStore<DTOConsulta05> store;
	private Chart chart;

	public FormInformation05() {

		setCollapsible(true);
		setAnimCollapse(false);
		setFrame(true);
		setHeading("Quantidade de Hectares por Cultura");
		setButtonAlign(HorizontalAlignment.CENTER);
		setLayout(new BorderLayout());
		setSize(640, 480);

		store = new ListStore<DTOConsulta05>();

		final NumberFormat number = NumberFormat.getFormat("0.00");
		final NumberCellRenderer<Grid<DTOConsulta05>> numberRenderer = new NumberCellRenderer<Grid<DTOConsulta05>>(number);  

		GridCellRenderer<DTOConsulta05> gridNumber = new GridCellRenderer<DTOConsulta05>() {  
			public String render(DTOConsulta05 model, String property, ColumnData config, int rowIndex, int colIndex,  
					ListStore<DTOConsulta05> store, Grid<DTOConsulta05> grid) {  
				return numberRenderer.render(null, property, model.get(property));
			}
		};

		chart = new Chart("gxt/chart/open-flash-chart.swf");

		ChartModel model = new ChartModel("Quantidade de Hectares por Cultura",  
		"font-size: 14px; font-family: Verdana; text-align: center;");
		model.setBackgroundColour("#fefefe");
		model.setLegend(new Legend(Position.TOP, true));
		model.setScaleProvider(ScaleProvider.ROUNDED_NEAREST_SCALE_PROVIDER);  
		
		BarChart bar = new BarChart(BarStyle.GLASS);
		bar.setColour("#0000cc");
		bar.setAnimateOnShow(true);
		bar.setText("Total HA");
		BarDataProvider barProvider = new BarDataProvider("HAPlantados", "cultura");
		barProvider.bind(store);
		bar.setDataProvider(barProvider);
		model.addChartConfig(bar);

		chart.setChartModel(getPieChartData());

		List<ColumnConfig> configs = new ArrayList<ColumnConfig>();
		
		configs.add(new RowNumberer());

		ColumnConfig column = new ColumnConfig();
		column.setId("cultura");
		column.setHeader("Cultura");
		column.setWidth(200);
		configs.add(column);

		column = new ColumnConfig();
		column.setId("HAPlantados");
		column.setHeader("Total H.A");
		column.setWidth(100);
		column.setAlignment(HorizontalAlignment.CENTER); 
		column.setRenderer(gridNumber); 
		configs.add(column);

		ColumnModel cm = new ColumnModel(configs);

		AggregationRowConfig<DTOConsulta05> averages = new AggregationRowConfig<DTOConsulta05>();  
		averages.setHtml("cultura", "Totais");

		averages.setSummaryType("HAPlantados", SummaryType.SUM);
		averages.setSummaryFormat("HAPlantados", number);

		cm.addAggregationRow(averages);

		final Grid<DTOConsulta05> grid = new Grid<DTOConsulta05>(store, cm); 
		grid.getView().setForceFit(true);
		
		add(grid, new BorderLayoutData(LayoutRegion.SOUTH, 160));
		add(chart, new BorderLayoutData(LayoutRegion.CENTER));
		
		ToolBar toolBar = new ToolBar();
		
		Button button = new Button("Atualiza");
		button.addSelectionListener(new SelectionListener<ButtonEvent>() {
			
			@Override
			public void componentSelected(ButtonEvent ce) {
				loadDados();				
			}
		});
		
		toolBar.add(button);
		setTopComponent(toolBar);

		loadDados();

	}

	private void loadDados() {
		InstanceService.PROPRIEDADE_SERVICE.getDadosConsulta05(new AsyncCallback<List<DTOConsulta05>>() {

			@Override
			public void onSuccess(List<DTOConsulta05> listaConsulta05) {
				store.removeAll();
				store.add(listaConsulta05);
				chart.setChartModel(getPieChartData());
				chart.recalculate();
				chart.refresh();
			}

			@Override
			public void onFailure(Throwable caught) {
				WebMessageBox.error(caught.getLocalizedMessage());
			}
		});
	}
	
	private ChartModel getPieChartData() {  
		ChartModel cm = new ChartModel("Quantidade de Hectares por Cultura",
				"font-size: 14px; font-family: Verdana; text-align: center;");
		cm.setBackgroundColour("#fffff5");  
		Legend lg = new Legend(Position.RIGHT, true);  
		lg.setPadding(10);
		cm.setLegend(lg);

		PieChart pie = new PieChart();
		pie.setAlpha(0.5f);
		pie.setNoLabels(true);
		pie.setTooltip("#val# HA<br>#percent#%");
//		pie.setColours("#ff0000", "#00aa00", "#0000ff", "#ff9900", "#ff00ff");

		List<String> colors = new ArrayList<String>();
		List<PieChart.Slice> slices = new ArrayList<PieChart.Slice>();

		for (DTOConsulta05 dados : store.getModels()) {
			slices.add(new PieChart.Slice(dados.getHAPlantados(), dados.getCultura(), dados.getCultura()));
			colors.add(dados.getColor());
		}
		
		GWT.log(colors.toString());
		
		pie.setColours(colors);
		pie.addSlices(slices);		

		cm.addChartConfig(pie);  
		return cm;  
	}

}