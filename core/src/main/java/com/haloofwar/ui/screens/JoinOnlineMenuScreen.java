package com.haloofwar.ui.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.haloofwar.common.context.GameContext;
import com.haloofwar.common.enumerators.Background;
import com.haloofwar.engine.events.CharacterTypedEvent;
import com.haloofwar.engine.events.IncorrectCodeEvent;
import com.haloofwar.engine.events.StartGameEvent;
import com.haloofwar.engine.utils.Text;
import com.haloofwar.game.managers.GameManager;
import com.haloofwar.launcher.GameInitializer;
import com.haloofwar.network.GameController;
import com.haloofwar.ui.Menu;

public class JoinOnlineMenuScreen extends Menu {

    private final Text typedCodeText;
    private final Text promptText;
    private int incorrectCounter = 0;
    private final int MAX_CHARACTERS = 6;
    
    
    public JoinOnlineMenuScreen(final GameContext context, final Screen previousScreen) {
        super(context, "Unirte a partida", new String[] { "Conectarse", "Volver" }, previousScreen, Background.MAIN_MENU);

        this.typedCodeText = new Text("", context.getRender().getFont().getTitleFont());
        this.typedCodeText.color = Color.YELLOW;

        this.promptText = new Text("Ingresar codigo: ", context.getRender().getFont().getTitleFont());
        this.promptText.color = Color.WHITE;

        this.listenerManager.add(this.context.getGlobalBus(), CharacterTypedEvent.class, this::onCharacterTyped);
        this.listenerManager.add(this.context.getGlobalBus(), StartGameEvent.class, this::onStartGame);
        this.listenerManager.add(this.context.getGlobalBus(), IncorrectCodeEvent.class, this::onIncorrectCode);
    }
    
    public void setController(GameController controller) {
    	this.controller = controller;
    }
    
    private void onCharacterTyped(CharacterTypedEvent event) {
        char character = event.character;
         
        if(Character.isLetter(character)) {
        	return;
        }
        
        if(Character.isDigit(character)) {
        	if(this.typedCodeText.message.length() == this.MAX_CHARACTERS) {
        		return;
        	}
        	
            String current = this.typedCodeText.message;
            this.typedCodeText.message = current + character;
        }

        if(character == '\b') {
            String current = this.typedCodeText.message;
            if(!current.isEmpty()) {
                this.typedCodeText.message = current.substring(0, current.length() - 1);
            }
        }
    }
    
    private void onIncorrectCode(IncorrectCodeEvent event) {
    	this.incorrectCounter++;
    	super.updateTitle("Unirte a partida - incorrecto, intento: " + this.incorrectCounter);
    }

    @Override
    protected void processOption(int optionIndex) {
        switch (optionIndex) {
            case 0:
                String code = typedCodeText.message.trim();
                if (!code.isEmpty()) {
                    this.connectToHost(code);
                } else {
                    System.out.println("Código vacío.");
                }
                break;
            case 1: 
                this.dispose();
                this.goBack();
                break;
        }
    }

    private void connectToHost(String code) {
        this.controller.sendJoinGame(Integer.parseInt(code));
    }
    
    private void onStartGame(StartGameEvent event) {
       	this.listenerManager.clear();
    	this.context.createGameplay();
		GameManager manager = GameInitializer.initializeOnlineGameManager(event.kratosId, event.masterchiefId, this.context, this.controller, event.playerType);
		this.context.getGame().setScreen(manager);
    }


    @Override
    public void render(float delta) {
        super.render(delta);

        float x = 100;
        float y = 300;

        SpriteBatch batch = this.context.getRender().getBatch();
        batch.begin();
        drawTextWithOutline(promptText, batch, x, y, Color.WHITE, Color.BLACK);
        float offsetX = promptText.getWidth();
        drawTextWithOutline(typedCodeText, batch, x + offsetX, y, Color.YELLOW, Color.BLACK);
        batch.end();
    }
    
    private void drawTextWithOutline(Text text, SpriteBatch batch, float x, float y, Color textColor, Color outlineColor) {
        Color original = text.color;
        float offset = 2f;

        text.color = outlineColor;
        text.draw(batch, x - offset, y);
        text.draw(batch, x + offset, y);
        text.draw(batch, x, y - offset);
        text.draw(batch, x, y + offset);
        text.draw(batch, x - offset, y - offset);
        text.draw(batch, x + offset, y + offset);
        text.draw(batch, x - offset, y + offset);
        text.draw(batch, x + offset, y - offset);

        text.color = textColor;
        text.draw(batch, x, y);

        text.color = original;
    }
}
