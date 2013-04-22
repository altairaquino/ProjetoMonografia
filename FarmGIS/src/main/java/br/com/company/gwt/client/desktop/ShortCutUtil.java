package br.com.company.gwt.client.desktop;

import java.util.ArrayList;
import java.util.List;

import br.com.company.gwt.client.component.WebUtil;
import br.com.company.gwt.client.mvc.FormManager;

import com.extjs.gxt.desktop.client.Shortcut;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Window;

public class ShortCutUtil {

	public static List<Shortcut> getShortcuts(){

		List<Shortcut> shortCuts = new ArrayList<Shortcut>();

		SelectionListener<ComponentEvent> shortcutListener = new SelectionListener<ComponentEvent>() {
			@Override
			public void componentSelected(ComponentEvent ce) {
				Shortcut shortcut = (Shortcut)ce.getSource();
				Window window = (Window)shortcut.getData("window");
				if (window != null){
					DesktopAppView.getInstance().addWindow(window);
				}
			}
		};

//		shortCuts.add(WebUtil.createShortcut("Mapas", "mapa-shortcut", FormManager.FormPanelMapaInstance));
		shortCuts.add(WebUtil.createShortcut("Parcelas", "mapa-shortcut", FormManager.FormPanelParcelaInstance));
		shortCuts.add(WebUtil.createShortcut("Fazendas", "fazenda-shortcut", FormManager.FormPanelPropriedadeInstance));
		shortCuts.add(WebUtil.createShortcut("Culturas", "tree-shortcut", FormManager.FormPanelCulturaInstance));
		
		for (Shortcut shortcut : shortCuts) {
			shortcut.addSelectionListener(shortcutListener);
		}
		
		return shortCuts;
	}

}