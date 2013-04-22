package br.com.company.gwt.client.desktop;

import java.util.ArrayList;
import java.util.List;

import br.com.company.gwt.client.mvc.FormManager;

import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.util.IconHelper;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;

public class StartMenuUtil {
	
	public static List<MenuItem> getMenuItems(){
		
		List<MenuItem> menuItems = new ArrayList<MenuItem>();
		
		MenuItem menuItem = new MenuItem("Consulta 03");
		menuItem.addSelectionListener(getSelectionListener(FormManager.FormInformationInstance));
		menuItem.setIcon(IconHelper.createStyle("tabs"));
	    menuItems.add(menuItem);
	    
	    menuItem = new MenuItem("Consulta 05");
		menuItem.addSelectionListener(getSelectionListener(FormManager.FormInformationInstance05));
		menuItem.setIcon(IconHelper.createStyle("tabs"));
	    menuItems.add(menuItem);

	    menuItem = new MenuItem("Propriedades");
	    menuItem.setIcon(IconHelper.createStyle("tabs"));
	    menuItem.addSelectionListener(getSelectionListener(FormManager.FormPanelPropriedadeInstance));
	    menuItems.add(menuItem);

	    menuItem = new MenuItem("Parcelas");
	    menuItem.setIcon(IconHelper.createStyle("tabs"));
	    menuItem.addSelectionListener(getSelectionListener(FormManager.FormPanelParcelaInstance));
	    menuItems.add(menuItem);

//	    menuItem = new MenuItem("Cadastros");
//	    menuItem.setIcon(IconHelper.createStyle("icon-grid"));

//	    Menu sub = new Menu();

//	    MenuItem item = new MenuItem("Bogus Window ");
//	    item.setData("window", createBogusWindow(i));
//	    item.addSelectionListener(menuListener);
//	    sub.add(item);

//	    menuItem.setSubMenu(sub);
	    menuItems.add(menuItem);
	    
	    return menuItems;
	}
	
	private static SelectionListener<MenuEvent> getSelectionListener(final Window window){
		SelectionListener<MenuEvent> menuListener = new SelectionListener<MenuEvent>() {
			@Override
			public void componentSelected(MenuEvent me) {
				if (window != null){
					DesktopAppView.getInstance().addWindow(window);
				}
			}
		};
		return menuListener;
	}

}