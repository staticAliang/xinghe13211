package com.fengshen.server.disruptor;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GameQueue {
	
	public static RingBuffer<GameEvent> ringBuffer;
	

	public static void start() {
//		GameEventFactory eventFactory = new GameEventFactory();
//		ringBuffer = RingBuffer.createSingleProducer(eventFactory, 2048);
//		int size = 25;
//		ExecutorService executor = Executors.newFixedThreadPool(size);
//		SequenceBarrier sequenceBarrier = ringBuffer.newBarrier();
//		@SuppressWarnings("unchecked")
//		WorkHandler<GameEvent>[] workHandlers = new WorkHandler[size];
//		for (int i = 0; i < size; i++) {
//			workHandlers[i] = new GameEventHandler();
//		}
//		poll = new WorkerPool<GameEvent>(ringBuffer,sequenceBarrier, new IgnoreExceptionHandler(), workHandlers);
//		poll.start(executor);
		
		EventFactory<GameEvent> eventFactory = new GameEventFactory();
		ThreadFactory producerFactory = Executors.defaultThreadFactory();
		int ringBufferSize = 8192;
		Disruptor<GameEvent> disruptor = new Disruptor<GameEvent>(eventFactory,
				ringBufferSize, producerFactory, ProducerType.MULTI,
				new SleepingWaitStrategy());
		ringBuffer = disruptor.getRingBuffer();
		EventHandler<GameEvent> eventHandler = new GameEventHandler();
		disruptor.handleEventsWith(eventHandler);
		disruptor.start();
		log.info("---------------------------START DISRUPTOR---------------------------");
	}
}
