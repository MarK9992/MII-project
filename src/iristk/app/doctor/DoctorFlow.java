package iristk.app.doctor;

import java.util.List;
import iristk.system.Event;
import iristk.flow.*;
import iristk.util.Record;
import static iristk.util.Converters.*;
import iristk.app.quiz.QuestionSet;
import iristk.app.quiz.Question;

public class DoctorFlow extends iristk.flow.Flow {

	private QuestionSet questions;
	private iristk.situated.SituatedDialog dialog;
	private iristk.situated.UserModel users;
	private Question question;
	private int guess;
	private int winningScore;
	private DiseaseMap map;

	private void initVariables() {
		users = (iristk.situated.UserModel) dialog.getUsers();
		guess = asInteger(0);
		winningScore = asInteger(3);
		map = (DiseaseMap) new DiseaseMap();
	}

	public iristk.situated.UserModel getUsers() {
		return this.users;
	}

	public void setUsers(iristk.situated.UserModel value) {
		this.users = value;
	}

	public Question getQuestion() {
		return this.question;
	}

	public void setQuestion(Question value) {
		this.question = value;
	}

	public int getGuess() {
		return this.guess;
	}

	public void setGuess(int value) {
		this.guess = value;
	}

	public int getWinningScore() {
		return this.winningScore;
	}

	public void setWinningScore(int value) {
		this.winningScore = value;
	}

	public DiseaseMap getMap() {
		return this.map;
	}

	public void setMap(DiseaseMap value) {
		this.map = value;
	}

	public QuestionSet getQuestions() {
		return this.questions;
	}

	public iristk.situated.SituatedDialog getDialog() {
		return this.dialog;
	}

	@Override
	public Object getVariable(String name) {
		if (name.equals("users")) return this.users;
		if (name.equals("question")) return this.question;
		if (name.equals("guess")) return this.guess;
		if (name.equals("winningScore")) return this.winningScore;
		if (name.equals("map")) return this.map;
		if (name.equals("questions")) return this.questions;
		if (name.equals("dialog")) return this.dialog;
		return null;
	}


	public DoctorFlow(QuestionSet questions, iristk.situated.SituatedDialog dialog) {
		this.questions = questions;
		this.dialog = dialog;
		initVariables();
	}

	@Override
	protected State getInitialState() {return new iristk.app.doctor.DoctorFlow.Idle();}


	private class Idle extends State {



		@Override
		public void setFlowRunner(FlowRunner flowRunner) {
			super.setFlowRunner(flowRunner);
		}

		@Override
		public void onentry() {
			int eventResult;
			Event event = new Event("state.enter");
			EXECUTION: {
				iristk.situated.SituatedDialog.attendNobody state0 = dialog.new attendNobody();
				if (!flowRunner.callState(state0, Idle.this, event)) {
					eventResult = EVENT_ABORTED;
					break EXECUTION;
				}
			}
		}

		@Override
		public int onFlowEvent(Event event) {
			int eventResult;
			if (event.triggers("sense.user.enter")) {
				eventResult = EVENT_CONSUMED;
				EXECUTION: {
					iristk.situated.SituatedDialog.attend state1 = dialog.new attend();
					state1.setTarget(event.get("agent"));
					if (!flowRunner.callState(state1, Idle.this, event)) {
						eventResult = EVENT_ABORTED;
						break EXECUTION;
					}
					iristk.situated.SituatedDialog.say state2 = dialog.new say();
					state2.setText("Hi there");
					if (!flowRunner.callState(state2, Idle.this, event)) {
						eventResult = EVENT_ABORTED;
						break EXECUTION;
					}
					Event sendEvent3 = new Event("action.gesture");
					sendEvent3.put("name", "smile");
					flowRunner.sendEvent(sendEvent3, Idle.this, event);
					Question1 state4 = new Question1();
					flowRunner.gotoState(state4, this, Idle.this, event);
					eventResult = EVENT_ABORTED;
					break EXECUTION;
				}
				if (eventResult != EVENT_IGNORED) return eventResult;
			}
			eventResult = super.onFlowEvent(event);
			if (eventResult != EVENT_IGNORED) return eventResult;
			eventResult = callerHandlers(event);
			if (eventResult != EVENT_IGNORED) return eventResult;
			return EVENT_IGNORED;
		}

	}


	private class Dialog extends State {



		@Override
		public void setFlowRunner(FlowRunner flowRunner) {
			super.setFlowRunner(flowRunner);
		}


		@Override
		public int onFlowEvent(Event event) {
			int eventResult;
			if (event.triggers("sense.user.leave")) {
				if (users.isAttending(event) || users.isAttendingAll()) {
					eventResult = EVENT_CONSUMED;
					EXECUTION: {
						if (users.hasAny()) {
							iristk.situated.SituatedDialog.attendRandom state5 = dialog.new attendRandom();
							if (!flowRunner.callState(state5, Dialog.this, event)) {
								eventResult = EVENT_ABORTED;
								break EXECUTION;
							}
							flowRunner.reentryState(this, Dialog.this, event);
							eventResult = EVENT_ABORTED;
							break EXECUTION;
						} else {
							Goodbye state6 = new Goodbye();
							flowRunner.gotoState(state6, this, Dialog.this, event);
							eventResult = EVENT_ABORTED;
							break EXECUTION;
						}
					}
					if (eventResult != EVENT_IGNORED) return eventResult;
				}
			}
			eventResult = super.onFlowEvent(event);
			if (eventResult != EVENT_IGNORED) return eventResult;
			eventResult = callerHandlers(event);
			if (eventResult != EVENT_IGNORED) return eventResult;
			return EVENT_IGNORED;
		}

	}


	private class Goodbye extends State {



		@Override
		public void setFlowRunner(FlowRunner flowRunner) {
			super.setFlowRunner(flowRunner);
		}

		@Override
		public void onentry() {
			int eventResult;
			Event event = new Event("state.enter");
			EXECUTION: {
				iristk.situated.SituatedDialog.say state7 = dialog.new say();
				state7.setText("Goodbye");
				if (!flowRunner.callState(state7, Goodbye.this, event)) {
					eventResult = EVENT_ABORTED;
					break EXECUTION;
				}
				Idle state8 = new Idle();
				flowRunner.gotoState(state8, this, Goodbye.this, event);
				eventResult = EVENT_ABORTED;
				break EXECUTION;
			}
		}

		@Override
		public int onFlowEvent(Event event) {
			int eventResult;
			eventResult = super.onFlowEvent(event);
			if (eventResult != EVENT_IGNORED) return eventResult;
			eventResult = callerHandlers(event);
			if (eventResult != EVENT_IGNORED) return eventResult;
			return EVENT_IGNORED;
		}

	}


	private class Question1 extends Dialog {



		@Override
		public void setFlowRunner(FlowRunner flowRunner) {
			super.setFlowRunner(flowRunner);
		}

		@Override
		public void onentry() {
			int eventResult;
			Event event = new Event("state.enter");
			EXECUTION: {
				if (users.hasMany()) {
					iristk.situated.SituatedDialog.attendAll state9 = dialog.new attendAll();
					if (!flowRunner.callState(state9, Question1.this, event)) {
						eventResult = EVENT_ABORTED;
						break EXECUTION;
					}
				}
				iristk.situated.SituatedDialog.say state10 = dialog.new say();
				state10.setText("How do you feel?");
				if (!flowRunner.callState(state10, Question1.this, event)) {
					eventResult = EVENT_ABORTED;
					break EXECUTION;
				}
				Event sendEvent11 = new Event("action.gesture");
				sendEvent11.put("name", "sleep");
				flowRunner.sendEvent(sendEvent11, Question1.this, event);
				iristk.situated.SituatedDialog.listen state12 = dialog.new listen();
				if (!flowRunner.callState(state12, Question1.this, event)) {
					eventResult = EVENT_ABORTED;
					break EXECUTION;
				}
			}
		}

