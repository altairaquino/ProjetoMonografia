package br.com.company.gwt.client;

import br.com.company.gwt.client.desktop.DesktopAppEvents;
import br.com.company.gwt.client.desktop.DesktopController;

import com.extjs.gxt.ui.client.GXT;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.util.Theme;
import com.google.gwt.core.client.EntryPoint;

public class GwtApplication implements EntryPoint {
	
	public void onModuleLoad() {
		
		GXT.setDefaultTheme(Theme.BLUE, true);
		
		Dispatcher dispatcher = Dispatcher.get();
		dispatcher.addController(new DesktopController());

		dispatcher.dispatch(DesktopAppEvents.Login);

		GXT.hideLoadingPanel("loading");
		
	}
}