package Utilities;

import java.lang.reflect.Field;
import java.util.Map;

public class TestContext {
	
	public static String OrgID;
    public static String UserName;
    public static String Password;
    public static String StaffNo;
    public static String LeaveType;
    public static String Reason;

    private static int currentRow        = 1;
    private static int scenarioCount     = 0;  // tracks scenarios executed in current row
    private static int totalScenarios    = 0;  // total scenarios in feature file

    public static void setTotalScenarios(int count) {
        totalScenarios = count;
    }

    public static int getTotalScenarios() {
        return totalScenarios;
    }

    public static int getCurrentRow() {
        return currentRow;
    }

    public static void incrementScenarioCount() {
        scenarioCount++;
        // Once all scenarios for this row are done → move to next row
        if (scenarioCount >= totalScenarios) {
            scenarioCount = 0;
            currentRow++;
            System.out.println("\n🔄 All scenarios done for Row " 
                + (currentRow - 1) + " → Moving to Row " + currentRow);
        }
    }

    public static void resetAll() {
        currentRow     = 1;
        scenarioCount  = 0;
    }

    public static void setRowData(Map<String, String> rowData) {
        for (Map.Entry<String, String> entry : rowData.entrySet()) {
            try {
                Field field = TestContext.class.getDeclaredField(entry.getKey());
                field.setAccessible(true);
                field.set(null, entry.getValue());
            } catch (NoSuchFieldException e) {
                System.out.println("⚠️ Skipped unknown column: " + entry.getKey());
            } catch (IllegalAccessException e) {
                System.out.println("❌ Cannot access field: " + entry.getKey());
            }
        }
    }

}