		@Override
		public int onFlowEvent(Event event) {
			int eventResult;
			if (event.triggers("sense.user.speech**")) {
				if (event.has("sem:good")) {
					eventResult = EVENT_CONSUMED;
					EXECUTION: {
						iristk.situated.SituatedDialog.say state13 = dialog.new say();
						state13.setText("Oh, then you don't need me!");
						if (!flowRunner.callState(state13, Question1.this, event)) {
							eventResult = EVENT_ABORTED;
							break EXECUTION;
						}
						Goodbye state14 = new Goodbye();
						flowRunner.gotoState(state14, this, Question1.this, event);
						eventResult = EVENT_ABORTED;
						break EXECUTION;
					}
					if (eventResult != EVENT_IGNORED) return eventResult;
				}
			}
			if (event.triggers("sense.user.speech**")) {
				if (event.has("sem:bad")) {
					eventResult = EVENT_CONSUMED;
					EXECUTION: {
						iristk.situated.SituatedDialog.say state15 = dialog.new say();
						state15.setText("Okay, let's see if I can help you!");
						if (!flowRunner.callState(state15, Question1.this, event)) {
							eventResult = EVENT_ABORTED;
							break EXECUTION;
						}
						soar_throat state16 = new soar_throat();
						flowRunner.gotoState(state16, this, Question1.this, event);
						eventResult = EVENT_ABORTED;
						break EXECUTION;
					}
					if (eventResult != EVENT_IGNORED) return eventResult;
				}
			}
			if (event.triggers("sense.user.speech**")) {
				if (event.has("sem:repeat")) {
					eventResult = EVENT_CONSUMED;
					EXECUTION: {
						flowRunner.reentryState(this, Question1.this, event);
						eventResult = EVENT_ABORTED;
						break EXECUTION;
					}
					if (eventResult != EVENT_IGNORED) return eventResult;
				}
			}
			if (event.triggers("sense.user.speech**")) {
				eventResult = EVENT_CONSUMED;
				EXECUTION: {
					flowRunner.reentryState(this, Question1.this, event);
					eventResult = EVENT_ABORTED;
					break EXECUTION;
				}
				if (eventResult != EVENT_IGNORED) return eventResult;
			}
			eventResult = super.onFlowEvent(event);
			if (eventResult != EVENT_IGNORED) return eventResult;
			eventResult = callerHandlers(event);
			if (eventResult != EVENT_IGNORED) return eventResult;
			return EVENT_IGNORED;
		}

	}


	private class soar_throat extends Dialog {



		@Override
		public void setFlowRunner(FlowRunner flowRunner) {
			super.setFlowRunner(flowRunner);
		}

		@Override
		public void onentry() {
			int eventResult;
			Event event = new Event("state.enter");
			EXECUTION: {
				map.questionSymptom(Symptom.SOARTHROAT);
				iristk.situated.SituatedDialog.say state17 = dialog.new say();
				state17.setText("Do you have a soar throat?");
				if (!flowRunner.callState(state17, soar_throat.this, event)) {
					eventResult = EVENT_ABORTED;
					break EXECUTION;
				}
				iristk.situated.SituatedDialog.listen state18 = dialog.new listen();
				if (!flowRunner.callState(state18, soar_throat.this, event)) {
					eventResult = EVENT_ABORTED;
					break EXECUTION;
				}
			}
		}

		@Override
		public int onFlowEvent(Event event) {
			int eventResult;
			if (event.triggers("sense.user.speech**")) {
				if (event.has("sem:yes")) {
					eventResult = EVENT_CONSUMED;
					EXECUTION: {
						iristk.situated.SituatedDialog.say state19 = dialog.new say();
						state19.setText("You have a soar throat!");
						if (!flowRunner.callState(state19, soar_throat.this, event)) {
							eventResult = EVENT_ABORTED;
							break EXECUTION;
						}
						Event sendEvent20 = new Event("action.gesture");
						sendEvent20.put("name", "emotion_anger");
						flowRunner.sendEvent(sendEvent20, soar_throat.this, event);
						map.answerSymptom(Symptom.SOARTHROAT);
						cough state21 = new cough();
						flowRunner.gotoState(state21, this, soar_throat.this, event);
						eventResult = EVENT_ABORTED;
						break EXECUTION;
					}
					if (eventResult != EVENT_IGNORED) return eventResult;
				}
			}
			if (event.triggers("sense.user.speech**")) {
				if (event.has("sem:no")) {
					eventResult = EVENT_CONSUMED;
					EXECUTION: {
						Event sendEvent22 = new Event("action.gesture");
						sendEvent22.put("name", "wake_up");
						flowRunner.sendEvent(sendEvent22, soar_throat.this, event);
						cough state23 = new cough();
						flowRunner.gotoState(state23, this, soar_throat.this, event);
						eventResult = EVENT_ABORTED;
						break EXECUTION;
					}
					if (eventResult != EVENT_IGNORED) return eventResult;
				}
			}
			if (event.triggers("sense.user.speech**")) {
				if (event.has("sem:repeat")) {
					eventResult = EVENT_CONSUMED;
					EXECUTION: {
						flowRunner.reentryState(this, soar_throat.this, event);
						eventResult = EVENT_ABORTED;
						break EXECUTION;
					}
					if (eventResult != EVENT_IGNORED) return eventResult;
				}
			}
			if (event.triggers("sense.user.speech**")) {
				eventResult = EVENT_CONSUMED;
				EXECUTION: {
					flowRunner.reentryState(this, soar_throat.this, event);
					eventResult = EVENT_ABORTED;
					break EXECUTION;
				}
				if (eventResult != EVENT_IGNORED) return eventResult;
			}
			eventResult = super.onFlowEvent(event);
			if (eventResult != EVENT_IGNORED) return eventResult;
			eventResult = callerHandlers(event);
			if (eventResult != EVENT_IGNORED) return eventResult;
			return EVENT_IGNORED;
		}

	}


	private class cough extends Dialog {



		@Override
		public void setFlowRunner(FlowRunner flowRunner) {
			super.setFlowRunner(flowRunner);
		}

		@Override
		public void onentry() {
			int eventResult;
			Event event = new Event("state.enter");
			EXECUTION: {
				map.questionSymptom(Symptom.COUGH);
				iristk.situated.SituatedDialog.say state24 = dialog.new say();
				state24.setText("Are you suffering from cough?");
				if (!flowRunner.callState(state24, cough.this, event)) {
					eventResult = EVENT_ABORTED;
					break EXECUTION;
				}
				iristk.situated.SituatedDialog.listen state25 = dialog.new listen();
				if (!flowRunner.callState(state25, cough.this, event)) {
					eventResult = EVENT_ABORTED;
					break EXECUTION;
				}
			}
		}

