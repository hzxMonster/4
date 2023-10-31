import javax.jws.WebService;
import javax.xml.ws.Endpoint;

@WebService(endpointInterface = "IncomeTaxCalculator", targetNamespace = "http://incometaxcalculator.com/")
public class IncomeTaxCalculatorImpl implements IncomeTaxCalculator {
    public double calculateIncomeTax(double income) {
        double tax = 0;

        if (income <= 5000) {
            tax = 0;
        } else if (income <= 8000) {
            tax = (income - 5000) * 0.03;
        } else if (income <= 17000) {
            tax = (income - 8000) * 0.1 + 300;
        } else if (income <= 30000) {
            tax = (income - 17000) * 0.2 + 1300;
        } else {
            tax = (income - 30000) * 0.3 + 3800;
        }

        return tax;
    }

    public static void main(String[] args) {
        // 发布WebService服务
        Endpoint.publish("http://localhost:8082/incometaxcalculator", new IncomeTaxCalculatorImpl());
        System.out.println("IncomeTaxCalculator WebService服务已启动，访问地址：http://localhost:8082/incometaxcalculator");
    }
}
