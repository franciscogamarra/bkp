package js.html.support;

import gm.languages.ts.javaToTs.annotacoes.Any;
import js.html.Element;

@Any
public class MouseEvent {
	public int clientX;
	public int clientY;
	public int pageX;
	public int pageY;
	public int movementX;
	public int movementY;
	public int screenX;
	public int screenY;
	public Element target;
	public Element currentTarget;
	public MouseEvent clientX(int o){clientX = o; return this;}
	public MouseEvent clientY(int o){clientY = o; return this;}
	public MouseEvent pageX(int o){pageX = o; return this;}
	public MouseEvent pageY(int o){pageY = o; return this;}
	public MouseEvent movementX(int o){movementX = o; return this;}
	public MouseEvent movementY(int o){movementY = o; return this;}
	public MouseEvent screenX(int o){screenX = o; return this;}
	public MouseEvent screenY(int o){screenY = o; return this;}
	public MouseEvent target(Element o){target = o; return this;}
	public MouseEvent currentTarget(Element o){currentTarget = o; return this;}

	public void preventDefault() {}
	public void stopPropagation() {}

}
