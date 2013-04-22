package br.com.company.gwt.client.mvc;

import br.com.company.gwt.client.form.FormInformation;
import br.com.company.gwt.client.form.FormInformation05;
import br.com.company.gwt.client.form.FormPanelContato;
import br.com.company.gwt.client.form.FormPanelCultura;
import br.com.company.gwt.client.form.FormPanelMapa;
import br.com.company.gwt.client.form.FormPanelParcela;
import br.com.company.gwt.client.form.FormPanelPropriedade;

public class FormManager {
	
	public static final FormPanelContato FormPanelContatosInstance = new FormPanelContato();
	public static final FormPanelMapa FormPanelMapaInstance = new FormPanelMapa();
	public static final FormPanelPropriedade FormPanelPropriedadeInstance = new FormPanelPropriedade();
	public static final FormPanelParcela FormPanelParcelaInstance = new FormPanelParcela();
	public static final FormPanelCultura FormPanelCulturaInstance = new FormPanelCultura();
	public static final FormInformation FormInformationInstance = new FormInformation();
	public static final FormInformation05 FormInformationInstance05 = new FormInformation05();

}