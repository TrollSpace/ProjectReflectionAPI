public class MyClass {
    @BeforeSuite
    public static void initialize() {
        System.out.println("Init");
    }

    @AfterSuite
    public static void closeApp() {
        System.out.println("Close");
    }

    @Test(priority = 7)
    public static void test1() {
        System.out.println("test priority 7");
    }


    @Test(priority = 1)
    public static void test2() {
        System.out.println("test priority 1");
    }

    @Test
    public static void test3() {
        System.out.println("test without priority");
    }

}
