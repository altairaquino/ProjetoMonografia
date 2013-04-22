package br.com.company.gwt.client.form;

import java.util.ArrayList;
import java.util.List;

import br.com.company.gwt.client.CulturaServiceAsync;
import br.com.company.gwt.client.InstanceService;
import br.com.company.gwt.client.component.WebMessageBox;
import br.com.company.gwt.client.component.WebWindow;
import br.com.company.gwt.client.dto.DTOCultura;
import br.com.company.gwt.client.dto.DTOParcela;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.Record;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.grid.CellEditor;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.EditorGrid;
import com.extjs.gxt.ui.client.widget.grid.EditorGrid.ClicksToEdit;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.grid.GridSelectionModel;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class FormPanelCultura extends WebWindow {
	
	final CulturaServiceAsync culturaService = InstanceService.CULTURA_SERVICE;
	
	private ListStore<DTOCultura> listStoreCultura;
	private Button addCultura;
	private Button salvaCultura;
	private Button showMapa;

	public FormPanelCultura() {
		setIconStyle("contato_window");
		setHeading("Culturas");
		setSize(640, 480);
		fillGrid();
	}
	
	private void fillGrid() {
		culturaService.listAll(new AsyncCallback<List<DTOCultura>>() {
			
			@Override
			public void onSuccess(List<DTOCultura> listParcelas) {
				listStoreCultura.removeAll();
				listStoreCultura.add(listParcelas);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				WebMessageBox.error(caught.getMessage());
			}
		});		
	}
	
	@Override
	public void initComponents() {
		
		listStoreCultura = new ListStore<DTOCultura>();

	    List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
	    ColumnConfig columnConfig = new ColumnConfig("id", "ID", 40);
	    columns.add(columnConfig);
	    columnConfig = new ColumnConfig("nome", "Nome", 150);
	    columnConfig.setEditor(new CellEditor(new TextField<String>()));
	    columns.add(columnConfig);
	    columnConfig = new ColumnConfig("corMapa", "Cor no Mapa", 150);
	    columnConfig.setEditor(new CellEditor(new TextField<String>()));
	    columnConfig.setRenderer(new GridCellRenderer<DTOCultura>() {	    	
	    	
	    	@Override
	    	public String render(DTOCultura model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<DTOCultura> store, Grid<DTOCultura> grid) {
	    		String value = model.get(property);
	    		config.style = "background-color: "+value+";";
	            return value; 
	    	};
		});
	    columns.add(columnConfig);
	    
	    ColumnModel cm = new ColumnModel(columns);

	    final EditorGrid<DTOCultura> grid = new EditorGrid<DTOCultura>(listStoreCultura, cm);
	    grid.getView().setForceFit(true);
	    grid.setClicksToEdit(ClicksToEdit.TWO);
	    grid.setSelectionModel(new GridSelectionModel<DTOCultura>());
	    
	    ContentPanel panel = new ContentPanel();
	    panel.setHeaderVisible(false);
	    panel.setLayout(new FitLayout());
	    panel.add(grid);
	    
	    salvaCultura = new Button("Salvar");
	    salvaCultura.addSelectionListener(new SelectionListener<ButtonEvent>() {
	    	@Override
	    	public void componentSelected(ButtonEvent ce) {
	    		for (Record record : listStoreCultura.getModifiedRecords()) {
	    			culturaService.save((DTOCultura)record.getModel(), salvaParcelaAsync);
				}
	    		grid.getStore().commitChanges();
	    	}
		});
	    
	    addCultura = new Button("Nova Parcela");
	    addCultura.addSelectionListener(new SelectionListener<ButtonEvent>() {
			
			@Override
			public void componentSelected(ButtonEvent ce) {
				listStoreCultura.add(new DTOCultura());
			}
		});
	    
	    showMapa = new Button("Show Mapa");
	    showMapa.addSelectionListener(new SelectionListener<ButtonEvent>() {
	    	
	    	@Override
	    	public void componentSelected(ButtonEvent ce) {
	    		DTOCultura dto = grid.getSelectionModel().getSelectedItem();
	    		
	    		
	    		if (dto != null){
	    			InstanceService.PARCELA_SERVICE.listByCultura(dto.getId(), new AsyncCallback<List<DTOParcela>>() {
	    				
	    				@Override
	    				public void onFailure(Throwable caught) {
	    					WebMessageBox.error(caught.getLocalizedMessage());
	    				}
	    				@Override
	    				public void onSuccess(List<DTOParcela> result) {
	    					PanelMapaParcelasByCultura panelMapa = new PanelMapaParcelasByCultura(result);
	    					panelMapa.setVisible(true);
	    				}
					});
	    			
	    		}else{
	    			Info.display("Info", "Selecione a cultura!");
	    		}
	    	}
	    });
	    
	    addToToolBar(addCultura);
	    addToToolBar(salvaCultura);
	    addToToolBar(showMapa);

	    add(panel);
	}

	@Override
	protected void loadDados() {
		fillGrid();
	}
	
	AsyncCallback<Boolean> salvaParcelaAsync = new AsyncCallback<Boolean>() {
		
		@Override
		public void onSuccess(Boolean result) {
			if (result){
				Info.display("INFO", "Salvo com sucesso!");
			}
		}
		
		@Override
		public void onFailure(Throwable caught) {
			WebMessageBox.alert("Erro", "Error: " +caught.getMessage());				
		}
	};
	
}