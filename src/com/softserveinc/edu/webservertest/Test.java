package com.softserveinc.edu.webservertest;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.softserveinc.edu.webserver.LoadBallancerCommunicator;
import com.softserveinc.edu.webserver.RedirectCommunicator;
import com.softserveinc.edu.webserver.SimpleCommunicator;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			//SimpleCommunicator comm = new SimpleCommunicator();
			LoadBallancerCommunicator comm = new LoadBallancerCommunicator();
			//RedirectCommunicator comm = new RedirectCommunicator();
			comm.run();
			// Thread communicatorThread = new Threadcomm);
			// communicatorThread.setDaemon(true);
			// communicatorThread.start();
		} catch (IOException ex) {
			// TODO Auto-generated catch block
			Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
		}
		System.out.print("done!");
	}
}
