package br.com.company.gwt.client.desktop;

import com.extjs.gxt.desktop.client.Desktop;
import com.extjs.gxt.desktop.client.Shortcut;
import com.extjs.gxt.desktop.client.StartMenu;
import com.extjs.gxt.desktop.client.TaskBar;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;

public class WebDesktop extends Desktop {

	private TaskBar taskBar;
	private StartMenu startMenu;

	public WebDesktop() {
		super();
		taskBar = super.getTaskBar();
		startMenu = taskBar.getStartMenu();
//		DTOUser dtoUser = Registry.get("dtoUser");
//		if(dtoUser != null){
//			setStartMenuHeading("user-icon", dtoUser.getUsername());
//		}else{
//			setStartMenuHeading("m2m-icon", "M2M Solutions");
//		}
		setStartMenuHeading("icon-system_", "FARM GIS");
		Button button = (Button)startMenu.getData("parent");
		if(button != null){
			button.setText("Iniciar");
			button.setId("botaoIniciar");
		}

	}

	public void setStartMenuHeading(String icon, String title){
		startMenu.setIconStyle(icon);
		startMenu.setHeading("<span style=\"font-size: 14pt; margin-left: 5px;\">" + title + "</span>");
	}

	public void addShortcut(Shortcut shortcut) {
		super.addShortcut(shortcut);
	}

	public void addWindow(Window window) {
		super.addWindow(window);
		window.show();
		window.toFront();
	}

	public TaskBar getTaskBar() {
		return taskBar;
	}

	public void setTaskBar(TaskBar taskBar) {
		this.taskBar = taskBar;
	}

	public StartMenu getStartMenu() {
		return startMenu;
	}

	public void setStartMenu(StartMenu startMenu) {
		this.startMenu = startMenu;
	}

}