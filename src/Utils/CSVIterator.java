package Utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class CSVIterator implements Iterator<String[]>, Iterable<String[]> {

	BufferedReader fileInput;
	String seperator;
	String line;
	int characterCheck;

	public CSVIterator(BufferedReader fileInput, String seperator) {
		this.fileInput = fileInput;
		this.seperator = seperator;
		this.characterCheck = 1;
	}

	public CSVIterator(BufferedReader fileInput, String seperator, int characterCheck) {
		this.fileInput = fileInput;
		this.seperator = seperator;
		this.characterCheck = characterCheck;
	}

	@Override
	public Iterator<String[]> iterator() {
		return this;
	}

	@Override
	public boolean hasNext() {
		try {
			line = fileInput.readLine();

			while (line != null && !line.matches(".*\\d+.*")) {
				line = fileInput.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (line != null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String[] next() {
		String[] col = splitCsvWithQuotes(line);
		return col;
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub

	}

	private String[] splitCsvWithQuotes(String arg) {
		boolean foundEndQuote = false;
		boolean foundStartQuote = false;
		StringBuilder output = new StringBuilder();
		ArrayList<String> newLine = new ArrayList<String>();
		for (char element : arg.toCharArray()) {
			if (foundEndQuote) {
				foundStartQuote = false;
				foundEndQuote = false;
			}
			if (element == ((char) 34) & (!foundEndQuote) & foundStartQuote) {
				foundEndQuote = true;
			}

			if (element == ((char) 34) & !foundStartQuote) {
				foundStartQuote = true;
			}

			if ((element == ((char) 44) && !foundStartQuote)) {
				newLine.add(output.toString());
				output = new StringBuilder();
			} else {
				output.append(element);
			}
		}
		newLine.add(output.toString());
		return newLine.toArray(new String[newLine.size()]);
	}
}