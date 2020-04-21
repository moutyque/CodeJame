import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Test {
	static char[] arr = new char[]{'0','1','0','0','0','1','1','0','0','0'};

	public static void main(String[] args) {
		System.out.println("5 10");
		Scanner scan = new Scanner(System.in);
		String answer = scan.nextLine();
		System.err.println(answer);
		int index = 1;
		while(answer.length()==1 && index<151) {
			if(index-1%10==0) {
				Random rand = new Random();
				int choice = rand.nextInt(4);
				if(choice==1) {
					returnArr();
				}
				else if(choice ==2) {
					revertArr();
				}
				else if(choice ==3) {
					returnArr();
					revertArr();
				}
			}
			answer = scan.nextLine();
			index++;
		}
		scan.close();



	}

	public static void revertArr() {

		for(int i = 0 ; i< arr.length/2;i++) {
			char tmp = arr[arr.length-1-i];
			arr[arr.length-1-i]=arr[i];
			arr[i]=tmp;
		}
	}

	public static void returnArr() {
		for(int i = 0;i< arr.length;i++) {
			if(arr[i]=='1') {
				arr[i]='0';
			}
			else {
				arr[i]='1';
			}
		}
	}

}