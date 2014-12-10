package iristk.app.doctor;

import java.util.ArrayList;
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
	private java.util.HashMap<String, java.util.ArrayList<Integer>> map;

	private void initVariables() {
		users = (iristk.situated.UserModel) dialog.getUsers();
		guess = asInteger(0);
		winningScore = asInteger(3);
		map = (java.util.HashMap<String, java.util.ArrayList<Integer>>) new java.util.HashMap<String, java.util.ArrayList<Integer>>();
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

	public java.util.HashMap<String, java.util.ArrayList<Integer>> getMap() {
		return this.map;
	}

	public void setMap(java.util.HashMap<String, java.util.ArrayList<Integer>> value) {
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
				ArrayList<Integer> list = new ArrayList<Integer>();list.add(0);list.add(0);
				map.put("flu", new ArrayList<Integer>(list));
				list = new ArrayList<Integer>();list.add(0);list.add(0);
				map.put("comcold", new ArrayList<Integer>(list));
				list = new ArrayList<Integer>();list.add(0);list.add(0);
				map.put("concussion", new ArrayList<Integer>(list));
				list = new ArrayList<Integer>();list.add(0);list.add(0);
				map.put("otitis", new ArrayList<Integer>(list));
				list = new ArrayList<Integer>();list.add(0);list.add(0);
				map.put("bronchitis", new ArrayList<Integer>(list));
				list = new ArrayList<Integer>();list.add(0);list.add(0);
				map.put("mono", new ArrayList<Integer>(list));
				list = new ArrayList<Integer>();list.add(0);list.add(0);
				map.put("myocarditis", new ArrayList<Integer>(list));
				list = new ArrayList<Integer>();list.add(0);list.add(0);
				map.put("pneumonia", new ArrayList<Integer>(list));
				list = new ArrayList<Integer>();list.add(0);list.add(0);
				map.put("lyme disease", new ArrayList<Integer>(list));
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
						map.get("comcold").set(0, map.get("comcold").get(0) + 1); map.get("comcold").set(1, map.get("comcold").get(1) + 1);
						map.get("flu").set(0, map.get("flu").get(0) + 1); map.get("flu").set(1, map.get("flu").get(1) + 1);
						map.get("bronchitis").set(0, map.get("bronchitis").get(0) + 1); map.get("bronchitis").set(1, map.get("bronchitis").get(1) + 1);
						map.get("pneumonia").set(0, map.get("pneumonia").get(0) + 1); map.get("pneumonia").set(1, map.get("pneumonia").get(1) + 1);
						map.get("mono").set(0, map.get("mono").get(0) + 1); map.get("mono").set(1, map.get("mono").get(1) + 1);
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
						iristk.situated.SituatedDialog.say state22 = dialog.new say();
						state22.setText("You don't have a soar throat!");
						if (!flowRunner.callState(state22, soar_throat.this, event)) {
							eventResult = EVENT_ABORTED;
							break EXECUTION;
						}
						map.get("comcold").set(1, map.get("comcold").get(1) + 1);
						map.get("flu").set(1, map.get("flu").get(1) + 1);
						map.get("bronchitis").set(1, map.get("bronchitis").get(1) + 1);
						map.get("pneumonia").set(1, map.get("pneumonia").get(1) + 1);
						map.get("mono").set(1, map.get("mono").get(1) + 1);
						Event sendEvent23 = new Event("action.gesture");
						sendEvent23.put("name", "wake_up");
						flowRunner.sendEvent(sendEvent23, soar_throat.this, event);
						cough state24 = new cough();
						flowRunner.gotoState(state24, this, soar_throat.this, event);
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
				iristk.situated.SituatedDialog.say state25 = dialog.new say();
				state25.setText("Are you suffering from cough?");
				if (!flowRunner.callState(state25, cough.this, event)) {
					eventResult = EVENT_ABORTED;
					break EXECUTION;
				}
				iristk.situated.SituatedDialog.listen state26 = dialog.new listen();
				if (!flowRunner.callState(state26, cough.this, event)) {
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
						map.get("bronchitis").set(0, map.get("bronchitis").get(0) + 1); map.get("bronchitis").set(1, map.get("bronchitis").get(1) + 1);
						map.get("pneumonia").set(0, map.get("pneumonia").get(0) + 1); map.get("pneumonia").set(1, map.get("pneumonia").get(1) + 1);
						iristk.situated.SituatedDialog.say state27 = dialog.new say();
						state27.setText("Okay");
						if (!flowRunner.callState(state27, cough.this, event)) {
							eventResult = EVENT_ABORTED;
							break EXECUTION;
						}
						headache state28 = new headache();
						flowRunner.gotoState(state28, this, cough.this, event);
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
						map.get("bronchitis").set(1, map.get("bronchitis").get(1) + 1);
						map.get("pneumonia").set(1, map.get("pneumonia").get(1) + 1);
						iristk.situated.SituatedDialog.say state29 = dialog.new say();
						state29.setText("Good for you");
						if (!flowRunner.callState(state29, cough.this, event)) {
							eventResult = EVENT_ABORTED;
							break EXECUTION;
						}
						headache state30 = new headache();
						flowRunner.gotoState(state30, this, cough.this, event);
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
				iristk.situated.SituatedDialog.say state31 = dialog.new say();
				state31.setText("Are you suffering from headache?");
				if (!flowRunner.callState(state31, headache.this, event)) {
					eventResult = EVENT_ABORTED;
					break EXECUTION;
				}
				iristk.situated.SituatedDialog.listen state32 = dialog.new listen();
				if (!flowRunner.callState(state32, headache.this, event)) {
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
						iristk.situated.SituatedDialog.say state33 = dialog.new say();
						state33.setText("Okay");
						if (!flowRunner.callState(state33, headache.this, event)) {
							eventResult = EVENT_ABORTED;
							break EXECUTION;
						}
						sleepy state34 = new sleepy();
						flowRunner.gotoState(state34, this, headache.this, event);
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
						iristk.situated.SituatedDialog.say state35 = dialog.new say();
						state35.setText("Good for you");
						if (!flowRunner.callState(state35, headache.this, event)) {
							eventResult = EVENT_ABORTED;
							break EXECUTION;
						}
						sleepy state36 = new sleepy();
						flowRunner.gotoState(state36, this, headache.this, event);
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
				iristk.situated.SituatedDialog.say state37 = dialog.new say();
				state37.setText("Are you sleepy?");
				if (!flowRunner.callState(state37, sleepy.this, event)) {
					eventResult = EVENT_ABORTED;
					break EXECUTION;
				}
				iristk.situated.SituatedDialog.listen state38 = dialog.new listen();
				if (!flowRunner.callState(state38, sleepy.this, event)) {
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
						iristk.situated.SituatedDialog.say state39 = dialog.new say();
						state39.setText("Okay");
						if (!flowRunner.callState(state39, sleepy.this, event)) {
							eventResult = EVENT_ABORTED;
							break EXECUTION;
						}
						fever state40 = new fever();
						flowRunner.gotoState(state40, this, sleepy.this, event);
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
						iristk.situated.SituatedDialog.say state41 = dialog.new say();
						state41.setText("Good for you, pneumonia");
						if (!flowRunner.callState(state41, sleepy.this, event)) {
							eventResult = EVENT_ABORTED;
							break EXECUTION;
						}
						fever state42 = new fever();
						flowRunner.gotoState(state42, this, sleepy.this, event);
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
				iristk.situated.SituatedDialog.say state43 = dialog.new say();
				state43.setText("Do you have fever?");
				if (!flowRunner.callState(state43, fever.this, event)) {
					eventResult = EVENT_ABORTED;
					break EXECUTION;
				}
				iristk.situated.SituatedDialog.listen state44 = dialog.new listen();
				if (!flowRunner.callState(state44, fever.this, event)) {
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
						iristk.situated.SituatedDialog.say state45 = dialog.new say();
						state45.setText("Okay");
						if (!flowRunner.callState(state45, fever.this, event)) {
							eventResult = EVENT_ABORTED;
							break EXECUTION;
						}
						pain state46 = new pain();
						flowRunner.gotoState(state46, this, fever.this, event);
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
						iristk.situated.SituatedDialog.say state47 = dialog.new say();
						state47.setText("Good for you");
						if (!flowRunner.callState(state47, fever.this, event)) {
							eventResult = EVENT_ABORTED;
							break EXECUTION;
						}
						pain state48 = new pain();
						flowRunner.gotoState(state48, this, fever.this, event);
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
				iristk.situated.SituatedDialog.say state49 = dialog.new say();
				state49.setText("Are you in pain?");
				if (!flowRunner.callState(state49, pain.this, event)) {
					eventResult = EVENT_ABORTED;
					break EXECUTION;
				}
				Event sendEvent50 = new Event("action.gesture");
				sendEvent50.put("name", "emotion_anger");
				flowRunner.sendEvent(sendEvent50, pain.this, event);
				iristk.situated.SituatedDialog.listen state51 = dialog.new listen();
				if (!flowRunner.callState(state51, pain.this, event)) {
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
						iristk.situated.SituatedDialog.say state52 = dialog.new say();
						state52.setText("Okay, now we will ask you some other questions");
						if (!flowRunner.callState(state52, pain.this, event)) {
							eventResult = EVENT_ABORTED;
							break EXECUTION;
						}
						map.get("otitis").set(1, map.get("otitis").get(1) + 1);
						map.get("flu").set(1, map.get("flu").get(1) + 3);
						map.get("myocarditis").set(1, map.get("myocarditis").get(1) + 1);
						map.get("lyme disease").set(1, map.get("lyme disease").get(1) + 1);
						whatpain state53 = new whatpain();
						flowRunner.gotoState(state53, this, pain.this, event);
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
						iristk.situated.SituatedDialog.say state54 = dialog.new say();
						state54.setText("Keep it up!");
						if (!flowRunner.callState(state54, pain.this, event)) {
							eventResult = EVENT_ABORTED;
							break EXECUTION;
						}
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
				iristk.situated.SituatedDialog.say state55 = dialog.new say();
				state55.setText("Where are you suffering from pain? In your eyes, your ears, your muscles or your chest?");
				if (!flowRunner.callState(state55, whatpain.this, event)) {
					eventResult = EVENT_ABORTED;
					break EXECUTION;
				}
				iristk.situated.SituatedDialog.listen state56 = dialog.new listen();
				if (!flowRunner.callState(state56, whatpain.this, event)) {
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
						map.get("flu").set(0, map.get("flu").get(0) + 1); map.get("flu").set(1, map.get("flu").get(1) + 1);
						iristk.situated.SituatedDialog.say state57 = dialog.new say();
						state57.setText("Okay, your eyes");
						if (!flowRunner.callState(state57, whatpain.this, event)) {
							eventResult = EVENT_ABORTED;
							break EXECUTION;
						}
						evaluate state58 = new evaluate();
						flowRunner.gotoState(state58, this, whatpain.this, event);
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
						map.get("otitis").set(0, map.get("otitis").get(0) + 1); map.get("otitis").set(1, map.get("otitis").get(1) + 1);
						iristk.situated.SituatedDialog.say state59 = dialog.new say();
						state59.setText("Okay, your ears");
						if (!flowRunner.callState(state59, whatpain.this, event)) {
							eventResult = EVENT_ABORTED;
							break EXECUTION;
						}
						evaluate state60 = new evaluate();
						flowRunner.gotoState(state60, this, whatpain.this, event);
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
						map.get("lyme disease").set(0, map.get("lyme disease").get(0) + 1); map.get("lyme disease").set(1, map.get("lyme disease").get(1) + 1);
						map.get("flu").set(0, map.get("flu").get(0) + 1); map.get("flu").set(1, map.get("flu").get(1) + 1);
						iristk.situated.SituatedDialog.say state61 = dialog.new say();
						state61.setText("Okay, your muscles");
						if (!flowRunner.callState(state61, whatpain.this, event)) {
							eventResult = EVENT_ABORTED;
							break EXECUTION;
						}
						evaluate state62 = new evaluate();
						flowRunner.gotoState(state62, this, whatpain.this, event);
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
						map.get("flu").set(0, map.get("flu").get(0) + 1); map.get("flu").set(1, map.get("flu").get(1) + 1);
						map.get("myocarditis").set(0, map.get("myocarditis").get(0) + 1); map.get("myocarditis").set(1, map.get("myocarditis").get(1) + 1);
						iristk.situated.SituatedDialog.say state63 = dialog.new say();
						state63.setText("Okay, your chest");
						if (!flowRunner.callState(state63, whatpain.this, event)) {
							eventResult = EVENT_ABORTED;
							break EXECUTION;
						}
						evaluate state64 = new evaluate();
						flowRunner.gotoState(state64, this, whatpain.this, event);
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
				System.out.println("comcold:"+map.get("comcold"));
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
