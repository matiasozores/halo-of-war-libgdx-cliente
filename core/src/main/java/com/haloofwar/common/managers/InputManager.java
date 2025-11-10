package com.haloofwar.common.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.haloofwar.common.enumerators.Direction;
import com.haloofwar.common.enumerators.GameState;
import com.haloofwar.common.enumerators.UIState;
import com.haloofwar.engine.events.AttackEvent;
import com.haloofwar.engine.events.ChangeCurrentPlayerEvent;
import com.haloofwar.engine.events.CharacterTypedEvent;
import com.haloofwar.engine.events.EventBus;
import com.haloofwar.engine.events.EventListenerManager;
import com.haloofwar.engine.events.GameStateEvent;
import com.haloofwar.engine.events.InteractEvent;
import com.haloofwar.engine.events.NavigationEvent;
import com.haloofwar.engine.events.NextEvent;
import com.haloofwar.engine.events.SelectOptionEvent;
import com.haloofwar.engine.events.TogglePopupEvent;
import com.haloofwar.engine.events.UpdateMouseEvent;
import com.haloofwar.engine.events.online.EntityMoveEventOnline;

public class InputManager implements InputProcessor {

	private final EventBus globalBus;
	private final EventBus gameplayBus;
	private int x, y;
	private EventListenerManager listenerManager = new EventListenerManager();
	
    public InputManager(final EventBus globalBus, final EventBus gameplayBus) {
        this.globalBus = globalBus;
        this.gameplayBus = gameplayBus;
    }
    
    public void reset() {
    	this.listenerManager.clear();
    	this.subscribeEvents();
    }
    
    public void subscribeEvents() {
        this.listenerManager.add(gameplayBus, UpdateMouseEvent.class, this::onUpdateMouse);
    }
    
    private void onUpdateMouse(UpdateMouseEvent event) {
        this.x = event.x;
        this.y = event.y;
    }

 
    private void publish(final EventBus bus, final Object event) {
    	if(bus == null) {
    		System.out.println("Se intenta publicar un evento con un EventBus nulo... | InputManager");
    		return;
    	}

        bus.publish(event);
    }
    
	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
			case Input.Keys.W: this.publish(this.gameplayBus, new EntityMoveEventOnline(Direction.UP, true)); break;
		    case Input.Keys.S: this.publish(this.gameplayBus, new EntityMoveEventOnline(Direction.DOWN, true)); break;
		    case Input.Keys.A: this.publish(this.gameplayBus, new EntityMoveEventOnline(Direction.LEFT, true)); break;
		    case Input.Keys.D: this.publish(this.gameplayBus, new EntityMoveEventOnline(Direction.RIGHT, true)); break;
		    case Input.Keys.E: this.publish(this.gameplayBus, new InteractEvent(true)); break;
	        case Input.Keys.I: this.publish(this.gameplayBus,new TogglePopupEvent(UIState.INVENTORY)); break;
	        case Input.Keys.U: this.publish(this.gameplayBus,new TogglePopupEvent(UIState.SHOP)); break;
	        case Input.Keys.O: this.publish(this.gameplayBus,new TogglePopupEvent(UIState.EQUIPMENT)); break;
	        case Input.Keys.P: this.publish(this.gameplayBus,new TogglePopupEvent(UIState.ACHIEVEMENT)); break;
	        case Input.Keys.L: this.publish(this.gameplayBus,new TogglePopupEvent(UIState.VESTMENT)); break;
	        case Input.Keys.M: this.publish(this.gameplayBus,new TogglePopupEvent(UIState.MINIMAP)); break;
	        case Input.Keys.C: this.publish(this.gameplayBus, new ChangeCurrentPlayerEvent()); break;
	        case Input.Keys.ESCAPE: this.publish(this.gameplayBus, new GameStateEvent(GameState.PAUSED)); break;        
		    case Input.Keys.SPACE: this.publish(this.gameplayBus, new NextEvent(true)); break;
		