		@Override
		public int onFlowEvent(Event event) {
			int eventResult;
			if (event.triggers("sense.user.speech**")) {
				if (event.has("sem:yes")) {
					eventResult = EVENT_CONSUMED;
					EXECUTION: {
						map.answerSymptom(Symptom.COUGH);
						iristk.situated.SituatedDialog.say state26 = dialog.new say();
						state26.setText("Okay");
						if (!flowRunner.callState(state26, cough.this, event)) {
							eventResult = EVENT_ABORTED;
							break EXECUTION;
						}
						headache state27 = new headache();
						flowRunner.gotoState(state27, this, cough.this, event);
						eventResult = EVENT_ABORTED;
						break EXECUTION;
					}
					if (eventResult != EVENT_IGNORED) return eventResult;
				}
			}
			if (event.triggers("sense.user.speech**")) {
				if (event.has("sem:no")) {
					eventResult = EVENT_CONSUMED;
					EXECUTION: {
						iristk.situated.SituatedDialog.say state28 = dialog.new say();
						state28.setText("Good for you");
						if (!flowRunner.callState(state28, cough.this, event)) {
							eventResult = EVENT_ABORTED;
							break EXECUTION;
						}
						headache state29 = new headache();
						flowRunner.gotoState(state29, this, cough.this, event);
						eventResult = EVENT_ABORTED;
						break EXECUTION;
					}
					if (eventResult != EVENT_IGNORED) return eventResult;
				}
			}
			if (event.triggers("sense.user.speech**")) {
				if (event.has("sem:repeat")) {
					eventResult = EVENT_CONSUMED;
					EXECUTION: {
						flowRunner.reentryState(this, cough.this, event);
						eventResult = EVENT_ABORTED;
						break EXECUTION;
					}
					if (eventResult != EVENT_IGNORED) return eventResult;
				}
			}
			if (event.triggers("sense.user.speech**")) {
				eventResult = EVENT_CONSUMED;
				EXECUTION: {
					flowRunner.reentryState(this, cough.this, event);
					eventResult = EVENT_ABORTED;
					break EXECUTION;
				}
				if (eventResult != EVENT_IGNORED) return eventResult;
			}
			eventResult = super.onFlowEvent(event);
			if (eventResult != EVENT_IGNORED) return eventResult;
			eventResult = callerHandlers(event);
			if (eventResult != EVENT_IGNORED) return eventResult;
			return EVENT_IGNORED;
		}

	}


	private class headache extends Dialog {



		@Override
		public void setFlowRunner(FlowRunner flowRunner) {
			super.setFlowRunner(flowRunner);
		}

		@Override
		public void onentry() {
			int eventResult;
			Event event = new Event("state.enter");
			EXECUTION: {
				map.questionSymptom(Symptom.HEADACHE);
				iristk.situated.SituatedDialog.say state30 = dialog.new say();
				state30.setText("Are you suffering from headache?");
				if (!flowRunner.callState(state30, headache.this, event)) {
					eventResult = EVENT_ABORTED;
					break EXECUTION;
				}
				iristk.situated.SituatedDialog.listen state31 = dialog.new listen();
				if (!flowRunner.callState(state31, headache.this, event)) {
					eventResult = EVENT_ABORTED;
					break EXECUTION;
				}
			}
		}

		@Override
		public int onFlowEvent(Event event) {
			int eventResult;
			if (event.triggers("sense.user.speech**")) {
				if (event.has("sem:yes")) {
					eventResult = EVENT_CONSUMED;
					EXECUTION: {
						map.answerSymptom(Symptom.HEADACHE);
						iristk.situated.SituatedDialog.say state32 = dialog.new say();
						state32.setText("Okay");
						if (!flowRunner.callState(state32, headache.this, event)) {
							eventResult = EVENT_ABORTED;
							break EXECUTION;
						}
						sleepy state33 = new sleepy();
						flowRunner.gotoState(state33, this, headache.this, event);
						eventResult = EVENT_ABORTED;
						break EXECUTION;
					}
					if (eventResult != EVENT_IGNORED) return eventResult;
				}
			}
			if (event.triggers("sense.user.speech**")) {
				if (event.has("sem:no")) {
					eventResult = EVENT_CONSUMED;
					EXECUTION: {
						iristk.situated.SituatedDialog.say state34 = dialog.new say();
						state34.setText("Good for you");
						if (!flowRunner.callState(state34, headache.this, event)) {
							eventResult = EVENT_ABORTED;
							break EXECUTION;
						}
						sleepy state35 = new sleepy();
						flowRunner.gotoState(state35, this, headache.this, event);
						eventResult = EVENT_ABORTED;
						break EXECUTION;
					}
					if (eventResult != EVENT_IGNORED) return eventResult;
				}
			}
			if (event.triggers("sense.user.speech**")) {
				if (event.has("sem:repeat")) {
					eventResult = EVENT_CONSUMED;
					EXECUTION: {
						flowRunner.reentryState(this, headache.this, event);
						eventResult = EVENT_ABORTED;
						break EXECUTION;
					}
					if (eventResult != EVENT_IGNORED) return eventResult;
				}
			}
			if (event.triggers("sense.user.speech**")) {
				eventResult = EVENT_CONSUMED;
				EXECUTION: {
					flowRunner.reentryState(this, headache.this, event);
					eventResult = EVENT_ABORTED;
					break EXECUTION;
				}
				if (eventResult != EVENT_IGNORED) return eventResult;
			}
			eventResult = super.onFlowEvent(event);
			if (eventResult != EVENT_IGNORED) return eventResult;
			eventResult = callerHandlers(event);
			if (eventResult != EVENT_IGNORED) return eventResult;
			return EVENT_IGNORED;
		}

	}


	private class sleepy extends Dialog {



		@Override
		public void setFlowRunner(FlowRunner flowRunner) {
			super.setFlowRunner(flowRunner);
		}

		@Override
		public void onentry() {
			int eventResult;
			Event event = new Event("state.enter");
			EXECUTION: {
				map.questionSymptom(Symptom.SLEEPY);
				iristk.situated.SituatedDialog.say state36 = dialog.new say();
				state36.setText("Are you sleepy?");
				if (!flowRunner.callState(state36, sleepy.this, event)) {
					eventResult = EVENT_ABORTED;
					break EXECUTION;
				}
				iristk.situated.SituatedDialog.listen state37 = dialog.new listen();
				if (!flowRunner.callState(state37, sleepy.this, event)) {
					eventResult = EVENT_ABORTED;
					break EXECUTION;
				}
			}
		}

		@Override
		public int onFlowEvent(Event event) {
			int eventResult;
			if (event.triggers("sense.user.speech**")) {
				if (event.has("sem:yes")) {
					eventResult = EVENT_CONSUMED;
					EXECUTION: {
						map.answerSymptom(Symptom.SLEEPY);
						iristk.situated.SituatedDialog.say state38 = dialog.new say();
						state38.setText("Okay");
						if (!flowRunner.callState(state38, sleepy.this, event)) {
							eventResult = EVENT_ABORTED;
							break EXECUTION;
						}
						fever state39 = new fever();
						flowRunner.gotoState(state39, this, sleepy.this, event);
						eventResult = EVENT_ABORTED;
						break EXECUTION;
					}
					if (eventResult != EVENT_IGNORED) return eventResult;
				}
			}
			if (event.triggers("sense.user.speech**")) {
				if (event.has("sem:no")) {
					eventResult = EVENT_CONSUMED;
					EXECUTION: {
						iristk.situated.SituatedDialog.say state40 = dialog.new say();
						state40.setText("Good for you, pneumonia");
						if (!flowRunner.callState(state40, sleepy.this, event)) {
							eventResult = EVENT_ABORTED;
							break EXECUTION;
						}
						fever state41 = new fever();
						flowRunner.gotoState(state41, this, sleepy.this, event);
						eventResult = EVENT_ABORTED;
						break EXECUTION;
					}
					if (eventResult != EVENT_IGNORED) return eventResult;
				}
			}
			if (event.triggers("sense.user.speech**")) {
				if (event.has("sem:repeat")) {
					eventResult = EVENT_CONSUMED;
					EXECUTION: {
						flowRunner.reentryState(this, sleepy.this, event);
						eventResult = EVENT_ABORTED;
						break EXECUTION;
					}
					if (eventResult != EVENT_IGNORED) return eventResult;
				}
			}
			if (event.triggers("sense.user.speech**")) {
				eventResult = EVENT_CONSUMED;
				EXECUTION: {
					flowRunner.reentryState(this, sleepy.this, event);
					eventResult = EVENT_ABORTED;
					break EXECUTION;
				}
				if (eventResult != EVENT_IGNORED) return eventResult;
			}
			eventResult = super.onFlowEvent(event);
			if (eventResult != EVENT_IGNORED) return eventResult;
			eventResult = callerHandlers(event);
			if (eventResult != EVENT_IGNORED) return eventResult;
			return EVENT_IGNORED;
		}

	}


