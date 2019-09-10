import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class test {
    @Test
    public void test1(){
        ApplicationContext ac=new ClassPathXmlApplicationContext("spring.xml");
        DriverManagerDataSource dataSource = ac.getBean("dataSource", DriverManagerDataSource.class);
        String url = dataSource.getUrl();
        System.out.println(url);
    }
}
