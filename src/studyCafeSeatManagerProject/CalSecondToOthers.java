package studyCafeSeatManagerProject;

public class CalSecondToOthers {
	public static void main(String[] args) {
		calTime(1);
		calTime(59);
		calTime(60);
		calTime(941);
		calTime(3600);
		calTime(15200);
		calTime(86399);
		calTime(86400);
		calTime(90000);
		calTime(186542);
		
	}
	public static void calTime(int time) {
		if(time < 60) {
			System.out.println(time + " �� ");
		} else if(time < 3600) {
			System.out.println(time/60 + " �� " + time%60 + " �� ");
		} else if(time < 86400) {
			System.out.println(time/3600 + " �ð� " + (time%3600)/60 + 
					" �� " + (time%3600)%60 + " �� ");
		} else {
			System.out.println(time/86400 + " �� " + (time%86400)/3600 + 
					" �ð� " + ((time%86400)%3600)/60 + " �� " + 
					(((time%86400)%3600)/60)%60 + " �� ");
		}
	}
}
