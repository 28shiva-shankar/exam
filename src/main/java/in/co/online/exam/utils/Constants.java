package in.co.online.exam.utils;

public class Constants {

	public static final int EXAM_DURATION_IN_MINUTES = 90;

	public static final int CORRECT_ANSWER_SCORE = 1;

	public static final int INCORRECT_ANSWER_SCORE = 0;

	public enum QuestionType {
		SINGLE, MULTI, TEXT
	}

	public enum QuestionCategory {
		APTITUDE(10), LOGICAL(10), TECHNICAL(10);

		public final int count;

		private QuestionCategory(int count) {
			this.count = count;
		}

	}
}
