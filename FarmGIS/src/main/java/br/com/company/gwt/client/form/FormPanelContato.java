package br.com.company.gwt.client.form;

import java.util.ArrayList;
import java.util.List;

import br.com.company.gwt.client.ContatoServiceAsync;
import br.com.company.gwt.client.InstanceService;
import br.com.company.gwt.client.component.WebMessageBox;
import br.com.company.gwt.client.component.WebWindow;
import br.com.company.gwt.shared.dto.DTOContato;

import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class FormPanelContato extends WebWindow {
	
	final ContatoServiceAsync contatoService = InstanceService.CONTATO_SERVICE;
	private ListStore<DTOContato> listStore;
	
	
	public FormPanelContato() {
		setIconStyle("contato_window");
		setHeading("Contatos");
		setSize(800, 600);
		fillGrid();
	}
	
	private void fillGrid() {
		contatoService.list(null, new AsyncCallback<List<DTOContato>>() {
			
			@Override
			public void onSuccess(List<DTOContato> listContatos) {
				listStore.add(listContatos);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				WebMessageBox.error(caught.getMessage());
			}
		});
		
	}

	@Override
	public void initComponents() {
		
		listStore = new ListStore<DTOContato>();

	    List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
	    columns.add(new ColumnConfig("ctcmatr", "Matric", 40));
	    columns.add(new ColumnConfig("ctcnome", "Nome", 150));
	    columns.add(new ColumnConfig("ctcfunc", "Função", 100));
	    columns.add(new ColumnConfig("ctcfone", "Fone", 100));
	    columns.add(new ColumnConfig("ctdnasc", "Dt. Nasc.", 70));
	    columns.add(new ColumnConfig("ctcmail", "E-Mail", 120));
	    columns.add(new ColumnConfig("ctlativ", "Ativo", 50));
	    ColumnModel cm = new ColumnModel(columns);

	    Grid<DTOContato> grid = new Grid<DTOContato>(listStore, cm);
	    grid.getView().setForceFit(true);
	    
	    ContentPanel panel = new ContentPanel();
	    panel.setHeaderVisible(false);
	    panel.setLayout(new FitLayout());
	    panel.add(grid);

	    add(panel);
	}

	@Override
	protected void loadDados() {
		// TODO Auto-generated method stub
		
	}

}
