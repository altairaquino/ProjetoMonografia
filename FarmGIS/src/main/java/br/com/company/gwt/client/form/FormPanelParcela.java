package br.com.company.gwt.client.form;

import java.util.ArrayList;
import java.util.List;

import br.com.company.gwt.client.InstanceService;
import br.com.company.gwt.client.ParcelaServiceAsync;
import br.com.company.gwt.client.PropriedadeServiceAsync;
import br.com.company.gwt.client.component.WebMessageBox;
import br.com.company.gwt.client.component.WebUtil;
import br.com.company.gwt.client.component.WebWindow;
import br.com.company.gwt.client.dto.DTOCultura;
import br.com.company.gwt.client.dto.DTOParcela;
import br.com.company.gwt.client.dto.DTOPropriedade;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.Record;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.grid.CellEditor;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.EditorGrid;
import com.extjs.gxt.ui.client.widget.grid.EditorGrid.ClicksToEdit;
import com.extjs.gxt.ui.client.widget.grid.GridSelectionModel;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class FormPanelParcela extends WebWindow {
	
	final ParcelaServiceAsync parcelaService = InstanceService.PARCELA_SERVICE;
	
	private ListStore<DTOParcela> listStore;
	private ListStore<DTOPropriedade> listStoreProp;
	private ListStore<DTOCultura> listStoreCultura;
	private ComboBox<DTOPropriedade> comboProp;
	private Button addParcela;
	private Button salvaParcela;
	private Button showMapa;

	public FormPanelParcela() {
		setIconStyle("contato_window");
		setHeading("Parcelas da Propriedade");
		setSize(640, 480);
		fillGrid();
	}
	
	private void fillGrid() {
		parcelaService.listAll(new AsyncCallback<List<DTOParcela>>() {
			
			@Override
			public void onSuccess(List<DTOParcela> listParcelas) {
				listStore.removeAll();
				listStore.add(listParcelas);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				WebMessageBox.error(caught.getMessage());
			}
		});
		
	}
	
	private void fillGrid(Integer idPropriedade) {
		parcelaService.listByPropriedade(idPropriedade, new AsyncCallback<List<DTOParcela>>() {
			
			@Override
			public void onSuccess(List<DTOParcela> listParcelas) {
				listStore.removeAll();
				listStore.add(listParcelas);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				WebMessageBox.error(caught.getMessage());
			}
		});
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initComponents() {
		
		listStore = new ListStore<DTOParcela>();
		listStoreProp = new ListStore<DTOPropriedade>();
		listStoreCultura = new ListStore<DTOCultura>();

	    List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
	    ColumnConfig columnConfig = new ColumnConfig("id", "ID", 40);
	    columns.add(columnConfig);
	    columnConfig = new ColumnConfig("codigo", "Código", 150);
	    columnConfig.setEditor(new CellEditor(new TextField<String>()));
	    columns.add(columnConfig);
	    columnConfig = WebUtil.createColumnEditorComboBox("dtoPropriedade", "Propriedade", "id", "nome");
	    columns.add(columnConfig);
	    ComboBox<DTOPropriedade> cbprop = (ComboBox<DTOPropriedade>) columnConfig.getEditor().getField();
	    cbprop.setStore(listStoreProp);
	    
	    columnConfig = WebUtil.createColumnEditorComboBox("dtoCultura", "Cultura", "id", "nome");
	    columns.add(columnConfig);
	    ComboBox<DTOCultura> cbCultura = (ComboBox<DTOCultura>) columnConfig.getEditor().getField();
	    cbCultura.setStore(listStoreCultura);
	    ColumnModel cm = new ColumnModel(columns);

	    final EditorGrid<DTOParcela> grid = new EditorGrid<DTOParcela>(listStore, cm);
	    grid.getView().setForceFit(true);
	    grid.setClicksToEdit(ClicksToEdit.TWO);
	    grid.setSelectionModel(new GridSelectionModel<DTOParcela>());
	    
	    ContentPanel panel = new ContentPanel();
	    panel.setHeaderVisible(false);
	    panel.setLayout(new FitLayout());
	    panel.add(grid);
	    
	    InstanceService.CULTURA_SERVICE.listAll(new AsyncCallback<List<DTOCultura>>() {
	    	
	    	@Override
	    	public void onFailure(Throwable caught) {
	    		WebMessageBox.alert("Erro", "Error: " +caught.getMessage());
	    	}
	    	@Override
	    	public void onSuccess(List<DTOCultura> result) {
	    		listStoreCultura.add(result);	    		
	    	}
		});
	    
	    comboProp = new ComboBox<DTOPropriedade>();
	    comboProp.setDisplayField("nome");
	    comboProp.setStore(listStoreProp);
	    comboProp.setForceSelection(true);
	    comboProp.setTriggerAction(TriggerAction.ALL);
	    comboProp.addSelectionChangedListener(new SelectionChangedListener<DTOPropriedade>() {
			
			@Override
			public void selectionChanged(SelectionChangedEvent<DTOPropriedade> se) {
				DTOPropriedade dtoPropriedade = se.getSelectedItem();
				if (dtoPropriedade != null){
					fillGrid(dtoPropriedade.getId());
				}
			}
		});
	    
	    PropriedadeServiceAsync propriedadeService = InstanceService.PROPRIEDADE_SERVICE;
	    
	    propriedadeService.listAll(new AsyncCallback<List<DTOPropriedade>>() {
	    	@Override
	    	public void onFailure(Throwable caught) {
	    		Info.display("Error", "Erro: "+ caught.getLocalizedMessage());	    		
	    	}
	    	@Override
	    	public void onSuccess(List<DTOPropriedade> result) {
	    		listStoreProp.add(result);
	    	}
		});
	    
	    salvaParcela = new Button("Salvar");
	    salvaParcela.addSelectionListener(new SelectionListener<ButtonEvent>() {
	    	@Override
	    	public void componentSelected(ButtonEvent ce) {
	    		for (Record record : listStore.getModifiedRecords()) {
	    			parcelaService.save((DTOParcela)record.getModel(), salvaParcelaAsync);
				}
	    		grid.getStore().commitChanges();
	    	}
		});
	    
	    addParcela = new Button("Nova Parcela");
	    addParcela.addSelectionListener(new SelectionListener<ButtonEvent>() {
			
			@Override
			public void componentSelected(ButtonEvent ce) {
				listStore.add(new DTOParcela());
			}
		});

	    showMapa = new Button("Show Mapa");
	    showMapa.addSelectionListener(new SelectionListener<ButtonEvent>() {
	    	
	    	@Override
	    	public void componentSelected(ButtonEvent ce) {
	    		DTOParcela dto = grid.getSelectionModel().getSelectedItem();
	    		if (dto != null){
	    			FormPanelMapa panelMapa = new FormPanelMapa(dto);
	    			panelMapa.setVisible(true);  			
	    		}else{
	    			Info.display("", "Selecione a parcela!");
	    		}
	    	}
	    });
	    
	    addToToolBar(comboProp);
//	    addToToolBar(addParcela);
	    addToToolBar(salvaParcela);
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
			MessageBox.alert("Erro", "Error: " +caught.getMessage(), null);				
		}
	};
	
}