	private class fever extends Dialog {



		@Override
		public void setFlowRunner(FlowRunner flowRunner) {
			super.setFlowRunner(flowRunner);
		}

		@Override
		public void onentry() {
			int eventResult;
			Event event = new Event("state.enter");
			EXECUTION: {
				map.questionSymptom(Symptom.FEVER);
				iristk.situated.SituatedDialog.say state42 = dialog.new say();
				state42.setText("Do you have fever?");
				if (!flowRunner.callState(state42, fever.this, event)) {
					eventResult = EVENT_ABORTED;
					break EXECUTION;
				}
				iristk.situated.SituatedDialog.listen state43 = dialog.new listen();
				if (!flowRunner.callState(state43, fever.this, event)) {
					eventResult = EVENT_ABORTED;
					break EXECUTION;
				}
			}
		}

		@Override
		public int onFlowEvent(Event event) {
			int eventResult;
			if (event.triggers("sense.user.speech**")) {
				if (event.has("sem:yes")) {
					eventResult = EVENT_CONSUMED;
					EXECUTION: {
						map.answerSymptom(Symptom.FEVER);
						iristk.situated.SituatedDialog.say state44 = dialog.new say();
						state44.setText("Okay");
						if (!flowRunner.callState(state44, fever.this, event)) {
							eventResult = EVENT_ABORTED;
							break EXECUTION;
						}
						pain state45 = new pain();
						flowRunner.gotoState(state45, this, fever.this, event);
						eventResult = EVENT_ABORTED;
						break EXECUTION;
					}
					if (eventResult != EVENT_IGNORED) return eventResult;
				}
			}
			if (event.triggers("sense.user.speech**")) {
				if (event.has("sem:no")) {
					eventResult = EVENT_CONSUMED;
					EXECUTION: {
						iristk.situated.SituatedDialog.say state46 = dialog.new say();
						state46.setText("Good for you");
						if (!flowRunner.callState(state46, fever.this, event)) {
							eventResult = EVENT_ABORTED;
							break EXECUTION;
						}
						pain state47 = new pain();
						flowRunner.gotoState(state47, this, fever.this, event);
						eventResult = EVENT_ABORTED;
						break EXECUTION;
					}
					if (eventResult != EVENT_IGNORED) return eventResult;
				}
			}
			if (event.triggers("sense.user.speech**")) {
				if (event.has("sem:repeat")) {
					eventResult = EVENT_CONSUMED;
					EXECUTION: {
						flowRunner.reentryState(this, fever.this, event);
						eventResult = EVENT_ABORTED;
						break EXECUTION;
					}
					if (eventResult != EVENT_IGNORED) return eventResult;
				}
			}
			if (event.triggers("sense.user.speech**")) {
				eventResult = EVENT_CONSUMED;
				EXECUTION: {
					flowRunner.reentryState(this, fever.this, event);
					eventResult = EVENT_ABORTED;
					break EXECUTION;
				}
				if (eventResult != EVENT_IGNORED) return eventResult;
			}
			eventResult = super.onFlowEvent(event);
			if (eventResult != EVENT_IGNORED) return eventResult;
			eventResult = callerHandlers(event);
			if (eventResult != EVENT_IGNORED) return eventResult;
			return EVENT_IGNORED;
		}

	}


	private class pain extends Dialog {



		@Override
		public void setFlowRunner(FlowRunner flowRunner) {
			super.setFlowRunner(flowRunner);
		}

		@Override
		public void onentry() {
			int eventResult;
			Event event = new Event("state.enter");
			EXECUTION: {
				map.questionSymptom(Symptom.PAIN);
				map.questionSymptom(Symptom.FLUCHEAT);
				map.questionSymptom(Symptom.FLUCHEAT);
				iristk.situated.SituatedDialog.say state48 = dialog.new say();
				state48.setText("Are you in pain?");
				if (!flowRunner.callState(state48, pain.this, event)) {
					eventResult = EVENT_ABORTED;
					break EXECUTION;
				}
				Event sendEvent49 = new Event("action.gesture");
				sendEvent49.put("name", "emotion_anger");
				flowRunner.sendEvent(sendEvent49, pain.this, event);
				iristk.situated.SituatedDialog.listen state50 = dialog.new listen();
				if (!flowRunner.callState(state50, pain.this, event)) {
					eventResult = EVENT_ABORTED;
					break EXECUTION;
				}
			}
		}

		@Override
		public int onFlowEvent(Event event) {
			int eventResult;
			if (event.triggers("sense.user.speech**")) {
				if (event.has("sem:yes")) {
					eventResult = EVENT_CONSUMED;
					EXECUTION: {
						iristk.situated.SituatedDialog.say state51 = dialog.new say();
						state51.setText("Where are you suffering from pain? In your eyes, your ears, your muscles or your chest?");
						if (!flowRunner.callState(state51, pain.this, event)) {
							eventResult = EVENT_ABORTED;
							break EXECUTION;
						}
						whatpain state52 = new whatpain();
						flowRunner.gotoState(state52, this, pain.this, event);
						eventResult = EVENT_ABORTED;
						break EXECUTION;
					}
					if (eventResult != EVENT_IGNORED) return eventResult;
				}
			}
			if (event.triggers("sense.user.speech**")) {
				if (event.has("sem:no")) {
					eventResult = EVENT_CONSUMED;
					EXECUTION: {
						iristk.situated.SituatedDialog.say state53 = dialog.new say();
						state53.setText("Keep it up!");
						if (!flowRunner.callState(state53, pain.this, event)) {
							eventResult = EVENT_ABORTED;
							break EXECUTION;
						}
						evaluate state54 = new evaluate();
						flowRunner.gotoState(state54, this, pain.this, event);
						eventResult = EVENT_ABORTED;
						break EXECUTION;
					}
					if (eventResult != EVENT_IGNORED) return eventResult;
				}
			}
			if (event.triggers("sense.user.speech**")) {
				if (event.has("sem:repeat")) {
					eventResult = EVENT_CONSUMED;
					EXECUTION: {
						flowRunner.reentryState(this, pain.this, event);
						eventResult = EVENT_ABORTED;
						break EXECUTION;
					}
					if (eventResult != EVENT_IGNORED) return eventResult;
				}
			}
			if (event.triggers("sense.user.speech**")) {
				eventResult = EVENT_CONSUMED;
				EXECUTION: {
					flowRunner.reentryState(this, pain.this, event);
					eventResult = EVENT_ABORTED;
					break EXECUTION;
				}
				if (eventResult != EVENT_IGNORED) return eventResult;
			}
			eventResult = super.onFlowEvent(event);
			if (eventResult != EVENT_IGNORED) return eventResult;
			eventResult = callerHandlers(event);
			if (eventResult != EVENT_IGNORED) return eventResult;
			return EVENT_IGNORED;
		}

	}


