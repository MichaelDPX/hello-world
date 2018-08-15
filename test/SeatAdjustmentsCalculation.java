package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class SeatAdjustmentsCalculation {
	/*
	 * This method is used to calculate the number of seat adjustments,
	 * due to given input user preference sequence and policy.
	 */
	public static int calculateSeatAdjustments(ToiletSeat seat, char[] preferenceSeq, char policy) {
		int count = 0;
		for (int i = 0; i < preferenceSeq.length; i++) {
			// A person may have to adjust the seat prior to using the seat
			if (seat.getSeatStatus() != preferenceSeq[i]) {
				seat.adjustSeat(preferenceSeq[i]);
				count++;
			}
			// A person may need to adjust the seat before leaving, due to policy
			switch (policy) {
			// Up and Down policy
			case 'U':
			case 'D':
				if (seat.getSeatStatus() != policy) {
					seat.adjustSeat(policy);
					count++;
				}
				break;
			// User Preferred policy
			case 'P':
				if (seat.getSeatStatus() != preferenceSeq[i]) {
					seat.adjustSeat(preferenceSeq[i]);
					count++;
				}
				break;
			default:
			}

		}
		return count;
	}
	/*
	 * This method is to valid whether the input sequence just contains the character 'U' and 'D',
	 * and whether the sequence has a length at least 2 but no more than 1000.
	 */
	public static boolean isValidInput(String input) {
		if (input.matches("^[UD]{2,1000}$")) {
			return true;
		} else {
			return false;
		}
	}
	/*
	 * This method is used to read input sequence from console.
	 */
	public static String readInput(BufferedReader reader) {
		String input = null;
		boolean keepLoop = true;
		try {
			while (keepLoop) {
				System.out.println("Please input the sequence that people want the seat up or down:");
				input = reader.readLine();
				if (!isValidInput(input)) {
					System.out.println("The input is not valid!");		
				} else {
					keepLoop = false;
				}
			}
		} catch (IOException ioe) {
			System.err.println("Erro occured when reading input, exit... application");
			System.exit(-1);
		}
		return input;
	}

	public static void main(String[] args) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		while (true) {
			String input = readInput(reader);
			char[] preferenceSeq = input.toCharArray();
			char initStatus = preferenceSeq[0];
			ToiletSeat seat = new ToiletSeat();
			preferenceSeq = Arrays.copyOfRange(preferenceSeq, 1, preferenceSeq.length);

			// Calculate and output the number of seat adjustments with policy UP 'U'
			char policy = 'U';
			seat.init(initStatus);
			int adjustmentsWithPolicyUp = calculateSeatAdjustments(seat, preferenceSeq, policy);
			System.out.println("Policy - Leave Seat Up: " + adjustmentsWithPolicyUp);

			// Calculate and output the number of seat adjustments with policy Down 'D'
			policy = 'D';
			seat.init(initStatus);
			int adjustmentsWithPolicyDown = calculateSeatAdjustments(seat, preferenceSeq, policy);
			System.out.println("Policy - Leave Seat Down: " + adjustmentsWithPolicyDown);

			// Calculate and output the number of seat adjustments with policy User
			// Preferred 'P'
			policy = 'P';
			seat.init(initStatus);
			int adjustmentsWithPolicyPreferred = calculateSeatAdjustments(seat, preferenceSeq, policy);
			System.out.println("Policy - Leave Seat User Preferred: " + adjustmentsWithPolicyPreferred);
		}
	}

}

class ToiletSeat {
	private char seatStatus = 'P';
	
	void init(char initStatus) {
		this.seatStatus = initStatus;
	}

	void adjustSeat(char status) {
		this.seatStatus = status;
	}

	char getSeatStatus() {
		return this.seatStatus;
	}
}
