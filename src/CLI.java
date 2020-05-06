import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class CLI {

	private static Scanner input;
	private static Parser parser = new Parser();
	static Terminal terminal = new Terminal();
	static ArrayList<String> arg = new ArrayList<>();
	static ArrayList<String> others = new ArrayList<>();
	static String CMD;
	static String defaultPath= System.getProperty("user.dir");

	public static void main(String[] args) throws IOException {
		input = new Scanner(System.in);
		String CMDInput = "";

		do {
			String Currentpath = System.getProperty("user.dir");
			if (CMDInput == "") {
				System.out.print(Currentpath + ":~$");
				CMDInput = input.nextLine();
			}

			if (CMDInput.equals("exit"))
				break;

			String parse = parser.parse(CMDInput);

			if (parse.equals("found")) {
				arg = parser.getArgument();
				others = parser.getOhers();
				CMD = parser.getCmd();

				if (CMD.equals("clear"))
					terminal.clear();

				else if (CMD.equals("date")) {
					String date = terminal.date();
					if (others.size() == 0)
						System.out.println(date);
					else if (others.size() == 2) {
						FileWriter file = null;
						if (others.get(0).equals(">"))
							file = new FileWriter(others.get(1));
						else if (others.get(0).equals(">>"))
							file = new FileWriter(others.get(1), true);
						file.write(date);
						file.write("\n");
						file.close();
					}
				}

				else if (CMD.equals("pwd")) {
					String pwd = terminal.pwd();
					if (others.size() == 0)
						System.out.println(pwd);
					else if (others.size() == 2) {
						FileWriter file = null;
						if (others.get(0).equals(">"))
							file = new FileWriter(others.get(1));
						else if (others.get(0).equals(">>"))
							file = new FileWriter(others.get(1), true);
						file.write(pwd);
						file.write("\n");
						file.close();
					}
				}

				else if (CMD.equals("cd"))
					terminal.cd(arg.get(0));

				else if (CMD.equals("rm"))
					terminal.rm(arg.get(0));

				else if (CMD.equals("rmdir"))
					terminal.rmdir(arg.get(0));

				else if (CMD.equals("cp"))
					terminal.cp(arg.get(0), arg.get(1));

				else if (CMD.equals("mv"))
					terminal.mv(arg.get(0), arg.get(1));

				else if (CMD.equals("cat")) {
					FileWriter file = null;
					if (arg.size() >= 1) {
						ArrayList<String> text = terminal.cat(arg);
						if (others.size() == 0)
							for (String line : text) {
								System.out.println(line);
							}
						else if (others.size() == 2) {
							if (others.get(0).equals(">"))
								file = new FileWriter(others.get(1));
							else if (others.get(0).equals(">>"))
								file = new FileWriter(others.get(1), true);
							for (String line : text) {
								file.write(line);
								file.write("\n");
							}
							file.close();
						}
					} else if (arg.size() == 0){
						file = new FileWriter(others.get(1));
						file.close();
					}
				} else if (CMD.equals("mkdir"))
					terminal.mkdir(arg.get(0));

				else if (CMD.equals("ls")) {
					ArrayList<String> files = terminal.ls();
					FileWriter file = null;
					if (others.size() == 0) {
						for (String line : files) {
							System.out.println(line);
						}
					} else if (others.size() == 2) {

						if (others.get(0).equals(">"))
							file = new FileWriter(others.get(1));
						else if (others.get(0).equals(">>"))
							file = new FileWriter(others.get(1), true);
						for (String line : files) {
							file.write(line);
							file.write("\n");
						}
						file.close();
					}

				}

				else if (CMD.equals("help")) {
					ArrayList<String> help = terminal.help();
					FileWriter file = null;
					if (others.size() == 0) {
						for (String line : help) {
							System.out.println(line);
						}
					} else if (others.size() == 2) {

						if (others.get(0).equals(">"))
							file = new FileWriter(others.get(1));
						else if (others.get(0).equals(">>"))
							file = new FileWriter(others.get(1), true);
						for (String line : help) {
							file.write(line);
							file.write("\n");
						}
						file.close();
					}

				}

				else if (CMD.equals("args")) {
					String ar = terminal.args(arg.get(0));
					FileWriter file = null;
					if (others.size() == 0 && !ar.equals("command not found")) {
						System.out.println(ar);
					} else if (others.size() == 2 && !ar.equals("command not found")) {

						if (others.get(0).equals(">"))
							file = new FileWriter(others.get(1));
						else if (others.get(0).equals(">>"))
							file = new FileWriter(others.get(1), true);
						file.write(ar);
						file.write("\n");
						file.close();
					} else
						System.out.println("command not found");
				}

				// | Operator
				if (others.size() >= 1 && others.get(0).equals("|")) {
					CMDInput = "";
					for (int i = 1; i < others.size(); i++)
						CMDInput += others.get(i) + " ";
					//dateSystem.out.println(CMDInput);
					continue;
				}

			} else if (parse.equals("not found"))
				System.out.println("Command not found");
			CMDInput = "";
		} while (!CMDInput.equals("exit"));
	}
}