	private class repain extends Dialog {



		@Override
		public void setFlowRunner(FlowRunner flowRunner) {
			super.setFlowRunner(flowRunner);
		}

		@Override
		public void onentry() {
			int eventResult;
			Event event = new Event("state.enter");
			EXECUTION: {
				iristk.situated.SituatedDialog.say state55 = dialog.new say();
				state55.setText("Are you in pain in another bodypart?");
				if (!flowRunner.callState(state55, repain.this, event)) {
					eventResult = EVENT_ABORTED;
					break EXECUTION;
				}
				Event sendEvent56 = new Event("action.gesture");
				sendEvent56.put("name", "emotion_anger");
				flowRunner.sendEvent(sendEvent56, repain.this, event);
				iristk.situated.SituatedDialog.listen state57 = dialog.new listen();
				if (!flowRunner.callState(state57, repain.this, event)) {
					eventResult = EVENT_ABORTED;
					break EXECUTION;
				}
			}
		}

		@Override
		public int onFlowEvent(Event event) {
			int eventResult;
			if (event.triggers("sense.user.speech**")) {
				if (event.has("sem:yes")) {
					eventResult = EVENT_CONSUMED;
					EXECUTION: {
						iristk.situated.SituatedDialog.say state58 = dialog.new say();
						state58.setText("Which one?");
						if (!flowRunner.callState(state58, repain.this, event)) {
							eventResult = EVENT_ABORTED;
							break EXECUTION;
						}
						whatpain state59 = new whatpain();
						flowRunner.gotoState(state59, this, repain.this, event);
						eventResult = EVENT_ABORTED;
						break EXECUTION;
					}
					if (eventResult != EVENT_IGNORED) return eventResult;
				}
			}
			if (event.triggers("sense.user.speech**")) {
				if (event.has("sem:no")) {
					eventResult = EVENT_CONSUMED;
					EXECUTION: {
						iristk.situated.SituatedDialog.say state60 = dialog.new say();
						state60.setText("Keep it up!");
						if (!flowRunner.callState(state60, repain.this, event)) {
							eventResult = EVENT_ABORTED;
							break EXECUTION;
						}
						evaluate state61 = new evaluate();
						flowRunner.gotoState(state61, this, repain.this, event);
						eventResult = EVENT_ABORTED;
						break EXECUTION;
					}
					if (eventResult != EVENT_IGNORED) return eventResult;
				}
			}
			if (event.triggers("sense.user.speech**")) {
				if (event.has("sem:repeat")) {
					eventResult = EVENT_CONSUMED;
					EXECUTION: {
						flowRunner.reentryState(this, repain.this, event);
						eventResult = EVENT_ABORTED;
						break EXECUTION;
					}
					if (eventResult != EVENT_IGNORED) return eventResult;
				}
			}
			if (event.triggers("sense.user.speech**")) {
				eventResult = EVENT_CONSUMED;
				EXECUTION: {
					flowRunner.reentryState(this, repain.this, event);
					eventResult = EVENT_ABORTED;
					break EXECUTION;
				}
				if (eventResult != EVENT_IGNORED) return eventResult;
			}
			eventResult = super.onFlowEvent(event);
			if (eventResult != EVENT_IGNORED) return eventResult;
			eventResult = callerHandlers(event);
			if (eventResult != EVENT_IGNORED) return eventResult;
			return EVENT_IGNORED;
		}

	}


	private class whatpain extends Dialog {



		@Override
		public void setFlowRunner(FlowRunner flowRunner) {
			super.setFlowRunner(flowRunner);
		}

		@Override
		public void onentry() {
			int eventResult;
			Event event = new Event("state.enter");
			EXECUTION: {
				iristk.situated.SituatedDialog.listen state62 = dialog.new listen();
				if (!flowRunner.callState(state62, whatpain.this, event)) {
					eventResult = EVENT_ABORTED;
					break EXECUTION;
				}
			}
		}

		@Override
		public int onFlowEvent(Event event) {
			int eventResult;
			if (event.triggers("sense.user.speech**")) {
				if (event.has("sem:eyes")) {
					eventResult = EVENT_CONSUMED;
					EXECUTION: {
						map.answerSymptom(Symptom.PAIN_EYES);
						iristk.situated.SituatedDialog.say state63 = dialog.new say();
						state63.setText("Okay, your eyes");
						if (!flowRunner.callState(state63, whatpain.this, event)) {
							eventResult = EVENT_ABORTED;
							break EXECUTION;
						}
						repain state64 = new repain();
						flowRunner.gotoState(state64, this, whatpain.this, event);
						eventResult = EVENT_ABORTED;
						break EXECUTION;
					}
					if (eventResult != EVENT_IGNORED) return eventResult;
				}
			}
			if (event.triggers("sense.user.speech**")) {
				if (event.has("sem:ears")) {
					eventResult = EVENT_CONSUMED;
					EXECUTION: {
						map.answerSymptom(Symptom.PAIN_EARS);
						iristk.situated.SituatedDialog.say state65 = dialog.new say();
						state65.setText("Okay, your ears");
						if (!flowRunner.callState(state65, whatpain.this, event)) {
							eventResult = EVENT_ABORTED;
							break EXECUTION;
						}
						repain state66 = new repain();
						flowRunner.gotoState(state66, this, whatpain.this, event);
						eventResult = EVENT_ABORTED;
						break EXECUTION;
					}
					if (eventResult != EVENT_IGNORED) return eventResult;
				}
			}
			if (event.triggers("sense.user.speech**")) {
				if (event.has("sem:muscles")) {
					eventResult = EVENT_CONSUMED;
					EXECUTION: {
						map.answerSymptom(Symptom.PAIN_MUSCLES);
						iristk.situated.SituatedDialog.say state67 = dialog.new say();
						state67.setText("Okay, your muscles");
						if (!flowRunner.callState(state67, whatpain.this, event)) {
							eventResult = EVENT_ABORTED;
							break EXECUTION;
						}
						repain state68 = new repain();
						flowRunner.gotoState(state68, this, whatpain.this, event);
						eventResult = EVENT_ABORTED;
						break EXECUTION;
					}
					if (eventResult != EVENT_IGNORED) return eventResult;
				}
			}
			if (event.triggers("sense.user.speech**")) {
				if (event.has("sem:chest")) {
					eventResult = EVENT_CONSUMED;
					EXECUTION: {
						map.answerSymptom(Symptom.PAIN_CHEST);
						iristk.situated.SituatedDialog.say state69 = dialog.new say();
						state69.setText("Okay, your chest");
						if (!flowRunner.callState(state69, whatpain.this, event)) {
							eventResult = EVENT_ABORTED;
							break EXECUTION;
						}
						repain state70 = new repain();
						flowRunner.gotoState(state70, this, whatpain.this, event);
						eventResult = EVENT_ABORTED;
						break EXECUTION;
					}
					if (eventResult != EVENT_IGNORED) return eventResult;
				}
			}
			if (event.triggers("sense.user.speech**")) {
				if (event.has("sem:repeat")) {
					eventResult = EVENT_CONSUMED;
					EXECUTION: {
						flowRunner.reentryState(this, whatpain.this, event);
						eventResult = EVENT_ABORTED;
						break EXECUTION;
					}
					if (eventResult != EVENT_IGNORED) return eventResult;
				}
			}
			if (event.triggers("sense.user.speech**")) {
				eventResult = EVENT_CONSUMED;
				EXECUTION: {
					flowRunner.reentryState(this, whatpain.this, event);
					eventResult = EVENT_ABORTED;
					break EXECUTION;
				}
				if (eventResult != EVENT_IGNORED) return eventResult;
			}
			eventResult = super.onFlowEvent(event);
			if (eventResult != EVENT_IGNORED) return eventResult;
			eventResult = callerHandlers(event);
			if (eventResult != EVENT_IGNORED) return eventResult;
			return EVENT_IGNORED;
		}

	}


