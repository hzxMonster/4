import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.URL;
import java.util.Scanner;

public class IncomeTaxCalculatorClient {
    public static void main(String[] args) {
        try {
            // 创建服务访问的URL
            URL url = new URL("http://localhost:8082/incometaxcalculator?wsdl");

            // 创建服务的QName
            QName qname = new QName("http://incometaxcalculator.com/", "IncomeTaxCalculatorImplService");

            // 创建服务
            Service service = Service.create(url, qname);

            // 获取服务实现的接口
            IncomeTaxCalculator incomeTaxCalculator = service.getPort(IncomeTaxCalculator.class);

            // 从键盘读取输入值
            Scanner scanner = new Scanner(System.in);
            System.out.print("请输入收入金额：");
            double income = scanner.nextDouble();

            // 调用服务的方法
            double result = incomeTaxCalculator.calculateIncomeTax(income);
            System.out.println("计算结果：" + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
