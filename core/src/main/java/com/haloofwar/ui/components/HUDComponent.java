package com.haloofwar.ui.components;

import com.haloofwar.interfaces.Disposable;
import com.haloofwar.interfaces.Renderable;

public interface HUDComponent extends Renderable, Disposable {
	void reset();
}