	private class evaluate extends State {



		@Override
		public void setFlowRunner(FlowRunner flowRunner) {
			super.setFlowRunner(flowRunner);
		}

		@Override
		public void onentry() {
			int eventResult;
			Event event = new Event("state.enter");
			EXECUTION: {
				map.evaluate();
				int c = asInteger(map.getPercentage(Disease.CONCUSSION));
				if ((c>70)) {
					dizzy state71 = new dizzy();
					flowRunner.gotoState(state71, this, evaluate.this, event);
					eventResult = EVENT_ABORTED;
					break EXECUTION;
				}
			}
		}

		@Override
		public int onFlowEvent(Event event) {
			int eventResult;
			eventResult = super.onFlowEvent(event);
			if (eventResult != EVENT_IGNORED) return eventResult;
			eventResult = callerHandlers(event);
			if (eventResult != EVENT_IGNORED) return eventResult;
			return EVENT_IGNORED;
		}

	}


	private class frozen extends Dialog {



		@Override
		public void setFlowRunner(FlowRunner flowRunner) {
			super.setFlowRunner(flowRunner);
		}

		@Override
		public void onentry() {
			int eventResult;
			Event event = new Event("state.enter");
			EXECUTION: {
				map.questionSymptom(Symptom.FROZEN);
				iristk.situated.SituatedDialog.say state72 = dialog.new say();
				state72.setText("Are you frozen?");
				if (!flowRunner.callState(state72, frozen.this, event)) {
					eventResult = EVENT_ABORTED;
					break EXECUTION;
				}
				iristk.situated.SituatedDialog.listen state73 = dialog.new listen();
				if (!flowRunner.callState(state73, frozen.this, event)) {
					eventResult = EVENT_ABORTED;
					break EXECUTION;
				}
			}
		}

		@Override
		public int onFlowEvent(Event event) {
			int eventResult;
			if (event.triggers("sense.user.speech**")) {
				if (event.has("sem:yes")) {
					eventResult = EVENT_CONSUMED;
					EXECUTION: {
						map.answerSymptom(Symptom.FROZEN);
						iristk.situated.SituatedDialog.say state74 = dialog.new say();
						state74.setText("Okay");
						if (!flowRunner.callState(state74, frozen.this, event)) {
							eventResult = EVENT_ABORTED;
							break EXECUTION;
						}
						runnose state75 = new runnose();
						flowRunner.gotoState(state75, this, frozen.this, event);
						eventResult = EVENT_ABORTED;
						break EXECUTION;
					}
					if (eventResult != EVENT_IGNORED) return eventResult;
				}
			}
			if (event.triggers("sense.user.speech**")) {
				if (event.has("sem:no")) {
					eventResult = EVENT_CONSUMED;
					EXECUTION: {
						iristk.situated.SituatedDialog.say state76 = dialog.new say();
						state76.setText("Good for you");
						if (!flowRunner.callState(state76, frozen.this, event)) {
							eventResult = EVENT_ABORTED;
							break EXECUTION;
						}
						runnose state77 = new runnose();
						flowRunner.gotoState(state77, this, frozen.this, event);
						eventResult = EVENT_ABORTED;
						break EXECUTION;
					}
					if (eventResult != EVENT_IGNORED) return eventResult;
				}
			}
			if (event.triggers("sense.user.speech**")) {
				if (event.has("sem:repeat")) {
					eventResult = EVENT_CONSUMED;
					EXECUTION: {
						flowRunner.reentryState(this, frozen.this, event);
						eventResult = EVENT_ABORTED;
						break EXECUTION;
					}
					if (eventResult != EVENT_IGNORED) return eventResult;
				}
			}
			if (event.triggers("sense.user.speech**")) {
				eventResult = EVENT_CONSUMED;
				EXECUTION: {
					flowRunner.reentryState(this, frozen.this, event);
					eventResult = EVENT_ABORTED;
					break EXECUTION;
				}
				if (eventResult != EVENT_IGNORED) return eventResult;
			}
			eventResult = super.onFlowEvent(event);
			if (eventResult != EVENT_IGNORED) return eventResult;
			eventResult = callerHandlers(event);
			if (eventResult != EVENT_IGNORED) return eventResult;
			return EVENT_IGNORED;
		}

	}


	private class runnose extends Dialog {



		@Override
		public void setFlowRunner(FlowRunner flowRunner) {
			super.setFlowRunner(flowRunner);
		}

		@Override
		public void onentry() {
			int eventResult;
			Event event = new Event("state.enter");
			EXECUTION: {
				map.questionSymptom(Symptom.RUNNY_NOSE);
				iristk.situated.SituatedDialog.say state78 = dialog.new say();
				state78.setText("Do you have a runny nose?");
				if (!flowRunner.callState(state78, runnose.this, event)) {
					eventResult = EVENT_ABORTED;
					break EXECUTION;
				}
				iristk.situated.SituatedDialog.listen state79 = dialog.new listen();
				if (!flowRunner.callState(state79, runnose.this, event)) {
					eventResult = EVENT_ABORTED;
					break EXECUTION;
				}
			}
		}

		@Override
		public int onFlowEvent(Event event) {
			int eventResult;
			if (event.triggers("sense.user.speech**")) {
				if (event.has("sem:yes")) {
					eventResult = EVENT_CONSUMED;
					EXECUTION: {
						map.answerSymptom(Symptom.RUNNY_NOSE);
						iristk.situated.SituatedDialog.say state80 = dialog.new say();
						state80.setText("Okay");
						if (!flowRunner.callState(state80, runnose.this, event)) {
							eventResult = EVENT_ABORTED;
							break EXECUTION;
						}
						endevaluation state81 = new endevaluation();
						flowRunner.gotoState(state81, this, runnose.this, event);
						eventResult = EVENT_ABORTED;
						break EXECUTION;
					}
					if (eventResult != EVENT_IGNORED) return eventResult;
				}
			}
			if (event.triggers("sense.user.speech**")) {
				if (event.has("sem:no")) {
					eventResult = EVENT_CONSUMED;
					EXECUTION: {
						iristk.situated.SituatedDialog.say state82 = dialog.new say();
						state82.setText("Good for you");
						if (!flowRunner.callState(state82, runnose.this, event)) {
							eventResult = EVENT_ABORTED;
							break EXECUTION;
						}
						endevaluation state83 = new endevaluation();
						flowRunner.gotoState(state83, this, runnose.this, event);
						eventResult = EVENT_ABORTED;
						break EXECUTION;
					}
					if (eventResult != EVENT_IGNORED) return eventResult;
				}
			}
			if (event.triggers("sense.user.speech**")) {
				if (event.has("sem:repeat")) {
					eventResult = EVENT_CONSUMED;
					EXECUTION: {
						flowRunner.reentryState(this, runnose.this, event);
						eventResult = EVENT_ABORTED;
						break EXECUTION;
					}
					if (eventResult != EVENT_IGNORED) return eventResult;
				}
			}
			if (event.triggers("sense.user.speech**")) {
				eventResult = EVENT_CONSUMED;
				EXECUTION: {
					flowRunner.reentryState(this, runnose.this, event);
					eventResult = EVENT_ABORTED;
					break EXECUTION;
				}
				if (eventResult != EVENT_IGNORED) return eventResult;
			}
			eventResult = super.onFlowEvent(event);
			if (eventResult != EVENT_IGNORED) return eventResult;
			eventResult = callerHandlers(event);
			if (eventResult != EVENT_IGNORED) return eventResult;
			return EVENT_IGNORED;
		}

	}


