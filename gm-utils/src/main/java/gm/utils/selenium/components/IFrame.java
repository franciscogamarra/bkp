package gm.utils.selenium.components;

import org.openqa.selenium.By;

import gm.utils.comum.Lst;
import gm.utils.selenium.Swe;

public class IFrame extends Swe {

	public IFrame(Swe swe) {
		super(swe.getSe(), swe.getEl());
	}
	
	@Override
	protected Lst<Swe> by(By by) {
		if (getSe().getFrame() != this) {
			throw new RuntimeException("Not in iframe");
		}
		return super.by(by);
	}

}
