import java.util.HashMap;
import java.util.Map;

public class EntityTest {
    private int a = 0;
    private Map<String, Object> map = new HashMap<>();
    public static void main(String[] args) {

        EntityTest e = new EntityTest();
        e.map.put("sasa", new EntityTest());
        System.out.printf("" + e);
    }


}
