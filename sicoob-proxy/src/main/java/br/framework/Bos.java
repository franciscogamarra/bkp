package br.framework;

import org.springframework.stereotype.Service;

import br.support.framework.Model;
import br.support.framework.services.IPreencherBuscas;
import br.support.framework.services.ITabelaBo;
import br.support.lambda.F0;
import br.support.select.SelectModel;
import br.support.time.Momento;
import lombok.AllArgsConstructor;

@Service @AllArgsConstructor
public class Bos implements ITabelaBo, IPreencherBuscas {
	
	@Override
	public Momento getDhMovimento(Class<?> classe) {
		return null;
	}

	@Override
	public void add(Class<? extends Model<?, ?>> classe, F0<SelectModel<?, ?, ?>> getSe) {
		// TODO Auto-generated method stub
		
	}

}
