
package bfst19.addressparser;

public class AddressBook {
	public static void main(String[] args) {
		if (args.length != 0) {
			System.out.println("Parsed Address:\n" + Address.parse(args[0]));
		} else {
			System.out.println("No arg given");
		}
	}
}
