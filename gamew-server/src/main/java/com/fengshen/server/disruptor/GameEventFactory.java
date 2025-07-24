package com.fengshen.server.disruptor;

import com.lmax.disruptor.EventFactory;

public class GameEventFactory implements EventFactory<GameEvent> {

	@Override
	public GameEvent newInstance() {
		return new GameEvent();
	}

}
