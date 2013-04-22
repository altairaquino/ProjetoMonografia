package br.com.company.gwt.client.form;

import java.util.ArrayList;
import java.util.List;

import br.com.company.gwt.client.InstanceService;
import br.com.company.gwt.client.PropriedadeServiceAsync;
import br.com.company.gwt.client.component.WebMessageBox;
import br.com.company.gwt.client.component.WebUtil;
import br.com.company.gwt.client.component.WebWindow;
import br.com.company.gwt.client.dto.DTOCidade;
import br.com.company.gwt.client.dto.DTOPropriedade;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.Record;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.grid.CellEditor;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.EditorGrid;
import com.extjs.gxt.ui.client.widget.grid.EditorGrid.ClicksToEdit;
import com.extjs.gxt.ui.client.widget.grid.GridSelectionModel;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class FormPanelPropriedade extends WebWindow {
	
	final PropriedadeServiceAsync propriedadeService = InstanceService.PROPRIEDADE_SERVICE;
	private ListStore<DTOPropriedade> listStore;
	private ListStore<DTOCidade> listStoreCidade;
	private Button salvaPropriedade;
	private Button addPropriedade;
	private Button showMapa;
	private EditorGrid<DTOPropriedade> grid;	
	
	public FormPanelPropriedade() {
		setIconStyle("contato_window");
		setHeading("Propriedades Rurais - Fazendas");
		setSize(640, 480);
		fillGrid();
	}
	
	private void fillGrid() {
		propriedadeService.listAll(new AsyncCallback<List<DTOPropriedade>>() {
			
			@Override
			public void onSuccess(List<DTOPropriedade> listContatos) {
				listStore.removeAll();
				listStore.add(listContatos);
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
		
		listStore = new ListStore<DTOPropriedade>();
		listStoreCidade = new ListStore<DTOCidade>();
		
		InstanceService.CIDADE_SERVICE.listAll(new AsyncCallback<List<DTOCidade>>() {
			
			@Override
			public void onFailure(Throwable caught) {
				MessageBox.alert("Erro", "Error: " +caught.getMessage(), null);
			}
			@Override
			public void onSuccess(List<DTOCidade> result) {
				listStoreCidade.add(result);			
			}
		});

	    List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
	    columns.add(new ColumnConfig("id", "ID", 40));
	    
	    ColumnConfig columnConfig = new ColumnConfig("nome", "Nome", 150);
	    columnConfig.setEditor(new CellEditor(new TextField<String>()));
	    columns.add(columnConfig);
	    
	    columnConfig = WebUtil.createColumnEditorComboBox("dtoCidade", "Cidade", "id", "nome");
	    columns.add(columnConfig);
	    ComboBox<DTOCidade> cbCidade = (ComboBox<DTOCidade>) columnConfig.getEditor().getField();
	    cbCidade.setStore(listStoreCidade);
	    
	    ColumnModel cm = new ColumnModel(columns);

	    grid = new EditorGrid<DTOPropriedade>(listStore, cm);
	    grid.getView().setForceFit(true);
	    grid.setClicksToEdit(ClicksToEdit.TWO);
	    grid.setSelectionModel(new GridSelectionModel<DTOPropriedade>());
	    
	    ContentPanel panel = new ContentPanel();
	    panel.setHeaderVisible(false);
	    panel.setLayout(new FitLayout());
	    panel.add(grid);
	    
	    salvaPropriedade = new Button("Salvar");
	    salvaPropriedade.addSelectionListener(new SelectionListener<ButtonEvent>() {
	    	@Override
	    	public void componentSelected(ButtonEvent ce) {
	    		for (Record record : listStore.getModifiedRecords()) {
	    			propriedadeService.save((DTOPropriedade)record.getModel(), salvaPropriedadeAsync);
				}
	    		grid.getStore().commitChanges();
	    	}
		});
	    
	    addPropriedade = new Button("Nova Propriedade");
	    addPropriedade.addSelectionListener(new SelectionListener<ButtonEvent>() {
			
			@Override
			public void componentSelected(ButtonEvent ce) {
				listStore.add(new DTOPropriedade());
			}
		});

	    showMapa = new Button("Show Mapa");
	    showMapa.addSelectionListener(new SelectionListener<ButtonEvent>() {
	    	
	    	@Override
	    	public void componentSelected(ButtonEvent ce) {
	    		DTOPropriedade dto = grid.getSelectionModel().getSelectedItem();
	    		if (dto != null){
	    			FormPanelMapaPropriedade panelMapa = new FormPanelMapaPropriedade(dto);
	    			panelMapa.setVisible(true);  			
	    		}else{
	    			Info.display("", "Selecione a propriedade!");
	    		}
	    	}
	    });
	    
//	    addToToolBar(addPropriedade);
	    addToToolBar(salvaPropriedade);
	    addToToolBar(showMapa);
	    

	    add(panel);
	}

	@Override
	protected void loadDados() {
		fillGrid();
	}
	
	AsyncCallback<Boolean> salvaPropriedadeAsync = new AsyncCallback<Boolean>() {
		
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