import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherApp extends JFrame {
    private JTextField cityField;
    private JTextArea weatherArea;

    public WeatherApp() {
        setTitle("天气预报系统");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        JLabel cityLabel = new JLabel("城市名称:");
        cityField = new JTextField(20);
        JButton getWeatherButton = new JButton("获取天气");
        weatherArea = new JTextArea();
        weatherArea.setEditable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(cityLabel, BorderLayout.WEST);
        panel.add(cityField, BorderLayout.CENTER);
        panel.add(getWeatherButton, BorderLayout.EAST);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panel, BorderLayout.NORTH);
        getContentPane().add(new JScrollPane(weatherArea), BorderLayout.CENTER);

        getWeatherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String city = cityField.getText();
                String weather = getWeather(city);
                weatherArea.setText(city + "的天气情况：" + weather);
            }
        });
    }

    public String getWeather(String city) {
        String url = "http://ws.webxml.com.cn/WebServices/WeatherWS.asmx/getWeather?theCityCode=" + city + "&theUserID=";
        StringBuilder response = new StringBuilder();
        try {
            URL apiUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/xml");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String weather = parseWeather(response.toString());
        return weather;
    }


    public static String parseWeather(String xml) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new ByteArrayInputStream(xml.getBytes()));

            NodeList nodeList = doc.getElementsByTagName("string");
            StringBuilder weatherBuilder = new StringBuilder();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                String content = element.getTextContent().trim();
                if(content.contains("gif")){
                    continue;
                }
                if(content.matches(".*\\b\\d{4,5}\\b.*")){
                    continue;
                }
                weatherBuilder.append(content).append("\n");

            }
            return weatherBuilder.toString().trim();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                WeatherApp app = new WeatherApp();
                app.setVisible(true);
            }
        });
    }
}
