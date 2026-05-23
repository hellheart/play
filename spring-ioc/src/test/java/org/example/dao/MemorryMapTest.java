package org.example.dao;

import com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Var;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.web.format.DateTimeFormatters;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StopWatch;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import sun.security.krb5.internal.crypto.crc32;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.StringWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.CollationKey;
import java.text.Collator;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.MessageFormat;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalUnit;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.Set;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import java.util.zip.CRC32;

@Slf4j
public class MemorryMapTest {

    @Test
    public void testFileInput() {
        StopWatch testFileInput = new StopWatch("testFileInput");
        testFileInput.start();
        try(InputStream inputStream = Files.newInputStream(Paths.get("D:\\java\\jdk-8u261\\jre\\lib\\rt.jar"))) {
            CRC32 crc32 = new CRC32();
            int read;
            while ((read = inputStream.read()) !=-1) {
                crc32.update(read);
            }
            long value = crc32.getValue();
            testFileInput.stop();
            log.info("crc32 value is {},testFileInput cost time is {} ms", value,testFileInput.getLastTaskTimeMillis());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testBufferInput() {
        StopWatch testFileInput = new StopWatch("testBufferInput");
        testFileInput.start();
        try(BufferedInputStream bufferedInputStream = new BufferedInputStream(Files.newInputStream(Paths.get("D:\\java\\jdk-8u261\\jre\\lib\\rt.jar")))) {
            CRC32 crc32 = new CRC32();
            int read;
            while ((read = bufferedInputStream.read()) !=-1) {
                crc32.update(read);
            }
            long value = crc32.getValue();
            testFileInput.stop();
            log.info("crc32 value is {},testBufferInput cost time is {} ms", value,testFileInput.getLastTaskTimeMillis());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRandomInput() {
        StopWatch testFileInput = new StopWatch("testRandomInput");
        testFileInput.start();
        try(RandomAccessFile randomAccessFile = new RandomAccessFile(Paths.get("D:\\java\\jdk-8u261\\jre\\lib\\rt.jar").toFile(), "r")) {
            CRC32 crc32 = new CRC32();
            long length = randomAccessFile.length();
            for (long i = 0; i < length; i++) {
                randomAccessFile.seek(i);
                int read = randomAccessFile.readByte();
                crc32.update(read);
            }
            long value = crc32.getValue();
            testFileInput.stop();
            log.info("crc32 value is {},testRandomInput cost time is {} ms", value,testFileInput.getLastTaskTimeMillis());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFileChannelInput() {
        StopWatch testFileInput = new StopWatch("testBufferInput");
        testFileInput.start();
        try(FileChannel open = FileChannel.open(Paths.get("D:\\java\\jdk-8u261\\jre\\lib\\rt.jar"))) {
            CRC32 crc32 = new CRC32();
            int size = (int)open.size();
            MappedByteBuffer map = open.map(FileChannel.MapMode.READ_ONLY, 0, size);
            for (int i = 0; i < size; i++) {
                byte b = map.get(i);
                crc32.update(b);
            }
            long value = crc32.getValue();
            testFileInput.stop();
            log.info("crc32 value is {},testBufferInput cost time is {} ms", value,testFileInput.getLastTaskTimeMillis());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * xml DTD
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    @Test
    public void testXmlDTDResolve() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactoryImpl.newInstance();
        documentBuilderFactory.setIgnoringElementContentWhitespace(true);
        documentBuilderFactory.setValidating(false);
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(Paths.get("D:\\test20220601\\play\\spring-ioc\\src\\test\\java\\org\\example\\dao\\mydtd.xml").toFile());
        NamedNodeMap attributes = document.getAttributes();
        for (int i = 0; i < attributes.getLength(); i++) {
            Node item = attributes.item(i);
            String nodeName = item.getNodeName();
            String nodeValue = item.getNodeValue();
            log.info("node name is {}, node value is {}", nodeName, nodeValue);
        }
    }

    @Test
    public void testXMLScjema() throws FileNotFoundException, XPathExpressionException {
        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xPath = xPathFactory.newXPath();
        NodeList evaluate = (NodeList)xPath.evaluate("/font", new InputSource(new FileInputStream(Paths.get("D:\\test20220601\\play\\spring-ioc\\src\\test\\java\\org\\example\\dao\\mydtd.xml").toFile())), XPathConstants.NODESET);
        log.info("result is {}", evaluate);
    }

    @Test
    public void testSAXParse() throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = saxParserFactory.newSAXParser();
        saxParser.parse("http://www.w3c.org/MarkUp", new DefaultHandler() {
            @Override
            public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                super.startElement(uri, localName, qName, attributes);
            }
        });

    }


    @Test
    public void testWebSocket() throws IOException {
        try(Socket socket = new Socket("time-a.nist.gov", 13);
            InputStream inputStream = socket.getInputStream();
            Scanner scanner = new Scanner(inputStream, "UTF-8")
        ) {
            while (scanner.hasNext()) {
                java.lang.String s = scanner.nextLine();
                System.out.println("scanner = " + scanner);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInetAddr() throws IOException {

    }

    /**public static void main(String[] args) throws IOException {

        try(ServerSocket serverSocket = new ServerSocket(8189);) {
            Socket accept = serverSocket.accept();
            InputStream inputStream = accept.getInputStream();
            OutputStream outputStream = accept.getOutputStream();
            Scanner scanner = new Scanner(inputStream);
            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(outputStream), true);
            printWriter.print("Hello!Enter Bye to Exit!");
            boolean done = false;
            while(!done && scanner.hasNext()) {
                String s = scanner.nextLine();
                printWriter.print("Echo" + s);
                if (s.trim().equals("Bye")) {
                    done = true;
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
     */

    public  static Connection getConnection() throws FileNotFoundException, SQLException {
        Properties properties = new Properties();
        FileInputStream inputStream = new FileInputStream(Paths.get("D:\\test20220601\\play\\spring-ioc\\src\\main\\resources\\jdbc.propertites").toFile());
        try {
            properties.load(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.setProperty("jdbc.driver", properties.getProperty("jdbc.driver"));
        Connection connection = DriverManager.getConnection(properties.getProperty("jdbc.url"), properties.getProperty("jdbc.username"), properties.getProperty("jdbc.password"));
        return connection;
    }

    @Test
    public void testJDBC() throws SQLException, FileNotFoundException {
        Connection connection = getConnection();
        DatabaseMetaData metaData1 = connection.getMetaData();
        try (
                ResultSet tables = metaData1.getTables(null, null, null, new String[]{"TABLE"})) {
            List<String> strings = new ArrayList<>();
            while (tables.next()) {
                String string = tables.getString(3);
                strings.add(string);
            }
            log.info("tables is {}", strings);
        }
        try (
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("select * from jobs");
                ) {
            ResultSetMetaData metaData = resultSet.getMetaData();
            List<String> strings1 = new ArrayList<>();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                String columnName = metaData.getColumnName(i);
                strings1.add(columnName);
            }
            log.info("columnName is {}", strings1);
            RowSetFactory rowSetFactory = RowSetProvider.newFactory();
            CachedRowSet cachedRowSet = rowSetFactory.createCachedRowSet();
            cachedRowSet.setTableName("jobs");
            cachedRowSet.populate(resultSet);
            String command = "select job_id from jobs";
            cachedRowSet.setCommand(command);
            cachedRowSet.setPageSize(2);
            cachedRowSet.execute();
            cachedRowSet.nextPage();
        }

    }

    @Test
    public void testLocalDate () {
        LocalDate now = LocalDate.now();
        String s = now.toString();
        System.out.println("s = " + s);

        LocalDate of = LocalDate.of(2012, 12, 22);
        System.out.println("of.toString() = " + of.toString());

        LocalDate localDate = of.plusDays(20);
        System.out.println("localDate = " + localDate);
        long until = now.until(of, ChronoUnit.DAYS);
        System.out.println("until = " + until);

        LocalDate with = now.with(TemporalAdjusters.nextOrSame(DayOfWeek.THURSDAY));
        System.out.println("with = " + with);

        TemporalAdjuster temporalAdjuster = TemporalAdjusters.ofDateAdjuster(w -> {
            do {w = w.plusDays(1);}
            while (w.getDayOfWeek().getValue() > 2);
            return w;
        });
        LocalDate with1 = now.with(temporalAdjuster);
        System.out.println("with1 = " + with1);
    }

    @Test
    public void testLocalTime() {
        LocalTime now = LocalTime.now();
        LocalTime of = LocalTime.of(22, 11, 33, 365);
        LocalTime localTime = now.plusMinutes(100);
        LocalTime localTime1 = of.minusMinutes(120);
        boolean before = now.isBefore(localTime1);
        boolean after = of.isAfter(localTime);
        System.out.println("now = " + now);
        System.out.println("of = " + of);
        System.out.println("localTime1 = " + localTime1);
        System.out.println("localTime = " + localTime);
        System.out.println("before = " + before);
        System.out.println("after = " + after);
        LocalDateTime now1 = LocalDateTime.now();
        System.out.println("now1 = " + now1);
    }

    @Test
    public void testZoneTime() {
        Set<String> availableZoneIds = ZoneId.getAvailableZoneIds();
        //availableZoneIds.forEach(System.out::println);
        ZonedDateTime of = ZonedDateTime.of(2013, 3, 31, 2, 30, 20, 1000, ZoneId.of("Europe/Berlin"));
        ZonedDateTime zonedDateTime = of.plusHours(1);
        System.out.println("zonedDateTime = " + zonedDateTime);

        ZonedDateTime am = ZonedDateTime.of(LocalDate.of(2013, 10, 27), LocalTime.of(2, 30), ZoneId.of("Europe/Berlin"));
        ZonedDateTime zonedDateTime1 = am.plusHours(1);
        System.out.println("zonedDateTime1 = " + zonedDateTime1);

        ZonedDateTime meetting = ZonedDateTime.of(LocalDate.of(2013, 10, 27), LocalTime.of(2, 30), ZoneId.of("Europe/Berlin"));
        ZonedDateTime zonedDateTime2 = meetting.plus(Duration.ofDays(7));
        System.out.println("zonedDateTime2 = " + zonedDateTime2);
        ZonedDateTime plus = meetting.plus(Period.ofDays(7));
        System.out.println("plus = " + plus);
    }

    @Test
    public void testTimeFormatter() {
        ZonedDateTime of = ZonedDateTime.of(LocalDate.of(2016, 3, 12), LocalTime.of(14, 20), ZoneId.of("Europe/Berlin"));
        String format = DateTimeFormatter.ISO_LOCAL_DATE.format(of);
        System.out.println("of = " + of);
        System.out.println("format = " + format);
        format = DateTimeFormatter.ISO_LOCAL_DATE.withLocale(Locale.CHINA).format(of);
        format = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).format(of);
        format = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withLocale(Locale.CHINA).format(of);
        format = DateTimeFormatter.ofPattern("yyyy-MM-dd hh-mm-ss nnnnn a ").format(of);
        System.out.println("format = " + format);

        for (DayOfWeek value : DayOfWeek.values()) {
            System.out.println(value.getDisplayName(TextStyle.SHORT, Locale.CHINA));
        }
    }

    @Test
    public void testTimeTransfer() {
        Date date = new Date();
        Instant instant = date.toInstant();
        Date from = Date.from(instant);
        System.out.println("instant = " + instant);
        System.out.println("from = " + from);

        ZonedDateTime of = ZonedDateTime.of(LocalDateTime.now(), ZoneId.of("Europe/Berlin"));
        GregorianCalendar from1 = GregorianCalendar.from(of);
        ZonedDateTime zonedDateTime = from1.toZonedDateTime();
        System.out.println("from1 = " + from1);
        System.out.println("zonedDateTime = " + zonedDateTime);

        Timestamp timestamp = new Timestamp(new Date().getTime());
        Instant instant1 = timestamp.toInstant();
        LocalDateTime localDateTime = timestamp.toLocalDateTime();
        Timestamp from2 = Timestamp.from(instant1);
        Timestamp timestamp1 = Timestamp.valueOf(localDateTime);
        System.out.println("instant1 = " + instant1);
        System.out.println("localDateTime = " + localDateTime);
        System.out.println("from2 = " + from2);

        java.sql.Date date1 = java.sql.Date.valueOf(LocalDate.now());
        LocalDate date2 = date1.toLocalDate();
        System.out.println("date1 = " + date1);
        System.out.println("date2 = " + date2);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE.withLocale(Locale.CHINA);
        Format format = dateTimeFormatter.toFormat();
        System.out.println("format = " + format);

        ZoneId of1 = ZoneId.of("Europe/Berlin");
        TimeZone timeZone = TimeZone.getTimeZone(of1);
        ZoneId zoneId = timeZone.toZoneId();
        System.out.println("timeZone = " + timeZone);
        System.out.println("zoneId = " + zoneId);

        Instant now = Instant.now();
        FileTime from3 = FileTime.from(now);
        Instant instant2 = from3.toInstant();
        System.out.println("from3 = " + from3);
        System.out.println("instant2 = " + instant2);
    }

    @Test
    public void testLocale() {
        Locale china = Locale.CHINA;
        String language = china.getLanguage();

        Locale locale = new Locale("zh", "CN");
        language = locale.getLanguage();
        String displayName = locale.getDisplayName();
        String country = locale.getCountry();
        String displayCountry = locale.getDisplayCountry();
        String displayScript = locale.getDisplayScript();
        String displayVariant = locale.getDisplayVariant();
        String s = locale.toLanguageTag();
        System.out.println("s = " + s);
        System.out.println("displayVariant = " + displayVariant);
        System.out.println("displayScript = " + displayScript);
        System.out.println("displayCountry = " + displayCountry);
        System.out.println("country = " + country);
        System.out.println("displayName = " + displayName);
        System.out.println("language = " + language);
    }

    @Test
    public void testDigitalFormatter() throws ParseException {
        Locale[] availableLocales = NumberFormat.getAvailableLocales();
        System.out.println(Arrays.toString(availableLocales));
        NumberFormat numberInstance = NumberFormat.getNumberInstance(Locale.GERMAN);
        double numb = 213.56;
        String format = numberInstance.format(numb);
        System.out.println("format = " + format);


        NumberFormat numberInstance1 = NumberFormat.getNumberInstance();
        String test = "565465.222";
        Number parse = numberInstance1.parse(test);
        double v = parse.doubleValue();
        System.out.println("v = " + v);
    }

    @Test
    public void testCurreny() throws ParseException {
        Set<Currency> availableCurrencies = Currency.getAvailableCurrencies();
        System.out.println("availableCurrencies = " + availableCurrencies);
        Currency instance = Currency.getInstance(Locale.CHINA);
        String displayName = instance.getDisplayName();
        String currencyCode = instance.getCurrencyCode();
        String symbol = instance.getSymbol();
        System.out.println("symbol = " + symbol);
        System.out.println("currencyCode = " + currencyCode);
        System.out.println("displayName = " + displayName);

        DecimalFormat currencyInstance = (DecimalFormat)NumberFormat.getCurrencyInstance(Locale.CHINA);
        currencyInstance.setCurrency(Currency.getInstance("JPY"));
        String format = currencyInstance.format(123.56);
        System.out.println("format = " + format);

        Currency yer = Currency.getInstance("GBP");
        String symbol1 = yer.getSymbol();
        System.out.println("symbol1 = " + symbol1);
    }

    @Test
    public void testTime() {
        FormatStyle style = FormatStyle.LONG;
        Set<String> availableZoneIds = ZoneId.getAvailableZoneIds();
        System.out.println("availableZoneIds = " + availableZoneIds);
        ZonedDateTime zonedDateTime = ZonedDateTime.of(LocalDate.of(2023, 11, 22), LocalTime.of(22, 10, 30), ZoneId.of("Asia/Shanghai"));
        String format = DateTimeFormatter.ofLocalizedDateTime(style).withLocale(Locale.CHINA).format(zonedDateTime);
        System.out.println("zonedDateTime = " + zonedDateTime);
        System.out.println("format = " + format);

        // 感觉不咋好用 文本要与FormatStyle格式对上 文本时间要与当地时间对上
        LocalTime parse = LocalTime.parse("2023年11月22日 下午10时10分30秒", DateTimeFormatter.ofLocalizedDateTime(style).withLocale(Locale.CHINA));
        System.out.println("parse = " + parse);

        DayOfWeek firstDayOfWeek = WeekFields.of(Locale.CHINA).getFirstDayOfWeek();
        System.out.println("firstDayOfWeek = " + firstDayOfWeek);

        for (DayOfWeek value : DayOfWeek.values()) {
            System.out.println(value.getDisplayName(TextStyle.FULL_STANDALONE, Locale.CANADA_FRENCH) + "");
        }
    }

    @Test
    public void testSortAndStand() {
        Locale[] availableLocales = Collator.getAvailableLocales();
        System.out.println(Arrays.toString(availableLocales));
        Locale sv = new Locale("sv");
        Collator instance = Collator.getInstance(sv);
        instance.setDecomposition(Collator.CANONICAL_DECOMPOSITION);
        List<String> strings = new ArrayList<>();
        strings.add("American");
        strings.add("able");
        strings.add("Zulu");
        strings.add("zebra");
        strings.add("\u00C5ngstr\u00f6m");
        strings.add("A\u030angstro\u0308m");
        strings.add("Angstrom");
        strings.add("Able");
        strings.add("office");
        strings.add("o\uFB03ce");
        strings.add("Java\u2122");
        strings.add("JavaTM");
        strings.sort(instance);
        strings.forEach(System.out::println);

        String name = "Ångström";
        String name2 = "Angstrom";
        CollationKey collationKey = instance.getCollationKey(name);
        int i = collationKey.compareTo(instance.getCollationKey(name2));
        System.out.println("i = " + i);
        String normalize = Normalizer.normalize(name, Normalizer.Form.NFD);
        System.out.println("normalize = " + normalize);
    }

    @Test
    public void testMessageFormat() {
        GregorianCalendar from = GregorianCalendar.from(ZonedDateTime.of(LocalDate.of(2013, 2, 12), LocalTime.of(2, 30), ZoneId.of("Asia/Shanghai")));
        String good = MessageFormat.format("{0,date,long}, I having a {1} day,it cost {2,number,integer}",
                from.getTime(), "good"
                , 99);
        System.out.println("good = " + good);
        String s = "{0,date,medium}, I having a {1,choice,0#good|1#bad|2#{1}} day,it cost {2,number,currency}";
        MessageFormat messageFormat = new MessageFormat(s,Locale.CHINA);
        String good1 = messageFormat.format(new Object[]{from.getTime(), 1
                , 99});
        System.out.println("good1 = " + good1);

    }

    @Test
    public void testEncoding() {
        Charset charset = Charset.defaultCharset();
        System.out.println("charset = " + charset);
    }

    @Test
    public void testResorce() {
        ResourceBundle bundle = ResourceBundle.getBundle("application",Locale.GERMANY);
        Set<String> strings = bundle.keySet();
        String hello = bundle.getString("hello");
        System.out.println("strings = " + hello);
    }

    @Test
    public void testScript() throws ScriptException {
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        scriptEngineManager.put("s", 123);
        List<ScriptEngineFactory> engineFactories = scriptEngineManager.getEngineFactories();
        for (ScriptEngineFactory engineFactory : engineFactories) {
            System.out.println(engineFactory.getEngineName());
            System.out.println(engineFactory.getLanguageName());
            System.out.println(engineFactory.getParameter("THREADING"));
        }
        ScriptEngine nashorn = scriptEngineManager.getEngineByName("nashorn");
        nashorn.eval("n = 100");
        Object eval = nashorn.eval("n+1");
        nashorn.put("n", 1000);
        eval = nashorn.eval("n * 100");
        nashorn.put("b", new JButton());
        eval = nashorn.eval("b.text= 'OK'");
        eval = nashorn.eval("s + 100");
        Bindings bindings = nashorn.createBindings();
        bindings.put("wo", "nidie");
        eval = nashorn.eval("'haha' + wo", bindings);

        ScriptContext context = nashorn.getContext();
        context.setWriter(new PrintWriter(new StringWriter(), true));
        eval = nashorn.eval("'println('hello world')'yhhn");
        System.out.println(eval);
    }

    @Test
    public void testByteCode() {
        Thread thread = Thread.currentThread();
        ClassLoader contextClassLoader = thread.getContextClassLoader();
        System.out.println("contextClassLoader = " + contextClassLoader);
    }

    @Test
    public  void testProfiler() {
        new Scanner(System.in).next();

    }
    public static void main(String[] args) {
        new Scanner(System.in).next();
    }

    @Test
    public void testMessDig() throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        String a = "我是你爸爸";
        byte[] bytes = a.getBytes();
        String s = Arrays.toString(bytes);
        System.out.println("s1 = " + s);
        messageDigest.update(bytes);
        byte[] digest = messageDigest.digest(bytes);
        s = Arrays.toString(digest);
        System.out.println("s = " + s);

    }
}


