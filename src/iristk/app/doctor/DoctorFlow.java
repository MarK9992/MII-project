package iristk.app.doctor;

import java.util.List;
import iristk.system.Event;
import iristk.app.quiz.Question;
import iristk.app.quiz.QuestionSet;
import iristk.flow.*;
import iristk.util.Record;
import static iristk.util.Converters.*;

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
						map.get("comcold").set(0, map.get("comcold").get(0) + 1); map.get("comcold").set(1, map.get("comcold").get(1) + 1);
						map.get("flu").set(0, map.get("flu").get(0) + 1); map.get("flu").set(1, map.get("flu").get(1) + 1);
						map.get("concussion").set(0, map.get("concussion").get(0) + 1); map.get("concussion").set(1, map.get("concussion").get(1) + 1);
						map.get("mono").set(0, map.get("mono").get(0) + 1); map.get("mono").set(1, map.get("mono").get(1) + 1);
						map.get("lyme disease").set(0, map.get("lyme disease").get(0) + 1); map.get("lyme disease").set(1, map.get("lyme disease").get(1) + 1);
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
						map.get("comcold").set(1, map.get("comcold").get(1) + 1);
						map.get("flu").set(1, map.get("flu").get(1) + 1);
						map.get("concussion").set(1, map.get("concussion").get(1) + 1);
						map.get("mono").set(1, map.get("mono").get(1) + 1);
						map.get("lyme disease").set(1, map.get("lyme disease").get(1) + 1);
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
						map.get("bronchitis").set(0, map.get("bronchitis").get(0) + 1); map.get("bronchitis").set(1, map.get("bronchitis").get(1) + 1);
						map.get("flu").set(0, map.get("flu").get(0) + 1); map.get("flu").set(1, map.get("flu").get(1) + 1);
						map.get("concussion").set(0, map.get("concussion").get(0) + 1); map.get("concussion").set(1, map.get("concussion").get(1) + 1);
						map.get("pneumonia").set(0, map.get("pneumonia").get(0) + 1); map.get("pneumonia").set(1, map.get("pneumonia").get(1) + 1);
						map.get("lyme disease").set(0, map.get("lyme disease").get(0) + 1); map.get("lyme disease").set(1, map.get("lyme disease").get(1) + 1);
						map.get("myocarditis").set(0, map.get("myocarditis").get(0) + 1); map.get("myocarditis").set(1, map.get("myocarditis").get(1) + 1);
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
						map.get("bronchitis").set(1, map.get("bronchitis").get(1) + 1);
						map.get("flu").set(1, map.get("flu").get(1) + 1);
						map.get("concussion").set(1, map.get("concussion").get(1) + 1);
						map.get("pneumonia").set(1, map.get("pneumonia").get(1) + 1);
						map.get("lyme disease").set(1, map.get("lyme disease").get(1) + 1);
						map.get("myocarditis").set(1, map.get("myocarditis").get(1) + 1);
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
						map.get("otitis").set(0, map.get("otitis").get(0) + 1); map.get("otitis").set(1, map.get("otitis").get(1) + 1);
						map.get("flu").set(0, map.get("flu").get(0) + 1); map.get("flu").set(1, map.get("flu").get(1) + 1);
						map.get("mono").set(0, map.get("mono").get(0) + 1); map.get("mono").set(1, map.get("mono").get(1) + 1);
						map.get("pneumonia").set(0, map.get("pneumonia").get(0) + 1); map.get("pneumonia").set(1, map.get("pneumonia").get(1) + 1);
						map.get("myocarditis").set(0, map.get("myocarditis").get(0) + 1); map.get("myocarditis").set(1, map.get("myocarditis").get(1) + 1);
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
						map.get("otitis").set(1, map.get("otitis").get(1) + 1);
						map.get("flu").set(1, map.get("flu").get(1) + 1);
						map.get("mono").set(1, map.get("mono").get(1) + 1);
						map.get("pneumonia").set(1, map.get("pneumonia").get(1) + 1);
						map.get("myocarditis").set(1, map.get("myocarditis").get(1) + 1);
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
						map.get("otitis").set(1, map.get("otitis").get(1) + 1);
						map.get("flu").set(1, map.get("flu").get(1) + 3);
						map.get("myocarditis").set(1, map.get("myocarditis").get(1) + 1);
						map.get("lyme disease").set(1, map.get("lyme disease").get(1) + 1);
						whatpain state51 = new whatpain();
						flowRunner.gotoState(state51, this, pain.this, event);
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
						map.get("otitis").set(1, map.get("otitis").get(1) + 1);
						map.get("flu").set(1, map.get("flu").get(1) + 3);
						map.get("myocarditis").set(1, map.get("myocarditis").get(1) + 1);
						map.get("lyme disease").set(1, map.get("lyme disease").get(1) + 1);
						iristk.situated.SituatedDialog.say state52 = dialog.new say();
						state52.setText("Keep it up!");
						if (!flowRunner.callState(state52, pain.this, event)) {
							eventResult = EVENT_ABORTED;
							break EXECUTION;
						}
						evaluate state53 = new evaluate();
						flowRunner.gotoState(state53, this, pain.this, event);
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
				iristk.situated.SituatedDialog.say state54 = dialog.new say();
				state54.setText("Are you in pain in another bodypart?");
				if (!flowRunner.callState(state54, repain.this, event)) {
					eventResult = EVENT_ABORTED;
					break EXECUTION;
				}
				Event sendEvent55 = new Event("action.gesture");
				sendEvent55.put("name", "emotion_anger");
				flowRunner.sendEvent(sendEvent55, repain.this, event);
				iristk.situated.SituatedDialog.listen state56 = dialog.new listen();
				if (!flowRunner.callState(state56, repain.this, event)) {
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
						whatpain state57 = new whatpain();
						flowRunner.gotoState(state57, this, repain.this, event);
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
						iristk.situated.SituatedDialog.say state58 = dialog.new say();
						state58.setText("Keep it up!");
						if (!flowRunner.callState(state58, repain.this, event)) {
							eventResult = EVENT_ABORTED;
							break EXECUTION;
						}
						evaluate state59 = new evaluate();
						flowRunner.gotoState(state59, this, repain.this, event);
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
				iristk.situated.SituatedDialog.say state60 = dialog.new say();
				state60.setText("Where are you suffering from pain? In your eyes, your ears, your muscles or your chest?");
				if (!flowRunner.callState(state60, whatpain.this, event)) {
					eventResult = EVENT_ABORTED;
					break EXECUTION;
				}
				iristk.situated.SituatedDialog.listen state61 = dialog.new listen();
				if (!flowRunner.callState(state61, whatpain.this, event)) {
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
						map.get("flu").set(0, map.get("flu").get(0) + 1);
						iristk.situated.SituatedDialog.say state62 = dialog.new say();
						state62.setText("Okay, your eyes");
						if (!flowRunner.callState(state62, whatpain.this, event)) {
							eventResult = EVENT_ABORTED;
							break EXECUTION;
						}
						repain state63 = new repain();
						flowRunner.gotoState(state63, this, whatpain.this, event);
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
						map.get("otitis").set(0, map.get("otitis").get(0) + 1);
						iristk.situated.SituatedDialog.say state64 = dialog.new say();
						state64.setText("Okay, your ears");
						if (!flowRunner.callState(state64, whatpain.this, event)) {
							eventResult = EVENT_ABORTED;
							break EXECUTION;
						}
						repain state65 = new repain();
						flowRunner.gotoState(state65, this, whatpain.this, event);
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
						map.get("lyme disease").set(0, map.get("lyme disease").get(0) + 1);
						map.get("flu").set(0, map.get("flu").get(0) + 1);
						iristk.situated.SituatedDialog.say state66 = dialog.new say();
						state66.setText("Okay, your muscles");
						if (!flowRunner.callState(state66, whatpain.this, event)) {
							eventResult = EVENT_ABORTED;
							break EXECUTION;
						}
						repain state67 = new repain();
						flowRunner.gotoState(state67, this, whatpain.this, event);
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
						map.get("flu").set(0, map.get("flu").get(0) + 1);
						map.get("myocarditis").set(0, map.get("myocarditis").get(0) + 1);
						iristk.situated.SituatedDialog.say state68 = dialog.new say();
						state68.setText("Okay, your chest");
						if (!flowRunner.callState(state68, whatpain.this, event)) {
							eventResult = EVENT_ABORTED;
							break EXECUTION;
						}
						repain state69 = new repain();
						flowRunner.gotoState(state69, this, whatpain.this, event);
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
					dizzy state70 = new dizzy();
					flowRunner.gotoState(state70, this, evaluate.this, event);
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
				iristk.situated.SituatedDialog.say state71 = dialog.new say();
				state71.setText("Are you frozen?");
				if (!flowRunner.callState(state71, frozen.this, event)) {
					eventResult = EVENT_ABORTED;
					break EXECUTION;
				}
				iristk.situated.SituatedDialog.listen state72 = dialog.new listen();
				if (!flowRunner.callState(state72, frozen.this, event)) {
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
						map.get("flu").set(0, map.get("flu").get(0) + 1); map.get("flu").set(1, map.get("flu").get(1) + 1);
						iristk.situated.SituatedDialog.say state73 = dialog.new say();
						state73.setText("Okay");
						if (!flowRunner.callState(state73, frozen.this, event)) {
							eventResult = EVENT_ABORTED;
							break EXECUTION;
						}
						runnose state74 = new runnose();
						flowRunner.gotoState(state74, this, frozen.this, event);
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
						map.get("flu").set(1, map.get("flu").get(1) + 1);
						iristk.situated.SituatedDialog.say state75 = dialog.new say();
						state75.setText("Good for you");
						if (!flowRunner.callState(state75, frozen.this, event)) {
							eventResult = EVENT_ABORTED;
							break EXECUTION;
						}
						runnose state76 = new runnose();
						flowRunner.gotoState(state76, this, frozen.this, event);
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
				iristk.situated.SituatedDialog.say state77 = dialog.new say();
				state77.setText("Do you have a runny nose?");
				if (!flowRunner.callState(state77, runnose.this, event)) {
					eventResult = EVENT_ABORTED;
					break EXECUTION;
				}
				iristk.situated.SituatedDialog.listen state78 = dialog.new listen();
				if (!flowRunner.callState(state78, runnose.this, event)) {
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
						map.get("comcold").set(0, map.get("comcold").get(0) + 1); map.get("comcold").set(1, map.get("comcold").get(1) + 1);
						map.get("flu").set(0, map.get("flu").get(0) + 1); map.get("flu").set(1, map.get("flu").get(1) + 1);
						iristk.situated.SituatedDialog.say state79 = dialog.new say();
						state79.setText("Okay");
						if (!flowRunner.callState(state79, runnose.this, event)) {
							eventResult = EVENT_ABORTED;
							break EXECUTION;
						}
						endevaluation state80 = new endevaluation();
						flowRunner.gotoState(state80, this, runnose.this, event);
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
						map.get("comcold").set(1, map.get("comcold").get(1) + 1);
						map.get("flu").set(1, map.get("flu").get(1) + 1);
						iristk.situated.SituatedDialog.say state81 = dialog.new say();
						state81.setText("Good for you");
						if (!flowRunner.callState(state81, runnose.this, event)) {
							eventResult = EVENT_ABORTED;
							break EXECUTION;
						}
						endevaluation state82 = new endevaluation();
						flowRunner.gotoState(state82, this, runnose.this, event);
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
				iristk.situated.SituatedDialog.say state83 = dialog.new say();
				state83.setText("Are you feeling dizzy?");
				if (!flowRunner.callState(state83, dizzy.this, event)) {
					eventResult = EVENT_ABORTED;
					break EXECUTION;
				}
				iristk.situated.SituatedDialog.listen state84 = dialog.new listen();
				if (!flowRunner.callState(state84, dizzy.this, event)) {
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
						map.get("concussion").set(0, map.get("concussion").get(0) + 1); map.get("concussion").set(1, map.get("concussion").get(1) + 1);
						iristk.situated.SituatedDialog.say state85 = dialog.new say();
						state85.setText("Okay");
						if (!flowRunner.callState(state85, dizzy.this, event)) {
							eventResult = EVENT_ABORTED;
							break EXECUTION;
						}
						nausea state86 = new nausea();
						flowRunner.gotoState(state86, this, dizzy.this, event);
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
						map.get("concussion").set(1, map.get("concussion").get(1) + 1);
						iristk.situated.SituatedDialog.say state87 = dialog.new say();
						state87.setText("Good for you");
						if (!flowRunner.callState(state87, dizzy.this, event)) {
							eventResult = EVENT_ABORTED;
							break EXECUTION;
						}
						nausea state88 = new nausea();
						flowRunner.gotoState(state88, this, dizzy.this, event);
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
				iristk.situated.SituatedDialog.say state89 = dialog.new say();
				state89.setText("Are you nauseous?");
				if (!flowRunner.callState(state89, nausea.this, event)) {
					eventResult = EVENT_ABORTED;
					break EXECUTION;
				}
				iristk.situated.SituatedDialog.listen state90 = dialog.new listen();
				if (!flowRunner.callState(state90, nausea.this, event)) {
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
						map.get("concussion").set(0, map.get("concussion").get(0) + 1); map.get("concussion").set(1, map.get("concussion").get(1) + 1);
						iristk.situated.SituatedDialog.say state91 = dialog.new say();
						state91.setText("Okay");
						if (!flowRunner.callState(state91, nausea.this, event)) {
							eventResult = EVENT_ABORTED;
							break EXECUTION;
						}
						vomit state92 = new vomit();
						flowRunner.gotoState(state92, this, nausea.this, event);
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
						map.get("concussion").set(1, map.get("concussion").get(1) + 1);
						iristk.situated.SituatedDialog.say state93 = dialog.new say();
						state93.setText("Good for you");
						if (!flowRunner.callState(state93, nausea.this, event)) {
							eventResult = EVENT_ABORTED;
							break EXECUTION;
						}
						vomit state94 = new vomit();
						flowRunner.gotoState(state94, this, nausea.this, event);
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
				iristk.situated.SituatedDialog.say state95 = dialog.new say();
				state95.setText("Have you vomited?");
				if (!flowRunner.callState(state95, vomit.this, event)) {
					eventResult = EVENT_ABORTED;
					break EXECUTION;
				}
				iristk.situated.SituatedDialog.listen state96 = dialog.new listen();
				if (!flowRunner.callState(state96, vomit.this, event)) {
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
						map.get("concussion").set(0, map.get("concussion").get(0) + 1); map.get("concussion").set(1, map.get("concussion").get(1) + 1);
						iristk.situated.SituatedDialog.say state97 = dialog.new say();
						state97.setText("Okay");
						if (!flowRunner.callState(state97, vomit.this, event)) {
							eventResult = EVENT_ABORTED;
							break EXECUTION;
						}
						amnesia state98 = new amnesia();
						flowRunner.gotoState(state98, this, vomit.this, event);
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
						map.get("concussion").set(1, map.get("concussion").get(1) + 1);
						iristk.situated.SituatedDialog.say state99 = dialog.new say();
						state99.setText("Good for you");
						if (!flowRunner.callState(state99, vomit.this, event)) {
							eventResult = EVENT_ABORTED;
							break EXECUTION;
						}
						amnesia state100 = new amnesia();
						flowRunner.gotoState(state100, this, vomit.this, event);
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
				iristk.situated.SituatedDialog.say state101 = dialog.new say();
				state101.setText("Are you experiencing amnesia?");
				if (!flowRunner.callState(state101, amnesia.this, event)) {
					eventResult = EVENT_ABORTED;
					break EXECUTION;
				}
				iristk.situated.SituatedDialog.listen state102 = dialog.new listen();
				if (!flowRunner.callState(state102, amnesia.this, event)) {
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
						map.get("concussion").set(0, map.get("concussion").get(0) + 1); map.get("concussion").set(1, map.get("concussion").get(1) + 1);
						iristk.situated.SituatedDialog.say state103 = dialog.new say();
						state103.setText("Okay");
						if (!flowRunner.callState(state103, amnesia.this, event)) {
							eventResult = EVENT_ABORTED;
							break EXECUTION;
						}
						endevaluation state104 = new endevaluation();
						flowRunner.gotoState(state104, this, amnesia.this, event);
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
						map.get("concussion").set(1, map.get("concussion").get(1) + 1);
						iristk.situated.SituatedDialog.say state105 = dialog.new say();
						state105.setText("Good for you");
						if (!flowRunner.callState(state105, amnesia.this, event)) {
							eventResult = EVENT_ABORTED;
							break EXECUTION;
						}
						endevaluation state106 = new endevaluation();
						flowRunner.gotoState(state106, this, amnesia.this, event);
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
				iristk.situated.SituatedDialog.say state107 = dialog.new say();
				state107.setText("Hope you don't die!");
				if (!flowRunner.callState(state107, endevaluation.this, event)) {
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
