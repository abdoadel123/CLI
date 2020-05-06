import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import static java.nio.file.StandardOpenOption.APPEND;

public class Terminal {

	public void cp(String sourcePath, String destinationPath) throws IOException {
		if (!sourcePath.contains(":\\")) {
			sourcePath = System.getProperty("user.dir") + "\\" + sourcePath;
		}
		File file1 = new File(sourcePath);
		if (file1.exists() && !file1.isDirectory()) {
			if (!destinationPath.contains(":\\")) {
				destinationPath = System.getProperty("user.dir") + "\\" + destinationPath;
			}

			File file3 = new File(destinationPath);
			if (!file3.exists()) {
				FileWriter file4 = new FileWriter(destinationPath);
				file4.close();
			}
			if (new File(file3.getParent()).exists() && new File(file3.getParent()).isDirectory()) {
				Files.write(file3.toPath(), Files.readAllLines(file1.toPath()), APPEND);
			} else
				System.out.println("Destination path not found");
		} else
			System.out.println("source file not found");
	}

	public void mv(String sourcePath, String destinationPath) throws IOException {
		File file = new File(destinationPath);
		File file2 = new File(sourcePath);
		if (file.isDirectory()) {
			destinationPath = destinationPath + "\\" + file2.getName();
			FileWriter file3 = new FileWriter(destinationPath);
			// System.out.println(file2.getName());
			file3.close();
		}
		cp(sourcePath, destinationPath);
		rm(sourcePath);

	}

	public void rm(String sourcePath) {
		if (!sourcePath.contains(":\\")) {
			sourcePath = System.getProperty("user.dir") + "\\" + sourcePath;
		}
		File file = new File(sourcePath);
		if (file.exists() && !file.isDirectory()) {
			file.delete();
		} else
			System.out.println("no such file");
	}

	public String pwd() {
		return System.getProperty("user.dir");
	}

	public ArrayList<String> cat(ArrayList<String> paths) throws IOException {
		ArrayList<String>fileOut=new ArrayList<>();
		for (String path : paths) {
			if (!path.contains(":\\")) {
				path = System.getProperty("user.dir") + "\\" + path;
			}
			FileReader fr = new FileReader(path);
			String line;
			BufferedReader bufRead = new BufferedReader(fr);
			do {

				line = bufRead.readLine();
				if (line==null||line.equals("")) {
				} else {
					fileOut.add(line);
				}
			} while (line != null);
			fr.close();
			bufRead.close();
		}
		return fileOut;
	}

	public void cd(String path) {

		if (!path.contains(":\\")) {
			path = System.getProperty("user.dir") + "\\" + path;
		}
		File dir = new File(path);
		if (dir.exists() && dir.isDirectory())
			System.setProperty("user.dir", path);
		else
			System.out.println("no such directory");
	}

	public void mkdir(String path) {
		if (!path.contains(":\\")) {
			path = System.getProperty("user.dir") + "\\" + path;
		}
		File dir=new File(path);
		if(dir.exists()&&dir.isDirectory()){
			System.out.println("Directory already exist");
		}
		else{
			dir.mkdirs();
		}
	}

	public ArrayList<String> ls() {
		ArrayList<String> filesName = new ArrayList<>();
		String currentPath = System.getProperty("user.dir");

		File[] files = new File(currentPath).listFiles();

		for (int i = 0; i < files.length; i++) {
			filesName.add(files[i].getName());
		}
		return filesName;
	}

	public void more(String fileName) {

	}

	public void rmdir(String sourcePath) {
		if (!sourcePath.contains(":\\")) {
			sourcePath = System.getProperty("user.dir") + "\\" + sourcePath;
		}
		File dir = new File(sourcePath);
		if (dir.exists() && dir.isDirectory() && dir.list().length == 0) {
			dir.delete();
		} else if (dir.exists() && dir.isDirectory() && dir.list().length != 0) {
			System.out.println("Can not delete that directory");
		} else
			System.out.println("no such file");
	}

	public String args(String cmd) {
		if(cmd.equals("ls"))
			return "no arguments";
		else if(cmd.equals("cd"))
			return "arg: DestinationPath";
		else if(cmd.equals("cp"))
			return "arg1: SourcePath, arg2: DestinationPath";
		else if(cmd.equals("cat"))
			return "arg: [file1,file2,..]";
		else if(cmd.equals("more"))
			return "arg: SuorcePath";
		else if(cmd.equals("mkdir"))
			return "arg: SuorcePath";
		else if(cmd.equals("rmdir"))
			return "arg: SuorcePath";
		else if(cmd.equals("rm"))
			return "arg: SuorcePath";
		else if(cmd.equals("args"))
			return "arg: Command";
		else if(cmd.equals("date"))
			return "no argument";
		else if(cmd.equals("help"))
			return "no argument";
		else if(cmd.equals("pwd"))
			return "no argument";
		else if(cmd.equals("clear"))
			return "no argument";
		else
			return "command not found";
		
	}

	public String date() {
		return ZonedDateTime.now().format(DateTimeFormatter.RFC_1123_DATE_TIME);
	}

	

	public ArrayList<String> help() {
		ArrayList<String> help=new ArrayList<>();
		help.add("clear : clear the current terminal screen.");
		help.add("cd    : changes the current directory to another one.");
		help.add("ls    : list each given file or directory name.");
		help.add("pwd   : Display current user directory.");
		help.add("cp    : copies file to another file .");
		help.add("mv    : move file to another file or move file to another directory .");
		help.add("rm    : delete specific file.");
		help.add("mkdir : create new directory.");
		help.add("rmdir : delete specific directory.");
		help.add("date  : display the date and time of the system.");
		help.add("cat   : Concatenate files and print on the standard output.");
		help.add("more  : display and scroll down the output in one direction only.");
		help.add("args  : List all command arguments.");
		help.add("exit  : Stop all.");
		help.add("|     : redirect the output of the previous command as in input to another command.");
		help.add(">     : Redirect the output to be written to a file .");
		help.add(">>    : Redirect the output to be append to a file .");
		return help;
	}

	public void clear() {
		for (int i = 0; i < 50; i++) {
			System.out.println("");
		}
	}

}
