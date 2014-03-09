package communication;

import pact.ledopiano.MainActivity;

public class ThreadHandler implements Thread.UncaughtExceptionHandler {

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		MainActivity.problemeDeConnexion();
		
	}

}
