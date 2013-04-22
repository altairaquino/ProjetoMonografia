package br.com.company.gwt.client.component;

import java.util.Iterator;

import com.extjs.gxt.ui.client.binding.Converter;
import com.extjs.gxt.ui.client.widget.form.Radio;
import com.extjs.gxt.ui.client.widget.form.RadioGroup;

public class RadioGroupConverter<D extends Enum<D>> extends Converter {

	protected RadioGroup radioGroup;
	private Class<D> clazz;

	public RadioGroupConverter(Class<D> clazz, RadioGroup field) {
		this.radioGroup = field;
		this.clazz = clazz;
	}

	@Override
	public Object convertFieldValue(Object value) {
		if (value == null) {
			return null;
		}
		String name = ((Radio)value).getData(radioGroup.getName()); 
		if (name == null) {
			return null;
		}
		D d = null;
		try {
			d = Enum.<D>valueOf(clazz, name);
		} catch (Exception e) {}
		return d;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object convertModelValue(Object value) {
		if(value == null){
			return null;
		}
		Iterator it = radioGroup.getAll().iterator();
		Radio found = null;
		while (found == null && it.hasNext()) {
			Object obj = it.next();
			if(obj instanceof Radio){
				String name = ((Radio)obj).getData(radioGroup.getName());
				if(((D)value).name().equals(name)){
					found = ((Radio)obj);
				}
			}
		}
		return found;
	}

}