		    case Input.Keys.UP:
		    	this.publish(this.globalBus, new NavigationEvent(Direction.UP, true));
		    	this.publish(this.gameplayBus, new NavigationEvent(Direction.UP, true));
	            break;
	        case Input.Keys.DOWN:
		    	this.publish(this.globalBus, new NavigationEvent(Direction.DOWN, true));
		    	this.publish(this.gameplayBus, new NavigationEvent(Direction.DOWN, true));
	            break;
	            
	        case Input.Keys.LEFT: 
		    	this.publish(this.globalBus, new NavigationEvent(Direction.LEFT, true));
		    	this.publish(this.gameplayBus, new NavigationEvent(Direction.LEFT, true));
	        	break;
	        	
	        case Input.Keys.RIGHT: 
		    	this.publish(this.globalBus, new NavigationEvent(Direction.RIGHT, true));
		    	this.publish(this.gameplayBus, new NavigationEvent(Direction.RIGHT, true));
	        	break;
	            
	        case Input.Keys.ENTER: 
	        	this.publish(this.globalBus, new SelectOptionEvent(true));
	        	this.publish(this.gameplayBus, new SelectOptionEvent(true)); 
	        	break;
		}
		
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch (keycode) {
			case Input.Keys.W: this.publish(this.gameplayBus,new EntityMoveEventOnline(Direction.UP, false)); break;
		    case Input.Keys.S: this.publish(this.gameplayBus,new EntityMoveEventOnline(Direction.DOWN, false)); break;
		    case Input.Keys.A: this.publish(this.gameplayBus,new EntityMoveEventOnline(Direction.LEFT, false)); break;
		    case Input.Keys.D: this.publish(this.gameplayBus,new EntityMoveEventOnline(Direction.RIGHT, false)); break;
		    case Input.Keys.E: this.publish(this.gameplayBus,new InteractEvent(false)); break;
		    case Input.Keys.SPACE: this.publish(this.gameplayBus,new NextEvent(false)); break;
		    
		    case Input.Keys.UP:
		    	this.publish(this.globalBus, new NavigationEvent(Direction.UP, false));
		    	this.publish(this.gameplayBus, new NavigationEvent(Direction.UP, false));
	            break;
	        case Input.Keys.DOWN:
		    	this.publish(this.globalBus, new NavigationEvent(Direction.DOWN, false));
		    	this.publish(this.gameplayBus, new NavigationEvent(Direction.DOWN, false));
	            break;
	            
	        case Input.Keys.LEFT: 
		    	this.publish(this.globalBus, new NavigationEvent(Direction.LEFT, false));
		    	this.publish(this.gameplayBus, new NavigationEvent(Direction.LEFT, false));
	        	break;
	        	
	        case Input.Keys.RIGHT: 
		    	this.publish(this.globalBus, new NavigationEvent(Direction.RIGHT, false));
		    	this.publish(this.gameplayBus, new NavigationEvent(Direction.RIGHT, false));
	        	break;
	            
	        case Input.Keys.ENTER: 
	        	this.publish(this.globalBus, new SelectOptionEvent(false));
	        	this.publish(this.gameplayBus, new SelectOptionEvent(false)); 
	        	break;
		}
		
		return true;
	}
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		 if (button == Input.Buttons.LEFT) {
			 this.publish(this.gameplayBus,new AttackEvent(true, this.x, this.y));
			 
	     }
		 
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (button == Input.Buttons.LEFT) {
			this.publish(this.gameplayBus, new AttackEvent(false, this.x, this.y));
			Gdx.input.setCursorPosition((int)screenX, (int)screenY);

		}
		
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		this.mouseMoved(screenX, screenY);
		return true;
	}
	
	@Override
	public boolean mouseMoved(int screenX, int screenY) { return true; }
	@Override
	public boolean scrolled(float amountX, float amountY) { return false; }
	@Override
	public boolean touchCancelled(int screenX, int screenY, int pointer, int button) { return false; }
	@Override
	public boolean keyTyped(char character) {
	    this.publish(this.globalBus, new CharacterTypedEvent(character));
	    this.publish(this.gameplayBus, new CharacterTypedEvent(character));
	    return true;
	}
	
	public void dispose() {
		this.listenerManager.clear();
	}
}