	private class dizzy extends Dialog {



		@Override
		public void setFlowRunner(FlowRunner flowRunner) {
			super.setFlowRunner(flowRunner);
		}

		@Override
		public void onentry() {
			int eventResult;
			Event event = new Event("state.enter");
			EXECUTION: {
				map.questionSymptom(Symptom.DIZZY);
				iristk.situated.SituatedDialog.say state84 = dialog.new say();
				state84.setText("Are you feeling dizzy?");
				if (!flowRunner.callState(state84, dizzy.this, event)) {
					eventResult = EVENT_ABORTED;
					break EXECUTION;
				}
				iristk.situated.SituatedDialog.listen state85 = dialog.new listen();
				if (!flowRunner.callState(state85, dizzy.this, event)) {
					eventResult = EVENT_ABORTED;
					break EXECUTION;
				}
			}
		}

		@Override
		public int onFlowEvent(Event event) {
			int eventResult;
			if (event.triggers("sense.user.speech**")) {
				if (event.has("sem:yes")) {
					eventResult = EVENT_CONSUMED;
					EXECUTION: {
						map.answerSymptom(Symptom.DIZZY);
						iristk.situated.SituatedDialog.say state86 = dialog.new say();
						state86.setText("Okay");
						if (!flowRunner.callState(state86, dizzy.this, event)) {
							eventResult = EVENT_ABORTED;
							break EXECUTION;
						}
						nausea state87 = new nausea();
						flowRunner.gotoState(state87, this, dizzy.this, event);
						eventResult = EVENT_ABORTED;
						break EXECUTION;
					}
					if (eventResult != EVENT_IGNORED) return eventResult;
				}
			}
			if (event.triggers("sense.user.speech**")) {
				if (event.has("sem:no")) {
					eventResult = EVENT_CONSUMED;
					EXECUTION: {
						iristk.situated.SituatedDialog.say state88 = dialog.new say();
						state88.setText("Good for you");
						if (!flowRunner.callState(state88, dizzy.this, event)) {
							eventResult = EVENT_ABORTED;
							break EXECUTION;
						}
						nausea state89 = new nausea();
						flowRunner.gotoState(state89, this, dizzy.this, event);
						eventResult = EVENT_ABORTED;
						break EXECUTION;
					}
					if (eventResult != EVENT_IGNORED) return eventResult;
				}
			}
			if (event.triggers("sense.user.speech**")) {
				if (event.has("sem:repeat")) {
					eventResult = EVENT_CONSUMED;
					EXECUTION: {
						flowRunner.reentryState(this, dizzy.this, event);
						eventResult = EVENT_ABORTED;
						break EXECUTION;
					}
					if (eventResult != EVENT_IGNORED) return eventResult;
				}
			}
			if (event.triggers("sense.user.speech**")) {
				eventResult = EVENT_CONSUMED;
				EXECUTION: {
					flowRunner.reentryState(this, dizzy.this, event);
					eventResult = EVENT_ABORTED;
					break EXECUTION;
				}
				if (eventResult != EVENT_IGNORED) return eventResult;
			}
			eventResult = super.onFlowEvent(event);
			if (eventResult != EVENT_IGNORED) return eventResult;
			eventResult = callerHandlers(event);
			if (eventResult != EVENT_IGNORED) return eventResult;
			return EVENT_IGNORED;
		}

	}


	private class nausea extends Dialog {



		@Override
		public void setFlowRunner(FlowRunner flowRunner) {
			super.setFlowRunner(flowRunner);
		}

		@Override
		public void onentry() {
			int eventResult;
			Event event = new Event("state.enter");
			EXECUTION: {
				map.questionSymptom(Symptom.NAUSEA);
				iristk.situated.SituatedDialog.say state90 = dialog.new say();
				state90.setText("Are you nauseous?");
				if (!flowRunner.callState(state90, nausea.this, event)) {
					eventResult = EVENT_ABORTED;
					break EXECUTION;
				}
				iristk.situated.SituatedDialog.listen state91 = dialog.new listen();
				if (!flowRunner.callState(state91, nausea.this, event)) {
					eventResult = EVENT_ABORTED;
					break EXECUTION;
				}
			}
		}

		@Override
		public int onFlowEvent(Event event) {
			int eventResult;
			if (event.triggers("sense.user.speech**")) {
				if (event.has("sem:yes")) {
					eventResult = EVENT_CONSUMED;
					EXECUTION: {
						map.answerSymptom(Symptom.NAUSEA);
						iristk.situated.SituatedDialog.say state92 = dialog.new say();
						state92.setText("Okay");
						if (!flowRunner.callState(state92, nausea.this, event)) {
							eventResult = EVENT_ABORTED;
							break EXECUTION;
						}
						vomit state93 = new vomit();
						flowRunner.gotoState(state93, this, nausea.this, event);
						eventResult = EVENT_ABORTED;
						break EXECUTION;
					}
					if (eventResult != EVENT_IGNORED) return eventResult;
				}
			}
			if (event.triggers("sense.user.speech**")) {
				if (event.has("sem:no")) {
					eventResult = EVENT_CONSUMED;
					EXECUTION: {
						iristk.situated.SituatedDialog.say state94 = dialog.new say();
						state94.setText("Good for you");
						if (!flowRunner.callState(state94, nausea.this, event)) {
							eventResult = EVENT_ABORTED;
							break EXECUTION;
						}
						vomit state95 = new vomit();
						flowRunner.gotoState(state95, this, nausea.this, event);
						eventResult = EVENT_ABORTED;
						break EXECUTION;
					}
					if (eventResult != EVENT_IGNORED) return eventResult;
				}
			}
			if (event.triggers("sense.user.speech**")) {
				if (event.has("sem:repeat")) {
					eventResult = EVENT_CONSUMED;
					EXECUTION: {
						flowRunner.reentryState(this, nausea.this, event);
						eventResult = EVENT_ABORTED;
						break EXECUTION;
					}
					if (eventResult != EVENT_IGNORED) return eventResult;
				}
			}
			if (event.triggers("sense.user.speech**")) {
				eventResult = EVENT_CONSUMED;
				EXECUTION: {
					flowRunner.reentryState(this, nausea.this, event);
					eventResult = EVENT_ABORTED;
					break EXECUTION;
				}
				if (eventResult != EVENT_IGNORED) return eventResult;
			}
			eventResult = super.onFlowEvent(event);
			if (eventResult != EVENT_IGNORED) return eventResult;
			eventResult = callerHandlers(event);
			if (eventResult != EVENT_IGNORED) return eventResult;
			return EVENT_IGNORED;
		}

	}


	private class vomit extends Dialog {



