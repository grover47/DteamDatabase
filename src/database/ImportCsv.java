package database;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import com.opencsv.CSVReader;

public class ImportCsv {
	public static void main(String[] args) {
		readCsv();
		readCsvUsingLoad();
	}

	private static void readCsv() {

		try (CSVReader reader = new CSVReader(new FileReader("C://Users//priya//Desktop//Reading.csv"), ',');
				Connection connection = DBConnection.getConnection();) {
			String insertQuery = "Insert into diabetic_dashboard_data.Reading (UserID,ReadingDate,TimeOfDayID,BloodGlucose,InsulinUnits) values (?,?,?,?,?)";
			PreparedStatement pstmt = connection.prepareStatement(insertQuery);
			String[] rowData = null;
			int i = 0;
			reader.readNext();
			while ((rowData = reader.readNext()) != null) {
				
				i=0;
				for (String data : rowData) {
					pstmt.setString(i+ 1, data);
					i++;
					
		//			if (++i % 3 == 0)
		//				pstmt.addBatch();// add batch

		//			if (i % 30 == 0) // insert when the batch size is 10
		//				pstmt.executeBatch();
				}
				pstmt.execute();

			}
			System.out.println("Data Successfully Uploaded");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void readCsvUsingLoad() {
		try (Connection connection = DBConnection.getConnection()) {

			String loadQuery = "LOAD DATA LOCAL INFILE '" + "C://Users//priya//Desktop//Reading.csv"
					+ "' INTO TABLE diabetic_dashboard_data.Reading FIELDS TERMINATED BY ','"
					+ " LINES TERMINATED BY '\n' (UserID,ReadingDate,TimeOfDayID,BloodGlucose,InsulinUnits) ";
			System.out.println(loadQuery);
			Statement stmt = connection.createStatement();
			stmt.execute(loadQuery);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
