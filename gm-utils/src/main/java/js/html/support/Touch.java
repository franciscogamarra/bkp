package js.html.support;

import js.annotations.Support;

@Support
public class Touch {
	public int clientX;
	public int clientY;
	public int force;
	public int identifier;
	public int pageX;
	public int pageY;
	public int radiusX;
	public int radiusY;
	public int rotationAngle;
	public int screenX;
	public int screenY;
	public Touch clientX(int o){clientX = o; return this;}
	public Touch clientY(int o){clientY = o; return this;}
	public Touch force(int o){force = o; return this;}
	public Touch identifier(int o){identifier = o; return this;}
	public Touch pageX(int o){pageX = o; return this;}
	public Touch pageY(int o){pageY = o; return this;}
	public Touch radiusX(int o){radiusX = o; return this;}
	public Touch radiusY(int o){radiusY = o; return this;}
	public Touch rotationAngle(int o){rotationAngle = o; return this;}
	public Touch screenX(int o){screenX = o; return this;}
	public Touch screenY(int o){screenY = o; return this;}
}
