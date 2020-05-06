import java.util.ArrayList;

public class Parser {
	ArrayList<String> args = new ArrayList<>();
	ArrayList<String> others = new ArrayList<>();
	String cmd;

	public String parse(String input) {
		others = new ArrayList<>();
		args = new ArrayList<>();
		input = input.trim().replaceAll("\\s+", " ");
		String[] splitStr = input.split(" ");
		this.cmd = splitStr[0];

		// ls && pwd
		if (this.cmd.equals("ls") || this.cmd.equals("pwd")) {
			if (splitStr.length > 1
					&& (splitStr[1].equals("|") || splitStr[1].equals(">") || splitStr[1].equals(">>"))) {
				for (int i = 1; i < splitStr.length; i++) {
					others.add(splitStr[i]);
				}
				return "found";
			} else if (splitStr.length == 1)
				return "found";
			else {
				System.out.println("it take no arguments");
				return "";
			}
		}

		// clear
		else if (this.cmd.equals("clear")) {
			if (splitStr.length > 1 && splitStr[1].equals("|")) {
				for (int i = 1; i < splitStr.length; i++) {
					others.add(splitStr[i]);
				}
				return "found";
			} else if (splitStr.length == 1)
				return "found";
			else {
				System.out.println("it take no arguments");
				return "";
			}
		}

		// date && help
		else if (this.cmd.equals("date") || this.cmd.equals("help")) {
			if (splitStr.length == 1)
				return "found";
			else if(splitStr.length==3 &&(splitStr[1].equals(">")||splitStr[1].equals(">>"))){
				others.add(splitStr[1]);
				others.add(splitStr[2]);
				return "found";
			}
			else {
				System.out.println("it take no arguments");
				return "";
			}
		}

		// cp && mv
		else if (this.cmd.equals("cp") || this.cmd.equals("mv")) {
			if (splitStr.length < 3) {
				System.out.println("it requires 2 arguments");
				return "";
			} else {
				args.add(splitStr[1]);
				args.add(splitStr[2]);
				if (splitStr.length > 3
						&& (splitStr[3].equals("|") || splitStr[3].equals(">") || splitStr[3].equals(">>"))) {
					for (int i = 3; i < splitStr.length; i++) {
						others.add(splitStr[i]);
					}
					return "found";
				} else if (splitStr.length == 3)
					return "found";
				else {
					System.out.println("it requires 1 argument");
					return "";
				}
			}

		}

		else if (this.cmd.equals("rm") || this.cmd.equals("cd") || this.cmd.equals("mkdir")
				|| this.cmd.equals("rmdir")) {
			if (splitStr.length < 2) {
				if(this.cmd.equals("cd"))
				{
					args.add("F:\\CLI");
					return "found";
				}
				else{
					System.out.println("it requires 1 argument");
					return "";
				}
				
			} else {
				args.add(splitStr[1]);
				if (splitStr.length > 2 && (splitStr[2].equals("|") || splitStr[2].equals(">") || splitStr[2].equals(">>"))) {
					for (int i = 2; i < splitStr.length; i++) {
						others.add(splitStr[i]);
					}
					return "found";
				} else if (splitStr.length == 2)
					return "found";
				else {
					System.out.println("it requires 1 argument");
					return "";
				}
			}
		}

		else if (this.cmd.equals("more")) {
			if (splitStr.length < 2) {
				System.out.println("it requires 1 argument");
				return "";
			} else {
				args.add(splitStr[1]);

				if (splitStr.length == 2)
					return "found";
				else {
					System.out.println("it requires 1 argument");
					return "";
				}
			}
		}
		
		else if (this.cmd.equals("args")) {
			if (splitStr.length < 2) {
				System.out.println("it requires 1 argument");
				return "";
			} else if(splitStr.length ==4) {
				args.add(splitStr[1]);
				if (splitStr.length > 2 && (splitStr[2].equals(">") || splitStr[2].equals(">>"))) {
					for (int i = 2; i < splitStr.length; i++) {
						others.add(splitStr[i]);
					}
					return "found";
				} 
				else {
					System.out.println("it requires 1 argument");
					return "";
				}
			}
			else if (splitStr.length == 2)
			{
				args.add(splitStr[1]);
				return "found";
			}
			else
				return "";
		}
		
		else if (this.cmd.equals("cat")) {
			if (splitStr.length < 2) {
				System.out.println("it requires 1 or more argument or redirect operator");
				return "";
			} else {
				int i=1;
				while(i<splitStr.length && !(splitStr[i].equals("|") || splitStr[i].equals(">") || splitStr[i].equals(">>"))){
					args.add(splitStr[i]);
					i++;
				}
				while(i<splitStr.length){
					others.add(splitStr[i]);
					i++;
				}
				return "found";
			}
		}

		else
			return "not found";
	}

	public String getCmd() {
		return cmd;
	}

	public ArrayList<String> getArgument() {
		return args;
	}

	public ArrayList<String> getOhers() {
		return others;
	}
}
