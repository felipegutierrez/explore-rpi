package org.sense.util;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class RandomScheduler {
	private final BlockingQueue<Integer> queuePeople;
	private final BlockingQueue<Integer> queueTickets;
	private final BlockingQueue<Integer> queueTrains;
	private Random randomGenerator;
	private Timer timer;

	public RandomScheduler(int interval) {
		this.queuePeople = new LinkedBlockingQueue<Integer>();
		this.queueTickets = new LinkedBlockingQueue<Integer>();
		this.queueTrains = new LinkedBlockingQueue<Integer>();
		this.randomGenerator = new Random();

		TimerTask task = new TimerTask() {
			public void run() {
				try {
					// clean the queues
					if (!queuePeople.isEmpty()) {
						queuePeople.clear();
					}
					if (!queueTickets.isEmpty()) {
						queueTickets.clear();
					}
					if (!queueTrains.isEmpty()) {
						queueTrains.clear();
					}
					// generate random value to introduce error on the scheduling policy.
					int random = randomGenerator.nextInt(11); // this value generates ERROR
					// int random = 5; // this value is WITH NO ERROR
					// System.out.print("scheduler assigned new value: ");
					// System.out.println(random + " - " + ((random > 8) || (random < 2)));
					int peopleAndTickets = Integer.valueOf(randomGenerator.nextInt(5000));

					// add random values to the queues and introduce an error for values less than
					// 20% or greater than 80%
					if (random < 2) {
						queuePeople.put(peopleAndTickets / 2);
					} else if (random > 8) {
						queuePeople.put(peopleAndTickets * 2);
					} else {
						queuePeople.put(peopleAndTickets);
					}
					queueTickets.put(peopleAndTickets);

					// calculate the amount of trains based on the amount of people coming and
					// tickets sold
					int trains = (peopleAndTickets / 500) + 1;
					queueTrains.put(trains);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		this.timer = new Timer(true);
		timer.schedule(task, 0, interval);
	}

	public BlockingQueue<Integer> getQueuePeople() throws InterruptedException {
		while (this.queuePeople.isEmpty()) {
			Thread.sleep(100);
		}
		return this.queuePeople;
	}

	public BlockingQueue<Integer> getQueueTickets() throws InterruptedException {
		while (this.queueTickets.isEmpty()) {
			Thread.sleep(100);
		}
		return queueTickets;
	}

	public BlockingQueue<Integer> getQueueTrains() throws InterruptedException {
		while (this.queueTrains.isEmpty()) {
			Thread.sleep(100);
		}
		return queueTrains;
	}

	public Timer getTimer() {
		return timer;
	}

	public static void main(String[] args) throws InterruptedException {
		RandomScheduler randomScheduler = new RandomScheduler(1000);
		for (int i = 0; i < 50; i++) {
			Thread.sleep(250);
			System.out.println("People[" + randomScheduler.queuePeople.peek() + "] Tickets["
					+ randomScheduler.queueTickets.peek() + "] Trains[" + randomScheduler.queueTrains.peek() + "]");
		}
		randomScheduler.timer.cancel();
	}
}
