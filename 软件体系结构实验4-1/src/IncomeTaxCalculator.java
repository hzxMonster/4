import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(targetNamespace = "http://incometaxcalculator.com/")
public interface IncomeTaxCalculator {
    @WebMethod
    double calculateIncomeTax(double income);
}