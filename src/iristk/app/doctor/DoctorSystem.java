/*******************************************************************************
 * Copyright (c) 2014 Gabriel Skantze.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     Gabriel Skantze - initial API and implementation
 ******************************************************************************/
package iristk.app.doctor;

import java.net.URISyntaxException;

import iristk.situated.DefaultSituation;
import iristk.situated.Location;
import iristk.situated.Rotation;
import iristk.situated.SingstarMicrophone;
import iristk.situated.SituationPanel;
import iristk.speech.Console;
import iristk.speech.RecognizerException;
import iristk.speech.RecognizerModule;
import iristk.speech.SynthesizerModule;
import iristk.speech.Voice.Gender;
import iristk.speech.windows.WindowsRecognizer;
import iristk.speech.windows.WindowsSynthesizer;
import iristk.system.EventMonitorPanel;
import iristk.system.InitializationException;
import iristk.system.IrisGUI;
import iristk.system.IrisSystem;
import iristk.util.Language;
import iristk.agent.face.FaceModule;
import iristk.app.quiz.Question;
import iristk.app.quiz.QuestionSet;
import iristk.app.doctor.DoctorFlow;
import iristk.flow.FlowModule;
import iristk.kinect.ColorViewPanel;
import iristk.kinect.KinectRecognizerModule;
import iristk.kinect.KinectModule;

public class DoctorSystem {

	// Use the animated agent
	private static final boolean USE_FACE = true;
	// Or use the Furhat robot head (on another machine)
	private static final boolean USE_FURHAT = false;
	// Furhat computer address (if Furhat is used)
	private static final String FURHAT_HOST = "127.0.0.1";
			
	// Use Kinect
	private static final boolean USE_KINECT = false;

	// Use local Singstar microphones for speech recognition
	private static final boolean USE_SINGSTAR_MICROPHONES = true;
	// Or use Kinect array microphone for speech recognition
	private static final boolean USE_KINECT_MICROPHONE = false;
		
	private IrisSystem system;
	private DefaultSituation situation;
	private IrisGUI gui;
	private KinectModule kinectModule;
	private QuestionSet questions;

	public DoctorSystem() throws Exception {
		// Create the system
		system = new IrisSystem(this.getClass());

		gui = new IrisGUI(system);
		gui.addDockPanel(new EventMonitorPanel(system));
		
		// Create a default situation (one system agent located in origo)
		situation = new DefaultSituation(system);
		// Add a panel to the GUI with a top view of the situation
		gui.addDockPanel(new SituationPanel(system, situation, SituationPanel.TOPVIEW));
		
		questions = new QuestionSet(getClass().getResource("questions.txt").openStream());
		system.addModule(new FlowModule(new DoctorFlow(questions, situation.getSituatedDialog())));
		
		if (USE_FACE)	
			initFace();
		else if (USE_FURHAT)	
			system.connectToBroker("furhat", FURHAT_HOST);
		
		if (USE_KINECT)	
			initKinect();
		
		if (USE_SINGSTAR_MICROPHONES)	
			initSingstarMicrophones();
		else if (USE_KINECT_MICROPHONE)		
			initKinectMicrophone();
		
		Console console = new Console();
		system.addModule(console);
		gui.addDockPanel(console);
				
		system.sendStartSignal();
	}
	
	private void initSingstarMicrophones() throws InitializationException, RecognizerException, URISyntaxException {
		// Initialize recognizers for both microphones, let them read from different channels
		for (SingstarMicrophone mic : SingstarMicrophone.MICROPHONES) {
			RecognizerModule recognizer = new RecognizerModule(new WindowsRecognizer(Language.ENGLISH_US, mic.getAudioChannel()));
			// Associate the recognizer with the microphone
			recognizer.setSensor(mic.id);
			// Add the microphone to the situation model so that the microphone can be associated with a user
			situation.addSensor(mic);
			loadGrammar(recognizer);
			system.addModule(recognizer);
		}
	}
	
	private void initKinect() throws InitializationException, RecognizerException, URISyntaxException {
		// Add a Kinect module to the system
		kinectModule = new KinectModule();
		system.addModule(kinectModule);
		// Add the Kinect to the situation model with a position. Default is below/front of the monitor/face
		situation.addSensor(kinectModule.getSituationSensor(new Location(0, -0.5, 0), new Rotation(20, 0, 0)));
		// Add the Kinect depth view to the situation 
		gui.addDockPanel(new ColorViewPanel(kinectModule.getKinect()));
	}
	
	private void loadGrammar(RecognizerModule recognizer) throws RecognizerException, URISyntaxException {
		recognizer.loadGrammar("default", getClass().getResource("QuizGrammar.xml").toURI());
		for (Question q : questions) {
			recognizer.loadGrammar(q.getId(), q.getGrammar());
		}
		recognizer.setDefaultGrammars("default");
	}
	
	private void initKinectMicrophone() throws RecognizerException, URISyntaxException, InitializationException {
		// Use the Kinect sensor for speech recognition if we are not using singstar microphones
		RecognizerModule recognizer = new KinectRecognizerModule(kinectModule.getKinect(), Language.ENGLISH_US);
		loadGrammar(recognizer);
		system.addModule(recognizer);
	}
	
	private void initFace() throws InitializationException {
		SynthesizerModule synth = new SynthesizerModule(new WindowsSynthesizer(Gender.FEMALE, Language.ENGLISH_US));
		// Turn on lipsync since we are using an animated agent. This makes the synthesizer wait for the agent to prepare lipsync before the speech starts.
		synth.doLipsync(true);		
		system.addModule(synth);
		system.addModule(new FaceModule("female"));
	}

	public static void main(String[] args) throws Exception {
		new DoctorSystem();
	}

}
