package br.com.company.gwt.client.desktop;

import br.com.company.gwt.client.component.WebUtil;
import br.com.company.gwt.client.widget.LoginDialog;

import com.extjs.gxt.desktop.client.Shortcut;
import com.extjs.gxt.desktop.client.StartMenu;
import com.extjs.gxt.ui.client.GXT;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.event.WindowEvent;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;

public class DesktopAppView extends View {

	private LoginDialog dialog;
	private static WebDesktop webDesktop;

	public DesktopAppView(Controller controller) {
		super(controller);
	}

	protected void initialize() {
		GXT.hideLoadingPanel("loading");
		login();
	}

	protected void login() {
		dialog = new LoginDialog();
		dialog.setClosable(false);
		dialog.addListener(Events.Hide, new Listener<WindowEvent>() {
			public void handleEvent(WindowEvent be) {
				Dispatcher.forwardEvent(DesktopAppEvents.Init);
			}
		});
		dialog.show();
	}

	@Override
	protected void handleEvent(AppEvent event) {
		if (event.getType() == DesktopAppEvents.Init) {
			initUI();
		}
	}
	
	public void init() {
		webDesktop = new WebDesktop();
	}
	
	public static WebDesktop getInstance(){
		return webDesktop;
	}

	private void initUI() {

		init();
		
		createTool();
		
		addShortcuts();
		
		addMenus();

	}

	private void addMenus() {
		for(final MenuItem mi: StartMenuUtil.getMenuItems()){
			webDesktop.getStartMenu().add(mi);
		}		
	}

	private void addShortcuts() {
		for(final Shortcut shortcut: ShortCutUtil.getShortcuts()){
			webDesktop.addShortcut(shortcut);
		}
		
	}

	private void createTool() {
		StartMenu startMenu = webDesktop.getStartMenu();
//		MenuItem menuItemSys = WebUtil.createMenuItem("Preferencias", "control-sys-icon");
//
//		Menu subMenu = new Menu();
//		subMenu.add(WebUtil.createMenuItem("perfil", "profile-icon", null, ""));
//		subMenu.add(WebUtil.createMenuItem("configuracoes", "settings-icon", null, ""));
//		subMenu.add(WebUtil.createMenuItem("parametrosDeIntegracao", "cadastro-icon", null, ""));
//		subMenu.add(WebUtil.createMenuItem("configuracaoLinha", "cadastro-icon", null, ""));
//
//		menuItemSys.setSubMenu(subMenu);
//
//		startMenu.addTool(menuItemSys);
		createAboutTool();
		startMenu.addToolSeperator();
		createExitTool();
		startMenu.setToolWidth(125);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void createExitTool(){
		MenuItem menuItem = WebUtil.createMenuItem("Sair do Sistema", "icon_logout");
		SelectionListener listenerExit = new SelectionListener<ComponentEvent>() {

			public void componentSelected(ComponentEvent ce) {
				WebUtil.reloadPage();
			}

		};
		menuItem.addSelectionListener(listenerExit);
		webDesktop.getStartMenu().addTool(menuItem);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void createAboutTool(){
		MenuItem menuItem = WebUtil.createMenuItem("Sobre", "icon_about");
		SelectionListener listenerExit = new SelectionListener<ComponentEvent>() {

			public void componentSelected(ComponentEvent ce) {
				new Window().show();
			}

		};
		menuItem.addSelectionListener(listenerExit);
		webDesktop.getStartMenu().addTool(menuItem);
	}

}