		@Override
		public void setFlowRunner(FlowRunner flowRunner) {
			super.setFlowRunner(flowRunner);
		}

		@Override
		public void onentry() {
			int eventResult;
			Event event = new Event("state.enter");
			EXECUTION: {
				map.questionSymptom(Symptom.VOMIT);
				iristk.situated.SituatedDialog.say state96 = dialog.new say();
				state96.setText("Have you vomited?");
				if (!flowRunner.callState(state96, vomit.this, event)) {
					eventResult = EVENT_ABORTED;
					break EXECUTION;
				}
				iristk.situated.SituatedDialog.listen state97 = dialog.new listen();
				if (!flowRunner.callState(state97, vomit.this, event)) {
					eventResult = EVENT_ABORTED;
					break EXECUTION;
				}
			}
		}

		@Override
		public int onFlowEvent(Event event) {
			int eventResult;
			if (event.triggers("sense.user.speech**")) {
				if (event.has("sem:yes")) {
					eventResult = EVENT_CONSUMED;
					EXECUTION: {
						map.answerSymptom(Symptom.VOMIT);
						iristk.situated.SituatedDialog.say state98 = dialog.new say();
						state98.setText("Okay");
						if (!flowRunner.callState(state98, vomit.this, event)) {
							eventResult = EVENT_ABORTED;
							break EXECUTION;
						}
						amnesia state99 = new amnesia();
						flowRunner.gotoState(state99, this, vomit.this, event);
						eventResult = EVENT_ABORTED;
						break EXECUTION;
					}
					if (eventResult != EVENT_IGNORED) return eventResult;
				}
			}
			if (event.triggers("sense.user.speech**")) {
				if (event.has("sem:no")) {
					eventResult = EVENT_CONSUMED;
					EXECUTION: {
						iristk.situated.SituatedDialog.say state100 = dialog.new say();
						state100.setText("Good for you");
						if (!flowRunner.callState(state100, vomit.this, event)) {
							eventResult = EVENT_ABORTED;
							break EXECUTION;
						}
						amnesia state101 = new amnesia();
						flowRunner.gotoState(state101, this, vomit.this, event);
						eventResult = EVENT_ABORTED;
						break EXECUTION;
					}
					if (eventResult != EVENT_IGNORED) return eventResult;
				}
			}
			if (event.triggers("sense.user.speech**")) {
				if (event.has("sem:repeat")) {
					eventResult = EVENT_CONSUMED;
					EXECUTION: {
						flowRunner.reentryState(this, vomit.this, event);
						eventResult = EVENT_ABORTED;
						break EXECUTION;
					}
					if (eventResult != EVENT_IGNORED) return eventResult;
				}
			}
			if (event.triggers("sense.user.speech**")) {
				eventResult = EVENT_CONSUMED;
				EXECUTION: {
					flowRunner.reentryState(this, vomit.this, event);
					eventResult = EVENT_ABORTED;
					break EXECUTION;
				}
				if (eventResult != EVENT_IGNORED) return eventResult;
			}
			eventResult = super.onFlowEvent(event);
			if (eventResult != EVENT_IGNORED) return eventResult;
			eventResult = callerHandlers(event);
			if (eventResult != EVENT_IGNORED) return eventResult;
			return EVENT_IGNORED;
		}

	}


	private class amnesia extends Dialog {



		@Override
		public void setFlowRunner(FlowRunner flowRunner) {
			super.setFlowRunner(flowRunner);
		}

		@Override
		public void onentry() {
			int eventResult;
			Event event = new Event("state.enter");
			EXECUTION: {
				map.questionSymptom(Symptom.AMNESIA);
				iristk.situated.SituatedDialog.say state102 = dialog.new say();
				state102.setText("Are you experiencing amnesia?");
				if (!flowRunner.callState(state102, amnesia.this, event)) {
					eventResult = EVENT_ABORTED;
					break EXECUTION;
				}
				iristk.situated.SituatedDialog.listen state103 = dialog.new listen();
				if (!flowRunner.callState(state103, amnesia.this, event)) {
					eventResult = EVENT_ABORTED;
					break EXECUTION;
				}
			}
		}

		@Override
		public int onFlowEvent(Event event) {
			int eventResult;
			if (event.triggers("sense.user.speech**")) {
				if (event.has("sem:yes")) {
					eventResult = EVENT_CONSUMED;
					EXECUTION: {
						map.answerSymptom(Symptom.AMNESIA);
						iristk.situated.SituatedDialog.say state104 = dialog.new say();
						state104.setText("Okay");
						if (!flowRunner.callState(state104, amnesia.this, event)) {
							eventResult = EVENT_ABORTED;
							break EXECUTION;
						}
						endevaluation state105 = new endevaluation();
						flowRunner.gotoState(state105, this, amnesia.this, event);
						eventResult = EVENT_ABORTED;
						break EXECUTION;
					}
					if (eventResult != EVENT_IGNORED) return eventResult;
				}
			}
			if (event.triggers("sense.user.speech**")) {
				if (event.has("sem:no")) {
					eventResult = EVENT_CONSUMED;
					EXECUTION: {
						iristk.situated.SituatedDialog.say state106 = dialog.new say();
						state106.setText("Good for you");
						if (!flowRunner.callState(state106, amnesia.this, event)) {
							eventResult = EVENT_ABORTED;
							break EXECUTION;
						}
						endevaluation state107 = new endevaluation();
						flowRunner.gotoState(state107, this, amnesia.this, event);
						eventResult = EVENT_ABORTED;
						break EXECUTION;
					}
					if (eventResult != EVENT_IGNORED) return eventResult;
				}
			}
			if (event.triggers("sense.user.speech**")) {
				if (event.has("sem:repeat")) {
					eventResult = EVENT_CONSUMED;
					EXECUTION: {
						flowRunner.reentryState(this, amnesia.this, event);
						eventResult = EVENT_ABORTED;
						break EXECUTION;
					}
					if (eventResult != EVENT_IGNORED) return eventResult;
				}
			}
			if (event.triggers("sense.user.speech**")) {
				eventResult = EVENT_CONSUMED;
				EXECUTION: {
					flowRunner.reentryState(this, amnesia.this, event);
					eventResult = EVENT_ABORTED;
					break EXECUTION;
				}
				if (eventResult != EVENT_IGNORED) return eventResult;
			}
			eventResult = super.onFlowEvent(event);
			if (eventResult != EVENT_IGNORED) return eventResult;
			eventResult = callerHandlers(event);
			if (eventResult != EVENT_IGNORED) return eventResult;
			return EVENT_IGNORED;
		}

	}


	private class endevaluation extends Dialog {



		@Override
		public void setFlowRunner(FlowRunner flowRunner) {
			super.setFlowRunner(flowRunner);
		}

		@Override
		public void onentry() {
			int eventResult;
			Event event = new Event("state.enter");
			EXECUTION: {
				iristk.situated.SituatedDialog.say state108 = dialog.new say();
				state108.setText("Hope you don't die!");
				if (!flowRunner.callState(state108, endevaluation.this, event)) {
					eventResult = EVENT_ABORTED;
					break EXECUTION;
				}
			}
		}

		@Override
		public int onFlowEvent(Event event) {
			int eventResult;
			eventResult = super.onFlowEvent(event);
			if (eventResult != EVENT_IGNORED) return eventResult;
			eventResult = callerHandlers(event);
			if (eventResult != EVENT_IGNORED) return eventResult;
			return EVENT_IGNORED;
		}

	}


}
