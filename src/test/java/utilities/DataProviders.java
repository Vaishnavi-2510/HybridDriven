package utilities;

import java.io.IOException;
import org.testng.annotations.DataProvider;

public class DataProviders {

    @DataProvider(name = "LoginData")
    public Object[][] getData() throws IOException {

        String path = ".\\testData\\LoginData.xlsx";

        ExcelUtility xlutil = new ExcelUtility(path);

        int totalrows = xlutil.getRowCount("Sheet1");
        int totalcols = xlutil.getCellCount("Sheet1", 0);

        // -1 because we skip header row
        Object[][] logindata = new Object[totalrows - 1][totalcols];

        for (int i = 1; i < totalrows; i++) {   // ✅ FIXED (< instead of <=)
            for (int j = 0; j < totalcols; j++) {

                logindata[i - 1][j] = xlutil.getCellData("Sheet1", i, j);
            }
        }

        return logindata;
    }
}